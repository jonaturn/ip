import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.TaskList;

public class Parser {
    public enum inputType {
        EXIT("bye"),
        LIST("list"),
        MARK("mark"),
        UNMARK("unmark"),
        TODO("todo"),
        DEADLINE("deadline"),
        EVENT("event"),
        DELETE("delete"),
        INVALID("invalid");

        private final String commandString;

        inputType(String commandString) {
            this.commandString = commandString;
        }

        public static inputType fromString(String command) {
            String[] parts = command.split("\\s+"); // Split by whitespace
            if (parts.length > 0) {
                String firstWord = parts[0];
                for (inputType type : inputType.values()) {
                    if (type.commandString.equals(firstWord)) {
                        return type;
                    }
                }
            }
            return INVALID;
        }
    }

    static class taskException extends Exception {
        taskException(String message) {
            super(message);
        }
    }

    static class deleteException extends taskException {
        deleteException(String message) {
            super(message);
        }
    }

    // private Ui ui;

    public boolean input_handling(String input, TaskList tasklist, Ui ui, Storage storage) throws IOException {
        inputType type = inputType.fromString(input);
        ArrayList<Task> list = tasklist.list();

        int index;
        Task selected;
        String description;
        Task item;
        LocalDateTime date;
        LocalDateTime fromDateTime;
        LocalTime toTime;

        try {
            switch (type) {
                case EXIT:
                    ui.exit();
                    // System.out.println(save(list, fw));
                    storage.save(list);
                    return false;
                // break;
                case LIST:
                    ui.listAll(list);
                    break;
                case MARK:
                    index = Integer.parseInt(input.substring(5)) - 1;
                    selected = list.get(index);
                    selected.done();
                    ui.mark(selected);
                    break;
                case UNMARK:
                    index = Integer.parseInt(input.substring(7)) - 1;
                    selected = list.get(index);
                    selected.undone();
                    ui.unmark(selected);
                    break;
                case TODO:
                    description = input.substring(5);
                    if (description.isEmpty()) {
                        throw new taskException(
                                "    \n    ____________________________________________________________\r\n" + //
                                        "       The description of a todo cannot be empty.\r\n" + //
                                        "    ____________________________________________________________");
                    }
                    item = new Task(description, "todo");
                    list.add(item);
                    ui.repeat(item, list.size());
                    break;
                case DEADLINE:
                    description = input.substring(9).split("/by")[0].trim();
                    if (description.isEmpty()) {
                        throw new taskException(
                                "    \n    ____________________________________________________________\r\n" + //
                                        "       The description of a deadline cannot be empty.\r\n" + //
                                        "    ____________________________________________________________");
                    }

                    String byPart = input.split("/by")[1].trim();
                    date = LocalDateTime.parse(byPart, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
                    item = new Deadline(description, "deadline", date);
                    list.add(item);
                    ui.repeat(item, list.size());
                    break;
                case EVENT:
                    description = input.substring(6).split("/from")[0].trim();
                    if (description.isEmpty()) {
                        throw new taskException(
                                "    \n    ____________________________________________________________\r\n" + //
                                        "       The description of an event cannot be empty.\r\n" + //
                                        "    ____________________________________________________________");
                    }

                    // description = input.substring(6);
                    String fromPart = input.split("/from")[1].split("/to")[0].trim();
                    String toPart = input.split("/to")[1].trim();
                    fromDateTime = LocalDateTime.parse(fromPart, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
                    toTime = LocalTime.parse(toPart);
                    item = new Event(description, "event", fromDateTime, toTime);
                    list.add(item);
                    ui.repeat(item, list.size());
                    break;
                case DELETE:
                    description = input.substring(7);
                    if (description.isEmpty()) {
                        throw new deleteException("Error deleting message");
                    }

                    index = Integer.parseInt(description) - 1;
                    System.out.println("    ____________________________________________________________\r\n" + //
                            "     Noted. I've removed this task:\r\n" + //
                            "       " + ui.item(list.get(index)) + "\r\n" + //
                            "     Now you have 4 tasks in the list.\r\n" + //
                            "    ____________________________________________________________");
                    list.remove(index);
                    break;
                case INVALID:
                    throw new taskException(
                            "\n    ____________________________________________________________\r\n" + //
                                    "     Wrong input, stop trolling :-(\r\n" + //
                                    "    ____________________________________________________________");
                // break;
                default:
                    throw new AssertionError("Unknown command type");
                // break;
            }
        } catch (taskException e) {
            System.out.println(e.getMessage());
            ui.exit();
            // sc.close();
            storage.save(list);
            return false;
        }

        return true;
    }
}
