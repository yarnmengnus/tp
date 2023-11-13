---
layout: page
title: Yarn Meng's Project Portfolio Page
---

## Overview

ProfPlan is a CLI-based task management tool tailored to university professors, offering features like task prioritization, categorization, research tracking, and team coordination to enhance productivity and organization in academia.

## Contributions
### Code contributed
**[Link to my code on tP Code Dashboard](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=yarnmengnus&breakdown=false)**.

### 1. Features implemented
* **New Feature**: Added the `link` field to tasks [#74](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/74)
  * What it does: Allows the user to include references for the task.
  * Justification: The user (CS professors) often has the need for references. This feature enables the user to view their references at a glance. This makes ProfPlan better tailored to the user's needs.
  * Highlights: This enhancement affects existing `add` and `edit` commands. It required much analysis of the user's needs regarding their needs for references. The chosen implementation takes into account the fact that Professors not only use URLs for referenecs, but also books and other hardcopy literature, etc.
  * Credits: Code written for this feature is original.

* **New Feature**: Added the `stats` command to view statistics [#155](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/155)
  * What it does: Shows the users statistics about their tasks.
  * Justification: This feature allows users to view a high-level summary of their tasks, providing motivation for them to complete them.
  * Credits: Code written for this feature is original.

* **New Feature**: Added significant capabilities to the `help` command [#127](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/127)
  * What it does:
    1. Lists all commands and their usage when no arguments supplied
    1. Shows more details description and usage of commands when a command is supplied as an argument
  * Justification: This feature guides new users to better navigate ProfPlan, instead of diverting users to the user guide. This saves much time for users.
  * Credits: Code written for this feature is original.


* **Project management**:
  * Responsible for uniformity of the User Guide.
  * Contributed to team code reviews while merging ALL PRs.
  * Tested release versions for bugs.
  * Set up physical meetings for code reviews.
  * Assisted in scoping of features in each release.
  * Contributed to milestone and issue categorisation and management.

### 2. Documentation
**User Guide**
  * Added documentation for the `help` and `stats` feature [#114](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/114)
  * Added docutmentation for the Link parameter.
  * Fixed link functionality
  * Fixed visual bugs in the User Guide.
  * In charge of the final formatting and cleaning up.

**Developer Guide**
  * Added  documentation for the `help` command, including an UML activation diagram. [#110](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/110)
  * Added detailed documentation for the `stats` command, including an UML sequence diagram. [#234](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/234)
  * Added Use Case for `help`, `stats` and `link` commands. [#63](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/63)

### Other contributions
* **Community**:
  * Reviewed most PRs from teammates

* **Tools**:
  * Integrated Java Reflections to the project ([#225](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/225))
