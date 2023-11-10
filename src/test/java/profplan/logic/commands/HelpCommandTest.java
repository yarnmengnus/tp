package profplan.logic.commands;

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
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(getAllCommandDescriptions(), false, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpCommandWord_success() {
        try {
            CommandResult expectedCommandResult = new CommandResult(
                getOneCommandDescription(AddCommand.COMMAND_WORD), false, false);
            assertCommandSuccess(new HelpCommand(AddCommand.COMMAND_WORD), model, expectedCommandResult, expectedModel);
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
