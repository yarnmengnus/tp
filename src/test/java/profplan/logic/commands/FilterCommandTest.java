package profplan.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import profplan.model.task.DueDate;
import profplan.model.task.Priority;
import profplan.model.task.Status;
import profplan.model.task.predicates.TaskDueDatePredicate;
import profplan.model.task.predicates.TaskPriorityPredicate;
import profplan.model.task.predicates.TaskStatusPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {

    @Test
    public void equalsDueDate() {
        TaskDueDatePredicate firstPredicate =
                new TaskDueDatePredicate(new DueDate("01-01-2000"));
        TaskDueDatePredicate secondPredicate =
                new TaskDueDatePredicate(new DueDate("02-02-2002"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void equalsPriority() {
        TaskPriorityPredicate firstPredicate =
                new TaskPriorityPredicate(new Priority("2"));
        TaskPriorityPredicate secondPredicate =
                new TaskPriorityPredicate(new Priority("3"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void equalsStatus() {
        TaskStatusPredicate firstPredicate =
                new TaskStatusPredicate(new Status("done"));
        TaskStatusPredicate secondPredicate =
                new TaskStatusPredicate(new Status("undone"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void toStringMethod() {
        TaskDueDatePredicate predicateDueDate = new TaskDueDatePredicate(new DueDate("01-01-2000"));
        FilterCommand filterCommandDueDate = new FilterCommand(predicateDueDate);
        String expectedDueDate = FilterCommand.class.getCanonicalName() + "{predicate=" + predicateDueDate + "}";
        assertEquals(expectedDueDate, filterCommandDueDate.toString());

        TaskPriorityPredicate predicatePriority = new TaskPriorityPredicate(new Priority("1"));
        FilterCommand filterCommandPriority = new FilterCommand(predicatePriority);
        String expectedPriority = FilterCommand.class.getCanonicalName() + "{predicate=" + predicatePriority + "}";
        assertEquals(expectedPriority, filterCommandPriority.toString());

        TaskStatusPredicate predicateStatus = new TaskStatusPredicate(new Status("done"));
        FilterCommand filterCommandStatus = new FilterCommand(predicateStatus);
        String expectedStatus = FilterCommand.class.getCanonicalName() + "{predicate=" + predicateStatus + "}";
        assertEquals(expectedStatus, filterCommandStatus.toString());
    }
}
