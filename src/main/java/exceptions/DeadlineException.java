package exceptions;

public class DeadlineException extends TaskException {
    public DeadlineException(String message) {
        super(message);
    }

    public DeadlineException() {
        super("\n    -----------------------------------------------------\r\n"
                + "       The description of a deadline cannot be empty.\r\n"
                + "    -----------------------------------------------------");
    }
}