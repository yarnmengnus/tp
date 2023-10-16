package profplan.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import profplan.commons.exceptions.IllegalValueException;
import profplan.commons.util.JsonUtil;
import profplan.model.ProfPlan;
import profplan.testutil.Assert;
import profplan.testutil.TypicalTasks;

public class JsonSerializableProfPlanTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableProfPlanTest");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTasksProfPlan.json");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskProfPlan.json");
    private static final Path DUPLICATE_TASK_FILE = TEST_DATA_FOLDER.resolve("duplicateTaskProfPlan.json");

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        JsonSerializableProfPlan dataFromFile = JsonUtil.readJsonFile(TYPICAL_TASKS_FILE,
                JsonSerializableProfPlan.class).get();
        ProfPlan profPlanFromFile = dataFromFile.toModelType();
        ProfPlan typicalTasksProfPlan = TypicalTasks.getTypicalProfPlan();
        assertEquals(profPlanFromFile, typicalTasksProfPlan);
    }

    @Test
    public void toModelType_invalidTaskFile_throwsIllegalValueException() throws Exception {
        JsonSerializableProfPlan dataFromFile = JsonUtil.readJsonFile(INVALID_TASK_FILE,
                JsonSerializableProfPlan.class).get();
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTasks_throwsIllegalValueException() throws Exception {
        JsonSerializableProfPlan dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TASK_FILE,
                JsonSerializableProfPlan.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableProfPlan.MESSAGE_DUPLICATE_TASK,
                dataFromFile::toModelType);
    }

}
