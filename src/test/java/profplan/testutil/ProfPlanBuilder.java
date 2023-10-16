package profplan.testutil;

import profplan.model.ProfPlan;
import profplan.model.task.Task;

/**
 * A utility class to help with building ProfPlan objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new ProfPlanBuilder().withTask("John", "Doe").build();}
 */
public class ProfPlanBuilder {

    private ProfPlan profPlan;

    public ProfPlanBuilder() {
        profPlan = new ProfPlan();
    }

    public ProfPlanBuilder(ProfPlan profPlan) {
        this.profPlan = profPlan;
    }

    /**
     * Adds a new {@code Task} to the {@code ProfPlan} that we are building.
     */
    public ProfPlanBuilder withTask(Task task) {
        profPlan.addTask(task);
        return this;
    }

    public ProfPlan build() {
        return profPlan;
    }
}
