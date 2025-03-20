import java.util.Date;

public class Task {
    private int id;
    private String title;
    private String description;
    private int pointsValue;
    private boolean completed;
    private String type;
    private static int taskIdCounter = 1;

    public Task(String title, String description, int pointsValue, String type) {
        this.id = taskIdCounter++;
        this.title = title;
        this.description = description;
        this.pointsValue = pointsValue;
        this.completed = false;
        this.type = type;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPointsValue() {
        return pointsValue;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getType() {
        return type;
    }
}