package au.com.eatclub.challenge.loader;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Utility class for parsing time strings into {@link LocalTime} objects.
 * <p>
 * This class provides methods to parse time strings in various formats,
 * including 12-hour (AM/PM) and 24-hour formats.
 * </p>
 */
public final class TimeParser {

    // Formatter for 12-hour time format with AM/PM
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);

    // Private constructor to prevent instantiation
    private TimeParser() {}

    /**
     * Parses a raw time string into a {@link LocalTime} object.
     *
     * @param raw the raw time string to parse (e.g., "2:30pm", "14:30", "9:15")
     * @return the parsed {@link LocalTime} object
     * @throws IllegalArgumentException if the input string is null or blank
     * @throws DateTimeParseException if the input string does not match any recognized time format
     */
    public static LocalTime parse(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("timeOfDay must not be empty");
        }

        raw = raw.trim().toLowerCase();

        // 1. If it ends with am/pm → use 12-hour format
        if (raw.endsWith("am") || raw.endsWith("pm")) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("h:mma");
            return LocalTime.parse(raw.toUpperCase(), fmt);
        }

        // 2. Try "HH:mm" (24-hour format)
        if (raw.matches("\\d{2}:\\d{2}")) {   // Strict 2-digit hour
            return LocalTime.parse(raw);
        }

        // 3. Try "H:mm" (1-digit hour allowed)
        if (raw.matches("\\d{1}:\\d{2}")) {   // 1-digit hour
            return LocalTime.parse("0" + raw); // Normalize to HH:mm
        }

        // Throw exception if no format matches
        throw new DateTimeParseException("Unrecognized time format", raw, 0);
    }
}