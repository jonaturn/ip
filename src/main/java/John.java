import java.io.IOException;
import java.util.Scanner;

import parser.Parser;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;

/**
 * Class that handles the chatbot
 */
public class John {
    private final Ui ui;
    private final Storage storage;
    private final Parser parser;
    private TaskList tasklist;

    John() {
        this.ui = new Ui();
        this.storage = new Storage();
        this.parser = new Parser();
        this.tasklist = new TaskList();
    }

    /**
     * Initialises and runs the chatbot
     *
     * @throws IOException
     */
    public void run() throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean listen = true;
        String input;
        tasklist = storage.load(tasklist);

        ui.greet();
        while (listen) {
            input = sc.nextLine();
            listen = parser.inputHandling(input, tasklist, ui, storage);
        }
        sc.close();

    }

    public String getResponse(String input) {
        return "John heard: " + input;
    }

    public static void main(String[] args) throws Exception {
        // init
        new John().run();
    }

}
