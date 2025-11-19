package au.com.eatclub.challenge.service;

import au.com.eatclub.challenge.api.ActiveDealResponse;
import au.com.eatclub.challenge.domain.ChallengeData;
import au.com.eatclub.challenge.domain.Restaurant;
import au.com.eatclub.challenge.loader.DataLoader;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

/**
 * Service class responsible for managing and retrieving active deals.
 */
@Service
public class DealService {

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