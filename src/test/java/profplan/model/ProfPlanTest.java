package profplan.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static profplan.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static profplan.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import profplan.model.person.Person;
import profplan.model.person.exceptions.DuplicatePersonException;
import profplan.testutil.Assert;
import profplan.testutil.PersonBuilder;
import profplan.testutil.TypicalPersons;

public class ProfPlanTest {

    private final ProfPlan profPlan = new ProfPlan();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), profPlan.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> profPlan.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyProfPlan_replacesData() {
        ProfPlan newData = TypicalPersons.getTypicalProfPlan();
        profPlan.resetData(newData);
        assertEquals(newData, profPlan);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(TypicalPersons.ALICE, editedAlice);
        ProfPlanStub newData = new ProfPlanStub(newPersons);

        Assert.assertThrows(DuplicatePersonException.class, () -> profPlan.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> profPlan.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInProfPlan_returnsFalse() {
        assertFalse(profPlan.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInProfPlan_returnsTrue() {
        profPlan.addPerson(TypicalPersons.ALICE);
        assertTrue(profPlan.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInProfPlan_returnsTrue() {
        profPlan.addPerson(TypicalPersons.ALICE);
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(profPlan.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> profPlan.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ProfPlan.class.getCanonicalName() + "{persons=" + profPlan.getPersonList() + "}";
        assertEquals(expected, profPlan.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class ProfPlanStub implements ReadOnlyProfPlan {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        ProfPlanStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
