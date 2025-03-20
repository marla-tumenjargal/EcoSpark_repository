import java.util.*;
import java.util.List;

public class QuizBackend {
    private List<String[]> allQuestions;
    private int currentQuestionIndex;
    private List<String[]> selectedQuestions;
    private int score;
    private static final int QUESTIONS_PER_QUIZ = 20;

    public QuizBackend() {
        allQuestions = new ArrayList<>();
        selectedQuestions = new ArrayList<>();

        loadAllQuestions();

        selectRandomQuestions();
        currentQuestionIndex = 0;
        score = 0;
    }

    private void loadAllQuestions() {

        // Climate-related multiple-choice questions
        allQuestions.add(new String[]{"What is the primary cause of global warming?", "Greenhouse gases", "Solar flares", "Volcanic activity", "Deforestation"});
        allQuestions.add(new String[]{"Which of the following is a renewable energy source?", "Solar power", "Coal", "Natural gas", "Nuclear power"});
        allQuestions.add(new String[]{"Which gas is the most abundant greenhouse gas in Earth's atmosphere?", "Water vapor", "Carbon dioxide", "Methane", "Nitrous oxide"});
        allQuestions.add(new String[]{"What is the Kyoto Protocol?", "An international treaty to reduce greenhouse gas emissions", "A method to measure carbon footprint", "A type of carbon capture technology", "A climate model system"});
        allQuestions.add(new String[]{"Which of the following contributes most to ocean acidification?", "Carbon dioxide absorption", "Plastic pollution", "Oil spills", "Agricultural runoff"});
        allQuestions.add(new String[]{"What is the Paris Agreement primarily focused on?", "Limiting global warming to well below 2°C", "Eliminating single-use plastics", "Protecting endangered marine species", "Promoting nuclear energy"});
        allQuestions.add(new String[]{"Which of the following is NOT a fossil fuel?", "Biomass", "Coal", "Natural gas", "Petroleum"});
        allQuestions.add(new String[]{"What is the greenhouse effect?", "The trapping of heat in Earth's atmosphere", "The cooling of Earth due to reflective clouds", "The destruction of the ozone layer", "The warming of oceans due to underwater volcanoes"});
        allQuestions.add(new String[]{"Which sector is typically the largest emitter of greenhouse gases globally?", "Energy production", "Agriculture", "Transportation", "Manufacturing"});
        allQuestions.add(new String[]{"What does IPCC stand for?", "Intergovernmental Panel on Climate Change", "International Protocol on Carbon Capture", "Institute for Planetary Climate Control", "Integrated Plan for Climate Correction"});
        allQuestions.add(new String[]{"Which of the following is a direct effect of global warming?", "Rising sea levels", "Increased volcanic activity", "More frequent solar eclipses", "Stronger gravitational pull"});
        allQuestions.add(new String[]{"What is carbon sequestration?", "The process of capturing and storing atmospheric carbon dioxide", "The burning of fossil fuels", "The measurement of carbon footprints", "The recycling of carbon-based products"});
        allQuestions.add(new String[]{"Which of these animals is considered particularly vulnerable to climate change?", "Polar bears", "Cockroaches", "Pigeons", "Domestic cats"});
        allQuestions.add(new String[]{"What does 'carbon neutral' mean?", "Having a net zero carbon footprint", "Not containing any carbon", "Using only carbon-free energy", "Removing all carbon dioxide from the atmosphere"});
        allQuestions.add(new String[]{"Which of the following is considered a tipping point in climate systems?", "Melting of the Greenland ice sheet", "Annual flooding of the Nile River", "Seasonal migration of birds", "Formation of hurricanes"});
        allQuestions.add(new String[]{"What is an electric vehicle's primary environmental advantage?", "Zero tailpipe emissions", "No manufacturing carbon footprint", "Quieter operation", "Less water consumption"});
        allQuestions.add(new String[]{"Which of the following best defines 'climate'?", "Long-term weather patterns in a specific area", "Daily weather conditions", "Current temperature and precipitation", "Seasonal changes in a region"});
        allQuestions.add(new String[]{"What is a carbon tax?", "A fee imposed on the burning of carbon-based fuels", "A fine for exceeding carbon emissions limits", "A tax credit for carbon-neutral companies", "A tariff on imported goods with high carbon footprints"});
        allQuestions.add(new String[]{"Which of the following is a consequence of melting permafrost?", "Release of stored methane", "Increased biodiversity", "More stable coastlines", "Cooler ocean temperatures"});
        allQuestions.add(new String[]{"What is the primary function of the ozone layer?", "To absorb UV radiation", "To trap heat in the atmosphere", "To produce oxygen", "To prevent acid rain"});
        allQuestions.add(new String[]{"Which country was the world's largest carbon dioxide emitter as of 2024?", "China", "United States", "India", "Russia"});
        allQuestions.add(new String[]{"What is greenwashing?", "Falsely representing products or policies as environmentally friendly", "Planting trees to offset carbon emissions", "Cleaning up polluted waterways", "Using green energy sources"});
        allQuestions.add(new String[]{"Which of the following is an example of climate adaptation?", "Building sea walls to prevent flooding", "Reducing greenhouse gas emissions", "Switching to renewable energy", "Decreasing meat consumption"});
        allQuestions.add(new String[]{"What is a carbon sink?", "A natural environment that absorbs more carbon than it releases", "A man-made container for storing carbon", "A measurement of a product's carbon footprint", "A device that removes carbon from the atmosphere"});
        allQuestions.add(new String[]{"Which of the following best describes the albedo effect?", "The reflection of solar radiation by Earth's surfaces", "The absorption of heat by greenhouse gases", "The circulation of ocean currents", "The distribution of rainfall patterns"});
        allQuestions.add(new String[]{"What is desertification?", "The process of fertile land becoming desert", "The creation of artificial desert ecosystems", "The conversion of deserts to farmland", "The study of desert climates"});
        allQuestions.add(new String[]{"Which global event significantly reduced carbon emissions in 2020?", "COVID-19 pandemic", "Paris Agreement implementation", "Renewable energy revolution", "International carbon tax"});
        allQuestions.add(new String[]{"What is the primary source of methane emissions from agriculture?", "Livestock digestion", "Fertilizer application", "Farm equipment", "Crop burning"});
        allQuestions.add(new String[]{"Which renewable energy source depends on the water cycle?", "Hydroelectric power", "Geothermal energy", "Wind power", "Solar power"});
        allQuestions.add(new String[]{"What is the primary purpose of a cap and trade system?", "To limit and reduce greenhouse gas emissions", "To regulate international oil prices", "To control population growth in urban areas", "To distribute agricultural subsidies"});
        allQuestions.add(new String[]{"Which of the following is NOT a potential effect of climate change on human health?", "Decreased incidence of respiratory diseases", "Increased heat-related illnesses", "Expanded range of vector-borne diseases", "More frequent injuries from extreme weather events"});
        allQuestions.add(new String[]{"What does the term 'net-zero emissions' mean?", "Balancing the amount of greenhouse gases produced and removed from the atmosphere", "Producing zero emissions whatsoever", "Offsetting all historical emissions", "Having a negative carbon footprint"});
        allQuestions.add(new String[]{"Which technological approach attempts to artificially cool the Earth?", "Solar radiation management", "Carbon capture and storage", "Renewable energy deployment", "Forest conservation"});
        allQuestions.add(new String[]{"What is eutrophication?", "Excessive nutrient enrichment of water bodies", "The process of soil formation", "The extinction of plant species", "The warming of ocean currents"});
        allQuestions.add(new String[]{"Which phenomenon can cause widespread coral bleaching?", "Ocean warming", "Increased phytoplankton", "Reduced salinity", "Higher dissolved oxygen"});
        allQuestions.add(new String[]{"What is the primary function of a wind turbine?", "To convert wind energy into electricity", "To reduce local air temperatures", "To disperse air pollution", "To prevent soil erosion"});
        allQuestions.add(new String[]{"Which international treaty aimed to phase out substances that deplete the ozone layer?", "Montreal Protocol", "Kyoto Protocol", "Paris Agreement", "Rio Declaration"});
        allQuestions.add(new String[]{"What is an urban heat island?", "A metropolitan area significantly warmer than surrounding rural areas", "A designated cooling center during heat waves", "A tropical island near urban areas", "A heat-resistant infrastructure design"});
        allQuestions.add(new String[]{"Which of the following describes climate mitigation?", "Actions to reduce greenhouse gas emissions", "Preparations for climate change impacts", "The study of past climate conditions", "The natural cycling of carbon"});
        allQuestions.add(new String[]{"What percentage of Earth's surface is covered by oceans?", "About 70%", "About 50%", "About 30%", "About 90%"});
        allQuestions.add(new String[]{"Which of the following is a common measurement unit for a carbon footprint?", "Metric tons of CO₂ equivalent", "Kilowatts per hour", "Parts per million", "British thermal units"});
        allQuestions.add(new String[]{"What is the El Niño-Southern Oscillation (ENSO)?", "A climate pattern involving changes in ocean temperature", "A type of hurricane formation", "A measurement of arctic ice thickness", "A greenhouse gas monitoring system"});
        allQuestions.add(new String[]{"Which sector is responsible for the most food waste globally?", "Households", "Restaurants", "Farms", "Grocery stores"});
        allQuestions.add(new String[]{"What is the primary purpose of the Green Climate Fund?", "To help developing countries respond to climate change", "To invest in green technology startups", "To purchase and protect rainforests", "To fund climate research"});
        allQuestions.add(new String[]{"Which of the following accurately describes a circular economy?", "An economic system aimed at eliminating waste and continual use of resources", "An economy based entirely on renewable energy", "A financial system with centralized control", "An economic model focused on maximizing consumption"});
        allQuestions.add(new String[]{"What does COP stand for in the context of climate change?", "Conference of the Parties", "Carbon Offset Program", "Climate Oversight Panel", "Committee On Pollution"});
        allQuestions.add(new String[]{"Which gas has the highest global warming potential?", "Sulfur hexafluoride", "Carbon dioxide", "Methane", "Nitrous oxide"});
        allQuestions.add(new String[]{"What is the primary cause of ocean dead zones?", "Nutrient pollution leading to oxygen depletion", "Oil spills", "Plastic pollution", "Ocean warming"});
        allQuestions.add(new String[]{"Which of the following is an example of a negative feedback loop in climate systems?", "Increased cloud formation reflecting more sunlight", "Melting ice reducing surface reflectivity", "Warming leading to more water vapor in the atmosphere", "Forest fires releasing more carbon dioxide"});
        allQuestions.add(new String[]{"What is meant by the term 'climate justice'?", "Addressing climate change while considering ethical and political issues", "Legal prosecution of major carbon emitters", "Equal distribution of carbon emission allowances", "Compensation for historical emissions"});
        allQuestions.add(new String[]{"Which tree-planting strategy is most effective for carbon sequestration?", "Planting diverse native species", "Creating monoculture plantations", "Focusing only on fast-growing species", "Planting only in urban areas"});
        allQuestions.add(new String[]{"What is a vertical farm?", "An agricultural technique using stacked layers in controlled environments", "A farm on a mountainside", "A skyscraper with gardens on multiple floors", "A deep underground growing facility"});
        allQuestions.add(new String[]{"Which of the following is the biggest threat to coral reefs?", "Ocean acidification and warming", "Overfishing", "Plastic pollution", "Oil spills"});
        allQuestions.add(new String[]{"What is energy efficiency?", "Using less energy to perform the same task", "Producing energy at lower cost", "Converting between different forms of energy", "Storing energy for later use"});
        allQuestions.add(new String[]{"What is the primary goal of the 'Trillion Trees' initiative?", "To combat climate change through reforestation", "To create sustainable timber sources", "To prevent soil erosion", "To increase biodiversity"});
        allQuestions.add(new String[]{"Which of the following is NOT generally considered a clean energy source?", "Natural gas", "Solar", "Wind", "Hydroelectric"});
        allQuestions.add(new String[]{"What is a food mile?", "The distance food travels from production to consumer", "A measurement of a food's carbon footprint", "The shelf life of produce", "A unit of agricultural efficiency"});
        allQuestions.add(new String[]{"Which of the following is a primary driver of deforestation in tropical regions?", "Agricultural expansion", "Urban development", "Mining operations", "Tourism"});
        allQuestions.add(new String[]{"What does the term 'blue carbon' refer to?", "Carbon captured by coastal and marine ecosystems", "Carbon stored in deep ocean trenches", "The color-coding system for carbon offsets", "Carbon dioxide dissolved in ocean water"});
        allQuestions.add(new String[]{"Which of the following is NOT typically considered a form of geoengineering?", "Energy efficiency improvements", "Ocean iron fertilization", "Stratospheric aerosol injection", "Space-based sun shields"});
        allQuestions.add(new String[]{"What is a heat pump?", "A device that transfers heat from a cool space to a warm space", "A solar-powered water heater", "A type of geothermal power plant", "A device that generates heat through compression"});
        allQuestions.add(new String[]{"Which of the following is a consequence of ocean acidification?", "Difficulty for shellfish to form shells", "Increased fish reproduction", "More vibrant coral reefs", "Higher ocean salinity"});
        allQuestions.add(new String[]{"What is the primary function of a smart grid?", "To integrate renewable energy and optimize energy efficiency", "To provide free electricity to low-income areas", "To detect power outages automatically", "To generate electricity from multiple sources"});
        allQuestions.add(new String[]{"Which of the following best describes carbon offsetting?", "Compensating for emissions by funding projects that reduce emissions elsewhere", "Directly removing carbon from the atmosphere", "Planting trees on industrial properties", "Reducing personal carbon emissions"});
        allQuestions.add(new String[]{"What is environmental racism?", "Policies that disproportionately affect minority communities with environmental hazards", "Opposition to environmental protection based on racial prejudice", "Discrimination in hiring for environmental jobs", "Exclusion of minorities from natural recreation areas"});
        allQuestions.add(new String[]{"Which of the following is a common criticism of biofuels?", "They can compete with food production", "They produce more greenhouse gases than fossil fuels", "They require more water than conventional agriculture", "They cannot be stored for long periods"});
        allQuestions.add(new String[]{"What is meant by the phrase 'just transition'?", "A fair transition to a low-carbon economy that supports workers and communities", "The legal process of prosecuting polluters", "The equitable distribution of climate adaptation funding", "A gradual implementation of environmental regulations"});
        allQuestions.add(new String[]{"Which renewable energy source relies on Earth's internal heat?", "Geothermal energy", "Solar power", "Wind power", "Hydroelectric power"});
        allQuestions.add(new String[]{"What is the primary source of indoor air pollution in developing countries?", "Cooking with solid fuels", "Building materials", "Consumer products", "Electronic devices"});
        allQuestions.add(new String[]{"Which of the following statements about electric vehicles is accurate?", "Their overall environmental impact depends on the source of electricity", "They have zero environmental impact", "They always produce more emissions in manufacturing than they save", "They require more maintenance than conventional vehicles"});
        allQuestions.add(new String[]{"What is meant by the term 'carbon budget'?", "The cumulative amount of carbon emissions allowed to limit warming to a specific level", "The amount a company is willing to spend on carbon offsets", "A national allocation of carbon permits", "A household's monthly carbon footprint"});
        allQuestions.add(new String[]{"Which of the following is a primary benefit of agroforestry?", "Carbon sequestration while maintaining food production", "Elimination of the need for pesticides", "Maximizing crop yields in the short term", "Reducing the need for irrigation"});
        allQuestions.add(new String[]{"What is the primary cause of increased atmospheric methane levels in recent decades?", "Human activities including agriculture and fossil fuel extraction", "Natural wetland emissions", "Volcanic eruptions", "Oceanic methane hydrates"});
        allQuestions.add(new String[]{"Which of the following best describes a carbon-intensive industry?", "An industry that emits large amounts of greenhouse gases", "An industry that manufactures carbon fiber products", "An industry that focuses on carbon capture technology", "An industry that produces carbon-based fuels"});
    }

