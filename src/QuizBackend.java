import java.util.*;
import java.util.List;

public class QuizBackend {
    private List<Map<String, Object>> allQuestions;
    private Queue<Map<String, Object>> quizQuestions;
    private int score;
    private Map<String, int[]> categoryPerformance;
    private static final int QUESTIONS_PER_QUIZ = 20;

    public QuizBackend() {
        allQuestions = new ArrayList<>();
        quizQuestions = new LinkedList<>();
        categoryPerformance = new HashMap<>();
        score = 0;
        loadAllQuestions();
        selectRandomQuestions();
    }

    private void loadAllQuestions() {
        allQuestions.add(createQuestion(
                "What is the primary cause of global warming?",
                Arrays.asList("Greenhouse gases", "Solar flares", "Volcanic activity", "Deforestation"),
                "Greenhouse gases",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is a renewable energy source?",
                Arrays.asList("Solar power", "Coal", "Natural gas", "Nuclear power"),
                "Solar power",
                "Energy"
        ));

        allQuestions.add(createQuestion(
                "Which gas is the most abundant greenhouse gas in Earth's atmosphere?",
                Arrays.asList("Water vapor", "Carbon dioxide", "Methane", "Nitrous oxide"),
                "Water vapor",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is the Kyoto Protocol?",
                Arrays.asList("An international treaty to reduce greenhouse gas emissions", "A method to measure carbon footprint", "A type of carbon capture technology", "A climate model system"),
                "An international treaty to reduce greenhouse gas emissions",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "Which of the following contributes most to ocean acidification?",
                Arrays.asList("Carbon dioxide absorption", "Plastic pollution", "Oil spills", "Agricultural runoff"),
                "Carbon dioxide absorption",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is the Paris Agreement primarily focused on?",
                Arrays.asList("Limiting global warming to well below 2°C", "Eliminating single-use plastics", "Protecting endangered marine species", "Promoting nuclear energy"),
                "Limiting global warming to well below 2°C",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is NOT a fossil fuel?",
                Arrays.asList("Biomass", "Coal", "Natural gas", "Petroleum"),
                "Biomass",
                "Energy"
        ));

        allQuestions.add(createQuestion(
                "What is the greenhouse effect?",
                Arrays.asList("The trapping of heat in Earth's atmosphere", "The cooling of Earth due to reflective clouds", "The destruction of the ozone layer", "The warming of oceans due to underwater volcanoes"),
                "The trapping of heat in Earth's atmosphere",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which sector is typically the largest emitter of greenhouse gases globally?",
                Arrays.asList("Energy production", "Agriculture", "Transportation", "Manufacturing"),
                "Energy production",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What does IPCC stand for?",
                Arrays.asList("Intergovernmental Panel on Climate Change", "International Protocol on Carbon Capture", "Institute for Planetary Climate Control", "Integrated Plan for Climate Correction"),
                "Intergovernmental Panel on Climate Change",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is a direct effect of global warming?",
                Arrays.asList("Rising sea levels", "Increased volcanic activity", "More frequent solar eclipses", "Stronger gravitational pull"),
                "Rising sea levels",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is carbon sequestration?",
                Arrays.asList("The process of capturing and storing atmospheric carbon dioxide", "The burning of fossil fuels", "The measurement of carbon footprints", "The recycling of carbon-based products"),
                "The process of capturing and storing atmospheric carbon dioxide",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of these animals is considered particularly vulnerable to climate change?",
                Arrays.asList("Polar bears", "Cockroaches", "Pigeons", "Domestic cats"),
                "Polar bears",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What does 'carbon neutral' mean?",
                Arrays.asList("Having a net zero carbon footprint", "Not containing any carbon", "Using only carbon-free energy", "Removing all carbon dioxide from the atmosphere"),
                "Having a net zero carbon footprint",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is considered a tipping point in climate systems?",
                Arrays.asList("Melting of the Greenland ice sheet", "Annual flooding of the Nile River", "Seasonal migration of birds", "Formation of hurricanes"),
                "Melting of the Greenland ice sheet",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is an electric vehicle's primary environmental advantage?",
                Arrays.asList("Zero tailpipe emissions", "No manufacturing carbon footprint", "Quieter operation", "Less water consumption"),
                "Zero tailpipe emissions",
                "Energy"
        ));

        allQuestions.add(createQuestion(
                "Which of the following best defines 'climate'?",
                Arrays.asList("Long-term weather patterns in a specific area", "Daily weather conditions", "Current temperature and precipitation", "Seasonal changes in a region"),
                "Long-term weather patterns in a specific area",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is a carbon tax?",
                Arrays.asList("A fee imposed on the burning of carbon-based fuels", "A fine for exceeding carbon emissions limits", "A tax credit for carbon-neutral companies", "A tariff on imported goods with high carbon footprints"),
                "A fee imposed on the burning of carbon-based fuels",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is a consequence of melting permafrost?",
                Arrays.asList("Release of stored methane", "Increased biodiversity", "More stable coastlines", "Cooler ocean temperatures"),
                "Release of stored methane",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is the primary function of the ozone layer?",
                Arrays.asList("To absorb UV radiation", "To trap heat in the atmosphere", "To produce oxygen", "To prevent acid rain"),
                "To absorb UV radiation",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which country was the world's largest carbon dioxide emitter as of 2024?",
                Arrays.asList("China", "United States", "India", "Russia"),
                "China",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is greenwashing?",
                Arrays.asList("Falsely representing products or policies as environmentally friendly", "Planting trees to offset carbon emissions", "Cleaning up polluted waterways", "Using green energy sources"),
                "Falsely representing products or policies as environmentally friendly",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is an example of climate adaptation?",
                Arrays.asList("Building sea walls to prevent flooding", "Reducing greenhouse gas emissions", "Switching to renewable energy", "Decreasing meat consumption"),
                "Building sea walls to prevent flooding",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "What is a carbon sink?",
                Arrays.asList("A natural environment that absorbs more carbon than it releases", "A man-made container for storing carbon", "A measurement of a product's carbon footprint", "A device that removes carbon from the atmosphere"),
                "A natural environment that absorbs more carbon than it releases",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of the following best describes the albedo effect?",
                Arrays.asList("The reflection of solar radiation by Earth's surfaces", "The absorption of heat by greenhouse gases", "The circulation of ocean currents", "The distribution of rainfall patterns"),
                "The reflection of solar radiation by Earth's surfaces",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is desertification?",
                Arrays.asList("The process of fertile land becoming desert", "The creation of artificial desert ecosystems", "The conversion of deserts to farmland", "The study of desert climates"),
                "The process of fertile land becoming desert",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which global event significantly reduced carbon emissions in 2020?",
                Arrays.asList("COVID-19 pandemic", "Paris Agreement implementation", "Renewable energy revolution", "International carbon tax"),
                "COVID-19 pandemic",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is the primary source of methane emissions from agriculture?",
                Arrays.asList("Livestock digestion", "Fertilizer application", "Farm equipment", "Crop burning"),
                "Livestock digestion",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which renewable energy source depends on the water cycle?",
                Arrays.asList("Hydroelectric power", "Geothermal energy", "Wind power", "Solar power"),
                "Hydroelectric power",
                "Energy"
        ));

        allQuestions.add(createQuestion(
                "What is the primary purpose of a cap and trade system?",
                Arrays.asList("To limit and reduce greenhouse gas emissions", "To regulate international oil prices", "To control population growth in urban areas", "To distribute agricultural subsidies"),
                "To limit and reduce greenhouse gas emissions",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is NOT a potential effect of climate change on human health?",
                Arrays.asList("Decreased incidence of respiratory diseases", "Increased heat-related illnesses", "Expanded range of vector-borne diseases", "More frequent injuries from extreme weather events"),
                "Decreased incidence of respiratory diseases",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What does the term 'net-zero emissions' mean?",
                Arrays.asList("Balancing the amount of greenhouse gases produced and removed from the atmosphere", "Producing zero emissions whatsoever", "Offsetting all historical emissions", "Having a negative carbon footprint"),
                "Balancing the amount of greenhouse gases produced and removed from the atmosphere",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which technological approach attempts to artificially cool the Earth?",
                Arrays.asList("Solar radiation management", "Carbon capture and storage", "Renewable energy deployment", "Forest conservation"),
                "Solar radiation management",
                "Technology"
        ));

        allQuestions.add(createQuestion(
                "What is eutrophication?",
                Arrays.asList("Excessive nutrient enrichment of water bodies", "The process of soil formation", "The extinction of plant species", "The warming of ocean currents"),
                "Excessive nutrient enrichment of water bodies",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which phenomenon can cause widespread coral bleaching?",
                Arrays.asList("Ocean warming", "Increased phytoplankton", "Reduced salinity", "Higher dissolved oxygen"),
                "Ocean warming",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is the primary function of a wind turbine?",
                Arrays.asList("To convert wind energy into electricity", "To reduce local air temperatures", "To disperse air pollution", "To prevent soil erosion"),
                "To convert wind energy into electricity",
                "Energy"
        ));

        allQuestions.add(createQuestion(
                "Which international treaty aimed to phase out substances that deplete the ozone layer?",
                Arrays.asList("Montreal Protocol", "Kyoto Protocol", "Paris Agreement", "Rio Declaration"),
                "Montreal Protocol",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "What is an urban heat island?",
                Arrays.asList("A metropolitan area significantly warmer than surrounding rural areas", "A designated cooling center during heat waves", "A tropical island near urban areas", "A heat-resistant infrastructure design"),
                "A metropolitan area significantly warmer than surrounding rural areas",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of the following describes climate mitigation?",
                Arrays.asList("Actions to reduce greenhouse gas emissions", "Preparations for climate change impacts", "The study of past climate conditions", "The natural cycling of carbon"),
                "Actions to reduce greenhouse gas emissions",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "What percentage of Earth's surface is covered by oceans?",
                Arrays.asList("About 70%", "About 50%", "About 30%", "About 90%"),
                "About 70%",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is a common measurement unit for a carbon footprint?",
                Arrays.asList("Metric tons of CO₂ equivalent", "Kilowatts per hour", "Parts per million", "British thermal units"),
                "Metric tons of CO₂ equivalent",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is the El Niño-Southern Oscillation (ENSO)?",
                Arrays.asList("A climate pattern involving changes in ocean temperature", "A type of hurricane formation", "A measurement of arctic ice thickness", "A greenhouse gas monitoring system"),
                "A climate pattern involving changes in ocean temperature",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which sector is responsible for the most food waste globally?",
                Arrays.asList("Households", "Restaurants", "Farms", "Grocery stores"),
                "Households",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is the primary purpose of the Green Climate Fund?",
                Arrays.asList("To help developing countries respond to climate change", "To invest in green technology startups", "To purchase and protect rainforests", "To fund climate research"),
                "To help developing countries respond to climate change",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "Which of the following accurately describes a circular economy?",
                Arrays.asList("An economic system aimed at eliminating waste and continual use of resources", "An economy based entirely on renewable energy", "A financial system with centralized control", "An economic model focused on maximizing consumption"),
                "An economic system aimed at eliminating waste and continual use of resources",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "What does COP stand for in the context of climate change?",
                Arrays.asList("Conference of the Parties", "Carbon Offset Program", "Climate Oversight Panel", "Committee On Pollution"),
                "Conference of the Parties",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "Which gas has the highest global warming potential?",
                Arrays.asList("Sulfur hexafluoride", "Carbon dioxide", "Methane", "Nitrous oxide"),
                "Sulfur hexafluoride",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is the primary cause of ocean dead zones?",
                Arrays.asList("Nutrient pollution leading to oxygen depletion", "Oil spills", "Plastic pollution", "Ocean warming"),
                "Nutrient pollution leading to oxygen depletion",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is an example of a negative feedback loop in climate systems?",
                Arrays.asList("Increased cloud formation reflecting more sunlight", "Melting ice reducing surface reflectivity", "Warming leading to more water vapor in the atmosphere", "Forest fires releasing more carbon dioxide"),
                "Increased cloud formation reflecting more sunlight",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is meant by the term 'climate justice'?",
                Arrays.asList("Addressing climate change while considering ethical and political issues", "Legal prosecution of major carbon emitters", "Equal distribution of carbon emission allowances", "Compensation for historical emissions"),
                "Addressing climate change while considering ethical and political issues",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "Which tree-planting strategy is most effective for carbon sequestration?",
                Arrays.asList("Planting diverse native species", "Creating monoculture plantations", "Focusing only on fast-growing species", "Planting only in urban areas"),
                "Planting diverse native species",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is a vertical farm?",
                Arrays.asList("An agricultural technique using stacked layers in controlled environments", "A farm on a mountainside", "A skyscraper with gardens on multiple floors", "A deep underground growing facility"),
                "An agricultural technique using stacked layers in controlled environments",
                "Technology"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is the biggest threat to coral reefs?",
                Arrays.asList("Ocean acidification and warming", "Overfishing", "Plastic pollution", "Oil spills"),
                "Ocean acidification and warming",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is energy efficiency?",
                Arrays.asList("Using less energy to perform the same task", "Producing energy at lower cost", "Converting between different forms of energy", "Storing energy for later use"),
                "Using less energy to perform the same task",
                "Energy"
        ));

        allQuestions.add(createQuestion(
                "What is the primary goal of the 'Trillion Trees' initiative?",
                Arrays.asList("To combat climate change through reforestation", "To create sustainable timber sources", "To prevent soil erosion", "To increase biodiversity"),
                "To combat climate change through reforestation",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is NOT generally considered a clean energy source?",
                Arrays.asList("Natural gas", "Solar", "Wind", "Hydroelectric"),
                "Natural gas",
                "Energy"
        ));

        allQuestions.add(createQuestion(
                "What is a food mile?",
                Arrays.asList("The distance food travels from production to consumer", "A measurement of a food's carbon footprint", "The shelf life of produce", "A unit of agricultural efficiency"),
                "The distance food travels from production to consumer",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is a primary driver of deforestation in tropical regions?",
                Arrays.asList("Agricultural expansion", "Urban development", "Mining operations", "Tourism"),
                "Agricultural expansion",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What does the term 'blue carbon' refer to?",
                Arrays.asList("Carbon captured by coastal and marine ecosystems", "Carbon stored in deep ocean trenches", "The color-coding system for carbon offsets", "Carbon dioxide dissolved in ocean water"),
                "Carbon captured by coastal and marine ecosystems",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is NOT typically considered a form of geoengineering?",
                Arrays.asList("Energy efficiency improvements", "Ocean iron fertilization", "Stratospheric aerosol injection", "Space-based sun shields"),
                "Energy efficiency improvements",
                "Technology"
        ));

        allQuestions.add(createQuestion(
                "What is a heat pump?",
                Arrays.asList("A device that transfers heat from a cool space to a warm space", "A solar-powered water heater", "A type of geothermal power plant", "A device that generates heat through compression"),
                "A device that transfers heat from a cool space to a warm space",
                "Technology"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is a consequence of ocean acidification?",
                Arrays.asList("Difficulty for shellfish to form shells", "Increased fish reproduction", "More vibrant coral reefs", "Higher ocean salinity"),
                "Difficulty for shellfish to form shells",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is the primary function of a smart grid?",
                Arrays.asList("To integrate renewable energy and optimize energy efficiency", "To provide free electricity to low-income areas", "To detect power outages automatically", "To generate electricity from multiple sources"),
                "To integrate renewable energy and optimize energy efficiency",
                "Technology"
        ));

        allQuestions.add(createQuestion(
                "Which of the following best describes carbon offsetting?",
                Arrays.asList("Compensating for emissions by funding projects that reduce emissions elsewhere", "Directly removing carbon from the atmosphere", "Planting trees on industrial properties", "Reducing personal carbon emissions"),
                "Compensating for emissions by funding projects that reduce emissions elsewhere",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is environmental racism?",
                Arrays.asList("Policies that disproportionately affect minority communities with environmental hazards", "Opposition to environmental protection based on racial prejudice", "Discrimination in hiring for environmental jobs", "Exclusion of minorities from natural recreation areas"),
                "Policies that disproportionately affect minority communities with environmental hazards",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is a common criticism of biofuels?",
                Arrays.asList("They can compete with food production", "They produce more greenhouse gases than fossil fuels", "They require more water than conventional agriculture", "They cannot be stored for long periods"),
                "They can compete with food production",
                "Energy"
        ));

        allQuestions.add(createQuestion(
                "What is meant by the phrase 'just transition'?",
                Arrays.asList("A fair transition to a low-carbon economy that supports workers and communities", "The legal process of prosecuting polluters", "The equitable distribution of climate adaptation funding", "A gradual implementation of environmental regulations"),
                "A fair transition to a low-carbon economy that supports workers and communities",
                "Policy"
        ));

        allQuestions.add(createQuestion(
                "Which renewable energy source relies on Earth's internal heat?",
                Arrays.asList("Geothermal energy", "Solar power", "Wind power", "Hydroelectric power"),
                "Geothermal energy",
                "Energy"
        ));

        allQuestions.add(createQuestion(
                "What is the primary source of indoor air pollution in developing countries?",
                Arrays.asList("Cooking with solid fuels", "Building materials", "Consumer products", "Electronic devices"),
                "Cooking with solid fuels",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of the following statements about electric vehicles is accurate?",
                Arrays.asList("Their overall environmental impact depends on the source of electricity", "They have zero environmental impact", "They always produce more emissions in manufacturing than they save", "They require more maintenance than conventional vehicles"),
                "Their overall environmental impact depends on the source of electricity",
                "Energy"
        ));

        allQuestions.add(createQuestion(
                "What is meant by the term 'carbon budget'?",
                Arrays.asList("The cumulative amount of carbon emissions allowed to limit warming to a specific level", "The amount a company is willing to spend on carbon offsets", "A national allocation of carbon permits", "A household's monthly carbon footprint"),
                "The cumulative amount of carbon emissions allowed to limit warming to a specific level",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of the following is a primary benefit of agroforestry?",
                Arrays.asList("Carbon sequestration while maintaining food production", "Elimination of the need for pesticides", "Maximizing crop yields in the short term", "Reducing the need for irrigation"),
                "Carbon sequestration while maintaining food production",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "What is the primary cause of increased atmospheric methane levels in recent decades?",
                Arrays.asList("Human activities including agriculture and fossil fuel extraction", "Natural wetland emissions", "Volcanic eruptions", "Oceanic methane hydrates"),
                "Human activities including agriculture and fossil fuel extraction",
                "Environment"
        ));

        allQuestions.add(createQuestion(
                "Which of the following best describes a carbon-intensive industry?",
                Arrays.asList("An industry that emits large amounts of greenhouse gases", "An industry that manufactures carbon fiber products", "An industry that focuses on carbon capture technology", "An industry that produces carbon-based fuels"),
                "An industry that emits large amounts of greenhouse gases",
                "Environment"
        ));
    }

