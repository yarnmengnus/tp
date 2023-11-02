package profplan.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import profplan.commons.exceptions.DataLoadingException;
import profplan.model.ReadOnlyUserConfigs;
import profplan.model.UserConfigs;

/**
 * Represents a storage for {@link profplan.model.UserConfigs}.
 */
public interface UserConfigsStorage {

    /**
     * Returns the file path of the UserConfigs data file.
     */
    Path getUserConfigsFilePath();

    /**
     * Returns UserConfigs data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if the loading of data from preference file failed.
     */
    Optional<UserConfigs> readUserConfigs() throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyUserConfigs} to the storage.
     * @param userConfigs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserConfigs(ReadOnlyUserConfigs userConfigs) throws IOException;

}
