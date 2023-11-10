---
layout: page
title: ProfPlan User-Guide
---
<style>
  .alert-primary{
      background-color: #d6e6ff;
      padding: 0.75rem 1.25rem;
      color: #073984;
      border-radius: 0.25rem;
  }

  .Warning{
      background-color: #fff3cd;
      padding: 0.75rem 1.25rem;
      color: #856404;
      border-radius: 0.25rem;
  }
</style>

# User guide
## Overview

ProfPlan is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, ProfPlan can get your contact management tasks done faster than traditional GUI apps.

We believe that no CS professor should have to wrestle and wrangle with redundant features and complicated interfaces. Built upon hours of research, ProfPlan offers a fast, efficient and seamless user experience for you to resolve your schedules in minimal time.


## What It Does
ProfPlan can help you manage tasks seamlessly. Create, edit, delete tasks with ease. Our specialised tasks help you assign priorities, deadlines, status and much more to enhance your task management experience.

Our smart recommendation and visualisation features help you decide what to do next, and rank tasks based on several factors. You can also manage recurring tasks smoothly using ProfPlan’s recurring task functionality.

## Target Users
This product is specifically designed for **CS professors** who prefer **CLI** over GUI and have a **variety of tasks** such as module management (lecture, assessments, readings), research tracking (deadlines, publications, collaborations, papers), admin tasks (department, paperwork, meetings, budget) and so on.

## Team Behind ProfPlan
![TeamProfPlan](images/TeamProfPlan.png)
Contact us at [teamprofplan@gmail.com](mailto:teamprofplan@gmail.com) to connect with us and more!

