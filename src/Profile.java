import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

class Profile extends User {
    private int points;
    private List<String> badges;
    private List<Task> completedTasks;

    public Profile(String name, String email, String password) {
        super(name, email, password);
        this.points = 0;
        this.badges = new ArrayList<>();
        this.completedTasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public List<String> getBadges() {
        return badges;
    }

    public boolean hasBadge(String badgeName) {
        return badges.contains(badgeName);
    }

    public void addBadge(String badgeName) {
        if (!badges.contains(badgeName)) {
            badges.add(badgeName);
        }
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    public boolean isTaskCompleted(Task task) {
        return completedTasks.contains(task);
    }

    public void addCompletedTask(Task task) {
        if (!completedTasks.contains(task)) {
            completedTasks.add(task);
            addPoints(task.getPointsValue());
        }
    }

    public void setPoints(int points) {
        this.points = points;
    }

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
