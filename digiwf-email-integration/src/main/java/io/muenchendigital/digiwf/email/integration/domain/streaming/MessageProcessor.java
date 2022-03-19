package io.muenchendigital.digiwf.email.integration.domain.streaming;

import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import io.muenchendigital.digiwf.email.integration.domain.service.MailingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProcessor {

    private final MailingService mailingService;

    @Bean
    public Consumer<Message<Object>> sendMailFromEventBus() {
        return message -> {
            if (message.getPayload() instanceof Mail) {
                final Mail mail = (Mail) message.getPayload();
                try {
                    mailingService.sendMail(mail);
                } catch (final MailException e) {
                    log.error("Mail could not be sent: {}", e.getMessage());
                }
            }
        };
    }

}
