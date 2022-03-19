package io.muenchendigital.digiwf.email.integration.api.controller;

import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import io.muenchendigital.digiwf.email.integration.domain.service.MailingService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.PayloadSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExampleController {

    private final MailingService mailingService;
    private final PayloadSender genericPayloadSender;

    @Value("${io.muenchendigital.email.test.receiver}")
    private String receiver;

    @GetMapping(value = "/testSendMail")
    public void testSendMail() {
        final Mail mail = new Mail();
        mail.setReceivers(receiver);
        mail.setSubject("Test1234");
        mail.setBody("Hallo test123");
        mail.setReplyTo("");
//        mail.setAttachment(new Attachment());


        mailingService.sendMail(mail);
    }

    @GetMapping(value = "/testEventBus")
    public void testEventBus() {
        final Mail mail = new Mail();
        mail.setReceivers(receiver);
        mail.setSubject("Test1234");
        mail.setBody("Hallo test123");
        mail.setReplyTo("");
//        mail.setAttachment(new Attachment());


        genericPayloadSender.sendPayload(mail, "sendMailFromEventBus");
        mailingService.sendMail(mail);
    }

}
