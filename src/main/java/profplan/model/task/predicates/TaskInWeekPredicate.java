package profplan.model.task.predicates;

import java.util.function.Predicate;

import profplan.model.task.Task;

/**
 * Tests that a {@code Task}'s {@code DueDate} falls within a week of the current date.
 */
public class TaskInWeekPredicate implements Predicate<Task> {

    @Override
    public boolean test(Task task) {
        return task.getDueDate().isWithinWeek();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return (other instanceof TaskInWeekPredicate);
    }
}
