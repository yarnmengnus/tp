package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import profplan.logic.commands.HelpCommand;

public class HelpCommandParserTest {

    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_emptyArg_returnsEmptyHelpCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "     ", new HelpCommand(""));
    }

    @Test
    public void parse_validArgs_returnsHelpCommand() {
        // no leading and trailing whitespaces
        HelpCommand expectedHelpCommand =
                new HelpCommand("add");
        CommandParserTestUtil.assertParseSuccess(parser, "add", expectedHelpCommand);
    }

    @Test
    public void parse_invalidArgs_returnsHelpCommand() {
        // no leading and trailing whitespaces
        CommandParserTestUtil.assertParseFailure(parser, "invalid Command",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE + HelpCommand.MESSAGE_DETAILS + HelpCommand.MESSAGE_EXAMPLE));
    }
}
