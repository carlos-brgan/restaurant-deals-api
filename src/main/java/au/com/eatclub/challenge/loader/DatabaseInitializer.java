package au.com.eatclub.challenge.loader;

    import org.springframework.boot.CommandLineRunner;
    import org.springframework.stereotype.Component;

    /**
     * The `DatabaseInitializer` class is a Spring component that implements the `CommandLineRunner` interface.
     * It is used to initialize the database by invoking the `load` method of the `DataLoader` class
     * when the application starts.
     */
    @Component
    public class DatabaseInitializer implements CommandLineRunner {

        private final DataLoader loader;

        /**
         * Constructs a `DatabaseInitializer` with the specified `DataLoader`.
         *
         * @param loader the `DataLoader` instance responsible for loading and saving domain data
         */
        public DatabaseInitializer(DataLoader loader) {
            this.loader = loader;
        }

        /**
         * Executes the `load` method of the `DataLoader` class when the application starts.
         * This method is triggered automatically by Spring Boot after the application context is loaded.
         *
         * @param args command-line arguments passed to the application (not used in this implementation)
         */
        @Override
        public void run(String... args) {
            loader.load();   // load + saveDomain() will run once
        }
    }