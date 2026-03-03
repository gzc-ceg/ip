package bruno.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a task with a specific deadline.
 * The deadline can be stored as either a LocalDate (if parsable) or a plain string.
 */
public class Deadline extends Task {

    /**
     * Time can be stored in two ways:
     *
     * 1. If the user inputs a date in the standard format "yyyy-MM-dd" (e.g., 2026-02-14),
     *    the date will be formatted in the "MMM dd yyyy" pattern (e.g., Feb 14 2026).
     *
     * 2. If the user inputs a time in any other format (e.g., "next Monday" or "14/2/26"),
     *    the input is stored as a plain string and displayed exactly as the user entered it.
     *
     */

    private final LocalDate byDate;
    private final String byStr;

    public Deadline(String description, String by) {
        super(description);
        LocalDate tempDate = null;
        try {
            tempDate = LocalDate.parse(by.trim());
        } catch (DateTimeParseException e) {
            // leave tempDate as null
        }
        this.byDate = tempDate;
        this.byStr = by.trim();
    }

    /**
     * Returns the deadline in a standard string format.
     * If stored as a LocalDate, returns "yyyy-MM-dd"; otherwise returns the original string.
     *
     * @return The deadline as a string suitable for storage.
     */
    public String getBy() {
        return byDate != null ? byDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : byStr;
    }

    /**
     * Returns a string representation of the deadline task, including its status icon,
     * description, and formatted deadline.
     *
     * @return The formatted string representation of the task.
     */
    @Override
    public String toString() {
        String display = byDate != null
                ? byDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH))
                : byStr;
        return "[D]" + super.toString().trim() + " (by: " + display + ")";
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }
}
