package exceptions;

/**
 * Exception to handle event faults
 */
public class InvalidDateException extends TaskException {
    public InvalidDateException(String message) {
        super(message);
    }

    /**
     * Empty constructor to build default message
     */
    public InvalidDateException() {
        super("    -----------------------------------------------------\r\n"
                + "       The dateTime format is wrong,\r\n"
                + "       pls use dd/mm/yyyy hhmm.\r\n"
                + "    -----------------------------------------------------");
    }
}
