package io.muenchendigital.digiwf.email.integration.domain.service;

import io.muenchendigital.digiwf.email.integration.domain.exception.MissingInformationMailException;
import io.muenchendigital.digiwf.email.integration.domain.model.Attachment;
import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import io.muenchendigital.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
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

@Slf4j
@AllArgsConstructor
public class MailingService {

    private final JavaMailSender mailSender;
    private final String fromAdress;
    private final S3FileTransferRepository s3FileTransferRepository;

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
            helper.setFrom(this.fromAdress);

            if (StringUtils.isNotBlank(mail.getReplyTo())) {
                helper.setReplyTo(mail.getReplyTo());
            }

            if (CollectionUtils.isNotEmpty(mail.getAttachments())) {
                for (val attachment : mail.getAttachments()) {
                    if (this.isPresignedUrlAndDocumentStorageNotBlank(attachment)) {
                        final byte[] binaryFile = this.s3FileTransferRepository.getFile(attachment.getS3PresignedUrl());
                        val tika = new Tika();
                        val file = new ByteArrayDataSource(binaryFile, tika.detect(binaryFile));
                        helper.addAttachment(attachment.getFileName(), file);
                    } else {
                        log.error("Attachment could not be loaded as mandatory fields were missing: {}", attachment);
                    }
                }
            }
        };

        this.mailSender.send(preparator);

        log.debug("Mail sent to: {})", mail.getReceivers());
    }

    protected boolean isPresignedUrlAndDocumentStorageNotBlank(final Attachment attachment) {
        return StringUtils.isNotBlank(attachment.getS3PresignedUrl())
                && StringUtils.isNotBlank(attachment.getFileName());
    }
    
}
