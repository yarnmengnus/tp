package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static profplan.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static profplan.logic.parser.CliSyntax.PREFIX_EMAIL;
import static profplan.logic.parser.CliSyntax.PREFIX_NAME;
import static profplan.logic.parser.CliSyntax.PREFIX_PHONE;
import static profplan.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import profplan.logic.commands.AddCommand;
import profplan.logic.parser.exceptions.ParseException;
import profplan.model.tag.Tag;
import profplan.model.task.Address;
import profplan.model.task.DueDate;
import profplan.model.task.Email;
import profplan.model.task.Name;
import profplan.model.task.Phone;
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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = (argMultimap.getValue(PREFIX_PHONE) == Optional.<String>empty()) ? new Phone("000")
                : ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = (argMultimap.getValue(PREFIX_EMAIL) == Optional.<String>empty()) ? new Email("null@null")
                : ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = (argMultimap.getValue(PREFIX_ADDRESS) == Optional.<String>empty()) ? new Address("000")
                : ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        DueDate dueDate = ParserUtil.parseDueDate("01-01-2000"); // TO CHANGE

        Task task = new Task(name, phone, email, address, tagList, dueDate);

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
