# Bruno User Guide

![img_1.png](img_1.png)
Bruno is a desktop task management assistant​ that helps you manage your tasks efficiently via a Command-Line Interface (CLI). It's optimized for fast typists who prefer keyboard input.

## Features
### Viewing all tasks: list
Shows a numbered list of all your tasks.

Example: `list`
```
    Here are the tasks in your list:
    1.[T][X] read books
    2.[D][ ] homework (by: 2pm)
```
### Adding a to-do task
Adds a simple task without any date/time constraints.

Fromat: `todo DESCRIPTION`

Example: `todo read books`
```
    Got it. I've added this task:
      [T][ ] read books
    Now you have 1 tasks in the list.
```
### Adding deadlines
Adds a task with a specific due date.

Format: `deadline DESCRIPTION /by TIME`

Examples: 
1. TIME is a parable date (YYYY-MM-DD):

`deadline submit report /by 2026-03-15`
```aiignore
    Got it. I've added this task:
      [D][ ] submit report (by: Mar 15 2026)
    Now you have 2 tasks in the list.
```

2. TIME is not a parsable date:

`deadline submit report /by this sunday`

```aiignore
    Got it. I've added this task:
      [D][ ] submit report (by: this sunday)
    Now you have 3 tasks in the list.
```

### Adding events
Adds a task that occurs over a period of time.

Format: `event DESCRIPTION /from START_TIME /to END_TIME`

Example: `event meeting /from 2026-03-10 /to 2026-03-11`
```aiignore
    Got it. I've added this task:
      [E][ ] meeting (from: Mar 10 2026 to: Mar 11 2026)
    Now you have 4 tasks in the list.
```
### Marking tasks as done
Marks a specific task as completed.

Format: `mark INDEX`

Example: `mark 2`
```aiignore
    Nice! I've marked this task as done:
    [D][X] submit report (by: Mar 15 2026)
```
### Marking tasks as not done
Marks a specific task as not completed.

Format: `unmark INDEX`

Example: `unmark 2`
```aiignore
    OK, I've marked this task as not done yet:
    [D][ ] submit report (by: Mar 15 2026)
```
### Deleting tasks
Removes a task from your list.

Format: `delete INDEX`

Example: `delete 4`
```aiignore
    Noted. I've removed this task:
      [E][ ] meeting (from: Mar 10 2026 to: Mar 11 2026)
    Now you have 3 tasks in the list.
```
### Finding tasks
Searches for tasks containing a specific keyword (case-insensitive).

Format: `find KEYWORD`

Example: `find report`
```aiignore
    Here are the matching tasks in your list:
    1.[D][ ] submit report (by: Mar 15 2026)
    2.[D][ ] submit report (by: this sunday)
```
### Exiting the program: bye
Saves all your tasks and closes the application.

Example: `bye`
```aiignore
    Bye! Hope to see you again!
    Remember, Bruno is always here for you!
```
## Data Storage
Bruno automatically saves your tasks to a file (./data/bruno.txt) in the background. Your data is preserved between sessions.

Example:
```aiignore
T | 0 | read books
D | 0 | submit report | 2026-03-15
D | 1 | submit report | this sunday
E | 0 | meeting | 2026-03-10 to 2026-03-11
```
