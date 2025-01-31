package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime by;

    public Deadline(String taskName, String type, LocalDateTime by) {
        super(taskName, type);
        this.by = by;
    }

    @Override
    public String name() {
        return super.name() + " (by: " + by.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mma")) + ")";
    }

    public String by() {
        return this.by.toString();
    }
}
