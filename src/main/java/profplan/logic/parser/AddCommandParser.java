package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static profplan.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static profplan.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static profplan.logic.parser.CliSyntax.PREFIX_LINK;
import static profplan.logic.parser.CliSyntax.PREFIX_NAME;
import static profplan.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static profplan.logic.parser.CliSyntax.PREFIX_RECURRING;
import static profplan.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import profplan.logic.commands.AddCommand;
import profplan.logic.parser.exceptions.ParseException;
import profplan.model.tag.Tag;
import profplan.model.task.Description;
import profplan.model.task.DueDate;
import profplan.model.task.Link;
import profplan.model.task.Name;
import profplan.model.task.Priority;
import profplan.model.task.Task;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRIORITY, PREFIX_RECURRING,
                        PREFIX_TAG, PREFIX_DUEDATE, PREFIX_LINK, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PRIORITY, PREFIX_DUEDATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_FULL_HELP));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PRIORITY, PREFIX_RECURRING);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        DueDate dueDate = ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_DUEDATE).orElse("No due date"));
        Link link = ParserUtil.parseLink(argMultimap.getValue(PREFIX_LINK).orElse("-"));
        boolean isRecurring = argMultimap.getValue(PREFIX_RECURRING).isPresent();
        Task.RecurringType recurringType = isRecurring
                ? ParserUtil.parseRecurringType(argMultimap.getValue(PREFIX_RECURRING).get()) : null;

        Task task = new Task(name, priority, isRecurring, recurringType, tagList, dueDate,
                link, new Description(""));
        return new AddCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
