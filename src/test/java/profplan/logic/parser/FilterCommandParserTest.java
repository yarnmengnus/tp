package profplan.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import profplan.logic.commands.FilterCommand;
import profplan.logic.parser.exceptions.ParseException;
import profplan.model.task.DueDate;
import profplan.model.task.Priority;
import profplan.model.task.Status;
import profplan.model.task.Task;
import profplan.model.task.predicates.CombinedPredicate;
import profplan.model.task.predicates.TaskDueDatePredicate;
import profplan.model.task.predicates.TaskPriorityPredicate;
import profplan.model.task.predicates.TaskStatusPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FilterCommand.MESSAGE_USAGE + FilterCommand.MESSAGE_DETAILS + FilterCommand.MESSAGE_EXAMPLE));
    }

    @Test
    public void parse_validDueDate_returnsFilterCommand() {
        ArrayList<Predicate<Task>> preds = new ArrayList<>();
        preds.add(new TaskDueDatePredicate(new DueDate("01-01-2000")));

        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new CombinedPredicate(preds));
        CommandParserTestUtil.assertParseSuccess(parser, " d/01-01-2000", expectedFilterCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n d/01-01-2000\t", expectedFilterCommand);
    }

    @Test
    public void parse_validPriority_returnsFilterCommand() {
        ArrayList<Predicate<Task>> preds = new ArrayList<>();
        preds.add(new TaskPriorityPredicate(new Priority("1")));

        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new CombinedPredicate(preds));
        CommandParserTestUtil.assertParseSuccess(parser, " p/1", expectedFilterCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n p/1\t", expectedFilterCommand);
    }

    @Test
    public void parse_validStatus_returnsFilterCommand() {
        ArrayList<Predicate<Task>> preds = new ArrayList<>();
        preds.add(new TaskStatusPredicate(new Status("done")));

        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new CombinedPredicate(preds));
        CommandParserTestUtil.assertParseSuccess(parser, " s/done", expectedFilterCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n s/done\t", expectedFilterCommand);
    }

    @Test
    public void testMessageSuccessDueDate() {
        FilterCommand filterDueDate = null;
        try {
            filterDueDate = parser.parse("d/01-01-2024");
        } catch (ParseException e) {
            return;
        }
        String successMessageDueDate = "Here are your tasks that are:\nDue before: 01-01-2024";
        assertEquals(filterDueDate.getSuccessMessage(), successMessageDueDate);
    }

    @Test
    public void testMessageSuccessPriority() {
        FilterCommand filterPriority = null;
        try {
            filterPriority = parser.parse("p/3");
        } catch (ParseException e) {
            return;
        }
        String successMessagePriority = "Here are your tasks that are:\nPriority: 3";
        assertEquals(filterPriority.getSuccessMessage(), successMessagePriority);
    }

    @Test
    public void testMessageSuccessStatus() {
        FilterCommand filterStatus = null;
        try {
            filterStatus = parser.parse("s/done");
        } catch (ParseException e) {
            return;
        }
        String successMessageStatus = "Here are your tasks that are:\nStatus: done";
        assertEquals(filterStatus.getSuccessMessage(), successMessageStatus);
    }
}
