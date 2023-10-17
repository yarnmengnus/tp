package profplan.logic.parser;

import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static profplan.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static profplan.logic.parser.CliSyntax.PREFIX_EMAIL;
import static profplan.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static profplan.logic.parser.CliSyntax.PREFIX_TAG;

import org.junit.jupiter.api.Test;

import profplan.commons.core.index.Index;
import profplan.logic.Messages;
import profplan.logic.commands.CommandTestUtil;
import profplan.logic.commands.EditCommand;
import profplan.logic.commands.EditCommand.EditTaskDescriptor;
import profplan.model.tag.Tag;
import profplan.model.task.Address;
import profplan.model.task.Email;
import profplan.model.task.Name;
import profplan.model.task.Priority;
import profplan.testutil.EditTaskDescriptorBuilder;
import profplan.testutil.TypicalIndexes;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + CommandTestUtil.NAME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + CommandTestUtil.NAME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_PRIORITY_DESC,
                Priority.MESSAGE_CONSTRAINTS); // invalid priority
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS); // invalid email
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS); // invalid address
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid priority followed by valid email
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_PRIORITY_DESC
                + CommandTestUtil.EMAIL_DESC_AMY, Priority.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Task} being edited,
        // parsing it together with a valid tag results in error
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.TAG_DESC_FRIEND
                + CommandTestUtil.TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.TAG_DESC_FRIEND
                + TAG_EMPTY + CommandTestUtil.TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + TAG_EMPTY + CommandTestUtil.TAG_DESC_FRIEND
                + CommandTestUtil.TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC
                        + CommandTestUtil.INVALID_EMAIL_DESC + CommandTestUtil.VALID_ADDRESS_AMY
                        + CommandTestUtil.VALID_PRIORITY_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.PRIORITY_DESC_BOB +
                CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.EMAIL_DESC_AMY +
                CommandTestUtil.ADDRESS_DESC_AMY + CommandTestUtil.NAME_DESC_AMY
                + CommandTestUtil.TAG_DESC_FRIEND;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_AMY)
                .withPriority(CommandTestUtil.VALID_PRIORITY_BOB).withEmail(CommandTestUtil.VALID_EMAIL_AMY)
                .withAddress(CommandTestUtil.VALID_ADDRESS_AMY)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND, CommandTestUtil.VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.PRIORITY_DESC_BOB +
                CommandTestUtil.EMAIL_DESC_AMY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withPriority(CommandTestUtil.VALID_PRIORITY_BOB)
                .withEmail(CommandTestUtil.VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = TypicalIndexes.INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.NAME_DESC_AMY;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // priority
        userInput = targetIndex.getOneBased() + CommandTestUtil.PRIORITY_DESC_AMY;
        descriptor = new EditTaskDescriptorBuilder().withPriority(CommandTestUtil.VALID_PRIORITY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + CommandTestUtil.EMAIL_DESC_AMY;
        descriptor = new EditTaskDescriptorBuilder().withEmail(CommandTestUtil.VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + CommandTestUtil.ADDRESS_DESC_AMY;
        descriptor = new EditTaskDescriptorBuilder().withAddress(CommandTestUtil.VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + CommandTestUtil.TAG_DESC_FRIEND;
        descriptor = new EditTaskDescriptorBuilder().withTags(CommandTestUtil.VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = TypicalIndexes.INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.INVALID_PRIORITY_DESC
                + CommandTestUtil.PRIORITY_DESC_BOB;

        CommandParserTestUtil.assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRIORITY));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + CommandTestUtil.PRIORITY_DESC_BOB +
                CommandTestUtil.INVALID_PRIORITY_DESC;

        CommandParserTestUtil.assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRIORITY));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + CommandTestUtil.PRIORITY_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY
                + CommandTestUtil.EMAIL_DESC_AMY
                + CommandTestUtil.TAG_DESC_FRIEND + CommandTestUtil.PRIORITY_DESC_AMY +
                CommandTestUtil.ADDRESS_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.TAG_DESC_FRIEND
                + CommandTestUtil.PRIORITY_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND;

        CommandParserTestUtil.assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRIORITY, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + CommandTestUtil.INVALID_PRIORITY_DESC
                + CommandTestUtil.INVALID_ADDRESS_DESC + CommandTestUtil.INVALID_EMAIL_DESC
                + CommandTestUtil.INVALID_PRIORITY_DESC + CommandTestUtil.INVALID_ADDRESS_DESC
                + CommandTestUtil.INVALID_EMAIL_DESC;

        CommandParserTestUtil.assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRIORITY, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
