package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;

/**
 * Sorts tasklist by Priority.
 */
public class SortPriorityCommand extends Command {

    public static final String COMMAND_WORD = "sort_priority";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the tasks based on priority.";
    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = " Here is your task list Prof, sorted based on priority";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
//            model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
            model.sortTaskByPriority();
        } catch (UnsupportedOperationException e) {
            return new CommandResult(e.getMessage());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
