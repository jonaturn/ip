import java.util.Scanner;
import java.util.ArrayList;

public class John {
    static class Task {
        private final String taskName;
        private Boolean status;
        private String type;

        Task(String taskName, String type) {
            this.taskName = taskName;
            this.status = false;
            this.type = type;
        }

        public void done() {
            this.status = true;
        }

        public void undone() {
            this.status = false;
        }

        public String name() {
            return this.taskName;
        }

        public Boolean status() {
            return this.status;
        }

        public String check() {
            String check;
            if (this.status) {
                check = "X";
            } else {
                check = " ";
            }
            return check;
        }

        public void toggle() {
            this.status = !this.status;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            if (type.equals("deadline")) {
                return "D";
            } else if (type.equals("todo")) {
                return "T";
            } else if (type.equals("event")) {
                return "E";
            } else {
                return "";
            }
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

    static class Event extends Task {
        private String from;
        private String to;

        Event(String taskName, String type, String from, String to) {
            super(taskName, type);
            this.from = from;
            this.to = to;
        }

        @Override
        public String name() {
            // TODO Auto-generated method stub
            return super.name() + " (from: " + from + " to: " + to + ")";
        }
    }

    static class Deadline extends Task {
        private String by;

        Deadline(String taskName, String type, String by) {
            super(taskName, type);
            this.by = by;
        }

        @Override
        public String name() {
            // TODO Auto-generated method stub
            return super.name() + " (by: " + by + ")";
        }
    }

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

    public static void main(String[] args) throws Exception {
        // init
        Scanner sc = new Scanner(System.in);
        Boolean listen = true;
        ArrayList<Task> list = new ArrayList<>();
        String input;
        String logo = "         _       _           \n"
                + "        | | ___ | |__  _ __  \n"
                + "     _  | |/ _ \\| '_ \\| '_ \\\n"
                + "    | |_| | (_) | | | | | | |\n"
                + "     \\___/ \\___/|_| |_|_| |_|\n";

        System.out.println("Hello from\n" + logo);

        greet();
        while (listen) {
            input = sc.nextLine();
            inputType type = inputType.fromString(input);

            try {
                int index;
                Task selected;
                String description;
                Task item;

                switch (type) {
                    case EXIT:
                        exit();
                        listen = false;
                        ;
                    case LIST:
                        listAll(list);
                        break;
                    case MARK:
                        index = Integer.parseInt(input.substring(5)) - 1;
                        selected = list.get(index);
                        selected.done();
                        mark(selected);
                        break;
                    case UNMARK:
                        index = Integer.parseInt(input.substring(7)) - 1;
                        selected = list.get(index);
                        selected.undone();
                        unmark(selected);
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
                        repeat(item, list.size());
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
                        item = new Deadline(description, "deadline", byPart);
                        list.add(item);
                        repeat(item, list.size());
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

                        item = new Event(description, "event", toPart, fromPart);
                        list.add(item);
                        repeat(item, list.size());
                        break;
                    case DELETE:
                        description = input.substring(7);
                        if (description.isEmpty()) {
                            throw new deleteException("Error deleting message");
                        }

                        index = Integer.parseInt(description) - 1;
                        System.out.println("    ____________________________________________________________\r\n" + //
                                "     Noted. I've removed this task:\r\n" + //
                                "       " + item(list.get(index)) + "\r\n" + //
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
            }
            // list.add(input);

            // System.out.println("\n");
            // try {
            // while (!input.equals("bye")) {
            // if (input.equals("list")) {
            // listAll(list);
            // } else if (input.substring(0, 4).equals("mark")) {
            // int index = Integer.parseInt(input.substring(5));
            // list.get(index - 1).toggle();
            // System.out.println("--------------------------------------------------------------------\n");
            // } else if (input.substring(0, 4).equals("todo")) {
            // if (input.substring(4).equals("")) {
            // throw new taskException(
            // " \n ____________________________________________________________\r\n" + //
            // " The description of a todo cannot be empty.\r\n" + //
            // " ____________________________________________________________");
            // }
            // list.add(new Task(input.substring(5), input.substring(0, 4)));
            // repeat(list.get(list.size() - 1), list.size());
            // } else if (input.substring(0, 8).equals("deadline")) {
            // if (input.substring(8).equals("")) {
            // throw new taskException(
            // " \n ____________________________________________________________\r\n" + //
            // " The description of a deadline cannot be empty.\r\n" + //
            // " ____________________________________________________________");
            // }

            // String byPart = input.split("/by")[1].trim();

            // list.add(new Deadline(input.split("deadline ")[1].split("/by")[0].trim(),
            // input.substring(0, 8),
            // byPart));
            // repeat(list.get(list.size() - 1), list.size());
            // } else if (input.substring(0, 5).equals("event")) {
            // if (input.substring(5).equals("")) {
            // throw new taskException(
            // " \n ____________________________________________________________\r\n" + //
            // " The description of an event cannot be empty.\r\n" + //
            // " ____________________________________________________________");
            // }

            // String fromPart = input.split("/from")[1].split("/to")[0].trim();
            // String toPart = input.split("/to")[1].trim();

            // list.add(new Event(input.split("event ")[1].split("/from")[0].trim(),
            // input.substring(0, 5),
            // fromPart,
            // toPart));
            // repeat(list.get(list.size() - 1), list.size());
            // } else if (input.contains("delete ")) {
            // try {
            // System.out.println("
            // ____________________________________________________________\r\n" + //
            // " Noted. I've removed this task:\r\n" + //
            // " " + item(list.get(Integer.parseInt(input.substring(7)) - 1)) + "\r\n" + //
            // " Now you have 4 tasks in the list.\r\n" + //
            // " ____________________________________________________________");
            // list.remove(Integer.parseInt(input.substring(7)) - 1);
            // } catch (Exception e) {
            // throw new deleteException("Error deleting message");
            // }
            // } else {
            // throw new taskException("\n
            // ____________________________________________________________\r\n" + //
            // " Wrong input, stop trolling :-(\r\n" + //
            // " ____________________________________________________________");
            // }
            // input = sc.nextLine();
            // }
            // } catch (deleteException e) {
            // System.out.println(e);

            // } catch (taskException e) {
            // System.out.println(e);
            // } catch (StringIndexOutOfBoundsException e) {
            // System.out.println(e);
            // }
            catch (taskException e) {
                System.out.println(e.getMessage());
                exit();
                sc.close();
            }
        }

    }

    public static void greet() {
        System.out.println("--------------------------------------------------------------------\n"
                + "Hello! I'm John\n"
                + "What can I do for you?\n\n"
                + "--------------------------------------------------------------------\n");

    }

    public static void exit() {
        System.out.println("--------------------------------------------------------------------\n"
                + "Bye. Hope to see you again soon!\n"
                + "--------------------------------------------------------------------\n");
    }

    public static void repeat(Task input, int listSize) {
        System.out.println("--------------------------------------------------------------------\n"
                + "Got it. I've added this task:\n"
                + "  " + item(input) + "\n"
                + listSize(listSize)
                + "--------------------------------------------------------------------\n");
    }

    public static String item(Task input) {
        String x = "[" + input.getType() + "][" + input.check() + "] " + input.name();
        return x;
    }

    public static String listSize(int size) {
        String x = "Now you have " + size + " tasks in the list.\n";
        return x;
    }

    public static void listAll(ArrayList<Task> list) {
        int counter = 1;
        System.out.println("--------------------------------------------------------------------");
        for (Task item : list) {
            System.out.println(counter + "." + item(item));
            counter++;
        }
        System.out.println("--------------------------------------------------------------------\n");
    }

    public static void mark(Task item) {
        System.out.println("    ____________________________________________________________\r\n" + //
                "     Nice! I've marked this task as done:\r\n" + //
                "       [X] return " + item.taskName + "\r\n" + //
                "    ____________________________________________________________\r\n" + //
                "");
    }

    public static void unmark(Task item) {
        System.out.println("    ____________________________________________________________\r\n" + //
                "     OK, I've marked this task as not done yet:\r\n" + //
                "       [ ] return book\r\n" + //
                "    ____________________________________________________________");
    }
}
