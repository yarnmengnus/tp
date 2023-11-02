package profplan.logic.commands;

import static profplan.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import profplan.commons.core.index.Index;
import profplan.logic.Messages;
import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;
import profplan.model.task.Description;
import profplan.model.task.Task;

/**
 * Adds a description to a Task.
 */
public class DescriptionCommand extends Command {

    public static final String COMMAND_WORD = "description";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the description of the task identified "
            + "by the index number used in the last task listing.";
    public static final String MESSAGE_DETAILS = "Existing description will be overwritten by the input.\n"
            + "Parameters: [index] (must be a positive integer) "
            + "des/ [description]\n";
    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + " 1 "
            + "des/ Put particular emphasis on recursion.";

    public static final String MESSAGE_ADD_DESCRIPTION_SUCCESS = "Added description to Task %d";
    public static final String MESSAGE_DELETE_DESCRIPTION_SUCCESS = "Removed description from Task: %d";

    private final Index index;
    private final Description description;

    /**
     * @param index The index of the task to attach the description to.
     * @param description The description to be added to the task.
     */
    public DescriptionCommand(Index index, Description description) {
        requireAllNonNull(index, description);

        this.index = index;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = new Task(taskToEdit.getName(), taskToEdit.getPriority(),
                taskToEdit.getIsRecurring(), taskToEdit.getRecurringType(),
                taskToEdit.getTags(), taskToEdit.getDueDate(),
                taskToEdit.getLink(), description);

        model.setTask(taskToEdit, editedTask);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);

        return new CommandResult(generateSuccessMessage(index.getOneBased()));
    }

    /**
     * Generates a command execution success message based on whether
     * the description is added to or removed from {@code taskToEdit}.
     */
    private String generateSuccessMessage(int index) {
        String message = !description.description.isEmpty() ? MESSAGE_ADD_DESCRIPTION_SUCCESS
                : MESSAGE_DELETE_DESCRIPTION_SUCCESS;
        return String.format(message, index);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DescriptionCommand)) {
            return false;
        }

        DescriptionCommand descriptionCommand = (DescriptionCommand) other;
        return index.equals(descriptionCommand.index) && description.equals(descriptionCommand.description);
    }
}
