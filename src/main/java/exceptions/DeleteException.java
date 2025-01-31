package exceptions;

/**
 * Exceptions that stem from delete command
 */
public class DeleteException extends TaskException {
    public DeleteException(String message) {
        super(message);
    }
}
