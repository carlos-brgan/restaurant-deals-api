package au.com.eatclub.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for setting up a WebClient bean.
 * This class is annotated with @Configuration, indicating that it contains
 * Spring bean definitions.
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates and configures a WebClient bean.
     *
     * @return a new instance of WebClient.Builder built as a WebClient
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}