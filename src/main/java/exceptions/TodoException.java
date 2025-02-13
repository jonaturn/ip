package exceptions;

/**
 * Exception to handle todo faults
 */
public class TodoException extends TaskException {
    public TodoException(String message) {
        super(message);
    }

    /**
     * Empty constructor used to construct default message
     */
    public TodoException() {
        super(
                "\n    -----------------------------------------------------\r\n"
                        + "       The description of a todo cannot be empty.\r\n"
                        + "    -----------------------------------------------------"
        );
    }
}
