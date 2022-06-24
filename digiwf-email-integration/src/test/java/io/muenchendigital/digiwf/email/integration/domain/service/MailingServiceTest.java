package io.muenchendigital.digiwf.email.integration.domain.service;

import io.muenchendigital.digiwf.email.integration.domain.model.Attachment;
import io.muenchendigital.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class MailingServiceTest {

    @Mock
    private S3FileTransferRepository s3FileTransferRepository;

    @Mock
    private JavaMailSender javaMailSender;

    private MailingService mailingService;

    @BeforeEach
    public void beforeEach() {
        this.mailingService = new MailingService(this.javaMailSender, "from@example.com", this.s3FileTransferRepository);

    }

    @Test
    void isAttachmentPathAndDocumentStorageNotBlank() {
        final Attachment attachment = new Attachment();

        assertThat(this.mailingService.isPresignedUrlAndDocumentStorageNotBlank(attachment), is(false));
        attachment.setS3PresignedUrl("");
        assertThat(this.mailingService.isPresignedUrlAndDocumentStorageNotBlank(attachment), is(false));
        attachment.setFileName("");
        assertThat(this.mailingService.isPresignedUrlAndDocumentStorageNotBlank(attachment), is(false));
        attachment.setS3PresignedUrl("Path");
        assertThat(this.mailingService.isPresignedUrlAndDocumentStorageNotBlank(attachment), is(false));
        attachment.setS3PresignedUrl("");
        attachment.setFileName("Url");
        assertThat(this.mailingService.isPresignedUrlAndDocumentStorageNotBlank(attachment), is(false));
        attachment.setS3PresignedUrl("Path");
        assertThat(this.mailingService.isPresignedUrlAndDocumentStorageNotBlank(attachment), is(true));
    }
}