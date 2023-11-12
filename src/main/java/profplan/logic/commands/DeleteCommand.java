package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import profplan.commons.core.index.Index;
import profplan.commons.util.ToStringBuilder;
import profplan.logic.Messages;
import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;
import profplan.model.task.Task;

/**
 * Deletes a task identified using its displayed index from the task list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the displayed task list.";
    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DETAILS = "Parameters: INDEX (must be a positive integer)\n"
            + "Tip: Use \"delete all\" to delete all tasks. "
            + "Be careful, this is IRREVERSIBLE.";
    public static final String MESSAGE_FULL_HELP = MESSAGE_USAGE + "\n" + MESSAGE_DETAILS + "\n" + MESSAGE_EXAMPLE;

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public static final String MESSAGE_DELETE_ALL_TASKS_SUCCESS = "All Tasks Deleted Successfully Prof!";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public DeleteCommand() {
        this.targetIndex = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex == null && lastShownList.size() != 0) {
            model.deleteTask();
            return new CommandResult(MESSAGE_DELETE_ALL_TASKS_SUCCESS);
        }

        if (targetIndex == null && lastShownList.size() == 0) {
            return new CommandResult("Can not delete all tasks in empty Task List");
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTask(taskToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, Messages.format(taskToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        if (targetIndex == null && otherDeleteCommand.targetIndex == null) {
            return true;
        } else if (targetIndex != null && otherDeleteCommand.targetIndex != null) {
            return targetIndex.equals(otherDeleteCommand.targetIndex);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
