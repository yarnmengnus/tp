package profplan.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import profplan.testutil.Assert;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidPriority = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // null priority number
        Assert.assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // invalid priority numbers
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("0")); // less than 1
        assertFalse(Priority.isValidPriority("11")); // more than 10
        assertFalse(Priority.isValidPriority("priority")); // non-numeric
        assertFalse(Priority.isValidPriority("9p")); // alphabets within digits
        assertFalse(Priority.isValidPriority("9 1")); // spaces within digits

        // valid priority numbers
        assertTrue(Priority.isValidPriority("1")); // minimum valid priority
        assertTrue(Priority.isValidPriority("10")); // maximum valid priority
        assertTrue(Priority.isValidPriority("3")); // valid priority within the range
        assertTrue(Priority.isValidPriority("6"));
    }

    @Test
    public void equals() {
        Priority priority = new Priority("9");

        // same values -> returns true
        assertTrue(priority.equals(new Priority("9")));

        // same object -> returns true
        assertTrue(priority.equals(priority));

        // null -> returns false
        assertFalse(priority.equals(null));

        // different types -> returns false
        assertFalse(priority.equals(5.0f));

        // different values -> returns false
        assertFalse(priority.equals(new Priority("5")));
    }
}
