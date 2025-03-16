import java.util.Map;

class Graph {
    public static void displayProgressGraph(Profile profile) {
        System.out.println("\n--- Task Progress Graph ---");
        System.out.println("Points earned: " + profile.getPoints());
        System.out.println("Tasks completed: " + profile.getTotalTasksCompleted());

        // Simulated progress bar using ASCII characters
        int progressPercentage = Math.min(100, (profile.getPoints() / 10));
        System.out.print("Progress: [");
        for (int i = 0; i < 20; i++) {
            if (i < progressPercentage / 5) {
                System.out.print("=");
            } else {
                System.out.print(" ");
            }
        }
        System.out.println("] " + progressPercentage + "%");

        // Achievement levels
        if (profile.getPoints() < 50) {
            System.out.println("Achievement Level: Beginner Environmentalist");
        } else if (profile.getPoints() < 150) {
            System.out.println("Achievement Level: Active Environmentalist");
        } else if (profile.getPoints() < 300) {
            System.out.println("Achievement Level: Environmental Hero");
        } else {
            System.out.println("Achievement Level: Environmental Champion");
        }

        System.out.println("---------------------------");
    }

    public static void displayCarbonFootprintGraph(CarbonFootprint footprint) {
        if (footprint.getCalculationDate() == null) {
            System.out.println("\nNo carbon footprint data available to display graph.");
            return;
        }

        Map<String, Double> breakdown = footprint.getDetailedBreakdown();
        double total = footprint.getTotalEmissions();

        System.out.println("\n--- Carbon Footprint Graph ---");

        // Display each category as a percentage of the total
        for (Map.Entry<String, Double> entry : breakdown.entrySet()) {
            String category = entry.getKey();
            double value = entry.getValue();
            int percentage = (int) ((value / total) * 100);

            System.out.print(category + " (" + String.format("%.1f", value) + " tons, " + percentage + "%): ");

            // Simple bar chart using ASCII characters
            for (int i = 0; i < percentage / 2; i++) {
                System.out.print("█");
            }
            System.out.println();
        }

        System.out.println("\nTotal: " + String.format("%.2f", total) + " tons of CO2 per year");
        System.out.println("-------------------------------");
    }
}