package ar.edu.utn.frc.tup.lciii.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Configuration class for creating and configuring a RestTemplate bean.
 * This class sets the connection and read timeouts for the RestTemplate.
 */
@Configuration
public class RestTemplateConfig {
    /**
     * Timeout duration in milliseconds.
     */
    private static final int TIMEOUT_MILLISECONDS = 100000;

    /**
     * Creates and configures a RestTemplate bean.
     *
     * @param restTemplateBuilder A builder provided by Spring Boot to create and
     *                            configure RestTemplate instances.
     * @return A fully configured RestTemplate instance.
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(TIMEOUT_MILLISECONDS))
                .setReadTimeout(Duration.ofMillis(TIMEOUT_MILLISECONDS))
                .build();
    }
}
