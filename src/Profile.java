import java.util.*;

/**
 * The Profile class inherits from User and defines the log-in and log-out methods
 * that the User class has as abstract. Profile method stores the user's information.
 */
public class Profile extends User {
    private String name;
    private int points;
    private List<Task> completedTasks;
    private List<String> badges;
    private List<Task> favoriteTasks;

    /**
     * Constructor; constructs a new instance of the Profile class
     * @param email, user's email
     * @param password, user's password
     * @param name, user's name
     */
    public Profile(String email, String password, String name) {
        super(email, password);
        this.name = name;
        this.points = 0;
        this.completedTasks = new ArrayList<>();
        this.badges = new ArrayList<>();
        this.favoriteTasks = new ArrayList<>();
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    public List<Task> getFavoriteTasks() {
        return favoriteTasks;
    }

    /**
     * Login method
     * @param email, user's email
     * @param password, user's password
     * @return true or false (whether the user has logged in or not)
     */
    @Override
    public boolean login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password)) {
            this.isLoggedIn = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Logout method; logs out user
     */
    @Override
    public void logout() {
        this.isLoggedIn = false;
    }

    /**
     * Getter, gets name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter, set name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter, gets points
     * @return points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Setter, sets password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void addCompletedTask(Task task) {
        this.completedTasks.add(task);
    }

    public void addBadge(String badgeName) {
        this.badges.add(badgeName); // Add a badge to the user's profile
    }

    public boolean hasBadge(String badgeName) {
        return this.badges.contains(badgeName); // Check if the user has a specific badge
    }

    public String getEmail() {
        return email;
    }

    public List<String> getBadges() {
        return badges; // Return the list of earned badges
    }

}