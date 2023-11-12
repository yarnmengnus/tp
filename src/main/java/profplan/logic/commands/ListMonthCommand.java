package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;
import profplan.model.task.predicates.TaskInMonthPredicate;

/**
 * Lists tasks within a month of today.
 */
public class ListMonthCommand extends Command {

    public static final String COMMAND_WORD = "list_month";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks within a month of today.";
    public static final String MESSAGE_FULL_HELP = MESSAGE_USAGE;

    public static final String MESSAGE_SUCCESS = " Here are your tasks within a month Prof!";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTaskList(new TaskInMonthPredicate());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
