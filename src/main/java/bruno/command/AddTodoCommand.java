package bruno.command;

import bruno.Storage;
import bruno.TaskList;
import bruno.Ui;
import bruno.exception.BrunoException;
import bruno.task.Task;
import bruno.task.Todo;

/**
 * Represents a command to add a new to-do task to the task list.
 */
public class AddTodoCommand extends Command {

    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the command by creating a new Todo task, adding it to the task list,
     * showing a confirmation, and saving the updated list to storage.
     *
     * @param tasks The task list to modify.
     * @param ui The user interface for displaying messages.
     * @param storage The storage handler for data persistence.
     * @throws BrunoException If an error occurs during saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BrunoException {
        Task task = new Todo(description);
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