package profplan.testutil;

import static profplan.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static profplan.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static profplan.logic.commands.CommandTestUtil.VALID_PRIORITY_AMY;
import static profplan.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static profplan.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static profplan.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import profplan.model.ProfPlan;
import profplan.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ALICE = new TaskBuilder().withName("Alice Pauline")
            .withPriority("1")
            .withTags("friends").withDueDate("01-01-2000").build();
    public static final Task BENSON = new TaskBuilder().withName("Benson Meier")
            .withPriority("2")
            .withTags("owesMoney", "friends").withDueDate("01-01-2000").build();
    public static final Task CARL = new TaskBuilder().withName("Carl Kurz").withPriority("3")
            .withDueDate("01-01-2000").build();
    public static final Task DANIEL = new TaskBuilder().withName("Daniel Meier").withPriority("4")
            .withTags("friends").withDueDate("01-01-2000").build();
    public static final Task ELLE = new TaskBuilder().withName("Elle Meyer").withPriority("5")
            .withDueDate("01-01-2000").build();
    public static final Task FIONA = new TaskBuilder().withName("Fiona Kunz").withPriority("6")
            .withDueDate("01-01-2000").build();
    public static final Task GEORGE = new TaskBuilder().withName("George Best").withPriority("10")
            .withDueDate("01-01-2000").build();

    // Manually added
    public static final Task HOON = new TaskBuilder().withName("Hoon Meier").withPriority("1")
            .withDueDate("01-01-2000").build();
    public static final Task IDA = new TaskBuilder().withName("Ida Mueller").withPriority("5")
            .withDueDate("01-01-2000").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task AMY = new TaskBuilder().withName(VALID_NAME_AMY).withPriority(VALID_PRIORITY_AMY)
            .withTags(VALID_TAG_FRIEND).withDueDate("10-12-2023").withDescription("").build();
    public static final Task BOB = new TaskBuilder().withName(VALID_NAME_BOB).withPriority(VALID_PRIORITY_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withDueDate("10-12-2023").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static ProfPlan getTypicalProfPlan() {
        ProfPlan pp = new ProfPlan();
        for (Task task : getTypicalTasks()) {
            pp.addTask(task);
        }
        return pp;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
