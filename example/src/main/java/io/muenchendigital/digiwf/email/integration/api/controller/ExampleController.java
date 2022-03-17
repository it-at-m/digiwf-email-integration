package io.muenchendigital.digiwf.email.integration.api.controller;

import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import io.muenchendigital.digiwf.email.integration.domain.service.MailingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExampleController {

    private final MailingService mailingService;

    @GetMapping(value = "/test")
    public void test() {
        final Mail mail = new Mail();
//        mail.setReceivers("");
//        mail.setSubject("Test1234");
//        mail.setBody("Hallo test123");
//        mail.setReplyTo("");
//        mail.setAttachment(new Attachment());


        mailingService.sendMail(mail);
    }

}
