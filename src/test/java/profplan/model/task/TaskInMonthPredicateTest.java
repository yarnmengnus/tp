package profplan.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import profplan.model.task.predicates.TaskInMonthPredicate;
import profplan.model.task.predicates.TaskInWeekPredicate;

public class TaskInMonthPredicateTest {
    @Test
    public void equals() {
        TaskInMonthPredicate one = new TaskInMonthPredicate();
        TaskInMonthPredicate two = new TaskInMonthPredicate();
        assertTrue(one.equals(two));

        TaskInWeekPredicate not = new TaskInWeekPredicate();
        assertFalse(one.equals(not));
    }
}
