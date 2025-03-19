import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles the calculation and analysis of a user's carbon footprint
 * based on their lifestyle inputs across various categories.
 * hihihih
 */
class CarbonFootprint {
    // Carbon emission values by category in metric tons
    private double homeEmissions;
    private double transportEmissions;
    private double foodEmissions;
    private double wasteEmissions;

    // Detailed breakdown for subcategories
    private Map<String, Double> homeBreakdown;
    private Map<String, Double> transportBreakdown;
    private Map<String, Double> foodBreakdown;
    private Map<String, Double> wasteBreakdown;

    // Timestamp of when calculation was performed
    private Date calculationDate;

    // Constants for emission factors
    private static final double ELECTRICITY_FACTOR = 0.92; // pounds CO2 per kWh
    private static final double NATURAL_GAS_FACTOR = 11.7; // pounds CO2 per therm
    private static final double CAR_EMISSIONS = 404.0; // grams CO2 per mile (average)
    private static final double PUBLIC_TRANSPORT_EMISSIONS = 150.0; // grams CO2 per mile
    private static final double FLIGHT_EMISSIONS = 200.0; // grams CO2 per mile
    private static final double WASTE_EMISSIONS = 2.1; // kg CO2 per kg of waste
    private static final double AVERAGE_US_EMISSIONS = 16.0; // tons CO2 per year

    /**
     * Constructor initializes all emission categories to zero
     * and creates maps for detailed breakdown.
     */
    public CarbonFootprint() {
        homeEmissions = 0;
        transportEmissions = 0;
        foodEmissions = 0;
        wasteEmissions = 0;

        homeBreakdown = new HashMap<>();
        transportBreakdown = new HashMap<>();
        foodBreakdown = new HashMap<>();
        wasteBreakdown = new HashMap<>();
    }

    /**
     * Calculates the carbon footprint based on user inputs.
     *
     * @param electricityUsage Monthly electricity usage in kWh
     * @param gasUsage Monthly natural gas usage in therms
     * @param carMiles Annual miles driven
     * @param publicTransportMiles Annual miles on public transportation
     * @param flightMiles Annual miles flown
     * @param isVegetarian Whether the user is vegetarian
     * @param meatMealsPerWeek Number of meat-containing meals per week
     * @param wasteProducedKg Annual waste produced in kg
     */
    public void calculateFootprint(
            double electricityUsage, double gasUsage,
            double carMiles, double publicTransportMiles, double flightMiles,
            boolean isVegetarian, int meatMealsPerWeek, double wasteProducedKg) {

        // Record calculation date
        calculationDate = new Date();

        // Calculate home emissions
        calculateHomeEmissions(electricityUsage, gasUsage);

        // Calculate transportation emissions
        calculateTransportEmissions(carMiles, publicTransportMiles, flightMiles);

        // Calculate food emissions
        calculateFoodEmissions(isVegetarian, meatMealsPerWeek);

        // Calculate waste emissions
        calculateWasteEmissions(wasteProducedKg);
    }

    /**
     * Calculates emissions from home energy usage.
     *
     * @param electricityUsage Monthly electricity usage in kWh
     * @param gasUsage Monthly natural gas usage in therms
     */
    private void calculateHomeEmissions(double electricityUsage, double gasUsage) {
        // Calculate electricity emissions (convert from pounds to metric tons)
        double electricityEmissions = (electricityUsage * 12 * ELECTRICITY_FACTOR) / 2204.62;
        homeBreakdown.put("Electricity", electricityEmissions);

        // Calculate natural gas emissions (convert from pounds to metric tons)
        double gasEmissions = (gasUsage * 12 * NATURAL_GAS_FACTOR) / 2204.62;
        homeBreakdown.put("Natural Gas", gasEmissions);

        // Sum up total home emissions
        homeEmissions = electricityEmissions + gasEmissions;
    }

    /**
     * Calculates emissions from transportation.
     *
     * @param carMiles Annual miles driven
     * @param publicTransportMiles Annual miles on public transportation
     * @param flightMiles Annual miles flown
     */
    private void calculateTransportEmissions(double carMiles, double publicTransportMiles, double flightMiles) {
        // Calculate car emissions (convert from grams to metric tons)
        double carEmissions = (carMiles * CAR_EMISSIONS) / 1000000;
        transportBreakdown.put("Car", carEmissions);

        // Calculate public transport emissions (convert from grams to metric tons)
        double publicTransportEmissions = (publicTransportMiles * PUBLIC_TRANSPORT_EMISSIONS) / 1000000;
        transportBreakdown.put("Public Transport", publicTransportEmissions);

        // Calculate flight emissions (convert from grams to metric tons)
        double flightEmissions = (flightMiles * FLIGHT_EMISSIONS) / 1000000;
        transportBreakdown.put("Flights", flightEmissions);

        // Sum up total transport emissions
        transportEmissions = carEmissions + publicTransportEmissions + flightEmissions;
    }

