package bruno;

import bruno.exception.BrunoException;
import bruno.task.Deadline;
import bruno.task.Event;
import bruno.task.Task;
import bruno.task.Todo;

public class Parser {

    public static boolean parse(String input,
                                TaskList tasks,
                                Ui ui,
                                Storage storage) throws BrunoException {

        if (input == null || input.trim().isEmpty()) {
            throw new BrunoException("Please say something, I am listening...");
        }

        String[] parts = input.trim().split(" ", 2);
        String command = parts[0].toLowerCase();

        switch (command) {

        case "bye":
            ui.showBye();
            return true;

        case "list":
            ui.showList(tasks.getAll());
            break;

        case "mark":
            handleMark(parts, tasks, ui, storage);
            break;

        case "unmark":
            handleUnmark(parts, tasks, ui, storage);
            break;

        case "todo":
            handleTodo(parts, tasks, ui, storage);
            break;

        case "deadline":
            handleDeadline(parts, tasks, ui, storage);
            break;

        case "event":
            handleEvent(parts, tasks, ui, storage);
            break;

        case "delete":
            handleDelete(parts, tasks, ui, storage);
            break;

        default:
            throw new BrunoException("I'm sorry, but I don't know what that means :-(");
        }

        return false;
    }

    // ================= MARK =================

    private static void handleMark(String[] parts,
                                   TaskList tasks,
                                   Ui ui,
                                   Storage storage) throws BrunoException {

        int index = parseIndex(parts, tasks);

        Task task = tasks.get(index);
        task.markAsDone();

        ui.showMark(task);
        save(tasks, storage);
    }

    private static void handleUnmark(String[] parts,
                                     TaskList tasks,
                                     Ui ui,
                                     Storage storage) throws BrunoException {

        int index = parseIndex(parts, tasks);

        Task task = tasks.get(index);
        task.markAsNotDone();

        ui.showUnmark(task);
        save(tasks, storage);
    }

    // ================= TODO =================

    private static void handleTodo(String[] parts,
                                   TaskList tasks,
                                   Ui ui,
                                   Storage storage) throws BrunoException {

        checkDescription(parts, "The description of a todo cannot be empty.");

        Task t = new Todo(parts[1].trim());
        tasks.add(t);

        ui.showAdd(t, tasks.size());
        save(tasks, storage);
    }

    // ================= DEADLINE =================

    private static void handleDeadline(String[] parts,
                                       TaskList tasks,
                                       Ui ui,
                                       Storage storage) throws BrunoException {

        checkDescription(parts, "The description of a deadline cannot be empty.");

        String[] deadlineParts = parts[1].split("/by", 2);

        if (deadlineParts.length < 2) {
            throw new BrunoException(
                    "Invalid deadline format. Please use: deadline [description] /by [time]");
        }

        Task t = new Deadline(
                deadlineParts[0].trim(),
                deadlineParts[1].trim());

        tasks.add(t);

        ui.showAdd(t, tasks.size());
        save(tasks, storage);
    }

    // ================= EVENT =================

    private static void handleEvent(String[] parts,
                                    TaskList tasks,
                                    Ui ui,
                                    Storage storage) throws BrunoException {

        checkDescription(parts, "The description of an event cannot be empty.");

        String[] eventParts = parts[1].split("/from|/to");

        if (eventParts.length < 3) {
            throw new BrunoException(
                    "Invalid event format. Please use: event [description] /from [start] /to [end]");
        }

        Task t = new Event(
                eventParts[0].trim(),
                eventParts[1].trim(),
                eventParts[2].trim());

        tasks.add(t);

        ui.showAdd(t, tasks.size());
        save(tasks, storage);
    }

    // ================= DELETE =================

    private static void handleDelete(String[] parts,
                                     TaskList tasks,
                                     Ui ui,
                                     Storage storage) throws BrunoException {

        int index = parseIndex(parts, tasks);

        Task removed = tasks.remove(index);

        ui.showDelete(removed, tasks.size());
        save(tasks, storage);
    }

    // ================= STORAGE SAVE =================

    private static void save(TaskList tasks,
                             Storage storage) throws BrunoException {

        try {
            storage.saveTasks(tasks.getAll());
        } catch (Exception e) {
            throw new BrunoException("Error saving tasks to file.");
        }
    }

    // ================= HELPERS =================

    private static int parseIndex(String[] parts,
                                  TaskList tasks) throws BrunoException {

        if (parts.length < 2) {
            throw new BrunoException("Please specify a task number.");
        }

        try {
            int index = Integer.parseInt(parts[1].trim()) - 1;

            if (index < 0 || index >= tasks.size()) {
                throw new BrunoException(
                        "Task number must be between 1 and " + tasks.size());
            }

            return index;

        } catch (NumberFormatException e) {
            throw new BrunoException("Please provide a valid task number.");
        }
    }

    private static void checkDescription(String[] parts,
                                         String errorMsg) throws BrunoException {

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new BrunoException(errorMsg);
        }
    }
}