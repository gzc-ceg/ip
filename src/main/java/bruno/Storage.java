package bruno;

import bruno.task.*;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to a file.
 * Manages the persistence layer of the application.
 */
public class Storage {

    private final String filePath;

    /**
     * Constructs a Storage object with the given file path.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file. Creates the file and directory if they do not exist.
     *
     * @return An ArrayList containing all loaded tasks.
     * @throws Exception If an I/O error occurs during file creation or reading.
     */
    public ArrayList<Task> loadTasks() throws Exception {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        ArrayList<Task> tasks = new ArrayList<>();

        if (!file.exists()) {
            file.createNewFile();
            return tasks;
        }

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            Task task = parseLine(sc.nextLine());
            if (task != null) tasks.add(task);
        }
        sc.close();
        return tasks;
    }

    /**
     * Parses a single line from the storage file into a Task object.
     * Silently ignores malformed lines.
     *
     * @param line A line from the storage file in the format "type | status | description | extra".
     * @return The parsed Task object, or null if the line is malformed.
     */
    private Task parseLine(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String desc = parts[2];
            Task task;

            switch (type) {
            case "T":
                task = new Todo(desc);
                break;
            case "D":
                String by = parts[3];
                task = new Deadline(desc, by);
                break;
            case "E":
                String[] times = parts[3].split(" to ");
                task = new Event(desc, times[0], times[1]);
                break;
            default:
                return null;
            }

            if (isDone) task.markAsDone();
            return task;
        } catch (Exception e) {
            return null; // skip corrupted lines safely
        }
    }

    /**
     * Saves the current list of tasks to the storage file.
     *
     * @param tasks The list of tasks to save.
     * @throws Exception If an I/O error occurs during writing.
     */
    public void saveTasks(ArrayList<Task> tasks) throws Exception {
        FileWriter fw = new FileWriter(filePath);

        for (Task task : tasks) {
            fw.write(formatTask(task) + System.lineSeparator());
        }

        fw.close();
    }

    /**
     * Formats a Task object into a string for storage.
     *
     * @param task The Task to format.
     * @return A string representation of the task suitable for file storage.
     */
    private String formatTask(Task task) {
        String done = task.isDone() ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + done + " | " + task.getDescription();
        }

        if (task instanceof Deadline d) {
            return "D | " + done + " | " + d.getDescription() + " | " + d.getBy();
        }

        if (task instanceof Event e) {
            return "E | " + done + " | " + e.getDescription() + " | "
                    + e.getFrom() + " to " + e.getTo();
        }

        return "";
    }
}