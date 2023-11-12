package profplan.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import profplan.model.Model;
import profplan.model.ModelManager;
import profplan.model.task.Task;
import profplan.testutil.Assert;
import profplan.testutil.TaskBuilder;

public class UnmarkCommandTest {


    @Test
    public void constructor_taskNumberAsZero_throwsNullPointerException() {
        Assert.assertThrows(AssertionError.class, () -> new UnmarkCommand(0));
    }

    @Test
    public void execute_invalidTaskNumber_throwsAssertionFailure() throws Exception {
        Model modelStub = new ModelManager();
        Task validTask = new TaskBuilder().build();

        modelStub.addTask(validTask);

        assertThrows(AssertionError.class, ()-> new UnmarkCommand(-1).execute(modelStub));
    }

    @Test
    public void execute_negativeIndexToMark_throwsAssertionFailureException() {
        int outOfBoundIndex = -100;
        assertThrows(AssertionError.class, ()-> new UnmarkCommand(outOfBoundIndex));
    }

    @Test
    public void execute_zeroIndexToMark_throwsAssertionFailureException() {
        int outOfBoundIndex = 0;
        assertThrows(AssertionError.class, ()-> new UnmarkCommand(outOfBoundIndex));
    }


    @Test
    public void testEqualsWithVariousScenarios() {
        UnmarkCommand unmarkCommand1 = new UnmarkCommand(1);
        UnmarkCommand markCommand2 = new UnmarkCommand(1);
        UnmarkCommand markCommand3 = new UnmarkCommand(2);
        Object notMarkCommand = new Object();


        assertTrue(unmarkCommand1.equals(unmarkCommand1));

        // Test for null
        assertFalse(unmarkCommand1.equals(null));


        assertTrue(unmarkCommand1.equals(markCommand2));
        assertTrue(markCommand2.equals(unmarkCommand1));


        UnmarkCommand markCommand4 = new UnmarkCommand(1);
        assertTrue(unmarkCommand1.equals(markCommand2));
        assertTrue(markCommand2.equals(markCommand4));
        assertTrue(unmarkCommand1.equals(markCommand4));


        assertTrue(unmarkCommand1.equals(markCommand2));
        assertTrue(unmarkCommand1.equals(markCommand2));


        assertFalse(unmarkCommand1.equals(markCommand3));


        assertFalse(unmarkCommand1.equals(notMarkCommand));
    }

    @Test
    public void toString_indexToUnmark_success() {
        UnmarkCommand unmarkCommand = new UnmarkCommand(1);
        String expectedStatus = UnmarkCommand.class.getCanonicalName() + "{taskNumber=" + 1 + "}";
        assertEquals(unmarkCommand.toString(), expectedStatus);
    }
}





