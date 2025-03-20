import org.json.JSONObject;

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

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("title", title);
        json.put("description", description);
        json.put("pointsValue", pointsValue);
        json.put("completed", completed);
        json.put("type", type);
        return json;
    }

    public static Task fromJSON(JSONObject json) {
        Task task = new Task(
                json.getString("title"),
                json.getString("description"),
                json.getInt("pointsValue"),
                json.getString("type")
        );

        if (json.has("id")) {
            task.id = json.getInt("id");
            if (task.id >= taskIdCounter) {
                taskIdCounter = task.id + 1;
            }
        }

        if (json.has("completed")) {
            task.completed = json.getBoolean("completed");
        }

        return task;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pointsValue=" + pointsValue +
                ", completed=" + completed +
                ", type='" + type + '\'' +
                '}';
    }
}