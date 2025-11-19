package au.com.eatclub.challenge.loader;

import au.com.eatclub.challenge.domain.ChallengeData;
import au.com.eatclub.challenge.domain.Deal;
import au.com.eatclub.challenge.domain.Restaurant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for verifying the parsing of JSON data into domain objects.
 */
class MapperParsingTest {

    /**
     * Loads and parses the JSON file into a ChallengeData object using ObjectMapper.
     *
     * @return ChallengeData object containing parsed data.
     * @throws Exception if an error occurs while reading or parsing the file.
     */
    private ChallengeData loadUsingMapper() throws Exception {
        String json = Files.readString(Path.of("src/test/resources/challengedata.json"));
        ObjectMapper mapper = new ObjectMapper();
        ChallengeDataDTO raw = mapper.readValue(json, ChallengeDataDTO.class);

        return new ChallengeData(
                ChallengeMapper.toRestaurants(raw.restaurants())
        );
    }

    /**
     * Finds a restaurant by name in the given ChallengeData object.
     *
     * @param name the name of the restaurant to find.
     * @param data the ChallengeData object containing the list of restaurants.
     * @return the Restaurant object with the specified name.
     * @throws NoSuchElementException if no restaurant with the given name is found.
     */
    private Restaurant find(String name, ChallengeData data) {
        return data.restaurants()
                .stream()
                .filter(r -> r.name().equals(name))
                .findFirst()
                .orElseThrow();
    }

    // ----------------------------------------------------------

    /**
     * Tests if the JSON file can be read and contains the "restaurants" key.
     *
     * @throws Exception if an error occurs while reading the file.
     */
    @Test
    void test_canReadFile() throws Exception {
        String json = Files.readString(Path.of("src/test/resources/challengedata.json"));
        assertTrue(json.contains("\"restaurants\""));
    }

    // ----------------------------------------------------------

    /**
     * Tests if the mapper correctly parses the list of restaurants.
     *
     * @throws Exception if an error occurs during parsing.
     */
    @Test
    void test_mapperParsesRestaurants() throws Exception {
        ChallengeData data = loadUsingMapper();
        assertFalse(data.restaurants().isEmpty());
    }

    // ----------------------------------------------------------

    /**
     * Tests if the opening and closing times of a restaurant are parsed correctly.
     *
     * @throws Exception if an error occurs during parsing.
     */
    @Test
    void test_restaurantTimesParsed() throws Exception {
        ChallengeData data = loadUsingMapper();
        Restaurant r = find("Masala Kitchen", data);

        assertEquals(LocalTime.of(15, 0), r.openTime());
        assertEquals(LocalTime.of(21, 0), r.closeTime());
    }

    // ----------------------------------------------------------

    /**
     * Tests if the start and end times of a deal are explicitly parsed.
     *
     * @throws Exception if an error occurs during parsing.
     */
    @Test
    void test_dealStartEnd_explicit() throws Exception {
        ChallengeData data = loadUsingMapper();
        Restaurant r = find("Kekou", data);
        Deal d = r.deals().getFirst();

        assertEquals(LocalTime.of(14, 0), d.availableFrom());
        assertEquals(LocalTime.of(21, 0), d.availableTo());
    }

    // ----------------------------------------------------------

    /**
     * Tests if a deal falls back to the restaurant's opening and closing times
     * when no explicit times are provided.
     *
     * @throws Exception if an error occurs during parsing.
     */
    @Test
    void test_dealFallbackToRestaurantTimes() throws Exception {
        ChallengeData data = loadUsingMapper();
        Restaurant r = find("ABC Chicken", data);
        Deal d = r.deals().getFirst();

        assertEquals(r.openTime(), d.availableFrom());
        assertEquals(r.closeTime(), d.availableTo());
    }

    // ----------------------------------------------------------

    /**
     * Tests if the discount value of a deal is parsed as an integer.
     *
     * @throws Exception if an error occurs during parsing.
     */
    @Test
    void test_discountParsedAsInt() throws Exception {
        ChallengeData data = loadUsingMapper();
        Deal d = find("Masala Kitchen", data).deals().getFirst();
        assertEquals(50, d.discount());
    }

    // ----------------------------------------------------------

    /**
     * Tests if boolean fields (e.g., dineIn, lightning) are parsed correctly.
     *
     * @throws Exception if an error occurs during parsing.
     */
    @Test
    void test_booleanParsed() throws Exception {
        ChallengeData data = loadUsingMapper();
        Deal d = find("Masala Kitchen", data).deals().getFirst();

        assertFalse(d.dineIn());
        assertTrue(d.lightning());
    }

    // ----------------------------------------------------------

    /**
     * Tests if the quantity left (qtyLeft) field of a deal is parsed as an integer.
     *
     * @throws Exception if an error occurs during parsing.
     */
    @Test
    void test_qtyParsedAsInt() throws Exception {
        ChallengeData data = loadUsingMapper();
        Deal d = find("Masala Kitchen", data).deals().getFirst();
        assertEquals(5, d.qtyLeft());
    }
}