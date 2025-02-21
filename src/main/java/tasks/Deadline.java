package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class to handle deadlines
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Constructor
     *
     * @param taskName name of task
     * @param type type of task
     * @param by deadline of task
     */
    public Deadline(String taskName, String type, LocalDateTime by) {
        super(taskName, type);
        this.by = by;
    }

    /**
     * Returns the name of the deadline and the formatted date and time
     *
     * @return description of deadline
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Returns duration of task in a specific format
     */
    @Override
    public String getDuration() {
        return " (by: " + by.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mma")) + ")";
    }

    /**
     * Returns the deadline of the task
     *
     * @return the deadline in string
     */
    public String returnByDate() {
        return this.by.toString();
    }
}
