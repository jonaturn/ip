package exceptions;

public class EventException extends TaskException {
    public EventException(String message) {
        super(message);
    }

    public EventException() {
        super("    \n    -----------------------------------------------------\r\n"
                + "       The description of an event cannot be empty.\r\n"
                + "    -----------------------------------------------------");
    }
}