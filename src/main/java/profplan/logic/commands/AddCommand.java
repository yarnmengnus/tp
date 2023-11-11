package profplan.logic.commands;

import static java.util.Objects.requireNonNull;
import static profplan.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static profplan.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static profplan.logic.parser.CliSyntax.PREFIX_LINK;
import static profplan.logic.parser.CliSyntax.PREFIX_NAME;
import static profplan.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static profplan.logic.parser.CliSyntax.PREFIX_RECURRING;
import static profplan.logic.parser.CliSyntax.PREFIX_TAG;

import profplan.commons.util.ToStringBuilder;
import profplan.logic.Messages;
import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;
import profplan.model.task.Task;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list.";
    public static final String MESSAGE_DETAILS = "Compulsory Parameters: "
            + PREFIX_NAME + "[name] "
            + PREFIX_PRIORITY + "[priority] "
            + PREFIX_DUEDATE + "[dueDate] "
            + "Optional Parameters:\n"
            + PREFIX_RECURRING + "[recur] "
            + PREFIX_TAG + "[tag...] "
            + PREFIX_LINK + "[link] "
            + PREFIX_DESCRIPTION + "[description]";
    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Grade assignments "
            + PREFIX_PRIORITY + "1 "
            + PREFIX_TAG + "assignment "
            + PREFIX_TAG + "grade "
            + PREFIX_DUEDATE + "01-01-2023 "
            + PREFIX_LINK + "www.example.com";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public AddCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
