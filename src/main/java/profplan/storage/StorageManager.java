package profplan.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import profplan.commons.core.LogsCenter;
import profplan.commons.exceptions.DataLoadingException;
import profplan.model.ReadOnlyProfPlan;
import profplan.model.ReadOnlyUserConfigs;
import profplan.model.ReadOnlyUserPrefs;
import profplan.model.UserConfigs;
import profplan.model.UserPrefs;

/**
 * Manages storage of ProfPlan data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ProfPlanStorage profPlanStorage;
    private UserPrefsStorage userPrefsStorage;
    private UserConfigsStorage userConfigsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ProfPlanStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ProfPlanStorage profPlanStorage, UserPrefsStorage userPrefsStorage,
                          UserConfigsStorage userConfigsStorage) {
        this.profPlanStorage = profPlanStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userConfigsStorage = userConfigsStorage;
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

    // ================ UserConfigs methods ==============================

    @Override
    public Path getUserConfigsFilePath() {
        return userConfigsStorage.getUserConfigsFilePath();
    }

    @Override
    public Optional<UserConfigs> readUserConfigs() throws DataLoadingException {
        return userConfigsStorage.readUserConfigs();
    }

    @Override
    public void saveUserConfigs(ReadOnlyUserConfigs userConfigs) throws IOException {
        userConfigsStorage.saveUserConfigs(userConfigs);
    }

    // ================ ProfPlan methods ==============================

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
    public void saveProfPlan(ReadOnlyProfPlan profPlan) throws IOException {
        saveProfPlan(profPlan, profPlanStorage.getProfPlanFilePath());
    }

    @Override
    public void saveProfPlan(ReadOnlyProfPlan profPlan, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        profPlanStorage.saveProfPlan(profPlan, filePath);
    }

}
