package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;

/**
 * Adds a task to the address book.
 */
public class SortDueDateCommand extends Command {

    public static final String COMMAND_WORD = "sort_duedate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the task by due date.";

    public static final String MESSAGE_SUCCESS = " Here is your task list Prof, sorted by nearest due date.";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortTaskByDeadline();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
