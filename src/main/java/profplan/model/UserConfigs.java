package profplan.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import profplan.commons.core.Settings;

/**
 * Stores global settings used in various commands. These can be updated by the user.
 */
public class UserConfigs implements ReadOnlyUserConfigs {

    private Settings settings = new Settings();
    private Path profPlanFilePath = Paths.get("data" , "profplan.json");

    /**
     * Creates a {@code UserConfigs} with default values.
     */
    public UserConfigs() {}

    /**
     * Creates a {@code UserConfigs} with the prefs in {@code userConfigs}.
     */
    public UserConfigs(ReadOnlyUserConfigs userConfigs) {
        this();
        resetData(userConfigs);
    }

    /**
     * Resets the existing data of this {@code UserConfigs} with {@code newUserConfigs}.
     */
    public void resetData(ReadOnlyUserConfigs newUserConfigs) {
        requireNonNull(newUserConfigs);
        setSettings(newUserConfigs.getSettings());
        setProfPlanFilePath(newUserConfigs.getProfPlanFilePath());
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        requireNonNull(settings);
        this.settings = settings;
    }

    public Path getProfPlanFilePath() {
        return profPlanFilePath;
    }

    public void setProfPlanFilePath(Path profPlanFilePath) {
        requireNonNull(profPlanFilePath);
        this.profPlanFilePath = profPlanFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserConfigs)) {
            return false;
        }

        UserConfigs otherUserConfigs = (UserConfigs) other;
        return settings.equals(otherUserConfigs.settings)
                && profPlanFilePath.equals(otherUserConfigs.profPlanFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(settings, profPlanFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Settings : " + settings);
        sb.append("\nLocal data file location : " + profPlanFilePath);
        return sb.toString();
    }

}
