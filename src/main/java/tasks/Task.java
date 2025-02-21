package tasks;

import java.util.ArrayList;

/**
 * Parent class for all tasks
 */
public class Task {
    private final String taskName;
    private Boolean status;
    private String type;
    private ArrayList<String> tags;

    /**
     * ConstructorS
     *
     * @param taskName name of task
     * @param type     type of task
     */
    public Task(String taskName, String type) {
        this.taskName = taskName;
        this.status = false;
        this.type = type;
        this.tags = new ArrayList<String>();
    }

    /**
     * Marks this task as done
     */
    public void done() {
        this.status = true;
    }

    /**
     * Marks this task as not done
     */
    public void undone() {
        this.status = false;
    }

    /**
     * Returns the name of this task
     */
    public String getName() {
        return this.taskName;
    }

    /**
     * adds a tag to the task
     */
    public void addTag(String tag) {
        this.tags.add(tag);
    }

    /**
     * Returns all the tags of this task
     */
    public String getTags() {
        String tagsString = "tags: ";
        for (String tag : this.tags) {
            tagsString += tag + " ";
        }
        return tagsString;
    }

    /**
     * function that marks X on the task list
     *
     * @return X if done and blank if not
     */
    public String check() {
        String check;
        if (this.status) {
            check = "X";
        } else {
            check = " ";
        }
        return check;
    }

    /**
     * Returns the initial of the type of task
     *
     * @return the specified initial
     */
    public String getType() {
        if (type.equals("deadline")) {
            return "D";
        } else if (type.equals("todo")) {
            return "T";
        } else if (type.equals("event")) {
            return "E";
        } else {
            return "";
        }
    }

    /**
     * Returns duration of task in a specific format
     */
    public String getDuration() {
        return "";
    }
}
