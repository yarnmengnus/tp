package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;

/**
 * Lists tasks within a month of today.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Shows a summary of your progress";

    public static final String MESSAGE_SUCCESS = "Here are your statistics Prof!\n";

    public static final String COMPLETION_RATE_MESSAGE_FORMAT = "Completion Rate: %.1f%%";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String rtn = MESSAGE_SUCCESS
            + String.format(COMPLETION_RATE_MESSAGE_FORMAT, Math.ceil(model.getCompletionRate() * 1000) / 10);
        return new CommandResult(rtn);
    }

}
