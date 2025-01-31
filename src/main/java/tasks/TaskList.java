package tasks;

import java.util.ArrayList;

/**
 * Class to keep track of all current tasks
 */
public class TaskList {
    private ArrayList<Task> tl;

    public TaskList() {
        this.tl = new ArrayList<>();
    }

    public void add(Task t) {
        this.tl.add(t);
    }

    public ArrayList<Task> list() {
        return this.tl;
    }

}
