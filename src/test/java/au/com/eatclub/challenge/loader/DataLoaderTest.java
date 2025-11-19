package au.com.eatclub.challenge.loader;

import au.com.eatclub.challenge.domain.ChallengeData;
import au.com.eatclub.challenge.domain.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link DataLoader} class.
 * <p>
 * This test class ensures that the {@link DataLoader} can successfully fetch and process
 * {@link ChallengeData} using a mocked HTTP client layer (`WebClient`).
 *
 * <ul>
 *   <li>The HTTP response simulation is handled via a custom {@link ExchangeFunction}.</li>
 *   <li>Assertions verify that the system processes the response correctly and produces
 *   a valid {@link ChallengeData} object with expected values.</li>
 * </ul>
 */
class DataLoaderTest {

    /**
     * Tests that the {@link DataLoader#load()} correctly fetches challenge data
     * from a mocked HTTP response and processes it successfully.
     * <p>
     * Steps performed:
     * <ol>
     *   <li>A mock JSON response is returned by configuring a custom {@link ExchangeFunction}.</li>
     *   <li>A {@link WebClient} with the mocked behavior is passed into the {@link DataLoader}.</li>
     *   <li>The {@code load()} method of {@link DataLoader} retrieves and processes the data.</li>
     *   <li>Assertions verify the correctness of the resulting {@link ChallengeData} object.</li>
     * </ol>
     * <p>
     * Assertions:
     * <ul>
     *   <li>Ensures the result is not <code>null</code>.</li>
     *   <li>Validates the size of the restaurants list.</li>
     *   <li>Checks individual properties of the first restaurant.</li>
     * </ul>
     */
    @Test
    void testLoadReturnsChallengeData() {
        // Setup: Create a mock ExchangeFunction that returns predefined JSON data
        ExchangeFunction exchange = getExchangeFunction();

        // Create a WebClient with the mock ExchangeFunction
        WebClient client = WebClient.builder()
                .exchangeFunction(exchange)
                .build();

        // Create the system under test (DataLoader) with mocked WebClient
        DataLoader loader = new DataLoader(client);

        // Invocation: Call the load() method to fetch ChallengeData
        ChallengeData result = loader.load();

        // Assertions: Verify the ChallengeData object and its contents
        assertNotNull(result, "Resulting ChallengeData should not be null");
        assertEquals(1, result.restaurants().size(), "Expected exactly 1 restaurant in the result");

        // Validate individual restaurant properties
        Restaurant r = result.restaurants().getFirst();
        assertEquals("R1", r.objectId(), "Unexpected restaurant object ID");
        assertEquals("Test R", r.name(), "Unexpected restaurant name");
        assertEquals("123", r.address1(), "Unexpected restaurant address1");
        assertEquals("Sydney", r.suburb(), "Unexpected restaurant suburb");
    }

    /**
     * Mocks the HTTP response for the {@link WebClient}.
     * <p>
     * This method provides an {@link ExchangeFunction} that simulates an HTTP request
     * and returns a predefined JSON payload. The payload contains a list of restaurants.
     *
     * @return A mocked {@link ExchangeFunction} that returns a static response.
     */
    private static ExchangeFunction getExchangeFunction() {
        String json = """
                {
                  "restaurants": [
                    {
                      "objectId": "R1",
                      "name": "Test R",
                      "address1": "123",
                      "imageLink": null,
                      "openTime": null,
                      "closeTime": null,
                      "suburb": "Sydney",
                      "cuisines": [],
                      "deals": []
                    }
                  ]
                }
                """;

        // Mock WebClient at the HTTP layer
        return request -> Mono.just(
                ClientResponse.create(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(json)
                        .build()
        );
    }
}