package profplan.model;

import javafx.collections.ObservableList;
import profplan.model.task.Task;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyProfPlan {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();

}
