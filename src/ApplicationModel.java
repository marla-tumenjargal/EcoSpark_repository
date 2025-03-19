public class ApplicationModel {
    private Profile currentUser;
    private TaskManager taskManager;

    public ApplicationModel() {
        taskManager = new TaskManager();
    }

    public void setCurrentUser(Profile user) {
        this.currentUser = user;
    }

    public Profile getCurrentUser() {
        return currentUser;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }
}