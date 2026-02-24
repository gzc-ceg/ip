package bruno;

import bruno.command.Command;
import bruno.exception.BrunoException;
import bruno.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Bruno {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    public Bruno(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            ArrayList<Task> loadedTasks = storage.loadTasks();
            tasks = new TaskList(loadedTasks);
        } catch (Exception e) {
            ui.showError("Error loading tasks. Starting with empty list.");
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = scanner.nextLine();
                Command command = Parser.parse(input, tasks);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (BrunoException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Unexpected error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        new Bruno("./data/bruno.txt").run();
    }
}