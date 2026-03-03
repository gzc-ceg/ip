package bruno.command;

import bruno.Storage;
import bruno.TaskList;
import bruno.Ui;
import bruno.exception.BrunoException;
import bruno.task.Event;
import bruno.task.Task;

/**
 * Represents a command to add a new event task to the task list.
 */
public class AddEventCommand extends Command {

    private final String description;
    private final String from;
    private final String to;

    public AddEventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    /**
     * Executes the command by creating a new Event task, adding it to the task list,
     * displaying a confirmation, and saving the updated list to storage.
     *
     * @param tasks The task list to modify.
     * @param ui The user interface for displaying messages.
     * @param storage The storage handler for data persistence.
     * @throws BrunoException If an error occurs during saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BrunoException {
        Task task = new Event(description, from, to);
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