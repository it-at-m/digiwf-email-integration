package io.muenchendigital.digiwf.email.integration.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

@Data
@ConfigurationProperties(prefix = "digiwf.mail")
public class CustomMailProperties {

    /**
     * Sender mail address.
     */
    @NotBlank
    private String fromAddress;

}
