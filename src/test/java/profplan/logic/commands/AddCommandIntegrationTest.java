package profplan.logic.commands;

import static profplan.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import profplan.logic.Messages;
import profplan.model.Model;
import profplan.model.ModelManager;
import profplan.model.UserPrefs;
import profplan.model.task.Task;
import profplan.testutil.TaskBuilder;
import profplan.testutil.TypicalTasks;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalTasks.getTypicalProfPlan(), new UserPrefs());
    }

    @Test
    public void execute_newTask_success() {
        Task validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(model.getProfPlan(), new UserPrefs());
        expectedModel.addTask(validTask);

        assertCommandSuccess(new AddCommand(validTask), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validTask)),
                expectedModel);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task taskInList = model.getProfPlan().getTaskList().get(0);
        CommandTestUtil.assertCommandFailure(new AddCommand(taskInList), model,
                AddCommand.MESSAGE_DUPLICATE_TASK);
    }

}
