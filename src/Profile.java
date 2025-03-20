import java.util.*;

public class Profile extends User {
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
}
