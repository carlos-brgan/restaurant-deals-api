package au.com.eatclub.challenge.config;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.reactive.function.client.WebClient;

    /**
     * Configuration class for setting up WebClient beans.
     * This class provides a centralized configuration for creating and customizing
     * WebClient instances, which are used for making non-blocking HTTP requests.
     */
    @Configuration
    public class WebClientConfig {

        /**
         * Creates a WebClient bean using the provided WebClient.Builder.
         *
         * @param builder the WebClient.Builder used to configure and build the WebClient instance
         * @return a fully configured WebClient instance
         */
        @Bean
        public WebClient webClient(WebClient.Builder builder) {
            return builder.build();
        }

        /**
         * Creates a WebClient.Builder bean.
         *
         * @return a new instance of WebClient.Builder
         */
        @Bean
        public WebClient.Builder webClientBuilder() {
            return WebClient.builder();
        }
    }