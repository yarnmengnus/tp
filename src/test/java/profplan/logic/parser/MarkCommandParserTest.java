package profplan.logic.parser;

import org.junit.jupiter.api.Test;

import profplan.logic.commands.MarkCommand;

public class MarkCommandParserTest {

    private MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1", new MarkCommand(1));
    }

    @Test
    public void parse_negativeNumber_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "-1", MarkCommand.MESSAGE_INVALID_NUMBER);
    }
    @Test
    public void parse_zero_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "0", MarkCommand.MESSAGE_INVALID_NUMBER);
    }

    @Test
    public void parse_nonNumeric_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a", String.format(
            MarkCommand.MESSAGE_FULL_HELP));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "", String.format(
            MarkCommand.MESSAGE_FULL_HELP));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "   ", String.format(
            MarkCommand.MESSAGE_FULL_HELP));
    }

    @Test
    public void parse_validNumberWithWhitespace_returnsMarkCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, " 3 ", new MarkCommand(3));
    }

}
