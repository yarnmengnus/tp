package profplan.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import profplan.commons.util.AppUtil;

/**
 * Represents a Task's due date in the address book.
 */
public class DueDate implements Comparable<DueDate> {
    public static final String MESSAGE_CONSTRAINTS =
        "Due date should be of dd-MM-yyyy format, and should not be after the year 2030";

    public static final String DATE_REGEX = "(0[1-9]|[12][0-9]|3[0,1])"; // specify date beween 01 and 31
    public static final String MONTH_REGEX = "(0[1-9]|1[0-2])"; // specify month between 01 and 12
    public static final String YEAR_REGEX = "(2000|20[0-2][0-9]|2030)";
    public static final String VALIDATION_REGEX = DATE_REGEX + "-" + MONTH_REGEX + "-" + YEAR_REGEX;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public final String value;

    private LocalDate parsedValue = null;

    /**
     * Constructs an {@code Address}.
     *
     * @param date A valid date.
     */
    public DueDate(String date) {
        requireNonNull(date);
        value = date;
        AppUtil.checkArgument(isValidDate(this), MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given Duedate is of correct format.
     */
    public static boolean isValidDate(DueDate test) {
        try {
            if (test.value.equals("No due date")) {
                return true;
            }
            LocalDate parsed = LocalDate.parse(test.value, dateTimeFormatter);
            if (test.value.matches(VALIDATION_REGEX)) {
                test.parsedValue = parsed;
                return true;
            } else {
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Overloaded method for isValidDate
     */
    public static boolean isValidDate(String test) {
        try {
            if (test.equals("No due date")) {
                return true;
            }
            LocalDate.parse(test, dateTimeFormatter);
            return test.matches(VALIDATION_REGEX);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks whether the current date is before, on equals the given date
     */
    public boolean isIncludedorBefore(DueDate otherDate) {
        if (otherDate.value.equals("No due date")) {
            return true;
        }
        if (this.value.equals("No due date")) {
            return false;
        }
        return this.parsedValue.isBefore(otherDate.parsedValue)
            || this.parsedValue.equals(otherDate.parsedValue);
    }

    /**
     * Returns true if due date is within a week of today.
     */
    public boolean isWithinWeek() {
        if (this.value.equals("No due date")) {
            return false;
        }
        LocalDate endOfWeek = LocalDate.now().plusWeeks(1);
        System.out.println(this.parsedValue);

        return !this.parsedValue.isBefore(LocalDate.now())
            && !this.parsedValue.isAfter(endOfWeek);
    }

    /**
     * Returns true if due date is within a month of today.
     */
    public boolean isWithinMonth() {
        if (this.value.equals("No due date")) {
            return false;
        }
        LocalDate endOfMonth = LocalDate.now().plusMonths(1);

        return !this.parsedValue.isBefore(LocalDate.now())
            && !this.parsedValue.isAfter(endOfMonth);
    }

    /**
     * Increments a DueDate by the number of days specified.
     * @param dueDate The DueDate to increment
     * @param days The number of days to increment by. Must be between 0-31.
     * @return A new DueDate object, with the incremented value.
     */
    public DueDate addDays(DueDate dueDate, long days) {
        requireNonNull(dueDate);
        LocalDate date = LocalDate.parse(value, dateTimeFormatter);
        return new DueDate(date.plusDays(days).format(dateTimeFormatter));
    }

    /**
     * Increments a DueDate by 1 month.
     * @param dueDate The DueDate to increment
     * @return A new DueDate object, with the incremented value.
     */
    public DueDate addMonth(DueDate dueDate) {
        requireNonNull(dueDate);
        LocalDate date = LocalDate.parse(value, dateTimeFormatter);
        return new DueDate(date.plusMonths(1).format(dateTimeFormatter));
    }

    @Override
    public String toString() {
        return parsedValue == null ? value : dateTimeFormatter.format(parsedValue);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DueDate)) {
            return false;
        }

        DueDate otherDate = (DueDate) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(DueDate o) {
        try {
            LocalDate thisDate = LocalDate.parse(this.value, dateTimeFormatter);
            LocalDate otherDate = LocalDate.parse(o.value, dateTimeFormatter);

            return thisDate.compareTo(otherDate);
        } catch (DateTimeParseException e) {
            return 0;
        } 
    }
}
