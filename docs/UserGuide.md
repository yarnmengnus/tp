---
layout: page
title: User Guide
---

ProfPlan is a **desktop app for managing tasks, optimized for use via a Command Line Interface** (CLI) while still 
having the benefits of a Graphical User Interface (GUI). If you can type fast, ProfPlan can get your task management 
done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `profplan.jar` from [here](https://github.com/AY2324S1-CS2103T-W15-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for ProfPlan.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar profplan.jar` 
   command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it.<br>
   Some example commands you can try:

   * `list` : Lists all tasks.

   * `todo set finals /by 02 Nov 2023`: Adds a task titled `set finals` to the task list.

   * `delete 3` : Deletes the 3rd task shown in the current list.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in square brackets are the parameters to be supplied by the user.<br>
  e.g. in `task [taskToDo] /by [deadline]`, `taskToDo` and `deadline` are parameters which can be used as `add n/John 
  Doe`.

* Items in braces are optional.<br>

* Items with `…` after them can accept multiple parameters. If these items are in braces, they can be used 0 times 
  as well.<br>
  e.g. `[keywords…]` can be used as `quiz`, `mock midterm` etc.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Categorising a task: `categorise`

Puts the task in a category of the task list. If the category specified does not yet exist, it will be created. 

Format: `categorise [taskNumber] /cat [category]​`

* Categorises the task at the specified [taskNumber]. The [taskNumber] refers to the [taskNumber] number shown in the displayed tasks list. The [taskNumber] must be a positive integer 1, 2, 3, … 
* [category] must be a string.
* Existing values will be updated to the input values.

Examples:
* `categorise 2 /cat quiz` Categorises the 2nd task into the `category` quiz.
  ![result for 'categorise 2 /cat quiz'](images/catergoriseTwoCatQuizResult.png)

### Attach links or references to a task: `link`

Attach a URL or external link to the task.

Format: ` link [taskNumber] /link [URL]​`

* Attaches the specified [link] to the task at the specified [taskNumber]. The [taskNumber] refers to the [taskNumber] number shown in the displayed tasks list. The [taskNumber] must be a positive integer 1, 2, 3, … 
* [link] must be a string.
* Existing values will be updated to the input values.

Examples:
* `link 1 /link www.example.com` Attaches the link `www.example.com` to the 1st task.
  ![result for 'link 1 /link www.example.com'](images/linkOneLinkWwwDotExampleDotComResult.png)

### Saving the data

ProfPlan data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ProfPlan data are saved automatically as a JSON file `[JAR file location]/data/ProfPlan.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, ProfPlan will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
