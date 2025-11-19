package au.com.eatclub.challenge.loader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Data Transfer Object (DTO) representing a deal.
 * This class is designed to map JSON data to a Java object using Jackson annotations.
 *
 * <p>Fields include various deal attributes such as object ID, discount, and time-related properties.
 * The time-related fields are annotated with {@link JsonProperty} to map specific JSON keys.</p>
 *
 * <p>Unknown JSON properties are ignored during deserialization due to the {@link JsonIgnoreProperties} annotation.</p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DealDTO(

        /**
         * The unique identifier for the deal.
         */
        String objectId,

        /**
         * The discount associated with the deal, represented as a string.
         * This will be converted to the appropriate type later in the mapper.
         */
        String discount,

        /**
         * Indicates whether the deal is for dine-in, represented as a string.
         */
        String dineIn,

        /**
         * Indicates whether the deal is a lightning deal, represented as a string.
         */
        String lightning,

        /**
         * The quantity left for the deal, represented as a string.
         */
        String qtyLeft,

        /**
         * The raw start time of the deal, mapped from the JSON property "start".
         */
        @JsonProperty("start") String startRaw,

        /**
         * The raw end time of the deal, mapped from the JSON property "end".
         */
        @JsonProperty("end") String endRaw,

        /**
         * The raw opening time of the deal, mapped from the JSON property "open".
         */
        @JsonProperty("open") String openRaw,

        /**
         * The raw closing time of the deal, mapped from the JSON property "close".
         */
        @JsonProperty("close") String closeRaw
) {
}