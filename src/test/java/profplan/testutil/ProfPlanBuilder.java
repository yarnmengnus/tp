package profplan.testutil;

import profplan.model.ProfPlan;
import profplan.model.person.Person;

/**
 * A utility class to help with building ProfPlan objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new ProfPlanBuilder().withPerson("John", "Doe").build();}
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
     * Adds a new {@code Person} to the {@code ProfPlan} that we are building.
     */
    public ProfPlanBuilder withPerson(Person person) {
        profPlan.addPerson(person);
        return this;
    }

    public ProfPlan build() {
        return profPlan;
    }
}
