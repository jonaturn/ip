package exceptions;

/**
 * Exception to handle deadline faults
 */
public class DeadlineException extends TaskException {
    public DeadlineException(String message) {
        super(message);
    }

    /**
     * New empty constructor with default message
     */
    public DeadlineException() {
        super("\n    -----------------------------------------------------\r\n"
                + "       The description of a deadline cannot be empty.\r\n"
                + "    -----------------------------------------------------");
    }
}
