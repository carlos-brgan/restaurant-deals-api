package au.com.eatclub.challenge.persistence;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity class representing a restaurant in the system.
 * Maps to the "restaurant" table in the database.
 */
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {

    /**
     * Primary key for the restaurant entity.
     * Auto-generated using the IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique identifier for the restaurant.
     * Cannot be null and must be unique.
     */
    @Column(name = "object_id", nullable = false, unique = true)
    private String objectId;

    /**
     * Name of the restaurant.
     */
    private String name;

    /**
     * Address line 1 of the restaurant.
     */
    private String address1;

    /**
     * URL or link to the restaurant's image.
     */
    @Column(name = "image_link")
    private String imageLink;

    /**
     * Opening time of the restaurant.
     */
    @Column(name = "open_time")
    private LocalTime openTime;

    /**
     * Closing time of the restaurant.
     */
    @Column(name = "close_time")
    private LocalTime closeTime;

    /**
     * The suburb where the restaurant is located.
     * Many restaurants can belong to one suburb.
     */
    @ManyToOne
    @JoinColumn(name = "suburb_id")
    private SuburbEntity suburb;

    /**
     * The cuisines offered by the restaurant.
     * Many-to-many relationship with the CuisineEntity.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "restaurant_cuisine",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "cuisine_id")
    )
    private Set<CuisineEntity> cuisines = new HashSet<>();

    /**
     * The deals associated with the restaurant.
     * One-to-many relationship with the DealEntity.
     * Deals are cascaded and removed when the restaurant is deleted.
     */
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DealEntity> deals = new ArrayList<>();

    /**
     * Default constructor for JPA.
     */
    public RestaurantEntity() {
    }

    /**
     * Constructor to initialize a restaurant entity with basic details.
     *
     * @param objectId  Unique identifier for the restaurant.
     * @param name      Name of the restaurant.
     * @param address1  Address line 1 of the restaurant.
     * @param imageLink URL or link to the restaurant's image.
     * @param openTime  Opening time of the restaurant.
     * @param closeTime Closing time of the restaurant.
     */
    public RestaurantEntity(
            String objectId,
            String name,
            String address1,
            String imageLink,
            LocalTime openTime,
            LocalTime closeTime
    ) {
        this.objectId = objectId;
        this.name = name;
        this.address1 = address1;
        this.imageLink = imageLink;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    // Getters

    /**
     * @return The primary key of the restaurant.
     */
    public Long getId() {
        return id;
    }

    /**
     * @return The unique identifier of the restaurant.
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * @return The name of the restaurant.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The address line 1 of the restaurant.
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @return The image link of the restaurant.
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     * @return The opening time of the restaurant.
     */
    public LocalTime getOpenTime() {
        return openTime;
    }

    /**
     * @return The closing time of the restaurant.
     */
    public LocalTime getCloseTime() {
        return closeTime;
    }

    /**
     * @return The suburb entity associated with the restaurant.
     */
    public SuburbEntity getSuburb() {
        return suburb;
    }

    /**
     * @return The set of cuisines offered by the restaurant.
     */
    public Set<CuisineEntity> getCuisines() {
        return cuisines;
    }

    /**
     * @return The list of deals associated with the restaurant.
     */
    public List<DealEntity> getDeals() {
        return deals;
    }

    // Setters

    /**
     * Sets the primary key of the restaurant.
     *
     * @param id The primary key to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the unique identifier of the restaurant.
     *
     * @param objectId The unique identifier to set.
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * Sets the name of the restaurant.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the address line 1 of the restaurant.
     *
     * @param address1 The address to set.
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Sets the image link of the restaurant.
     *
     * @param imageLink The image link to set.
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    /**
     * Sets the opening time of the restaurant.
     *
     * @param openTime The opening time to set.
     */
    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    /**
     * Sets the closing time of the restaurant.
     *
     * @param closeTime The closing time to set.
     */
    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * Sets the suburb entity associated with the restaurant.
     *
     * @param suburb The suburb entity to set.
     */
    public void setSuburb(SuburbEntity suburb) {
        this.suburb = suburb;
    }

    /**
     * Sets the set of cuisines offered by the restaurant.
     *
     * @param cuisines The set of cuisines to set.
     */
    public void setCuisines(Set<CuisineEntity> cuisines) {
        this.cuisines = cuisines;
    }

    /**
     * Sets the list of deals associated with the restaurant.
     *
     * @param deals The list of deals to set.
     */
    public void setDeals(List<DealEntity> deals) {
        this.deals = deals;
    }

    // Convenience helpers

    /**
     * Adds a deal to the restaurant and sets the relationship.
     *
     * @param deal The deal to add.
     */
    public void addDeal(DealEntity deal) {
        deals.add(deal);
        deal.setRestaurant(this);
    }

    /**
     * Removes a deal from the restaurant and clears the relationship.
     *
     * @param deal The deal to remove.
     */
    public void removeDeal(DealEntity deal) {
        deals.remove(deal);
        deal.setRestaurant(null);
    }
}