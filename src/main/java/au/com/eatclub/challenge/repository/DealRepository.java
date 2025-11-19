package au.com.eatclub.challenge.repository;

import au.com.eatclub.challenge.persistence.DealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing `DealEntity` persistence.
 *
 * <p>This interface extends the Spring Data JPA `JpaRepository` interface,
 * providing CRUD operations and additional query methods for the `DealEntity` entity.</p>
 *
 * <p>By extending `JpaRepository`, this interface inherits several methods for working
 * with `DealEntity` persistence, such as saving, deleting, and finding entities.
 * Custom query methods can also be defined by following Spring Data JPA conventions.</p>
 *
 * @see JpaRepository
 * @see DealEntity
 */
public interface DealRepository extends JpaRepository<DealEntity, Long> {
}