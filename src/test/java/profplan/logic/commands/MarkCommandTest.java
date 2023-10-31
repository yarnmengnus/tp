package profplan.logic.commands;

import static javafx.beans.binding.Bindings.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import profplan.model.Model;
import profplan.model.ModelManager;
import profplan.model.task.Task;
import profplan.model.task.UniqueTaskList;
import profplan.testutil.Assert;
import profplan.testutil.TaskBuilder;

public class MarkCommandTest {
    @Test
    public void constructor_taskNumberAsZero_throwsNullPointerException() {
        Assert.assertThrows(AssertionError.class, () -> new MarkCommand(0));
    }

    @Test
    public void execute_validTaskNumber_taskMarkedAsDone() throws Exception {
        Model modelStub = new ModelManager();
        Task validTask = new TaskBuilder().build();

        modelStub.addTask(validTask);

        CommandResult commandResult = new MarkCommand(1).execute(modelStub);

        assertEquals(MarkCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
    }



}
