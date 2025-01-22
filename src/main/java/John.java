import java.util.Scanner;
import java.util.ArrayList;

public class John {
    public static void main(String[] args) {
        // init
        Scanner sc = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
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
            } else {
                list.add(input);
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

    public static void listAll(ArrayList<String> list) {
        int counter = 1;
        System.out.println("--------------------------------------------------------------------");
        for (String item : list) {
            System.out.println(counter + ". " + item);
            counter++;
        }
        System.out.println("--------------------------------------------------------------------\n");
    }
}
