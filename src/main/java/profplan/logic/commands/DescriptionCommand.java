package profplan.logic.commands;

import static profplan.commons.util.CollectionUtil.requireAllNonNull;

import profplan.commons.core.index.Index;
import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;

/**
 * Adds a description to a Task.
 */
public class DescriptionCommand extends Command {

    public static final String COMMAND_WORD = "description";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the description of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing description will be overwritten by the input.\n"
            + "Parameters: [index] (must be a positive integer) "
            + "des/ [description]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "des/ Put particular emphasis on recursion.";

    private final Index index;
    private final String description;

    /**
     * @param index The index of the task to attach the description to.
     * @param description The description to be added to the task.
     */
    public DescriptionCommand(Index index, String description) {
        requireAllNonNull(index, description);

        this.index = index;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
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