--------------------------------------------------------------------------------------------------------------------
## Contents
* [Overview](#overview)
* [ProfPlan: Tasks and You](#profplan-tasks-and-you)
* [Quick Start](#quick-start)
* [Before you start](#before-you-start)
* [Basic Features](#basic-features)
    1. [Viewing help : help](#viewing-help--help)
        <ol type="a">
        <li><a href="#a-viewing-help-for-a-specific-command">Viewing help for a specific command</a></li>
        </ol>
    1. [Create a new task : add](#create-a-new-task--add)
    1. [Edit existing tasks : edit](#edit-existing-tasks--edit)
    1. [Delete tasks and delete all tasks : delete](#delete-tasks-and-delete-all-tasks--delete)
    1. [List all Tasks : list](#list-all-tasks--list)
    1. [Locating Tasks by name: find](#locating-tasks-by-name-find)
    1. [Editing the data file](#editing-the-data-file)
    1. [Saving the data](#saving-the-data)
    1. [Exiting the program : exit](#exiting-the-program--exit)
* [Task Management Features](#task-management-features)
    1. [Mark task as done/undone : mark / unmark](#mark-task-as-doneundone--mark--unmark)
    1. [Filter tasks : filter](#filter-tasks--filter)
        <ol type="a">
        <li><a href="#a-due-date">Due Date</a></li>
        <li><a href="#b-priority">Priority</a></li>
        <li><a href="#c-status">Status</a></li>
        </ol>

    1. [Set other tasks as parent : set](#set-other-tasks-as-parent--set)
    1. [Priority](#priority)
    1. [Due Date](#due-date-1)
    1. [Tags](#tags)
    1. [Link](#link)
* [Advanced Features](#advanced-features)
    1. [Recommend next task :](#recommend-next-task)
    1. [Visualise important and urgent tasks :](#visualise-important-and-urgent-tasks)
    1. [Create Recurring task :](#create-recurring-task)
    1. [Sort Tasks according to date, priority and status :](#sort-tasks-according-to-date-priority-and-status)
    1. [View Task statistics: stats](#view-task-statistics-stats)
* [Supported flags](#supported-flags)
* [Command summary](#command-summary)
* [Supported Setting Parameters](#supported-setting-parameters)
* [Glossary](#glossary)
* [Contact Us for Feedback & More!](#contact-us-for-feedback--more)


--------------------------------------------------------------------------------------------------------------------
## ProfPlan: Tasks and You
Your central interaction with ProfPlan is through the creation and management of Tasks.

:question: So, what is a Task in ProfPlan?

A Task contains all the information relevant to a piece of work that needs to be done. This work could be anything: academia-related, like research projects and papers, teaching-related, like grading and lecturing, or even personal tasks, like a daily brew of coffee.

:question: What information does a Task contain?

A Task must contain, minimally, a <ins>Name</ins>. Optionally, you may also choose to add a <ins>Priority</ins>, a <ins>Link</ins>, a <ins>Due Date</ins>, and/or a <ins>Description</ins> to it. Refer to the add and edit commands below for details on how to achieve this.

:question: What are all those underlined terms?

These are the various attributes of a Task:
* <ins>Name</ins>: A short summary of the work. Consider this akin to the title of a book, or an abstract of a paper - concise, yet descriptive.
* <ins>Priority</ins>: An integer from 1 to 10, indicative of the importance of the task. The higher the number, the more important the task.
* <ins>Status</ins>: Represents the current completion state of a task. (Done or Undone)
* <ins>Link</ins>: A hyperlink to an external URL.
* <ins>Due Date</ins>: The date by which the task should be completed by. Presented in the dd-MM-yyyy format.
* <ins>Description</ins>: Further details about the task. Any information that is too long to be put into the name should go here.

:question: What else can I do with Tasks?

Just like on a checklist, Tasks can be [marked](#mark-task-as-doneundone--mark--unmark) as done or undone, or you can create associations between tasks by [setting](#set-other-tasks-as-parent--set) a Task as a parent of another. To better navigate your Task list, you may also [filter](#filter-tasks--filter) or [search](#locating-tasks-by-name-find) for Tasks.

:question: What really makes ProfPlan standout?
This is but the tip of the iceberg of the things you can do working with ProfPlan.

ProfPlan allows you to create recurring tasks with frequency of a day, week or month. With automatically shifting deadlines, you never have to spend time setting up repetitive tasks again.

Puzzled about which task to do next? Worry not, ProfPlan has you covered.

To explore more, visit [this section](#advanced-features) for more advanced tips.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have `Java 11` or above installed in your Computer.

1. Download the latest `profplan.jar` from [here](https://github.com/AY2324S1-CS2103T-W15-1/tp/releases).

1. Copy the file to the folder you want to use as the home folder for your ProfPlan.
1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar profplan.jar` command to run the application.<br>
A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.
![NewLaunch](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing `help` and pressing Enter will open the help window.
Some example commands you can try:
    * `list` : Lists all tasks.
    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a task named John Doe to ProfPlan.
    * `delete 3` : Deletes the 3rd task shown in the current list.
    * `delete all` : Deletes all tasks.
    * `exit` : Exits the app.
1. Refer to the [Features](#basic-features) below for details of each command.


--------------------------------------------------------------------------------------------------------------------
## Before you start
<div markdown="block" class="alert alert-primary">
:blue_book: Notes about the command format:

* Words in square brackets are the parameters you should supply to ProfPlan.
    * Example: `task [taskToDo] /by [deadline]`
* Items in braces (`{ }`) are optional.
    * Example: `help {command name} `
* Items with … after them can accept multiple parameters.
    * Example: `find [keywords…]`
* Extraneous parameters for commands that do not take in parameters (such as `list`, `exit`, and `clear`) will be ignored.
    * Example: `list 123` will be interpreted as `list`
* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines, as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>
--------------------------------------------------------------------------------------------------------------------

## Basic Features
Simple yet essential features for you to get started.

### Create a new task : `add`
Creates a new task and adds it to your task list. You may specify the name and deadline for the task when creating it.<br>
**Command Format:** `task [taskToDo] /by [deadline]` <br>
**Acceptable Values for each Parameter:**<br>
&emsp;`[taskToDo]` - String of len > 0<br>
&emsp;`[deadline]` - String of len > 0<br>
**Example Commands:** `task submit quiz /by 02 Sep 2023`<br>

**Precise Expected Outputs when the command succeeds:**
```
Task successfully created, Prof! Here is your current task list:
	1. submit quiz (by - 02 Sep 2023)
```

**Precise Expected Outputs when the command fails:**
```
Unable to create Task, Prof. Try entering in this format task [taskToDo] /by [deadline] and ensure that parameter lengths are > 0.
```


### Edit existing tasks : `edit`
Edit the aspects of selected existing task, as specified by the user.

**Command Format:** `edit [index] /[aspect] [new value]` <br>
**Acceptable Values for each Parameter:** <br>
&emsp;`[index]` - integer <br>
&emsp;`[aspect]` - String: “name”, “priority”, “category”, “parent”, or “link” <br>
&emsp;`[new value]`- String based on input for [aspect] <br>
**Example Commands:** `edit 2 /name Updated task /priority 3` <br>

**Precise Expected Outputs when the command succeeds:**
```
Your task has been changed from:
	1. submit quiz (by - 02 Sep 2023)
to:
	1. Submit assignment (by - 02 Sep 2023)
```

**Precise Expected Outputs when the command fails:**
```
Unable edit task. Check task index, or format of [aspect].
Try entering in this format edit 1 /[aspect] [new value], with aspects being “name”, “priority”, “category”, “parent”, or “link”
```



### Delete tasks and delete all tasks : `delete`
The Delete Task feature allows you to remove a specific task from your task list when it is no longer relevant or even remove all the tasks if required.

**Command Format:** `delete [taskNumber]` <br>
**Command Format:** `delete all` (for delete all) <br>
**Example Commands:** <br>
&emsp; `delete 2` (To delete a task present at index number 2) <br>
&emsp; `delete all` (To delete all the tasks present in the list) <br>
**Acceptable Values for each Parameter:** <br>
&emsp; `[taskNumber]` - Integer, that is a valid task number in the list <br>
&emsp; `“all”` - A keyword (type String) to delete all the tasks present in the list. <br>

**Precise Expected Outputs when the command succeeds:** <br>

(For Deleting a particular Task)
```
Deleted Task: [Task Description]
[Displays the list with the specific task deleted from the list].
```

(For Deleting all the Tasks in the list)
```
All Tasks Deleted Successfully Prof!
```
**Precise Expected Outputs when the command fails:** <br>
(If provided with an invalid tasknumber which is not in the list or even a negative index number
the following message will be printed)
```
Please enter a valid Task number.
```
**Precise Expected Outputs when the command fails: (for delete all command)** <br>
```
Can not delete all tasks in empty Task List
```


### List all Tasks : `list`
Shows a list of all tasks in ProfPlan.. <br>
**Command Format:** `list` <br>
**Precise Expected Outputs when the command succeeds:** <br>
```
Displays the contents of Task List.
```


### List Tasks within a week from now: `list_week`
Shows a list of tasks within a week from now in ProfPlan.. <br>
**Command Format:** `list_week` <br>
**Precise Expected Outputs when the command succeeds:** <br>
```
Here are your tasks within a week Prof!
[Displays the contents of Task List.]
```


### List Tasks within a month from now: `list_month`
Shows a list of tasks within a month from now in ProfPlan. <br>
**Command Format:** `list_week` <br>
**Precise Expected Outputs when the command succeeds:** <br>

```
Here are your tasks within a month Prof!
[Displays the contents of Task List.]
```


### Locating Tasks by name: `find`
Finding a task is user-friendly and efficient. It's case-insensitive, allowing "task" to match "Task." Keyword order is 
flexible, 
and only the task name is considered. Full words are matched, so "Tas" won't match "Task." The search operates on an OR logic, returning tasks matching at least one keyword. This ensures a simple and effective task-finding process. <br>
**Command Format:** `find [KEYWORD]` <br>
**Acceptable Values for each Parameter:** <br>
&emsp;`[TaskName]` - A task name that matches in the TaskList. <br>
**Example Commands:** <br>
&emsp;`find quiz` (returns quiz grading task and make a quiz) <br>
&emsp;`find project` (returns project grading) <br>
**Precise Expected Outputs when the command succeeds:** <br>
(For Finding a particular Task)
```
[Task Description] Here is your updated list prof!, found 3 tasks.-
[Displays the list with the specific tasks found.].
```
**Precise Expected Outputs when the command fails:** <br>
(If provided with an invalid tasknumber which is not in the list or even a negative index number
the following message will be printed)
```
No Matching Tasks found prof.
```



### Editing the data file
ProfPlan data is saved automatically as a JSON file `[JAR file location]/data/profplan.json`. Advanced users are welcome to update data directly by editing that data file

<div markdown = 'block' class = 'Warning' > :warning: Caution: If your changes to the data file makes its format invalid, ProfPlan will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it
</div>

### Saving the data
ProfPlan data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Exiting the program : `exit`
Exits the program. <br>
Format: `exit`


### Viewing help : `help`
Shows a list of commands.
**Command Format:**
`help`
#### a. Viewing help for a specific command:
&emsp;Shows the description and usage of the command you specified. <br>
&emsp;**Command Format:** `help {command name}` <br>
&emsp;**Example Command:** `help add`


### Configuring Settings : `set`
Allows you to configure certain parameters as you see fit. <br>
Command Format: `set [parameterName] [value]`

For a list of the currently supported parameters, click [here](#supported-setting-parameters).
To make ProfPlan more customizable, we are looking to add more in the future, so stay tuned!



## Task Management Features
Recommended features for you to get more out of ProfPlan

### Mark task as done/undone : `mark / unmark`
**What it does:** <br>
&emsp;Allows you to easily track the completion status of your tasks. You can mark a task as done when you have completed it, and mark it as undone if you need to revisit or revise the same task. <br>

**Command Format:** <br>
&emsp;To mark Task as done: `mark [tasknumber]` <br>
&emsp;To mark Task as undone: `unmark [tasknumber]` <br>

**Acceptable Values for each Parameter:** <br>
&emsp;`[taskNumber]` - Integer representing the task number in your task list (0,1, 2, 3, ...) <br>

**Example Commands:** <br>
&emsp;`mark 5` <br>
&emsp;`unmark 4` <br>

**Precise Expected Outputs when marking a task as done:** <br>
When you mark Task as done, you will receive the following confirmation message:
```
Task successfully marked as done, Prof! Here is your updated task list :
  /*This message will display the current TaskList after the command is executed*/
```

**Precise Expected Outputs when marking a task as undone:** <br>
When you mark a task as undone, you'll receive the following confirmation message:
```
Task successfully marked as undone, Prof! Here is your updated task list:
  /*This message will display the current TaskList after the command is  executed*/
```

**Precise Expected Outputs when the command fails:** <br>
If you provide an invalid task number (e.g., a task number that does not exist in your task list),
you will receive the following error message: (both for mark and unmark command)
```
Task not found please enter a valid Task Number.
```

### Filter tasks : `filter`
#### a. Due Date:
&emsp;**What it does:**<br>
&emsp;&emsp;All tasks that fall before the given due date is displayed<br>

&emsp;**Command Format:** `filter d/[date]` <br>
&emsp;**Example Commands:** `filter d/01-01-2000`<br>
&emsp;**Acceptable Values for each Parameter:** <br>
&emsp;&emsp;`[date]` - In dd-MM-yyyy format. <br>


**Precise Expected Outputs when the command succeeds:**
```
Here are your tasks that are:
Due before: [date]
[Displays the list with the specific tasks found.]
```


#### b. Priority
&emsp;**What it does:** <br>
&emsp;&emsp;All tasks of the given priority is displayed. <br>

&emsp;**Command Format:** `filter p/[priority]` <br>
&emsp;**Example Commands:** `filter p/3` <br>
&emsp;**Acceptable Values for each Parameter:** <br>
&emsp;&emsp;`[priority]` - Integer from 1 to 10 inclusive. <br>

**Precise Expected Outputs when the command succeeds:**
```
Here are your tasks that are:
Priority: [priority]
[Displays the list with the specific tasks found.]
```

#### c. Status
&emsp;**What it does:** <br>
&emsp;All tasks of the given status is displayed.

&emsp;**Command Format:** `filter s/[status]` <br>
&emsp;**Example Commands:** `filter s/done` <br>
&emsp;**Acceptable Values for each Parameter:** <br>
&emsp;&emsp;`[status]`- done or undone <br>

**Precise Expected Outputs when the command succeeds:**
```
Here are your tasks that are:
Status: [status]
[Displays the list with the specific tasks found.]
```

#### d. Recurrence
&emsp;**What it does:** <br>
&emsp;All tasks of the given recurring type is displayed.

&emsp;**Command Format:** `filter recur/[recurringType]` <br>
&emsp;**Example Commands:** `filter recur/weekly` <br>
&emsp;**Acceptable Values for each Parameter:** <br>
&emsp;&emsp;`[recurringType]`- none, daily, weekly, monthly, semesterly <br>

**Precise Expected Outputs when the command succeeds:**
```
Here are your tasks that are:
Recurring: [recurringType]
[Displays the list with the specific tasks found.]
```

#### e. Combination of the above
&emsp;**What it does:** <br>
&emsp;All tasks meeting the specified criteria is displayed.

&emsp;**Command Format:** `filter d/[duedate] p/[priority] s[status] recur/[recurringType]` <br>
&emsp;**Example Commands:** `filter p/3 recur/weekly` <br>

**Precise Expected Outputs when the command succeeds:**
```
Here are your tasks that are:
Priority: [priority]
Status: [status]
Due before: [dueDate]
Recurring: [recurringType]
[Displays the list with the specific tasks found.]
```

**Precise Expected Outputs when the command fails:**
```
Invalid command format! 
filter: Filters for tasks with one or more criteria and displays them as a list with index numbers.
Parameters: d/DUEDATE p/PRIORITY recur/RECUR s/STATUS
Example: filter d/01-01-2024 s/done
```


### Priority
Tasks have priorities that can be assigned upon task adding, and also edited.
Unassigned priorities will have the value `000`. <br>
**Valid format:** `p/[Integer from 1 to 10 inclusive]` <br>

### Due Date
You can specify the due date of a task upon creation and edit. <br>
Unassigned due dates will have the value `01-01-2000`. <br>
**Valid format:** `d/dd-MM-yyyy` <br>

### Tags
You can assign tags to a task, to further segregate and classify them. <br>
**Valid format:** `t/[tag]` <br>

### Link
You can assign a link to a task, to access the reference easily. <br>
**Valid format:** `l/[www.LINKNAME.com]` <br>

## Advanced Features
Psst! Were the features mentioned in the introduction not quite enough to satiate your craving for productivity? Fret not! ProfPlan comes with novel capabilities that will reinvent the way you approach tasks! Before we end this guide, we’ll show you some ways our veteran users use ProfPlan to make themselves more productive than ever before!


### Recommend next task :
Ever felt overwhelmed by a mountain of work, and lost on what to do next? Many tasks, each with slightly different deadlines and priorities, can come together to overload our capacities for good planning, and make it impossible for us to choose the optimal task to work on next.<br>
With our customised algorithm, ProfPlan takes this cognitive load off your shoulders. Simply use the command do_next, and ProfPlan will identify the ideal task for you to work on next!<br>
**Valid Format:** `do_next` <br>
**Expected Output:** <br>
`Here is the next task you need to do Prof:
Grade assignments, Priority: 10, DueDate: 02-11-2023`


### Visualise important and urgent tasks :
Sometimes, we might have 20, 30, 40 or more tasks in our lists!
It can be extremely difficult to prioritize and visualize them based on what to do next.
Fret not! With the Urgency-Priority Matrix, you can constantly visualize your tasks with ease.<br>
The matrix is updated automatically whenever there's any change to your taskList.<br><br>

The columns represent the different Urgency Levels: `1-10`
<br>
The rows represent the different Priority Levels: `1-10`

The higher the urgency and the priority, the more important is the task.
![img.png](matrix.png)

### Create Recurring task :


### Sort Tasks based on priority :
ProfPlan arranges your tasks in decreasing order of priority. It's like a wizard's duel, with the mightiest spells taking the center stage. The high-priority tasks take their rightful place at the top of the list, ready to be conquered.
**Valid Format:** `sort_priority` <br>
**Expected Output:** 
` Here is your task list Prof, sorted based on priority` <br>
**Expected Output (when command fails):** (i.e. When there is no tasks in the list) <br>
`Can not sort an empty task list.`

### Sort Tasks according to deadline:
TaskMagic weaves its duedate magic. It sorts your tasks by nearest due date. This means the tasks with the nearest deadlines are revealed like shining stars, beckoning you to attend to them next.
**Valid Format:** `sort_duedate` <br>
**Expected Output:**
` Here is your task list Prof, sorted based on nearest deadline` <br>
**Expected Output (when command fails):** (i.e. When there is no tasks in the list) <br>
`Can not sort an empty task list.`

### View Task statistics: stats

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that
contains the data in the previous ProfPlan home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Supported flags


| **Name of flag** | **Flag in command** |                **Description** |
|------------------|:-------------------:|-------------------------------:|
| Name             |         n/          |           The name of the task |
| Due Date         |         d/          |       The due date of the task |
| Priority         |         p/          |       The priority of the task |
| Tag              |         t/          | The tag that the task is under |
| Link             |         l/          |           The link of the task |
| Recur            |       recur/        | The recurring type of the task |
| Description      |        des/         |    The description of the task |


## Command summary


| **Action**        | **Format, Examples**                                                            |
|-------------------|---------------------------------------------------------------------------------|
| **Add**           | `add n/[name] p/[priority]...`, e.g. `add n/Task p/1 d/01-01-2023 recur/weekly` |
| **Edit**          | `edit [INDEX] n/[name] p/[priority]...`, e.g. `edit 1 n/Task p/1 d/01-01-2023`  |
| **Find**          | `find [keywords...]`, e.g. `find canvas quiz`                                   |
| **List Week**     | `list_week`                                                                     |
| **List Month**    | `list_month`                                                                    |
| **Mark**          | `mark [INDEX]`, e.g. `mark 2`                                                   |
| **Unmark**        | `unmark [INDEX]`, e.g. `unmark 2`                                               |
| **Delete**        | `delete [INDEX]`, e.g. `delete 2`                                               |
| **Sort Priority** | `sort_priority`                                                                 |
| **Sort Duedate**  | `sort_duedate`                                                                  |
| **Filter**        | `filter d/[duedate] s/[status]...`, e.g. `filter s/done p/4`                    |
| **Help**          | `help`                                                                          |

## Supported Setting Parameters
| **Parameter**    | **Description**                   | **Valid values**         | **Default value** |
|------------------|-----------------------------------|--------------------------|-------------------|
| **semesterDays** | The number of days in a semester. | Any non-negative integer | 180               |

## Glossary
| **Word**     | **Definition**                                                                            |
|--------------|-------------------------------------------------------------------------------------------|
| **Syntax**   | The way that you should format your commands <br> such that ProfPlan can understand them. |
| **Status**   | Whether a task is done or undone                                                          |
| **Priority** | Importance of a task (different from due date)                                            |

## Contact Us for Feedback & More!
We're delighted to hear from you! At ProfPlan, we value your thoughts, inquiries, and suggestions. Your feedback helps us continually improve and provide you with the best possible experience. Whether you have a question, need assistance, or want to share your insights, we're here to assist.

Kindly fill in the form below and we will get back to you as soon as possible.

Form Link : https://forms.gle/Dzb12Re4MYJxzf8w6
