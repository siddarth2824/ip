package duke.storage;

import duke.exceptions.DukeException;

import duke.parser.DateTimeParser;

import duke.task.Deadline;
import duke.task.DukeDateTime;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskManager;
import duke.task.Todo;

import duke.utils.Ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a translator which will encode the tasks in {@link TaskManager} object into the valid format to write
 * into {@link Storage} object and decode the information from {@link Storage} into the valid format to store in
 * {@link TaskManager} object.
 */

public class DataTranslator {
    private static final Ui formatter = new Ui();

    /**
     * Decodes a list of Strings into a TaskManager
     *
     * @param lines The list of Strings
     * @return The taskManager
     */
    public static TaskManager decode(List<String> lines) {
        TaskManager taskManager = new TaskManager();
        for (String line : lines) {
            Task task = null;
            String[] parsedLine = line.split(" \\| ");
            try {
                switch (parsedLine[0]) {
                case "T":
                    task = new Todo(parsedLine[2]);
                    break;
                case "D":
                    DukeDateTime deadlineDukeDateTime = DateTimeParser.parseDateTime(parsedLine[3]);
                    task = new Deadline(parsedLine[2], deadlineDukeDateTime);
                    break;
                case "E":
                    DukeDateTime eventDukeDateTime = DateTimeParser.parseDateTime(parsedLine[3]);
                    task = new Event(parsedLine[2], eventDukeDateTime);
                    break;
                default:
                    break;
                }
            } catch (DukeException e) {
                formatter.print(e.getMessage());
            }
            if (parsedLine[1].equals("1")) {
                assert task != null;
                task.markTaskAsDone();
            }
            taskManager.addTask(task);
        }
        return taskManager;
    }

    /**
     * Encodes a {@link TaskManager} into a list of Strings to be saved into a file
     * @param taskManager The {@link TaskManager}
     * @return The list of strings
     */
    public static List<String> encode(TaskManager taskManager) {
        List<Task> tasks = taskManager.getAllTasks();
        return tasks.stream()
                .map(Task::toDataFileFormat)
                .collect(Collectors.toList());
    }
}
