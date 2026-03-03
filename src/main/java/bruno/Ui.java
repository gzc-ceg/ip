package bruno;

import bruno.task.Task;

import java.util.ArrayList;

/**
 * Handles all user interface interactions, including printing messages and task displays.
 */
public class Ui {

    /**
     * Displays the welcome message and application logo.
     */
    public void showWelcome() {
        String logo =
                " ____  ____  _   _ _   _  ____  \n" +
                        "| __ )|  _ \\| | | | \\ | |  _  |\n" +
                        "|  _ \\| |_) | | | |  \\| | | | |\n" +
                        "| |_) |  _ <| |_| | |\\  | |_| | \n" +
                        "|____/|_| \\_\\\\___/|_| \\_| ____| \n";

        System.out.println("    Hello from\n" + logo);
        System.out.println("    Hello! I am Bruno, your personal assistant!");
        System.out.println("    What can I do for you?");
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showBye() {
        System.out.println("    Bye! Hope to see you again!");
        System.out.println("    Remember, Bruno is always here for you!");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println("    OOPS!!! " + message);
    }

    /**
     * Displays all tasks in the list, or a message if the list is empty.
     *
     * @param tasks The list of tasks to display.
     */
    public void showList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("    Your list is empty!");
            return;
        }

        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Confirms that a task has been added and shows the updated task count.
     *
     * @param task The task that was added.
     * @param size The new total number of tasks in the list.
     */
    public void showAdd(Task task, int size) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + size + " tasks in the list.");
    }

    /**
     * Confirms that a task has been deleted and shows the updated task count.
     *
     * @param task The task that was deleted.
     * @param size The new total number of tasks in the list.
     */
    public void showDelete(Task task, int size) {
        System.out.println("    Noted. I've removed this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + size + " tasks in the list.");
    }

    /**
     * Confirms that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showMark(Task task) {
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + task);
    }

    /**
     * Confirms that a task has been marked as not done.
     *
     * @param task The task that was marked as not done.
     */
    public void showUnmark(Task task) {
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
    }

    /**
     * Displays the results of a search operation.
     *
     * @param matchingTasks The list of tasks that match the search keyword.
     */
    public void showFindResults(ArrayList<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            System.out.println("    No tasks matching your keyword were found!");
            return;
        }
        System.out.println("    Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + matchingTasks.get(i));
        }
    }
}