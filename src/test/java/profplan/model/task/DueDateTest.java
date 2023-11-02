package profplan.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import profplan.testutil.Assert;

public class DueDateTest {
    private DateTimeFormatter dateTimeFormatter = DueDate.getDateFormat();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DueDate(null));
    }

    @Test
    public void isValidDate() {
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

        LocalDate in4Days = LocalDate.now().plusDays(4);

        DueDate test = new DueDate(dateTimeFormatter.format(in4Days));
        assertTrue(test.isWithinWeek());

        LocalDate in15Days = LocalDate.now().plusDays(15);
        DueDate test2 = new DueDate(dateTimeFormatter.format(in15Days));
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
