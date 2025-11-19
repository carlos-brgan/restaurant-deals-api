package au.com.eatclub.challenge.api;

import au.com.eatclub.challenge.loader.TimeParser;
import au.com.eatclub.challenge.service.DealService;
import au.com.eatclub.challenge.service.PeakTimeResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

/**
 * REST controller for managing deals.
 * Provides endpoints to retrieve active deals based on the time of day.
 */
@RestController
@RequestMapping("/api/deals")
public class DealController {

    private final DealService service;

    /**
     * Constructs a new DealController with the specified DealService.
     *
     * @param service the service used to manage deals
     */
    public DealController(DealService service) {
        this.service = service;
    }

    /**
     * Retrieves a list of active deals for a given time of day.
     *
     * @param timeOfDay the time of day in string format (e.g., "14:30")
     * @return a response containing the list of active deals
     */
    @GetMapping
    public ActiveDealListResponse getActiveDeals(@RequestParam("timeOfDay") String timeOfDay) {
        LocalTime t = TimeParser.parse(timeOfDay);
        return new ActiveDealListResponse(service.findActiveDeals(t));
    }

    /**
     * Retrieves the peak time during which the highest number of deals are active.
     *
     * This endpoint calculates the time range (start and end) representing the peak period
     * of deal activity, along with the count of deals active during that period.
     *
     * @return a `PeakTimeResponse` containing:
     *         - the start time of the peak period as a string
     *         - the end time of the peak period as a string
     *         - the count of deals active during the peak period
     */
    @GetMapping("/peak-time")
    public PeakTimeResponse getPeakTime() {
        PeakTimeResult result = service.calculatePeakTime();
        return new PeakTimeResponse(
                result.start().toString(),
                result.end().toString(),
                result.count()
        );
    }
}