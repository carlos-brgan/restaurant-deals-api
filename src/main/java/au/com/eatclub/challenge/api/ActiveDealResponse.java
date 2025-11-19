package au.com.eatclub.challenge.api;

        import au.com.eatclub.challenge.domain.Deal;
        import au.com.eatclub.challenge.domain.Restaurant;

        /**
         * A record representing the response for an active deal.
         * This record encapsulates details about a restaurant and its associated deal.
         *
         * @param restaurantObjectId The unique identifier of the restaurant.
         * @param restaurantName The name of the restaurant.
         * @param restaurantAddress1 The primary address of the restaurant.
         * @param restaurantSuburb The suburb where the restaurant is located.
         * @param restaurantOpen The opening time of the restaurant (as a string).
         * @param restaurantClose The closing time of the restaurant (as a string).
         * @param dealObjectId The unique identifier of the deal.
         * @param discount The discount percentage offered in the deal.
         * @param dineIn Indicates whether the deal is for dine-in customers.
         * @param lightning Indicates whether the deal is a lightning deal.
         * @param qtyLeft The quantity of the deal left.
         */
        public record ActiveDealResponse(
                String restaurantObjectId,
                String restaurantName,
                String restaurantAddress1,
                String restaurantSuburb,
                String restaurantOpen,
                String restaurantClose,
                String dealObjectId,
                int discount,
                boolean dineIn,
                boolean lightning,
                int qtyLeft
        ) {

            /**
             * Creates an instance of ActiveDealResponse from a Restaurant and a Deal.
             *
             * @param r The restaurant object containing details about the restaurant.
             * @param d The deal object containing details about the deal.
             * @return A new ActiveDealResponse instance populated with data from the provided Restaurant and Deal.
             */
            public static ActiveDealResponse from(Restaurant r, Deal d) {
                return new ActiveDealResponse(
                        r.objectId(),
                        r.name(),
                        r.address1(),
                        r.suburb(),
                        r.openTime() != null ? r.openTime().toString() : null,
                        r.closeTime() != null ? r.closeTime().toString() : null,
                        d.objectId(),
                        d.discount(),
                        d.dineIn(),
                        d.lightning(),
                        d.qtyLeft()
                );
            }
        }