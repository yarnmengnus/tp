package profplan.model.task;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import profplan.commons.util.AppUtil;


public class DueDate {
    public static final String MESSAGE_CONSTRAINTS = "Due date should be of DD/MM/YYYY format, and should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    // public static final String DATE_REGEX = "(0[1-9]|[12][0-9]|3[01])"; // specify date beween 01 and 31
    // public static final String MONTH_REGEX = "(0[1-9]|1[1,2])"; // specify month between 01 and 12
    // public static final String YEAR_REGEX = "(19|20)\\d{2}";
    // public static final String VALIDATION_REGEX = DATE_REGEX + "/" + MONTH_REGEX + "/" + YEAR_REGEX;

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
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
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.parse(test);
            return true;
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

}