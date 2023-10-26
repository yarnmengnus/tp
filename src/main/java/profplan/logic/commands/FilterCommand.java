package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.commons.util.ToStringBuilder;
import profplan.model.Model;
import profplan.model.task.DueDate;
import profplan.model.task.TasksDueBeforeDatePredicate;

/**
 * Filters for all tasks in task list whose due date falls before the argument due date.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters for all tasks before and on "
            + "the duedate given and displays them as a list with index numbers.\n"
            + "Parameters: [DUEDATE]\n"
            + "Example: " + COMMAND_WORD + " 01-01-2023\n"
            + DueDate.MESSAGE_CONSTRAINTS;

    public static final String MESSAGE_DETAILS = "";
    private String messageSuccess;

    private final TasksDueBeforeDatePredicate datePredicate;

    /**
     * Initialise FilterCommand object
     */
    public FilterCommand(TasksDueBeforeDatePredicate datePredicate) {
        this.datePredicate = datePredicate;
        this.messageSuccess = "Here are your tasks before " + datePredicate.getDate();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(datePredicate);
        return new CommandResult(
                String.format(messageSuccess, model.getFilteredTaskList().size()));
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
