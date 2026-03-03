package bruno;

import bruno.command.*;
import bruno.exception.BrunoException;
import bruno.TaskList;

/**
 * Parses user input strings and converts them into executable Command objects.
 */
public class Parser {

    /**
     * Parses the user input and returns the corresponding Command object.
     *
     * @param input The raw user input string.
     * @param tasks The current task list (used for index validation).
     * @return A Command object corresponding to the parsed input.
     * @throws BrunoException If the input is invalid, empty, or cannot be parsed.
     */
    public static Command parse(String input, TaskList tasks) throws BrunoException {
        if (input == null || input.trim().isEmpty()) {
            throw new BrunoException("Please say something, I am listening...");
        }

        String[] parts = input.trim().split(" ", 2);
        String command = parts[0].toLowerCase();

        switch (command) {
        case "bye":
            return new ExitCommand();

        case "list":
            return new ListCommand();

        case "mark":
            return new MarkCommand(parseIndex(parts, tasks));

        case "unmark":
            return new UnmarkCommand(parseIndex(parts, tasks));

        case "delete":
            return new DeleteCommand(parseIndex(parts, tasks));

        case "todo":
            checkDescription(parts, "The description of a todo cannot be empty.");
            return new AddTodoCommand(parts[1].trim());

        case "deadline":
            checkDescription(parts, "The description of a deadline cannot be empty.");
            String[] deadlineParts = parts[1].split("/by", 2);
            if (deadlineParts.length < 2) {
                throw new BrunoException(
                        "Invalid deadline format. Please use: deadline [description] /by [time]"
                );
            }
            return new AddDeadlineCommand(deadlineParts[0].trim(), deadlineParts[1].trim());

        case "event":
            checkDescription(parts, "The description of an event cannot be empty.");
            String[] eventParts = parts[1].split("/from|/to");
            if (eventParts.length < 3) {
                throw new BrunoException(
                        "Invalid event format. Please use: event [description] /from [start] /to [end]"
                );
            }
            return new AddEventCommand(
                    eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim()
            );

        case "find":
            checkDescription(parts, "Please provide a keyword to search for.");
            return new FindCommand(parts[1].trim());

        default:
            throw new BrunoException(
                    "I'm sorry, but I don't know what that means :-("
            );
        }
    }

    /**
     * Parses a task index from the input parts, validates it, and returns the zero-based index.
     *
     * @param parts The input split into command and arguments.
     * @param tasks The current task list for bounds checking.
     * @return The zero-based index of the task.
     * @throws BrunoException If the index is missing, not a number, or out of bounds.
     */
    private static int parseIndex(String[] parts, TaskList tasks) throws BrunoException {
        if (parts.length < 2) throw new BrunoException("Please specify a task number.");

        try {
            int index = Integer.parseInt(parts[1].trim()) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new BrunoException("Task number must be between 1 and " + tasks.size());
            }
            return index;
        } catch (NumberFormatException e) {
            throw new BrunoException("Please provide a valid task number.");
        }
    }

    /**
     * Checks that the input contains a non-empty description after the command.
     *
     * @param parts The input split into command and arguments.
     * @param errorMsg The error message to throw if description is missing.
     * @throws BrunoException If the description is missing or empty.
     */
    private static void checkDescription(String[] parts, String errorMsg) throws BrunoException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new BrunoException(errorMsg);
        }
    }
}