package io.muenchendigital.digiwf.email.integration.api.controller;

import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import io.muenchendigital.digiwf.email.integration.domain.service.MailingService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.PayloadSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExampleController {

    private final MailingService mailingService;
    private final PayloadSenderService genericPayloadSender;

    @Value("${io.muenchendigital.email.test.receiver}")
    private String receiver;

    @GetMapping(value = "/testSendMail")
    public void testSendMail() {
        mailingService.sendMail(getMail());
    }

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
        final List<String> attachmentPaths = new ArrayList<>();
        attachmentPaths.add("test/picture.jpg");
        mail.setAttachmentPaths(attachmentPaths);
        return mail;
    }

}
