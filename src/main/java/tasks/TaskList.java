package tasks;

import java.util.ArrayList;

/**
 * Class to keep track of all current tasks
 */
public class TaskList {
    private ArrayList<Task> tl;

    public TaskList() {
        this.tl = new ArrayList<Task>();
    }

    public void add(Task t) {
        this.tl.add(t);
    }

    public ArrayList<Task> list() {
        return this.tl;
    }

    /**
     * Returns an ArrayList of Tasks that matches the keyword
     *
     * @param keyword keyword in string
     * @return ArrayList of matches
     */
    public ArrayList<Task> match(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task item : tl) {
            if (item.getName().contains(keyword)) {
                matches.add(item);
            }
        }
        return matches;
    }

}
