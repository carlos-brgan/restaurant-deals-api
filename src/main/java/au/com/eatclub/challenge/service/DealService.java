package au.com.eatclub.challenge.service;

import au.com.eatclub.challenge.api.ActiveDealResponse;
import au.com.eatclub.challenge.domain.ChallengeData;
import au.com.eatclub.challenge.domain.Deal;
import au.com.eatclub.challenge.domain.Restaurant;
import au.com.eatclub.challenge.loader.DataLoader;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Service class responsible for managing and retrieving active deals.
 */
@Service
public class DealService {

    private record TimeEvent(LocalTime time, int delta) {}

    private final DataLoader loader;
    private final DealFilter filter;

    /**
     * Constructs a new DealService with the specified DataLoader and DealFilter.
     *
     * @param loader the DataLoader used to load challenge data
     * @param filter the DealFilter used to determine active deals
     */
    public DealService(DataLoader loader, DealFilter filter) {
        this.loader = loader;
        this.filter = filter;
    }

    /**
     * Finds and returns a list of active deals for the given time.
     *
     * @param time the time to check for active deals
     * @return a list of ActiveDealResponse objects representing the active deals
     */
    public List<ActiveDealResponse> findActiveDeals(LocalTime time) {
        ChallengeData data = loader.load();

        return data.restaurants().stream()
                .filter(r -> isRestaurantOpen(r, time))
                .flatMap(r -> r.deals().stream()
                        .filter(d -> filter.isActive(d, time))
                        .map(d -> ActiveDealResponse.from(r, d)))
                .toList();
    }

    /**
     * Calculates the peak time during which the maximum number of deals are active.
     * This method analyzes the availability of deals across all restaurants and determines
     * the time range with the highest number of active deals.
     *
     * @return a PeakTimeResult object containing the start time, end time, and the maximum number of active deals
     */
    public PeakTimeResult calculatePeakTime() {
        // Load the challenge data containing restaurants and their deals
        ChallengeData data = loader.load();

        // 1. Build event list
        // Create a list of time events representing the start and end times of deals
        List<TimeEvent> events = new ArrayList<>();

        // Iterate through all restaurants and their deals
        for (Restaurant r : data.restaurants()) {
            for (Deal d : r.deals()) {
                LocalTime start = d.availableFrom(); // Start time of the deal
                LocalTime end = d.availableTo();     // End time of the deal

                // Always-active deals → count entire 24 hours
                if (start == null || end == null) {
                    start = LocalTime.MIN; // Start of the day (00:00)
                    end = LocalTime.MAX;   // End of the day (23:59:59.999)
                }

                // Handle wrap-around deals (e.g., 18:00 → 02:00)
                if (start.isAfter(end)) {
                    events.add(new TimeEvent(start, +1));          // Deal starts
                    events.add(new TimeEvent(LocalTime.MAX, -1));  // Midnight boundary
                    events.add(new TimeEvent(LocalTime.MIN, +1));  // Start of the next day
                    events.add(new TimeEvent(end, -1));            // Deal ends
                } else {
                    // Normal case: deal starts and ends on the same day
                    events.add(new TimeEvent(start, +1)); // Deal starts
                    events.add(new TimeEvent(end, -1));   // Deal ends
                }
            }
        }

        // 2. Sort
        // Sort the events by time to prepare for the sweep line algorithm
        events.sort(Comparator.comparing(TimeEvent::time));

        // 3. Sweep
        // Use a sweep line algorithm to calculate the peak time
        int current = 0; // Current number of active deals
        int max = 0;     // Maximum number of active deals

        LocalTime bestStart = null; // Start time of the peak period
        LocalTime bestEnd = null;   // End time of the peak period

        // Iterate through the sorted events
        for (int i = 0; i < events.size() - 1; i++) {
            TimeEvent e = events.get(i);
            current += e.delta; // Update the current number of active deals

            // Update the peak time if the current number of deals is greater than or equal to the max
            if (current >= max) {
                max = current;
                bestStart = e.time();               // Start of the peak period
                bestEnd = events.get(i + 1).time(); // End of the peak period (next boundary)
            }
        }

        // Return the result containing the peak time range and the maximum number of active deals
        return new PeakTimeResult(bestStart, bestEnd, max);
    }



    /**
     * Checks whether a restaurant is open at the given time.
     * Supports both normal (e.g., 09:00–17:00) and wrap-around (e.g., 15:00–01:00) opening hours.
     *
     * @param r    the restaurant to check
     * @param time the time to check
     * @return true if the restaurant is open at the given time, false otherwise
     */
    private boolean isRestaurantOpen(Restaurant r, LocalTime time) {
        LocalTime open = r.openTime();
        LocalTime close = r.closeTime();

        // Normal case: opening and closing times are on the same day (e.g., 09:00 → 15:00)
        if (!open.isAfter(close)) {
            return !time.isBefore(open) && time.isBefore(close);  // closeTime is exclusive
        }

        // Wrap-around case: opening and closing times span midnight (e.g., 18:00 → 03:00)
        // Two intervals:
        //   [open → 23:59...]
        //   [00:00 → close)
        return time.isAfter(open) || time.isBefore(close);  // close is exclusive
    }
}