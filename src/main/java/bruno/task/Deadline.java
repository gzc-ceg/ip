package bruno.task;

public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString().trim() + " (by: " + by.trim() + ')';
    }

    public String getDescription() {
        return super.getDescription();
    }

    public String getBy() {
        return by;
    }
}
