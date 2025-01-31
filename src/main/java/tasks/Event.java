package tasks;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Class that handles events
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalTime to;

    /**
     * Constructor
     *
     * @param taskName name of task
     * @param type type of task
     * @param from start datetime of task
     * @param to end datetime of task
     */
    public Event(String taskName, String type, LocalDateTime from, LocalTime to) {
        super(taskName, type);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the name of the event and the formatted date and time
     *
     * @return description of event
     */
    @Override
    public String name() {
        return super.name() + " (from: " + from.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mma"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("hh:mma"))
                + ")";
    }

    public String from() {
        return this.from.toString();
    }

    public String to() {
        return this.toString();
    }
}
