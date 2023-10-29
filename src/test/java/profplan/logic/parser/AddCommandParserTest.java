package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static profplan.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static profplan.logic.parser.CliSyntax.PREFIX_EMAIL;
import static profplan.logic.parser.CliSyntax.PREFIX_NAME;
import static profplan.logic.parser.CliSyntax.PREFIX_PRIORITY;

import org.junit.jupiter.api.Test;

import profplan.logic.Messages;
import profplan.logic.commands.AddCommand;
import profplan.logic.commands.CommandTestUtil;
import profplan.model.tag.Tag;
import profplan.model.task.Email;
import profplan.model.task.Name;
import profplan.model.task.Priority;
import profplan.model.task.Task;
import profplan.testutil.TaskBuilder;
import profplan.testutil.TypicalTasks;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(TypicalTasks.BOB).withTags(CommandTestUtil.VALID_TAG_FRIEND)
                .build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PRIORITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND
                + CommandTestUtil.DESCRIPTION_DESC_BOB, new AddCommand(expectedTask));


        // multiple tags - all accepted
        Task expectedTaskMultipleTags = new TaskBuilder(TypicalTasks.BOB)
                .withTags(CommandTestUtil.VALID_TAG_FRIEND, CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PRIORITY_DESC_BOB
                        + CommandTestUtil.EMAIL_DESC_BOB
                        + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_HUSBAND
                        + CommandTestUtil.TAG_DESC_FRIEND,
                new AddCommand(expectedTaskMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedTaskString = CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PRIORITY_DESC_BOB
                + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND;

        // multiple names
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_AMY
                        + validExpectedTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple prioritys
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PRIORITY_DESC_AMY
                        + validExpectedTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRIORITY));

        // multiple emails
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.EMAIL_DESC_AMY
                        + validExpectedTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.ADDRESS_DESC_AMY
                        + validExpectedTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        CommandParserTestUtil.assertParseFailure(parser,
                validExpectedTaskString + CommandTestUtil.PRIORITY_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY
                        + CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY
                        + validExpectedTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS,
                        PREFIX_EMAIL, PREFIX_PRIORITY));

        // invalid value followed by valid value

        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC
                        + validExpectedTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_EMAIL_DESC
                        + validExpectedTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid priority
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_PRIORITY_DESC
                        + validExpectedTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRIORITY));

        // invalid address
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_ADDRESS_DESC
                        + validExpectedTaskString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, validExpectedTaskString
                        + CommandTestUtil.INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, validExpectedTaskString
                        + CommandTestUtil.INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid priority
        CommandParserTestUtil.assertParseFailure(parser, validExpectedTaskString
                        + CommandTestUtil.INVALID_PRIORITY_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRIORITY));

        // invalid address
        CommandParserTestUtil.assertParseFailure(parser, validExpectedTaskString
                        + CommandTestUtil.INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Task expectedTask = new TaskBuilder(TypicalTasks.AMY).withTags().build();
        CommandParserTestUtil.assertParseSuccess(parser,
                CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.PRIORITY_DESC_AMY
                        + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY
                        + CommandTestUtil.DESCRIPTION_DESC_AMY,
                new AddCommand(expectedTask));
    }

    /*
    @Test - <<Rewrite test once all compulsory fields for task is determined>>
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB
                        + CommandTestUtil.PRIORITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                        + CommandTestUtil.ADDRESS_DESC_BOB,
                expectedMessage);

        // missing priority prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                        + CommandTestUtil.VALID_PRIORITY_BOB + CommandTestUtil.EMAIL_DESC_BOB
                        + CommandTestUtil.ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                        + CommandTestUtil.PRIORITY_DESC_BOB + CommandTestUtil.VALID_EMAIL_BOB
                        + CommandTestUtil.ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                        + CommandTestUtil.PRIORITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                        + CommandTestUtil.VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB
                        + CommandTestUtil.VALID_PRIORITY_BOB + CommandTestUtil.VALID_EMAIL_BOB
                        + CommandTestUtil.VALID_ADDRESS_BOB,
                expectedMessage);
    }
    */

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC
                + CommandTestUtil.PRIORITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_HUSBAND
                + CommandTestUtil.TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid priority
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.INVALID_PRIORITY_DESC + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_HUSBAND
                + CommandTestUtil.TAG_DESC_FRIEND, Priority.MESSAGE_CONSTRAINTS);

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.PRIORITY_DESC_BOB + CommandTestUtil.INVALID_EMAIL_DESC
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_HUSBAND
                + CommandTestUtil.TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.PRIORITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.INVALID_TAG_DESC + CommandTestUtil.VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC
                        + CommandTestUtil.PRIORITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                        + CommandTestUtil.INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                        + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PRIORITY_DESC_BOB
                        + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
