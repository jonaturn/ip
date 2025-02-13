package exceptions;

public class InvalidTaskException extends TaskException {
    public InvalidTaskException() {
        super("\n    -----------------------------------------------------\r\n"
                + "     Wrong input, stop trolling :-(\r\n"
                + "    -----------------------------------------------------");
    }
}