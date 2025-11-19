package au.com.eatclub.challenge.loader;

import au.com.eatclub.challenge.domain.ChallengeData;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * The DataLoader class is responsible for fetching challenge data from a remote JSON endpoint
 * and mapping it into the application's domain model.
 */
@Component
public class DataLoader {

    private final WebClient client;

    /**
     * Constructs a DataLoader with the specified WebClient.
     *
     * @param client the WebClient used to make HTTP requests
     */
    public DataLoader(WebClient client) {
        this.client = client;
    }

    /**
     * Loads challenge data from a predefined remote JSON endpoint.
     * <p>
     * The method fetches data using the WebClient, maps the response to a DTO,
     * and converts it into the application's domain model. If the response is null
     * or contains no restaurant data, an empty ChallengeData object is returned.
     * </p>
     *
     * @return a ChallengeData object containing the mapped restaurant data
     */
    public ChallengeData load() {
        // Fetch the data from the remote endpoint and map it to ChallengeDataDTO
        ChallengeDataDTO dto = client.get()
                .uri("https://eccdn.com.au/misc/challengedata.json")
                .retrieve()
                .bodyToMono(ChallengeDataDTO.class)
                .block();

        // Return an empty ChallengeData object if the response or restaurant data is null
        if (dto == null || dto.restaurants() == null) {
            return new ChallengeData(List.of());
        }

        // Map the DTO's restaurant data to the domain model and return it
        return new ChallengeData(
                ChallengeMapper.toRestaurants(dto.restaurants())
        );
    }
}