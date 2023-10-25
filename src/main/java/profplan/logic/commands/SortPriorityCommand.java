package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;

/**
 * Adds a task to the address book.
 */
public class SortPriorityCommand extends Command {

    public static final String COMMAND_WORD = "sort_priority";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the tasks based on priority. "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = " Here is your task list Prof!, sorted based on priority";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortTaskByPriority();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
