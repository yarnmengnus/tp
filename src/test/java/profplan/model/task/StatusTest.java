package profplan.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import profplan.testutil.Assert;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidStatus = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {

        //checks for invalid status
        Assert.assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));
        assertFalse(Status.isValidStatus(""));
        assertFalse(Status.isValidStatus(" "));
        assertFalse(Status.isValidStatus("0"));
        assertFalse(Status.isValidStatus("11"));
        assertFalse(Status.isValidStatus("status"));
        assertFalse(Status.isValidStatus("9p"));
        assertFalse(Status.isValidStatus("9 1"));
        assertFalse(Status.isValidStatus("1"));
        assertFalse(Status.isValidStatus("10"));
        assertFalse(Status.isValidStatus("3"));
        assertFalse(Status.isValidStatus("6"));

        //checks for valid status
        assertTrue(Status.isValidStatus("done"));
        assertTrue(Status.isValidStatus("undone"));
    }

    @Test
    public void equals() {
        Status status = new Status("done");

        // same values -> returns true
        assertTrue(status.equals(new Status("done")));

        // same object -> returns true
        assertTrue(status.equals(status));

        // different types -> returns false
        assertFalse(status.equals(5.0f));

        // different values -> returns false
        assertFalse(status.equals(new Status("undone")));
    }


}
