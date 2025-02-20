package tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

/**
 * Tests the Event class
 */
public class EventTester {
    /**
     * Tests the name function
     */
    @Test
    public void testEventName() {
        Event test = new Event("meeting", "event", LocalDateTime.parse("11/12/2001 0000",
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm")),
                LocalDateTime.parse("12/12/2001 1200", DateTimeFormatter.ofPattern("d/M/yyyy HHmm")));
        assertEquals(
                "meeting (from: 11 Dec 2001 12:00am to: 12 Dec 2001 12:00pm)",
                test.getName()
                        + test.getDuration()
        );
    }
}
