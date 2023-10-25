package profplan.commons.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import profplan.model.task.DueDate;
import profplan.model.task.Task;
import profplan.model.util.SampleDataUtil;

public class SampleDataUtilTest {
    @Test
    public void sampleDataUtil_test() {
        Task[] tasks = SampleDataUtil.getSampleTasks();
        for (Task task : tasks) {
            assertTrue(DueDate.isValidDate(task.getDueDate().value));
        }
    }
}
