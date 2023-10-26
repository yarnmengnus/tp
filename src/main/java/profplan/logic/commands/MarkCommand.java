package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.commons.util.ToStringBuilder;
import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;

/**
 * Adds a task to the address book.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a task as done. "
            + "Parameters: "
            + "INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 ";

    public static final String MESSAGE_SUCCESS = "Task successfully marked as done, Prof! "
                                                    + "Here is your updated task list";

    public static final String MESSAGE_INVALID_NUMBER = "INDEX should be greater than or equal to 1";
    public static final String MESSAGE_ALREADY_DONE = "This task is already marked as done";

    private final int taskNumber;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public MarkCommand(int number) {
        assert number > 0;
        taskNumber = number;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.markTask(taskNumber - 1);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherMarkCommand = (MarkCommand) other;
        return taskNumber == otherMarkCommand.taskNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("taskNumber", taskNumber)
                .toString();
    }
}
