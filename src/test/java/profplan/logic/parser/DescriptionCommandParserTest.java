package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import profplan.logic.commands.DescriptionCommand;
import profplan.model.task.Description;
import profplan.testutil.TypicalIndexes;

public class DescriptionCommandParserTest {

    private DescriptionCommandParser parser = new DescriptionCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DescriptionCommand.MESSAGE_FULL_HELP));
    }

    @Test
    public void parse_validArgs_returnsDescriptionCommand() {
        DescriptionCommand expectedDescriptionCommand =
                new DescriptionCommand(TypicalIndexes.INDEX_FIRST_TASK, new Description("test description"));

        CommandParserTestUtil.assertParseSuccess(parser, "   1    des/   test description    ",
                expectedDescriptionCommand);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, " abc des/def  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DescriptionCommand.MESSAGE_FULL_HELP));
    }

    @Test
    public void parse_emptyDescription_returnsDescriptionCommand() {
        DescriptionCommand expectedDescriptionCommand =
                new DescriptionCommand(TypicalIndexes.INDEX_FIRST_TASK, new Description(""));

        CommandParserTestUtil.assertParseSuccess(parser, "   1    des/       ",
                expectedDescriptionCommand);
    }

    @Test
    public void parse_specialCharactersDescription_returnsDescriptionCommand() {
        DescriptionCommand expectedDescriptionCommand =
                new DescriptionCommand(TypicalIndexes.INDEX_FIRST_TASK, new Description("!@#$%^&*()_"));

        CommandParserTestUtil.assertParseSuccess(parser, "   1    des/   !@#$%^&*()_  ",
                expectedDescriptionCommand);
    }
}