    /**
     * Calculates emissions from food consumption.
     *
     * @param isVegetarian Whether the user is vegetarian
     * @param meatMealsPerWeek Number of meat-containing meals per week
     */
    private void calculateFoodEmissions(boolean isVegetarian, int meatMealsPerWeek) {
        // Base emission values
        double baseEmission = isVegetarian ? 1.75 : 2.5;
        double meatEmission = 0;
        double nonMeatEmission = 0;

        if (isVegetarian) {
            // Vegetarian diet
            nonMeatEmission = baseEmission;
            foodBreakdown.put("Plant-based Foods", nonMeatEmission);
        } else {
            // Non-vegetarian diet
            // Average person has about 10 meat meals per week
            double meatFactor = meatMealsPerWeek / 10.0;

            // 30% of food emissions come from meat in average diet
            meatEmission = baseEmission * 0.3 * meatFactor;
            nonMeatEmission = baseEmission * 0.7;

            foodBreakdown.put("Meat", meatEmission);
            foodBreakdown.put("Other Foods", nonMeatEmission);
        }

        // Total food emissions
        foodEmissions = meatEmission + nonMeatEmission;
    }

    /**
     * Calculates emissions from waste.
     *
     * @param wasteProducedKg Annual waste produced in kg
     */
    private void calculateWasteEmissions(double wasteProducedKg) {
        // Convert kg waste to tons CO2
        wasteEmissions = wasteProducedKg * WASTE_EMISSIONS / 1000;
        wasteBreakdown.put("General Waste", wasteEmissions);
    }

    /**
     * Gets the total carbon emissions across all categories.
     *
     * @return Total emissions in metric tons CO2 per year
     */
    public double getTotalEmissions() {
        return homeEmissions + transportEmissions + foodEmissions + wasteEmissions;
    }

    /**
     * Gets a breakdown of emissions by major category.
     *
     * @return Map of category names to emission values
     */
    public Map<String, Double> getDetailedBreakdown() {
        Map<String, Double> breakdown = new HashMap<>();
        breakdown.put("Home", homeEmissions);
        breakdown.put("Transport", transportEmissions);
        breakdown.put("Food", foodEmissions);
        breakdown.put("Waste", wasteEmissions);
        return breakdown;
    }

    /**
     * Gets a detailed breakdown within each category.
     *
     * @param category The category to get detailed breakdown for
     * @return Map of subcategory names to emission values
     */
    public Map<String, Double> getSubcategoryBreakdown(String category) {
        switch (category) {
            case "Home":
                return homeBreakdown;
            case "Transport":
                return transportBreakdown;
            case "Food":
                return foodBreakdown;
            case "Waste":
                return wasteBreakdown;
            default:
                return new HashMap<>();
        }
    }

    /**
     * Gets the highest emission category.
     *
     * @return String representing highest emission category
     */
    public String getHighestCategory() {
        Map<String, Double> breakdown = getDetailedBreakdown();
        return breakdown.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");
    }

    /**
     * Gets the formatted calculation date.
     *
     * @return String representation of the calculation date
     */
    public String getCalculationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        return sdf.format(calculationDate);
    }

    /**
     * Compares user's footprint to national average and returns status.
     *
     * @return String describing comparison to average
     */
    public String getComparisonStatus() {
        double ratio = getTotalEmissions() / AVERAGE_US_EMISSIONS;

        if (ratio < 0.5) {
            return "Excellent - Your footprint is less than half the national average";
        } else if (ratio < 0.7) {
            return "Very Good - Your footprint is significantly below average";
        } else if (ratio < 0.9) {
            return "Good - Your footprint is below average";
        } else if (ratio < 1.1) {
            return "Average - Your footprint is about the national average";
        } else if (ratio < 1.3) {
            return "Above Average - Your footprint is somewhat higher than average";
        } else if (ratio < 1.5) {
            return "High - Your footprint is significantly above average";
        } else {
            return "Very High - Your footprint is more than 50% above the national average";
        }
    }

    /**
     * Calculates how much CO2 the user would need to offset.
     *
     * @return Amount of CO2 in tons to offset to reach carbon neutrality
     */
    public double getOffsetNeeded() {
        return getTotalEmissions();
    }

    /**
     * Estimates the cost to offset carbon emissions.
     * Average cost is about $10-15 per ton of CO2.
     *
     * @return Estimated cost in USD to offset carbon footprint
     */
    public double getEstimatedOffsetCost() {
        // Using $12.50 as average cost per ton
        return getOffsetNeeded() * 12.50;
    }

    /**
     * Calculates percentage reduction needed to reach sustainable level.
     * Sustainable level is considered 2 tons CO2 per year.
     *
     * @return Percentage reduction needed or 0 if already sustainable
     */
    public double getReductionNeededPercent() {
        double currentEmissions = getTotalEmissions();
        double sustainableLevel = 2.0; // 2 tons per year is considered sustainable

        if (currentEmissions <= sustainableLevel) {
            return 0.0;
        }

        return ((currentEmissions - sustainableLevel) / currentEmissions) * 100.0;
    }
}