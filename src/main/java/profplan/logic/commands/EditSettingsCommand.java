package profplan.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import profplan.commons.core.Settings;
import profplan.commons.util.ToStringBuilder;
import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;
import profplan.model.ModelManager;
import profplan.model.ReadOnlyUserConfigs;

/**
 * Edits the details of an existing setting in the user configs.
 */
public class EditSettingsCommand extends Command {

    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the value of a specified configuration "
            + "parameter in the user config files to the input value.";

    public static final String MESSAGE_DETAILS = "Existing values will be overwritten by the input values.\n"
            + "Parameters: settingField, the setting to be altered; "
            + "and value, the new value to be set";

    public static final String MESSAGE_EXAMPLE = "Example: " + COMMAND_WORD + " semesterDays "
            + "100 ";
    public static final String MESSAGE_FULL_HELP = MESSAGE_USAGE + "\n" + MESSAGE_DETAILS + "\n" + MESSAGE_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Settings successfully updated!";

    private final EditSettingsDescriptor editSettingsDescriptor;

    /**
     * @param editSettingsDescriptor details to edit the settings with
     */
    public EditSettingsCommand(EditSettingsDescriptor editSettingsDescriptor) {
        requireNonNull(editSettingsDescriptor);

        this.editSettingsDescriptor = new EditSettingsDescriptor(editSettingsDescriptor);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ReadOnlyUserConfigs userConfigs = ModelManager.getUserConfigs();

        Settings settingsToEdit = userConfigs.getSettings();
        Settings editedSettings = createEditedSettings(settingsToEdit, editSettingsDescriptor);

        ModelManager.setSettings(editedSettings);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private Settings createEditedSettings(Settings settingsToEdit,
                                          EditSettingsDescriptor editSettingsDescriptor) {
        assert settingsToEdit != null;

        Integer semesterDays = editSettingsDescriptor.getSemesterDays()
                .orElse(settingsToEdit.getSemesterDays());

        return new Settings(semesterDays);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditSettingsCommand)) {
            return false;
        }

        EditSettingsCommand otherEditSettingsCommand = (EditSettingsCommand) other;
        return editSettingsDescriptor.equals(otherEditSettingsCommand.editSettingsDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("editSettingsDescriptor", editSettingsDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the settings with. The non-empty field value will replace
     * the corresponding setting value.
     */
    public static class EditSettingsDescriptor {
        private Integer semesterDays;

        public EditSettingsDescriptor() {}

        public EditSettingsDescriptor(EditSettingsDescriptor toCopy) {
            setSemesterDays(toCopy.semesterDays);
        }

        public void setSemesterDays(int semesterDays) {
            this.semesterDays = semesterDays;
        }

        public Optional<Integer> getSemesterDays() {
            return Optional.ofNullable(semesterDays);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditSettingsDescriptor)) {
                return false;
            }

            EditSettingsDescriptor otherEditSettingsDescriptor = (EditSettingsDescriptor) other;
            return Objects.equals(semesterDays, otherEditSettingsDescriptor.semesterDays);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("semesterDays", semesterDays)
                    .toString();
        }
    }
}
