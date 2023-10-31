package profplan.logic.commands;

import static profplan.logic.commands.CommandTestUtil.assertCommandSuccess;
import static profplan.logic.commands.CommandTestUtil.showTaskAtIndex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import profplan.model.Model;
import profplan.model.ModelManager;
import profplan.model.UserPrefs;
import profplan.model.task.predicates.TaskInMonthPredicate;
import profplan.model.task.predicates.TaskInWeekPredicate;
import profplan.testutil.TypicalIndexes;
import profplan.testutil.TypicalTasks;

/**
 * Contains integration tests (interaction with the Model)
 * and unit tests for ListCommand, ListWeekCommand, and ListMonthCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalTasks.getTypicalProfPlan(), new UserPrefs());
        expectedModel = new ModelManager(model.getProfPlan(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTaskAtIndex(model, TypicalIndexes.INDEX_FIRST_TASK);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void testListWeek() {
        expectedModel.updateFilteredTaskList(new TaskInWeekPredicate());
        assertCommandSuccess(new ListWeekCommand(), model, ListWeekCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void testListMonth() {
        expectedModel.updateFilteredTaskList(new TaskInMonthPredicate());
        assertCommandSuccess(new ListMonthCommand(), model, ListMonthCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
