package au.com.eatclub.challenge.loader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A Data Transfer Object (DTO) representing a restaurant.
 * This class is used to transfer restaurant data between different layers of the application.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>@JsonIgnoreProperties(ignoreUnknown = true): Ignores unknown properties during JSON deserialization.</li>
 *   <li>@JsonProperty: Maps JSON properties to Java fields.</li>
 * </ul>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>objectId: Unique identifier for the restaurant.</li>
 *   <li>name: Name of the restaurant.</li>
 *   <li>address1: Address line 1 of the restaurant.</li>
 *   <li>suburb: Suburb where the restaurant is located.</li>
 *   <li>imageLink: URL of the restaurant's image.</li>
 *   <li>openRaw: Raw opening time of the restaurant (mapped from JSON "open").</li>
 *   <li>closeRaw: Raw closing time of the restaurant (mapped from JSON "close").</li>
 *   <li>cuisines: List of cuisines offered by the restaurant.</li>
 *   <li>deals: List of deals available at the restaurant.</li>
 * </ul>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RestaurantDTO(

        /**
         * Unique identifier for the restaurant.
         */
        String objectId,

        /**
         * Name of the restaurant.
         */
        String name,

        /**
         * Address line 1 of the restaurant.
         */
        String address1,

        /**
         * Suburb where the restaurant is located.
         */
        String suburb,

        /**
         * URL of the restaurant's image.
         */
        String imageLink,

        /**
         * Raw opening time of the restaurant.
         * Mapped from the JSON property "open".
         */
        @JsonProperty("open") String openRaw,

        /**
         * Raw closing time of the restaurant.
         * Mapped from the JSON property "close".
         */
        @JsonProperty("close") String closeRaw,

        /**
         * List of cuisines offered by the restaurant.
         */
        List<String> cuisines,

        /**
         * List of deals available at the restaurant.
         */
        List<DealDTO> deals
) {}