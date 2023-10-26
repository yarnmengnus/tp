---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is built on top of the codebase of [AddressBook Level-3 (AB3)](https://github.com/se-edu/addressbook-level3).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------


## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Task` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a task).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the task list data i.e., all `Task` objects (which are contained in a `UniqueTaskList` object).
* stores the currently 'selected' `Task` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Task>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Task` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Task` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both task list data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

## DoNext feature
### Actual implementation
- The DoNext feature in ProfPlan allows users to generate a recommendation of which task to do next. 
- We compute priority/#daysToDeadline for every task, and select the task with the highest computed value as the recommendation. 
- This is because a task is recommended if it has higher priority and lower number of days left to deadline.
- Below, we describe the implementation details for this feature through a uml class and state diagram:

### UML Class Diagram

[//]: # (<div style="display: flex; justify-content: space-between; margin-bottom: 20px;">)

[//]: # (    <div style="flex: 1; margin-right: 5px;">)

[//]: # (        <img src="images/DoNextCommandClassDiagram.png" alt="DoNextCommand Class Diagram" width="380">)

[//]: # (    </div>)

[//]: # (</div>)


The `DoNextCommand` is a part of the ProfPlan application, a task management tool. It allows users to generate next task recommendation. It is one of the standout features of ProfPlan, as it generates smart recommendations for professors.

### Code Structure
The code structure for the `DoNextCommand` is well-organized. It consists of the following components:
- `DoNextCommand` class: Represents the command itself.
- `ProfPlanParser` class: Responsible for parsing user input and creating `DoNextCommand` instances.
- `Priority` class: Represents the priority of a task (from 1 to 10).
- `DueDate` class: Represents the deadline of a task (in dd-MM-yyyy format or '01-02-2023').
- `Task` class: Represents a task, and it contains the priority and dueDate that `DoNextCommand` processes.
- `ModelManager` class: contains the `getDoNextTask()` function to generate recommended task.

### Class Details <a name="class-details"></a>
### `DoNextCommand` <a name="donextcommand"></a>
- Purpose: Represents the `do_next` command that generates next task recommendation.
- Key Methods:
    - `execute(Model model)`: Executes the `DoNextCommand` by computing and output the next task to do.

### `Priority` <a name="priority"></a>
- Purpose: Represents the priority of a task (from 1 to 10).
- Key Methods:
    - `isValidPriority(String test)`: Checks if a given priority is valid according to the predefined regex.

### `DueDate` <a name="duedate"></a>
- Purpose: Represents the deadline of a task (in dd-MM-yyyy format or '01-02-2023').
- Key Methods:
    - `isValidDate(String test)`: Checks if a given date is valid according to the predefined regex.
    - `isIncludedorBefore(DueDate otherDate)`: Checks whether the current date is before, on equals the given date.

### `Task` <a name="task"></a>
- Purpose: Represents a task, and it contains the priority and dueDate.
- Key Methods:
    - `getPriority()`: returns the priority of the task.
    - `getDueDate()`: returns the dueDate of the task.

### `ModelManager` <a name="modelmanager"></a>
- Purpose: Represents a model manager, implements the Model class and carries out core functionality for commands.
- Key Methods:
    - `getDoNextTask()`: returns the recommended task after processing priority/#daysTodueDate for every task.

### Sequence Diagram <a name="sequence-diagram"></a>
The sequence diagram provides an overview of how the `DoNext` is executed and interacts with other components.

[//]: # ()
[//]: # (![Sequence Diagram]&#40;images/DoNextCommandSequenceDiagram.png&#41;)

Here's a breakdown of the sequence:
1. The `LogicManager` receives the command "do_next" from the user.
2. The `ProfPlanParser` parses the command and creates a `DoNextCommand`.
4. The `DoNextCommand` is executed.
5. The `ModelManager` executes the function `getDoNextTask`
6. A `CommandResult` is created to provide feedback to the user, containing the recommended task as a String.
7. The result is returned to the `LogicManager`.

### Usage <a name="usage"></a>
To use the `DoNext` in the ProfPlan application, you can execute the following steps:

1. Enter the "do_next" command.

2. The `DoNext` will handle the task recommendation generation.

3. The application will provide feedback, and output the task generated in the message window.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current task list state in its history.
* `VersionedAddressBook#undo()` — Restores the previous task list state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone task list state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial task list state, and the `currentStatePointer` pointing to that single task list state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th task in the task list. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the task list after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted task list state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new task. The `add` command also calls `Model#commitAddressBook()`, causing another modified task list state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the task list state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the task was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous task list state, and restores the task list to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the task list to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest task list state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the task list, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all task list states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire task list.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the task being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of tasks
* prefer to organise tasks by  and classifications
* want to link tasks together to visualise their relationships
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Professors often need to plan and organise their course modules, including lecture materials, and research. ProfPlan can help them create and manage their academic tasks seamlessly through the CLI, which is optimised and helps in faster task management. It is tailored to professors’ requirements and task categories, helping them prioritise.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …    | I want to …                                                 | So that I can…                          |
|----------|-----------|-------------------------------------------------------------|-----------------------------------------|
| `* * *`  | professor | be able to create a new task                                | keep track of them in a list            |
| `* * *`  | professor | assign priorities to tasks in my list                       | gauge task urgency                      |
| `* * *`  | professor | categorise/tag my tasks                                     | segregate and classify tasks            |
| `* * *`  | professor | mark tasks as done/undone                                   | keep track of what has been completed   |
| `* * *`  | professor | delete tasks                                                | clear up clutter                        |
| `* *`    | professor | filter tasks by due date                                    | manage deadlines effectively            |
| `* *`    | professor | search for tasks                                            | find the task I want to work on quickly |
| `*`      | professor | attach reference materials (filepath) or links to each task | access reference material easily        |
| `*`      | professor | create subtasks within the task list                        | have better organisation                |

### Use cases

For all use cases below, the **System** is `ProfPlan` and the **Actor** is the `user`, unless specified otherwise.


**Use Case: Create a New Task**

**MSS**

1. User requests to create a new task with specified details.
2. ProfPlan validates the input format for the task name and deadline.
3. ProfPlan creates the new task and adds it to the task list.
4. ProfPlan displays a confirmation message with the updated task list.

   Use case ends.

**Extensions**

* **2a.** User enters an invalid task name or deadline format.

    * **2a1.** ProfPlan displays an error message and provides guidance on the correct format.

      *Use case ends.*

* **3a.** ProfPlan encounters an internal error while creating the task.

    * **3a1.** ProfPlan displays an error message indicating the task creation failed.

      *Use case ends.*


**Use case: Setting a task as parent of another task**

Preconditions: There are at least 2 tasks in the task list.

**MSS**

1. User [requests to list tasks]().
2. ProfPlan shows a list of tasks.
3. User requests to set a certain task as the parent of another task in the list.
4. ProfPlan sets the specified relationship.

   Use case ends.

**Extensions**

* 3a. User specifies the same task twice, i.e. tries to set a task as the parent of itself.

  * 3a1. ProfPlan displays an error message.

    Use case ends.

* 3b. User specifies one or more tasks outside the list indices.

  * 3b1. ProfPlan displays an error message.

   Use case ends.

* 3c. User specifies the setting of a task `a` as the parent of another task `b`, when `b` is already the parent of `a`.

  * 3c1. ProfPlan displays an error message.

    Use case ends.

**Use Case: Assign Priority to a Task**

*Preconditions: There is at least one task in the task list.*

**MSS**

1. User requests to assign a priority level to a specific task.
2. ProfPlan validates the input format for the task number and priority level.
3. ProfPlan assigns the specified priority level to the task.
4. ProfPlan displays a confirmation message with the updated task list, including the assigned priority.

   *Use case ends.*

**Extensions**

* **2a.** User enters an invalid task number or priority level format.

    * **2a1.** ProfPlan displays an error message and provides guidance on the correct format.

      *Use case ends.*

* **2b.** User specifies a task number that does not exist in the task list.

    * **2b1.** ProfPlan displays an error message, indicating that the task number is invalid.

      *Use case ends.*

* **3a.** ProfPlan encounters an internal error while assigning the priority.

    * **3a1.** ProfPlan displays an error message indicating the priority assignment failed.

      *Use case ends.*

**Use case: Find a task**

**MSS**

1. User requests to find tasks matching certain search term(s).
2. ProfPlan displays a list of tasks that contain the search term(s).

   Use case ends.

**Extensions**

* 2a. There are no tasks that match the search term(s).

  * 2a1. ProfPlan displays an empty list, and informs the user that the search failed to find matches.

  *Use case ends.*

**Use Case: Mark Task as Done/Undone**

**Preconditions:** There are tasks in the task list.

**MSS**

1. User requests to list tasks.
2. ProfPlan shows a list of tasks.
3. User selects a task to mark as done or undone by specifying its number in the list.
4. ProfPlan updates the task's completion status accordingly.

   Use case ends.

**Extensions**

* 3a. User specifies a task number that is outside the list indices or provides an invalid input.

    * 3a1. ProfPlan displays an error message.

      Use case ends.

* 3b. User attempts to mark a task as done/undone when there are no tasks in the list.

    * 3b1. ProfPlan displays an error message.

      Use case ends.

**Use Case: Delete Task/Delete All Tasks**

**Preconditions:** There are one or more tasks in the task list.

**MSS**

1. User requests to list tasks.
2. ProfPlan shows a list of tasks.
3. User chooses to delete a specific task by specifying its number in the list or requests to delete all tasks.
4. ProfPlan performs the deletion as per the user's request.

   Use case ends.

**Extensions**

* 3a. User specifies a task number that is outside the list indices or provides an invalid input.

    * 3a1. ProfPlan displays an error message.

      Use case ends.

* 3b. User requests to delete a task when there are no tasks in the list.

    * 3b1. ProfPlan displays an error message.

      Use case ends.


**Use Case: Filter task**

**MSS**

1. User requests to filter task based on date.
2. ProfPlan validates the input format for the date.
3. ProfPlan performs the filter and display tasks that fall before the specified date.


   Use case ends.

**Extensions**


* 2a. User provides an invalid date input.

    * 2a1. ProfPlan displays an error message to indicate invalid input.

      Use case ends.

* 3a. User requests to filter tasks when there are no tasks in the list.

    * 3a1. ProfPlan displays an error message to indicate that there are no tasks.

      Use case ends.

* 3b. There are tasks in the list but no tasks before the specified date.

    * 3b1. ProfPlan displays a message to indicate no tasks found.

      Use case ends.

**Use Case: Edit task**

**MSS**

1. User requests to edit a task.
2. ProfPlan validates the input format for the task number and the specified aspect(s).
3. ProfPlan assigns new values to the specified aspects of the task.
4. ProfPlan displays a confirmation message with the original and updated task.


   Use case ends.

**Extensions**

* 2a. User specifies a task number that is outside the list indices or provides an invalid input.

    * 2a1. ProfPlan displays an error message to indicate invalid input.

      Use case ends.

* 2b. User requests to edit a task when there are no tasks in the list.

    * 2b1. ProfPlan displays an error message to indicate that there are no tasks.

      Use case ends.

* 3a. ProfPlan encounters an internal error while updating new values.

    * 3a1. ProfPlan displays an error message to indicate edit task failed.

      Use case ends.

**Use Case: Categorising a Task**

**Preconditions:** There are one or more tasks in the task list.

**MSS**

1. User requests to list tasks.
2. ProfPlan shows a list of tasks.
3. User requests to assign a task to a category.
4. ProfPlan performs the categorisation as per the user's request.
3a. User specifies a task number that is outside the list indices or provides an invalid input.

    * 3a1. ProfPlan displays an error message.

      Use case ends.

* 3b. User requests to categorise a task when there are no tasks in the list.

    * 3b1. ProfPlan displays an error message.

      Use case ends.

* 3c. User requests to assign a task to a category that does not exist.

    * 3c1. ProfPlan creates the requested category.

    * 3c2. ProfPlan performs the categorisation as per the user's request.

      Use case ends.

**Use Case: Attaching a link to a Task**

**Preconditions:** There are one or more tasks in the task list.

**MSS**

1. User requests to list tasks.
2. ProfPlan shows a list of tasks.
3. User requests to attach a link to a task.
4. ProfPlan attaches the link as per the user's request.

* 3a. User specifies a task number that is outside the list indices or provides an invalid input.

    * 3a1. ProfPlan displays an error message.

      Use case ends.

* 3b. User requests to attach a link to a task when there are no tasks in the list.

    * 3b1. ProfPlan displays an error message.

      Use case ends.

* 3c. User requests to attach an invalid URL to a task.

    * 3c1. ProfPlan displays an error message.

      Use case ends.


### Non-Functional Requirements
#### Performance

- **Response Time:** ProfPlan shall respond to user commands within 2 seconds under normal system load conditions. This response time includes task creation, task modification, and task retrieval operations.

- **Scalability:** The application should be able to handle a growing number of tasks and users efficiently. It should scale gracefully without significant performance degradation.

#### Reliability

- **Availability:** ProfPlan should be available for use 24/7, with planned maintenance windows communicated to users in advance. The application should have an uptime of at least 99.5%.

- **Error Handling:** The application should provide informative error messages to users when they input incorrect or invalid commands. It should gracefully handle errors and exceptions without crashing.

#### Security

- **Data Protection:** ProfPlan should ensure the security and confidentiality of user data. User passwords and sensitive information should be stored securely using industry-standard encryption techniques.

- **Authentication:** Users should be required to log in with a valid username and password to access their task lists. Unauthorized access to user data should be prevented.

#### Compatibility

- **Java Version:** ProfPlan should be compatible with Java JDK 11 or higher to ensure optimal performance and support for the latest features and security updates.

#### Usability

- **User-Friendly Interface:** The CLI interface should be intuitive and easy to use, with clear and concise prompts, error messages, and help documentation.

- **Accessibility:** The application should be designed to be accessible to users with disabilities, following best practices for accessibility standards.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Parent**: A `parent` task is the main overarching task
* **Child**: A `child` task a subtask of its `parent` task

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a task

1. Deleting a task while all tasks are being shown

   1. Prerequisites: List all tasks using the `list` command. Multiple tasks in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No task is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
