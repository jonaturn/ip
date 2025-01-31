
// import java.util.ArrayList;
import java.util.Scanner;

import tasks.TaskList;

// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.io.FileWriter;
import java.io.IOException;
// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import tasks.Task;
// import tasks.TaskList;
// import tasks.Deadline;
// import tasks.Event;

public class John {
    private Ui ui;
    private TaskList tasklist;
    private Storage storage;
    private Parser parser;

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

        // Path path = Paths.get("data", "john.txt");
        // FileWriter fw = new FileWriter(path.toString());
        ui.greet();
        while (listen) {
            input = sc.nextLine();
            listen = parser.input_handling(input, tasklist, ui, storage);
        }
        sc.close();

    }

    public static void main(String[] args) throws Exception {
        // init
        new John().run();
    }

}

// this comment is for trying out git branch
