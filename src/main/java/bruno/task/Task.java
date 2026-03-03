package bruno.task;

/**
 * Represents a general task with a description and completion status.
 * This is the base class for all specific task types (Todo, Deadline, Event).
 */
public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing the task's completion state.
     * [X] for done tasks, [ ] for not done tasks.
     *
     * @return The status icon string.
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Updates the description of the task.
     *
     * @param description The new description for the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the task is completed.
     *
     * @return true if the task is marked as done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns a string representation of the task, including its status icon and description.
     *
     * @return The formatted string representation of the task.
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}
