package ui;

import java.util.ArrayList;

import tasks.Task;


public class Ui {
    private final String logo = "         _       _           \n"
            + "        | | ___ | |__  _ __  \n"
            + "     _  | |/ _ \\| '_ \\| '_ \\\n"
            + "    | |_| | (_) | | | | | | |\n"
            + "     \\___/ \\___/|_| |_|_| |_|\n";

    public void greet() {
        System.out.println(logo + "\n"
                + "--------------------------------------------------------------------\n"
                + "Hello! I'm John\n"
                + "What can I do for you?\n\n"
                + "--------------------------------------------------------------------\n");

    }

    public void exit() {
        System.out.println("--------------------------------------------------------------------\n"
                + "Bye. Hope to see you again soon!\n"
                + "--------------------------------------------------------------------\n");
    }

    public void repeat(Task input, int listSize) {
        System.out.println("--------------------------------------------------------------------\n"
                + "Got it. I've added this task:\n"
                + "  " + item(input) + "\n"
                + listSize(listSize)
                + "--------------------------------------------------------------------\n");
    }

    public String item(Task input) {
        return  "[" + input.getType() + "][" + input.check() + "] " + input.name();
    }

    public String listSize(int size) {
        return "Now you have " + size + " tasks in the list.\n";
    }

    public void listAll(ArrayList<Task> list) {
        int counter = 1;
        // ArrayList<Task> al = list.list();
        System.out.println("--------------------------------------------------------------------");
        for (Task item : list) {
            System.out.println(counter + "." + item(item));
            counter++;
        }
        System.out.println("--------------------------------------------------------------------\n");
    }

    public void mark(Task item) {
        System.out.println("    ____________________________________________________________\r\n" + //
                "     Nice! I've marked this task as done:\r\n" + //
                "       [X] return " + item.name() + "\r\n" + //
                "    ____________________________________________________________\r\n");
    }

    public void unmark(Task item) {
        System.out.println("    ____________________________________________________________\r\n" + //
                "     OK, I've marked this task as not done yet:\r\n" + //
                "       [ ] " + item.name() + "\r\n" + //
                "    ____________________________________________________________");
    }

}
