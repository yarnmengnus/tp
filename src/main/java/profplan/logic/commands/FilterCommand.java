package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.commons.util.ToStringBuilder;
import profplan.model.Model;
import profplan.model.task.TasksDueBeforeDatePredicate;

/**
 * Finds and lists all tasks in task list whose due date falls before the argument due date.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters for all tasks before "
            + "the duedate given and displays them as a list with index numbers.\n"
            + "Parameters: [DUEDATE]\n"
            + "Example: " + COMMAND_WORD + " 01-01-2023";

    private final TasksDueBeforeDatePredicate datePredicate;

    public final String MESSAGE_SUCCESS;

    public FilterCommand(TasksDueBeforeDatePredicate datePredicate) {
        this.datePredicate = datePredicate;
        this.MESSAGE_SUCCESS = "Here are your tasks before " + datePredicate.getDate();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(datePredicate);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherCommand = (FilterCommand) other;
        return datePredicate.equals(otherCommand.datePredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("date predicate", datePredicate)
                .toString();
    }
}
