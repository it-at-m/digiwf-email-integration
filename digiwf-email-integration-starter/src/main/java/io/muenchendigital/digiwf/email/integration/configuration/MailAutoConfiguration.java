package io.muenchendigital.digiwf.email.integration.configuration;

import io.muenchendigital.digiwf.email.integration.domain.configuration.MailConfiguration;
import io.muenchendigital.digiwf.email.integration.domain.service.MailingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = "io.muenchendigital.digiwf.email.integration")
@EnableConfigurationProperties({MailProperties.class, CustomMailProperties.class})
public class MailAutoConfiguration {

    private final MailProperties mailProperties;
    private final CustomMailProperties customMailProperties;
    private final MailConfiguration mailConfiguration;

    /**
     * Configures the {@link JavaMailSender}
     *
     * @return configured JavaMailSender
     */
    @Bean
    public JavaMailSender getJavaMailSender() throws MessagingException {
        return mailConfiguration.getJavaMailSender(
                this.mailProperties.getHost(),
                this.mailProperties.getPort(),
                this.mailProperties.getProtocol(),
                this.mailProperties.getUsername(),
                this.mailProperties.getPassword(),
                this.mailProperties.getProperties()
        );
    }

    @Bean
    public MailingService getMailingService(final JavaMailSender javaMailSender) {
        return new MailingService(javaMailSender, customMailProperties.getFromAdress());
    }
}
