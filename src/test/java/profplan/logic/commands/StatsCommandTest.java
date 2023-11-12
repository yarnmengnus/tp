package profplan.logic.commands;

import static profplan.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import profplan.model.Model;
import profplan.model.ModelManager;
import profplan.model.UserConfigs;
import profplan.model.UserPrefs;
import profplan.testutil.TypicalTasks;

public class StatsCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalTasks.getTypicalProfPlan(), new UserPrefs(), new UserConfigs());
        expectedModel = new ModelManager(model.getProfPlan(), new UserPrefs(), new UserConfigs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new StatsCommand(), model,
            StatsCommand.MESSAGE_SUCCESS + String.format(StatsCommand.COMPLETION_RATE_MESSAGE_FORMAT,
                Math.ceil(model.getCompletionRate() * 1000) / 10),
            expectedModel);
    }
}
