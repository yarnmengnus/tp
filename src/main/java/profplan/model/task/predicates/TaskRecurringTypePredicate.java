package profplan.model.task.predicates;

import java.util.function.Predicate;

import profplan.commons.util.ToStringBuilder;
import profplan.model.task.Task;
import profplan.model.task.Task.RecurringType;

/**
 * Tests that a {@code Task}'s {@code Recurring} matches the given recurring.
 */
public class TaskRecurringTypePredicate implements Predicate<Task> {
    private final RecurringType recur;

    public TaskRecurringTypePredicate(RecurringType recur) {
        this.recur = recur;
    }

    public RecurringType getRecurringType() {
        return this.recur;
    }

    @Override
    public boolean test(Task task) {
        return this.recur == null
            ? !task.getIsRecurring()
            : this.recur.equals(task.getRecurringType());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskRecurringTypePredicate)) {
            return false;
        }

        TaskRecurringTypePredicate otherPredicate = (TaskRecurringTypePredicate) other;
        if (recur == null) {
            return otherPredicate.recur == null;
        }
        return otherPredicate == null
            ? false
            : recur.equals(otherPredicate.recur);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("recurringType", recur == null ? "None" : recur)
            .toString();
    }
}
