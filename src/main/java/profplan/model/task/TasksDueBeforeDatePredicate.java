package profplan.model.task;

import java.util.function.Predicate;

import profplan.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Task}'s {@code Name} matches any of the keywords given.
 */
public class TasksDueBeforeDatePredicate implements Predicate<Task> {
    private final DueDate date;

    public TasksDueBeforeDatePredicate(DueDate date) {
        this.date = date;
    }

    public DueDate getDate() {
        return this.date;
    }

    @Override
    public boolean test(Task task) {
        return task.getDueDate().isBefore(date);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TasksDueBeforeDatePredicate)) {
            return false;
        }

        TasksDueBeforeDatePredicate otherPredicate = (TasksDueBeforeDatePredicate) other;
        return date.equals(otherPredicate.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("date", date).toString();
    }
}
