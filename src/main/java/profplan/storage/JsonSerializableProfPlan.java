package profplan.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import profplan.commons.exceptions.IllegalValueException;
import profplan.model.ProfPlan;
import profplan.model.ReadOnlyProfPlan;
import profplan.model.task.Task;

/**
 * An Immutable ProfPlan that is serializable to JSON format.
 */
@JsonRootName(value = "profplan")
class JsonSerializableProfPlan {

    public static final String MESSAGE_DUPLICATE_TASK = "Tasks list contains duplicate task(s).";

    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableProfPlan} with the given tasks.
     */
    @JsonCreator
    public JsonSerializableProfPlan(@JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.tasks.addAll(tasks);
    }

    /**
     * Converts a given {@code ReadOnlyProfPlan} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableProfPlan}.
     */
    public JsonSerializableProfPlan(ReadOnlyProfPlan source) {
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ProfPlan toModelType() throws IllegalValueException {
        ProfPlan profPlan = new ProfPlan();
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            if (profPlan.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            profPlan.addTask(task);
        }
        return profPlan;
    }

}
