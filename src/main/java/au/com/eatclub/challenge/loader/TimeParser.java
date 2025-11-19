package au.com.eatclub.challenge.loader;

    import java.time.LocalTime;
    import java.time.format.DateTimeFormatter;
    import java.util.Locale;

    /**
     * A utility class for parsing time strings into {@link LocalTime} objects.
     * <p>
     * This class provides a method to parse time strings in the format "h:mma"
     * (e.g., "3:00PM") into {@link LocalTime} instances. The parsing is case-insensitive
     * and ignores spaces in the input string.
     * </p>
     */
    public final class TimeParser {

        /**
         * A {@link DateTimeFormatter} for parsing time strings in the format "h:mma".
         * <p>
         * Example formats:
         * <ul>
         *     <li>"3:00PM"</li>
         *     <li>"12:30AM"</li>
         * </ul>
         * </p>
         */
        private static final DateTimeFormatter FMT =
                DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);

        /**
         * Private constructor to prevent instantiation of this utility class.
         */
        private TimeParser() {}

        /**
         * Parses a raw time string into a {@link LocalTime} object.
         *
         * @param raw the raw time string to parse, expected in the format "h:mma".
         *            The input is case-insensitive and may contain spaces.
         *            For example, "3:00 pm" or "3:00PM" are both valid.
         * @return a {@link LocalTime} object representing the parsed time,
         *         or {@code null} if the input is {@code null} or blank.
         * @throws java.time.format.DateTimeParseException if the input string
         *         cannot be parsed into a valid time.
         */
        public static LocalTime parse(String raw) {
            if (raw == null || raw.isBlank()) return null;

            // Normalize the input by removing spaces and converting to uppercase
            raw = raw.replace(" ", "").toUpperCase();  // "3:00pm" → "3:00PM"
            return LocalTime.parse(raw, FMT);
        }
    }