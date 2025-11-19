package au.com.eatclub.challenge.domain;

import java.time.LocalTime;

/**
 * Immutable data carrier representing a promotional Deal.
 *
 * <p>This record models a single deal offered by a merchant. Instances are immutable
 * and intended to be a simple transport object for the properties that describe
 * the deal's identity, pricing, availability and remaining quantity.</p>
 *
 * <p>Notes:
 * <ul>
 *   <li>`objectId` is expected to be a non-null unique identifier for the deal.</li>
 *   <li>`discount` is typically a percentage (0-100) representing the reduction applied.</li>
 *   <li>`dineIn` indicates whether the deal applies to dine-in orders.</li>
 *   <li>`lightning` indicates if the deal is a time-sensitive "lightning" promotion.</li>
 *   <li>`qtyLeft` is the remaining count available for purchase (should be >= 0).</li>
 *   <li>`availableFrom` / `availableTo` define the daily LocalTime window when the deal is valid;
 *       either may be null to indicate no bound in that direction.</li>
 * </ul>
 * </p>
 *
 * @param objectId      unique identifier for the deal (non-null)
 * @param discount      discount amount, typically a percentage (e.g. 20 for 20%)
 * @param dineIn        true if the deal applies to dine-in orders
 * @param lightning     true if the deal is a lightning/time-limited promotion
 * @param qtyLeft       quantity remaining for the deal (non-negative)
 * @param availableFrom time of day (inclusive) when the deal becomes available; may be null
 * @param availableTo   time of day (exclusive) when the deal expires; may be null
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