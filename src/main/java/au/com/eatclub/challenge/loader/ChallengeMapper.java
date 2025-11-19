package au.com.eatclub.challenge.loader;

import au.com.eatclub.challenge.domain.Deal;
import au.com.eatclub.challenge.domain.Restaurant;

import java.time.LocalTime;
import java.util.List;

/**
 * Utility class for mapping DTOs (Data Transfer Objects) to domain objects.
 */
public class ChallengeMapper {

    /**
     * Converts a list of RestaurantDTO objects to a list of Restaurant objects.
     *
     * @param dtos the list of RestaurantDTO objects to convert
     * @return a list of Restaurant objects
     */
    public static List<Restaurant> toRestaurants(List<RestaurantDTO> dtos) {
        return dtos.stream()
                .map(ChallengeMapper::toRestaurant)
                .toList();
    }

    /**
     * Converts a RestaurantDTO object to a Restaurant object.
     *
     * @param dto the RestaurantDTO object to convert
     * @return the corresponding Restaurant object
     */
    public static Restaurant toRestaurant(RestaurantDTO dto) {

        // Parse opening and closing times
        LocalTime open  = TimeParser.parse(dto.openRaw());
        LocalTime close = TimeParser.parse(dto.closeRaw());

        // Map deals from DTO to domain objects
        List<Deal> deals = dto.deals().stream()
                .map(d -> toDeal(d, open, close))
                .toList();

        // Create and return a Restaurant object
        return new Restaurant(
                dto.objectId(),
                dto.name(),
                dto.address1(),
                dto.imageLink(),
                open,
                close,
                dto.suburb(),
                dto.cuisines(),
                deals
        );
    }

    /**
     * Converts a DealDTO object to a Deal object.
     *
     * @param dto the DealDTO object to convert
     * @param restaurantOpen the opening time of the restaurant
     * @param restaurantClose the closing time of the restaurant
     * @return the corresponding Deal object
     */
    public static Deal toDeal(DealDTO dto, LocalTime restaurantOpen, LocalTime restaurantClose) {

        // Resolve the start time of the deal
        LocalTime start = resolveTime(
                dto.startRaw(),
                dto.openRaw(),
                restaurantOpen
        );

        // Resolve the end time of the deal
        LocalTime end = resolveTime(
                dto.endRaw(),
                dto.closeRaw(),
                restaurantClose
        );

        // Create and return a Deal object
        return new Deal(
                dto.objectId(),
                safeInt(dto.discount()),
                safeBool(dto.dineIn()),
                safeBool(dto.lightning()),
                safeInt(dto.qtyLeft()),
                start,
                end
        );
    }

    /**
     * Safely parses a string to an integer. Returns 0 if the string is null or blank.
     *
     * @param s the string to parse
     * @return the parsed integer, or 0 if the string is null or blank
     */
    private static int safeInt(String s) {
        return (s == null || s.isBlank()) ? 0 : Integer.parseInt(s);
    }

    /**
     * Safely parses a string to a boolean. Returns false if the string is null.
     *
     * @param s the string to parse
     * @return the parsed boolean, or false if the string is null
     */
    private static boolean safeBool(String s) {
        return s != null && Boolean.parseBoolean(s);
    }

    /**
     * Resolves a time value by attempting to parse the primary raw time,
     * then the secondary raw time, and finally falling back to a default value.
     *
     * @param primaryRaw the primary raw time string
     * @param secondaryRaw the secondary raw time string
     * @param fallback the fallback LocalTime value
     * @return the resolved LocalTime value
     */
    private static LocalTime resolveTime(String primaryRaw, String secondaryRaw, LocalTime fallback) {
        // Try primary (e.g. start / end)
        LocalTime t = parseIfPresent(primaryRaw);
        if (t != null) {
            return t;
        }

        // Try secondary (e.g. open / close)
        t = parseIfPresent(secondaryRaw);
        if (t != null) {
            return t;
        }

        // Fallback to restaurant open/close
        return fallback;
    }

    /**
     * Parses a raw time string into a LocalTime object if the string is not null or blank.
     *
     * @param raw the raw time string to parse
     * @return the parsed LocalTime object, or null if the string is null or blank
     */
    private static LocalTime parseIfPresent(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        return TimeParser.parse(raw); // your existing parser
    }
}