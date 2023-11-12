package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.model.Model;

/**
 * Lists all tasks in the task list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = "Lists all tasks.";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_FULL_HELP = MESSAGE_USAGE;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
