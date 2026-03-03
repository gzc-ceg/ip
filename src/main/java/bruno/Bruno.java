package bruno;

import bruno.command.Command;
import bruno.exception.BrunoException;
import bruno.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for the Bruno task management application.
 * Initializes the application, loads existing tasks, and runs the main command loop.
 */
public class Bruno {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a Bruno application instance with the given file path for data storage.
     * Attempts to load tasks from the file; starts with an empty list if loading fails.
     *
     * @param filePath The path to the data file for storing tasks.
     */
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

    /**
     * Runs the main application loop: displays welcome, reads user commands,
     * executes them, and exits when an exit command is issued.
     */
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

    /**
     * Entry point for the application. Creates and runs a Bruno instance.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Bruno("./data/bruno.txt").run();
    }
}