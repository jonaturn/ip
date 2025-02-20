package exceptions;

/**
 * Exception to handle wrong input for task
 */
public class InvalidInputException extends TaskException {
    /**
     * Empty Constructor to create default message
     */
    public InvalidInputException() {
        super("    -----------------------------------------------------\r\n"
                + "     Wrong input, stop trolling :-(\r\n"
                + "    -----------------------------------------------------");
    }
}
