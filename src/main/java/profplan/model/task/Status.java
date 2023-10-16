package profplan.model.task;

import static java.util.Objects.requireNonNull;

import profplan.commons.util.AppUtil;

/**
 * Represents a Task's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS =
            "Status should only should only be done or undone";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\bdone\\b|\\bundone\\b";

    public final String status;

    /**
     * Constructs a {@code Name}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        AppUtil.checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        this.status = status;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return status.equals(otherStatus.status);
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

}
