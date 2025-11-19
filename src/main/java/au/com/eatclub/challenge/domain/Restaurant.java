package au.com.eatclub.challenge.domain;

        import java.time.LocalTime;
        import java.util.List;

        /**
         * Immutable domain record that represents a restaurant.
         *
         * <p>This record captures identifying information, address details, daily opening hours,
         * the suburb/location, a list of cuisine types and any active deals for the restaurant.
         *
         * <p>Instances are immutable and intended for use as simple data carriers within the
         * application domain.
         *
         * @param objectId   The unique identifier of the restaurant.
         * @param name       The restaurant's display name.
         * @param address1   The primary street address.
         * @param imageLink  An optional link to an image for the restaurant.
         * @param openTime   The daily opening time.
         * @param closeTime  The daily closing time.
         * @param suburb     The suburb or city where the restaurant is located.
         * @param cuisines   A list of cuisine categories the restaurant belongs to.
         * @param deals      A list of deals offered by the restaurant.
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