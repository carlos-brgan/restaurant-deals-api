package au.com.eatclub.challenge.loader;

    import au.com.eatclub.challenge.domain.ChallengeData;
    import org.springframework.stereotype.Component;
    import org.springframework.web.reactive.function.client.WebClient;

    import java.util.List;

    /**
     * The DataLoader class is responsible for fetching challenge data from a remote JSON endpoint
     * and mapping it into a domain-specific object.
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
         * Fetches challenge data from a predefined URL, maps the response to a DTO,
         * and converts it into a ChallengeData object.
         *
         * @return a ChallengeData object containing the loaded data
         *         or an empty ChallengeData object if the response is null or invalid
         */
        public ChallengeData load() {
            // Fetch the data from the remote endpoint and map it to a DTO
            ChallengeDataDTO dto = client.get()
                    .uri("https://eccdn.com.au/misc/challengedata.json")
                    .retrieve()
                    .bodyToMono(ChallengeDataDTO.class)
                    .block();

            // Return an empty ChallengeData object if the DTO or its restaurant list is null
            if (dto == null || dto.restaurants() == null)
                return new ChallengeData(List.of());

            // Map the DTO's restaurant data to the domain-specific ChallengeData object
            return new ChallengeData(ChallengeMapper.toRestaurants(dto.restaurants()));
        }
    }