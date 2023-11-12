package profplan.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static profplan.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static profplan.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static profplan.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import profplan.model.task.Task.RecurringType;
import profplan.testutil.Assert;
import profplan.testutil.TaskBuilder;
import profplan.testutil.TypicalTasks;

public class TaskTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Task task = new TaskBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> task.getTags().remove(0));
    }

    @Test
    public void isSameTask() {
        // same object -> returns true
        assertTrue(TypicalTasks.ALICE.isSameTask(TypicalTasks.ALICE));

        // null -> returns false
        assertFalse(TypicalTasks.ALICE.isSameTask(null));

        // same name, all other attributes different -> returns true
        Task editedAlice = new TaskBuilder(TypicalTasks.ALICE).withPriority(VALID_PRIORITY_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(TypicalTasks.ALICE.isSameTask(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new TaskBuilder(TypicalTasks.ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(TypicalTasks.ALICE.isSameTask(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Task editedBob = new TaskBuilder(TypicalTasks.BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(TypicalTasks.BOB.isSameTask(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new TaskBuilder(TypicalTasks.BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(TypicalTasks.BOB.isSameTask(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task aliceCopy = new TaskBuilder(TypicalTasks.ALICE).build();
        assertTrue(TypicalTasks.ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(TypicalTasks.ALICE.equals(TypicalTasks.ALICE));

        // null -> returns false
        assertFalse(TypicalTasks.ALICE.equals(null));

        // different type -> returns false
        assertFalse(TypicalTasks.ALICE.equals(5));

        // different task -> returns false
        assertFalse(TypicalTasks.ALICE.equals(TypicalTasks.BOB));

        // different name -> returns false
        Task editedAlice = new TaskBuilder(TypicalTasks.ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(TypicalTasks.ALICE.equals(editedAlice));

        // different priority -> returns false
        editedAlice = new TaskBuilder(TypicalTasks.ALICE).withPriority(VALID_PRIORITY_BOB).build();
        assertFalse(TypicalTasks.ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new TaskBuilder(TypicalTasks.ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(TypicalTasks.ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Task.class.getCanonicalName() + "{name=" + TypicalTasks.ALICE.getName() + ", priority="
                + TypicalTasks.ALICE.getPriority()
                + ", status=" + TypicalTasks.ALICE.getStatus()
                + ", tags=" + TypicalTasks.ALICE.getTags()
                + ", dueDate=" + TypicalTasks.ALICE.getDueDate()
                + ", link=" + TypicalTasks.ALICE.getLink()
                + ", description=" + TypicalTasks.ALICE.getDescription()
                + "}";
        assertEquals(expected, TypicalTasks.ALICE.toString());
    }

    @Test
    public void recurringTask() {
        Task daily = new Task(new Name("daily"), new Priority("3"),
                true, RecurringType.DAILY, new HashSet<>(),
                new DueDate("01-01-2023"), new Link("-"), null);

        daily.setStatus(Status.DONE_STATUS);
        assertEquals(daily.getDueDate(), new DueDate("02-01-2023"));


        Task weekly = new Task(new Name("weekly"), new Priority("3"),
                true, RecurringType.WEEKLY, new HashSet<>(),
                new DueDate("01-01-2023"), new Link("-"), null);

        weekly.setStatus(Status.DONE_STATUS);
        assertEquals(weekly.getDueDate(), new DueDate("08-01-2023"));

        Task monthly = new Task(new Name("monthly"), new Priority("3"),
                true, RecurringType.MONTHLY, new HashSet<>(),
                new DueDate("01-01-2023"), new Link("-"), null);

        monthly.setStatus(Status.DONE_STATUS);
        assertEquals(monthly.getDueDate(), new DueDate("01-02-2023"));

        Task semesterly = new Task(new Name("semly"), new Priority("3"),
                true, RecurringType.SEMESTERLY, new HashSet<>(),
                new DueDate("01-01-2023"), new Link("-"), null);

        semesterly.setStatus(Status.DONE_STATUS);
        assertEquals(semesterly.getDueDate(), new DueDate("30-06-2023"));
    }
}
