import java.util.ArrayList;
import java.util.List;

/**
 * Manages ecofriendly tasks and stores them by how often
 * they can be completed
 */
public class TaskManager {
    private List<Task> taskLibrary;

    /**
     * Constructs new instance of TaskManager and initialized task library
     */
    public TaskManager() {
        taskLibrary = new ArrayList<>();
        initializeTaskLibrary();
    }

    /**
     * Populates the task library with predefined eco-friendly tasks.
     */
    private void initializeTaskLibrary() {
        taskLibrary.add(new Task("Recycle a water bottle", "Collect and properly recycle plastic water bottles.", 5, "daily"));
        taskLibrary.add(new Task("Use reusable bags", "Bring your own reusable bags when shopping.", 10, "daily"));
        taskLibrary.add(new Task("Turn off lights", "Turn off lights when leaving a room to save energy.", 5, "daily"));
        taskLibrary.add(new Task("Save water", "Take shorter showers to conserve water.", 10, "daily"));
        taskLibrary.add(new Task("Reduce plastic use", "Avoid single-use plastics for a day.", 15, "daily"));
        taskLibrary.add(new Task("Unplug electronics when not in use", "Unplug devices to eliminate phantom energy drain.", 5, "daily"));
        taskLibrary.add(new Task("Wash clothes in cold water", "Save energy by washing clothes in cold water.", 6, "daily"));
        taskLibrary.add(new Task("Line-dry clothes", "Use a clothesline instead of a dryer to save energy.", 12, "daily"));
        taskLibrary.add(new Task("Bring reusable bags while shopping", "Avoid single-use plastic bags.", 6, "daily"));
        taskLibrary.add(new Task("Use a reusable water bottle", "Avoid disposable plastic bottles.", 7, "daily"));

        // Weekly Tasks
        taskLibrary.add(new Task("Walk or bike", "Choose walking or biking instead of driving for short trips.", 15, "weekly"));
        taskLibrary.add(new Task("Reduce meat consumption", "Have a meat-free day to reduce your carbon footprint.", 20, "weekly"));
        taskLibrary.add(new Task("Use public transport", "Take public transportation instead of driving alone.", 25, "weekly"));
        taskLibrary.add(new Task("Inflate tires properly", "Improve fuel efficiency by maintaining proper tire pressure.", 5, "weekly"));
        taskLibrary.add(new Task("Combine errands into fewer trips", "Reduce emissions by planning efficient trips.", 8, "weekly"));
        taskLibrary.add(new Task("Work from home", "Eliminate commuting emissions by working remotely.", 20, "weekly"));
        taskLibrary.add(new Task("Maintain your vehicle", "Keep your car in good condition to reduce emissions.", 8, "weekly"));
        taskLibrary.add(new Task("Use cloth napkins", "Replace disposable paper napkins with reusable ones.", 6, "weekly"));
        taskLibrary.add(new Task("Buy in bulk", "Reduce packaging waste by purchasing in bulk.", 9, "weekly"));
        taskLibrary.add(new Task("Use reusable food containers", "Avoid single-use plastic wrap and containers.", 7, "weekly"));

        // Monthly Tasks
        taskLibrary.add(new Task("Plant a tree", "Plant a tree to help absorb CO2 from the atmosphere.", 50, "monthly"));
        taskLibrary.add(new Task("Clean up litter", "Collect and dispose of litter in your neighborhood.", 30, "monthly"));
        taskLibrary.add(new Task("Donate unwanted items", "Give usable items a second life instead of trashing them.", 10, "monthly"));
        taskLibrary.add(new Task("Opt out of junk mail", "Reduce paper waste by stopping unwanted mail.", 6, "monthly"));
        taskLibrary.add(new Task("Calculate your carbon footprint", "Understand your environmental impact.", 8, "monthly"));
        taskLibrary.add(new Task("Read books on environmental topics", "Educate yourself about sustainability.", 7, "monthly"));
        taskLibrary.add(new Task("Support environmental organizations", "Contribute to conservation efforts.", 15, "monthly"));
        taskLibrary.add(new Task("Volunteer for community cleanup events", "Help keep your community clean.", 15, "monthly"));
        taskLibrary.add(new Task("Participate in local environmental initiatives", "Get involved in local projects.", 12, "monthly"));
        taskLibrary.add(new Task("Educate others about eco-friendly practices", "Spread awareness about sustainability.", 10, "monthly"));

        // One-Time Tasks
        taskLibrary.add(new Task("Install a programmable thermostat", "Automatically adjust your home temperature to save energy.", 15, "one-time"));
        taskLibrary.add(new Task("Replace incandescent bulbs with LEDs", "Use energy-efficient lighting.", 8, "one-time"));
        taskLibrary.add(new Task("Install low-flow showerheads", "Reduce water usage with efficient fixtures.", 10, "one-time"));
        taskLibrary.add(new Task("Fix leaky faucets", "Prevent water waste by repairing leaks.", 7, "one-time"));
        taskLibrary.add(new Task("Install a rain barrel", "Collect rainwater for garden use.", 20, "one-time"));
        taskLibrary.add(new Task("Use smart power strips", "Eliminate phantom energy usage.", 8, "one-time"));
        taskLibrary.add(new Task("Apply weatherstripping to doors and windows", "Seal air leaks to improve energy efficiency.", 14, "one-time"));
        taskLibrary.add(new Task("Plant shade trees around your home", "Reduce cooling costs with strategic landscaping.", 25, "one-time"));
        taskLibrary.add(new Task("Switch to a tankless water heater", "Heat water on demand to save energy.", 18, "one-time"));
        taskLibrary.add(new Task("Add insulation to your attic", "Improve home insulation to reduce energy loss.", 22, "one-time"));
        taskLibrary.add(new Task("Install a dual-flush toilet", "Reduce water consumption with efficient toilets.", 15, "one-time"));
        taskLibrary.add(new Task("Set up a graywater system", "Reuse water from sinks and showers for irrigation.", 30, "one-time"));
        taskLibrary.add(new Task("Start a backyard compost pile", "Turn food scraps into nutrient-rich compost.", 15, "one-time"));
        taskLibrary.add(new Task("Grow some of your own food", "Start a home garden to reduce food miles.", 18, "one-time"));
        taskLibrary.add(new Task("Start a recycling program at work", "Encourage recycling in your workplace.", 15, "one-time"));
        taskLibrary.add(new Task("Encourage paperless practices at work", "Reduce paper usage in your office.", 10, "one-time"));
        taskLibrary.add(new Task("Suggest energy efficiency improvements", "Identify ways to save energy at work.", 12, "one-time"));
        taskLibrary.add(new Task("Set up a carpool system with colleagues", "Reduce commuting emissions by carpooling.", 14, "one-time"));
        taskLibrary.add(new Task("Start a workplace sustainability committee", "Drive sustainability initiatives at work.", 20, "one-time"));
        taskLibrary.add(new Task("Conduct a workplace energy audit", "Identify energy-saving opportunities at work.", 15, "one-time"));
        taskLibrary.add(new Task("Advocate for green purchasing policies", "Promote sustainable procurement at work.", 12, "one-time"));
        taskLibrary.add(new Task("Bring plants into the office environment", "Improve air quality and morale with indoor plants.", 6, "one-time"));
        taskLibrary.add(new Task("Learn a sustainability skill", "Develop skills like food preservation or basic repairs.", 15, "one-time"));
        taskLibrary.add(new Task("Talk with children about environmental stewardship", "Educate the next generation about sustainability.", 10, "one-time"));
        taskLibrary.add(new Task("Organize a neighborhood swap meet", "Exchange items to reduce waste.", 12, "one-time"));
        taskLibrary.add(new Task("Plant native species in your garden", "Support local ecosystems with native plants.", 16, "one-time"));
        taskLibrary.add(new Task("Create a wildlife-friendly yard", "Provide habitat for local wildlife.", 18, "one-time"));
        taskLibrary.add(new Task("Join a citizen science project", "Contribute to environmental research.", 10, "one-time"));
        taskLibrary.add(new Task("Advocate for renewable energy in your community", "Promote clean energy solutions locally.", 18, "one-time"));

    }

    /**
     * Getter, gets task library
     * @return tasklibrary
     */
    public List<Task> getTaskLibrary() {
        return taskLibrary;
    }

    /**
     * Returns true if the given task is completed
     * @param user, user
     * @param task, task user is completing
     * @return
     */
    public boolean isTaskCompleted(Profile user, Task task) {
        return user.isTaskCompleted(task);
    }

    /**
     * Adds the completed task to the user's profile
     * @param user, user
     * @param task, task
     */
    public void completeTask(Profile user, Task task) {
        user.addCompletedTask(task);
    }
}