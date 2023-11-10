package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;
import profplan.model.task.Task;

/**
 * Recommends the prof. which task to do next, based on the priority as well as urgency.
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
        if (t == null) {
            return new CommandResult("No more tasks to do Prof! Skies are clear ahead :)");
        } else {
            return new CommandResult(MESSAGE_SUCCESS + "\n" + t.beautifyString());
        }
    }
}
