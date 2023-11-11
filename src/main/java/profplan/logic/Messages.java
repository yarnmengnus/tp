package profplan.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import profplan.logic.commands.AddCommand;
import profplan.logic.parser.Prefix;
import profplan.model.task.Task;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";

    public static final String MESSAGE_ADD_COMMAND_INVALID = "Invalid command format! \n"
            + "add: Adds a task to the task list.\n"
            + "Compulsory Parameters: n/[name] p/[priority] d/[dueDate] \n"
            + "Optional Parameters: recur/[recur] t/[tag...] l/[link] des/[description]\n"
            + "Example: add n/Grade assignments p/1 t/assignment t/grade d/01-01-2023 l/www.example.com";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_COMMAND_WORD = "The command word specified is not a command";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_FULL_HELP);
    }

    /**
     * Formats the {@code task} for display to the user.
     */
    public static String format(Task task) {
        final StringBuilder builder = new StringBuilder();
        builder.append(task.getName())
                .append("; Priority: ")
                .append(task.getPriority())
                .append("; Status: ")
                .append(task.getStatus())
                .append("; Tags: ");
        task.getTags().forEach(builder::append);
        builder.append("; DueDate: ")
                .append(task.getDueDate())
                .append("; Link: ")
                .append(task.getLink());
        return builder.toString();
    }

}
