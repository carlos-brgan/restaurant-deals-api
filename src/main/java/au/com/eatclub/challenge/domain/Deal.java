package au.com.eatclub.challenge.domain;

import java.time.LocalTime;

/**
 * Represents a deal offered by a business.
 * This class is implemented as a Java Record, which is a concise way to define
 * immutable data objects.
 *
 * @param objectId      The unique identifier for the deal.
 * @param discount      The discount percentage offered in the deal.
 * @param dineIn        Indicates whether the deal is available for dine-in customers.
 * @param lightning     Indicates whether the deal is a lightning deal (limited-time offer).
 * @param qtyLeft       The quantity of the deal left.
 * @param availableFrom The time from which the deal is available.
 * @param availableTo   The time until which the deal is available.
 */
public record Deal(
        String objectId,
        int discount,
        boolean dineIn,
        boolean lightning,
        int qtyLeft,
        LocalTime availableFrom,
        LocalTime availableTo
) { }