package profplan.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static profplan.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import profplan.commons.exceptions.IllegalValueException;
import profplan.model.task.DueDate;
import profplan.model.task.Name;
import profplan.model.task.Priority;
import profplan.testutil.Assert;
import profplan.testutil.TypicalTasks;

public class JsonAdaptedTaskTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PRIORITY = "+651234";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DUEDATE = "31/01/3000";

    private static final String VALID_NAME = TypicalTasks.BENSON.getName().toString();
    private static final String VALID_PRIORITY = TypicalTasks.BENSON.getPriority().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = TypicalTasks.BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTask> VALID_CHILDREN = TypicalTasks.BENSON.getChildren().stream()
            .map(JsonAdaptedTask::new)
            .collect(Collectors.toList());
    private static final String VALID_DUEDATE = TypicalTasks.BENSON.getDueDate().toString();
    private static final String VALID_DESCRIPTION = TypicalTasks.BENSON.getDescription().toString();


    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        JsonAdaptedTask task = new JsonAdaptedTask(TypicalTasks.BENSON);
        assertEquals(TypicalTasks.BENSON, task.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(INVALID_NAME, VALID_PRIORITY, false, null,
                VALID_TAGS, "undone", VALID_DUEDATE, VALID_CHILDREN,
                null, VALID_DESCRIPTION);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(null, VALID_PRIORITY, false, null,
                VALID_TAGS, "undone", VALID_DUEDATE, VALID_CHILDREN,
                null, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, INVALID_PRIORITY, false, null,
                VALID_TAGS, "undone", VALID_DUEDATE, VALID_CHILDREN,
                null, VALID_DESCRIPTION);
        String expectedMessage = Priority.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test

    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, null, false, null,
                VALID_TAGS, "undone", VALID_DUEDATE, VALID_CHILDREN,
                null, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_PRIORITY, false, null,
                invalidTags, "undone", VALID_DUEDATE, VALID_CHILDREN, null, VALID_DESCRIPTION);
        Assert.assertThrows(IllegalValueException.class, task::toModelType);
    }

    @Test
    public void toModelType_invalidDueDate_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_PRIORITY, false, null,
                VALID_TAGS, "undone", INVALID_DUEDATE, VALID_CHILDREN,
                null, VALID_DESCRIPTION);
        String expectedMessage = DueDate.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

}
