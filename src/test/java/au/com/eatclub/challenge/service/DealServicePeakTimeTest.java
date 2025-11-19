package au.com.eatclub.challenge.service;

import au.com.eatclub.challenge.domain.ChallengeData;
import au.com.eatclub.challenge.domain.Deal;
import au.com.eatclub.challenge.domain.Restaurant;
import au.com.eatclub.challenge.loader.DataLoader;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for peak time window calculation in DealService.
 *
 * These tests validate:
 *  - normal overlapping windows
 *  - exclusive end-times
 *  - wrap-around windows (spanning midnight)
 *  - always-active deals (null/null)
 */
class DealServicePeakTimeTest {

    private DealService serviceWith(Restaurant... restaurants) {
        // Mock loader to return provided restaurants
        DataLoader loader = mock(DataLoader.class);
        when(loader.load()).thenReturn(new ChallengeData(List.of(restaurants)));
        return new DealService(loader, new DealFilter());
    }

    @Test
    void test_simpleOverlap() {
        // Overlap:
        // D1: 10 → 14
        // D2:    12 → 18
        // D3:       13 → 15
        // Peak overlap 13 → 14 (3 deals)
        Restaurant r = new Restaurant(
                "R1", "Test", "X", null,
                null, null, "Nowhere",
                List.of(),
                List.of(
                        new Deal("D1", 20,false,false,1, LocalTime.of(10,0), LocalTime.of(14,0)),
                        new Deal("D2", 20,false,false,1, LocalTime.of(12,0), LocalTime.of(18,0)),
                        new Deal("D3", 20,false,false,1, LocalTime.of(13,0), LocalTime.of(15,0))
                )
        );

        DealService svc = serviceWith(r);
        PeakTimeResult p = svc.calculatePeakTime();

        assertEquals(LocalTime.of(13, 0), p.start());
        assertEquals(LocalTime.of(14, 0), p.end());
        assertEquals(3, p.count());
    }

    @Test
    void test_wrapAroundMidnight() {
        // D1: 20:00 → 02:00
        // D2: 21:00 → 23:00
        // Peak is 21:00 → 23:00 (2 deals)
        Restaurant r = new Restaurant(
                "R2", "Wrap", "Y", null,
                null, null, "Nowhere",
                List.of(),
                List.of(
                        new Deal("D1",20,false,false,1, LocalTime.of(20,0), LocalTime.of(2,0)),
                        new Deal("D2",20,false,false,1, LocalTime.of(21,0), LocalTime.of(23,0))
                )
        );

        DealService svc = serviceWith(r);
        PeakTimeResult p = svc.calculatePeakTime();

        assertEquals(LocalTime.of(21, 0), p.start());
        assertEquals(LocalTime.of(23, 0), p.end());
        assertEquals(2, p.count());
    }

    @Test
    void test_alwaysActiveDeal() {
        // D1: always active (null/null)
        // D2: 10 → 12
        // Peak is 10 → 12 (2 deals)
        Restaurant r = new Restaurant(
                "R3","A","B",null,
                null,null,"Nowhere", List.of(),
                List.of(
                        new Deal("D1",20,false,false,1, null, null),
                        new Deal("D2",20,false,false,1, LocalTime.of(10,0), LocalTime.of(12,0))
                )
        );

        DealService svc = serviceWith(r);
        PeakTimeResult p = svc.calculatePeakTime();

        assertEquals(LocalTime.of(10, 0), p.start());
        assertEquals(LocalTime.of(12, 0), p.end());
        assertEquals(2, p.count());
    }

    @Test
    void test_exclusiveEndTime() {
        // D1 ends at 15:00 exclusive → not active at 15:00
        // D2 starts at 15:00 → active at 15:00
        // Peak is 15:00 → 16:00 (1 deal)
        Restaurant r = new Restaurant(
                "R4","A","B",null,
                null,null,"Nowhere", List.of(),
                List.of(
                        new Deal("D1",20,false,false,1, LocalTime.of(10,0), LocalTime.of(15,0)),
                        new Deal("D2",20,false,false,1, LocalTime.of(15,0), LocalTime.of(16,0))
                )
        );

        DealService svc = serviceWith(r);
        PeakTimeResult p = svc.calculatePeakTime();

        assertEquals(LocalTime.of(15,0), p.start());
        assertEquals(LocalTime.of(16,0), p.end());
        assertEquals(1, p.count());
    }
}