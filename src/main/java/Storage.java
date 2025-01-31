import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
// import tasks.TaskList;

public class Storage {
    public void save(ArrayList<Task> al) throws IOException {
        Path path = Paths.get("data", "john.txt");
        FileWriter fw = new FileWriter(path.toString());
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

        fw.write(content);
        fw.close();
    }
}
