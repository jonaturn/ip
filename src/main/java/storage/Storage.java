package storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.TaskException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.TaskList;

/**
 * Class that handles file IO
 */
public class Storage {
    private File file;

    /**
     * Constructor class for Storage
     */
    public Storage() {
        Path path = Paths.get("data", "john.txt");
        String fileName = path.toString();
        this.file = new File(fileName);

        try {
            if (file.createNewFile()) {
                System.out.println("\nFirst time using John, storage created\n");
            } else {
                System.out.println("\nWelcome back sir, Your data has already been loaded\n"
                        + "Use the 'list' command to view your saved tasks\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
        }
    }

    /**
     * Writes the latest list to file john.txt
     *
     * @param al latest list of Task
     * @throws IOException possible exceptions from file IO
     */
    public void save(ArrayList<Task> al) throws IOException {
        FileWriter fw = new FileWriter(this.file);
        String content = "";

        for (Task task : al) {
            content = task.getType() + " | [" + task.check() + "] " + task.name();
            if (task.getType().equals("D")) {
                Deadline task1 = (Deadline) task;
                content += " | " + task1.returnByDate() + "\n";
            } else if (task.getType().equals("E")) {
                Event task1 = (Event) task;
                content += " | from " + task1.from() + " to " + task1.to() + "\n";
            } else {
                content += "\n";
            }
        }

        fw.write(content);
        fw.close();
    }

    /**
     * Loads previously saved tasklist from john.txt
     *
     * @param tasklist object to contain tasks from txt file
     * @throws IOException
     */
    public TaskList load(TaskList tasklist) throws IOException {
        try {
            Scanner sc = new Scanner(this.file);
            while (sc.hasNextLine()) {
                String[] taskLineParts = sc.nextLine().split(" \\|");
                String taskDateTime = "";
                if (taskLineParts.length == 3) {
                    taskDateTime = taskLineParts[2];
                }
                String taskType = taskLineParts[0];
                String[] taskStatusAndDescription = taskLineParts[1].split("]");
                Boolean taskStatus = taskStatusAndDescription[0].contains("X");
                String taskDescription = taskStatusAndDescription[1];
                Task task;

                switch (taskType) {
                case "D":
                    LocalDateTime by = LocalDateTime.parse(taskDateTime, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
                    task = new Deadline(taskDescription, taskType, by);
                    if (taskStatus) {
                        task.done();
                    }
                    tasklist.add(task);
                    break;
                case "E":
                    String fromPart = taskDateTime.split("/from")[1].split("/to")[0].trim();
                    String toPart = taskDateTime.split("/to")[1].trim();
                    LocalDateTime fromDateTime = LocalDateTime.parse(
                            fromPart, DateTimeFormatter.ofPattern("d/M/yyyy HHmm")
                    );
                    LocalTime toTime = LocalTime.parse(toPart, DateTimeFormatter.ofPattern("HHmm"));
                    task = new Event(taskDescription, taskType, fromDateTime, toTime);
                    if (taskStatus) {
                        task.done();
                    }
                    tasklist.add(task);
                    break;
                case "T":
                    task = new Task(taskDescription, taskType);
                    if (taskStatus) {
                        task.done();
                    }
                    tasklist.add(task);
                    break;
                default:
                    throw new TaskException("Invalid conversion from txt file");
                }
            }
            sc.close();
            return tasklist;
        } catch (TaskException e) {
            System.out.println(e.getMessage());
            tasklist = new TaskList();
            return tasklist;
        }
    }
}
