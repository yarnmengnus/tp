package profplan.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import profplan.commons.exceptions.DataLoadingException;
import profplan.model.ReadOnlyProfPlan;
import profplan.model.ReadOnlyUserConfigs;
import profplan.model.ReadOnlyUserPrefs;
import profplan.model.UserConfigs;
import profplan.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ProfPlanStorage, UserPrefsStorage, UserConfigsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getUserConfigsFilePath();

    @Override
    Optional<UserConfigs> readUserConfigs() throws DataLoadingException;

    @Override
    void saveUserConfigs(ReadOnlyUserConfigs userConfigs) throws IOException;

    @Override
    Path getProfPlanFilePath();

    @Override
    Optional<ReadOnlyProfPlan> readProfPlan() throws DataLoadingException;

    @Override
    void saveProfPlan(ReadOnlyProfPlan profPlan) throws IOException;

}
