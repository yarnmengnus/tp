package profplan.model.task;

import java.util.function.Predicate;

import profplan.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Task}'s {@code DueDate} falls before the given duedate.
 */
public class TaskDueDatePredicate implements Predicate<Task> {
    private final DueDate date;

    public TaskDueDatePredicate(DueDate date) {
        this.date = date;
    }

    public DueDate getDate() {
        return this.date;
    }

    @Override
    public boolean test(Task task) {
        return task.getDueDate().isIncludedorBefore(date);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskDueDatePredicate)) {
            return false;
        }

        TaskDueDatePredicate otherPredicate = (TaskDueDatePredicate) other;
        return date.equals(otherPredicate.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("date", date).toString();
    }
}
