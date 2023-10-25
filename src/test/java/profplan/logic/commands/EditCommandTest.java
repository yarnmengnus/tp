package profplan.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static profplan.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import profplan.commons.core.index.Index;
import profplan.logic.Messages;
import profplan.logic.commands.EditCommand.EditTaskDescriptor;
import profplan.model.Model;
import profplan.model.ModelManager;
import profplan.model.ProfPlan;
import profplan.model.UserPrefs;
import profplan.model.task.Task;
import profplan.testutil.EditTaskDescriptorBuilder;
import profplan.testutil.TaskBuilder;
import profplan.testutil.TypicalIndexes;
import profplan.testutil.TypicalTasks;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(TypicalTasks.getTypicalProfPlan(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Task editedTask = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, Messages.format(editedTask));

        Model expectedModel = new ModelManager(new ProfPlan(model.getProfPlan()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        Task lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withName(CommandTestUtil.VALID_NAME_BOB)
                .withPriority(CommandTestUtil.VALID_PRIORITY_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB)
                .withPriority(CommandTestUtil.VALID_PRIORITY_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, Messages.format(editedTask));

        Model expectedModel = new ModelManager(new ProfPlan(model.getProfPlan()), new UserPrefs());
        expectedModel.setTask(lastTask, editedTask);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TASK, new EditTaskDescriptor());
        Task editedTask = model.getFilteredTaskList().get(TypicalIndexes.INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, Messages.format(editedTask));

        Model expectedModel = new ModelManager(new ProfPlan(model.getProfPlan()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showTaskAtIndex(model, TypicalIndexes.INDEX_FIRST_TASK);

        Task taskInFilteredList = model.getFilteredTaskList().get(
                TypicalIndexes.INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, Messages.format(editedTask));

        Model expectedModel = new ModelManager(new ProfPlan(model.getProfPlan()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTaskUnfilteredList_failure() {
        Task firstTask = model.getFilteredTaskList().get(TypicalIndexes.INDEX_FIRST_TASK.getZeroBased());
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_TASK, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_duplicateTaskFilteredList_failure() {
        CommandTestUtil.showTaskAtIndex(model, TypicalIndexes.INDEX_FIRST_TASK);

        // edit task in filtered list into a duplicate in task list
        Task taskInList = model.getProfPlan().getTaskList().get(TypicalIndexes.INDEX_SECOND_TASK
                .getZeroBased());
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder(taskInList).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB)
                .build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of task list
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        CommandTestUtil.showTaskAtIndex(model, TypicalIndexes.INDEX_FIRST_TASK);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of task list list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getProfPlan().getTaskList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TASK,
                CommandTestUtil.DESC_AMY);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(CommandTestUtil.DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(TypicalIndexes.INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_SECOND_TASK,
                CommandTestUtil.DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_FIRST_TASK,
                CommandTestUtil.DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        EditCommand editCommand = new EditCommand(index, editTaskDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editTaskDescriptor="
                + editTaskDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
