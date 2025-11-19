package au.com.eatclub.challenge.api;

import au.com.eatclub.challenge.loader.TimeParser;
import au.com.eatclub.challenge.service.DealService;
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
}