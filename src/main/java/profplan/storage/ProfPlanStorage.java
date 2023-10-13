package profplan.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import profplan.commons.exceptions.DataLoadingException;
import profplan.model.ProfPlan;
import profplan.model.ReadOnlyProfPlan;

/**
 * Represents a storage for {@link ProfPlan}.
 */
public interface ProfPlanStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getProfPlanFilePath();

    /**
     * Returns ProfPlan data as a {@link ReadOnlyProfPlan}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyProfPlan> readProfPlan() throws DataLoadingException;

    /**
     * @see #getProfPlanFilePath()
     */
    Optional<ReadOnlyProfPlan> readProfPlan(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyProfPlan} to the storage.
     * @param profPlan cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveProfPlan(ReadOnlyProfPlan profPlan) throws IOException;

    /**
     * @see #saveProfPlan(ReadOnlyProfPlan)
     */
    void saveProfPlan(ReadOnlyProfPlan addressBook, Path filePath) throws IOException;

}
