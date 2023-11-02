package profplan.model;

import java.nio.file.Path;

import profplan.commons.core.Settings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserConfigs {

    Settings getSettings();

    Path getProfPlanFilePath();

}
