package profplan.logic.parser;

import static java.util.Objects.requireNonNull;
import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import profplan.commons.core.Settings;
import profplan.logic.commands.EditSettingsCommand;
import profplan.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditSettingsCommand object
 */
public class EditSettingsCommandParser implements Parser<EditSettingsCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public EditSettingsCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        Prefix[] keywords = Settings.KEYWORDS;
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, keywords);

        if (!isPrefixPresent(argumentMultimap, keywords) || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditSettingsCommand.MESSAGE_FULL_HELP));
        }

        EditSettingsCommand.EditSettingsDescriptor editSettingsDescriptor =
                new EditSettingsCommand.EditSettingsDescriptor();

        // semesterDays
        if (argumentMultimap.getValue(keywords[0]).isPresent()) {
            editSettingsDescriptor.setSemesterDays(ParserUtil
                    .parseSemesterDays(argumentMultimap.getValue(keywords[0]).get()));
        }

        return new EditSettingsCommand(editSettingsDescriptor);
    }

    /**
     * Returns true if at least one of the prefixes does not contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
