import java.util.HashMap;
import java.util.Map;

public class CarbonFootprintCalculator {

    // Calculate general footprint based on country
    public static double calculateGeneralFootprint(String country) {
        switch (country) {
            case "USA": return 15.0;  // Average carbon footprint in tons per person per year
            case "India": return 2.0;  // Average for India
            case "China": return 7.0;  // Example for China
            default: return 7.0;  // Default for other countries
        }
    }

    // Calculate transportation footprint
    public static double calculateTransportationFootprint(int carMilesPerWeek, String vehicleType, String fuelType, int flightsPerYear) {
        double carFootprint = carMilesPerWeek * 0.25;  // kg CO2 per mile; 0.25 is an average estimate
        double vehicleFootprint = 0;

        if (vehicleType.equals("SUV")) {
            vehicleFootprint = 3.5;
        } else if (vehicleType.equals("Sedan")) {
            vehicleFootprint = 2.5;
        }

        double fuelFootprint = 0;
        if (fuelType.equals("Diesel")) {
            fuelFootprint = 2.7;  // Diesel vehicles emit more CO2
        } else if (fuelType.equals("Electric")) {
            fuelFootprint = 0.15;  // Lower for electric vehicles, depends on grid source
        }

        double flightFootprint = flightsPerYear * 0.5;  // Average for a typical short-distance flight (tons of CO2)

        return carFootprint + vehicleFootprint + fuelFootprint + flightFootprint;
    }

    // Calculate food consumption footprint
    public static double calculateFoodFootprint(String dietType, int meatConsumption) {
        double dietFootprint = 0;

        switch (dietType) {
            case "Vegan":
                dietFootprint = 1.5;  // Lower footprint for plant-based diet
                break;
            case "Vegetarian":
                dietFootprint = 2.5;
                break;
            case "Omnivore":
                dietFootprint = 4.0;  // Higher footprint for meat consumption
                break;
            default:
                dietFootprint = 3.0;  // Default
                break;
        }

        dietFootprint += meatConsumption * 0.5;  // Each meat-heavy meal adds additional CO2
        return dietFootprint;
    }

    // Calculate energy footprint
    public static double calculateEnergyFootprint(double electricityUsage, String energySource) {
        double energyFootprint = electricityUsage * 0.5;  // CO2 emissions per kWh (on average)

        if (energySource.equals("Coal")) {
            energyFootprint += 1.2;  // High CO2 footprint for coal-based energy
        } else if (energySource.equals("Solar")) {
            energyFootprint -= 0.5;  // Solar is carbon-neutral (or reduces footprint)
        }

        return energyFootprint;
    }

    // Calculate waste production footprint
    public static double calculateWasteFootprint(double wasteProduced) {
        return wasteProduced * 0.4;  // CO2 emissions for each kilogram of waste (landfills contribute to methane emissions)
    }

    // Calculate total footprint
    public static double calculateTotalFootprint(String country, int carMilesPerWeek, String vehicleType, String fuelType, int flightsPerYear, String dietType, int meatConsumption, double electricityUsage, String energySource, double wasteProduced) {
        double general = calculateGeneralFootprint(country);
        double transportation = calculateTransportationFootprint(carMilesPerWeek, vehicleType, fuelType, flightsPerYear);
        double food = calculateFoodFootprint(dietType, meatConsumption);
        double energy = calculateEnergyFootprint(electricityUsage, energySource);
        double waste = calculateWasteFootprint(wasteProduced);

        return general + transportation + food + energy + waste;
    }
}

