---
layout: page
title: Jackie's Project Portfolio Page
---

# Project: ProfPlan

ProfPlan is a CLI-based task management tool tailored to university professors, offering features like task prioritization, categorization, research tracking, and team coordination to enhance productivity and organization in academia.

## Contributions

**Code contributed:** [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=jack1e0&breakdown=true)

### 1. Features implemented

* **Added the due date field to tasks**
  * What it does: All tasks now have a due date, which signifies by when they should be complete by.
  * Justification: Due dates are integral part of tasks, and as a task management tool, implementing this increases the functionality of the application. It also acts as a basis to support other features, such as recurring tasks and the Urgency-Priority matrix.
  * Credits: Original code

* **Filter tasks function**
  * What it does: Users can filter their tasklist by one or more of the following criteria, namely priority, due date, status, and recurring type.
  * Justification: This will enable users to better categorize and manage their tasks, by grouping them together under common criteria.
  * Credits: Original code

* **List tasks within a week/month from now function**
  * What it does: Users can view what tasks are within a week, or month from the current date.
  * Justification: This will enable users to get a better sense of what tasks are more urgent/coming up.
  * Credits: Original code

### 2. Documentation

**User Guide**
* Added documentation for the `filter`, `list_week` and `list_month` commands.
* Added documentation for the `DueDate` parameter.
* Updated the previous version of other commands in AddressBook, such as `add`, `edit`, etc.
* Added information in the Supported Flags, and Command Summary tables, such that it is up-to-date with the current implementation.
* Contributed to formatting the UG and fixing errors.

**Developer Guide**
* Added detailed documentation for the `filter` command, including an UML class diagram, and code design explanation.
* Added documentation for the Proposed Task Archiving feature, including several sequence diagrams, and proposed implementation for related functions (such as display and restore archived tasks)
* Updated the previous version of the Proposed Undo/Redo feature, such that it is now specific to ProfPlan.
* Added Use Case for filtering tasks.
* In charge of the final formatting and cleaning up.


### 3. Contributions to team-based tasks
* Responsible for creating and setting deadlines for all milestones
* Responsible for merging few PRs to the master branch.
* Contributed to the renaming of AddressBook to ProfPlan.
* Tested all release versions before wrapping up milestones.
* Fixed severe bugs.
* Helped in giving inputs regarding the UI Color Scheme.


### 4. Community
* Reviewed most PRs from teammates
* Reported bugs and suggestions for other teams in the class (during [PE-D](https://github.com/jack1e0/ped/issues))



