package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.model.Model;
import profplan.model.ProfPlan;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setProfPlan(new ProfPlan());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
