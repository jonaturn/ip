package parser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import exceptions.DeleteException;
import exceptions.TaskException;
import storage.Storage;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.TaskList;
import tasks.Todo;
import ui.Ui;

/**
 * Handles all the logic for input processing
 */
public class Parser {
    /**
     * Processes input from UI
     *
     * @param input    input string
     * @param tasklist universal list of tasks
     * @param ui       object to handle ui
     * @param storage  object to handle filewriting
     * @return boolean to continue receiving input
     * @throws IOException possible error in filewriting
     */
    public String inputHandling(String input, TaskList tasklist, Ui ui, Storage storage) throws IOException {
        InputType type = InputType.fromString(input);
        ArrayList<Task> listOfTasks = tasklist.list();

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
                //ui.exit();
                storage.save(listOfTasks);
                return ui.exit();
            case LIST:
                return ui.listAll(listOfTasks);
            case MARK:
                index = Integer.parseInt(input.substring(5)) - 1;
                selected = listOfTasks.get(index);
                selected.done();
                return ui.mark(selected);
            case UNMARK:
                index = Integer.parseInt(input.substring(7)) - 1;
                selected = listOfTasks.get(index);
                selected.undone();
                return ui.unmark(selected);
            case TODO:
                description = input.substring(5);
                if (description.isEmpty()) {
                    throw new TaskException(
                            "    \n    ____________________________________________________________\r\n"
                                    + "       The description of a todo cannot be empty.\r\n"
                                    + "    ____________________________________________________________");
                }
                item = new Todo(description, "todo");
                listOfTasks.add(item);
                return ui.repeat(item, listOfTasks.size());
            case DEADLINE:
                description = input.substring(9).split("/by")[0].trim();
                if (description.isEmpty()) {
                    throw new TaskException(
                            "    \n    ____________________________________________________________\r\n"
                                    + "       The description of a deadline cannot be empty.\r\n"
                                    + "    ____________________________________________________________");
                }

                String byPart = input.split("/by")[1].trim();
                date = LocalDateTime.parse(byPart, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
                item = new Deadline(description, "deadline", date);
                listOfTasks.add(item);
                return ui.repeat(item, listOfTasks.size());
            case EVENT:
                description = input.substring(6).split("/from")[0].trim();
                if (description.isEmpty()) {
                    throw new TaskException(
                            "    \n    ____________________________________________________________\r\n"
                                    + "       The description of an event cannot be empty.\r\n"
                                    + "    ____________________________________________________________");
                }

                // description = input.substring(6);
                String fromPart = input.split("/from")[1].split("/to")[0].trim();
                String toPart = input.split("/to")[1].trim();
                fromDateTime = LocalDateTime.parse(fromPart, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
                toTime = LocalTime.parse(toPart, DateTimeFormatter.ofPattern("HHmm"));
                item = new Event(description, "event", fromDateTime, toTime);
                listOfTasks.add(item);
                return ui.repeat(item, listOfTasks.size());
            case DELETE:
                description = input.substring(7);
                if (description.isEmpty()) {
                    throw new DeleteException("Error deleting message");
                }

                index = Integer.parseInt(description) - 1;
                listOfTasks.remove(index);
                return ("    ____________________________________________________________\r\n"
                        + "     Noted. I've removed this task:\r\n"
                        + "       " + ui.returnOneItemAsString(listOfTasks.get(index)) + "\r\n"
                        + "     Now you have 4 tasks in the list.\r\n"
                        + "    ____________________________________________________________");
            case FIND:
                description = input.substring(5);
                if (description.isEmpty()) {
                    throw new TaskException("Cannot find blank.");
                }
                return ("    ____________________________________________________________"
                        + "Here are the matching tasks in your list:\r\n")
                        + ui.listAll(tasklist.match(description));
            case INVALID:
                throw new TaskException(
                        "\n    ____________________________________________________________\r\n"
                                + "     Wrong input, stop trolling :-(\r\n"
                                + "    ____________________________________________________________");
                // break;
            default:
                throw new AssertionError("Unknown command type");
                // break;
            }
        } catch (TaskException e) {
            System.out.println(e.getMessage());
            storage.save(listOfTasks);
            return e.getMessage()
                    + "\n"
                    + ui.exit();
        }
    }
}
