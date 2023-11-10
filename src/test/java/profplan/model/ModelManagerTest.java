package profplan.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static profplan.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import profplan.commons.core.GuiSettings;
import profplan.model.task.predicates.NameContainsKeywordsPredicate;
import profplan.testutil.Assert;
import profplan.testutil.ProfPlanBuilder;
import profplan.testutil.TypicalTasks;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ProfPlan(), new ProfPlan(modelManager.getProfPlan()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setProfPlanFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setProfPlanFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setProfPlanFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setProfPlanFilePath(null));
    }

    @Test
    public void setProfPlanFilePath_validPath_setsProfPlanFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setProfPlanFilePath(path);
        assertEquals(path, modelManager.getProfPlanFilePath());
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasTask(null));
    }

    @Test
    public void hasTask_taskNotInProfPlan_returnsFalse() {
        assertFalse(modelManager.hasTask(TypicalTasks.ALICE));
    }

    @Test
    public void hasTask_taskInProfPlan_returnsTrue() {
        modelManager.addTask(TypicalTasks.ALICE);
        assertTrue(modelManager.hasTask(TypicalTasks.ALICE));
    }

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTaskList().remove(0));
    }

    @Test
    public void equals() {
        ProfPlan profPlan = new ProfPlanBuilder().withTask(
                TypicalTasks.ALICE).withTask(TypicalTasks.BENSON).build();
        ProfPlan differentProfPlan = new ProfPlan();
        UserPrefs userPrefs = new UserPrefs();
        UserConfigs userConfigs = new UserConfigs();

        // same values -> returns true
        modelManager = new ModelManager(profPlan, userPrefs, userConfigs);
        ModelManager modelManagerCopy = new ModelManager(profPlan, userPrefs, userConfigs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentProfPlan, userPrefs, userConfigs)));

        // different filteredList -> returns false
        String[] keywords = TypicalTasks.ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredTaskList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(profPlan, userPrefs, userConfigs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setProfPlanFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(profPlan, differentUserPrefs, userConfigs)));
    }
}
