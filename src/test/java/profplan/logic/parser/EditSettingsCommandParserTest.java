package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import profplan.logic.commands.EditSettingsCommand;

public class EditSettingsCommandParserTest {

    private EditSettingsCommandParser parser = new EditSettingsCommandParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSettingsCommand.MESSAGE_FULL_HELP));
    }

    @Test
    public void parse_invalidParameter_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, " set aaaaaa 123  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSettingsCommand.MESSAGE_FULL_HELP));
    }
}
