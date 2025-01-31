package tasks;

/**
 * Parent class for all tasks
 */
public class Task {
    private final String taskName;
    private Boolean status;
    private String type;

    /**
     * ConstructorS
     *
     * @param taskName name of task
     * @param type type of task
     */
    public Task(String taskName, String type) {
        this.taskName = taskName;
        this.status = false;
        this.type = type;
    }

    public void done() {
        this.status = true;
    }

    public void undone() {
        this.status = false;
    }

    public String name() {
        return this.taskName;
    }

    public Boolean status() {
        return this.status;
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

    public void toggle() {
        this.status = !this.status;
    }

    public void setType(String type) {
        this.type = type;
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
}
