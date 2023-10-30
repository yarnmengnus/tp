package profplan.model.task.predicates;

import java.util.function.Predicate;

import profplan.commons.util.ToStringBuilder;
import profplan.model.task.Priority;
import profplan.model.task.Task;

/**
 * Tests that a {@code Task}'s {@code Priority} matches the given priority.
 */
public class TaskPriorityPredicate implements Predicate<Task> {
    private final Priority priority;

    public TaskPriorityPredicate(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return this.priority;
    }

    @Override
    public boolean test(Task task) {
        return task.getPriority().equals(priority);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskPriorityPredicate)) {
            return false;
        }

        TaskPriorityPredicate otherPredicate = (TaskPriorityPredicate) other;
        return priority.equals(otherPredicate.priority);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("priority", priority).toString();
    }
}
