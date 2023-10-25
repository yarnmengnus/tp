package profplan.logic.commands;

import java.util.List;
import java.util.Set;

import profplan.commons.core.index.Index;
import profplan.logic.Messages;
import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;
import profplan.model.task.Task;

/**
 * Sets one task as the parent of another.
 */
public class SetCommand extends Command {
    public static final String COMMAND_WORD = "set";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the first task, specified by index in the list,"
            + "as the parent of the second task, also specified by its index in the list.\n"
            + "Parameters: [parentTaskIndex] [childTaskIndex], both positive integers.\n"
            + "Example: set 3 2\n"
            + "sets the task at list index 3 as the parent of the task at list index 2.";
    private final Index parentIndex;
    private final Index childIndex;

    /**
     * @param parentIndex Index of the task that will be the parent in the relationship.
     * @param childIndex Index of the task that will be the child in the relationship.
     */
    public SetCommand(Index parentIndex, Index childIndex) {
        this.parentIndex = parentIndex;
        this.childIndex = childIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        int rebasedParentIndex = parentIndex.getZeroBased();
        int rebasedChildIndex = childIndex.getZeroBased();

        if (rebasedChildIndex >= lastShownList.size() || rebasedParentIndex >= lastShownList.size()
                || rebasedParentIndex == rebasedChildIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task parentTask = lastShownList.get(rebasedParentIndex);
        Task childTask = lastShownList.get(rebasedChildIndex);

        if (childTask.getChildren().contains(parentTask)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Set<Task> addedSet = parentTask.getChildren();
        addedSet.add(childTask);
        Task editedTask = new Task(parentTask.getName(), parentTask.getPriority(), parentTask.getEmail(),
                parentTask.getAddress(), parentTask.getTags(), parentTask.getDueDate(), addedSet, parentTask.getLink());

        model.setTask(parentTask, editedTask);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);

        return new CommandResult("Successfully set relationship.");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetCommand)) {
            return false;
        }

        SetCommand e = (SetCommand) other;
        return parentIndex.equals(e.parentIndex) && childIndex.equals(e.childIndex);
    }
}
