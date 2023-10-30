package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;
import profplan.model.task.predicates.TaskInWeekPredicate;

/**
 * Lists tasks within a week of today.
 */
public class ListWeekCommand extends Command {

    public static final String COMMAND_WORD = "list_week";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks within this week.";

    public static final String MESSAGE_SUCCESS = " Here are your tasks within a week Prof!";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTaskList(new TaskInWeekPredicate());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