    private void selectRandomQuestions() {
        // Create a copy of all questions to randomly select from
        List<String[]> questionPool = new ArrayList<>(allQuestions);
        selectedQuestions.clear();

        // Select QUESTIONS_PER_QUIZ random questions
        Random random = new Random();
        for (int i = 0; i < QUESTIONS_PER_QUIZ && !questionPool.isEmpty(); i++) {
            int randomIndex = random.nextInt(questionPool.size());
            selectedQuestions.add(questionPool.get(randomIndex));
            questionPool.remove(randomIndex);
        }
    }

    public String[] getCurrentQuestion() {
        return selectedQuestions.get(currentQuestionIndex);
    }

    public void checkAnswer(String answer) {
        if (answer.equals(selectedQuestions.get(currentQuestionIndex)[1])) {
            score++;
        }
        currentQuestionIndex++;
    }

    public boolean hasMoreQuestions() {
        return currentQuestionIndex < selectedQuestions.size();
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return selectedQuestions.size();
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionIndex + 1;
    }

    public String getCurrentCategory() {
        return "Environment"; // Example category
    }

    public void resetQuiz() {
        // Select a new set of random questions
        selectRandomQuestions();
        currentQuestionIndex = 0;
        score = 0;
    }

    public Map<String, Double> getCategoryPerformance() {
        // Example performance data
        Map<String, Double> performance = new HashMap<>();
        performance.put("Environment", 0.75);
        performance.put("Energy", 0.85);
        return performance;
    }
}

