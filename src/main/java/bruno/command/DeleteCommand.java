package bruno.command;

import bruno.Storage;
import bruno.TaskList;
import bruno.Ui;
import bruno.exception.BrunoException;
import bruno.task.Task;

/**
 * Represents a command to delete a task from the task list by its index.
 */
public class DeleteCommand extends Command {

    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the command by removing the specified task from the list,
     * showing a confirmation, and saving the updated list to storage.
     *
     * @param tasks The task list to modify.
     * @param ui The user interface for displaying messages.
     * @param storage The storage handler for data persistence.
     * @throws BrunoException If the index is invalid or saving fails.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BrunoException {
        Task removed = tasks.remove(index);
        ui.showDelete(removed, tasks.size());

        try {
            storage.saveTasks(tasks.getAll());
        } catch (Exception e) {
            throw new BrunoException("Error saving tasks to file.");
        }
    }
}
