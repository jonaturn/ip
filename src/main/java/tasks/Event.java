package tasks;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime from;
    private LocalTime to;

    public Event(String taskName, String type, LocalDateTime from, LocalTime to) {
        super(taskName, type);
        this.from = from;
        this.to = to;
    }

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
