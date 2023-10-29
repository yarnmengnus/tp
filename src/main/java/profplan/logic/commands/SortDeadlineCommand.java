package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;

/**
 * Adds a task to the address book.
 */
public class SortDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "sort_deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the task by the deadline.";
    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + " deadline";

    public static final String MESSAGE_SUCCESS = " Here is your task list Prof!, sorted by nearest deadline";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortTaskByDeadline();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
