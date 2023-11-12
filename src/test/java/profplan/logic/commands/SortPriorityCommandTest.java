package profplan.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static profplan.logic.commands.CommandTestUtil.assertCommandSuccess;
import static profplan.testutil.TypicalTasks.getTypicalProfPlan;

import org.junit.jupiter.api.Test;

import profplan.model.Model;
import profplan.model.ModelManager;
import profplan.model.ProfPlan;
import profplan.model.UserConfigs;
import profplan.model.UserPrefs;

public class SortPriorityCommandTest {

    private Model model = new ModelManager(getTypicalProfPlan(), new UserPrefs(), new UserConfigs());

    @Test
    public void execute_model_success() {
        Model expectedModel = new ModelManager(new ProfPlan(model.getProfPlan()), new UserPrefs(), new UserConfigs());
        expectedModel.sortTaskByPriority();
        Model tempModel = new ModelManager(new ProfPlan(model.getProfPlan()), new UserPrefs(), new UserConfigs());
        tempModel.sortTaskByPriority();
        SortPriorityCommand sortPriorityCommand = new SortPriorityCommand();
        assertDoesNotThrow(() -> sortPriorityCommand.execute(tempModel));
        String expectedMessage = String.format(SortPriorityCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(sortPriorityCommand, model, expectedMessage, expectedModel);
    }

}
