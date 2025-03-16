import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TaskManager {
    private Map<String, Boolean> taskCompletionStatus = new HashMap<>();
    private List<Task> taskLibrary;

    public TaskManager() {
        this.taskLibrary = Task.createTaskLibrary();
        // Initialize all tasks as not completed
        for (Task task : taskLibrary) {
            // This ensures all possible task keys exist in the map
            taskCompletionStatus.put("_" + task.getId(), false);
        }
    }

    public List<Task> getTaskLibrary() {
        return taskLibrary;
    }

    public boolean isTaskCompleted(String taskKey) {
        // Make sure we have this key in our map
        if (!taskCompletionStatus.containsKey(taskKey)) {
            taskCompletionStatus.put(taskKey, false);
        }
        return taskCompletionStatus.getOrDefault(taskKey, false);
    }

    public Map<String, Boolean> getTaskCompletionStatus() {
        return taskCompletionStatus;
    }

    public void completeTask(String taskKey, Profile profile) {
        // Split by last underscore to handle email addresses with underscores
        String[] parts = taskKey.split("_");
        int taskId = Integer.parseInt(parts[parts.length - 1]);

        Task task = getTaskById(taskId);
        if (task != null && !taskCompletionStatus.getOrDefault(taskKey, false)) {
            task.completeTask(profile);
            taskCompletionStatus.put(taskKey, true);
        }
    }

    private Task getTaskById(int id) {
        for (Task task : taskLibrary) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    // Add a helper method to complete a task directly by ID
    public void completeTaskById(int taskId, String userEmail, Profile profile) {
        String taskKey = userEmail + "_" + taskId;
        completeTask(taskKey, profile);
    }
}