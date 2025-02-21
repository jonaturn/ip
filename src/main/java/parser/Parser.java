package parser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import exceptions.DeadlineException;
import exceptions.DeleteException;
import exceptions.EventException;
import exceptions.InvalidDateException;
import exceptions.InvalidInputException;
import exceptions.TaskException;
import exceptions.TodoException;
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
    public String inputHandling(String input, TaskList tasklist, Ui ui, Storage storage) throws Exception {
        InputType type = InputType.fromString(input);
        ArrayList<Task> listOfTasks = tasklist.list();

        int index;
        Task selected;
        String description;
        Task item;
        LocalDateTime date;
        LocalDateTime fromDateTime;
        LocalDateTime toDateTime;

        try {
            switch (type) {
            case EXIT:
                storage.save(tasklist);
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
                    throw new TodoException();
                }
                item = new Todo(description, "todo");
                listOfTasks.add(item);
                return ui.repeat(item, listOfTasks.size());
            case DEADLINE:
                description = input.substring(9).split("by")[0].trim();
                if (description.isEmpty()) {
                    throw new DeadlineException();
                }
                String byPart = input.split("by")[1].trim();
                try {
                    date = LocalDateTime.parse(byPart, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
                } catch (Exception e) {
                    throw new InvalidDateException();
                }
                item = new Deadline(description, "deadline", date);
                listOfTasks.add(item);
                return ui.repeat(item, listOfTasks.size());
            case EVENT:
                description = input.substring(6).split("from")[0].trim();
                if (description.isEmpty()) {
                    throw new EventException();
                }

                String fromPart = input.split("from")[1].split("to")[0].trim();
                String toPart = input.split("to")[1].trim();
                try {
                    fromDateTime = LocalDateTime.parse(fromPart, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
                    toDateTime = LocalDateTime.parse(toPart, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
                } catch (Exception e) {
                    throw new InvalidDateException();
                }
                item = new Event(description, "event", fromDateTime, toDateTime);
                listOfTasks.add(item);
                return ui.repeat(item, listOfTasks.size());
            case DELETE:
                description = input.substring(7);
                if (description.isEmpty()) {
                    throw new DeleteException("Error deleting message");
                }

                index = Integer.parseInt(description) - 1;
                listOfTasks.remove(index);
                return ui.returnDeleteMessage(listOfTasks.get(index));
            case FIND:
                description = input.substring(5);
                if (description.isEmpty()) {
                    throw new TaskException("Cannot find blank.");
                }
                return ui.returnFindMessage(tasklist.match(description));
            case TAG:
                int taskIndex;
                description = input.substring(4);
                String[] descriptionParts = description.split(" ");
                try {
                    taskIndex = Integer.parseInt(descriptionParts[0]) - 1;
                } catch (NumberFormatException e) {
                    throw new TaskException("Error parsing task index");
                }
                String tag = descriptionParts[1];

                tasklist.list().get(taskIndex).addTag(tag);
                return ui.getTagMessage(tag, tasklist.list().get(taskIndex));
            case INVALID:
                throw new InvalidInputException();
            case HELP:
                return ui.getHelp();
            default:
                throw new InvalidInputException();
            }
        } catch (TaskException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Wrong input, try again";
        }
    }
}
