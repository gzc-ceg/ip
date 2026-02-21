package bruno;

import bruno.exception.BrunoException;
import bruno.task.Deadline;
import bruno.task.Event;
import bruno.task.Task;
import bruno.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Bruno {

    private static final String DATA_FOLDER = "./data";
    private static final String DATA_FILE = DATA_FOLDER + "/bruno.txt";

    public static void main(String[] args) {

        String logo =
                " ____  ____  _   _ _   _  ____  \n" +
                        "| __ )|  _ \\| | | | \\ | |  _  |\n" +
                        "|  _ \\| |_) | | | |  \\| | | | |\n" +
                        "| |_) |  _ <| |_| | |\\  | |_| | \n" +
                        "|____/|_| \\_\\\\___/|_| \\_| ____| \n";

        System.out.println("    Hello from\n" + logo);
        System.out.println("    Hello! I am Bruno, your personal assistant!");
        System.out.println("    What can I do for you?");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        loadTasks(tasks);

        while (true) {
            String userInput = scanner.nextLine().trim();

            if (userInput.isEmpty()) {
                System.out.println("    Please say something, I am listening...");
                continue;
            }

            String[] parts = userInput.split(" ", 2);
            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                case "bye":
                    handleBye(scanner);
                    return;

                case "list":
                    handleList(tasks);
                    break;

                case "mark":
                    handleMark(parts, tasks);
                    break;

                case "unmark":
                    handleUnmark(parts, tasks);
                    break;

                case "todo":
                    handleTodo(parts, tasks);
                    break;

                case "deadline":
                    handleDeadline(parts, tasks);
                    break;

                case "event":
                    handleEvent(parts, tasks);
                    break;

                case "delete":
                    handleDelete(parts, tasks);
                    break;

                default:
                    throw new BrunoException("I'm sorry, but I don't know what that means :-(");
                }
            } catch (BrunoException e) {
                System.out.println("    OOPS!!! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("    OOPS!!! Please provide a valid task number.");
            } catch (Exception e) {
                System.out.println("    OOPS!!! An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    //Handle Commands

    private static void handleBye(Scanner scanner) {
        System.out.println("    Bye! Hope to see you again!");
        System.out.println("    Remember, Bruno is always here for you!");
        scanner.close();
    }

    private static void handleList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("    Your list is empty! Please first add some tasks!");
            return;
        }
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
    }

    private static void handleMark(String[] parts, ArrayList<Task> tasks) throws BrunoException {
        if (parts.length < 2) throw new BrunoException("Please specify a task number to mark.");
        int index = Integer.parseInt(parts[1].trim()) - 1;
        if (index < 0 || index >= tasks.size())
            throw new BrunoException("Task number must be between 1 and " + tasks.size() + ".");
        tasks.get(index).markAsDone();
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + tasks.get(index));
        saveTasks(tasks);
    }

    private static void handleUnmark(String[] parts, ArrayList<Task> tasks) throws BrunoException {
        if (parts.length < 2) throw new BrunoException("Please specify a task number to unmark.");
        int index = Integer.parseInt(parts[1].trim()) - 1;
        if (index < 0 || index >= tasks.size())
            throw new BrunoException("Task number must be between 1 and " + tasks.size() + ".");
        tasks.get(index).markAsNotDone();
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + tasks.get(index));
        saveTasks(tasks);
    }

    private static void handleTodo(String[] parts, ArrayList<Task> tasks) throws BrunoException {
        if (parts.length < 2 || parts[1].trim().isEmpty())
            throw new BrunoException("The description of a todo cannot be empty.");
        Task t = new Todo(parts[1].trim());
        tasks.add(t);
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + t);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        saveTasks(tasks);
    }

    private static void handleDeadline(String[] parts, ArrayList<Task> tasks) throws BrunoException {
        if (parts.length < 2 || parts[1].trim().isEmpty())
            throw new BrunoException("The description of a deadline cannot be empty.");

        String input = parts[1].trim();
        String[] deadlineParts = input.split("/by", 2);
        if (deadlineParts.length < 2)
            throw new BrunoException("Invalid deadline format. Please use: deadline [description] /by [time]");

        Task t = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
        tasks.add(t);
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + t);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        saveTasks(tasks);
    }

    private static void handleEvent(String[] parts, ArrayList<Task> tasks) throws BrunoException {
        if (parts.length < 2 || parts[1].trim().isEmpty())
            throw new BrunoException("The description of an event cannot be empty.");

        String[] eventParts = parts[1].trim().split("/from|/to");
        if (eventParts.length < 3)
            throw new BrunoException("Invalid event format. Please use: event [description] /from [start] /to [end]");

        Task t = new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
        tasks.add(t);
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + t);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        saveTasks(tasks);
    }

    private static void handleDelete(String[] parts, ArrayList<Task> tasks) throws BrunoException {
        if (parts.length < 2) throw new BrunoException("Please specify a task number to delete.");
        int index = Integer.parseInt(parts[1].trim()) - 1;
        if (index < 0 || index >= tasks.size())
            throw new BrunoException("Task number must be between 1 and " + tasks.size() + ".");
        Task removed = tasks.remove(index);
        System.out.println("    Noted. I've removed this task:");
        System.out.println("      " + removed);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        saveTasks(tasks);
    }

    // Save/Load File

    private static void loadTasks(ArrayList<Task> tasks) {
        try {
            File folder = new File(DATA_FOLDER);
            if (!folder.exists()) folder.mkdir();
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                file.createNewFile();
                return;
            }

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                switch (type) {
                case "T":
                    Task t = new Todo(description);
                    if (isDone) t.markAsDone();
                    tasks.add(t);
                    break;
                case "D":
                    Task d = new Deadline(description, parts[3]);
                    if (isDone) d.markAsDone();
                    tasks.add(d);
                    break;
                case "E":
                    String[] ft = parts[3].split(" to ");
                    Task e = new Event(description, ft[0], ft[1]);
                    if (isDone) e.markAsDone();
                    tasks.add(e);
                    break;
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("    Warning: Failed to load saved tasks: " + e.getMessage());
        }
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            File folder = new File(DATA_FOLDER);
            if (!folder.exists()) folder.mkdir();
            FileWriter fw = new FileWriter(DATA_FILE);

            for (Task task : tasks) {
                String line = "";
                String isDone = task.isDone() ? "1" : "0";

                if (task instanceof Todo) {
                    line = "T | " + isDone + " | " + task.getDescription();
                } else if (task instanceof Deadline) {
                    Deadline d = (Deadline) task;
                    line = "D | " + isDone + " | " + d.getDescription() + " | " + d.getBy();
                } else if (task instanceof Event) {
                    Event e = (Event) task;
                    line = "E | " + isDone + " | " + e.getDescription() + " | " + e.getFrom() + " to " + e.getTo();
                }

                fw.write(line + "\n");
            }

            fw.close();
        } catch (Exception e) {
            System.out.println("    Warning: Failed to save tasks: " + e.getMessage());
        }
    }
}

