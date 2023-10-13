package profplan.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import profplan.model.ProfPlan;
import profplan.testutil.TypicalPersons;
import profplan.commons.core.GuiSettings;
import profplan.model.ReadOnlyProfPlan;
import profplan.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonProfPlanStorage addressBookStorage = new JsonProfPlanStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void profPlanReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonProfPlanStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonProfPlanStorageTest} class.
         */
        ProfPlan original = TypicalPersons.getTypicalProfPlan();
        storageManager.saveProfPlan(original);
        ReadOnlyProfPlan retrieved = storageManager.readProfPlan().get();
        assertEquals(original, new ProfPlan(retrieved));
    }

    @Test
    public void getProfPlanFilePath() {
        assertNotNull(storageManager.getProfPlanFilePath());
    }

}
