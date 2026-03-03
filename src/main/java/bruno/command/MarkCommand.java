package bruno.command;

import bruno.Storage;
import bruno.TaskList;
import bruno.Ui;
import bruno.exception.BrunoException;
import bruno.task.Task;

/**
 * Represents a command to mark a task as completed in the task list.
 */
public class MarkCommand extends Command {

    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the command by marking the specified task as completed,
     * showing a confirmation, and saving the updated list to storage.
     *
     * @param tasks The task list containing the task to mark.
     * @param ui The user interface for displaying messages.
     * @param storage The storage handler for data persistence.
     * @throws BrunoException If the index is invalid or saving fails.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BrunoException {
        Task task = tasks.get(index);
        task.markAsDone();
        ui.showMark(task);

        try {
            storage.saveTasks(tasks.getAll());
        } catch (Exception e) {
            throw new BrunoException("Error saving tasks to file.");
        }
    }
}
