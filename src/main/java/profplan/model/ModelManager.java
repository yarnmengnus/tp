package profplan.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import profplan.commons.core.GuiSettings;
import profplan.commons.core.LogsCenter;
import profplan.commons.util.CollectionUtil;
import profplan.model.task.Status;
import profplan.model.task.Task;

/**
 * Represents the in-memory model of the task list data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ProfPlan profPlan;
    private final UserPrefs userPrefs;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyProfPlan addressBook, ReadOnlyUserPrefs userPrefs) {
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with task list: " + addressBook + " and user prefs " + userPrefs);

        this.profPlan = new ProfPlan(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTasks = new FilteredList<>(this.profPlan.getTaskList());
    }

    public ModelManager() {
        this(new ProfPlan(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getProfPlanFilePath() {
        return userPrefs.getProfPlanFilePath();
    }

    @Override
    public void setProfPlanFilePath(Path profPlanFilePath) {
        requireNonNull(profPlanFilePath);
        userPrefs.setProfPlanFilePath(profPlanFilePath);
    }

    //=========== ProfPlan ================================================================================

    @Override
    public void setProfPlan(ReadOnlyProfPlan profPlan) {
        this.profPlan.resetData(profPlan);
    }

    @Override
    public ReadOnlyProfPlan getProfPlan() {
        return profPlan;
    }

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return profPlan.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        profPlan.removeTask(target);
    }

    @Override
    public void deleteTask() {
        profPlan.removeTask();
    }

    @Override
    public void addTask(Task task) {
        profPlan.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void markTask(int index) {
        Task temp = profPlan.getTaskList().get(index);
        temp.setStatus(Status.DONE_STATUS);
        profPlan.setTask(profPlan.getTaskList().get(index), temp);
    }

    @Override
    public void unmarkTask(int index) {
        Task temp = profPlan.getTaskList().get(index);
        temp.setStatus(Status.UNDONE_STATUS);
        profPlan.setTask(profPlan.getTaskList().get(index), temp);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        CollectionUtil.requireAllNonNull(target, editedTask);
        profPlan.setTask(target, editedTask);
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    @Override
    public void sortTaskByDeadline() {
        profPlan.setTasks(profPlan.getTaskList().sorted());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return profPlan.equals(otherModelManager.profPlan)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredTasks.equals(otherModelManager.filteredTasks);
    }

}
