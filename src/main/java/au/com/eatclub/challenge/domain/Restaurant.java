package au.com.eatclub.challenge.domain;

import java.time.LocalTime;
import java.util.List;

/**
 * Represents a restaurant with basic metadata used throughout the application.
 *
 * <p>This is an immutable data carrier implemented as a Java {@code record}.</p>
 *
 * @param objectId   unique identifier for the restaurant (for example, a database id or external service id)
 * @param name       human-readable name of the restaurant
 * @param address1   primary street address line for the restaurant
 * @param imageLink  URL or path to an image representing the restaurant (may be {@code null} or empty if unavailable)
 * @param openTime   daily opening time as a {@link LocalTime}
 * @param closeTime  daily closing time as a {@link LocalTime}
 * @param suburb     suburb or locality where the restaurant is located
 * @param cuisines   list of cuisine types offered by the restaurant; may be empty but should not be {@code null}
 */
public record Restaurant(
        String objectId,
        String name,
        String address1,
        String imageLink,
        LocalTime openTime,
        LocalTime closeTime,
        String suburb,
        List<String> cuisines
) {
}