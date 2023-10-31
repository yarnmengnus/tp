package profplan.logic.commands;

import profplan.logic.HelperCommandUtil;
import profplan.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {
    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n";
    public static final String MESSAGE_DETAILS = "Alternatively, "
        + "you can use help with a command to see an example of it.\n"
        + "Parameters: [command] (must be a valid command) "
        + "help [command]\n";
    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + "[command]";

    public static final String MESSAGE_INVALID_COMMAND_WORD = "INDEX should be greater than or equal to 1";
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private final String command;
    private final HelperCommandUtil util;

    /**
     * Creates an empty HelpCommand. This is will list all commands.
     */
    public HelpCommand() {
        this.command = null;
        this.util = new HelperCommandUtil();
    }

    /**
     * Creates a HelpCommand to list all commands.
     * This constructor allows old HelperCommandUtils to be reused.
     * @param util The HelperCommandUtil to be reused.
     */
    public HelpCommand(HelperCommandUtil util) {
        this.command = null;
        this.util = util;
    }

    /**
     * Creates a HelpCommand to get help for a particular command.
     * @param command The command to get help for.
     */
    public HelpCommand(String command) {
        this.command = command;
        this.util = new HelperCommandUtil();
    }

    /**
     * Creates a HelpCommand to get help for a particular command.
     * This constructor allows old HelperCommandUtils to be reused.
     * @param util The HelperCommandUtil to be reused.
     * @param command The command to get help for.
     */
    public HelpCommand(HelperCommandUtil util, String command) {
        this.command = command;
        this.util = util;
    }

    @Override
    public CommandResult execute(Model model) {
        if (this.command == null) {
            return new CommandResult(util.getAllCommandDescriptions(), false, false);
        } else {
            return new CommandResult(findCommandHelp(command));
        }
    }

    private String findCommandHelp(String command) {
        return util.getOneCommandDescription(command);
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
