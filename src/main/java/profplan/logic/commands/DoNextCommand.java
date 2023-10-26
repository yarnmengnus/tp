package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;
import profplan.model.task.Task;

/**
 * Adds a task to the address book.
 */
public class DoNextCommand extends Command {

    public static final String COMMAND_WORD = "do_next";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Recommends the next task to do."
            + "Example: " + COMMAND_WORD + "";

    public static final String MESSAGE_SUCCESS = " Here is the next task you need to do Prof:";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Task t = model.getDoNextTask();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + t.beautifyString());
    }
}
