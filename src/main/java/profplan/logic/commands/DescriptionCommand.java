package profplan.logic.commands;

import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;

public class DescriptionCommand extends Command {

    public static final String COMMAND_WORD = "description";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
