package profplan.testutil;

import java.util.HashSet;
import java.util.Set;

import profplan.model.tag.Tag;
import profplan.model.task.Description;
import profplan.model.task.DueDate;
import profplan.model.task.Link;
import profplan.model.task.Name;
import profplan.model.task.Priority;
import profplan.model.task.Task;
import profplan.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PRIORITY = "85355255";
    public static final String DEFAULT_DUEDATE = "01-01-2000";
    public static final String DEFAULT_LINK = "-";
    public static final String DEFUALT_DESCRIPTION = "";


    private Name name;
    private Priority priority;
    private Set<Tag> tags;
    private Set<Task> children;
    private DueDate dueDate;
    private Link link;
    private Description description;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        name = new Name(DEFAULT_NAME);
        priority = new Priority(DEFAULT_PRIORITY);
        tags = new HashSet<>();
        children = new HashSet<>();
        dueDate = new DueDate(DEFAULT_DUEDATE);
        link = new Link(DEFAULT_LINK);
        description = new Description(DEFUALT_DESCRIPTION);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        priority = taskToCopy.getPriority();
        tags = new HashSet<>(taskToCopy.getTags());
        children = new HashSet<>(taskToCopy.getChildren());
        dueDate = taskToCopy.getDueDate();
        link = taskToCopy.getLink();
        description = taskToCopy.getDescription();
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code Tasks} into a {@code Set<Task>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withChildren(Task ... children) {
        this.children = SampleDataUtil.getTaskSet(children);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    /**
     * Sets the {@code DueDate} of the {@code Task} that we are building.
     */
    public TaskBuilder withDueDate(String date) {
        this.dueDate = new DueDate(date);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Builds a new task with the given fields.
     */
    public Task build() {
        return new Task(name, priority, tags, dueDate, children,
                link, description);
    }

}
