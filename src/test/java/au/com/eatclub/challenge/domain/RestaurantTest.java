package au.com.eatclub.challenge.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Restaurant}.
 *
 * <p>Tests cover basic field storage and behavior when provided with an empty cuisine list.</p>
 */
class RestaurantTest {

    /**
     * Tests that the {@code Restaurant} constructor correctly stores the provided basic fields.
     *
     * <p>This test constructs a {@code Restaurant} with sample values for id, name, address,
     * image link, opening/closing times, suburb and cuisines, then asserts that each accessor
     * returns the expected value.</p>
     */
    @Test
    void testRestaurantStoresAllFieldsIncludingDeals() {
        Deal deal = new Deal(
                "D1",
                20,
                true,
                false,
                5,
                LocalTime.of(12, 0),
                LocalTime.of(15, 0)
        );

        Restaurant restaurant = new Restaurant(
                "R1",
                "Burger Place",
                "123 Main St",
                "https://image.com/burger.jpg",
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                "Sydney",
                List.of("Burgers", "Fast Food"),
                List.of(deal)
        );

        assertEquals("R1", restaurant.objectId());
        assertEquals("Burger Place", restaurant.name());
        assertEquals("123 Main St", restaurant.address1());
        assertEquals("https://image.com/burger.jpg", restaurant.imageLink());
        assertEquals(LocalTime.of(10, 0), restaurant.openTime());
        assertEquals(LocalTime.of(22, 0), restaurant.closeTime());
        assertEquals("Sydney", restaurant.suburb());
        assertEquals(List.of("Burgers", "Fast Food"), restaurant.cuisines());
        assertEquals(1, restaurant.deals().size());
        assertEquals("D1", restaurant.deals().getFirst().objectId());
    }

    /**
     * Verifies that a restaurant can be created with an empty deals list.
     * The restaurant should accept an empty list for {@code deals}
     * and expose it unchanged through the {@link Restaurant#deals()} accessor.
     */
    @Test
    void testRestaurantAcceptsEmptyDealsList() {
        Restaurant restaurant = new Restaurant(
                "R2",
                "Pizza House",
                "87 King St",
                null,
                LocalTime.of(9, 0),
                LocalTime.of(20, 0),
                "Melbourne",
                List.of("Pizza"),
                List.of()   // no deals
        );

        assertTrue(restaurant.deals().isEmpty());
    }
}