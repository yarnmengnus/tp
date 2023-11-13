package profplan.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import profplan.model.ProfPlan;
import profplan.model.ReadOnlyProfPlan;
import profplan.model.tag.Tag;
import profplan.model.task.Description;
import profplan.model.task.DueDate;
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
            new Task(new Name("3D Chip Printing Research"), new Priority("8"), false, null,
                    getTagSet("research", "application"), new DueDate("01-01-2024"),
                    new Link("https://nus.edu.sg/nuslibraries/spaces/tel-imaginarium/services/3d-printing"),
                    new Description("Test blueprints and apply for 3D Printing machine access.")),
            new Task(new Name("Review Student Project Proposal"), new Priority("5"), false, null,
                    getTagSet("student", "research"), new DueDate("30-11-2023"),
                    new Link("urop.com"), new Description("Review Raman's UROP Proposal.")),
            new Task(new Name("CS2100 Lecture"), new Priority("7"), true, Task.RecurringType.WEEKLY,
                    getTagSet("course", "lecture"), new DueDate("21-11-2023"),
                    new Link("https://nus-sg.zoom.us/j/84615636187?pwd=RWRHc1hiTDlUaU54dUIrRVFiT2l2Zz09"),
                    new Description("9AM Lecture in COM1-Seminar Room")),
            new Task(new Name("Read Neural Network Paper"), new Priority("6"), false, null,
                    getTagSet("reading", "research"), new DueDate("25-11-2023"),
                    new Link("arivx.com/12366"), new Description("Read and review newly released paper on CNNs")),
            new Task(new Name("CS2106 Course Admin"), new Priority("5"), true, Task.RecurringType.WEEKLY,
                    getTagSet("course", "admin"), new DueDate("10-12-2023"),
                    new Link("https://blog.nus.edu.sg/cs2106/about/"),
                    new Description("Release Quiz 9 and Midterm Grades")),
            new Task(new Name("Grant Application"), new Priority("8"), false, null,
                    getTagSet("grant", "research"), new DueDate("20-07-2024"),
                    new Link("-"), new Description("NUS SOC Grant Application for DLD Project"))
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
