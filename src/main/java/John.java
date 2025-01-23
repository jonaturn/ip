import java.util.Scanner;
import java.util.ArrayList;

public class John {
    static class task {
        private final String taskName;
        private Boolean status;
        private String type;

        task(String taskName, String type) {
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

    static class deleteException extends Exception {
        deleteException(String message) {
            super(message);
        }
    }

    static class event extends task {
        private String from;
        private String to;

        event(String taskName, String type, String from, String to) {
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

    static class deadline extends task {
        private String by;

        deadline(String taskName, String type, String by) {
            super(taskName, type);
            this.by = by;
        }

        @Override
        public String name() {
            // TODO Auto-generated method stub
            return super.name() + " (by: " + by + ")";
        }
    }

    public static void main(String[] args) throws Exception {
        // init
        Scanner sc = new Scanner(System.in);
        ArrayList<task> list = new ArrayList<>();
        String input;
        String logo = "         _       _           \n"
                + "        | | ___ | |__  _ __  \n"
                + "     _  | |/ _ \\| '_ \\| '_ \\\n"
                + "    | |_| | (_) | | | | | | |\n"
                + "     \\___/ \\___/|_| |_|_| |_|\n";

        System.out.println("Hello from\n" + logo);

        greet();
        input = sc.nextLine();
        // list.add(input);

        // System.out.println("\n");
        try {
            while (!input.equals("bye")) {
                if (input.equals("list")) {
                    listAll(list);
                } else if (input.substring(0, 4).equals("mark")) {
                    int index = Integer.parseInt(input.substring(5));
                    list.get(index - 1).toggle();
                    System.out.println("--------------------------------------------------------------------\n");
                } else if (input.substring(0, 4).equals("todo")) {
                    if (input.substring(4).equals("")) {
                        throw new taskException(
                                "    \n    ____________________________________________________________\r\n" + //
                                        "       The description of a todo cannot be empty.\r\n" + //
                                        "    ____________________________________________________________");
                    }
                    list.add(new task(input.substring(5), input.substring(0, 4)));
                    repeat(list.get(list.size() - 1), list.size());
                } else if (input.substring(0, 8).equals("deadline")) {
                    if (input.substring(8).equals("")) {
                        throw new taskException(
                                "    \n    ____________________________________________________________\r\n" + //
                                        "       The description of a deadline cannot be empty.\r\n" + //
                                        "    ____________________________________________________________");
                    }

                    String byPart = input.split("/by")[1].trim();

                    list.add(new deadline(input.split("deadline ")[1].split("/by")[0].trim(), input.substring(0, 8),
                            byPart));
                    repeat(list.get(list.size() - 1), list.size());
                } else if (input.substring(0, 5).equals("event")) {
                    if (input.substring(5).equals("")) {
                        throw new taskException(
                                "    \n    ____________________________________________________________\r\n" + //
                                        "       The description of an event cannot be empty.\r\n" + //
                                        "    ____________________________________________________________");
                    }

                    String fromPart = input.split("/from")[1].split("/to")[0].trim();
                    String toPart = input.split("/to")[1].trim();

                    list.add(new event(input.split("event ")[1].split("/from")[0].trim(), input.substring(0, 5),
                            fromPart,
                            toPart));
                    repeat(list.get(list.size() - 1), list.size());
                } else {
                    throw new taskException("\n    ____________________________________________________________\r\n" + //
                            "     Wrong input, stop trolling :-(\r\n" + //
                            "    ____________________________________________________________");
                }
                input = sc.nextLine();
            }
        } catch (taskException e) {
            System.out.println(e);
        }
        exit();
        sc.close();
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

    public static void repeat(task input, int listSize) {
        System.out.println("--------------------------------------------------------------------\n"
                + "Got it. I've added this task:\n"
                + "  " + item(input) + "\n"
                + listSize(listSize)
                + "--------------------------------------------------------------------\n");
    }

    public static String item(task input) {
        String x = "[" + input.getType() + "][" + input.check() + "] " + input.name();
        return x;
    }

    public static String listSize(int size) {
        String x = "Now you have " + size + " tasks in the list.\n";
        return x;
    }

    public static void listAll(ArrayList<task> list) {
        int counter = 1;
        System.out.println("--------------------------------------------------------------------");
        for (task item : list) {
            System.out.println(counter + "." + item(item));
            counter++;
        }
        System.out.println("--------------------------------------------------------------------\n");
    }
}
