package profplan.logic.parser;

import static java.util.Objects.requireNonNull;
import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import profplan.commons.core.index.Index;
import profplan.commons.exceptions.IllegalValueException;
import profplan.logic.commands.SetCommand;
import profplan.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and returns a SetCommand object
 */
public class SetCommandParser implements Parser<SetCommand> {
    @Override
    public SetCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        Index parentIndex;
        Index childIndex;
        try {
            String[] splitInput = userInput.trim().split(" ");
            parentIndex = ParserUtil.parseIndex(splitInput[0]);
            childIndex = ParserUtil.parseIndex(splitInput[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE
                + SetCommand.MESSAGE_DETAILS + SetCommand.MESSAGE_EXAMPLE),
                    ive);
        }
        return new SetCommand(parentIndex, childIndex);
    }
}
