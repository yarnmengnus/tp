package profplan.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import profplan.commons.core.Settings;
import profplan.commons.exceptions.DataLoadingException;
import profplan.model.UserConfigs;
import profplan.testutil.Assert;

public class JsonUserConfigsStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonUserConfigsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readUserConfigs_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readUserConfigs(null));
    }

    private Optional<UserConfigs> readUserConfigs(String userConfigsFileInTestDataFolder) throws DataLoadingException {
        Path configsFilePath = addToTestDataPathIfNotNull(userConfigsFileInTestDataFolder);
        return new JsonUserConfigsStorage(configsFilePath).readUserConfigs(configsFilePath);
    }

    @Test
    public void readUserConfigs_missingFile_emptyResult() throws DataLoadingException {
        assertFalse(readUserConfigs("NonExistentFile.json").isPresent());
    }

    @Test
    public void readUserConfigs_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataLoadingException.class, () -> readUserConfigs("NotJsonFormatUserConfigs.json"));
    }

    private Path addToTestDataPathIfNotNull(String userConfigsFileInTestDataFolder) {
        return userConfigsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(userConfigsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readUserConfigs_fileInOrder_successfullyRead() throws DataLoadingException {
        UserConfigs expected = getTypicalUserConfigs();
        UserConfigs actual = readUserConfigs("TypicalUserConfig.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readUserConfigs_valuesMissingFromFile_defaultValuesUsed() throws DataLoadingException {
        UserConfigs actual = readUserConfigs("EmptyUserConfigs.json").get();
        assertEquals(new UserConfigs(), actual);
    }

    @Test
    public void readUserConfigs_extraValuesInFile_extraValuesIgnored() throws DataLoadingException {
        UserConfigs expected = getTypicalUserConfigs();
        UserConfigs actual = readUserConfigs("ExtraValuesUserConfig.json").get();

        assertEquals(expected, actual);
    }

    private UserConfigs getTypicalUserConfigs() {
        UserConfigs userConfigs = new UserConfigs();
        userConfigs.setSettings(new Settings(180));
        userConfigs.setProfPlanFilePath(Paths.get("data\\profplan.json"));
        return userConfigs;
    }

    @Test
    public void saveConfigs_nullConfigs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveUserConfigs(null, "SomeFile.json"));
    }

    @Test
    public void saveUserConfigs_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveUserConfigs(new UserConfigs(), null));
    }

    /**
     * Saves {@code userConfigs} at the specified {@code ConfigsFileInTestDataFolder} filepath.
     */
    private void saveUserConfigs(UserConfigs userConfigs, String configsFileInTestDataFolder) {
        try {
            new JsonUserConfigsStorage(addToTestDataPathIfNotNull(configsFileInTestDataFolder))
                    .saveUserConfigs(userConfigs);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

    @Test
    public void saveUserConfigs_allInOrder_success() throws DataLoadingException, IOException {

        UserConfigs original = new UserConfigs();
        original.setSettings(new Settings(180));

        Path pefsFilePath = testFolder.resolve("TempConfigs.json");
        JsonUserConfigsStorage jsonUserConfigsStorage = new JsonUserConfigsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        jsonUserConfigsStorage.saveUserConfigs(original);
        UserConfigs readBack = jsonUserConfigsStorage.readUserConfigs().get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setSettings(new Settings(180));
        jsonUserConfigsStorage.saveUserConfigs(original);
        readBack = jsonUserConfigsStorage.readUserConfigs().get();
        assertEquals(original, readBack);
    }

}
