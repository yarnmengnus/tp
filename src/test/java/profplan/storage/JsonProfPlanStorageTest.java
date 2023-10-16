package profplan.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import profplan.commons.exceptions.DataLoadingException;
import profplan.model.ProfPlan;
import profplan.model.ReadOnlyProfPlan;
import profplan.testutil.Assert;
import profplan.testutil.TypicalTasks;

public class JsonProfPlanStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonProfPlanStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readProfPlan_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readProfPlan(null));
    }

    private java.util.Optional<ReadOnlyProfPlan> readProfPlan(String filePath) throws Exception {
        return new JsonProfPlanStorage(Paths.get(filePath)).readProfPlan(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readProfPlan("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataLoadingException.class, () -> readProfPlan("notJsonFormatProfPlan.json"));
    }

    @Test
    public void readProfPlan_invalidTaskProfPlan_throwDataLoadingException() {
        Assert.assertThrows(DataLoadingException.class, () -> readProfPlan("invalidTaskProfPlan.json"));
    }

    @Test
    public void readProfPlan_invalidAndValidTaskProfPlan_throwDataLoadingException() {
        Assert.assertThrows(DataLoadingException.class, () -> readProfPlan("invalidAndValidTaskProfPlan.json"));
    }

    @Test
    public void readAndSaveProfPlan_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        ProfPlan original = TypicalTasks.getTypicalProfPlan();
        JsonProfPlanStorage jsonProfPlanStorage = new JsonProfPlanStorage(filePath);

        // Save in new file and read back
        jsonProfPlanStorage.saveProfPlan(original, filePath);
        ReadOnlyProfPlan readBack = jsonProfPlanStorage.readProfPlan(filePath).get();
        assertEquals(original, new ProfPlan(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTask(TypicalTasks.HOON);
        original.removeTask(TypicalTasks.ALICE);
        jsonProfPlanStorage.saveProfPlan(original, filePath);
        readBack = jsonProfPlanStorage.readProfPlan(filePath).get();
        assertEquals(original, new ProfPlan(readBack));

        // Save and read without specifying file path
        original.addTask(TypicalTasks.IDA);
        jsonProfPlanStorage.saveProfPlan(original); // file path not specified
        readBack = jsonProfPlanStorage.readProfPlan().get(); // file path not specified
        assertEquals(original, new ProfPlan(readBack));

    }

    @Test
    public void saveProfPlan_nullProfPlan_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveProfPlan(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveProfPlan(ReadOnlyProfPlan profPlan, String filePath) {
        try {
            new JsonProfPlanStorage(Paths.get(filePath))
                    .saveProfPlan(profPlan, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveProfPlan_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveProfPlan(new ProfPlan(), null));
    }
}
