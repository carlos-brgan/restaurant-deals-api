package au.com.eatclub.challenge.loader;

import au.com.eatclub.challenge.domain.ChallengeData;
import au.com.eatclub.challenge.domain.Deal;
import au.com.eatclub.challenge.domain.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DataLoader class.
 * This class tests various scenarios for loading restaurant and deal data
 * from JSON input using the DataLoader class.
 */
class DataLoaderTest {

    /**
     * Helper method to create a DataLoader instance with a mocked WebClient
     * that returns the provided JSON response.
     *
     * @param json The JSON string to be returned by the mocked WebClient.
     * @return A DataLoader instance configured with the mocked WebClient.
     */
    private DataLoader loaderWithJson(String json) {

        ExchangeFunction exchange = request -> Mono.just(
                ClientResponse.create(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(json)
                        .build()
        );

        WebClient client = WebClient.builder()
                .exchangeFunction(exchange)
                .build();

        return new DataLoader(client);
    }

    // ----------------------------------------------------------------------
    // Basic single-restaurant load
    // ----------------------------------------------------------------------

    /**
     * Tests loading a single restaurant from JSON input.
     * Verifies that the restaurant data is correctly parsed and loaded.
     */
    @Test
    void test_loadSingleRestaurant() {

        String json = """
                {
                  "restaurants": [
                    {
                      "objectId": "R1",
                      "name": "Masala Kitchen",
                      "address1": "55 Walsh Street",
                      "suburb": "Lower East",
                      "open": "3:00pm",
                      "close": "9:00pm",
                      "cuisines": ["Indian"],
                      "deals": []
                    }
                  ]
                }
                """;

        DataLoader loader = loaderWithJson(json);
        ChallengeData data = loader.load();

        assertNotNull(data);
        assertEquals(1, data.restaurants().size());

        Restaurant r = data.restaurants().getFirst();

        assertEquals("R1", r.objectId());
        assertEquals("Masala Kitchen", r.name());
        assertEquals(LocalTime.of(15, 0), r.openTime());
        assertEquals(LocalTime.of(21, 0), r.closeTime());
    }

    // ----------------------------------------------------------------------
    // Deal parsing: explicit start/end
    // ----------------------------------------------------------------------

    /**
     * Tests parsing a deal with explicit start and end times.
     * Verifies that the deal's availability times are correctly parsed.
     */
    @Test
    void test_dealExplicitStartEnd() {

        String json = """
                {
                  "restaurants": [
                    {
                      "objectId": "RX",
                      "name": "Example",
                      "address1": "123",
                      "suburb": "Melbourne",
                      "open": "4:00pm",
                      "close": "10:00pm",
                      "cuisines": [],
                      "deals": [
                        {
                          "objectId": "D1",
                          "discount": "50",
                          "dineIn": "true",
                          "lightning": "false",
                          "qtyLeft": "5",
                          "start": "5:00pm",
                          "end": "9:00pm"
                        }
                      ]
                    }
                  ]
                }
                """;

        DataLoader loader = loaderWithJson(json);
        ChallengeData data = loader.load();
        Restaurant r = data.restaurants().getFirst();
        Deal d = r.deals().getFirst();

        assertEquals("D1", d.objectId());
        assertEquals(50, d.discount());
        assertTrue(d.dineIn());
        assertFalse(d.lightning());
        assertEquals(5, d.qtyLeft());

        assertEquals(LocalTime.of(17, 0), d.availableFrom()); // 5pm
        assertEquals(LocalTime.of(21, 0), d.availableTo());   // 9pm
    }

    // ----------------------------------------------------------------------
    // Deal parsing: fallback to restaurant open/close
    // ----------------------------------------------------------------------

    /**
     * Tests parsing a deal without explicit start and end times.
     * Verifies that the deal's availability falls back to the restaurant's open and close times.
     */
    @Test
    void test_dealFallbackStartEnd() {

        String json = """
                {
                  "restaurants": [
                    {
                      "objectId": "RX",
                      "name": "Example",
                      "address1": "123",
                      "suburb": "Melbourne",
                      "open": "4:00pm",
                      "close": "10:00pm",
                      "cuisines": [],
                      "deals": [
                        {
                          "objectId": "D2",
                          "discount": "20",
                          "dineIn": "false",
                          "lightning": "true",
                          "qtyLeft": "3"
                        }
                      ]
                    }
                  ]
                }
                """;

        DataLoader loader = loaderWithJson(json);
        ChallengeData data = loader.load();
        Restaurant r = data.restaurants().getFirst();
        Deal d = r.deals().getFirst();

        assertEquals(20, d.discount());
        assertFalse(d.dineIn());
        assertTrue(d.lightning());
        assertEquals(3, d.qtyLeft());

        // Restaurant opens at 4pm, closes at 10pm
        assertEquals(LocalTime.of(16, 0), d.availableFrom());
        assertEquals(LocalTime.of(22, 0), d.availableTo());
    }

    // ----------------------------------------------------------------------
    // Deal parsing: fallback to deal open/close fields
    // ----------------------------------------------------------------------

    /**
     * Tests parsing a deal with open and close fields.
     * Verifies that the deal's availability falls back to these fields.
     */
    @Test
    void test_dealOpenCloseFallback() {

        String json = """
                {
                  "restaurants": [
                    {
                      "objectId": "RX",
                      "name": "Example",
                      "address1": "123",
                      "suburb": "Melbourne",
                      "open": "4:00pm",
                      "close": "10:00pm",
                      "cuisines": [],
                      "deals": [
                        {
                          "objectId": "D3",
                          "discount": "10",
                          "dineIn": "false",
                          "lightning": "false",
                          "qtyLeft": "2",
                          "open": "6:00pm",
                          "close": "8:00pm"
                        }
                      ]
                    }
                  ]
                }
                """;

        DataLoader loader = loaderWithJson(json);
        ChallengeData data = loader.load();
        Restaurant r = data.restaurants().getFirst();
        Deal d = r.deals().getFirst();

        assertEquals(LocalTime.of(18, 0), d.availableFrom()); // 6pm
        assertEquals(LocalTime.of(20, 0), d.availableTo());   // 8pm
    }

    // ----------------------------------------------------------------------
    // Missing restaurant open/close is allowed
    // ----------------------------------------------------------------------

    /**
     * Tests loading a restaurant with missing open and close times.
     * Verifies that the restaurant's open and close times are null.
     */
    @Test
    void test_missingRestaurantOpenClose() {

        String json = """
                {
                  "restaurants": [
                    {
                      "objectId": "R1",
                      "name": "Missing Fields Test",
                      "address1": "Unknown",
                      "suburb": "Nowhere",
                      "cuisines": [],
                      "deals": []
                    }
                  ]
                }
                """;

        DataLoader loader = loaderWithJson(json);
        ChallengeData data = loader.load();
        Restaurant r = data.restaurants().getFirst();

        assertNull(r.openTime());
        assertNull(r.closeTime());
    }

    // ----------------------------------------------------------------------
    // Multiple restaurants
    // ----------------------------------------------------------------------

    /**
     * Tests loading multiple restaurants from JSON input.
     * Verifies that all restaurants are correctly parsed and loaded.
     */
    @Test
    void test_multipleRestaurants() {

        String json = """
                {
                  "restaurants": [
                    { "objectId": "R1", "name": "A", "address1": "1", "suburb": "X", "open": "1:00pm", "close": "5:00pm", "cuisines": [], "deals": [] },
                    { "objectId": "R2", "name": "B", "address1": "2", "suburb": "Y", "open": "2:00pm", "close": "6:00pm", "cuisines": [], "deals": [] }
                  ]
                }
                """;

        DataLoader loader = loaderWithJson(json);
        ChallengeData data = loader.load();

        assertEquals(2, data.restaurants().size());
        assertEquals("R1", data.restaurants().getFirst().objectId());
        assertEquals("R2", data.restaurants().get(1).objectId());
    }
}