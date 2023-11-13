---
layout: page
title: Kiat Win's Project Portfolio Page
---

### Project: ProfPlan

ProfPlan is a desktop productivity application written in Java, used for task management and organization. The user
interacts with it using a CLI, and it has a GUI created with JavaFX.

Listed below are my contributions to the project, with some relevant pull requests linked where possible. ([RepoSense
link to code contributed](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=kiatkat&breakdown=true))

### New Features
* Added the ability to create recurring tasks. [#130](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/130)
  * What it does: Allows the user to create a new type of task that postpones its due date when marked as completed.
  * Justification: A large number of tasks are cyclical in nature. The application can now better model these tasks
    and provide a more natural means for the user to manage them.
  * Highlights: This enhancement directly affects the `add` command, adding directly onto its implementation. A
    thorough consideration of design options was necessary to determine a solution which could do so with minimal
    overhead, while still providing extensibility and flexibility.
  * Credits: Code written is original.

* Added the ability for the user to modify settings. [#138](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/138)
  * What it does: Allows the user to change certain global parameters that affect the behaviour of other commands
    via the `set` command.
  * Justification: Different users may desire different parameters for some commands and wish to customize the
    application to suit their unique needs.
  * Highlights: This feature required changes across the codebase in multiple regions of the architecture. A
    file is created to maintain user configurations between sessions, and the user must be able to modify the
    values in this file, with any command also being able to read these settings. Careful thought had to be given to
    the structure and design of this system to minimize dependencies.
  * Credits: Code written is original.
  * Note: There was a `set` feature of the same name implemented in versions pre-v1.3, which is completely different
    in specification, and has since been removed. All mentions of `set` in this document refer to the implementation
    described above.

* Added the ability for the user to add descriptions. [#105](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/105)
  * What it does: Allows the user to add an associated description field to a task and edit it with the
    `description` command.
  * Justification: Users may want to store information related to the task that is not expressible by the limited
    values of the other fields.
  * Highlights: This feature required good planning to integrate well into the existing structure of a task, while
    maintaining flexibility and keeping dependence on other classes low.
  * Credits: Code written is original.

### Project management and team-based tasks
* Responsible for creating and setting deadlines for all milestones
* Performed most of the renaming required for the initial morph of AB3 into ProfPlan
* Responsible for removing redundancies and cleaning up the codebase [#121](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/121)
  [#137](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/137)
* Consolidated features to be implemented at the beginning of the project and divided work into issues for delegation
* Responsible for the creation and enforcement of early milestones for `v1.2` and `v1.3` to prevent deadline overrun
* Contributed to all team planning, scoping out the breadth and depth of features to be implemented

### Enhancements to existing features
* Updated the `find` feature to new specifications: [#77](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/77)

### User Guide

* Added documentation for the `find` and `set` features ([#22](https://github.com/AY2324S1-CS2103T-W15-1/tp/issues/22)),
  the `recur/` flag and corresponding mentions of recurring tasks, as well as supported setting parameters
* Clarified the acceptable values for the parameters that the user can input
* Wrote the section "What can ProfPlan do for you?" and some sections under "Advanced Features"
* Made some tweaks to fix language errors

### Developer Guide

* Modified UML diagrams to reflect the addition of the `UserConfigs` system
* Added use cases specific to the features implemented above
* Made tweaks to the design and layout and corrected some language errors

### Community

* Reviewed PRs (examples: [#104](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/104), [#133](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/133))
* Reported bugs and suggestions for other teams in the class (examples can be found in the [PE-D](https://github.com/kiatkat/ped/issues))
