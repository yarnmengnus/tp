package profplan.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static profplan.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static profplan.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static profplan.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static profplan.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static profplan.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

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
                .withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
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

        // different email -> returns false
        editedAlice = new TaskBuilder(TypicalTasks.ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(TypicalTasks.ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new TaskBuilder(TypicalTasks.ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(TypicalTasks.ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new TaskBuilder(TypicalTasks.ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(TypicalTasks.ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Task.class.getCanonicalName() + "{name=" + TypicalTasks.ALICE.getName() + ", priority="
                + TypicalTasks.ALICE.getPriority()
                + ", email=" + TypicalTasks.ALICE.getEmail() + ", address=" + TypicalTasks.ALICE.getAddress()
                + ", status=" + TypicalTasks.ALICE.getStatus()
                + ", tags=" + TypicalTasks.ALICE.getTags()
                + ", dueDate=" + TypicalTasks.ALICE.getDueDate()
                + ", link=" + TypicalTasks.ALICE.getLink()
                + "}";
        assertEquals(expected, TypicalTasks.ALICE.toString());
    }
}
