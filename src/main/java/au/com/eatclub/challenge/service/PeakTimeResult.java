package au.com.eatclub.challenge.service;

import java.time.LocalTime;

/**
 * Represents the result of a peak time analysis.
 * This record encapsulates the start and end times of a peak period,
 * along with the count of occurrences during that period.
 *
 * @param start The start time of the peak period.
 * @param end The end time of the peak period.
 * @param count The number of occurrences during the peak period.
 */
public record PeakTimeResult(
        LocalTime start,
        LocalTime end,
        int count
) { }