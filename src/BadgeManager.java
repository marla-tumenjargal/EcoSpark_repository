import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BadgeManager {
    // Badge definitions with improved colors and icons
    private Map<String, Badge> badgeTypes = new HashMap<>();

    public BadgeManager() {
        initializeBadges();
    }

    // Add additional badge types for more environmental problems
    public void initializeBadges() {
        // Define badges with improved colors and environmental categories
        // Point-based achievement badges
        badgeTypes.put("novice", new Badge("Novice", "Starting your sustainability journey", 10, new Color(173, 216, 230), "🌱"));
        badgeTypes.put("beginner", new Badge("Beginner", "Making regular eco-friendly choices", 20, new Color(135, 206, 235), "🌿"));
        badgeTypes.put("intermediate", new Badge("Intermediate", "Taking significant steps to reduce impact", 40, new Color(0, 191, 255), "🌍"));
        badgeTypes.put("advanced", new Badge("Advanced", "Adopting a sustainable lifestyle", 80, new Color(30, 144, 255), "🌊"));
        badgeTypes.put("expert", new Badge("Expert", "Leading by example in sustainability", 160, new Color(0, 0, 205), "⭐"));
        badgeTypes.put("master", new Badge("Master", "Mastering sustainable living", 320, new Color(75, 0, 130), "🏆"));

        // Environmental focus badges with brighter colors
        badgeTypes.put("community", new Badge("Community Champion", "Building environmental community connections", 0, new Color(135, 206, 250), "🤝"));
        badgeTypes.put("learning", new Badge("Eco Learner", "Expanding environmental knowledge", 0, new Color(173, 216, 230), "🧩"));
        badgeTypes.put("innovation", new Badge("Green Innovator", "Finding creative eco solutions", 0, new Color(152, 251, 152), "💡"));

        badgeTypes.put("water", new Badge("Water Protector", "Conserving and protecting water resources", 0, new Color(30, 144, 255), "💧"));
        badgeTypes.put("energy", new Badge("Energy Saver", "Reducing energy consumption", 0, new Color(255, 215, 0), "⚡"));
        badgeTypes.put("waste", new Badge("Zero Waste Hero", "Minimizing waste production", 0, new Color(255, 182, 193), "♻️"));

        badgeTypes.put("biodiversity", new Badge("Biodiversity Guardian", "Protecting plants and wildlife", 0, new Color(152, 251, 152), "🌺"));
        badgeTypes.put("advocacy", new Badge("Environmental Advocate", "Spreading awareness and advocacy", 0, new Color(255, 99, 71), "🏅"));
        badgeTypes.put("climate", new Badge("Climate Defender", "Taking action against climate change", 0, new Color(147, 112, 219), "🌀"));

        // Additional environmental problem badges with vibrant colors
        badgeTypes.put("oceans", new Badge("Ocean Defender", "Taking action to protect marine ecosystems", 0, new Color(0, 105, 148), "🐠"));
        badgeTypes.put("plastic", new Badge("Plastic Fighter", "Reducing plastic pollution in the environment", 0, new Color(255, 64, 129), "🚫"));
        badgeTypes.put("forest", new Badge("Forest Guardian", "Protecting and preserving forest ecosystems", 0, new Color(76, 175, 80), "🌲"));
        badgeTypes.put("air", new Badge("Clean Air Champion", "Taking steps to reduce air pollution", 0, new Color(178, 235, 242), "💨"));
        badgeTypes.put("soil", new Badge("Soil Protector", "Preserving soil health and preventing erosion", 0, new Color(121, 85, 72), "🌰"));
        badgeTypes.put("wildlife", new Badge("Wildlife Ally", "Supporting and protecting endangered species", 0, new Color(255, 193, 7), "🦁"));
        badgeTypes.put("sustainable_food", new Badge("Food Sustainability", "Supporting sustainable agriculture practices", 0, new Color(139, 195, 74), "🥗"));
        badgeTypes.put("transport", new Badge("Green Transport", "Using eco-friendly transportation methods", 0, new Color(103, 58, 183), "🚲"));
    }

    public Map<String, Badge> getBadgeTypes() {
        return badgeTypes;
    }

    public Badge getBadgeByName(String badgeName) {
        return badgeTypes.get(badgeName);
    }

    // Check if user earns any new badges based on their points
    public List<Badge> checkForNewBadges(Profile user) {
        List<Badge> newBadges = new ArrayList<>();
        int currentPoints = user.getPoints();

        // Check point-based badges
        for (Badge badge : badgeTypes.values()) {
            if (badge.getPointsRequired() > 0) {
                // If the user has enough points but doesn't have the badge yet
                if (currentPoints >= badge.getPointsRequired() &&
                        !user.hasBadge(badge.getName())) {
                    user.addBadge(badge.getName());
                    newBadges.add(badge);
                }
            }
        }

        return newBadges;
    }

    // The Badge class moved from DashboardPanel
    public static class Badge {
        private String name;
        private String description;
        private int pointsRequired;
        private Color color;
        private String icon;

        public Badge(String name, String description, int pointsRequired, Color color, String icon) {
            this.name = name;
            this.description = description;
            this.pointsRequired = pointsRequired;
            this.color = color;
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getPointsRequired() {
            return pointsRequired;
        }

        public Color getColor() {
            return color;
        }

        public String getIcon() {
            return icon;
        }
    }
}