    private Map<String, Object> createQuestion(String questionText, List<String> options, String correctAnswer, String category) {
        Map<String, Object> question = new HashMap<>();
        question.put("questionText", questionText);
        question.put("options", options);
        question.put("correctAnswer", correctAnswer);
        question.put("category", category);
        return question;
    }

    // Randomly selects questions for the quiz
    private void selectRandomQuestions() {
        List<Map<String, Object>> questionPool = new ArrayList<>(allQuestions);
        Collections.shuffle(questionPool); // Shuffle for randomness
        quizQuestions.clear();

        // Add the first `QUESTIONS_PER_QUIZ` questions to the quiz
        for (int i = 0; i < QUESTIONS_PER_QUIZ && i < questionPool.size(); i++) {
            quizQuestions.add(questionPool.get(i));
        }
    }

    // Returns the current question
    public Map<String, Object> getCurrentQuestion() {
        return quizQuestions.peek();
    }

    public boolean checkAnswer(String answer) {
        Map<String, Object> currentQuestion = quizQuestions.poll();
        String correctAnswer = (String) currentQuestion.get("correctAnswer");
        String category = (String) currentQuestion.get("category");

        boolean isCorrect = correctAnswer.equals(answer);
        if (isCorrect) {
            score++;
        }

        // Make sure to update category performance
        updateCategoryPerformance(category, isCorrect);

        return isCorrect;
    }

    // Updates performance metrics for a specific category
    private void updateCategoryPerformance(String category, boolean isCorrect) {
        int[] performance = categoryPerformance.getOrDefault(category, new int[2]); // [correct, total]
        if (isCorrect) {
            performance[0]++;
        }
        performance[1]++;
        categoryPerformance.put(category, performance);
    }

    public boolean hasMoreQuestions() {
        return !quizQuestions.isEmpty();
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return QUESTIONS_PER_QUIZ;
    }

    public int getCurrentQuestionNumber() {
        return QUESTIONS_PER_QUIZ - quizQuestions.size();
    }

    public String getCurrentCategory() {
        return quizQuestions.isEmpty() ? "" : (String) quizQuestions.peek().get("category");
    }

    public Map<String, Double> getCategoryPerformance() {
        Map<String, Double> performance = new HashMap<>();
        for (Map.Entry<String, int[]> entry : categoryPerformance.entrySet()) {
            String category = entry.getKey();
            int correct = entry.getValue()[0];
            int total = entry.getValue()[1];
            if (total > 0) {
                performance.put(category, (double) correct / total);
            }
        }
        return performance;
    }
}
