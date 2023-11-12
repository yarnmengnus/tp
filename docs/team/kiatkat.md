---
layout: page
title: Kiat Win's Project Portfolio Page
---

## Project: ProfPlan

ProfPlan is a desktop productivity application written in Java, used for task management and organization. The user
interacts with it using a CLI, and it has a GUI created with JavaFX.

Listed below are my contributions to the project, with the relevant pull requests linked where possible.

**Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=kiatkat&breakdown=true)

### New Features
* Added the ability to create recurring tasks. [#130](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/130)
  * What it does: Allows the user to denote a task as a recurring task, a new type of task that is never entirely 
    completed. Rather, marking this type of task as done will push its due date into the future by an amount 
    depending on its type.
  * Justification: This feature better reflects the needs of most users, as a large number of tasks are cyclical in 
    nature and repeat on a periodic basis. The application can then better model these tasks and provide a more 
    natural means for the user to manage them.
  * Highlights: This enhancement directly affects the `add` command, adding directly onto its implementation. A 
    thorough consideration of design options was necessary to determine a solution which could seamlessly integrate 
    with the `add` command and which had minimal overhead, while still providing extensibility and flexibility to 
    its own usage.
  * Credits: Code written for this feature is original.

* Added the ability for the user to change some defined settings to customize their experience. [#138](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/138)
  * What it does: Allows the user to change certain global parameters that affect the behaviour of other commands 
    via the `set` command.
  * Justification: Different users may desire different parameters for some commands, and wish to customize the 
    application to suit their unique needs.
  * Highlights: This feature required changes across the codebase in multiple regions of the architecture. A 
    `settings.json` file must be created to maintain these user configurations through sessions, and so various 
    infrastructure must be set up to properly store and read from this file. Furthermore, the user has to be able to 
    modify the values in this file, and any command must be able to read these settings. Careful thought had to be 
    given to the structure and design of this system to reduce dependencies across the codebase.
  * Credits: The design of the `UserConfigs` system is similar to AB3's `UserPrefs` in the interest of consistency. 
    However, `UserConfigs` and its related classes are independent of the existing systems, and all such code 
    written is otherwise original.
  * Note: The `set` feature initially implemented (pre-v1.3) is a different feature from the current `set` feature, 
    and it has since been removed. As I was responsible for both of these features, past pull requests may mention 
    this dated `set` feature. All further mentions of `set` in this document are meant to refer to the current 
    implementation (settings) as described above.

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

### Documentation

#### User Guide

* Added documentation for the features `find` and `set` [#22](https://github.com/AY2324S1-CS2103T-W15-1/tp/issues/22)
* Added documentation for the `recur/` flag and all corresponding mentions of recurring tasks
* Added documentation for supported setting parameters
* Added information on the acceptable values for the parameters that the user can input
* Wrote the introductory section, "What can ProfPlan do for you?"
* Contributed to the preambles of some sections under "Advanced Features"
* Made some tweaks to fix language errors

#### Developer Guide

* Modified UML diagrams to reflect the addition of the `UserConfigs` system
* Added use cases specific to `find` and `set`

### Community

* Reviewed PRs (examples: [#104](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/104), [#133](https://github.com/AY2324S1-CS2103T-W15-1/tp/pull/133))
* Reported bugs and suggestions for other teams in the class (examples can be found in the [PE-D](https://github.com/kiatkat/ped/issues))
