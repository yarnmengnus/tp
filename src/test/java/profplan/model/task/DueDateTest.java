package profplan.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import profplan.testutil.Assert;

public class DueDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DueDate(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidDate = "31-31/2000";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DueDate(invalidDate));
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
    public void isDateBefore() {
        DueDate before = new DueDate("01-01-2023");
        DueDate after = new DueDate("01-01-2024");
        assertTrue(before.isIncludedorBefore(after));

        DueDate no = new DueDate("No due date");
        assertTrue(before.isIncludedorBefore(no));
        assertFalse(no.isIncludedorBefore(before));
    }

    @Test
    public void isDateWithinWeekMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Calendar currentCal = Calendar.getInstance();
        currentCal.set(Calendar.DAY_OF_WEEK, currentCal.getFirstDayOfWeek());

        Calendar testCal = Calendar.getInstance();
        testCal.set(Calendar.DAY_OF_WEEK, currentCal.getFirstDayOfWeek());
        testCal.add(Calendar.DAY_OF_YEAR, 4);

        DueDate test = new DueDate(sdf.format(testCal.getTime()));
        assertTrue(test.isWithinWeek());

        testCal.add(Calendar.DAY_OF_YEAR, 15);
        DueDate test2 = new DueDate(sdf.format(testCal.getTime()));
        assertFalse(test2.isWithinWeek());
        assertTrue(test2.isWithinMonth());
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
