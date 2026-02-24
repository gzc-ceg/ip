package bruno;

import bruno.task.*;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
            if (task != null) {
                tasks.add(task);
            }
        }

        sc.close();
        return tasks;
    }

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
                task = new Deadline(desc, parts[3]);
                break;

            case "E":
                String[] time = parts[3].split(" to ");
                task = new Event(desc, time[0], time[1]);
                break;

            default:
                return null;
            }

            if (isDone) {
                task.markAsDone();
            }

            return task;

        } catch (Exception e) {
            // skip corrupted line safely
            return null;
        }
    }

    public void saveTasks(ArrayList<Task> tasks) throws Exception {

        FileWriter fw = new FileWriter(filePath);

        for (Task task : tasks) {
            fw.write(formatTask(task) + System.lineSeparator());
        }

        fw.close();
    }

    private String formatTask(Task task) {

        String done = task.isDone() ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + done + " | " + task.getDescription();
        }

        if (task instanceof Deadline d) {
            return "D | " + done + " | "
                    + d.getDescription() + " | "
                    + d.getBy();
        }

        if (task instanceof Event e) {
            return "E | " + done + " | "
                    + e.getDescription() + " | "
                    + e.getFrom() + " to "
                    + e.getTo();
        }

        return "";
    }
}
