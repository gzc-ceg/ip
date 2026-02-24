package bruno.command;

import bruno.TaskList;
import bruno.Ui;
import bruno.Storage;
import bruno.exception.BrunoException;
import bruno.task.Task;

import java.util.ArrayList;

public class FindCommand extends Command {

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

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
