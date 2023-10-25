package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import profplan.logic.commands.FilterCommand;
import profplan.model.task.DueDate;
import profplan.model.task.TasksDueBeforeDatePredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new TasksDueBeforeDatePredicate(new DueDate("01-01-2000")));
        CommandParserTestUtil.assertParseSuccess(parser, "01-01-2000", expectedFilterCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n 01-01-2000\t", expectedFilterCommand);
    }

}
