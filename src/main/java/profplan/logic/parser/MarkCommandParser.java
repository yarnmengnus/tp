package profplan.logic.parser;

import profplan.logic.commands.MarkCommand;
import profplan.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class MarkCommandParser implements Parser<MarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCommand parse(String args) throws ParseException {
        try {
            int number = Integer.parseInt(args.strip());
            if (number <= 0) {
                throw new ParseException(MarkCommand.MESSAGE_INVALID_NUMBER);
            }
            return new MarkCommand(number);
        } catch (NumberFormatException e) {
            throw new ParseException(MarkCommand.MESSAGE_USAGE);
        }
    }
}
