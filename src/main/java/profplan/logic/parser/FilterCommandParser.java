package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static profplan.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static profplan.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static profplan.logic.parser.CliSyntax.PREFIX_RECURRING;
import static profplan.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.ArrayList;
import java.util.function.Predicate;

import profplan.logic.commands.FilterCommand;
import profplan.logic.parser.exceptions.ParseException;
import profplan.model.task.Task;
import profplan.model.task.predicates.CombinedPredicate;
import profplan.model.task.predicates.TaskDueDatePredicate;
import profplan.model.task.predicates.TaskPriorityPredicate;
import profplan.model.task.predicates.TaskRecurringTypePredicate;
import profplan.model.task.predicates.TaskStatusPredicate;


/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses input, which should be a valid Status, DueDate, or Priority
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRIORITY, PREFIX_STATUS, PREFIX_DUEDATE, PREFIX_RECURRING);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_FULL_HELP));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PRIORITY, PREFIX_STATUS, PREFIX_DUEDATE, PREFIX_RECURRING);

        ArrayList<Predicate<Task>> predList = new ArrayList<>();
        ArrayList<String> filters = new ArrayList<>();

        try {
            if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
                TaskPriorityPredicate newPred = new TaskPriorityPredicate(
                        ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
                predList.add(newPred);
                filters.add("Priority: " + newPred.getPriority() + "\n");
            }

            if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
                TaskStatusPredicate newPred = new TaskStatusPredicate(
                        ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
                predList.add(newPred);
                filters.add("Status: " + newPred.getStatus() + "\n");
            }

            if (argMultimap.getValue(PREFIX_DUEDATE).isPresent()) {
                TaskDueDatePredicate newPred = new TaskDueDatePredicate(
                        ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_DUEDATE).get()));
                predList.add(newPred);
                filters.add("Due before: " + newPred.getDate() + "\n");
            }

            if (argMultimap.getValue(PREFIX_RECURRING).isPresent()) {
                TaskRecurringTypePredicate newPred = new TaskRecurringTypePredicate(
                        ParserUtil.parseRecurringType(argMultimap.getValue(PREFIX_RECURRING).get()));
                predList.add(newPred);
                String recurringType = newPred.getRecurringType() == null
                    ? "NONE" : newPred.getRecurringType()
                    .toString();
                filters.add("Recurring: " + recurringType + "\n");
            }

            if (predList.isEmpty()) {
                throw new ParseException("", null);
            }

        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_FULL_HELP));
        }

        FilterCommand cmd = new FilterCommand(new CombinedPredicate(predList));
        StringBuilder success = new StringBuilder(cmd.getSuccessMessage());
        for (String string : filters) {
            success.append(string);
        }
        cmd.setSuccessMessage(success.toString());
        return cmd;
    }
}
