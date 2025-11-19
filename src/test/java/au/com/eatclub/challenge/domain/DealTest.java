package au.com.eatclub.challenge.domain;

    import org.junit.jupiter.api.Test;

    import java.time.LocalTime;

    import static org.junit.jupiter.api.Assertions.*;

    /**
     * Unit tests for the {@link Deal} domain model.
     *
     * <p>These tests verify that a {@code Deal} instance correctly stores its basic fields
     * (identifier, discount, dine-in flag, lightning flag, quantity left and availability window)
     * and that it correctly allows null time windows.</p>
     */
    class DealTest {

        /**
         * Verifies that a Deal constructed with non-null basic fields returns the expected values.
         *
         * <p>Constructs a {@code Deal} with:
         * <ul>
         *   <li>objectId "D1"</li>
         *   <li>discount 40</li>
         *   <li>dineIn true</li>
         *   <li>lightning false</li>
         *   <li>qtyLeft 5</li>
         *   <li>availableFrom 10:00</li>
         *   <li>availableTo 14:00</li>
         * </ul>
         * Then asserts each accessor returns the expected value.</p>
         */
        @Test
        void testDealStoresBasicFields() {
            Deal deal = new Deal(
                    "D1",
                    40,
                    true,
                    false,
                    5,
                    LocalTime.of(10, 0),
                    LocalTime.of(14, 0)
            );

            assertEquals("D1", deal.objectId());
            assertEquals(40, deal.discount());
            assertTrue(deal.dineIn());
            assertFalse(deal.lightning());
            assertEquals(5, deal.qtyLeft());
            assertEquals(LocalTime.of(10, 0), deal.availableFrom());
            assertEquals(LocalTime.of(14, 0), deal.availableTo());
        }

        /**
         * Verifies that a Deal constructed with null time windows allows null values for
         * availableFrom and availableTo.
         *
         * <p>This ensures that the domain model supports deals without specific time windows.</p>
         */
        @Test
        void testDealAllowsNullTimeWindows() {
            Deal deal = new Deal(
                    "D2",
                    20,
                    false,
                    true,
                    1,
                    null,
                    null
            );

            assertNull(deal.availableFrom());
            assertNull(deal.availableTo());
        }
    }