package au.com.eatclub.challenge.api;

import java.util.List;

/**
 * A record representing the response for a list of active deals.
 *
 * @param deals A list of {@link ActiveDealResponse} objects representing the active deals.
 */
public record ActiveDealListResponse(
        List<ActiveDealResponse> deals
) {}