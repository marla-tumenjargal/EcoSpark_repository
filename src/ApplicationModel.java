public class ApplicationModel {
    private Profile currentUser;
    private TaskManager taskManager;

    public ApplicationModel() {
        taskManager = new TaskManager();
    }

    public void setCurrentUser(Profile user) {
        this.currentUser = user;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
}