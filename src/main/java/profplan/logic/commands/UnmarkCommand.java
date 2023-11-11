package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.commons.util.ToStringBuilder;
import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;

/**
 * Marks Status of task as undone.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a task as undone.";
    public static final String MESSAGE_DETAILS = "Parameters: "
            + "[index] ";

    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_FULL_HELP = MESSAGE_USAGE + "\n" + MESSAGE_DETAILS + "\n" + MESSAGE_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Task successfully marked as undone, Prof! "
                                                    + "Here is your updated task list";

    public static final String MESSAGE_INVALID_NUMBER = "INDEX should be greater than or equal to 1";
    public static final String MESSAGE_ALREADY_UNDONE = "This task is already marked as undone";

    private final int taskNumber;

    /**
     * It is a constructor which assigns the number of the task which is to be unmarked
     * to the tasknumber variable.
     * @param number
     */
    public UnmarkCommand(int number) {
        assert number > 0;
        taskNumber = number;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (taskNumber > model.getFilteredTaskList().size()) {
            throw new CommandException("Task not found please enter a valid Task Number.");
        }
        model.unmarkTask(taskNumber - 1);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkCommand)) {
            return false;
        }

        UnmarkCommand otherMarkCommand = (UnmarkCommand) other;
        return taskNumber == otherMarkCommand.taskNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("taskNumber", taskNumber)
                .toString();
    }
}