///**
// * Enhanced QuizBackend class with advanced data structures
// */
//class QuizBackend {
//    // Advanced data structure for questions: TreeMap for categorization and prioritization
//    private Map<String, List<QuizQuestion>> questionsByCategory;
//    // Maintain a LinkedHashSet for tracking viewed questions to prevent immediate repeats
//    private Set<QuizQuestion> recentlyViewedQuestions;
//    // Use a PriorityQueue for adaptive question selection based on difficulty/user performance
//    private PriorityQueue<QuizQuestion> adaptiveQuestionQueue;
//
//    private QuizQuestion currentQuestion;
//    private int score = 0;
//    private int questionCounter = 0;
//    private final int totalQuestions = 15;
//    private Random random = new Random();
//
//    // Add user performance tracking
//    private Map<String, Double> categoryPerformance = new HashMap<>();
//
//    public QuizBackend() {
//        questionsByCategory = new TreeMap<>();
//        recentlyViewedQuestions = new LinkedHashSet<>(30); // Remember last 30 questions
//
//        // Define custom comparator for adaptive question selection
//        Comparator<QuizQuestion> questionComparator = (q1, q2) -> {
//            // First prioritize by category performance (focus on weaker areas)
//            double p1 = categoryPerformance.getOrDefault(q1.getCategory(), 0.5);
//            double p2 = categoryPerformance.getOrDefault(q2.getCategory(), 0.5);
//            if (p1 != p2) {
//                return Double.compare(p1, p2);
//            }
//            // Then by difficulty
//            return Integer.compare(q2.getDifficulty(), q1.getDifficulty());
//        };
//
//        adaptiveQuestionQueue = new PriorityQueue<>(questionComparator);
//
//        // Initialize with all eco-friendly questions
//        initializeQuestions();
//
//        // Prepare first question
//        prepareNextQuestion();
//    }
//
//    private void initializeQuestions() {
//        // Create predefined categories
//        String[] categories = {"Recycling", "Energy", "Water Conservation",
//                "Sustainable Transport", "Wildlife", "Climate Change",
//                "Eco-Living", "Food Sustainability", "Pollution",
//                "Green Technology"};
//
//        // Create at least 100 questions across multiple categories
//        addQuestionsForCategory(categories[0], createRecyclingQuestions());
//        addQuestionsForCategory(categories[1], createEnergyQuestions());
//        addQuestionsForCategory(categories[2], createWaterQuestions());
//        addQuestionsForCategory(categories[3], createTransportQuestions());
//        addQuestionsForCategory(categories[4], createWildlifeQuestions());
//        addQuestionsForCategory(categories[5], createClimateQuestions());
//        addQuestionsForCategory(categories[6], createEcoLivingQuestions());
//        addQuestionsForCategory(categories[7], createFoodQuestions());
//        addQuestionsForCategory(categories[8], createPollutionQuestions());
//        addQuestionsForCategory(categories[9], createTechQuestions());
//
//        // Initialize performance metrics
//        for (String category : categories) {
//            categoryPerformance.put(category, 0.5); // Start at neutral 50%
//        }
//
//        // Initially load all questions into the queue
//        for (List<QuizQuestion> questions : questionsByCategory.values()) {
//            adaptiveQuestionQueue.addAll(questions);
//        }
//    }
//
//    private void addQuestionsForCategory(String category, List<QuizQuestion> questions) {
//        questionsByCategory.put(category, questions);
//    }
//
//    public boolean hasMoreQuestions() {
//        return questionCounter < totalQuestions;
//    }
//
//    public String[] getCurrentQuestion() {
//        String[] questionData = new String[5];
//        questionData[0] = currentQuestion.getQuestion();
//
//        List<String> options = currentQuestion.getOptions();
//        for (int i = 0; i < options.size(); i++) {
//            questionData[i + 1] = options.get(i);
//        }
//
//        return questionData;
//    }
//
//    public void checkAnswer(String selectedAnswer) {
//        boolean correct = currentQuestion.isCorrect(selectedAnswer);
//        if (correct) {
//            score++;
//        }
//
//        // Update category performance
//        String category = currentQuestion.getCategory();
//        double currentPerformance = categoryPerformance.get(category);
//        double newPerformance = correct ?
//                currentPerformance + 0.1 : currentPerformance - 0.05;
//        // Clamp between 0.1 and 0.9
//        newPerformance = Math.max(0.1, Math.min(0.9, newPerformance));
//        categoryPerformance.put(category, newPerformance);
//
//        // Move to next question
//        questionCounter++;
//        if (hasMoreQuestions()) {
//            prepareNextQuestion();
//        }
//    }
//
//    private void prepareNextQuestion() {
//        // Use different selection strategies based on progress
//        if (questionCounter < 5) {
//            // First 5 questions: One from each of 5 random categories
//            selectCategoryBasedQuestion();
//        } else if (questionCounter < 10) {
//            // Next 5 questions: Focus on weaker areas using adaptive queue
//            selectAdaptiveQuestion();
//        } else {
//            // Last 5 questions: Mixed approach with slight randomization
//            if (random.nextBoolean()) {
//                selectCategoryBasedQuestion();
//            } else {
//                selectAdaptiveQuestion();
//            }
//        }
//    }
//
//    private void selectCategoryBasedQuestion() {
//        // Select a random category
//        List<String> categories = new ArrayList<>(questionsByCategory.keySet());
//        String selectedCategory = categories.get(random.nextInt(categories.size()));
//
//        // Get all questions for this category
//        List<QuizQuestion> categoryQuestions = questionsByCategory.get(selectedCategory);
//
//        // Filter out recently viewed questions
//        List<QuizQuestion> availableQuestions = new ArrayList<>();
//        for (QuizQuestion q : categoryQuestions) {
//            if (!recentlyViewedQuestions.contains(q)) {
//                availableQuestions.add(q);
//            }
//        }
//
//        // If all questions were recently viewed, just use the full list
//        if (availableQuestions.isEmpty()) {
//            availableQuestions = categoryQuestions;
//        }
//
//        // Select a random question from filtered list
//        currentQuestion = availableQuestions.get(random.nextInt(availableQuestions.size()));
//
//        // Add to recently viewed and shuffle options
//        updateRecentlyViewed(currentQuestion);
//    }
//
//    private void selectAdaptiveQuestion() {
//        // Get question from priority queue but don't remove it yet
//        List<QuizQuestion> candidates = new ArrayList<>();
//
//        // Take top 5 candidates from the queue
//        int count = 0;
//        while (!adaptiveQuestionQueue.isEmpty() && count < 5) {
//            candidates.add(adaptiveQuestionQueue.poll());
//            count++;
//        }
//
//        // Put them back in the queue
//        adaptiveQuestionQueue.addAll(candidates);
//
//        // Select one question randomly from these top candidates
//        // (This adds some variety while still focusing on priority)
//        currentQuestion = candidates.get(random.nextInt(candidates.size()));
//
//        // Update recently viewed and shuffle options
//        updateRecentlyViewed(currentQuestion);
//    }
//
//    private void updateRecentlyViewed(QuizQuestion question) {
//        // Keep the set at fixed size by removing oldest if necessary
//        if (recentlyViewedQuestions.size() >= 30) {
//            recentlyViewedQuestions.remove(recentlyViewedQuestions.iterator().next());
//        }
//
//        // Add current question to recently viewed
//        recentlyViewedQuestions.add(question);
//
//        // Shuffle the options to prevent pattern recognition
//        question.shuffleOptions();
//    }
//
//    public int getScore() {
//        return score;
//    }
//
//    public int getTotalQuestions() {
//        return totalQuestions;
//    }
//
//    public int getCurrentQuestionNumber() {
//        return questionCounter + 1;
//    }
//
//    public String getCurrentCategory() {
//        return currentQuestion.getCategory();
//    }
//
//    public Map<String, Double> getCategoryPerformance() {
//        return categoryPerformance;
//    }
//
//    // Sample question sets for each category
//    private List<QuizQuestion> createRecyclingQuestions() {
//        List<QuizQuestion> questions = new ArrayList<>();
//        questions.add(new QuizQuestion(
//                "Which item should NOT be put in a standard recycling bin?",
//                Arrays.asList("Plastic bottle", "Ceramic mug", "Cardboard box", "Aluminum can"),
//                "Ceramic mug", "Recycling", 1));
//        questions.add(new QuizQuestion(
//                "What does the recycling symbol with number 1 (PET) indicate?",
//                Arrays.asList("Polyethylene terephthalate (e.g., water bottles)", "High-density polyethylene", "Polyvinyl chloride", "Bio-degradable plastic"),
//                "Polyethylene terephthalate (e.g., water bottles)", "Recycling", 2));
//        questions.add(new QuizQuestion(
//                "How many times can glass be recycled?",
//                Arrays.asList("Once", "Five times", "Ten times", "Indefinitely"),
//                "Indefinitely", "Recycling", 2));
//        questions.add(new QuizQuestion(
//                "What is 'wishcycling'?",
//                Arrays.asList("Hoping to win recycling contests", "Putting non-recyclable items in recycling bins hoping they'll be recycled", "Recycling while making a wish", "A recycling festival"),
//                "Putting non-recyclable items in recycling bins hoping they'll be recycled", "Recycling", 3));
//        questions.add(new QuizQuestion(
//                "What percentage of plastic is estimated to be recycled globally?",
//                Arrays.asList("Around 9%", "Around 25%", "Around 40%", "Around 60%"),
//                "Around 9%", "Recycling", 3));
//        // Add more recycling questions
//        // ...
//        return questions;
//    }
//
//    private List<QuizQuestion> createEnergyQuestions() {
//        List<QuizQuestion> questions = new ArrayList<>();
//        questions.add(new QuizQuestion(
//                "Which renewable energy source currently provides the most electricity worldwide?",
//                Arrays.asList("Solar power", "Wind power", "Hydropower", "Geothermal power"),
//                "Hydropower", "Energy", 2));
//        questions.add(new QuizQuestion(
//                "What percentage of a traditional incandescent light bulb's energy is converted to light?",
//                Arrays.asList("About 5-10%", "About 25-30%", "About 50-60%", "About 75-80%"),
//                "About 5-10%", "Energy", 2));
//        questions.add(new QuizQuestion(
//                "Which household appliance typically uses the most energy?",
//                Arrays.asList("Refrigerator", "Television", "Washing machine", "Microwave"),
//                "Refrigerator", "Energy", 1));
//        questions.add(new QuizQuestion(
//                "What is a 'passive house'?",
//                Arrays.asList("A house that generates its own electricity", "A house that uses minimal energy for heating and cooling", "A house made of recycled materials", "A house without active security systems"),
//                "A house that uses minimal energy for heating and cooling", "Energy", 3));
//        questions.add(new QuizQuestion(
//                "Which action saves the most energy?",
//                Arrays.asList("Turning off lights when leaving a room", "Using energy-efficient appliances", "Proper home insulation", "Unplugging devices not in use"),
//                "Proper home insulation", "Energy", 2));
//        // Add more energy questions
//        // ...
//        return questions;
//    }
//
//    // Continue with other categories
//    private List<QuizQuestion> createWaterQuestions() {
//        List<QuizQuestion> questions = new ArrayList<>();
//        // Add water conservation questions
//        questions.add(new QuizQuestion(
//                "How much water does a typical 10-minute shower use?",
//                Arrays.asList("About 10 gallons", "About 25 gallons", "About 50 gallons", "About 100 gallons"),
//                "About 25 gallons", "Water Conservation", 1));
//        // Add more water questions...
//        return questions;
//    }
//
//    private List<QuizQuestion> createTransportQuestions() {
//        List<QuizQuestion> questions = new ArrayList<>();
//        // Add transport questions
//        questions.add(new QuizQuestion(
//                "Which form of transportation typically has the lowest carbon footprint per passenger mile?",
//                Arrays.asList("Train", "Bus", "Carpool", "Electric scooter"),
//                "Train", "Sustainable Transport", 2));
//        // Add more transport questions...
//        return questions;
//    }
//
//    private List<QuizQuestion> createWildlifeQuestions() {
//        List<QuizQuestion> questions = new ArrayList<>();
//        // Add wildlife questions
//        questions.add(new QuizQuestion(
//                "Which of these animals was brought back from the brink of extinction due to conservation efforts?",
//                Arrays.asList("American bison", "Tasmanian tiger", "Dodo bird", "Passenger pigeon"),
//                "American bison", "Wildlife", 2));
//        // Add more wildlife questions...
//        return questions;
//    }
//
//    private List<QuizQuestion> createClimateQuestions() {
//        List<QuizQuestion> questions = new ArrayList<>();
//        // Add climate questions
//        questions.add(new QuizQuestion(
//                "What is the primary greenhouse gas released by human activities?",
//                Arrays.asList("Carbon dioxide", "Methane", "Nitrous oxide", "Water vapor"),
//                "Carbon dioxide", "Climate Change", 1));
//        // Add more climate questions...
//        return questions;
//    }
//
//    private List<QuizQuestion> createEcoLivingQuestions() {
//        List<QuizQuestion> questions = new ArrayList<>();
//        // Add eco-living questions
//        questions.add(new QuizQuestion(
//                "What is a 'zero waste' lifestyle?",
//                Arrays.asList("Producing no trash at all", "Minimizing waste sent to landfill through reducing, reusing, recycling, and composting",
//                        "Only using biodegradable products", "Living without electricity"),
//                "Minimizing waste sent to landfill through reducing, reusing, recycling, and composting", "Eco-Living", 2));
//        // Add more eco-living questions...
//        return questions;
//    }
//
//    private List<QuizQuestion> createFoodQuestions() {
//        List<QuizQuestion> questions = new ArrayList<>();
//        // Add food sustainability questions
//        questions.add(new QuizQuestion(
//                "Which food typically has the highest carbon footprint per pound?",
//                Arrays.asList("Beef", "Chicken", "Rice", "Vegetables"),
//                "Beef", "Food Sustainability", 1));
//        // Add more food questions...
//        return questions;
//    }
//
//    private List<QuizQuestion> createPollutionQuestions() {
//        List<QuizQuestion> questions = new ArrayList<>();
//        // Add pollution questions
//        questions.add(new QuizQuestion(
//                "What is the Great Pacific Garbage Patch?",
//                Arrays.asList("A landfill in the Pacific Northwest", "A collection of marine debris in the North Pacific Ocean",
//                        "A recycling center in Hawaii", "A polluted beach in California"),
//                "A collection of marine debris in the North Pacific Ocean", "Pollution", 1));
//        // Add more pollution questions...
//        return questions;
//    }
//
//    private List<QuizQuestion> createTechQuestions() {
//        List<QuizQuestion> questions = new ArrayList<>();
//        // Add green technology questions
//        questions.add(new QuizQuestion(
//                "What is 'e-waste'?",
//                Arrays.asList("Energy waste from inefficient appliances", "Electronic products nearing the end of their useful life",
//                        "Excess electricity from power plants", "Email spam"),
//                "Electronic products nearing the end of their useful life", "Green Technology", 1));
//        // Add more tech questions...
//        return questions;
//    }
//}



