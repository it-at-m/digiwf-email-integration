package io.muenchendigital.digiwf.email.integration.domain.service;

import io.muenchendigital.digiwf.email.integration.domain.model.Attachment;
import io.muenchendigital.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
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
    private DocumentStorageFileRepository documentStorageFileRepository;

    @Mock
    private JavaMailSender javaMailSender;

    private MailingService mailingService;

    @BeforeEach
    public void beforeEach() {
        this.mailingService = new MailingService(this.javaMailSender, "from@example.com", this.documentStorageFileRepository);

    }

    @Test
    void isAttachmentPathAndDocumentStorageNotBlank() {
        final Attachment attachment = new Attachment();

        assertThat(mailingService.isAttachmentPathAndDocumentStorageNotBlank(attachment), is(false));
        attachment.setAttachmentPath("");
        assertThat(mailingService.isAttachmentPathAndDocumentStorageNotBlank(attachment), is(false));
        attachment.setDocumentStorageUrl("");
        assertThat(mailingService.isAttachmentPathAndDocumentStorageNotBlank(attachment), is(false));
        attachment.setAttachmentPath("Path");
        assertThat(mailingService.isAttachmentPathAndDocumentStorageNotBlank(attachment), is(false));
        attachment.setAttachmentPath("");
        attachment.setDocumentStorageUrl("Url");
        assertThat(mailingService.isAttachmentPathAndDocumentStorageNotBlank(attachment), is(false));
        attachment.setAttachmentPath("Path");
        assertThat(mailingService.isAttachmentPathAndDocumentStorageNotBlank(attachment), is(true));
    }
}