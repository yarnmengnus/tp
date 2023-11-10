package profplan.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static profplan.logic.Messages.MESSAGE_INVALID_COMMAND_WORD;
import static profplan.logic.commands.CommandTestUtil.assertCommandFailure;
import static profplan.logic.commands.CommandTestUtil.assertCommandSuccess;
import static profplan.logic.commands.HelpCommand.DELIMITTER_BETWEEN_COMMANDS;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;
import profplan.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void equality_help_success() {
        assertTrue(new HelpCommand("add").equals(new HelpCommand("add")));
        assertTrue(new HelpCommand().equals(new HelpCommand()));
    }

    @Test
    public void equality_help_failure() {
        assertFalse(new HelpCommand("add").equals(new HelpCommand("delete")));
        assertFalse(new HelpCommand().equals(new HelpCommand("invalidCommand")));
    }

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(getAllCommandDescriptions(), false, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpInvalidCommand_failure() {
        assertCommandFailure(new HelpCommand("Invalid Command"),
            model, HelpCommand.MESSAGE_INVALID_COMMAND_WORD);
    }

    @Test
    public void execute_helpAdd_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(AddCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(AddCommand.COMMAND_WORD), model,
                expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpDelete_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(DeleteCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(DeleteCommand.COMMAND_WORD), model,
                expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpEdit_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(EditCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(EditCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpEditSettings_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(EditSettingsCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(EditSettingsCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpMark_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(MarkCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(MarkCommand.COMMAND_WORD), model,
                expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpUnmark_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(UnmarkCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(UnmarkCommand.COMMAND_WORD), model,
                expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpDoNext_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(DoNextCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(DoNextCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpClear_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(ClearCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(ClearCommand.COMMAND_WORD), model,
                expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpFind_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(FindCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(FindCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpFilter_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(FilterCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(FilterCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpList_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(ListCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(ListCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpExit_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(ExitCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(ExitCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpHelp_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(HelpCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(HelpCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpDescription_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(DescriptionCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(DescriptionCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpSortDueDate_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(SortDueDateCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(SortDueDateCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpSortPriority_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(SortPriorityCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(SortPriorityCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpListWeek_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(ListWeekCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(ListWeekCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpListMonth_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(ListMonthCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(ListMonthCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Test
    public void execute_helpStats_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(StatsCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(StatsCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
        } catch (CommandException e) {
            assertCommandFailure(null, model, MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    private String getOneCommandDescription(String command) throws CommandException {
        switch (command) {

        case AddCommand.COMMAND_WORD:
            return AddCommand.MESSAGE_USAGE + "\n" + AddCommand.MESSAGE_EXAMPLE;

        case EditCommand.COMMAND_WORD:
            return EditCommand.MESSAGE_USAGE + "\n" + EditCommand.MESSAGE_EXAMPLE;

        case EditSettingsCommand.COMMAND_WORD:
            return EditSettingsCommand.MESSAGE_USAGE + "\n" + EditSettingsCommand.MESSAGE_EXAMPLE;

        case MarkCommand.COMMAND_WORD:
            return MarkCommand.MESSAGE_USAGE + "\n" + MarkCommand.MESSAGE_EXAMPLE;

        case UnmarkCommand.COMMAND_WORD:
            return UnmarkCommand.MESSAGE_USAGE + "\n" + UnmarkCommand.MESSAGE_EXAMPLE;

        case DeleteCommand.COMMAND_WORD:
            return DeleteCommand.MESSAGE_USAGE + "\n" + DeleteCommand.MESSAGE_EXAMPLE;

        case DoNextCommand.COMMAND_WORD:
            return DoNextCommand.MESSAGE_USAGE;

        case ClearCommand.COMMAND_WORD:
            return ClearCommand.MESSAGE_DETAILS;

        case FindCommand.COMMAND_WORD:
            return FindCommand.MESSAGE_USAGE + "\n" + FindCommand.MESSAGE_EXAMPLE;

        case FilterCommand.COMMAND_WORD:
            return FilterCommand.MESSAGE_USAGE + "\n" + FilterCommand.MESSAGE_EXAMPLE;

        case ListCommand.COMMAND_WORD:
            return ListCommand.MESSAGE_DETAILS;

        case ExitCommand.COMMAND_WORD:
            return ExitCommand.MESSAGE_DETAILS;

        case HelpCommand.COMMAND_WORD:
            return HelpCommand.MESSAGE_USAGE + "\n" + HelpCommand.MESSAGE_EXAMPLE;

        case DescriptionCommand.COMMAND_WORD:
            return DescriptionCommand.MESSAGE_USAGE + "\n" + DescriptionCommand.MESSAGE_EXAMPLE;

        case SortDueDateCommand.COMMAND_WORD:
            return SortDueDateCommand.MESSAGE_USAGE;

        case SortPriorityCommand.COMMAND_WORD:
            return SortPriorityCommand.MESSAGE_USAGE;

        case ListWeekCommand.COMMAND_WORD:
            return ListWeekCommand.MESSAGE_USAGE;

        case ListMonthCommand.COMMAND_WORD:
            return ListMonthCommand.MESSAGE_USAGE;

        case StatsCommand.COMMAND_WORD:
            return StatsCommand.MESSAGE_USAGE;

        default:
            throw new CommandException(MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    private String getAllCommandDescriptions() {
        ArrayList<Class<? extends Command>> commands = listCommands();
        String rtn = "";
        for (Class<? extends Command> command : commands) {
            try {
                Field usage = command.getDeclaredField("MESSAGE_USAGE");
                System.out.println(usage.get(null).toString());
                rtn += usage.get(null).toString() + DELIMITTER_BETWEEN_COMMANDS;
            } catch (NoSuchFieldException e) {
                //impossible unless your command has no command word??
                System.out.println(e);
                continue;
            } catch (IllegalAccessException e) {
                //impossible as command word MUST be public
                System.out.println(e);
                continue;
            }
        }
        return rtn;
    }

    private ArrayList<Class<? extends Command>> listCommands() {
        ArrayList<Class<? extends Command>> rtn = new ArrayList<Class<? extends Command>>();
        rtn.add(AddCommand.class);
        rtn.add(EditCommand.class);
        rtn.add(EditSettingsCommand.class);
        rtn.add(MarkCommand.class);
        rtn.add(UnmarkCommand.class);
        rtn.add(DeleteCommand.class);
        rtn.add(DoNextCommand.class);
        rtn.add(ClearCommand.class);
        rtn.add(FindCommand.class);
        rtn.add(FilterCommand.class);
        rtn.add(HelpCommand.class);
        rtn.add(DescriptionCommand.class);
        rtn.add(SortDueDateCommand.class);
        rtn.add(SortPriorityCommand.class);
        rtn.add(ListWeekCommand.class);
        rtn.add(ListMonthCommand.class);
        System.out.println(rtn.size());
        return rtn;
    }
}
