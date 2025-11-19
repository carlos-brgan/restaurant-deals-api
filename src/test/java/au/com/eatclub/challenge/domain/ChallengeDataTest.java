/**
 * Unit tests for the domain classes used in the challenge.
 * <p>
 * This test class verifies that a ChallengeData instance correctly stores
 * Restaurant objects and that those restaurants contain their associated Deal objects.
 * <p>
 * The test constructs a Deal and a Restaurant (containing that deal), wraps the restaurant
 * in a ChallengeData instance, and asserts the structure and identifiers are preserved.
 */
package au.com.eatclub.challenge.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeDataTest {

    /**
     * Verifies that ChallengeData stores the provided restaurants and their deals.
     * Steps:
     * 1. Create a Deal with a known objectId ("D1").
     * 2. Create a Restaurant with a known objectId ("R1") that includes the Deal.
     * 3. Create a ChallengeData instance containing the Restaurant.
     * 4. Assert that ChallengeData contains exactly one restaurant with the expected id
     *    and that the restaurant contains the expected deal with the expected id.
     */
    @Test
    void testChallengeDataStoresRestaurants() {

        Deal d = new Deal(
                "D1",
                30,
                true,
                false,
                10,
                LocalTime.of(12, 0),
                LocalTime.of(16, 0)
        );

        Restaurant r = new Restaurant(
                "R1",
                "Test Restaurant",
                "123 Street",
                null,                        // imageLink
                LocalTime.of(9, 0),          // openTime
                LocalTime.of(17, 0),         // closeTime
                "Melbourne",                 // suburb
                List.of("Thai"),             // cuisines
                List.of(d)                   // deals
        );

        ChallengeData data = new ChallengeData(
                List.of(r)
        );

        // Verify there is exactly one restaurant stored
        assertEquals(1, data.restaurants().size());
        // Verify the stored restaurant has the expected object id
        assertEquals("R1", data.restaurants().getFirst().objectId());
        // Verify the restaurant contains exactly one deal
        assertEquals(1, data.restaurants().getFirst().deals().size());
        // Verify the stored deal has the expected object id
        assertEquals("D1", data.restaurants().getFirst().deals().getFirst().objectId());
    }
}