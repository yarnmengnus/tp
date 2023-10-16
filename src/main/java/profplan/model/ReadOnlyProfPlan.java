package profplan.model;

import javafx.collections.ObservableList;
import profplan.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyProfPlan {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
