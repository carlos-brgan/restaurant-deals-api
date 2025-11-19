package au.com.eatclub.challenge.repository;

import au.com.eatclub.challenge.persistence.SuburbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing `SuburbEntity` persistence.
 *
 * This interface extends the Spring Data JPA `JpaRepository` to provide
 * CRUD operations and additional query methods for the `SuburbEntity`.
 *
 * @author jcpendesign
 */
public interface SuburbRepository extends JpaRepository<SuburbEntity, Long> {

    /**
     * Finds a `SuburbEntity` by its name.
     *
     * @param name the name of the suburb to search for
     * @return an `Optional` containing the `SuburbEntity` if found, or empty if not found
     */
    Optional<SuburbEntity> findByName(String name);
}