/// /OGOGOOG
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.Map;
//import java.util.Queue;
//
//class QuizBackend {
//    private static final String[][] quizData = {
//            {"What is the primary cause of climate change?", "Deforestation", "Burning fossil fuels", "Agriculture", "Volcanic eruptions", "Burning fossil fuels"},
//            {"Which greenhouse gas is most responsible for global warming?", "Oxygen", "Methane", "Carbon dioxide", "Nitrogen", "Carbon dioxide"},
//            {"What percentage of climate scientists agree that climate change is human-caused?", "50%", "75%", "97%", "100%", "97%"}
//    };
//
//    private Queue<String[]> questionQueue;
//    private int score = 0;
//    private Map<String, Integer> answerFrequencies;
//
//    public QuizBackend() {
//        questionQueue = new LinkedList<>();
//        answerFrequencies = new HashMap<>();
//        for (String[] q : quizData) {
//            questionQueue.offer(q);
//        }
//    }
//
//    public String[] getCurrentQuestion() {
//        return questionQueue.peek();
//    }
//
//    public boolean checkAnswer(String selectedAnswer) {
//        if (questionQueue.isEmpty()) return false;
//
//        String[] currentQuestion = questionQueue.poll();
//        String correctAnswer = currentQuestion[5];
//
//        answerFrequencies.put(selectedAnswer, answerFrequencies.getOrDefault(selectedAnswer, 0) + 1);
//
//        if (selectedAnswer.equals(correctAnswer)) {
//            score++;
//            return true;
//        }
//        return false;
//    }
//
//    public boolean hasMoreQuestions() {
//        return !questionQueue.isEmpty();
//    }
//
//    public int getScore() {
//        return score;
//    }
//
//    public int getTotalQuestions() {
//        return quizData.length;
//    }
//
//    public Map<String, Integer> getAnswerFrequencies() {
//        return answerFrequencies;
//    }
//}