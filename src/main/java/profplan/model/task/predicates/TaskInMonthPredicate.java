package profplan.model.task.predicates;

import java.util.function.Predicate;

import profplan.model.task.Task;

/**
 * Tests that a {@code Task}'s {@code DueDate} falls within a month of the current date.
 */
public class TaskInMonthPredicate implements Predicate<Task> {

    @Override
    public boolean test(Task task) {
        return task.getDueDate().isWithinMonth();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return (other instanceof TaskInMonthPredicate);
    }
}

