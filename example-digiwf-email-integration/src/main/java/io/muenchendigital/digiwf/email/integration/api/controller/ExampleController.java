package io.muenchendigital.digiwf.email.integration.api.controller;

import io.muenchendigital.digiwf.email.integration.domain.exception.MissingInformationMailException;
import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import io.muenchendigital.digiwf.email.integration.domain.service.MailingService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.PayloadSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
//        final List<String> attachmentPaths = new ArrayList<>();
//        attachmentPaths.add("test/picture.jpg");
//        mail.setAttachmentPaths(attachmentPaths);
        return mail;
    }

}
