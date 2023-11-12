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
public class MarkCommandTest {


    @Test
    public void constructor_taskNumberAsZero_throwsNullPointerException() {
        Assert.assertThrows(AssertionError.class, () -> new MarkCommand(0));
    }

    @Test
    public void execute_invalidTaskNumber_throwsAssertionFailure() throws Exception {
        Model modelStub = new ModelManager();
        Task validTask = new TaskBuilder().build();

        modelStub.addTask(validTask);

        assertThrows(AssertionError.class, ()-> new MarkCommand(-1).execute(modelStub));
    }

    @Test
    public void execute_negativeIndexToMark_throwsAssertionFailureException() {
        int outOfBoundIndex = -100;
        assertThrows(AssertionError.class, ()-> new MarkCommand(outOfBoundIndex));
    }

    @Test
    public void execute_zeroIndexToMark_throwsAssertionFailureException() {
        int outOfBoundIndex = 0;
        assertThrows(AssertionError.class, ()-> new MarkCommand(outOfBoundIndex));
    }


    @Test
    public void testEqualsWithVariousScenarios() {
        MarkCommand markCommand1 = new MarkCommand(1);
        MarkCommand markCommand2 = new MarkCommand(1);
        MarkCommand markCommand3 = new MarkCommand(2);
        Object notMarkCommand = new Object();


        assertTrue(markCommand1.equals(markCommand1));

        // Test for null
        assertFalse(markCommand1.equals(null));


        assertTrue(markCommand1.equals(markCommand2));
        assertTrue(markCommand2.equals(markCommand1));


        MarkCommand markCommand4 = new MarkCommand(1);
        assertTrue(markCommand1.equals(markCommand2));
        assertTrue(markCommand2.equals(markCommand4));
        assertTrue(markCommand1.equals(markCommand4));


        assertTrue(markCommand1.equals(markCommand2));
        assertTrue(markCommand1.equals(markCommand2));


        assertFalse(markCommand1.equals(markCommand3));


        assertFalse(markCommand1.equals(notMarkCommand));
    }

    @Test
    public void toString_indexToMark_success() {
        MarkCommand markCommand = new MarkCommand(1);
        String expectedStatus = MarkCommand.class.getCanonicalName() + "{taskNumber=" + 1 + "}";
        assertEquals(markCommand.toString(), expectedStatus);
    }
}





