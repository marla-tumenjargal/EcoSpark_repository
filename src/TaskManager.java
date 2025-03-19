import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> taskLibrary;

    public TaskManager() {
        taskLibrary = new ArrayList<>();
        initializeTaskLibrary();
    }

    private void initializeTaskLibrary() {
        // Initialize with some default tasks
        taskLibrary.add(new Task("Reduce Water Usage", "Take shorter showers and turn off the tap while brushing teeth", 5, "water"));
        taskLibrary.add(new Task("Use Reusable Bags", "Bring your own bags when shopping instead of using plastic bags", 5, "waste"));
        taskLibrary.add(new Task("Energy Conservation", "Turn off lights and unplug electronics when not in use", 5, "energy"));
        taskLibrary.add(new Task("Plant a Tree", "Plant a native tree in your yard or community", 15, "conservation"));
        taskLibrary.add(new Task("Community Cleanup", "Participate in a local cleanup event", 10, "community"));
        // Add more tasks as needed
    }

    public List<Task> getTaskLibrary() {
        return taskLibrary;
    }

    public boolean isTaskCompleted(Profile user, Task task) {
        return user.isTaskCompleted(task);
    }

    public void completeTask(Profile user, Task task) {
        user.addCompletedTask(task);
        // Points are now added in the Profile.addCompletedTask method
    }

    // Method to add a new task to the library
    public void addTask(Task task) {
        if (!taskLibrary.contains(task)) {
            taskLibrary.add(task);
        }
    }
}

//public class TaskManager {
//    private List<Task> taskLibrary;
//    private Map<String, Boolean> taskCompletionStatus;
//
//    public TaskManager() {
//        this.taskLibrary = Task.createTaskLibrary();
//        this.taskCompletionStatus = new HashMap<>();
//    }
//
//    public List<Task> getTaskLibrary() {
//        return taskLibrary;
//    }
//
//    public List<Task> getTasksByType(String type) {
//        return taskLibrary.stream()
//                .filter(task -> task.getType().equals(type))
//                .collect(Collectors.toList());
//    }
//
//    public List<Task> getFavoriteTasks(Profile profile) {
//        // In a real application, this would fetch favorites based on the profile
//        // For now, returning a subset of tasks as "favorites"
//        List<Task> favorites = new ArrayList<>();
//        for (int i = 0; i < taskLibrary.size() && i < 5; i++) {
//            favorites.add(taskLibrary.get(i));
//        }
//        return favorites;
//    }
//
//    public boolean isTaskCompleted(Profile profile, Task task) {
//        String taskKey = profile.getEmail() + "_" + task.getId();
//        return taskCompletionStatus.getOrDefault(taskKey, false);
//    }
//
//    public void completeTask(Profile profile, Task task) {
//        String taskKey = profile.getEmail() + "_" + task.getId();
//
//        if (!taskCompletionStatus.getOrDefault(taskKey, false)) {
//            task.completeTask(profile);
//            profile.addCompletedTask(task);
//            taskCompletionStatus.put(taskKey, true);
//        }
//    }
//
//    public Task getTaskById(int id) {
//        for (Task task : taskLibrary) {
//            if (task.getId() == id) {
//                return task;
//            }
//        }
//        return null;
//    }
//
//    public Map<String, Boolean> getTaskCompletionStatus() {
//        return taskCompletionStatus;
//    }
//}