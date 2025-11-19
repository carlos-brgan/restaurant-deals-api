package au.com.eatclub.challenge.repository;

import au.com.eatclub.challenge.persistence.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing `RestaurantEntity` persistence.
 *
 * <p>This interface extends the Spring Data JPA `JpaRepository` to provide
 * CRUD operations and additional query methods for the `RestaurantEntity` class.
 * </p>
 *
 * <p>Spring Data JPA will automatically generate the implementation for this
 * interface at runtime.</p>
 */
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    /**
     * Finds a `RestaurantEntity` by its object ID.
     *
     * @param objectId the unique object ID of the restaurant
     * @return an `Optional` containing the `RestaurantEntity` if found, or empty if not found
     */
    Optional<RestaurantEntity> findByObjectId(String objectId);
}