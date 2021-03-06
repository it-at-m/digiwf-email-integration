package io.muenchendigital.digiwf.email.integration.api.controller;

import io.muenchendigital.digiwf.email.integration.domain.exception.MissingInformationMailException;
import io.muenchendigital.digiwf.email.integration.domain.model.Attachment;
import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import io.muenchendigital.digiwf.email.integration.domain.service.MailingService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.PayloadSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final MailingService mailingService;
    private final PayloadSenderService genericPayloadSender;

    @Value("${io.muenchendigital.email.test.receiver}")
    private String receiver;

    @GetMapping(value = "/testSendMail")
    public void testSendMail() {
        try {
            mailingService.sendMail(getMail());
        } catch (final MissingInformationMailException e) {
            log.error(e.toString());
        }
    }

    /**
     * Note: for this to work, you have to configure both
     * spring.cloud.stream.bindings.sendMessage-out-0.destination and
     * spring.cloud.stream.bindings.functionRouter-in-0.destination
     * to the same topic.
     */
    @GetMapping(value = "/testEventBus")
    public void testEventBus() {
        genericPayloadSender.sendPayload(getMail(), "sendMailFromEventBus");
    }

    private Mail getMail() {
        final Mail mail = new Mail();
        mail.setReceivers(receiver);
        mail.setSubject("Test1234");
        mail.setBody("Hallo test123");
        mail.setReplyTo("");

//        Uncomment to set Attachments
//        mail.setAttachments(getAttachment());

        return mail;
    }

    private List<Attachment> getAttachment() {
        final Attachment attachment1 = new Attachment();
        attachment1.setDocumentStorageUrl("http://localhost:8086");
        attachment1.setAttachmentPath("test/picture.jpg");

        final Attachment attachment2 = new Attachment();
        attachment2.setDocumentStorageUrl("http://localhost:8086");
        attachment2.setAttachmentPath("test/picture.jpg");
        attachment2.setFileName("image.jpg");

        final List<Attachment> attachmentList = new ArrayList<>();
        attachmentList.add(attachment1);
        attachmentList.add(attachment2);

        return attachmentList;

    }

}
