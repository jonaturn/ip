package exceptions;

public class TodoException extends TaskException {
   public TodoException(String message) {
       super(message);
   }

   public TodoException() {
       super("\n    -----------------------------------------------------\r\n"
               + "       The description of a todo cannot be empty.\r\n"
               + "    -----------------------------------------------------");
   }
}