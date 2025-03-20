import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Profile class represents the user's profile.
 * It stores the user's progress, completed tasks, badges, and points.
 */
class Profile extends User {
    private int points;
    private List<String> badges;
    private List<Task> completedTasks;

    /**
     * Constructor, constructs a user's profile
     *
     * @param name user's name
     * @param email user's email
     * @param password user's password.
     */
    public Profile(String name, String email, String password) {
        super(name, email, password);
        this.points = 0;
        this.badges = new ArrayList<>();
        this.completedTasks = new ArrayList<>();
    }

    /**
     * Gets the user's name.
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     * @param name new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's email.
     * @return user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the user's password.
     * @return user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * @param password new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's total points.
     * @return total user points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Adds points to the user's total.
     * @param points number of points to add.
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * Gets the list of badges the user has earned.
     * @return list of earned badges.
     */
    public List<String> getBadges() {
        return badges;
    }

    /**
     * Checks if the user has a specific badge.
     * @param badgeName name of the badge.
     * @return true if the user has the badge, false otherwise.
     */
    public boolean hasBadge(String badgeName) {
        return badges.contains(badgeName);
    }

    /**
     * Adds a badge to the user's collection.
     * @param badgeName name of the badge to add.
     */
    public void addBadge(String badgeName) {
        if (!badges.contains(badgeName)) {
            badges.add(badgeName);
        }
    }

    /**
     * Gets the list of tasks the user has completed.
     * @return list of completed tasks.
     */
    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    /**
     * Checks if a task has been completed by the user.
     * @param task The task to check.
     * @return true if the task is completed, false otherwise.
     */
    public boolean isTaskCompleted(Task task) {
        return completedTasks.contains(task);
    }

    /**
     * Adds a completed task to the user's record and awards points.
     * @param task The completed task.
     */
    public void addCompletedTask(Task task) {
        if (!completedTasks.contains(task)) {
            completedTasks.add(task);
            addPoints(task.getPointsValue());
        }
    }

    /**
     * Sets the user's total points.
     * @param points total number of points.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Creates a Profile object from a JSON representation.
     * @param json The JSON object containing profile data.
     * @return A Profile object populated with the JSON data.
     */
    public static Profile fromJSON(JSONObject json) {
        String name = json.getString("name");
        String email = json.getString("email");
        String password = json.getString("password");
        int points = json.getInt("points");

        Profile profile = new Profile(name, email, password);
        profile.setPoints(points);

        JSONArray completedTasksArray = json.getJSONArray("completedTasks");
        for (int i = 0; i < completedTasksArray.length(); i++) {
            JSONObject taskJson = completedTasksArray.getJSONObject(i);
            Task task = Task.fromJSON(taskJson); // Ensure Task has a fromJSON method
            profile.getCompletedTasks().add(task);
        }

        JSONArray badgesArray = json.getJSONArray("badges");
        for (int i = 0; i < badgesArray.length(); i++) {
            String badge = badgesArray.getString(i);
            profile.getBadges().add(badge);
        }

        return profile;
    }

    /**
     * Converts the profile into a JSON representation.
     * @return JSONObject representing the profile.
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("email", email);
        json.put("password", password);
        json.put("points", points);

        JSONArray completedTasksArray = new JSONArray();
        for (Task task : completedTasks) {
            completedTasksArray.put(task.toJSON());
        }
        json.put("completedTasks", completedTasksArray);

        JSONArray badgesArray = new JSONArray();
        for (String badge : badges) {
            badgesArray.put(badge);
        }
        json.put("badges", badgesArray);
        return json;
    }
}
