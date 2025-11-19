package au.com.eatclub.challenge.api;

/**
 * A record representing the response for peak time data.
 *
 * @param peakTimeStart The start time of the peak period as a string.
 * @param peakTimeEnd   The end time of the peak period as a string.
 * @param count         The count of occurrences during the peak period.
 */
public record PeakTimeResponse(
        String peakTimeStart,
        String peakTimeEnd,
        int count
) { }