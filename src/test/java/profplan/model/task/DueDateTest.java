package profplan.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import profplan.testutil.Assert;

public class DueDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DueDate(null));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> DueDate.isValidDate(null));

        // invalid dates
        assertFalse(DueDate.isValidDate("01-01-2031")); // max year is 2030
        assertFalse(DueDate.isValidDate("01/01/2000"));

        // valid dates
        assertTrue(DueDate.isValidDate("01-01-2000"));
        assertTrue(DueDate.isValidDate("31-02-2000"));
        assertTrue(DueDate.isValidDate("12-12-2030"));
    }

    @Test
    public void equals() {
        DueDate date = new DueDate("01-01-2000");

        // same values -> returns true
        assertTrue(date.equals(new DueDate("01-01-2000")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new DueDate("01-01-2030")));
    }
}
