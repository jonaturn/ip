import java.io.IOException;
import java.util.Scanner;

import tasks.TaskList;

public class John {
    private final Ui ui;
    private final TaskList tasklist;
    private final Storage storage;
    private final Parser parser;

    John() {
        this.ui = new Ui();
        this.storage = new Storage();
        this.parser = new Parser();
        this.tasklist = new TaskList();
    }

    public void run() throws IOException {
        Scanner sc = new Scanner(System.in);
        Boolean listen = true;
        String input;

        ui.greet();
        while (listen) {
            input = sc.nextLine();
            listen = parser.inputHandling(input, tasklist, ui, storage);
        }
        sc.close();

    }

    public static void main(String[] args) throws Exception {
        // init
        new John().run();
    }

}
