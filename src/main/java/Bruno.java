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
            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("    Bye! Hope to see you again!");
                System.out.println("    Remember, Bruno is always here for you!");
                break;
            } else if (userInput.isEmpty()) {
                System.out.println("    Please say something, I am listening...");
            } else if (userInput.equalsIgnoreCase("list")) {
                if (taskCount == 0) {
                    System.out.println("    Your list is empty! Please first add some tasks!");
                } else {
                    System.out.println("    Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("    " + (i + 1) + '.' + tasks[i]);
                    }
                }
            } else if (userInput.startsWith("mark ")) {
                int index = Integer.parseInt(userInput.substring(5).trim()) - 1;
                tasks[index].markAsDone();
                System.out.println("    Nice! I've marked this task as done:");
                System.out.println("    " + tasks[index]);
            } else if (userInput.startsWith("unmark ")) {
                int index = Integer.parseInt(userInput.substring(7).trim()) - 1;
                tasks[index].markAsNotDone();
                System.out.println("    OK, I've marked this task as not done yet:");
                System.out.println("    " + tasks[index]);
            } else if (!userInput.isEmpty()) {
                tasks[taskCount] = new Task(userInput);
                taskCount++;
                System.out.println("    added: " + userInput);
            }
        }
        scanner.close();
    }
}