package profplan.logic.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;

import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {
    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.";
    public static final String MESSAGE_DETAILS = "Alternatively, "
        + "you can use help with a command to see an example of it.\n"
        + "Parameters: [command] (must be a valid command) "
        + "help [command]\n";
    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + "[command]";

    public static final String MESSAGE_INVALID_COMMAND_WORD = "Invalid command word!";
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    public static final String DELIMITTER_BETWEEN_COMMANDS = "\n-----------------\n";

    private final String command;

    /**
     * Creates an empty HelpCommand. This is will list all commands.
     */
    public HelpCommand() {
        this.command = null;
    }

    /**
     * Creates a HelpCommand to get help for a particular command.
     * @param command The command to get help for.
     */
    public HelpCommand(String command) {
        this.command = command;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (this.command == null || this.command.equals("")) {
            return new CommandResult(getAllCommandDescriptions(), false, false);
        } else {
            return new CommandResult(getOneCommandDescription(command));
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
                //when command has no usage field
                System.out.println(e);
                continue;
            } catch (IllegalAccessException e) {
                //impossible as command usage MUST be public
                System.out.println(e);
                continue;
            }
        }
        return rtn;
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
            return ClearCommand.MESSAGE_USAGE;

        case FindCommand.COMMAND_WORD:
            return FindCommand.MESSAGE_USAGE + "\n" + FindCommand.MESSAGE_EXAMPLE;

        case FilterCommand.COMMAND_WORD:
            return FilterCommand.MESSAGE_USAGE + "\n" + FilterCommand.MESSAGE_EXAMPLE;

        case ListCommand.COMMAND_WORD:
            return ListCommand.MESSAGE_USAGE;


        case ExitCommand.COMMAND_WORD:
            return ExitCommand.MESSAGE_USAGE;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HelpCommand)) {
            return false;
        }
        HelpCommand h = (HelpCommand) o;
        if (h.command != this.command) {
            return false;
        }
        return true;
    }

}
