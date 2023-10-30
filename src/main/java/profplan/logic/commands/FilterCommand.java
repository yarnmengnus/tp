package profplan.logic.commands;

import static java.util.Objects.requireNonNull;
import static profplan.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static profplan.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static profplan.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.function.Predicate;

import profplan.commons.util.ToStringBuilder;
import profplan.model.Model;
import profplan.model.task.Task;


/**
 * Filters for all tasks in task list whose due date falls before the argument due date.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters for tasks with one criteria and displays them as a list with index numbers.\n";

    public static final String MESSAGE_DETAILS = "Format: \n"
            + COMMAND_WORD + " "
            + "[" + PREFIX_STATUS + "STATUS]" + " (done/undone)\n"
            + "[" + PREFIX_DUEDATE + "DUEDATE]" + " (dd-MM-yyyy)\n"
            + "[" + PREFIX_PRIORITY + "PRIORITY]" + " (integer between 1 and 10)\n"
            + "And displays them as a list with index numbers.\n";

    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + " d/01-01-2024";

    private final Predicate<?> predicate;

    private String messageSuccess = "Here are your tasks that are:\n";

    /**
     * Initialise FilterCommand object
     */
    public FilterCommand(Predicate<Task> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);

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
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

    public String getSuccessMessage() {
        return messageSuccess;
    }

    public void setSuccessMessage(String newMsg) {
        messageSuccess = newMsg;
    }
}
