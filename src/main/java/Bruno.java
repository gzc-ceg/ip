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
        System.out.println("    Hello! I am Bruno, your personl assistant!");
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

            switch (command) {

            case "bye":
                System.out.println("    Bye! Hope to see you again!");
                System.out.println("    Remember, Bruno is always here for you!");
                scanner.close();
                return;

            case "list":
                if (taskCount == 0) {
                    System.out.println("    Your list is empty! Please first add some tasks!");
                } else {
                    System.out.println("    Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("    " + (i + 1) + '.' + tasks[i]);
                    }
                }
                break;

            case "mark":
                int markIndex = Integer.parseInt(parts[1].trim()) - 1;
                tasks[markIndex].markAsDone();
                System.out.println("    Nice! I've marked this task as done:");
                System.out.println("    " + tasks[markIndex]);
                break;

            case "unmark":
                int unmarkIndex = Integer.parseInt(parts[1].trim()) - 1;
                tasks[unmarkIndex].markAsNotDone();
                System.out.println("    OK, I've marked this task as not done yet:");
                System.out.println("    " + tasks[unmarkIndex]);
                break;

            case "todo":
                System.out.println("    Got it. I've added this task:");
                tasks[taskCount] = new Todo(parts[1]);
                taskCount++;
                System.out.println("      " + tasks[taskCount - 1]);
                if (taskCount == 1) {
                    System.out.println("    Now you have 1 task in the list.");
                } else {
                    System.out.println("    Now you have " + taskCount + " tasks in the list.");
                }
                break;

            case "deadline":
                System.out.println("    Got it. I've added this task:");
                String[] parts_deadline = parts[1].split("/by", 2);
                tasks[taskCount] = new Deadline(parts_deadline[0], parts_deadline[1]);
                taskCount++;
                System.out.println("      " + tasks[taskCount - 1]);
                if (taskCount == 1) {
                    System.out.println("    Now you have 1 task in the list.");
                } else {
                    System.out.println("    Now you have " + taskCount + " tasks in the list.");
                }
                break;

            case "event":
                System.out.println("    Got it. I've added this task:");
                String[] parts_event = parts[1].split("/from|/to");
                tasks[taskCount] = new Event(parts_event[0], parts_event[1], parts_event[2]);
                taskCount++;
                System.out.println("      " + tasks[taskCount - 1]);
                if (taskCount == 1) {
                    System.out.println("    Now you have 1 task in the list.");
                } else {
                    System.out.println("    Now you have " + taskCount + " tasks in the list.");
                }
                break;
            }
        }
    }
}