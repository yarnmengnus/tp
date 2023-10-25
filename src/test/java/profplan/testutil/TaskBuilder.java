package profplan.testutil;

import java.util.HashSet;
import java.util.Set;

import profplan.model.tag.Tag;
import profplan.model.task.Address;
import profplan.model.task.DueDate;
import profplan.model.task.Email;
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
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DUEDATE = "01-01-2000";
    public static final String DEFAULT_LINK = "-";


    private Name name;
    private Priority priority;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Set<Task> children;
    private DueDate dueDate;
    private Link link;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        name = new Name(DEFAULT_NAME);
        priority = new Priority(DEFAULT_PRIORITY);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        children = new HashSet<>();
        dueDate = new DueDate(DEFAULT_DUEDATE);
        link = new Link(DEFAULT_LINK);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        priority = taskToCopy.getPriority();
        email = taskToCopy.getEmail();
        address = taskToCopy.getAddress();
        tags = new HashSet<>(taskToCopy.getTags());
        children = new HashSet<>(taskToCopy.getChildren());
        dueDate = taskToCopy.getDueDate();
        link = taskToCopy.getLink();
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
     * Sets the {@code Address} of the {@code Task} that we are building.
     */
    public TaskBuilder withAddress(String address) {
        this.address = new Address(address);
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
     * Sets the {@code Email} of the {@code Task} that we are building.
     */
    public TaskBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code DueDate} of the {@code Task} that we are building.
     */
    public TaskBuilder withDueDate(String date) {
        this.dueDate = new DueDate(date);
        return this;
    }

    public Task build() {
        return new Task(name, priority, email, address, tags, dueDate, children, link);
    }

}
