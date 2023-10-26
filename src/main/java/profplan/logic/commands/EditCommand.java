package profplan.logic.commands;

import static java.util.Objects.requireNonNull;
import static profplan.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static profplan.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static profplan.logic.parser.CliSyntax.PREFIX_EMAIL;
import static profplan.logic.parser.CliSyntax.PREFIX_LINK;
import static profplan.logic.parser.CliSyntax.PREFIX_NAME;
import static profplan.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static profplan.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import profplan.commons.core.index.Index;
import profplan.commons.util.CollectionUtil;
import profplan.commons.util.ToStringBuilder;
import profplan.logic.Messages;
import profplan.logic.commands.exceptions.CommandException;
import profplan.model.Model;
import profplan.model.tag.Tag;
import profplan.model.task.Address;
import profplan.model.task.DueDate;
import profplan.model.task.Email;
import profplan.model.task.Link;
import profplan.model.task.Name;
import profplan.model.task.Priority;
import profplan.model.task.Task;



/**
 * Edits the details of an existing task in the task list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_LINK + "LINK] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "[" + PREFIX_DUEDATE + "DUEDATE...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PRIORITY + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";
    public static final String MESSAGE_DETAILS = "";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (!taskToEdit.isSameTask(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.setTask(taskToEdit, editedTask);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, Messages.format(editedTask)));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElse(taskToEdit.getName());
        Priority updatedPriority = editTaskDescriptor.getPriority().orElse(taskToEdit.getPriority());
        Email updatedEmail = editTaskDescriptor.getEmail().orElse(taskToEdit.getEmail());
        Address updatedAddress = editTaskDescriptor.getAddress().orElse(taskToEdit.getAddress());
        Link updatedLink = editTaskDescriptor.getLink().orElse(taskToEdit.getLink());
        Set<Tag> updatedTags = editTaskDescriptor.getTags().orElse(taskToEdit.getTags());
        Set<Task> updatedChildren = editTaskDescriptor.getChildren().orElse(taskToEdit.getChildren());
        DueDate updatedDueDate = editTaskDescriptor.getDueDate().orElse(taskToEdit.getDueDate());
        return new Task(updatedName, updatedPriority, updatedEmail, updatedAddress, updatedTags,
                        updatedDueDate, updatedChildren, updatedLink);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editTaskDescriptor.equals(otherEditCommand.editTaskDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editTaskDescriptor", editTaskDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Name name;
        private Priority priority;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Set<Task> children;
        private DueDate dueDate;
        private Link link;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setName(toCopy.name);
            setPriority(toCopy.priority);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setLink(toCopy.link);
            setTags(toCopy.tags);
            setDueDate(toCopy.dueDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, priority, email, address, tags, dueDate, link);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }
        public void setChildren(Set<Task> children) {
            this.children = (children != null) ? new HashSet<>(children) : null;
        }

        public Optional<Set<Task>> getChildren() {
            return (children != null) ? Optional.of(Collections.unmodifiableSet(children)) : Optional.empty();
        }
        public void setDueDate(DueDate date) {
            this.dueDate = date;
        }

        public Optional<DueDate> getDueDate() {
            return Optional.ofNullable(dueDate);
        }

        public void setLink(Link link) {
            this.link = link;
        }

        public Optional<Link> getLink() {
            return Optional.ofNullable(link);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            EditTaskDescriptor otherEditTaskDescriptor = (EditTaskDescriptor) other;
            return Objects.equals(name, otherEditTaskDescriptor.name)
                    && Objects.equals(priority, otherEditTaskDescriptor.priority)
                    && Objects.equals(email, otherEditTaskDescriptor.email)
                    && Objects.equals(address, otherEditTaskDescriptor.address)
                    && Objects.equals(tags, otherEditTaskDescriptor.tags)
                    && Objects.equals(link, otherEditTaskDescriptor.link);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("priority", priority)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("link", link)
                    .toString();
        }
    }
}
