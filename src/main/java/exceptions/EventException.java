package exceptions;

/**
 * Exception to handle event faults
 */
public class EventException extends TaskException {
    public EventException(String message) {
        super(message);
    }

    /**
     * Empty constructor to build default message
     */
    public EventException() {
        super("    \n    -----------------------------------------------------\r\n"
                + "       The description of an event cannot be empty.\r\n"
                + "    -----------------------------------------------------");
    }
}
