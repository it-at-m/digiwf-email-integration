package io.muenchendigital.digiwf.email.integration.domain.streaming;

import io.muenchendigital.digiwf.email.integration.domain.exception.MissingInformationMailException;
import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import io.muenchendigital.digiwf.email.integration.domain.service.MailingService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.RoutingCallback;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.CorrelateMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProcessor {

    private final MailingService mailingService;
    private final CorrelateMessageService correlateMessageService;
    private static final String MAIL_SENT_STATUS = "mailSentStatus";

    /**
     * Override the custom router of the digiwf-spring-cloudstream-utils. We only have one type we need to map.
     * @return the custom router
     */
    @Bean
    public MessageRoutingCallback mailRouter() {
        final Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put("sendMailFromEventBus", "sendMailFromEventBus");
        return new RoutingCallback(typeMappings);
    }

    /**
     * All messages from the route "sendMailFromEventBus" go here.
     * @return the consumer
     */
    @Bean
    public Consumer<Message<Mail>> sendMailFromEventBus() {
        return message -> {
            log.info("Processing new mail from eventbus");
            final Mail mail = message.getPayload();
            log.debug("Mail: {}", mail);
            try {
                mailingService.sendMail(mail);
                emitResponse(message.getHeaders(), true);
            } catch (final MissingInformationMailException | MailException e) {
                log.error("Mail could not be sent: {}", e.getMessage());
                emitResponse(message.getHeaders(), false);
            }
        };
    }

    /**
     * Function to emit a reponse using the correlateMessageService of digiwf-spring-cloudstream-utils
     * @param messageHeaders The MessageHeaders of the incoming message you want to correlate your answer to
     * @param status true when the e-mail has been sent, false when an error occured
     */
    public void emitResponse(final MessageHeaders messageHeaders, final boolean status) {
        final Map<String, Object> correlatePayload = new HashMap<>();
        correlatePayload.put(MAIL_SENT_STATUS, status);
        correlateMessageService.sendCorrelateMessage(messageHeaders, correlatePayload);
    }
}
