package io.muenchendigital.digiwf.email.integration.domain.service;

import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import io.muenchendigital.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;

@Slf4j
@AllArgsConstructor
public class MailingService {

    private final JavaMailSender mailSender;
    private final String fromAdress;
    private final DocumentStorageFileRepository documentStorageFileRepository;

    /**
     * Send a mail.
     *
     * @param mail mail that is sent
     */
    public void sendMail(final Mail mail) {

            //handler
            final MimeMessagePreparator preparator = mimeMessage -> {
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceivers()));

                val helper = new MimeMessageHelper(mimeMessage, true);
                helper.setText(mail.getBody());
                helper.setSubject(mail.getSubject());
                helper.setFrom(fromAdress);

                if (mail.hasReplyTo()) {
                    helper.setReplyTo(mail.getReplyTo());
                }

                if (mail.hasAttachement()) {
                    for (val attachmentPath : mail.getAttachmentPaths()) {
                        final byte[] binaryFile = this.documentStorageFileRepository.getFile(
                                attachmentPath,
                                3
                        );
                        final Tika tika = new Tika();
                        val file = new ByteArrayDataSource(binaryFile, tika.detect(binaryFile));
                        val fileName = StringUtils.substringAfterLast(attachmentPath, "/");
                        helper.addAttachment(fileName, file);
                    }
                }
            };

            this.mailSender.send(preparator);

            log.info("Mail sent to: {})", mail.getReceivers());
    }

}
