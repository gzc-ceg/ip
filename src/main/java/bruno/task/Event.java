package bruno.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents an event task with a start and end time.
 * The times can be stored as either LocalDates (if parsable) or plain strings.
 */
public class Event extends Task {

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

    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final String fromStr;
    private final String toStr;

    public Event(String description, String from, String to) {
        super(description);

        LocalDate tempFrom = null;
        LocalDate tempTo = null;

        try {
            tempFrom = LocalDate.parse(from.trim());
        } catch (DateTimeParseException e) {
        }

        try {
            tempTo = LocalDate.parse(to.trim());
        } catch (DateTimeParseException e) {
        }

        this.fromDate = tempFrom;
        this.toDate = tempTo;
        this.fromStr = from.trim();
        this.toStr = to.trim();
    }

    public String getFrom() {
        return fromDate != null ? fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : fromStr;
    }

    public String getTo() {
        return toDate != null ? toDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : toStr;
    }

    /**
     * Returns a string representation of the event task, including its status icon,
     * description, and formatted start and end times.
     *
     * @return The formatted string representation of the task.
     */
    @Override
    public String toString() {
        String fromDisplay = fromDate != null
                ? fromDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH))
                : fromStr;
        String toDisplay = toDate != null
                ? toDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH))
                : toStr;
        return "[E]" + super.toString().trim() + " (from: " + fromDisplay + " to: " + toDisplay + ")";
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }
}