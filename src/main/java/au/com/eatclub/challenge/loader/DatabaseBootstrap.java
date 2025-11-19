package au.com.eatclub.challenge.loader;

import au.com.eatclub.challenge.domain.ChallengeData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The `DatabaseBootstrap` class is a Spring Boot component that implements the `CommandLineRunner` interface.
 * It is executed after the application context is loaded and the Spring Boot application starts.
 * This class is responsible for loading challenge data and saving it to the database.
 */
@Component
public class DatabaseBootstrap implements CommandLineRunner {

    private final DataLoader loader; // Service responsible for loading challenge data
    private final DatabaseSaver saver; // Service responsible for saving challenge data to the database

    /**
     * Constructs a new `DatabaseBootstrap` instance with the specified `DataLoader` and `DatabaseSaver`.
     *
     * @param loader the service used to load challenge data
     * @param saver the service used to save challenge data
     */
    public DatabaseBootstrap(DataLoader loader, DatabaseSaver saver) {
        this.loader = loader;
        this.saver = saver;
    }

    /**
     * Executes the data loading and saving process when the application starts.
     *
     * @param args command-line arguments passed to the application
     */
    @Override
    public void run(String... args) {
        // Load challenge data using the DataLoader service
        ChallengeData data = loader.load();

        // Save the loaded data using the DatabaseSaver service
        saver.save(data);   // THIS NOW COMPILES
    }
}