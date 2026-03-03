package bruno.command;

import bruno.Storage;
import bruno.TaskList;
import bruno.Ui;
import bruno.exception.BrunoException;
import bruno.task.Deadline;
import bruno.task.Task;

/**
 * Represents a command to add a new deadline task to the task list.
 *  This command creates a Deadline object with the specified description
 *  and due date, adds it to the task list, and persists the change.
 */

public class AddDeadlineCommand extends Command {

    private final String description;
    private final String by;

    /**
     * Constructs an AddDeadlineCommand with the given task description and due date.
     *
     * @param description The description of the deadline task.
     * @param by         The due date/time string for the deadline.
     */
    public AddDeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    /**
     * Executes the command by creating a new Deadline task, adding it to the task list,
     * displaying a confirmation message, and saving the updated list to storage.
     *
     * @param tasks   The task list to which the new deadline will be added.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage handler for persisting task data.
     * @throws BrunoException If an error occurs while saving the tasks to storage.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BrunoException {
        Task task = new Deadline(description, by);
        tasks.add(task);
        ui.showAdd(task, tasks.size());
        try {
            storage.saveTasks(tasks.getAll());
        } catch (Exception e) {
            throw new BrunoException("Error saving tasks to file.");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
