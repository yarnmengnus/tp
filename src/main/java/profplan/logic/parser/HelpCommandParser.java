package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import profplan.logic.HelperCommandUtil;
import profplan.logic.commands.HelpCommand;
import profplan.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) throws ParseException {
        HelperCommandUtil util = new HelperCommandUtil();
        try {
            if (args.equals(null)) {
                return new HelpCommand(util);
            }
            String command = args.strip();
            if (command.equals("")) {
                return new HelpCommand(util);
            }
            if (util.isValidCommand(command)) {
                return new HelpCommand(util, command);
            } else {
                throw new ParseException(args);
            }
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE
                        + HelpCommand.MESSAGE_DETAILS + HelpCommand.MESSAGE_EXAMPLE));
        }
    }
}
