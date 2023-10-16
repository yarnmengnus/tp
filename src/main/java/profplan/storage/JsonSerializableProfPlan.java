package profplan.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import profplan.commons.exceptions.IllegalValueException;
import profplan.model.ProfPlan;
import profplan.model.ReadOnlyProfPlan;
import profplan.model.person.Person;

/**
 * An Immutable ProfPlan that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableProfPlan {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableProfPlan} with the given persons.
     */
    @JsonCreator
    public JsonSerializableProfPlan(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyProfPlan} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableProfPlan}.
     */
    public JsonSerializableProfPlan(ReadOnlyProfPlan source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ProfPlan toModelType() throws IllegalValueException {
        ProfPlan profPlan = new ProfPlan();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (profPlan.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            profPlan.addPerson(person);
        }
        return profPlan;
    }

}
