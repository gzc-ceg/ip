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
        Task[] tasks = new Task[100];
        int taskCount = 0;

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
                    handleList(tasks, taskCount);
                    break;

                case "mark":
                    taskCount = handleMark(parts, tasks, taskCount);
                    break;

                case "unmark":
                    taskCount = handleUnmark(parts, tasks, taskCount);
                    break;

                case "todo":
                    taskCount = handleTodo(parts, tasks, taskCount);
                    break;

                case "deadline":
                    taskCount = handleDeadline(parts, tasks, taskCount);
                    break;

                case "event":
                    taskCount = handleEvent(parts, tasks, taskCount);
                    break;

                default:
                    throw new BrunoException("I'm sorry, but I don't know what that means :-(");
                }
            } catch (BrunoException e) {
                System.out.println("    OOPS!!! " + e.getMessage());
            }
        }
    }

    private static void handleBye(Scanner scanner) {
        System.out.println("    Bye! Hope to see you again!");
        System.out.println("    Remember, Bruno is always here for you!");
        scanner.close();
    }

    private static void handleList(Task[] tasks, int taskCount) {
        if (taskCount == 0) {
            System.out.println("    Your list is empty! Please first add some tasks!");
        } else {
            System.out.println("    Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println("    " + (i + 1) + '.' + tasks[i]);
            }
        }
    }

    private static int handleMark(String[] parts, Task[] tasks, int taskCount) throws BrunoException {
        if (parts.length < 2) {
            throw new BrunoException("Please specify a task number to mark.");
        }

        try {
            int markIndex = Integer.parseInt(parts[1].trim()) - 1;

            if (markIndex < 0 || markIndex >= taskCount) {
                if (taskCount == 0) {
                    throw new BrunoException("Your list is empty! There are no tasks to mark.");
                } else {
                    throw new BrunoException("Task number must be between 1 and " + taskCount + ".");
                }
            }

            tasks[markIndex].markAsDone();
            System.out.println("    Nice! I've marked this task as done:");
            System.out.println("    " + tasks[markIndex]);
        } catch (NumberFormatException e) {
            throw new BrunoException("Please provide a valid task number.");
        }

        return taskCount;
    }

    private static int handleUnmark(String[] parts, Task[] tasks, int taskCount) throws BrunoException {
        if (parts.length < 2) {
            throw new BrunoException("Please specify a task number to unmark.");
        }

        try {
            int unmarkIndex = Integer.parseInt(parts[1].trim()) - 1;

            if (unmarkIndex < 0 || unmarkIndex >= taskCount) {
                if (taskCount == 0) {
                    throw new BrunoException("Your list is empty! There are no tasks to unmark.");
                } else {
                    throw new BrunoException("Task number must be between 1 and " + taskCount + ".");
                }
            }

            tasks[unmarkIndex].markAsNotDone();
            System.out.println("    OK, I've marked this task as not done yet:");
            System.out.println("    " + tasks[unmarkIndex]);
        } catch (NumberFormatException e) {
            throw new BrunoException("Please provide a valid task number.");
        }

        return taskCount;
    }

    private static int handleTodo(String[] parts, Task[] tasks, int taskCount) throws BrunoException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new BrunoException("The description of a todo cannot be empty.");
        }
        System.out.println("    Got it. I've added this task:");
        tasks[taskCount] = new Todo(parts[1]);
        taskCount++;
        System.out.println("      " + tasks[taskCount - 1]);
        if (taskCount == 1) {
            System.out.println("    Now you have 1 task in the list.");
        } else {
            System.out.println("    Now you have " + taskCount + " tasks in the list.");
        }
        return taskCount;
    }

    private static int handleDeadline(String[] parts, Task[] tasks, int taskCount) throws BrunoException {
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

        if (description.isEmpty()) {
            throw new BrunoException("Deadline description cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new BrunoException("Deadline time cannot be empty.");
        }

        System.out.println("    Got it. I've added this task:");
        tasks[taskCount] = new Deadline(description, by);
        taskCount++;
        System.out.println("      " + tasks[taskCount - 1]);
        if (taskCount == 1) {
            System.out.println("    Now you have 1 task in the list.");
        } else {
            System.out.println("    Now you have " + taskCount + " tasks in the list.");
        }
        return taskCount;
    }

    private static int handleEvent(String[] parts, Task[] tasks, int taskCount) throws BrunoException {
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

        if (description.isEmpty()) {
            throw new BrunoException("Event description cannot be empty.");
        }
        if (from.isEmpty() || to.isEmpty()) {
            throw new BrunoException("Event start and end times cannot be empty.");
        }

        System.out.println("    Got it. I've added this task:");
        tasks[taskCount] = new Event(description, from, to);
        taskCount++;
        System.out.println("      " + tasks[taskCount - 1]);
        if (taskCount == 1) {
            System.out.println("    Now you have 1 task in the list.");
        } else {
            System.out.println("    Now you have " + taskCount + " tasks in the list.");
        }
        return taskCount;
    }
}