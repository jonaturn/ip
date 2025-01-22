import java.util.Scanner;
import java.util.ArrayList;

public class John {
    static class task {
        private final String taskName;
        private Boolean status;

        task(String taskName) {
            this.taskName = taskName;
            this.status = false;
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

        public void toggle() {
            this.status = !this.status;
        }
    }

    public static void main(String[] args) {
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
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                listAll(list);
            } else if (input.substring(0, 4).equals("mark")) {
                int index = Integer.parseInt(input.substring(5));
                list.get(index - 1).toggle();
                System.out.println("--------------------------------------------------------------------\n");

            } else {
                list.add(new task(input));
                repeat(input);
            }
            input = sc.nextLine();
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

    public static void repeat(String input) {
        System.out.println("--------------------------------------------------------------------\n"
                + "added: " + input + "\n"
                + "--------------------------------------------------------------------\n");
    }

    public static void listAll(ArrayList<task> list) {
        int counter = 1;
        System.out.println("--------------------------------------------------------------------");
        for (task item : list) {
            String check;
            if (item.status) {
                check = "X";
            } else {
                check = " ";
            }
            System.out.println(counter + ".[" + check + "] " + item.name());
            counter++;
        }
        System.out.println("--------------------------------------------------------------------\n");
    }
}
