import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.format.FormatStyle;

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
        private LocalDateTime from;
        private LocalTime to;

        Event(String taskName, String type, LocalDateTime from, LocalTime to) {
            super(taskName, type);
            this.from = from;
            this.to = to;
        }

        @Override
        public String name() {
            // TODO Auto-generated method stub
            return super.name() + " (from: " + from.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mma"))
                    + " to: " + to.format(DateTimeFormatter.ofPattern("hh:mma"))
                    + ")";
        }

        public String from() {
            return this.from.toString();
        }

        public String to() {
            return this.toString();
        }
    }

    static class Deadline extends Task {
        private LocalDateTime by;

        Deadline(String taskName, String type, LocalDateTime by) {
            super(taskName, type);
            this.by = by;
        }

        @Override
        public String name() {
            // TODO Auto-generated method stub
            return super.name() + " (by: " + by.format(DateTimeFormatter.ofPattern("dd MMM yyyy h:mma")) + ")";
        }

        public String by() {
            return this.by.toString();
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
        Path path = Paths.get("data", "john.txt");
        FileWriter fw = new FileWriter(path.toString());

        System.out.println(path);

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
                LocalDateTime date;
                LocalDateTime fromDateTime;
                LocalTime toTime;

                switch (type) {
                    case EXIT:
                        exit();
                        // System.out.println(save(list, fw));
                        fw.write(save(list, fw));
                        listen = false;
                        break;
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
                        date = LocalDateTime.parse(byPart, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
                        item = new Deadline(description, "deadline", date);
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
                        fromDateTime = LocalDateTime.parse(fromPart, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
                        toTime = LocalTime.parse(toPart);
                        item = new Event(description, "event", fromDateTime, toTime);
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

            catch (taskException e) {
                System.out.println(e.getMessage());
                exit();
                sc.close();
                fw.write(save(list, fw));
            }
        }
        fw.close();
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

    // merge example again

    public static void unmark(Task item) {
        System.out.println("    ____________________________________________________________\r\n" + //
                "     OK, I've marked this task as not done yet:\r\n" + //
                "       [ ] return book\r\n" + //
                "    ____________________________________________________________");
    }

    public static String save(ArrayList<Task> al, FileWriter fw) throws IOException {
        String content = "";
        for (Task task : al) {
            content = task.getType() + " | [" + task.check() + "] " + task.name();
            if (task.getType() == "D") {
                Deadline task1 = (Deadline) task;
                content += " | " + task1.by() + "\n";
            } else if (task.getType() == "E") {
                Event task1 = (Event) task;
                content += " | from " + task1.from() + " to " + task1.to() + "\n";
            } else {
                content += "\n";
            }
        }
        // fw.write(content);
        return content;
    }
}

// this comment is for trying out git branch
