package au.com.eatclub.challenge.loader;

import au.com.eatclub.challenge.domain.ChallengeData;
import org.springframework.web.reactive.function.client.WebClient;

public class DataLoader {

    private final WebClient client;

    public DataLoader(WebClient client) {
        this.client = client;
    }

    public ChallengeData load() {
        return client.get()
                .uri("https://eccdn.com.au/misc/challengedata.json")
                .retrieve()
                .bodyToMono(ChallengeData.class)
                .block();
    }
}