package profplan.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static profplan.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import profplan.model.ProfPlan;
import profplan.testutil.Assert;
import profplan.testutil.TypicalPersons;
import profplan.commons.exceptions.IllegalValueException;
import profplan.commons.util.JsonUtil;

public class JsonSerializableProfPlanTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsProfPlan.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonProfPlan.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonProfPlan.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableProfPlan dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableProfPlan.class).get();
        ProfPlan profPlanFromFile = dataFromFile.toModelType();
        ProfPlan typicalPersonsProfPlan = TypicalPersons.getTypicalProfPlan();
        assertEquals(profPlanFromFile, typicalPersonsProfPlan);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableProfPlan dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableProfPlan.class).get();
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableProfPlan dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableProfPlan.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableProfPlan.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
