/**
 * Manages the application's data,
 * including the current user and task manager.
 */
public class ApplicationModel {
    private Profile currentUser;
    private TaskManager taskManager;

    /**
     * Constructs an ApplicationModel
     * and initializes the task manager.
     */
    public ApplicationModel() {
        taskManager = new TaskManager();
    }

    /**
     * Sets the current user profile.
     * @param user The user to set as the current user.
     */
    public void setCurrentUser(Profile user) {
        this.currentUser = user;
    }

    /**
     * Gets the task manager associated with the application.]
     * @return The TaskManager instance.
     */
    public TaskManager getTaskManager() {
        return taskManager;
    }
}