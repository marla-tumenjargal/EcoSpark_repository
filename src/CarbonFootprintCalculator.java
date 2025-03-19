/**
 * Implements the back end and mathematics behind the front-end
 * components within the main "createCarbonFootprintPanel()" method.
 */
class CarbonFootprintCalculator {

    /**
     * Uses the average carbon footprint (by country) to start the calculation.
     * On average, countries with a frontier consume more resources and therefore produce more CO2
     * @param country, the country the user lives in
     * @return average footprint of a person living in the chosen country
     */
    public double calculateGeneralFootprint(String country) {
        switch (country) {
            case "USA": return 15.0;
            case "Canada": return 14.2;
            case "UK": return 9.8;
            case "China": return 7.5;
            case "India": return 1.9;
            case "Brazil": return 2.3;
            case "Australia": return 16.8;
            case "Other": return 5.0;
            default: return 2.0;
        }
    }

    /**
     * Calculates the carbon footprint associated with transportation such as by car and plane.
     * @param carMilesPerWeek, number of miles driven in a week
     * @param vehicleType, type of vehicle driven
     * @param fuelType, type of fuel vehicle uses
     * @param flightsPerYear, number of flights taken per year
     * @return total C02 emissions from car and plane
     */
    public double calculateTransportationFootprint(int carMilesPerWeek, String vehicleType, String fuelType, int flightsPerYear) {
        double carFootprint = calculateCarFootprint(carMilesPerWeek, vehicleType, fuelType);
        double flightFootprint = calculateFlightFootprint(flightsPerYear);
        return carFootprint + flightFootprint;
    }

    /**
     * Calculates the car footprint in more detail depending on the car's model.
     * Since SUVs are less fuel efficient than a hybrid, for example, the calculator must
     * take into account the model of car driven.
     * @param carMilesPerWeek, number of miles driven by the car in a week
     * @param vehicleType, type of vehicle
     * @param fuelType, type of fuel
     * @return totalEmissions / 1000
     */
    private double calculateCarFootprint(int carMilesPerWeek, String vehicleType, String fuelType) {
        double yearlyMiles = carMilesPerWeek * 52;
        double emissionsFactor = 0;
        switch (vehicleType) {
            case "SUV": emissionsFactor = 0.44; break;
            case "Sedan": emissionsFactor = 0.35; break;
            case "Truck": emissionsFactor = 0.55; break;
            case "Compact Car": emissionsFactor = 0.30; break;
            case "Hybrid": emissionsFactor = 0.22; break;
            case "Electric Vehicle": emissionsFactor = 0.10; break;
            case "None": return 0.0;
            default: emissionsFactor = 0.35; break;
        }

        switch (fuelType) {
            case "Gas": break;
            case "Diesel": emissionsFactor *= 1.15; break;
            case "Electric": emissionsFactor = 0.10; break;
            case "Hybrid": emissionsFactor *= 0.75; break;
            default: break;
        }

        double totalEmissions = yearlyMiles * emissionsFactor;
        return totalEmissions / 1000;
    }

    /**
     * Calculates flight carbon footprint by multiplying total flights taken by 0.5
     * @param flightsPerYear, number of flights taken per year
     * @return carbon footprint via airplanes
     */
    private double calculateFlightFootprint(int flightsPerYear) {
        double emissionsPerFlight = 0.5;
        return flightsPerYear * emissionsPerFlight;
    }

    /**
     * Calculates food carbon footprint by returning the average
     * carbon footprint of a person with a vegan, vegetarian, pescatarian, omnivore,
     * or keto diet. The default diet is none with a average footprint of 3.
     * @param dietType, type of diet
     * @param meatConsumption, how much meat consumed (meat is the main contributing factor to the footprint here)
     * @return base value (food)
     */
    public double calculateFoodFootprint(String dietType, int meatConsumption) {
        double baseFoodprint = 0;
        switch (dietType) {
            case "Vegan": baseFoodprint = 1.5; break;
            case "Vegetarian": baseFoodprint = 2.1; break;
            case "Pescatarian": baseFoodprint = 2.5; break;
            case "Omnivore": baseFoodprint = 3.3; break;
            case "Keto": baseFoodprint = 3.6; break;
            case "Other": baseFoodprint = 3.0; break;
            default: baseFoodprint = 3.0; break;
        }

        if (!dietType.equals("Vegan") && !dietType.equals("Vegetarian")) {
            if (meatConsumption <= 3) {
                baseFoodprint *= 0.8; // Reduced meat consumption
            } else if (meatConsumption >= 14) {
                baseFoodprint *= 1.4; // High meat consumption
            } else if (meatConsumption >= 7) {
                baseFoodprint *= 1.2; // Moderate-high meat consumption
            }
        }
        return baseFoodprint;
    }

    /**
     * Calculates electricty carbon footprint (such as within the home, AC, lights, etc)
     * @param electricityUsage, amount of electricity used
     * @param energySource, electricity type
     * @return totalEmissions / 1000
     */
    public double calculateEnergyFootprint(double electricityUsage, String energySource) {
        double yearlyUsage = electricityUsage * 12;

        double emissionFactor = 0;
        switch (energySource) {
            case "Coal": emissionFactor = 0.9; break;
            case "Natural Gas": emissionFactor = 0.4; break;
            case "Nuclear": emissionFactor = 0.02; break;
            case "Solar": emissionFactor = 0.05; break;
            case "Wind": emissionFactor = 0.02; break;
            case "Hydroelectric": emissionFactor = 0.03; break;
            case "Other": emissionFactor = 0.5; break;
            default: emissionFactor = 0.5; break;
        }
        double totalEmissions = yearlyUsage * emissionFactor;
        return totalEmissions / 1000;
    }

    /**
     * Calculates footprint based on waste produced (in the home mainly)
     * @param wasteProduced, amount of waste produced
     * @return totalEmissions / 1000
     */
    public double calculateWasteFootprint(double wasteProduced) {
        double yearlyWaste = wasteProduced * 52;
        double emissionFactor = 0.5;
        double totalEmissions = yearlyWaste * emissionFactor;
        return totalEmissions / 1000;
    }

    /**
     * this is the overall calculator that adds up all the previous numbers to calculate
     * the total emissions number
     * @param country, country user lives in
     * @param carMilesPerWeek, miles driven by user in a week
     * @param vehicleType, type of vehicle
     * @param fuelType, type of fuel
     * @param flightsPerYear, number of flights taken per year
     * @param dietType, takes into account the types of food (plants, meats, etc) regularly consumed by user
     * @param meatConsumption, amount of meat consumed
     * @param electricityUsage, amount of electricity used
     * @param energySource, main energy source (in home, at work, etc)
     * @param wasteProduced, amount of waste produced
     * @return total footprint!!
     */
    public double calculateTotalFootprint(String country, int carMilesPerWeek, String vehicleType, String fuelType, int flightsPerYear, String dietType, int meatConsumption, double electricityUsage, String energySource, double wasteProduced) {
        double generalFootprint = calculateGeneralFootprint(country);
        double transportationFootprint = calculateTransportationFootprint(carMilesPerWeek, vehicleType, fuelType, flightsPerYear);
        double foodFootprint = calculateFoodFootprint(dietType, meatConsumption);
        double energyFootprint = calculateEnergyFootprint(electricityUsage, energySource);
        double wasteFootprint = calculateWasteFootprint(wasteProduced);
        return (generalFootprint * 0.3) + transportationFootprint + foodFootprint + energyFootprint + wasteFootprint;
    }
}
