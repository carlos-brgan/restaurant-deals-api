package au.com.eatclub.challenge.persistence;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Suburb entity in the persistence layer.
 * This entity is mapped to the "suburb" table in the database.
 */
@Entity
@Table(name = "suburb")
public class SuburbEntity {

    /**
     * The unique identifier for the suburb.
     * This is the primary key and is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the suburb.
     * This field is mandatory and must be unique.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * The list of restaurants associated with this suburb.
     * This is a one-to-many relationship, mapped by the "suburb" field in the RestaurantEntity.
     */
    @OneToMany(mappedBy = "suburb")
    private List<RestaurantEntity> restaurants = new ArrayList<>();

    /**
     * Default constructor for JPA.
     */
    public SuburbEntity() {
    }

    /**
     * Constructs a SuburbEntity with the specified name.
     *
     * @param name the name of the suburb
     */
    public SuburbEntity(String name) {
        this.name = name;
    }

    /**
     * Gets the unique identifier of the suburb.
     *
     * @return the ID of the suburb
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the name of the suburb.
     *
     * @return the name of the suburb
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of restaurants associated with this suburb.
     *
     * @return the list of restaurants
     */
    public List<RestaurantEntity> getRestaurants() {
        return restaurants;
    }

    /**
     * Sets the unique identifier of the suburb.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the name of the suburb.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the list of restaurants associated with this suburb.
     *
     * @param restaurants the list of restaurants to set
     */
    public void setRestaurants(List<RestaurantEntity> restaurants) {
        this.restaurants = restaurants;
    }
}