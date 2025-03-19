import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> taskLibrary;
    private Map<String, Boolean> taskCompletionStatus;

    public TaskManager() {
        this.taskLibrary = Task.createTaskLibrary();
        this.taskCompletionStatus = new HashMap<>();
    }

    public List<Task> getTaskLibrary() {
        return taskLibrary;
    }

    public List<Task> getTasksByType(String type) {
        return taskLibrary.stream()
                .filter(task -> task.getType().equals(type))
                .collect(Collectors.toList());
    }

    public List<Task> getFavoriteTasks(Profile profile) {
        // In a real application, this would fetch favorites based on the profile
        // For now, returning a subset of tasks as "favorites"
        List<Task> favorites = new ArrayList<>();
        for (int i = 0; i < taskLibrary.size() && i < 5; i++) {
            favorites.add(taskLibrary.get(i));
        }
        return favorites;
    }

    public boolean isTaskCompleted(Profile profile, Task task) {
        String taskKey = profile.getEmail() + "_" + task.getId();
        return taskCompletionStatus.getOrDefault(taskKey, false);
    }

    public void completeTask(Profile profile, Task task) {
        String taskKey = profile.getEmail() + "_" + task.getId();

        if (!taskCompletionStatus.getOrDefault(taskKey, false)) {
            task.completeTask(profile);
            profile.addCompletedTask(task);
            taskCompletionStatus.put(taskKey, true);
        }
    }

    public Task getTaskById(int id) {
        for (Task task : taskLibrary) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public Map<String, Boolean> getTaskCompletionStatus() {
        return taskCompletionStatus;
    }
}