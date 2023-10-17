package profplan.model.task;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import profplan.commons.util.CollectionUtil;
import profplan.commons.util.ToStringBuilder;
import profplan.model.tag.Tag;

/**
 * Represents a Task in the task list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private Status status;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field except status must be present and not null.
     */
    public Task(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        // CollectionUtil.requireAllNonNull(name, phone, email, address, tags);
        CollectionUtil.requireAllNonNull(name);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = Status.UNDONE_STATUS;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Phone phone, Email email, Address address, Status status, Set<Tag> tags) {
        CollectionUtil.requireAllNonNull(name, phone, email, address, status, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = status;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both tasks have the same name.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getName().equals(getName());
    }

    /**
     * Returns true if both tasks have the same identity and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return name.equals(otherTask.name)
                && phone.equals(otherTask.phone)
                && email.equals(otherTask.email)
                && address.equals(otherTask.address)
                && status.equals(otherTask.status)
                && tags.equals(otherTask.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, status, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("status", status)
                .add("tags", tags)
                .toString();
    }

}
