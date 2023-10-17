package profplan.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static profplan.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import profplan.commons.exceptions.IllegalValueException;
import profplan.model.task.Address;
import profplan.model.task.DueDate;
import profplan.model.task.Email;
import profplan.model.task.Name;
import profplan.model.task.Phone;
import profplan.testutil.Assert;
import profplan.testutil.TypicalTasks;

public class JsonAdaptedTaskTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DUEDATE = "31/01/3000";

    private static final String VALID_NAME = TypicalTasks.BENSON.getName().toString();
    private static final String VALID_PHONE = TypicalTasks.BENSON.getPhone().toString();
    private static final String VALID_EMAIL = TypicalTasks.BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = TypicalTasks.BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = TypicalTasks.BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_DUEDATE = TypicalTasks.BENSON.getDueDate().toString();

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        JsonAdaptedTask task = new JsonAdaptedTask(TypicalTasks.BENSON);
        assertEquals(TypicalTasks.BENSON, task.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                                    VALID_ADDRESS, "undone", VALID_TAGS, VALID_DUEDATE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(null, VALID_PHONE, VALID_EMAIL,
            VALID_ADDRESS, "undone", VALID_TAGS, VALID_DUEDATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, "undone", VALID_TAGS, VALID_DUEDATE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, null, VALID_EMAIL,
            VALID_ADDRESS, "undone", VALID_TAGS, VALID_DUEDATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                VALID_ADDRESS, "undone", VALID_TAGS, VALID_DUEDATE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_PHONE, null,
            VALID_ADDRESS, "undone", VALID_TAGS, VALID_DUEDATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, "undone", VALID_TAGS, VALID_DUEDATE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_PHONE, VALID_EMAIL,
            null, "undone", VALID_TAGS, VALID_DUEDATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, "undone", invalidTags, VALID_DUEDATE);
        Assert.assertThrows(IllegalValueException.class, task::toModelType);
    }

    @Test
    public void toModelType_invalidDueDate_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                    VALID_ADDRESS, VALID_TAGS, INVALID_DUEDATE);
        String expectedMessage = DueDate.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

}
