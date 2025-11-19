package au.com.eatclub.challenge.loader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Data Transfer Object (DTO) representing challenge data.
 * This class is designed to hold a list of restaurants and is used
 * for transferring data between different layers of the application.
 *
 * The `@JsonIgnoreProperties` annotation ensures that any unknown
 * properties in the JSON input are ignored during deserialization.
 *
 * @param restaurants A list of `RestaurantDTO` objects representing the restaurants.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ChallengeDataDTO(
        java.util.List<RestaurantDTO> restaurants
) {}