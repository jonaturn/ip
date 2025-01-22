import java.util.Scanner;

public class John {
    public static void main(String[] args) {
        // init
        Scanner sc = new Scanner(System.in);
        String input;
        String logo = "         _       _           \n"
                + "        | | ___ | |__  _ __  \n"
                + "     _  | |/ _ \\| '_ \\| '_ \\\n"
                + "    | |_| | (_) | | | | | | |\n"
                + "     \\___/ \\___/|_| |_|_| |_|\n";

        System.out.println("Hello from\n" + logo);

        greet();
        input = sc.nextLine();
        // System.out.println("\n");
        while (!input.equals("bye")) {
            System.out.println("--------------------------------------------------------------------\n"
                    + input + "\n"
                    + "--------------------------------------------------------------------\n");
            input = sc.nextLine();
        }
        exit();
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
}
