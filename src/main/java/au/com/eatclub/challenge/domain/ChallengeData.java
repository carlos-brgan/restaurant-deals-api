package au.com.eatclub.challenge.domain;

import java.util.List;

/**
 * A record that represents challenge data.
 *
 * @param restaurants A list of restaurants associated with the challenge.
 */
public record ChallengeData(
        List<Restaurant> restaurants
) {
}
