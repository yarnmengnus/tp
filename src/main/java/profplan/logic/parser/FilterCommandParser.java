package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static profplan.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static profplan.logic.parser.CliSyntax.PREFIX_STATUS;
import static profplan.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.function.Predicate;

import profplan.logic.commands.FilterCommand;
import profplan.logic.parser.exceptions.ParseException;
import profplan.model.task.TaskDueDatePredicate;
import profplan.model.task.TaskPriorityPredicate;
import profplan.model.task.TaskStatusPredicate;


/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses input, which should be a valid DueDate value
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRIORITY, PREFIX_STATUS, PREFIX_DUEDATE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PRIORITY, PREFIX_STATUS, PREFIX_DUEDATE);

        Predicate<?> pred = null;
 
        try {
            if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            pred = new TaskPriorityPredicate(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
            }

            if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
                pred = new TaskStatusPredicate(ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
            }

            if (argMultimap.getValue(PREFIX_DUEDATE).isPresent()) {
                pred = new TaskDueDatePredicate(ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_DUEDATE).get()));
            }

            if (pred == null) {
                throw new ParseException("", null);
            }

        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(pred);
    }
}
