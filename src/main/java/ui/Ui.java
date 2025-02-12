package ui;

import java.util.ArrayList;

import tasks.Task;

/**
 * Class to handle UI
 */
public class Ui {
    private final String logo = "         _       _           \n"
            + "        | | ___ | |__  _ __  \n"
            + "     _  | |/ _ \\| '_ \\| '_ \\\n"
            + "    | |_| | (_) | | | | | | |\n"
            + "     \\___/ \\___/|_| |_|_| |_|\n";

    /**
     * Returns the logo
     */
    public String returnLogo() {
        return logo;
    }
    /**
     * Welcome message on start
     */
    public String greet() {
        return (logo + "\n"
                + "--------------------------------------------------------------------\n"
                + "Hello! I'm John\n"
                + "What can I do for you?\n\n"
                + "--------------------------------------------------------------------\n");
    }

    /**
     * Exit message on end
     */
    public String exit() {
        return ("--------------------------------------------------------------------\n"
                + "Bye. Hope to see you again soon!\n"
                + "--------------------------------------------------------------------\n");
    }

    /**
     * Prints a message to confirm users last added task
     *
     * @param input    last added task
     * @param listSize size of TaskList
     */
    public String repeat(Task input, int listSize) {
        return ("--------------------------------------------------------------------\n"
                + "Got it. I've added this task:\n"
                + "  " + returnOneItemAsString(input) + "\n"
                + listSize(listSize)
                + "--------------------------------------------------------------------\n");
    }

    /**
     * Returns a string to be printed on the checklist
     * each string belongs to one task
     *
     * @param input current task to be printed
     * @return a string to be printed
     */
    public String returnOneItemAsString(Task input) {
        return "[" + input.getType() + "][" + input.check() + "] " + input.name();
    }

    /**
     * Returns the size of the current TaskList
     *
     * @param size size of TaskList
     * @return String to be printed
     */
    public String listSize(int size) {
        return "Now you have " + size + " tasks in the list.\n";
    }

    /**
     * Prints out the whole TaskList
     *
     * @param list the list to be printed
     */
    public String listAll(ArrayList<Task> list) {
        int counter = 1;
        String allTasksString = "";
        allTasksString = ("--------------------------------------------------------------------");
        for (Task item : list) {
            allTasksString += (counter + "." + returnOneItemAsString(item));
            counter++;
        }
        allTasksString += ("--------------------------------------------------------------------\n");
        return allTasksString;
    }

    /**
     * Prints the confirmation message for marking a task
     *
     * @param item last marked task
     */
    public String mark(Task item) {
        return ("    ____________________________________________________________\r\n"
                + "     Nice! I've marked this task as done:\r\n"
                + "       [X] return " + item.name() + "\r\n"
                + "    ____________________________________________________________\r\n");
    }

    /**
     * Prints the confirmation message for unmarking a task
     *
     * @param item last unmarked task
     */
    public String unmark(Task item) {
        return ("    ____________________________________________________________\r\n"
                + "     OK, I've marked this task as not done yet:\r\n"
                + "       [ ] " + item.name() + "\r\n"
                + "    ____________________________________________________________");
    }

}
