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
        sc.close();
    }

    /**
     * returns john logo
     */
    public String returnLogo() throws IOException {
        this.tasklist = storage.load(tasklist);
        return ui.returnLogo();
    }

    /**
     * Invoke the function that handles input
     */
    public String getResponse(String input) throws IOException {
        return parser.inputHandling(input, tasklist, ui, storage);
    }

    /**
     * Java main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new John().run();
    }

}
