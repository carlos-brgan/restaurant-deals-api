package au.com.eatclub.challenge.domain;

        import java.time.LocalTime;
        import java.util.List;

        /**
         * Represents a Restaurant entity with various attributes such as name, address,
         * operating hours, cuisines, and deals.
         *
         * @param objectId  Unique identifier for the restaurant.
         * @param name      Name of the restaurant.
         * @param address1  Address of the restaurant.
         * @param imageLink URL link to the restaurant's image.
         * @param openTime  Opening time of the restaurant.
         * @param closeTime Closing time of the restaurant.
         * @param suburb    Suburb where the restaurant is located.
         * @param cuisines  List of cuisines offered by the restaurant.
         * @param deals     List of deals available at the restaurant.
         */
        public record Restaurant(
                String objectId,
                String name,
                String address1,
                String imageLink,
                LocalTime openTime,
                LocalTime closeTime,
                String suburb,
                List<String> cuisines,
                List<Deal> deals
        ) { }