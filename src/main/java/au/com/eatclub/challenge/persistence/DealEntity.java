package au.com.eatclub.challenge.persistence;

    import jakarta.persistence.*;

    import java.time.LocalTime;

    /**
     * Entity class representing a deal in the system.
     * Maps to the "deal" table in the database.
     */
    @Entity
    @Table(name = "deal")
    public class DealEntity {

        /**
         * Unique identifier for the deal.
         * Auto-generated using the IDENTITY strategy.
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        /**
         * Unique object identifier for the deal.
         * Cannot be null.
         */
        @Column(name = "object_id", nullable = false)
        private String objectId;

        /**
         * Discount percentage for the deal.
         */
        private int discount;

        /**
         * Indicates if the deal is available for dine-in.
         */
        private boolean dineIn;

        /**
         * Indicates if the deal is a lightning deal.
         */
        private boolean lightning;

        /**
         * Quantity of the deal left.
         */
        private int qtyLeft;

        /**
         * Time from which the deal is available.
         */
        @Column(name = "available_from")
        private LocalTime availableFrom;

        /**
         * Time until which the deal is available.
         */
        @Column(name = "available_to")
        private LocalTime availableTo;

        /**
         * The restaurant associated with the deal.
         * Maps to the "restaurant_id" column in the database.
         * Cannot be null.
         */
        @ManyToOne
        @JoinColumn(name = "restaurant_id", nullable = false)
        private RestaurantEntity restaurant;

        /**
         * Default constructor for JPA.
         */
        public DealEntity() {
        }

        /**
         * Constructor to initialize all fields of the DealEntity.
         *
         * @param objectId      Unique object identifier for the deal.
         * @param discount      Discount percentage for the deal.
         * @param dineIn        Indicates if the deal is available for dine-in.
         * @param lightning     Indicates if the deal is a lightning deal.
         * @param qtyLeft       Quantity of the deal left.
         * @param availableFrom Time from which the deal is available.
         * @param availableTo   Time until which the deal is available.
         * @param restaurant    The restaurant associated with the deal.
         */
        public DealEntity(
                String objectId,
                int discount,
                boolean dineIn,
                boolean lightning,
                int qtyLeft,
                LocalTime availableFrom,
                LocalTime availableTo,
                RestaurantEntity restaurant
        ) {
            this.objectId = objectId;
            this.discount = discount;
            this.dineIn = dineIn;
            this.lightning = lightning;
            this.qtyLeft = qtyLeft;
            this.availableFrom = availableFrom;
            this.availableTo = availableTo;
            this.restaurant = restaurant;
        }

        // Getters

        /**
         * Gets the unique identifier for the deal.
         *
         * @return the deal ID.
         */
        public Long getId() {
            return id;
        }

        /**
         * Gets the unique object identifier for the deal.
         *
         * @return the object ID.
         */
        public String getObjectId() {
            return objectId;
        }

        /**
         * Gets the discount percentage for the deal.
         *
         * @return the discount percentage.
         */
        public int getDiscount() {
            return discount;
        }

        /**
         * Checks if the deal is available for dine-in.
         *
         * @return true if dine-in is available, false otherwise.
         */
        public boolean isDineIn() {
            return dineIn;
        }

        /**
         * Checks if the deal is a lightning deal.
         *
         * @return true if it is a lightning deal, false otherwise.
         */
        public boolean isLightning() {
            return lightning;
        }

        /**
         * Gets the quantity of the deal left.
         *
         * @return the quantity left.
         */
        public int getQtyLeft() {
            return qtyLeft;
        }

        /**
         * Gets the time from which the deal is available.
         *
         * @return the start time of availability.
         */
        public LocalTime getAvailableFrom() {
            return availableFrom;
        }

        /**
         * Gets the time until which the deal is available.
         *
         * @return the end time of availability.
         */
        public LocalTime getAvailableTo() {
            return availableTo;
        }

        /**
         * Gets the restaurant associated with the deal.
         *
         * @return the associated restaurant.
         */
        public RestaurantEntity getRestaurant() {
            return restaurant;
        }

        // Setters

        /**
         * Sets the unique identifier for the deal.
         *
         * @param id the deal ID.
         */
        public void setId(Long id) {
            this.id = id;
        }

        /**
         * Sets the unique object identifier for the deal.
         *
         * @param objectId the object ID.
         */
        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        /**
         * Sets the discount percentage for the deal.
         *
         * @param discount the discount percentage.
         */
        public void setDiscount(int discount) {
            this.discount = discount;
        }

        /**
         * Sets whether the deal is available for dine-in.
         *
         * @param dineIn true if dine-in is available, false otherwise.
         */
        public void setDineIn(boolean dineIn) {
            this.dineIn = dineIn;
        }

        /**
         * Sets whether the deal is a lightning deal.
         *
         * @param lightning true if it is a lightning deal, false otherwise.
         */
        public void setLightning(boolean lightning) {
            this.lightning = lightning;
        }

        /**
         * Sets the quantity of the deal left.
         *
         * @param qtyLeft the quantity left.
         */
        public void setQtyLeft(int qtyLeft) {
            this.qtyLeft = qtyLeft;
        }

        /**
         * Sets the time from which the deal is available.
         *
         * @param availableFrom the start time of availability.
         */
        public void setAvailableFrom(LocalTime availableFrom) {
            this.availableFrom = availableFrom;
        }

        /**
         * Sets the time until which the deal is available.
         *
         * @param availableTo the end time of availability.
         */
        public void setAvailableTo(LocalTime availableTo) {
            this.availableTo = availableTo;
        }

        /**
         * Sets the restaurant associated with the deal.
         *
         * @param restaurant the associated restaurant.
         */
        public void setRestaurant(RestaurantEntity restaurant) {
            this.restaurant = restaurant;
        }
    }