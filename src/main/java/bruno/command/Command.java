package bruno.command;

import bruno.Storage;
import bruno.TaskList;
import bruno.Ui;
import bruno.exception.BrunoException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BrunoException;
    public boolean isExit() {
        return false;
    }
}