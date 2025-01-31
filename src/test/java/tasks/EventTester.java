package tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *Tests the Event class
 */
public class EventTester {
    /**
     *Tests the name function
     */
    @Test
    public void testEventName() {
        Event test = new Event("meeting", "event", LocalDateTime.parse("11/12/2001 0000", DateTimeFormatter.ofPattern("d/M/yyyy HHmm")), LocalTime.parse("1200", DateTimeFormatter.ofPattern("HHmm")));
        assertEquals("meeting (from: 11 Dec 2001 12:00am to: 12:00pm)", test.name());
    }
}