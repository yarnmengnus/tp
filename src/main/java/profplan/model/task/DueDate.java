package profplan.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import profplan.commons.util.AppUtil;

/**
 * Represents a Task's due date in the ProfPlan.
 */
public class DueDate implements Comparable<DueDate> {
    public static final String MESSAGE_CONSTRAINTS =
        "Due date should be of dd-MM-yyyy format, and should be between 2000 and 2030.";

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static LocalDate min = LocalDate.of(1999, 12, 31);
    private static LocalDate max = LocalDate.of(2031, 1, 1);

    private String value;

    private LocalDate parsedValue = null;

    /**
     * Constructs a {@code DueDate}.
     *
     * @param date A valid date.
     */
    public DueDate(String date) {
        requireNonNull(date);
        value = date;
        AppUtil.checkArgument(isValidDate(this), MESSAGE_CONSTRAINTS);
        value = parsedValue == null ? date : dateTimeFormatter.format(parsedValue);
    }

    /**
     * Returns true if a given Duedate is of correct format.
     */
    public static boolean isValidDate(DueDate test) {
        try {
            LocalDate parsed = LocalDate.parse(test.value, dateTimeFormatter);
            if (parsed.isBefore(max) && parsed.isAfter(min)) {
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
            LocalDate parsed = LocalDate.parse(test, dateTimeFormatter);
            return parsed.isBefore(max) && parsed.isAfter(min);

        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks whether the current date is before, on equals the given date
     */
    public boolean isIncludedorBefore(DueDate otherDate) {
        return this.parsedValue.isBefore(otherDate.parsedValue)
            || this.parsedValue.equals(otherDate.parsedValue);
    }

    /**
     * Returns true if due date is within a week of today.
     */
    public boolean isWithinWeek() {
        LocalDate endOfWeek = LocalDate.now().plusWeeks(1);
        System.out.println(this.parsedValue);

        return !this.parsedValue.isBefore(LocalDate.now())
            && !this.parsedValue.isAfter(endOfWeek);
    }

    /**
     * Returns true if due date is within a month of today.
     */
    public boolean isWithinMonth() {
        LocalDate endOfMonth = LocalDate.now().plusMonths(1);

        return !this.parsedValue.isBefore(LocalDate.now())
            && !this.parsedValue.isAfter(endOfMonth);
    }

    /**
     * Increments a DueDate by the number of days specified.
     * @param days The number of days to increment by. Must be between 0-31.
     * @return A new DueDate object, with the incremented value.
     */
    public DueDate addDays(long days) throws IllegalArgumentException {
        return new DueDate(parsedValue.plusDays(days).format(dateTimeFormatter));
    }

    /**
     * Increments a DueDate by 1 month.
     * @return A new DueDate object, with the incremented value.
     */
    public DueDate addMonth() {
        return new DueDate(parsedValue.plusMonths(1).format(dateTimeFormatter));
    }

    /**
     * Returns the format for DueDate
     */
    public static DateTimeFormatter getDateFormat() {
        return dateTimeFormatter;
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
