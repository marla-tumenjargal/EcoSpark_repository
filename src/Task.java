import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Task {
    private int id;
    private String title;
    private String description;
    private int pointsValue;
    private boolean completed;
    private Date completionDate;
    private String type; // <-- New attribute for task type
    private static int taskIdCounter = 1;

    public Task(String title, String description, int pointsValue, String type) {
        this.id = taskIdCounter++;
        this.title = title;
        this.description = description;
        this.pointsValue = pointsValue;
        this.completed = false;
        this.type = type;
    }

    public void completeTask(Profile profile) {
        if (!this.completed) {
            this.completed = true;
            this.completionDate = new Date();
            profile.addPoints(this.pointsValue);
            System.out.println("Task '" + this.title + "' completed! You earned " + this.pointsValue + " points.");
        } else {
            System.out.println("This task has already been completed.");
        }
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPointsValue() {
        return pointsValue;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public String getType() {
        return type;
    }

    // Create a library of predefined tasks
    public static List<Task> createTaskLibrary() {
        List<Task> taskLibrary = new ArrayList<>();

        // Existing tasks
        taskLibrary.add(new Task("Recycle a water bottle", "Collect and properly recycle plastic water bottles.", 5, "daily"));
        taskLibrary.add(new Task("Use reusable bags", "Bring your own reusable bags when shopping.", 10, "daily"));
        taskLibrary.add(new Task("Turn off lights", "Turn off lights when leaving a room to save energy.", 5, "daily"));
        taskLibrary.add(new Task("Walk or bike", "Choose walking or biking instead of driving for short trips.", 15, "weekly"));
        taskLibrary.add(new Task("Plant a tree", "Plant a tree to help absorb CO2 from the atmosphere.", 50, "monthly"));
        taskLibrary.add(new Task("Save water", "Take shorter showers to conserve water.", 10, "daily"));
        taskLibrary.add(new Task("Reduce meat consumption", "Have a meat-free day to reduce your carbon footprint.", 20, "weekly"));
        taskLibrary.add(new Task("Use public transport", "Take public transportation instead of driving alone.", 25, "weekly"));
        taskLibrary.add(new Task("Clean up litter", "Collect and dispose of litter in your neighborhood.", 30, "monthly"));
        taskLibrary.add(new Task("Reduce plastic use", "Avoid single-use plastics for a day.", 15, "daily"));

        // New tasks (Home & Energy)
        taskLibrary.add(new Task("Install a programmable thermostat", "Automatically adjusting your home temperature reduces energy consumption when you're away or sleeping. This small change can reduce your heating and cooling costs by up to 10%.", 15, "one-time"));
        taskLibrary.add(new Task("Replace incandescent bulbs with LEDs", "LED bulbs use up to 90% less energy and last up to 25 times longer than traditional incandescent bulbs. The energy savings over the lifetime of each bulb is substantial.", 8, "one-time"));
        taskLibrary.add(new Task("Unplug electronics when not in use", "Many electronics use standby power even when turned off, creating 'phantom loads' that waste electricity. Unplugging them completely eliminates this unnecessary energy drain.", 5, "daily"));
        taskLibrary.add(new Task("Install low-flow showerheads", "These devices reduce water usage while maintaining water pressure. Each low-flow showerhead can save thousands of gallons of water annually.", 10, "one-time"));
        taskLibrary.add(new Task("Fix leaky faucets", "A faucet dripping just once per second wastes over 3,000 gallons of water per year. Repairing leaks promptly prevents this invisible but significant waste.", 7, "one-time"));
        taskLibrary.add(new Task("Wash clothes in cold water", "About 90% of the energy used by washing machines goes to heating water. Cold-water washing saves energy and is often just as effective for regular laundry.", 6, "daily"));
        taskLibrary.add(new Task("Line-dry clothes instead of using a dryer", "Clothes dryers are among the most energy-intensive household appliances. Line-drying uses zero electricity and helps clothes last longer.", 12, "daily"));
        taskLibrary.add(new Task("Install a rain barrel", "Collecting rainwater allows you to water plants without using treated tap water. Each barrel can save hundreds of gallons of water during growing seasons.", 20, "one-time"));
        taskLibrary.add(new Task("Use smart power strips", "These automatically cut power to peripheral devices when a main device is turned off. This eliminates phantom energy usage without having to unplug multiple items.", 8, "one-time"));
        taskLibrary.add(new Task("Apply weatherstripping to doors and windows", "Sealing air leaks prevents heated or cooled air from escaping your home. This simple upgrade can reduce energy bills by up to 20% annually.", 14, "one-time"));
        taskLibrary.add(new Task("Plant shade trees around your home", "Strategically placed trees can reduce summer cooling costs by up to 35%. They also absorb carbon dioxide and provide habitat for wildlife.", 25, "one-time"));
        taskLibrary.add(new Task("Switch to a tankless water heater", "These heat water on demand rather than constantly maintaining a tank of hot water. The efficiency improvement can reduce water heating costs by 30%.", 18, "one-time"));
        taskLibrary.add(new Task("Add insulation to your attic", "Proper insulation significantly reduces heat transfer between your home and the outside. This investment pays for itself through energy savings within a few years.", 22, "one-time"));
        taskLibrary.add(new Task("Install a dual-flush toilet", "These toilets use different amounts of water depending on what's being flushed. They can reduce household water consumption by thousands of gallons annually.", 15, "one-time"));
        taskLibrary.add(new Task("Set up a graywater system", "Reusing water from sinks and showers for irrigation conserves tremendous amounts of water. A simple system can reclaim thousands of gallons annually for garden use.", 30, "one-time"));

        // New tasks (Transportation & Travel)
        taskLibrary.add(new Task("Bike or walk for short trips", "Replacing car trips under 2 miles with active transportation eliminates emissions and provides health benefits. These short trips are surprisingly emission-intensive due to cold engines.", 10, "daily"));
        taskLibrary.add(new Task("Carpool to work", "Sharing rides cuts fuel consumption and emissions proportionally to the number of participants. Each person who joins a carpool removes one car from rush hour traffic.", 12, "daily"));
        taskLibrary.add(new Task("Inflate tires properly", "Properly inflated tires improve fuel efficiency by 3-4% and extend tire life. This simple maintenance task takes minutes but provides long-term benefits.", 5, "weekly"));
        taskLibrary.add(new Task("Use public transportation", "Buses and trains move many people with significantly less energy per person than individual cars. A full bus can replace 40+ individual car trips.", 15, "daily"));
        taskLibrary.add(new Task("Combine errands into fewer trips", "Planning multiple stops in a single outing reduces total distance traveled. This 'trip chaining' approach eliminates redundant travel and cold starts.", 8, "daily"));
        taskLibrary.add(new Task("Work from home when possible", "Each day working remotely eliminates commuting emissions entirely. This creates substantial carbon savings for those with long commutes.", 20, "daily"));
        taskLibrary.add(new Task("Choose economy class for flights", "First and business class seats take up more space, resulting in higher per-passenger emissions. Economy seating maximizes efficiency for necessary air travel.", 10, "one-time"));
        taskLibrary.add(new Task("Purchase carbon offsets for unavoidable travel", "Carbon offsets fund projects that reduce greenhouse gas emissions elsewhere. Quality offsets help mitigate the impact of necessary travel.", 15, "one-time"));
        taskLibrary.add(new Task("Maintain your vehicle regularly", "A well-maintained engine runs more efficiently and produces fewer emissions. Regular maintenance often pays for itself through improved fuel economy.", 8, "monthly"));
        taskLibrary.add(new Task("Choose local destinations for vacations", "Vacationing closer to home drastically reduces transportation emissions. Exploring nearby destinations often reveals overlooked local treasures.", 18, "one-time"));

        // New tasks (Consumption & Waste)
        taskLibrary.add(new Task("Bring reusable bags while shopping", "Each reusable bag can replace hundreds of single-use plastic bags over its lifetime. This reduces plastic pollution and conserves the resources used to produce disposable bags.", 6, "daily"));
        taskLibrary.add(new Task("Use a reusable water bottle", "A single reusable bottle can prevent hundreds of plastic bottles from being manufactured annually. This eliminates the energy used in production and prevents potential waste.", 7, "daily"));
        taskLibrary.add(new Task("Start composting food scraps", "Composting diverts waste from landfills where it would generate methane, a powerful greenhouse gas. The resulting compost also enriches soil and reduces the need for chemical fertilizers.", 15, "daily"));
        taskLibrary.add(new Task("Repair items instead of replacing them", "Extending product lifespans through repair reduces resource extraction and manufacturing impacts. Many items can be fixed with minimal skills and tools.", 12, "one-time"));
        taskLibrary.add(new Task("Buy secondhand when possible", "Purchasing used items prevents the environmental impacts of manufacturing new products. Secondhand markets extend the useful life of products that have already been created.", 10, "daily"));
        taskLibrary.add(new Task("Choose products with minimal packaging", "Excessive packaging creates waste and requires resources to produce. Selecting minimally packaged goods reduces material consumption and waste generation.", 8, "daily"));
        taskLibrary.add(new Task("Switch to paperless billing", "Electronic statements eliminate paper usage, printing, and physical delivery. This seemingly small change adds up to substantial resource savings across millions of accounts.", 5, "one-time"));
        taskLibrary.add(new Task("Use cloth napkins instead of paper", "Reusable cloth napkins can replace thousands of disposable paper napkins over their lifespan. This reduces both tree harvesting and waste generation.", 6, "daily"));
        taskLibrary.add(new Task("Buy in bulk to reduce packaging", "Purchasing larger quantities typically involves less packaging per unit of product. This approach minimizes waste while often saving money.", 9, "daily"));
        taskLibrary.add(new Task("Use reusable food containers for leftovers", "Durable containers eliminate the need for disposable plastic wrap, foil, or single-use containers. They protect food just as well while creating zero waste.", 7, "daily"));
        taskLibrary.add(new Task("Donate unwanted items instead of trashing them", "Giving usable items a second life keeps them out of landfills and reduces demand for new products. This extends the environmental return on the original manufacturing investment.", 10, "one-time"));
        taskLibrary.add(new Task("Make your own cleaning products", "Homemade cleaners using simple ingredients reduce packaging waste and avoid harmful chemicals. Basic ingredients like vinegar and baking soda effectively clean most surfaces.", 12, "one-time"));
        taskLibrary.add(new Task("Choose rechargeable batteries", "Each rechargeable battery can replace hundreds of disposable batteries over its lifespan. This prevents mining impacts and keeps toxic materials out of waste streams.", 8, "one-time"));
        taskLibrary.add(new Task("Opt out of junk mail", "Reducing unwanted mail saves trees and eliminates the emissions associated with production and delivery. A single opt-out request can prevent hundreds of pieces of junk mail annually.", 6, "one-time"));
        taskLibrary.add(new Task("Use cloth diapers", "Reusable diapers prevent thousands of disposable diapers from entering landfills per child. They also eliminate the substantial manufacturing impacts of disposable alternatives.", 25, "daily"));

        // New tasks (Food & Diet)
        taskLibrary.add(new Task("Reduce meat consumption", "Animal agriculture is resource-intensive and generates significant greenhouse gas emissions. Even reducing meat intake by one day per week makes a measurable difference.", 20, "weekly"));
        taskLibrary.add(new Task("Choose locally grown foods", "Local foods typically travel shorter distances, reducing transportation emissions. They also support local farmers and agricultural diversity.", 12, "daily"));
        taskLibrary.add(new Task("Grow some of your own food", "Home gardens eliminate packaging, transportation, and commercial agriculture impacts for the foods you grow. Even small container gardens on balconies can provide fresh herbs and some vegetables.", 18, "one-time"));
        taskLibrary.add(new Task("Start a backyard compost pile", "Composting food scraps returns nutrients to the soil rather than sending them to landfills. This natural process creates rich soil amendments while diverting waste.", 15, "one-time"));
        taskLibrary.add(new Task("Support organic farming by buying organic when possible", "Organic farming reduces synthetic pesticide and fertilizer use, protecting soil and water quality. Organic practices typically use less energy and emit fewer greenhouse gases.", 10, "daily"));
        taskLibrary.add(new Task("Plan meals to reduce food waste", "Thoughtful meal planning prevents food from spoiling before it can be used. Reducing food waste conserves all the resources that went into growing, processing, and transporting that food.", 8, "daily"));
        taskLibrary.add(new Task("Choose plant-based milk alternatives", "Plant milks typically have lower carbon and water footprints than dairy milk. Options like oat milk use substantially fewer resources per cup produced.", 7, "daily"));
        taskLibrary.add(new Task("Buy imperfect produce", "Purchasing 'ugly' fruits and vegetables prevents edible food from being wasted for cosmetic reasons. These items are nutritionally identical but often discarded in conventional food systems.", 8, "daily"));
        taskLibrary.add(new Task("Eat seasonally", "Seasonal eating reduces the energy required for greenhouse growing and long-distance transportation. In-season produce is typically fresher, more flavorful, and less resource-intensive.", 9, "daily"));
        taskLibrary.add(new Task("Preserve excess garden harvest", "Canning, freezing, or dehydrating garden surplus prevents waste and reduces dependence on store-bought foods in off-seasons. These preservation methods extend the environmental benefits of home gardening.", 14, "one-time"));

        // New tasks (Community & Advocacy)
        taskLibrary.add(new Task("Volunteer for community cleanup events", "Removing litter from natural areas prevents pollution of waterways and harm to wildlife. These events also raise awareness about waste issues in your community.", 15, "one-time"));
        taskLibrary.add(new Task("Start or join a community garden", "Community gardens transform unused spaces into productive growing areas that benefit many people. They reduce food miles while building community resilience and food security.", 20, "one-time"));
        taskLibrary.add(new Task("Participate in local environmental initiatives", "Getting involved in local environmental projects amplifies individual impact through collective action. Your participation strengthens community efforts for broader change.", 12, "one-time"));
        taskLibrary.add(new Task("Educate others about eco-friendly practices", "Sharing knowledge multiplies your impact by inspiring others to make sustainable choices. Each person you influence creates a ripple effect of environmental benefits.", 10, "one-time"));
        taskLibrary.add(new Task("Support environmental legislation", "Contacting representatives about environmental policies helps shape larger systemic changes. Policy changes can have widespread impacts beyond what individuals can achieve alone.", 15, "one-time"));
        taskLibrary.add(new Task("Organize a neighborhood swap meet", "Item exchanges give products new life with different owners, preventing waste and unnecessary purchasing. These events build community while reducing consumption.", 12, "one-time"));
        taskLibrary.add(new Task("Plant native species in your garden", "Native plants support local pollinators and wildlife while requiring less water and maintenance. They contribute to biodiversity and ecosystem health in your region.", 16, "one-time"));
        taskLibrary.add(new Task("Create a wildlife-friendly yard", "Providing habitat features like bird baths and pollinator plants supports local ecosystems. Even small yards can become valuable wildlife corridors and habitat islands.", 18, "one-time"));
        taskLibrary.add(new Task("Join a citizen science project", "Contributing observations to research projects helps scientists track environmental changes and biodiversity. These collective efforts provide valuable data for conservation efforts.", 10, "one-time"));
        taskLibrary.add(new Task("Advocate for renewable energy in your community", "Supporting local renewable energy initiatives helps transition communities away from fossil fuels. Community advocacy can accelerate the adoption of clean energy solutions.", 18, "one-time"));

        // New tasks (Workplace & School)
        taskLibrary.add(new Task("Start a recycling program at work", "Workplace recycling programs can divert substantial waste from landfills. Office environments often generate large amounts of recyclable paper and packaging.", 15, "one-time"));
        taskLibrary.add(new Task("Bring a reusable lunch container", "Using durable containers eliminates daily disposable packaging waste. Over a work year, this prevents hundreds of single-use containers from being discarded.", 8, "daily"));
        taskLibrary.add(new Task("Encourage paperless practices at work", "Digital documentation reduces paper consumption and storage needs. Modern workplaces can function efficiently with minimal printing.", 10, "one-time"));
        taskLibrary.add(new Task("Suggest energy efficiency improvements", "Identifying opportunities for energy savings at work can have large-scale impacts. Commercial buildings often have significant efficiency improvement potential.", 12, "one-time"));
        taskLibrary.add(new Task("Set up a carpool system with colleagues", "Organized workplace carpooling reduces commuting emissions and parking needs. Each additional participant multiplies the environmental benefits.", 14, "one-time"));
        taskLibrary.add(new Task("Choose virtual meetings when possible", "Remote participation eliminates travel emissions associated with in-person meetings. Virtual options are increasingly effective alternatives for many types of collaboration.", 8, "daily"));
        taskLibrary.add(new Task("Start a workplace sustainability committee", "Dedicated groups can systematically improve organizational environmental performance. These committees identify opportunities and implement changes that individual employees cannot.", 20, "one-time"));
        taskLibrary.add(new Task("Conduct a workplace energy audit", "Systematic evaluation identifies energy waste and prioritizes improvements. Professional or DIY audits reveal opportunities that are often invisible in daily operations.", 15, "one-time"));
        taskLibrary.add(new Task("Advocate for green purchasing policies", "Environmentally preferable purchasing leverages organizational buying power for sustainability. Procurement policies can drive substantial upstream environmental improvements.", 12, "one-time"));
        taskLibrary.add(new Task("Bring plants into the office environment", "Indoor plants improve air quality and connection to nature in work settings. They absorb pollutants while creating more pleasant, productive spaces.", 6, "one-time"));

        // New tasks (Personal Development)
        taskLibrary.add(new Task("Calculate your carbon footprint", "Understanding your personal impact helps identify priority areas for improvement. Carbon footprint calculators provide valuable insights for effective action.", 8, "one-time"));
        taskLibrary.add(new Task("Learn a sustainability skill like preserving food or basic repairs", "Practical skills enable more self-sufficient, lower-impact living. These abilities often save money while reducing environmental impacts.", 15, "one-time"));
        taskLibrary.add(new Task("Read books on environmental topics", "Deepening your knowledge provides context for more effective environmental choices. Well-informed actions tend to have greater positive impact.", 7, "one-time"));
        taskLibrary.add(new Task("Talk with children about environmental stewardship", "Early environmental education shapes lifelong attitudes and behaviors. Children who understand environmental connections often become effective advocates.", 10, "one-time"));
        taskLibrary.add(new Task("Support environmental organizations", "Financial contributions enable professional advocacy and conservation work. Organizations can achieve systemic changes that require collective resources.", 15, "one-time"));

        return taskLibrary;
    }
}