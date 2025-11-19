package au.com.eatclub.challenge.loader;

import au.com.eatclub.challenge.domain.ChallengeData;
import au.com.eatclub.challenge.domain.Deal;
import au.com.eatclub.challenge.domain.Restaurant;
import au.com.eatclub.challenge.persistence.*;
import au.com.eatclub.challenge.repository.*;
import org.springframework.stereotype.Component;

/**
 * Component responsible for saving challenge data into the database.
 * This class interacts with various repositories to persist entities such as
 * Suburb, Cuisine, Restaurant, and Deal.
 */
@Component
public class DatabaseSaver {

    private final SuburbRepository suburbRepo;
    private final CuisineRepository cuisineRepo;
    private final RestaurantRepository restaurantRepo;
    private final DealRepository dealRepo;

    /**
     * Constructor for DatabaseSaver.
     *
     * @param suburbRepo     Repository for managing Suburb entities.
     * @param cuisineRepo    Repository for managing Cuisine entities.
     * @param restaurantRepo Repository for managing Restaurant entities.
     * @param dealRepo       Repository for managing Deal entities.
     */
    public DatabaseSaver(
            SuburbRepository suburbRepo,
            CuisineRepository cuisineRepo,
            RestaurantRepository restaurantRepo,
            DealRepository dealRepo
    ) {
        this.suburbRepo = suburbRepo;
        this.cuisineRepo = cuisineRepo;
        this.restaurantRepo = restaurantRepo;
        this.dealRepo = dealRepo;
    }

    /**
     * Saves the provided ChallengeData into the database.
     * This method processes restaurants, their associated suburbs, cuisines, and deals,
     * and persists them using the appropriate repositories.
     *
     * @param data The ChallengeData object containing the data to be saved.
     */
    public void save(ChallengeData data) {

        // Iterate over each restaurant in the provided data
        for (Restaurant r : data.restaurants()) {

            // ----- Suburb -----
            // Find or create a SuburbEntity based on the restaurant's suburb name
            SuburbEntity suburb = suburbRepo
                    .findByName(r.suburb())
                    .orElseGet(() -> suburbRepo.save(new SuburbEntity(r.suburb())));

            // ----- Restaurant -----
            // Create a new RestaurantEntity and associate it with the suburb
            RestaurantEntity restaurant = new RestaurantEntity(
                    r.objectId(),
                    r.name(),
                    r.address1(),
                    r.imageLink(),
                    r.openTime(),
                    r.closeTime()
            );
            restaurant.setSuburb(suburb);

            // Save the restaurant to generate its ID
            restaurantRepo.save(restaurant);

            // ----- Cuisines -----
            // Process and associate cuisines with the restaurant
            for (String c : r.cuisines()) {
                CuisineEntity cuisine = cuisineRepo
                        .findByName(c)
                        .orElseGet(() -> cuisineRepo.save(new CuisineEntity(c)));

                restaurant.getCuisines().add(cuisine);
            }

            // Save the restaurant again to persist the cuisine relationships
            restaurantRepo.save(restaurant);

            // ----- Deals -----
            // Process and save deals associated with the restaurant
            for (Deal d : r.deals()) {

                DealEntity deal = new DealEntity(
                        d.objectId(),
                        d.discount(),
                        d.dineIn(),
                        d.lightning(),
                        d.qtyLeft(),
                        d.availableFrom(),
                        d.availableTo(),
                        restaurant
                );

                dealRepo.save(deal);
            }
        }
    }
}