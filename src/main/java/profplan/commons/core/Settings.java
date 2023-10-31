package profplan.commons.core;

import java.io.Serializable;
import java.util.Objects;

import profplan.commons.util.ToStringBuilder;

/**
 * A Serializable class that contains the general settings.
 * Guarantees: immutable.
 */
public class Settings implements Serializable {

    // default values
    private static final int DEFAULT_SEMESTER_DAYS = 180;

    // fields
    private final int semesterDays;

    /**
     * Constructs a {@code Settings} with default settings.
     */
    public Settings() {
        semesterDays = DEFAULT_SEMESTER_DAYS;
    }

    /**
     * Constructs a {@code Settings} with the specified settings.
     */
    public Settings(int semesterDays) {
        this.semesterDays = semesterDays;
    }

    // getters
    public int getSemesterDays() {
        return semesterDays;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Settings)) {
            return false;
        }

        Settings otherSettings = (Settings) other;
        return this.semesterDays == otherSettings.semesterDays;
    }

    @Override
    public int hashCode() {
        return Objects.hash(semesterDays);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("semesterDays", semesterDays)
                .toString();
    }

}
