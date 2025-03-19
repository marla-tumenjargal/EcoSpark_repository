import java.awt.*;

public class Badge {
    private String name;
    private String description;
    private int pointsRequired;
    private Color color;
    private String category;
    private String icon;

    public Badge(String name, String description, int pointsRequired, Color color, String icon) {
        this.name = name;
        this.description = description;
        this.pointsRequired = pointsRequired;
        this.color = color;
        this.category = "";
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
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
}