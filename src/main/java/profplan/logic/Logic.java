package profplan.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import profplan.commons.core.GuiSettings;
import profplan.logic.commands.CommandResult;
import profplan.logic.commands.exceptions.CommandException;
import profplan.logic.parser.exceptions.ParseException;
import profplan.model.Model;
import profplan.model.ReadOnlyProfPlan;
import profplan.model.task.Task;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see Model#getProfPlan()
     */
    ReadOnlyProfPlan getProfPlan();

    /** Returns an unmodifiable view of the filtered list of tasks */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Returns the user prefs' task list file path.
     */
    Path getProfPlanFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
