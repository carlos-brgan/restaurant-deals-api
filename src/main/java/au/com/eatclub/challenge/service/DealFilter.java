package au.com.eatclub.challenge.service;

        import au.com.eatclub.challenge.domain.Deal;
        import au.com.eatclub.challenge.domain.Restaurant;
        import org.springframework.stereotype.Component;

        import java.time.LocalTime;

        /**
         * The `DealFilter` class provides functionality to determine if a deal is active
         * at a given time. It considers the deal's availability window and handles both
         * normal and wrap-around time windows.
         */
        @Component
        public class DealFilter {

            /**
             * Determines if a given deal is active at a specified time.
             *
             * @param d    The `Deal` object containing the availability window.
             * @param time The `LocalTime` to check against the deal's availability.
             * @return `true` if the deal is active at the given time, `false` otherwise.
             *
             * The method handles two types of availability windows:
             * 1. Normal window: The start time is before or equal to the end time
             *    (e.g., 10:00 → 14:00).
             * 2. Wrap-around window: The start time is after the end time
             *    (e.g., 20:00 → 02:00, spanning midnight).
             *
             * If the deal has no specific availability window (both `availableFrom` and
             * `availableTo` are `null`), it is treated as always available.
             */
            public boolean isActive(Deal d, LocalTime time) {
                LocalTime start = d.availableFrom();
                LocalTime end = d.availableTo();

                // If deal has no specific window, treat it as available but
                // restaurant open logic will filter out if needed.
                if (start == null || end == null) {
                    return true;
                }

                // NORMAL WINDOW: 10:00 → 14:00  (end is EXCLUSIVE)
                if (!start.isAfter(end)) {
                    return !time.isBefore(start) && time.isBefore(end);
                }

                // WRAP-AROUND: 20:00 → 02:00
                // Window is [20:00 → 24:00) and [00:00 → 02:00)
                return time.isAfter(start) || time.isBefore(end);
            }

        }