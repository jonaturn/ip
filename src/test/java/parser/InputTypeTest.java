package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the InputType enum
 */
class InputTypeTest {
    /**
     * Tests the inputType function
     */
    @Test
    public void testInputType() {
        assertEquals("EXIT", InputType.fromString("bye").toString());
        assertEquals("LIST", InputType.fromString("list").toString());
        assertEquals("MARK", InputType.fromString("mark").toString());
        assertEquals("UNMARK", InputType.fromString("unmark").toString());
        assertEquals("TODO", InputType.fromString("todo").toString());
        assertEquals("DEADLINE", InputType.fromString("deadline").toString());
        assertEquals("EVENT", InputType.fromString("event").toString());
        assertEquals("DELETE", InputType.fromString("delete").toString());
        assertEquals("INVALID", InputType.fromString("invalid").toString());
    }
}
