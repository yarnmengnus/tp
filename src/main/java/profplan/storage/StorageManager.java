package profplan.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import profplan.commons.core.LogsCenter;
import profplan.commons.exceptions.DataLoadingException;
import profplan.model.ReadOnlyProfPlan;
import profplan.model.ReadOnlyUserPrefs;
import profplan.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ProfPlanStorage profPlanStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ProfPlanStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ProfPlanStorage profPlanStorage, UserPrefsStorage userPrefsStorage) {
        this.profPlanStorage = profPlanStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getProfPlanFilePath() {
        return profPlanStorage.getProfPlanFilePath();
    }

    @Override
    public Optional<ReadOnlyProfPlan> readProfPlan() throws DataLoadingException {
        return readProfPlan(profPlanStorage.getProfPlanFilePath());
    }

    @Override
    public Optional<ReadOnlyProfPlan> readProfPlan(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return profPlanStorage.readProfPlan(filePath);
    }

    @Override
    public void saveProfPlan(ReadOnlyProfPlan addressBook) throws IOException {
        saveProfPlan(addressBook, profPlanStorage.getProfPlanFilePath());
    }

    @Override
    public void saveProfPlan(ReadOnlyProfPlan addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        profPlanStorage.saveProfPlan(addressBook, filePath);
    }

}
