import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task {
    private int id;
    private String title;
    private String description;
    private int pointsValue;
    private boolean completed;
    private Date completionDate;
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

    public void completeTask(Profile profile) {
        if (!this.completed) {
            this.completed = true;
            this.completionDate = new Date();
            profile.addPoints(this.pointsValue);
            System.out.println("Task '" + this.title + "' completed! You earned " + this.pointsValue + " points.");
        } else {
            System.out.println("This task has already been completed.");
        }
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

    public Date getCompletionDate() {
        return completionDate;
    }

    public String getType() {
        return type;
    }
}