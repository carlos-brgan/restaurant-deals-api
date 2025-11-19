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
                    void testRestaurantStoresBasicFields() {
                        Restaurant restaurant = new Restaurant(
                                "R1",
                                "Burger Place",
                                "123 Main St",
                                "https://image.com/burger.jpg",
                                LocalTime.of(10, 0),
                                LocalTime.of(22, 0),
                                "Sydney",
                                List.of("Burgers", "Fast Food")
                        );

                        assertEquals("R1", restaurant.objectId());
                        assertEquals("Burger Place", restaurant.name());
                        assertEquals("123 Main St", restaurant.address1());
                        assertEquals("https://image.com/burger.jpg", restaurant.imageLink());
                        assertEquals(LocalTime.of(10, 0), restaurant.openTime());
                        assertEquals(LocalTime.of(22, 0), restaurant.closeTime());
                        assertEquals("Sydney", restaurant.suburb());
                        assertEquals(List.of("Burgers", "Fast Food"), restaurant.cuisines());
                    }

                    /**
                     * Ensures that a {@code Restaurant} accepts an empty cuisine list.
                     *
                     * <p>When constructed with an empty list for cuisines, the {@code cuisines()} accessor
                     * should return an empty list (not {@code null}).</p>
                     */
                    @Test
                    void testRestaurantAllowsEmptyCuisineList() {
                        Restaurant restaurant = new Restaurant(
                                "R2",
                                "Pizza House",
                                "87 King St",
                                null,
                                LocalTime.of(9, 0),
                                LocalTime.of(20, 0),
                                "Melbourne",
                                List.of()
                        );

                        assertTrue(restaurant.cuisines().isEmpty());
                    }

                }