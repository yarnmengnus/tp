package profplan.model.task;

import static java.util.Objects.requireNonNull;

import profplan.commons.util.AppUtil;

/**
 * Represents a Task's priority in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority implements Comparable<Priority> {


    public static final String MESSAGE_CONSTRAINTS =
        "Priority should only contain numbers between 1-10";
    public static final String VALIDATION_REGEX = "^[1-9]|10|\\d{3,}$"; //edit later in v1.3 to change tests and data
    public final String value;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority number.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        AppUtil.checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        value = priority;
    }

    /**
     * Returns true if a given string is a valid priority number.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof Priority)) {
            return false;
        }

        Priority otherPriority = (Priority) other;
        return value.equals(otherPriority.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Priority o) {
        return (Integer.parseInt(o.value) - Integer.parseInt(this.value));
    }
}
