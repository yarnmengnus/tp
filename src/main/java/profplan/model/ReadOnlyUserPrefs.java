package profplan.model;

import java.nio.file.Path;

import profplan.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getProfPlanFilePath();

}
