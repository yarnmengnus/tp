package profplan.model.task;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    private static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param date A valid date.
     */
    public DueDate(String date) {
        requireNonNull(date);
        AppUtil.checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidDate(String test) {
        try {
            if (test.equals("No due date")) {
                return true;
            }
            format.parse(test);
            return test.matches(VALIDATION_REGEX);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Checks whether the current date is before, on equals the given date
     */
    public boolean isIncludedorBefore(DueDate otherDate) {
        try {
            if (otherDate.value.equals("No due date")) {
                return true;
            } if (this.value.equals("No due date")) {
                return false;
            }
            Date parsedDate = format.parse(this.value);
            Date parsedOtherDate = format.parse(otherDate.value);
            return parsedDate.before(parsedOtherDate) || parsedDate.equals(parsedOtherDate);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Returns true if due date is within a week of today.
     */
    public boolean isWithinWeek() {
        try {
            if (this.value.equals("No due date")) {
                return false;
            }
            Date parsedDate = format.parse(this.value);

            Calendar currentCal = Calendar.getInstance();
            currentCal.set(Calendar.DAY_OF_WEEK, currentCal.getFirstDayOfWeek());

            Date currentDay = currentCal.getTime();

            currentCal.add(Calendar.DAY_OF_YEAR, 7);
            Date endOfWeek = currentCal.getTime();

            return !parsedDate.before(currentDay) && !parsedDate.after(endOfWeek);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Returns true if due date is within a month of today.
     */
    public boolean isWithinMonth() {
        try {
            if (this.value.equals("No due date")) {
                return false;
            }
            Date parsedDate = format.parse(this.value);

            Calendar currentCal = Calendar.getInstance();
            currentCal.set(Calendar.DAY_OF_WEEK, currentCal.getFirstDayOfWeek());

            Date currentDay = currentCal.getTime();

            currentCal.add(Calendar.DAY_OF_YEAR, 30);
            Date endOfMonth = currentCal.getTime();

            return !parsedDate.before(currentDay) && !parsedDate.after(endOfMonth);
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
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
            return format.parse(this.value).compareTo(format.parse(o.value));
        } catch (ParseException e) {
            return 0;
        }
    }
}
