package io.muenchendigital.digiwf.email.integration.domain.service;

import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;
import javax.validation.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Set;

@Slf4j
@AllArgsConstructor
public class MailingService {

    private final JavaMailSender mailSender;
    private final String fromAdress;
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(60))
            .build();

    /**
     * Send a mail.
     *
     * @param mail mail that is sent
     */
    public void sendMail(final Mail mail) throws RuntimeException {
        // validation
        final Validator validator = this.validatorFactory.getValidator();
        final Set<ConstraintViolation<Mail>> mailViolations = validator.validate(mail);
        if (!mailViolations.isEmpty()) {
            throw new ConstraintViolationException(mailViolations);
        }

        //handler
        final MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceivers()));

            if (StringUtils.isNotEmpty(mail.getReceiversCc())) {
                mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mail.getReceiversCc()));
            }
            if (StringUtils.isNotEmpty(mail.getReceiversBcc())) {
                mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mail.getReceiversBcc()));
            }

            val helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody());
            helper.setFrom(this.fromAdress);

            if (StringUtils.isNotBlank(mail.getReplyTo())) {
                helper.setReplyTo(mail.getReplyTo());
            }

            // mail attachments
            if (CollectionUtils.isNotEmpty(mail.getAttachments())) {
                for (val attachment : mail.getAttachments()) {
                    // TODO @lmoesle async?
                    final HttpRequest request = HttpRequest.newBuilder()
                            .GET()
                            .uri(URI.create(attachment.getUrl()))
                            .build();
                    final HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    // don't send the mail if any file is missing
                    if (response.statusCode() >= 400) {
                        log.error("An attachment could not be loaded: {}", attachment);
                        throw new RuntimeException(String.format("Could not download file %s", attachment.getPath()));
                    }
                    log.info("Downloaded file {} with status {}", attachment.getPath(), response.statusCode());
                    final String binaryFile = response.body();
                    final Tika tika = new Tika();
                    // TODO files are broken if you download and try to open them
                    final ByteArrayDataSource file = new ByteArrayDataSource(binaryFile, tika.detect(binaryFile));
                    final String fileName = StringUtils.substringAfterLast(attachment.getPath(), "/");
                    helper.addAttachment(fileName, file);
                }
            }
        };

        this.mailSender.send(preparator);
        log.info("Mail sent to: {})", mail.getReceivers());
    }

}
