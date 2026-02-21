package bruno;

import bruno.exception.BrunoException;
import bruno.task.Deadline;
import bruno.task.Event;
import bruno.task.Task;
import bruno.task.Todo;

import java.util.ArrayList;
import java.util.Scanner;

public class Bruno {

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
                    handleDelete(parts,tasks);
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

        if (parts.length < 2) {
            throw new BrunoException("Please specify a task number to mark.");
        }

        int index = Integer.parseInt(parts[1].trim()) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new BrunoException("Task number must be between 1 and " + tasks.size() + ".");
        }

        tasks.get(index).markAsDone();

        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + tasks.get(index));
    }


    private static void handleUnmark(String[] parts, ArrayList<Task> tasks) throws BrunoException {

        if (parts.length < 2) {
            throw new BrunoException("Please specify a task number to unmark.");
        }

        int index = Integer.parseInt(parts[1].trim()) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new BrunoException("Task number must be between 1 and " + tasks.size() + ".");
        }

        tasks.get(index).markAsNotDone();

        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + tasks.get(index));
    }


    private static void handleTodo(String[] parts, ArrayList<Task> tasks) throws BrunoException {

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new BrunoException("The description of a todo cannot be empty.");
        }

        Task t = new Todo(parts[1].trim());
        tasks.add(t);

        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + t);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
    }


    private static void handleDeadline(String[] parts, ArrayList<Task> tasks) throws BrunoException {

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new BrunoException("The description of a deadline cannot be empty.");
        }

        String input = parts[1].trim();
        String[] deadlineParts = input.split("/by", 2);

        if (deadlineParts.length < 2) {
            throw new BrunoException("Invalid deadline format. Please use: deadline [description] /by [time]");
        }

        String description = deadlineParts[0].trim();
        String by = deadlineParts[1].trim();

        Task t = new Deadline(description, by);
        tasks.add(t);

        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + t);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
    }


    private static void handleEvent(String[] parts, ArrayList<Task> tasks) throws BrunoException {

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new BrunoException("The description of an event cannot be empty.");
        }

        String input = parts[1].trim();
        String[] eventParts = input.split("/from|/to");

        if (eventParts.length < 3) {
            throw new BrunoException("Invalid event format. Please use: event [description] /from [start] /to [end]");
        }

        String description = eventParts[0].trim();
        String from = eventParts[1].trim();
        String to = eventParts[2].trim();

        Task t = new Event(description, from, to);
        tasks.add(t);

        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + t);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
    }

    private static void handleDelete(String[] parts, ArrayList<Task> tasks) throws BrunoException {

        if (parts.length < 2) {
            throw new BrunoException("Please specify a task number to delete.");
        }

        int index = Integer.parseInt(parts[1].trim()) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new BrunoException("Task number must be between 1 and " + tasks.size() + ".");
        }

        Task removedTask = tasks.remove(index);

        System.out.println("    Noted. I've removed this task:");
        System.out.println("      " + removedTask);
        if(tasks.size()==1){
            System.out.println("    Now you have 1 task in the list.");
        }else{
            System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        }
    }


}
