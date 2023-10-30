package profplan.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import profplan.commons.exceptions.IllegalValueException;
import profplan.model.tag.Tag;
import profplan.model.task.Description;
import profplan.model.task.DueDate;
import profplan.model.task.Link;
import profplan.model.task.Name;
import profplan.model.task.Priority;
import profplan.model.task.Status;
import profplan.model.task.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String name;
    private final String priority;
    private final boolean isRecurring;
    private final Task.RecurringType recurringType;
    private final String dueDate;
    private final String status;
    private final String link;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedTask> children = new ArrayList<JsonAdaptedTask>();
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("name") String name, @JsonProperty("priority") String priority,
            @JsonProperty("isRecurring") boolean isRecurring,
            @JsonProperty("recurringType") Task.RecurringType recurringType,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("status") String status, @JsonProperty("dueDate") String dueDate,
            @JsonProperty("children") List<JsonAdaptedTask> children, @JsonProperty("link") String link,
            @JsonProperty("description") String description) {
        this.name = name;
        this.priority = priority;
        this.isRecurring = isRecurring;
        this.recurringType = recurringType;
        this.dueDate = dueDate;
        this.status = status;
        this.link = link;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (children != null) {
            this.children.addAll(children);
        }
        this.description = description;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        name = source.getName().fullName;
        priority = source.getPriority().value;
        isRecurring = source.getIsRecurring();
        recurringType = source.getRecurringType();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        link = source.getLink().value;
        children.addAll(source.getChildren().stream()
                .map(JsonAdaptedTask::new)
                .collect(Collectors.toList()));
        dueDate = source.getDueDate().value;
        status = source.getStatus().status;
        description = source.getDescription().description;
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            taskTags.add(tag.toModelType());
        }

        final List<Task> taskChildren = new ArrayList<>();
        for (JsonAdaptedTask child : children) {
            taskChildren.add(child.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(priority)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        final Priority modelPriority = new Priority(priority);

        final boolean modelIsRecurring = isRecurring;

        final Task.RecurringType modelRecurringType = recurringType;

        final Set<Tag> modelTags = new HashSet<>(taskTags);

        String linkToLoad = link;
        if (link == null) {
            linkToLoad = "-";
        }

        final Link modelLink = new Link(linkToLoad);

        final Set<Task> modelChildren = new HashSet<>(taskChildren);

        if (dueDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DueDate.class.getSimpleName()));
        }
        if (!DueDate.isValidDate(dueDate)) {
            throw new IllegalValueException(DueDate.MESSAGE_CONSTRAINTS);
        }
        final DueDate modelDueDate = new DueDate(dueDate);

        Status modelStatus = Status.UNDONE_STATUS;
        if (status != null) {
            modelStatus = new Status(status);
        }

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final Description modelDescription = new Description(description);

        return new Task(modelName, modelPriority, modelIsRecurring, modelRecurringType,
                modelStatus, modelTags, modelDueDate,
                modelChildren, modelLink, modelDescription);
    }

}
