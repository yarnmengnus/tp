package profplan.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import profplan.model.task.predicates.TaskInMonthPredicate;
import profplan.model.task.predicates.TaskInWeekPredicate;

public class TaskInWeekPredicateTest {
    @Test
    public void equals() {
        TaskInWeekPredicate one = new TaskInWeekPredicate();
        TaskInWeekPredicate two = new TaskInWeekPredicate();
        assertTrue(one.equals(two));

        TaskInMonthPredicate not = new TaskInMonthPredicate();
        assertFalse(one.equals(not));
    }
}
