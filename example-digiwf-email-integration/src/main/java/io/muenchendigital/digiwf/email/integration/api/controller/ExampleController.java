package io.muenchendigital.digiwf.email.integration.api.controller;

import io.muenchendigital.digiwf.email.integration.domain.model.Attachment;
import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import io.muenchendigital.digiwf.email.integration.domain.service.MailingService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.PayloadSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
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
            this.mailingService.sendMail(this.getMail());
        } catch (final ConstraintViolationException e) {
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
        this.genericPayloadSender.sendPayload(this.getMail(), "sendMailFromEventBus");
    }

    private Mail getMail() {
        final Mail mail = new Mail();
        mail.setReceivers(this.receiver);
        mail.setSubject("Test1234");
        mail.setBody("Hallo test123");
        mail.setReplyTo("");

//        Uncomment to set Attachments
//        mail.setAttachments(getAttachment());

        return mail;
    }

    private List<Attachment> getAttachment() {
        final Attachment attachment1 = new Attachment();
        attachment1.setUrl("http://localhost:8086");
        attachment1.setPath("test/picture.jpg");
        attachment1.setAction("GET");

        final Attachment attachment2 = new Attachment();
        attachment2.setUrl("http://localhost:8086");
        attachment2.setPath("test/picture.jpg");
        attachment2.setAction("GET");

        final List<Attachment> attachmentList = new ArrayList<>();
        attachmentList.add(attachment1);
        attachmentList.add(attachment2);

        return attachmentList;

    }

}
