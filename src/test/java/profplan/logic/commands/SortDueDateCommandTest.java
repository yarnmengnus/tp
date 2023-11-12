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

public class SortDueDateCommandTest {

    private Model model = new ModelManager(getTypicalProfPlan(), new UserPrefs(), new UserConfigs());

    @Test
    public void execute_model_success() {
        Model expectedModel = new ModelManager(new ProfPlan(model.getProfPlan()), new UserPrefs(), new UserConfigs());
        expectedModel.sortTaskByDueDate();
        Model tempModel = new ModelManager(new ProfPlan(model.getProfPlan()), new UserPrefs(), new UserConfigs());
        tempModel.sortTaskByDueDate();
        SortDueDateCommand sortDueDateCommand = new SortDueDateCommand();
        assertDoesNotThrow(() -> sortDueDateCommand.execute(tempModel));
        String expectedMessage = String.format(SortDueDateCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(sortDueDateCommand, model, expectedMessage, expectedModel);
    }

}
