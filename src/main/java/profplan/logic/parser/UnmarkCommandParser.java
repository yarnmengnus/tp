package profplan.logic.parser;

import profplan.logic.commands.UnmarkCommand;
import profplan.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class UnmarkCommandParser implements Parser<UnmarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkCommand parse(String args) throws ParseException {
        try {
            int number = Integer.parseInt(args.strip());
            if (number <= 0) {
                throw new ParseException(UnmarkCommand.MESSAGE_INVALID_NUMBER);
            }
            return new UnmarkCommand(number);
        } catch (NumberFormatException e) {
            throw new ParseException(UnmarkCommand.MESSAGE_FULL_HELP);
        }
    }
}
