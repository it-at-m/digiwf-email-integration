package io.muenchendigital.digiwf.email.integration.domain.service;

import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.MediaType;
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

    /**
     * Send a mail.
     *
     * @param mail mail that is sent
     */
    public void sendMail(final Mail mail) {
//        this.mailConfigurationHandler.handleMail(mail);

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
                val attachment = new ByteArrayDataSource(mail.getAttachment().getContent(), MediaType.APPLICATION_PDF.toString());
                helper.addAttachment(mail.getAttachment().getName(), attachment);
            }
        };

        this.mailSender.send(preparator);

        log.info("mail sent to: {})", mail.getReceivers());
    }
}
