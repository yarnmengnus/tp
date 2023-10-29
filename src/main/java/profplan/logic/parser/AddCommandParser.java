package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static profplan.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static profplan.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static profplan.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static profplan.logic.parser.CliSyntax.PREFIX_EMAIL;
import static profplan.logic.parser.CliSyntax.PREFIX_LINK;
import static profplan.logic.parser.CliSyntax.PREFIX_NAME;
import static profplan.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static profplan.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import profplan.logic.commands.AddCommand;
import profplan.logic.parser.exceptions.ParseException;
import profplan.model.tag.Tag;
import profplan.model.task.Address;
import profplan.model.task.Description;
import profplan.model.task.DueDate;
import profplan.model.task.Email;
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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRIORITY, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_DUEDATE, PREFIX_LINK, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE
                + AddCommand.MESSAGE_DETAILS + AddCommand.MESSAGE_EXAMPLE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PRIORITY, PREFIX_EMAIL, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Priority priority = (argMultimap.getValue(PREFIX_PRIORITY) == Optional.<String>empty()) ? new Priority("000")
                : ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
        Email email = (argMultimap.getValue(PREFIX_EMAIL) == Optional.<String>empty()) ? new Email("null@null")
                : ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = (argMultimap.getValue(PREFIX_ADDRESS) == Optional.<String>empty()) ? new Address("000")
                : ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        DueDate dueDate = ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_DUEDATE).orElse("No due date"));
        Link link = ParserUtil.parseLink(argMultimap.getValue(PREFIX_LINK).orElse("-"));

        Task task = new Task(name, priority, email, address, tagList, dueDate, new HashSet<>(),
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
