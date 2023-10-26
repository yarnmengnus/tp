package profplan.model.task;

import java.util.function.Predicate;

import profplan.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Task}'s {@code Priority} matches the given priority.
 */
public class TaskStatusPredicate implements Predicate<Task> {
    private final Status status;

    public TaskStatusPredicate(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }

    @Override
    public boolean test(Task task) {
        return task.getStatus().equals(status);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskStatusPredicate)) {
            return false;
        }

        TaskStatusPredicate otherPredicate = (TaskStatusPredicate) other;
        return status.equals(otherPredicate.status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("status", status).toString();
    }
}
