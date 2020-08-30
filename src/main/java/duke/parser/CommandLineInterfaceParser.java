package duke.parser;

import duke.commands.AddCommand;
import duke.commands.Command;
import duke.commands.CommandTypes;
import duke.commands.DeleteCommand;
import duke.commands.DoneCommand;
import duke.commands.ExitCommand;
import duke.commands.FindCommand;
import duke.commands.ListTasksCommand;

import duke.exceptions.DukeException;

import duke.task.Deadline;
import duke.task.DukeDateTime;
import duke.task.Event;
import duke.task.Todo;

import duke.utils.ResourceHandler;

import java.util.Scanner;

/**
 * Represents a parser which will parse user input into its respective commands.
 */

public class CommandLineInterfaceParser {
    private static final Scanner scanner = new Scanner(System.in);

    public static Command parse() throws DukeException {
        String userInput = scanner.nextLine();
        String[] words = userInput.toLowerCase().split(" ");
        CommandTypes commandType = CommandTypes.valueOf(words[0].toUpperCase());
        commandType.checkInput(userInput.toLowerCase());
        String content;
        String dateTime;
        String[] userInputArgs;
        DukeDateTime dukeDateTime;
        switch (commandType) {
        case LIST:
            ListTasksCommand listTasks = new ListTasksCommand();
            return listTasks;
        case BYE:
            ExitCommand exitDuke = new ExitCommand();
            return exitDuke;
        case TODO:
            content = userInput.replaceFirst("^(?i)todo", "");
            String trimmedContent = content.trim();
            AddCommand addTask = new AddCommand(new Todo(trimmedContent));
            return addTask;
        case DEADLINE:
            userInput = userInput.replaceFirst("^(?i)deadline", "");
            userInputArgs = userInput.split("\\s*/by\\s*");
            content = userInputArgs[0].trim();
            dateTime = userInputArgs[1];
            dukeDateTime = DateTimeParser.parseDateTime(dateTime);
            AddCommand addDeadline = new AddCommand(new Deadline(content, dukeDateTime));
            return addDeadline;
        case EVENT:
            userInput = userInput.replaceFirst("^(?i)event", "");
            userInputArgs = userInput.split("\\s*/at\\s*");
            content = userInputArgs[0].trim();
            dateTime = userInputArgs[1];
            dukeDateTime = DateTimeParser.parseDateTime(dateTime);
            AddCommand addEvent = new AddCommand(new Event(content, dukeDateTime));
            return addEvent;
        case DONE:
            try {
                int completedTaskIndex = Integer.parseInt(words[1]);
                DoneCommand completedTask = new DoneCommand(completedTaskIndex);
                return completedTask;
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException(
                        ResourceHandler.getMessage("commandline.invalidTaskIndexErrorMessage"));
            }
        case DELETE:
            try {
                int taskIndex = Integer.parseInt(words[1]);
                DeleteCommand deleteTask = new DeleteCommand(taskIndex);
                return deleteTask;
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException(
                        ResourceHandler.getMessage("commandline.invalidTaskIndexErrorMessage"));
            }
        case FIND:
            content = userInput.replaceFirst("^(?i)find", "");
            trimmedContent = content.trim();
            FindCommand findTaskCommand = new FindCommand(trimmedContent);
            return findTaskCommand;
        default:
            break;
        }
        scanner.close();
        return null;
    }
}
