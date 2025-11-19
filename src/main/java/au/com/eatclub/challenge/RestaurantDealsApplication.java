package au.com.eatclub.challenge;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The `RestaurantDealsApplication` class serves as the entry point for the
 * Restaurant Deals application. It is a Spring Boot application that
 * initializes and runs the application context.
 */
@SpringBootApplication
public class RestaurantDealsApplication {

    /**
     * The main method is the entry point of the Java application. It uses
     * Spring Boot's `SpringApplication.run` method to launch the application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(RestaurantDealsApplication.class, args);
    }
}