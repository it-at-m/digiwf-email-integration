package io.muenchendigital.digiwf.email.integration.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "digiwf.mail")
public class CustomMailProperties {

    private String fromAddress = "noreply@noreply.com";

}
