package bruno;

import bruno.task.Task;

import java.util.ArrayList;

/**
 * Encapsulates a list of tasks and provides operations to manipulate it.
 * Serves as the main data structure for managing tasks in the application.
 */
public class TaskList {

    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAll() {
        return tasks;
    }
}
