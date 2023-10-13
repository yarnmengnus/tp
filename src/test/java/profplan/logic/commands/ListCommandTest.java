package profplan.logic.commands;

import static profplan.logic.commands.CommandTestUtil.assertCommandSuccess;
import static profplan.logic.commands.CommandTestUtil.showPersonAtIndex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import profplan.model.Model;
import profplan.model.ModelManager;
import profplan.model.UserPrefs;
import profplan.testutil.TypicalIndexes;
import profplan.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalProfPlan(), new UserPrefs());
        expectedModel = new ModelManager(model.getProfPlan(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
