package profplan.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static profplan.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static profplan.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import profplan.model.task.exceptions.DuplicateTaskException;
import profplan.model.task.exceptions.TaskNotFoundException;
import profplan.testutil.Assert;
import profplan.testutil.TaskBuilder;
import profplan.testutil.TypicalTasks;

public class UniqueTaskListTest {

    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();

    @Test
    public void contains_nullTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueTaskList.contains(null));
    }

    @Test
    public void contains_taskNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.contains(TypicalTasks.ALICE));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        uniqueTaskList.add(TypicalTasks.ALICE);
        assertTrue(uniqueTaskList.contains(TypicalTasks.ALICE));
    }

    @Test
    public void contains_taskWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTaskList.add(TypicalTasks.ALICE);
        Task editedAlice = new TaskBuilder(TypicalTasks.ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueTaskList.contains(editedAlice));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueTaskList.add(null));
    }

    @Test
    public void add_duplicateTask_throwsDuplicateTaskException() {
        uniqueTaskList.add(TypicalTasks.ALICE);
        Assert.assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.add(TypicalTasks.ALICE));
    }

    @Test
    public void setTask_nullTargetTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueTaskList.setTask(null, TypicalTasks.ALICE));
    }

    @Test
    public void setTask_nullEditedTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueTaskList.setTask(TypicalTasks.ALICE, null));
    }

    @Test
    public void setTask_targetTaskNotInList_throwsTaskNotFoundException() {
        Assert.assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.setTask(TypicalTasks.ALICE,
                TypicalTasks.ALICE));
    }

    @Test
    public void setTask_editedTaskIsSameTask_success() {
        uniqueTaskList.add(TypicalTasks.ALICE);
        uniqueTaskList.setTask(TypicalTasks.ALICE, TypicalTasks.ALICE);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TypicalTasks.ALICE);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasSameIdentity_success() {
        uniqueTaskList.add(TypicalTasks.ALICE);
        Task editedAlice = new TaskBuilder(TypicalTasks.ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueTaskList.setTask(TypicalTasks.ALICE, editedAlice);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(editedAlice);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasDifferentIdentity_success() {
        uniqueTaskList.add(TypicalTasks.ALICE);
        uniqueTaskList.setTask(TypicalTasks.ALICE, TypicalTasks.BOB);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TypicalTasks.BOB);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasNonUniqueIdentity_throwsDuplicateTaskException() {
        uniqueTaskList.add(TypicalTasks.ALICE);
        uniqueTaskList.add(TypicalTasks.BOB);
        Assert.assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setTask(TypicalTasks.ALICE,
                TypicalTasks.BOB));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueTaskList.remove(null));
    }

    @Test
    public void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        Assert.assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.remove(TypicalTasks.ALICE));
    }

    @Test
    public void remove_existingTask_removesTask() {
        uniqueTaskList.add(TypicalTasks.ALICE);
        uniqueTaskList.remove(TypicalTasks.ALICE);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_nullUniqueTaskList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((UniqueTaskList) null));
    }

    @Test
    public void setTasks_uniqueTaskList_replacesOwnListWithProvidedUniqueTaskList() {
        uniqueTaskList.add(TypicalTasks.ALICE);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TypicalTasks.BOB);
        uniqueTaskList.setTasks(expectedUniqueTaskList);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((List<Task>) null));
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        uniqueTaskList.add(TypicalTasks.ALICE);
        List<Task> taskList = Collections.singletonList(TypicalTasks.BOB);
        uniqueTaskList.setTasks(taskList);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TypicalTasks.BOB);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_listWithDuplicateTasks_throwsDuplicateTaskException() {
        List<Task> listWithDuplicateTasks = Arrays.asList(TypicalTasks.ALICE, TypicalTasks.ALICE);
        Assert.assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setTasks(
            listWithDuplicateTasks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTaskList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueTaskList.asUnmodifiableObservableList().toString(), uniqueTaskList.toString());
    }
}
