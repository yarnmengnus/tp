package profplan.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static profplan.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import profplan.model.task.Task;
import profplan.model.task.exceptions.DuplicateTaskException;
import profplan.testutil.Assert;
import profplan.testutil.TaskBuilder;
import profplan.testutil.TypicalTasks;

public class ProfPlanTest {

    private final ProfPlan profPlan = new ProfPlan();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), profPlan.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> profPlan.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyProfPlan_replacesData() {
        ProfPlan newData = TypicalTasks.getTypicalProfPlan();
        profPlan.resetData(newData);
        assertEquals(newData, profPlan);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsDuplicateTaskException() {
        // Two tasks with the same identity fields
        Task editedAlice = new TaskBuilder(TypicalTasks.ALICE)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        List<Task> newTasks = Arrays.asList(TypicalTasks.ALICE, editedAlice);
        ProfPlanStub newData = new ProfPlanStub(newTasks);

        Assert.assertThrows(DuplicateTaskException.class, () -> profPlan.resetData(newData));
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> profPlan.hasTask(null));
    }

    @Test
    public void hasTask_taskNotInProfPlan_returnsFalse() {
        assertFalse(profPlan.hasTask(TypicalTasks.ALICE));
    }

    @Test
    public void hasTask_taskInProfPlan_returnsTrue() {
        profPlan.addTask(TypicalTasks.ALICE);
        assertTrue(profPlan.hasTask(TypicalTasks.ALICE));
    }

    @Test
    public void hasTask_taskWithSameIdentityFieldsInProfPlan_returnsTrue() {
        profPlan.addTask(TypicalTasks.ALICE);
        Task editedAlice = new TaskBuilder(TypicalTasks.ALICE)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(profPlan.hasTask(editedAlice));
    }

    //    @Test
    //    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
    //        Assert.assertThrows(UnsupportedOperationException.class, () -> profPlan.getTaskList().remove(0));
    //    }

    @Test
    public void toStringMethod() {
        String expected = ProfPlan.class.getCanonicalName() + "{tasks=" + profPlan.getTaskList() + "}";
        assertEquals(expected, profPlan.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose tasks list can violate interface constraints.
     */
    private static class ProfPlanStub implements ReadOnlyProfPlan {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        ProfPlanStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }
    }

}
