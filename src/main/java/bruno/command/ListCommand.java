package bruno.command;

import bruno.Storage;
import bruno.TaskList;
import bruno.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks.getAll());
    }
}