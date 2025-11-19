package au.com.eatclub.challenge.repository;

import au.com.eatclub.challenge.persistence.CuisineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing `CuisineEntity` persistence.
 *
 * <p>This interface extends the Spring Data JPA `JpaRepository` to provide
 * CRUD operations and additional query methods for the `CuisineEntity` class.
 * </p>
 *
 * <p>Spring Data JPA will automatically generate the implementation for this
 * interface at runtime.</p>
 */
public interface CuisineRepository extends JpaRepository<CuisineEntity, Long> {

    /**
     * Finds a `CuisineEntity` by its name.
     *
     * @param name the name of the cuisine to search for
     * @return an `Optional` containing the `CuisineEntity` if found, or empty if not found
     */
    Optional<CuisineEntity> findByName(String name);
}