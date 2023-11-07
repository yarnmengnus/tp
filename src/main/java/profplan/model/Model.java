package profplan.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import profplan.commons.core.GuiSettings;
import profplan.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' task list file path.
     */
    Path getProfPlanFilePath();

    /**
     * Sets the user prefs' task list file path.
     */
    void setProfPlanFilePath(Path profPlanFilePath);

    /**
     * Replaces task list data with the data in {@code profPlan}.
     */
    void setProfPlan(ReadOnlyProfPlan profPlan);

    /** Returns the ProfPlan */
    ReadOnlyProfPlan getProfPlan();

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task list.
     */
    boolean hasTask(Task task);

    /**
     * Deletes the given task.
     * The task must exist in the task list.
     */
    void deleteTask(Task target);

    /**
     * Deletes all the tasks present in the list.
     */
    void deleteTask();


    /**
     * Adds the given task.
     * {@code task} must not already exist in the task list.
     */
    void addTask(Task task);

    /**
     * Marks the task at given index as done.
     * {@code index} must be in range.
     */
    void markTask(int index);


    /**
     * Marks the task at given index as undone.
     * {@code index} must be in range.
     */
    void unmarkTask(int index);


    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the task list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the task list.
     */
    void setTask(Task target, Task editedTask);

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Returns the task to do next based on the formula: priority/#daysToDueDate.
     */
    Task getDoNextTask();

    /**
     * Sorts the task by nearest duedate.
     */
    void sortTaskByDueDate();

    /**
     * Sorts the task based on priority.
     */
    void sortTaskByPriority();

    /**
     * Gets completion rate of existing tasks.
     */
    double getCompletionRate();
}
