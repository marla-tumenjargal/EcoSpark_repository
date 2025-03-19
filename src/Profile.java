import java.util.*;

public class Profile extends User {
    private int points;
    private List<String> badges;
    private List<Task> favoriteTasks;
    private List<Task> completedTasks;

    public Profile(String name, String email, String password) {
        super(name, email, password);
        this.points = 0;
        this.badges = new ArrayList<>();
        this.favoriteTasks = new ArrayList<>();
        this.completedTasks = new ArrayList<>();  // Initialize completed tasks list
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

    public List<Task> getFavoriteTasks() {
        return favoriteTasks;
    }

    public boolean isFavoriteTask(Task task) {
        return favoriteTasks.contains(task);
    }

    public void addFavoriteTask(Task task) {
        if (!favoriteTasks.contains(task)) {
            favoriteTasks.add(task);
        }
    }

    public void removeFavoriteTask(Task task) {
        favoriteTasks.remove(task);
    }

    // New methods for completed tasks
    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    public boolean isTaskCompleted(Task task) {
        return completedTasks.contains(task);
    }

    public void addCompletedTask(Task task) {
        if (!completedTasks.contains(task)) {
            completedTasks.add(task);
            // Add points automatically when task is completed
            addPoints(task.getPointsValue());
        }
    }
}

///**
// * The Profile class inherits from User and defines the log-in and log-out methods
// * that the User class has as abstract. Profile method stores the user's information.
// */
//public class Profile extends User {
//    private String name;
//    private int points;
//    private List<Task> completedTasks;
//    private List<String> badges;
//    private List<Task> favoriteTasks;
//
//    /**
//     * Constructor; constructs a new instance of the Profile class
//     * @param email, user's email
//     * @param password, user's password
//     * @param name, user's name
//     */
//    public Profile(String email, String password, String name) {
//        super(email, password);
//        this.name = name;
//        this.points = 0;
//        this.completedTasks = new ArrayList<>();
//        this.badges = new ArrayList<>();
//        this.favoriteTasks = new ArrayList<>();
//    }
//
//    public List<Task> getCompletedTasks() {
//        return completedTasks;
//    }
//
//    public List<Task> getFavoriteTasks() {
//        return favoriteTasks;
//    }
//
//    /**
//     * Login method
//     * @param email, user's email
//     * @param password, user's password
//     * @return true or false (whether the user has logged in or not)
//     */
//    @Override
//    public boolean login(String email, String password) {
//        if (this.email.equals(email) && this.password.equals(password)) {
//            this.isLoggedIn = true;
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * Logout method; logs out user
//     */
//    @Override
//    public void logout() {
//        this.isLoggedIn = false;
//    }
//
//    /**
//     * Getter, gets name
//     * @return name
//     */
//    public String getName() {
//        return name;
//    }
//
//    /**
//     * Setter, set name
//     * @param name
//     */
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    /**
//     * Getter, gets points
//     * @return points
//     */
//    public int getPoints() {
//        return points;
//    }
//
//    /**
//     * Setter, sets password
//     * @param password
//     */
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void addPoints(int points) {
//        this.points += points;
//    }
//
//    public void addCompletedTask(Task task) {
//        this.completedTasks.add(task);
//    }
//
//    public void addBadge(String badgeName) {
//        this.badges.add(badgeName); // Add a badge to the user's profile
//    }
//
//    public boolean hasBadge(String badgeName) {
//        return this.badges.contains(badgeName); // Check if the user has a specific badge
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public List<String> getBadges() {
//        return badges; // Return the list of earned badges
//    }
//
//}