package task;

public class Deadline extends Task {
    private final DukeDateTime dateTime;

    public Deadline(String content, DukeDateTime dateTime) {
        super(content);
        this.dateTime = dateTime;
    }

    public Deadline(String content, DukeDateTime dateTime, boolean isDone) {
        super(content, isDone);
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dateTime + ")";
    }

}
