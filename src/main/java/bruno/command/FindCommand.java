package bruno.command;

import bruno.TaskList;
import bruno.Ui;
import bruno.Storage;
import bruno.exception.BrunoException;
import bruno.task.Task;

import java.util.ArrayList;

/**
 * Represents a command to search for tasks whose descriptions contain a given keyword.
 */
public class FindCommand extends Command {

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the command by searching through all tasks for those whose description
     * contains the keyword (case-insensitive) and displaying the results.
     *
     * @param tasks The task list to search.
     * @param ui The user interface for displaying search results.
     * @param storage The storage handler (not directly used but required by interface).
     * @throws BrunoException If an error occurs during execution.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BrunoException {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks.getAll()) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        ui.showFindResults(matchingTasks);
    }
}
