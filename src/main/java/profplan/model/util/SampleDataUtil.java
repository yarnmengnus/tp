package profplan.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import profplan.model.ProfPlan;
import profplan.model.ReadOnlyProfPlan;
import profplan.model.tag.Tag;
import profplan.model.task.Description;
import profplan.model.task.DueDate;
import profplan.model.task.Email;
import profplan.model.task.Link;
import profplan.model.task.Name;
import profplan.model.task.Priority;
import profplan.model.task.Task;

/**
 * Contains utility methods for populating {@code ProfPlan} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Name("Alex Yeoh"), new Priority("4"), new Email("alexyeoh@example.com"),
                    getTagSet("friends"), new DueDate("01-01-2000"), new HashSet<>(), new Link("-"),
                    new Description("")),
            new Task(new Name("Bernice Yu"), new Priority("2"), new Email("berniceyu@example.com"),
                    getTagSet("colleagues", "friends"), new DueDate("01-01-2000"), new HashSet<>(),
                    new Link("-"), new Description("")),
            new Task(new Name("Charlotte Oliveiro"), new Priority("3"), new Email("charlotte@example.com"),
                    getTagSet("neighbours"), new DueDate("01-01-2000"), new HashSet<>(),
                    new Link("-"), new Description("")),
            new Task(new Name("David Li"), new Priority("10"), new Email("lidavid@example.com"),
                    getTagSet("family"), new DueDate("01-01-2000"), new HashSet<>(),
                    new Link("-"), new Description("")),
            new Task(new Name("Irfan Ibrahim"), new Priority("6"), new Email("irfan@example.com"),
                    getTagSet("classmates"), new DueDate("01-01-2000"), new HashSet<>(),
                    new Link("-"), new Description("")),
            new Task(new Name("Roy Balakrishnan"), new Priority("9"), new Email("royb@example.com"),
                    getTagSet("colleagues"), new DueDate("01-01-2000"), new HashSet<>(),
                    new Link("-"), new Description(""))
        };
    }

    public static ReadOnlyProfPlan getSampleProfPlan() {
        ProfPlan sampleAb = new ProfPlan();
        for (Task sampleTask : getSampleTasks()) {
            sampleAb.addTask(sampleTask);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a Task set containing the list of Tasks given.
     */
    public static Set<Task> getTaskSet(Task... tasks) {
        return Arrays.stream(tasks)
                .map(Task::new)
                .collect(Collectors.toSet());
    }

}
