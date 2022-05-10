package io.muenchendigital.digiwf.email.integration.domain.service;

import io.muenchendigital.digiwf.email.integration.domain.exception.MissingInformationMailException;
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
    private final int EXPIRES_IN_MINUTES = 3;

    /**
     * Send a mail.
     *
     * @param mail mail that is sent
     */
    public void sendMail(final Mail mail) throws MissingInformationMailException {
        final StringBuilder exceptionText = new StringBuilder();
        if (StringUtils.isEmpty(mail.getReceivers())) {
            exceptionText.append("No receivers given. ");
        }
        if (StringUtils.isEmpty(mail.getSubject())) {
            exceptionText.append("No subject given. ");
        }
        if (StringUtils.isEmpty(mail.getBody())) {
            exceptionText.append("No body given. ");
        }

        if (StringUtils.isNotEmpty(exceptionText)) {
            throw new MissingInformationMailException(exceptionText.toString());
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
            helper.setFrom(fromAdress);

            if (mail.hasReplyTo()) {
                helper.setReplyTo(mail.getReplyTo());
            }

            if (mail.hasAttachement()) {
                for (val attachment : mail.getAttachments()) {
                    if (attachment.hasAttachmentPath()) {
                        final byte[] binaryFile;
                        if (attachment.hasDocumentStorageUrl()) {
                            binaryFile = this.documentStorageFileRepository.getFile(
                                    attachment.getAttachmentPath(),
                                    EXPIRES_IN_MINUTES,
                                    attachment.getDocumentStorageUrl()
                            );
                        } else {
                            binaryFile = this.documentStorageFileRepository.getFile(
                                    attachment.getAttachmentPath(),
                                    EXPIRES_IN_MINUTES
                            );
                        }
                        final Tika tika = new Tika();
                        val file = new ByteArrayDataSource(binaryFile, tika.detect(binaryFile));
                        val fileName = StringUtils.substringAfterLast(attachment.getAttachmentPath(), "/");
                        helper.addAttachment(fileName, file);
                    }
                }
            }
        };

        this.mailSender.send(preparator);

        log.info("Mail sent to: {})", mail.getReceivers());
    }

}
