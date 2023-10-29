package profplan.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import profplan.model.task.predicates.CombinedPredicate;
import profplan.model.task.predicates.TaskDueDatePredicate;
import profplan.model.task.predicates.TaskInMonthPredicate;
import profplan.model.task.predicates.TaskPriorityPredicate;
import profplan.model.task.predicates.TaskStatusPredicate;
import profplan.testutil.TaskBuilder;

public class CombinedPredicateTest {
    @Test
    public void equals() {
        ArrayList<Predicate<Task>> preds = new ArrayList<>();
        preds.add(new TaskStatusPredicate(new Status("done")));
        preds.add(new TaskPriorityPredicate(new Priority("3")));

        CombinedPredicate one = new CombinedPredicate(preds);
        CombinedPredicate two = new CombinedPredicate(preds);
        assertTrue(one.equals(one));
        assertTrue(one.equals(two));

        ArrayList<Predicate<Task>> preds2 = new ArrayList<>();
        preds2.add(new TaskStatusPredicate(new Status("done")));

        CombinedPredicate not = new CombinedPredicate(preds2);
        assertFalse(one.equals(not));
        assertFalse(one.equals(new TaskInMonthPredicate()));

        assertTrue(one.toString().equals(two.toString()));
    }

    @Test
    public void test() {
        ArrayList<Predicate<Task>> preds = new ArrayList<>();
        preds.add(new TaskDueDatePredicate(new DueDate("01-01-2000")));
        preds.add(new TaskPriorityPredicate(new Priority("3")));

        CombinedPredicate predicate = new CombinedPredicate(preds);
        assertFalse(predicate.test(new TaskBuilder().withName("Alice").build()));
    }
}
