import org.json.JSONObject;

/**
 * The Task class defines a task with attributes
 * such as title, point, completion, type, and description
 */
public class Task {
    private int id;
    private String title;
    private String description;
    private int pointsValue;
    private boolean completed;
    private String type;
    private static int taskIdCounter = 1;

    /**
     * Constructor, creates new instance of Task class
     * @param title, title of task
     * @param description, description of task
     * @param pointsValue, amount of points assigned to task
     * @param type, type of task
     */
    public Task(String title, String description, int pointsValue, String type) {
        this.id = taskIdCounter++;
        this.title = title;
        this.description = description;
        this.pointsValue = pointsValue;
        this.completed = false;
        this.type = type;
    }

    /**
     * Getter, gets title of task
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter, gets description of task
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter, gets amount of points of a task
     * @return pointsValue
     */
    public int getPointsValue() {
        return pointsValue;
    }

    /**
     * Getter, gets type of task
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Converts the task into a JSON representation.
     *
     * @return A JSONObject representing the task.
     */
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

    /**
     * Creates a Task object from a JSON representation.
     *
     * @param json JSON object
     * @return A Task object populated with the JSON data.
     */
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

    /**
     * Overriden method returning string of tasks
     * @return
     */
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