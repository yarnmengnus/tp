package profplan.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import profplan.commons.exceptions.DataLoadingException;
import profplan.commons.util.JsonUtil;
import profplan.model.ReadOnlyUserConfigs;
import profplan.model.UserConfigs;

/**
 * A class to access UserConfigs stored in the hard disk as a json file
 */
public class JsonUserConfigsStorage implements UserConfigsStorage {

    private Path filePath;

    public JsonUserConfigsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getUserConfigsFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserConfigs> readUserConfigs() throws DataLoadingException {
        return readUserConfigs(filePath);
    }

    /**
     * Similar to {@link #readUserConfigs()}
     * @param configsFilePath location of the data. Cannot be null.
     * @throws DataLoadingException if the file format is not as expected.
     */
    public Optional<UserConfigs> readUserConfigs(Path configsFilePath) throws DataLoadingException {
        return JsonUtil.readJsonFile(configsFilePath, UserConfigs.class);
    }

    @Override
    public void saveUserConfigs(ReadOnlyUserConfigs userConfigs) throws IOException {
        JsonUtil.saveJsonFile(userConfigs, filePath);
    }

}
