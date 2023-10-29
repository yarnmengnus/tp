package profplan.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code task} for display to the user.
     */
    public static String format(Task task) {
        final StringBuilder builder = new StringBuilder();
        builder.append(task.getName())
                .append("; Priority: ")
                .append(task.getPriority())
                .append("; Email: ")
                .append(task.getEmail())
                .append("; Address: ")
                .append(task.getAddress())
                .append("; Tags: ");
        task.getTags().forEach(builder::append);
        builder.append("; DueDate: ")
                .append(task.getDueDate())
                .append("; Link: ")
                .append(task.getLink());
        return builder.toString();
    }

}
