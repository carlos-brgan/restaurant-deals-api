package au.com.eatclub.challenge.persistence;

    import jakarta.persistence.*;
    import java.util.HashSet;
    import java.util.Set;

    /**
     * Entity class representing a Cuisine in the database.
     * This class is mapped to the "cuisine" table and contains
     * information about different types of cuisines.
     */
    @Entity
    @Table(name = "cuisine")
    public class CuisineEntity {

        /**
         * The unique identifier for the cuisine.
         * This is the primary key and is auto-generated.
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        /**
         * The name of the cuisine.
         * This field is mandatory and must be unique.
         */
        @Column(nullable = false, unique = true)
        private String name;

        /**
         * The set of restaurants associated with this cuisine.
         * This is a bidirectional many-to-many relationship.
         */
        @ManyToMany(mappedBy = "cuisines")
        private Set<RestaurantEntity> restaurants = new HashSet<>();

        /**
         * Default constructor for JPA.
         */
        public CuisineEntity() {
        }

        /**
         * Constructor to create a CuisineEntity with a specific name.
         *
         * @param name The name of the cuisine.
         */
        public CuisineEntity(String name) {
            this.name = name;
        }

        /**
         * Gets the unique identifier of the cuisine.
         *
         * @return The ID of the cuisine.
         */
        public Long getId() {
            return id;
        }

        /**
         * Gets the name of the cuisine.
         *
         * @return The name of the cuisine.
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the set of restaurants associated with this cuisine.
         *
         * @return A set of RestaurantEntity objects.
         */
        public Set<RestaurantEntity> getRestaurants() {
            return restaurants;
        }

        /**
         * Sets the unique identifier of the cuisine.
         *
         * @param id The ID to set.
         */
        public void setId(Long id) {
            this.id = id;
        }

        /**
         * Sets the name of the cuisine.
         *
         * @param name The name to set.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Sets the set of restaurants associated with this cuisine.
         *
         * @param restaurants The set of RestaurantEntity objects to set.
         */
        public void setRestaurants(Set<RestaurantEntity> restaurants) {
            this.restaurants = restaurants;
        }
    }