package profplan.logic.parser;

import static java.util.Objects.requireNonNull;
import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static profplan.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import profplan.commons.core.index.Index;
import profplan.commons.exceptions.IllegalValueException;
import profplan.logic.commands.DescriptionCommand;
import profplan.logic.parser.exceptions.ParseException;
import profplan.model.task.Description;

/**
 * Parses user input and creates a DescriptionCommand object.
 */
public class DescriptionCommandParser implements Parser<DescriptionCommand> {
    @Override
    public DescriptionCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_DESCRIPTION);

        Index index;
        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (IllegalValueException illegalValueException) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DescriptionCommand.MESSAGE_USAGE), illegalValueException);
        }

        Description description = new Description(argumentMultimap
                .getValue(PREFIX_DESCRIPTION).orElse(""));

        return new DescriptionCommand(index, description);
    }
}
