package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import profplan.logic.commands.FilterCommand;
import profplan.logic.parser.exceptions.ParseException;
import profplan.model.task.DueDate;
import profplan.model.task.TasksDueBeforeDatePredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser {

    /**
     * Parses input, which should be a valid DueDate value
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        DueDate date = null;
        try {
            date = ParserUtil.parseDueDate(trimmedArgs);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(new TasksDueBeforeDatePredicate(date));
    }
}
