package io.muenchendigital.digiwf.email.integration.configuration;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.RoutingCallback;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MailConfiguration {

    public static final String TYPE_HEADER_SEND_MAIL_FROM_EVENT_BUS = "sendMailFromEventBus";

    public JavaMailSender getJavaMailSender(
            final String host,
            final Integer port,
            final String protocol,
            final String username,
            final String password,
            final Map<String, String> properties
    ) throws MessagingException {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        final Properties props = mailSender.getJavaMailProperties();
        props.putAll(properties);
        mailSender.setJavaMailProperties(props);
        mailSender.testConnection();
        return mailSender;
    }

    /**
     * Override the custom router of the digiwf-spring-cloudstream-utils. We only have one type we need to map.
     *
     * @return the custom router
     */
    public MessageRoutingCallback mailRouter() {
        final Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put(TYPE_HEADER_SEND_MAIL_FROM_EVENT_BUS, TYPE_HEADER_SEND_MAIL_FROM_EVENT_BUS);
        return new RoutingCallback(typeMappings);
    }

}
