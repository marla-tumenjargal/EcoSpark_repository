import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class EcoSparkApp extends JFrame {

    private ApplicationModel model;
    private Profile currentUser;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JScrollPane scrollPane;
    private JPanel tasksListPanel;
    private JPanel frame;
    private TaskManager taskManager;
    private JFrame dashboardFrame;
    private Map<String, JComponent> formFields = new HashMap<>();

    CarbonFootprintCalculator calculator = new CarbonFootprintCalculator();

    // Login Panels
    private JPanel loginPanel;
    private JPanel registerPanel;
    JPanel dashboardPanel;
    private JPanel profilePanel;
    private JPanel tasksPanel;
    private JPanel carbonFootprintPanel;
    private JPanel quizPanel;

    private Map<String, String> userCredentials = new HashMap<>(); // Store user credentials (email -> password)
    private Map<String, String> userNames = new HashMap<>(); // Store usernames (email -> name)
    private Map<String, Boolean> taskCompletionStatus = new HashMap<>(); // Store user task completion status (email_taskId -> completed)
    private Map<String, Profile> userProfiles = new HashMap<>(); // Store user profiles (email -> Profile)

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EcoSparkApp();
            }
        });
    }

    public EcoSparkApp() {
        model = new ApplicationModel();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("🌲EcoSpark 🌲");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 1000);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        cardLayout = new CardLayout();
        taskManager = new TaskManager();

        createNavBar();
        createHeroSection();
        createOfferSection();
        createAboutSection();
        createFooterSection();

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Prevent horizontal scrolling

        add(scrollPane);
        setVisible(true);
    }

    private boolean isUserLoggedIn() {
        return currentUser != null;
    }

    private void createCarbonFootprintPanel() {
        getContentPane().remove(scrollPane);

        // Create main panel with white background
        carbonFootprintPanel = new JPanel();
        carbonFootprintPanel.setLayout(new BoxLayout(carbonFootprintPanel, BoxLayout.Y_AXIS));
        carbonFootprintPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        carbonFootprintPanel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Calculate Your Carbon Footprint");
        titleLabel.setFont(UIConstants.HEADER_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Information Panel
        JPanel infoPanel = createInfoPanel();

        // Create tabbed pane for different categories
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.WHITE);

        // Add tabs with enhanced input fields
        JPanel generalPanel = createGeneralPanel();
        JPanel transportationPanel = createTransportationPanel();
        JPanel foodPanel = createFoodPanel();
        JPanel energyPanel = createEnergyPanel();
        JPanel wastePanel = createWastePanel();

        tabbedPane.addTab("General Usages", generalPanel);
        tabbedPane.addTab("Transportation", transportationPanel);
        tabbedPane.addTab("Food Consumption", foodPanel);
        tabbedPane.addTab("Energy Usage", energyPanel);
        tabbedPane.addTab("Waste & Other", wastePanel);

        // Set the font for the tab headers to BUTTON_FONT
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component tabComponent = tabbedPane.getTabComponentAt(i);
            if (tabComponent instanceof JComponent) {
                ((JComponent) tabComponent).setFont(UIConstants.BUTTON_FONT);  // Apply BUTTON_FONT to tab headers
            }
        }

        // Add components to main panel
        carbonFootprintPanel.add(titleLabel);
        carbonFootprintPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        carbonFootprintPanel.add(infoPanel);
        carbonFootprintPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        carbonFootprintPanel.add(tabbedPane);
        tabbedPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        // Submit button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        JButton submitButton = new JButton("SUBMIT");
        submitButton.setBackground(UIConstants.BUTTON_BLUE);
        submitButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(submitButton);

        // Add submit button with some space
        carbonFootprintPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        carbonFootprintPanel.add(buttonPanel);

        // Results section
        JPanel resultsPanel = createResultsPanel();

        // Add results to main panel
        carbonFootprintPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        carbonFootprintPanel.add(resultsPanel);

        // Create a scroll pane for the carbon footprint panel
        JScrollPane carbonScrollPane = new JScrollPane(carbonFootprintPanel);
        carbonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        carbonScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        carbonScrollPane.setBorder(null);
        carbonScrollPane.setBackground(Color.WHITE);

        // Submit button action listener with validation
        submitButton.addActionListener(e -> {
            if (validateAllFieldsFilled()) {
                calculateAndDisplayResults(resultsPanel);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please fill in all fields before submitting.",
                        "Incomplete Form",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        getContentPane().add(carbonScrollPane);
        setVisible(true);
    }


    private boolean validateAllFieldsFilled() {
        for (Map.Entry<String, JComponent> entry : formFields.entrySet()) {
            if (entry.getValue() instanceof JTextField) {
                JTextField textField = (JTextField) entry.getValue();
                if (textField.getText().trim().isEmpty()) {
                    return false;
                }
            } else if (entry.getValue() instanceof JComboBox) {
                JComboBox<?> comboBox = (JComboBox<?>) entry.getValue();
                if (comboBox.getSelectedIndex() == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel whatIsCarbonFootprintLabel = new JLabel("What is a carbon footprint?");
        whatIsCarbonFootprintLabel.setFont(UIConstants.BUTTON_FONT);

        JTextArea whatIsCarbonFootprintText = new JTextArea(
                "A carbon footprint is the total amount of greenhouse gases (including carbon " +
                        "dioxide and methane) that are generated by our actions. The average carbon " +
                        "footprint for a person in the United States is 16 tons, one of the highest rates in " +
                        "the world."
        );
        whatIsCarbonFootprintLabel.setFont(UIConstants.BODY_FONT);
        whatIsCarbonFootprintText.setWrapStyleWord(true);
        whatIsCarbonFootprintText.setLineWrap(true);
        whatIsCarbonFootprintText.setEditable(false);
        whatIsCarbonFootprintText.setBackground(Color.WHITE);

        JLabel whyIsCarbonFootprintImportantLabel = new JLabel("Why is my carbon footprint important?");
        whyIsCarbonFootprintImportantLabel.setFont(UIConstants.BUTTON_FONT);
        JTextArea whyIsCarbonFootprintImportantText = new JTextArea(
                "Your carbon footprint is important because it measures the total greenhouse " +
                        "gas emissions for which you are responsible, contributing to climate change " +
                        "and highlighting areas where you can reduce environmental impact."
        );
        whyIsCarbonFootprintImportantText.setWrapStyleWord(true);
        whyIsCarbonFootprintImportantText.setLineWrap(true);
        whyIsCarbonFootprintImportantText.setEditable(false);
        whyIsCarbonFootprintImportantText.setBackground(Color.WHITE);
        whyIsCarbonFootprintImportantText.setFont(UIConstants.BODY_FONT);

        infoPanel.add(whatIsCarbonFootprintLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(whatIsCarbonFootprintText);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        infoPanel.add(whyIsCarbonFootprintImportantLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(whyIsCarbonFootprintImportantText);

        return infoPanel;
    }

    private JPanel createResultsPanel() {
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Results title
        JLabel footprintLabel = new JLabel("Your Footprint");
        footprintLabel.setFont(UIConstants.HEADER_FONT);
        footprintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  // Ensure it's aligned left

        // Total footprint display
        JLabel totalFootprintLabel = new JLabel("Total Carbon Footprint: 0.00 tons CO2e");
        totalFootprintLabel.setFont(UIConstants.BUTTON_FONT);
        totalFootprintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  // Ensure it's aligned left

        // Breakdown section title
        JLabel breakdownLabel = new JLabel("Breakdown by Category:");
        breakdownLabel.setFont(UIConstants.BUTTON_FONT);
        breakdownLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  // Ensure it's aligned left

        // Breakdown panel
        JPanel breakdownPanel = new JPanel();
        breakdownPanel.setBackground(Color.WHITE);
        breakdownPanel.setLayout(new GridLayout(0, 1, 10, 10));  // Adjust spacing between the rows
        breakdownPanel.setName("breakdownPanel");

        // Add results components
        resultsPanel.add(footprintLabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        resultsPanel.add(totalFootprintLabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        resultsPanel.add(breakdownLabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        resultsPanel.add(breakdownPanel);

        totalFootprintLabel.setName("totalFootprintLabel");

        return resultsPanel;
    }


    private void calculateAndDisplayResults(JPanel resultsPanel) {
        // Get all input values from form fields
        Map<String, Object> inputValues = collectInputValues();

        // Calculate total footprint using the calculator instance
        double totalFootprint = calculator.calculateTotalFootprint(
                (String) inputValues.get("country"),
                Integer.parseInt((String) inputValues.get("carMilesPerWeek")),
                (String) inputValues.get("vehicleType"),
                (String) inputValues.get("fuelType"),
                Integer.parseInt((String) inputValues.get("flightsPerYear")),
                (String) inputValues.get("dietType"),
                Integer.parseInt((String) inputValues.get("meatConsumption")),
                Double.parseDouble((String) inputValues.get("electricityUsage")),
                (String) inputValues.get("energySource"),
                Double.parseDouble((String) inputValues.get("wasteProduced"))
        );

        // Get breakdown data
        Map<String, Double> footprintData = new HashMap<>();
        footprintData.put("General Usage", calculator.calculateGeneralFootprint((String) inputValues.get("country")));
        footprintData.put("Transportation", calculator.calculateTransportationFootprint(
                Integer.parseInt((String) inputValues.get("carMilesPerWeek")),
                (String) inputValues.get("vehicleType"),
                (String) inputValues.get("fuelType"),
                Integer.parseInt((String) inputValues.get("flightsPerYear"))
        ));
        footprintData.put("Food Consumption", calculator.calculateFoodFootprint(
                (String) inputValues.get("dietType"),
                Integer.parseInt((String) inputValues.get("meatConsumption"))
        ));
        footprintData.put("Energy Usage", calculator.calculateEnergyFootprint(
                Double.parseDouble((String) inputValues.get("electricityUsage")),
                (String) inputValues.get("energySource")
        ));
        footprintData.put("Waste Production", calculator.calculateWasteFootprint(
                Double.parseDouble((String) inputValues.get("wasteProduced"))
        ));

        // Update total footprint label
        for (Component comp : resultsPanel.getComponents()) {
            if (comp instanceof JLabel && "totalFootprintLabel".equals(comp.getName())) {
                ((JLabel) comp).setText("Total Carbon Footprint: " + String.format("%.2f", totalFootprint) + " tons CO2e");
                break;
            }
        }

        // Update breakdown panel
        for (Component comp : resultsPanel.getComponents()) {
            if (comp instanceof JPanel && "breakdownPanel".equals(comp.getName())) {
                JPanel breakdownPanel = (JPanel) comp;
                breakdownPanel.removeAll();

                for (Map.Entry<String, Double> entry : footprintData.entrySet()) {
                    JPanel categoryPanel = new JPanel(new BorderLayout());
                    categoryPanel.setBackground(getCategoryColor(entry.getKey()));

                    JLabel categoryLabel = new JLabel(entry.getKey() + ": " + String.format("%.2f", entry.getValue()) + " tons");
                    categoryLabel.setForeground(Color.WHITE);
                    categoryLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    categoryPanel.add(categoryLabel, BorderLayout.CENTER);
                    breakdownPanel.add(categoryPanel);
                }

                break;
            }
        }

        // Show results panel
        resultsPanel.setVisible(true);
        revalidate();
        repaint();
    }

    private Map<String, Object> collectInputValues() {
        Map<String, Object> values = new HashMap<>();

        // Collect values from form fields
        for (Map.Entry<String, JComponent> entry : formFields.entrySet()) {
            String key = entry.getKey();
            JComponent component = entry.getValue();

            if (component instanceof JTextField) {
                values.put(key, ((JTextField) component).getText());
            } else if (component instanceof JComboBox) {
                values.put(key, ((JComboBox<?>) component).getSelectedItem().toString());
            }
        }

        return values;
    }

    private Color getCategoryColor(String category) {
        switch (category) {
            case "General Usage":
                return new Color(65, 105, 225); // Royal Blue
            case "Transportation":
                return new Color(220, 20, 60); // Crimson
            case "Food Consumption":
                return new Color(34, 139, 34); // Forest Green
            case "Energy Usage":
                return new Color(218, 165, 32); // Goldenrod
            case "Waste Production":
                return new Color(41, 128, 185); // Steel Blue
            default:
                return Color.LIGHT_GRAY;
        }
    }

    // Enhanced tab panels with 7+ questions each and sample responses
    private JPanel createGeneralPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create combo boxes with empty selection
        JComboBox<String> countryCombo = new JComboBox<>(new String[]{"", "USA", "Canada", "UK", "China", "India", "Brazil", "Australia", "Other"});
        JComboBox<String> stateCombo = new JComboBox<>(new String[]{"", "Alabama", "Alaska", "Arizona", "California", "Colorado", "New York", "Texas", "Other"});
        JComboBox<String> householdCombo = new JComboBox<>(new String[]{"", "1", "2", "3", "4", "5+"});
        JComboBox<String> areaCombo = new JComboBox<>(new String[]{"", "Urban", "Suburban", "Rural"});
        JComboBox<String> homeCombo = new JComboBox<>(new String[]{"", "Apartment", "Townhouse", "Single Family Home", "Other"});
        JComboBox<String> renewableCombo = new JComboBox<>(new String[]{"", "Yes", "No", "Partial"});

        // Add fields with sample responses
        addFormFieldWithSample(panel, "What country do you live in?", countryCombo, "(e.g., USA)");
        addFormFieldWithSample(panel, "What state do you live in? (US only)", stateCombo, "(e.g., California)");
        addFormFieldWithSample(panel, "How many people are in your household?", householdCombo, "(e.g., 3)");
        addFormFieldWithSample(panel, "Do you live in an urban, suburban, or rural area?", areaCombo, "(e.g., Suburban)");
        addFormFieldWithSample(panel, "What type of home do you live in?", homeCombo, "(e.g., Apartment)");
        addFormFieldWithSample(panel, "What is the square footage of your home?", createAndRegisterTextField("squareFootage"), "(e.g., 1500)");
        addFormFieldWithSample(panel, "Do you use renewable energy at home?", renewableCombo, "(e.g., Partial)");

        formFields.put("country", countryCombo);
        formFields.put("state", stateCombo);
        formFields.put("household", householdCombo);
        formFields.put("area", areaCombo);
        formFields.put("homeType", homeCombo);
        formFields.put("renewable", renewableCombo);

        return panel;
    }

    private JPanel createTransportationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create combo boxes with empty selection
        JComboBox<String> vehicleTypeCombo = new JComboBox<>(new String[]{"", "SUV", "Sedan", "Truck", "Compact Car", "Hybrid", "Electric Vehicle", "None"});
        JComboBox<String> fuelTypeCombo = new JComboBox<>(new String[]{"", "Gas", "Diesel", "Electric", "Hybrid", "Other"});
        JComboBox<String> carpoolCombo = new JComboBox<>(new String[]{"", "Yes", "No", "Sometimes"});

        // Add fields with sample responses
        addFormFieldWithSample(panel, "How many miles do you drive per week?", createAndRegisterTextField("carMilesPerWeek"), "(e.g., 100)");
        addFormFieldWithSample(panel, "What type of vehicle do you own?", vehicleTypeCombo, "(e.g., Sedan)");
        addFormFieldWithSample(panel, "What type of fuel does your vehicle use?", fuelTypeCombo, "(e.g., Gas)");
        addFormFieldWithSample(panel, "How fuel-efficient is your vehicle (mpg)?", createAndRegisterTextField("mpg"), "(e.g., 25 miles per gallon)");
        addFormFieldWithSample(panel, "How many flights do you take per year?", createAndRegisterTextField("flightsPerYear"), "(e.g., 2)");
        addFormFieldWithSample(panel, "How many miles do you travel by public transit weekly?", createAndRegisterTextField("transitMiles"), "(e.g., 30)");
        addFormFieldWithSample(panel, "Do you carpool regularly?", carpoolCombo, "(e.g., Sometimes)");

        formFields.put("vehicleType", vehicleTypeCombo);
        formFields.put("fuelType", fuelTypeCombo);
        formFields.put("carpool", carpoolCombo);

        return panel;
    }

    private JPanel createFoodPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create combo boxes with empty selection
        JComboBox<String> dietTypeCombo = new JComboBox<>(new String[]{"", "Vegan", "Vegetarian", "Pescatarian", "Omnivore", "Keto", "Other"});
        JComboBox<String> localFoodCombo = new JComboBox<>(new String[]{"", "0-20%", "21-40%", "41-60%", "61-80%", "81-100%"});
        JComboBox<String> restaurantCombo = new JComboBox<>(new String[]{"", "Daily", "Several times a week", "Weekly", "Monthly", "Rarely"});
        JComboBox<String> foodWasteCombo = new JComboBox<>(new String[]{"", "Very little", "Some", "Average", "Significant", "A lot"});
        JComboBox<String> growFoodCombo = new JComboBox<>(new String[]{"", "Yes", "No", "Some"});

        // Add fields with sample responses
        addFormFieldWithSample(panel, "What type of diet do you follow?", dietTypeCombo, "(e.g., Omnivore)");
        addFormFieldWithSample(panel, "How many meat-containing meals do you eat per week?", createAndRegisterTextField("meatConsumption"), "(e.g., 7)");
        addFormFieldWithSample(panel, "How many dairy-containing meals do you eat per week?", createAndRegisterTextField("dairyConsumption"), "(e.g., 14)");
        addFormFieldWithSample(panel, "What percentage of your food is locally produced?", localFoodCombo, "(e.g., 21-40%)");
        addFormFieldWithSample(panel, "How often do you eat at restaurants?", restaurantCombo, "(e.g., Weekly)");
        addFormFieldWithSample(panel, "How much food waste do you generate weekly?", foodWasteCombo, "(e.g., Some)");
        addFormFieldWithSample(panel, "Do you grow any of your own food?", growFoodCombo, "(e.g., No)");

        formFields.put("dietType", dietTypeCombo);
        formFields.put("localFood", localFoodCombo);
        formFields.put("restaurant", restaurantCombo);
        formFields.put("foodWaste", foodWasteCombo);
        formFields.put("growFood", growFoodCombo);

        return panel;
    }

    private JPanel createEnergyPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create combo boxes with empty selection
        JComboBox<String> energySourceCombo = new JComboBox<>(new String[]{"", "Coal", "Natural Gas", "Nuclear", "Solar", "Wind", "Hydroelectric", "Other"});
        JComboBox<String> efficientAppliancesCombo = new JComboBox<>(new String[]{"", "All", "Most", "Some", "Few", "None"});
        JComboBox<String> ledBulbsCombo = new JComboBox<>(new String[]{"", "All", "Most", "Some", "None"});

        // Add fields with sample responses
        addFormFieldWithSample(panel, "What is your average monthly electricity usage (kWh)?", createAndRegisterTextField("electricityUsage"), "(e.g., 500)");
        addFormFieldWithSample(panel, "What is your primary source of electricity?", energySourceCombo, "(e.g., Natural Gas)");
        addFormFieldWithSample(panel, "What is your average monthly natural gas usage?", createAndRegisterTextField("gasUsage"), "(e.g., 100 therms)");
        addFormFieldWithSample(panel, "Do you use any energy-efficient appliances?", efficientAppliancesCombo, "(e.g., Most)");
        addFormFieldWithSample(panel, "What temperature do you keep your home in summer?", createAndRegisterTextField("summerTemp"), "(e.g., 75°F)");
        addFormFieldWithSample(panel, "What temperature do you keep your home in winter?", createAndRegisterTextField("winterTemp"), "(e.g., 68°F)");
        addFormFieldWithSample(panel, "Do you use LED light bulbs?", ledBulbsCombo, "(e.g., Most)");

        formFields.put("energySource", energySourceCombo);
        formFields.put("efficientAppliances", efficientAppliancesCombo);
        formFields.put("ledBulbs", ledBulbsCombo);

        return panel;
    }

    private JPanel createWastePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create combo boxes with empty selection
        JComboBox<String> recycleCombo = new JComboBox<>(new String[]{"", "Always", "Often", "Sometimes", "Rarely", "Never"});
        JComboBox<String> compostCombo = new JComboBox<>(new String[]{"", "Yes", "No", "Sometimes"});
        JComboBox<String> clothesCombo = new JComboBox<>(new String[]{"", "Weekly", "Monthly", "Quarterly", "Yearly", "Rarely"});
        JComboBox<String> electronicsCombo = new JComboBox<>(new String[]{"", "Monthly", "Quarterly", "Yearly", "Every few years", "Rarely"});
        JComboBox<String> plasticCombo = new JComboBox<>(new String[]{"", "Daily", "Often", "Sometimes", "Rarely", "Never"});

        // Add fields with sample responses
        addFormFieldWithSample(panel, "How much waste do you produce per week (kg)?", createAndRegisterTextField("wasteProduced"), "(e.g., 10)");
        addFormFieldWithSample(panel, "Do you recycle?", recycleCombo, "(e.g., Often)");
        addFormFieldWithSample(panel, "Do you compost?", compostCombo, "(e.g., Sometimes)");
        addFormFieldWithSample(panel, "How often do you buy new clothes?", clothesCombo, "(e.g., Quarterly)");
        addFormFieldWithSample(panel, "How often do you buy new electronics?", electronicsCombo, "(e.g., Yearly)");
        addFormFieldWithSample(panel, "Do you use single-use plastics?", plasticCombo, "(e.g., Sometimes)");
        addFormFieldWithSample(panel, "How many disposable items do you use weekly?", createAndRegisterTextField("disposableItems"), "(e.g., 15)");

        formFields.put("recycle", recycleCombo);
        formFields.put("compost", compostCombo);
        formFields.put("clothes", clothesCombo);
        formFields.put("electronics", electronicsCombo);
        formFields.put("plastic", plasticCombo);

        return panel;
    }

    private JTextField createAndRegisterTextField(String fieldName) {
        JTextField textField = new JTextField(10);
        formFields.put(fieldName, textField);
        return textField;
    }

    // Helper method to add form fields with sample responses
    private void addFormFieldWithSample(JPanel panel, String labelText, JComponent inputComponent, String sampleText) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.X_AXIS));
        fieldPanel.setBackground(new Color(240, 240, 240));
        fieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(250, 20));

        JLabel sampleLabel = new JLabel(sampleText);
        sampleLabel.setForeground(Color.GRAY);
        sampleLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        if (inputComponent instanceof JTextField) {
            inputComponent.setMaximumSize(new Dimension(150, 30));
            inputComponent.setPreferredSize(new Dimension(150, 30));
        } else if (inputComponent instanceof JComboBox) {
            inputComponent.setMaximumSize(new Dimension(150, 30));
            inputComponent.setPreferredSize(new Dimension(150, 30));
        }

        fieldPanel.add(label);
        fieldPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        fieldPanel.add(inputComponent);
        fieldPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        fieldPanel.add(sampleLabel);

        panel.add(fieldPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void createLoginPanel() {
        getContentPane().remove(scrollPane);

        // Make the login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Title
        JLabel titleLabel = new JLabel("Login to EcoSpark");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // email
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        // password
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 248, 255));
        JButton loginButton = new JButton("Log In");
        JButton backButton = new JButton("Back to Home");
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        // status
        JLabel statusLabel = new JLabel(" ");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginPanel.add(titleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(emailPanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(buttonPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(statusLabel);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Please enter email and password");
                statusLabel.setForeground(Color.RED);
                return;
            }

            if (userCredentials.containsKey(email) && userCredentials.get(email).equals(password)) {
                // Set current user

                model.setCurrentUser(currentUser);
                currentUser = userProfiles.get(email);
                statusLabel.setText("Login successful!");
                statusLabel.setForeground(Color.GREEN);

                // Switch back to main view
                getContentPane().remove(loginPanel);
                getContentPane().add(scrollPane);
                revalidate();
                repaint();
            } else {
                statusLabel.setText("Invalid email or password");
                statusLabel.setForeground(Color.RED);
            }
        });

        backButton.addActionListener(e -> {
            // Remove login panel and restore main panel
            getContentPane().remove(loginPanel);
            getContentPane().add(scrollPane);
            revalidate();
            repaint();
        });

        mainPanel.add(loginPanel, "login");
        getContentPane().add(loginPanel);
        revalidate();
        repaint();
    }

    private void createRegisterPanel() {
        getContentPane().remove(scrollPane);

        // Make the register panel
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));
        registerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Title
        JLabel titleLabel = new JLabel("Create Your EcoSpark Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Name
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("Full Name:");
        JTextField nameField = new JTextField(20);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        // Email
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        // Password
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Confirm Password
        JPanel confirmPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordPanel.add(confirmPasswordLabel);
        confirmPasswordPanel.add(confirmPasswordField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 248, 255));
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back to Home");
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        // Status
        JLabel statusLabel = new JLabel(" ");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerPanel.add(titleLabel);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        registerPanel.add(namePanel);
        registerPanel.add(emailPanel);
        registerPanel.add(passwordPanel);
        registerPanel.add(confirmPasswordPanel);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        registerPanel.add(buttonPanel);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        registerPanel.add(statusLabel);

        // Button actions

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Simple validation check
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                statusLabel.setText("All fields must be filled out!");
                statusLabel.setForeground(Color.RED);
            } else if (!password.equals(confirmPassword)) {
                statusLabel.setText("Passwords do not match!");
                statusLabel.setForeground(Color.RED);
            } else {
                // Store the new user credentials
                userCredentials.put(email, password);
                userNames.put(email, name);
                userProfiles.put(email, new Profile(name, password, email));

                statusLabel.setText("Account created successfully!");
                statusLabel.setForeground(Color.GREEN);

                // Return to main view after a short delay
                Timer timer = new Timer(1500, event -> {
                    getContentPane().remove(registerPanel);
                    getContentPane().add(scrollPane);
                    revalidate();
                    repaint();
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        backButton.addActionListener(e -> {
            getContentPane().remove(registerPanel);
            getContentPane().add(scrollPane);
            revalidate();
            repaint();
        });

        getContentPane().add(registerPanel);
        setVisible(true);
    }

    private void createHeroSection() {
        JPanel heroPanel = new JPanel();
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.X_AXIS));
        heroPanel.setBackground(Color.WHITE);
        heroPanel.setBorder(new EmptyBorder(0, 40, 40, 40));
        heroPanel.setMaximumSize(new Dimension(850, 600));

        // Text section
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Check if user is logged in and display personalized welcome
        String welcomeStart = "Welcome to EcoSpark";
        String userName = "";
        if (currentUser != null) {
            welcomeStart = "Welcome back,";
            userName = currentUser.getName() + "!";
        }

        JLabel welcomeStartLabel = new JLabel(welcomeStart);
        welcomeStartLabel.setFont(UIConstants.HEADER_FONT);
        welcomeStartLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        welcomePanel.add(welcomeStartLabel);

        if (currentUser != null) {
            JLabel userNameLabel = new JLabel(userName);
            userNameLabel.setFont(UIConstants.HEADER_FONT);
            userNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            welcomePanel.add(userNameLabel);
        }

        JTextArea descriptionText = new JTextArea(
                "Climate change is one of our world's most pressing issues. It " +
                        "threatens our forests, our freshwater supply, and our planet's " +
                        "livelihood. EcoSpark was made with our Earth in mind; we strive " +
                        "to create a community that supports, educates, and inspires our " +
                        "users to visualize their unique impact on climate change. Be the " +
                        "change, one step at a time."
        );

        descriptionText.setWrapStyleWord(true);
        descriptionText.setLineWrap(true);
        descriptionText.setEditable(false);
        descriptionText.setFocusable(false);
        descriptionText.setFont(UIConstants.BODY_FONT);
        descriptionText.setBackground(Color.WHITE);
        descriptionText.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionText.setMaximumSize(new Dimension(350, 150));

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Only show Join/Login buttons if not logged in
        if (currentUser == null) {
            JButton joinButton = new RoundedButton("Join Now", UIConstants.BUTTON_BLUE, Color.WHITE);
            joinButton.setPreferredSize(new Dimension(180, 80));
            joinButton.addActionListener(e -> createRegisterPanel());

            JButton loginButton = new RoundedButton("Log In", Color.WHITE, UIConstants.TEXT_COLOR);
            loginButton.setPreferredSize(new Dimension(150, 50));
            loginButton.addActionListener(e -> createLoginPanel());

            buttonPanel.add(joinButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(15, 0)));
            buttonPanel.add(loginButton);
        } else {
            // If logged in, show a dashboard button instead
            JButton dashboardButton = new JButton("GO TO DASHBOARD");
            dashboardButton.setBackground(UIConstants.BUTTON_BLUE);
            dashboardButton.setForeground(Color.WHITE);
            dashboardButton.setFont(UIConstants.BUTTON_FONT);
            dashboardButton.setFocusPainted(false);
            dashboardButton.setBorderPainted(false);
            dashboardButton.setPreferredSize(new Dimension(200, 40));
            dashboardButton.setMaximumSize(new Dimension(200, 40));
            // Add action for dashboard
            dashboardButton.addActionListener(e -> {
                // Navigate to dashboard
                // Add your dashboard navigation code here
            });

            buttonPanel.add(dashboardButton);
        }

        // Stats
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.X_AXIS));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        JPanel resourcesPanel = new JPanel();
        resourcesPanel.setLayout(new BoxLayout(resourcesPanel, BoxLayout.Y_AXIS));
        resourcesPanel.setBackground(Color.WHITE);

        JLabel resourcesNumber = new JLabel("30+");
        resourcesNumber.setFont(new Font("Arial", Font.BOLD, 20));
        resourcesNumber.setForeground(UIConstants.BUTTON_BLUE);
        resourcesNumber.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel resourcesLabel = new JLabel("Informative Resources");
        resourcesLabel.setFont(UIConstants.BODY_FONT);
        resourcesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resourcesPanel.add(resourcesNumber);
        resourcesPanel.add(resourcesLabel);

        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        actionsPanel.setBackground(Color.WHITE);
        actionsPanel.setBorder(new EmptyBorder(0, 50, 0, 0));

        JLabel actionsNumber = new JLabel("100+");
        actionsNumber.setFont(new Font("Arial", Font.BOLD, 20));
        actionsNumber.setForeground(UIConstants.BRIGHT_BLUE);
        actionsNumber.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel actionsLabel = new JLabel("Daily Actions");
        actionsLabel.setFont(UIConstants.BODY_FONT);
        actionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        actionsPanel.add(actionsNumber);
        actionsPanel.add(actionsLabel);

        statsPanel.add(resourcesPanel);
        statsPanel.add(actionsPanel);

        textPanel.add(welcomePanel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        textPanel.add(descriptionText);
        textPanel.add(buttonPanel);
        textPanel.add(statsPanel);

        JPanel imagePanel = new JPanel(new GridBagLayout());
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imagePanel.setBackground(Color.WHITE);

        try {
            ImageIcon homeGraphic = new ImageIcon(new ImageIcon(getClass().getResource("/com/hillcrest/visuals/homepagegraphic.png"))
                    .getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
            imageLabel.setIcon(homeGraphic);
        } catch (Exception e) {
            imageLabel.setText("Image Not Found");
            imageLabel.setFont(new Font("Arial", Font.BOLD, 14));
            imageLabel.setPreferredSize(new Dimension(300, 400));
            imageLabel.setOpaque(true);
        }

        imagePanel.add(imageLabel, new GridBagConstraints());
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.CENTER);
        setVisible(true);
        heroPanel.add(textPanel);
        heroPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        heroPanel.add(imagePanel);
        mainPanel.add(heroPanel);
    }

    private void createNavBar() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        navPanel.setBackground(Color.WHITE);
        navPanel.setMaximumSize(new Dimension(800, 70));
        navPanel.setPreferredSize(new Dimension(800, 70));

        // EcoSpark Header - only this text now
        JLabel logoLabel = new JLabel("EcoSpark");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        logoLabel.setBorder(new EmptyBorder(0, 20, 0, 0));

        // Profile icon
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        profilePanel.setBackground(Color.WHITE);

        JButton profileButton = new JButton();
        try {
            ImageIcon profileIcon = new ImageIcon(new ImageIcon(getClass().getResource("/com/hillcrest/visuals/profile.png"))
                    .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            profileButton.setIcon(profileIcon);
        } catch (Exception e) {
            profileButton.setText("Profile");
        }
        profileButton.setBorderPainted(false);
        profileButton.setContentAreaFilled(false);
        profileButton.setFocusPainted(false);

        // Add action listener to profile button
        profileButton.addActionListener(e -> {
            if (currentUser != null) {
                // User is logged in, show profile edit panel
                createProfileEditPanel();
            } else {
                // User is not logged in, redirect to login
                JOptionPane.showMessageDialog(this,
                        "Please log in to view your profile",
                        "Login Required",
                        JOptionPane.INFORMATION_MESSAGE);
                createLoginPanel();
            }
        });

        profilePanel.add(profileButton);

        // Add EcoSpark logo to the left, and the profile icon to the right
        navPanel.add(logoLabel, BorderLayout.WEST);
        navPanel.add(profilePanel, BorderLayout.EAST);

        // Add separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(230, 230, 230));

        JPanel navContainer = new JPanel();
        navContainer.setLayout(new BoxLayout(navContainer, BoxLayout.Y_AXIS));
        navContainer.add(navPanel);
        navContainer.add(separator);
        navContainer.setBackground(Color.WHITE);
        navContainer.setMaximumSize(new Dimension(800, 71));

        mainPanel.add(navContainer);
    }


    private void createProfileEditPanel() {
        getContentPane().remove(scrollPane);

        JPanel profileEditPanel = new JPanel();
        profileEditPanel.setLayout(new BoxLayout(profileEditPanel, BoxLayout.Y_AXIS));
        profileEditPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        profileEditPanel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Edit Your Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Current profile info display
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setMaximumSize(new Dimension(500, 150));

        // Name field
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(currentUser.getName());

        // Email field (read-only)
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(currentUser.getEmail());
        emailField.setEditable(false);  // Email shouldn't be changed

        // Password field
        JLabel passwordLabel = new JLabel("New Password:");
        JPasswordField passwordField = new JPasswordField();

        infoPanel.add(nameLabel);
        infoPanel.add(nameField);
        infoPanel.add(emailLabel);
        infoPanel.add(emailField);
        infoPanel.add(passwordLabel);
        infoPanel.add(passwordField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        JButton saveButton = new JButton("Save Changes");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Status label
        JLabel statusLabel = new JLabel(" ");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to panel
        profileEditPanel.add(titleLabel);
        profileEditPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        profileEditPanel.add(infoPanel);
        profileEditPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        profileEditPanel.add(buttonPanel);
        profileEditPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        profileEditPanel.add(statusLabel);

        // Save button action
        saveButton.addActionListener(e -> {
            String newName = nameField.getText();
            String newPassword = new String(passwordField.getPassword());

            if (newName.isEmpty()) {
                statusLabel.setText("Name cannot be empty");
                statusLabel.setForeground(Color.RED);
                return;
            }

            // Update user info
            currentUser.setName(newName);
            userNames.put(currentUser.getEmail(), newName);

            if (!newPassword.isEmpty()) {
                currentUser.setPassword(newPassword);
                userCredentials.put(currentUser.getEmail(), newPassword);
            }

            statusLabel.setText("Profile updated successfully!");
            statusLabel.setForeground(Color.GREEN);

            // Return to main screen after a delay
            Timer timer = new Timer(1500, event -> {
                getContentPane().remove(profileEditPanel);
                getContentPane().add(scrollPane);

                // Refresh hero section to show updated name
                mainPanel.removeAll();
                createNavBar();
                createHeroSection();
                createOfferSection();
                createAboutSection();
                createFooterSection();

                revalidate();
                repaint();
            });
            timer.setRepeats(false);
            timer.start();
        });

        // Cancel button action
        cancelButton.addActionListener(e -> {
            getContentPane().remove(profileEditPanel);
            getContentPane().add(scrollPane);
            revalidate();
            repaint();
        });

        getContentPane().add(profileEditPanel);
        revalidate();
        repaint();
    }

    private JPanel createCategoryPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel panelTitle = new JLabel(title);
        panelTitle.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 0));

        panel.add(panelTitle);
        return panel;
    }



    private JPanel createIconPanel(Color bgColor, String iconText) {
        JPanel iconPanel = new JPanel();
        iconPanel.setPreferredSize(new Dimension(50, 50));
        iconPanel.setBackground(bgColor);
        iconPanel.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 1));
        iconPanel.setLayout(new BorderLayout());

        JLabel icon = new JLabel(iconText);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setFont(new Font("Arial", Font.PLAIN, 18));

        iconPanel.add(icon, BorderLayout.CENTER);
        return iconPanel;
    }

    private void createQuizPanel() {
        JPanel quizPanel = new JPanel(new BorderLayout());
        quizPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Climate Change Knowledge Quiz");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        headerPanel.add(backButton, BorderLayout.EAST);

        JPanel quizContentPanel = new JPanel();
        quizContentPanel.setLayout(new BoxLayout(quizContentPanel, BoxLayout.Y_AXIS));

        JLabel quizInstructionsLabel = new JLabel("Test your knowledge about climate change and its impacts");
        quizInstructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quizInstructionsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel questionLabel = new JLabel("1. What is the main greenhouse gas contributing to climate change?");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JRadioButton option1 = new JRadioButton("Carbon Dioxide (CO₂)");
        JRadioButton option2 = new JRadioButton("Oxygen (O₂)");
        JRadioButton option3 = new JRadioButton("Nitrogen (N₂)");
        JRadioButton option4 = new JRadioButton("Hydrogen (H₂)");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(option1);
        buttonGroup.add(option2);
        buttonGroup.add(option3);
        buttonGroup.add(option4);

        JButton submitButton = new JButton("Submit Answer");
        submitButton.addActionListener(e -> {
            if (option1.isSelected()) {
                JOptionPane.showMessageDialog(frame, "Correct! Carbon dioxide is the primary greenhouse gas contributing to climate change.");
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect. The correct answer is Carbon Dioxide (CO₂).");
            }
        });

        questionPanel.add(questionLabel);
        questionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        questionPanel.add(option1);
        questionPanel.add(option2);
        questionPanel.add(option3);
        questionPanel.add(option4);
        questionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        questionPanel.add(submitButton);

        quizContentPanel.add(quizInstructionsLabel);
        quizContentPanel.add(questionPanel);

        JScrollPane scrollPane = new JScrollPane(quizContentPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Questions"));

        quizPanel.add(headerPanel, BorderLayout.NORTH);
        quizPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(quizPanel, "quiz");
    }

    private void createOfferSection() {
        JPanel offerPanel = new JPanel();
        offerPanel.setLayout(new BoxLayout(offerPanel, BoxLayout.Y_AXIS));
        offerPanel.setBackground(Color.WHITE);
        offerPanel.setBorder(new EmptyBorder(20, 40, 40, 40));
        offerPanel.setMaximumSize(new Dimension(900, 600)); // Keep it within the frame

        JLabel offerTitle = new JLabel("What We Offer");
        offerTitle.setFont(UIConstants.HEADER_FONT);
        offerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Container for buttons (ensures proper spacing & centering)
        JPanel offerCards = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        offerCards.setBackground(Color.WHITE);
        offerCards.setMaximumSize(new Dimension(900, 835)); // Ensures centering

        JPanel carbonFootprintButton = createImageButton(
                "/com/hillcrest/visuals/carbon_footprint_button_graphic.png",
                "Calculate carbon footprint",
                e -> {
                    if (isUserLoggedIn()) {
                        // User is logged in, proceed with the action
                        JFrame dashboardFrame = new JFrame("Dashboard");
                        dashboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        dashboardFrame.setSize(800, 600); // Adjust size as needed

                        // Pass the model and currentUser to the DashboardPanel
                        DashboardPanel dashboardPanel = new DashboardPanel(model, currentUser);
                        dashboardFrame.add(dashboardPanel);

                        dashboardFrame.setLocationRelativeTo(null); // Center the window
                        dashboardFrame.setVisible(true);
                    } else {
                        // User is not logged in, show a login prompt
                        JOptionPane.showMessageDialog(null,
                                "Please log in to calculate your carbon footprint.",
                                "Login Required",
                                JOptionPane.INFORMATION_MESSAGE);
                        createLoginPanel();
                    }
                }
        );


        JPanel quizButton = createImageButton(
                "/com/hillcrest/visuals/informative_resources_button_graphic.png",
                "Test your knowledge",
                e -> System.out.println("Informative Resources clicked!")
        );

        JPanel actionsButton = createImageButton(
                "/com/hillcrest/visuals/take_action.png",
                "Take personalized actions",
                e -> {
                    if (isUserLoggedIn()) {
                        // User is logged in, show profile edit panel
                        createCarbonFootprintPanel();
                    } else {
                        // User is not logged in, redirect to login
                        JOptionPane.showMessageDialog(this,
                                "Please log in to view your profile",
                                "Login Required",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
        );

        // Add buttons to the panel
        offerCards.add(carbonFootprintButton);
        offerCards.add(quizButton);
        offerCards.add(actionsButton);

        offerPanel.add(offerTitle);
        offerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        offerPanel.add(offerCards);

        mainPanel.add(offerPanel);
    }

    // Helper method to create an image button
    private JPanel createImageButton(String imagePath, String title, ActionListener action) {
        // Create a panel to hold the button and title
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Use BorderLayout to easily position components
        panel.setBackground(Color.WHITE); // Set the background color to white

        // Create the button with the image
        JButton button = new JButton();
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(250, 250)); // Keep buttons uniform

        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image originalImage = originalIcon.getImage();

            // Calculate the scaled width and height while maintaining aspect ratio
            int originalWidth = originalImage.getWidth(null);
            int originalHeight = originalImage.getHeight(null);

            // Calculate new dimensions based on max width or height (in this case 200px)
            int newWidth = 200;
            int newHeight = (int) ((double) originalHeight / originalWidth * newWidth);

            // Scale the image while maintaining aspect ratio
            ImageIcon icon = new ImageIcon(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Image not found: " + imagePath);
        }

        button.addActionListener(action);

        // Create a label for the title
        JLabel titleLabel = new JLabel(title, JLabel.CENTER); // Title centered below the image
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Set title font style and size
        titleLabel.setPreferredSize(new Dimension(250, 30)); // Set label size to fit title

        // Add button and title to the panel
        panel.add(button, BorderLayout.CENTER);
        panel.add(titleLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createOfferCard(String title, String imagePath, Color backgroundColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(backgroundColor);
        card.setPreferredSize(new Dimension(220, 270));
        card.setMaximumSize(new Dimension(220, 270));

        JPanel imageContainer = new JPanel();
        imageContainer.setBackground(backgroundColor);
        imageContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageContainer.setPreferredSize(new Dimension(200, 200));
        imageContainer.setMaximumSize(new Dimension(200, 200));

        JLabel imageLabel = new JLabel();
        try {
            if (imagePath != null) {
                ImageIcon cardImage = new ImageIcon(new ImageIcon(getClass().getResource(imagePath))
                        .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                imageLabel.setIcon(cardImage);
            } else if (title.equals("Informative Resources")) {
                // We need to create a placeholder for earth in hands
                // In a real app, you would have an image for this
                imageLabel.setText("Earth Image");
                imageLabel.setForeground(UIConstants.PRIMARY_COLOR);
                imageLabel.setFont(UIConstants.BODY_FONT);
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setPreferredSize(new Dimension(150, 150));
            }
        } catch (Exception e) {
            imageLabel.setText(title + " Icon");
            imageLabel.setForeground(backgroundColor.equals(UIConstants.PRIMARY_COLOR) ? Color.WHITE : UIConstants.PRIMARY_COLOR);
            imageLabel.setFont(UIConstants.BODY_FONT);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setPreferredSize(new Dimension(150, 150));
        }

        imageContainer.add(imageLabel);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(backgroundColor.equals(UIConstants.PRIMARY_COLOR) ? Color.WHITE : UIConstants.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(20, 0, 0, 0));

        card.add(Box.createVerticalGlue());
        card.add(imageContainer);
        card.add(titleLabel);
        card.add(Box.createVerticalGlue());

        return card;
    }

    private JPanel createOfferCardButton(String title, String imagePath, Color backgroundColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(backgroundColor);
        card.setPreferredSize(new Dimension(220, 270));
        card.setMaximumSize(new Dimension(220, 270));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering

        // Make the entire card clickable
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (title.equals("Personalized Actions")) {
                    cardLayout.show(mainPanel, "tasks");
                } else if (title.equals("Carbon Footprint Calculator")) {
                    // Navigate to the carbon footprint panel (if implemented)
                    System.out.println("Carbon Footprint Calculator clicked");
                }
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(backgroundColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(backgroundColor);
            }
        });

        JPanel imageContainer = new JPanel();
        imageContainer.setBackground(backgroundColor);
        imageContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageContainer.setPreferredSize(new Dimension(200, 200));
        imageContainer.setMaximumSize(new Dimension(200, 200));
        imageContainer.setOpaque(false); // Make it transparent to show the panel's background color

        JLabel imageLabel = new JLabel();
        try {
            if (imagePath != null) {
                ImageIcon cardImage = new ImageIcon(new ImageIcon(getClass().getResource(imagePath))
                        .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                imageLabel.setIcon(cardImage);
            }
        } catch (Exception e) {
            imageLabel.setText(title + " Icon");
            imageLabel.setForeground(Color.WHITE);
            imageLabel.setFont(UIConstants.BODY_FONT);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setPreferredSize(new Dimension(150, 150));
        }

        imageContainer.add(imageLabel);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(20, 0, 0, 0));

        card.add(Box.createVerticalGlue());
        card.add(imageContainer);
        card.add(titleLabel);
        card.add(Box.createVerticalGlue());

        return card;
    }

    private void createAboutSection() {
        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.X_AXIS));
        aboutPanel.setBackground(Color.WHITE);
        aboutPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        aboutPanel.setMaximumSize(new Dimension(800, 400));

        // Text section
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel learnMore = new JLabel("LEARN MORE");
        learnMore.setFont(new Font("Arial", Font.BOLD, 14));
        learnMore.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel aboutLabel = new JLabel("About ");
        aboutLabel.setFont(UIConstants.HEADER_FONT);

        JLabel ecospark = new JLabel("EcoSpark");
        ecospark.setFont(UIConstants.HEADER_FONT);
        ecospark.setForeground(UIConstants.ACCENT_COLOR);

        titlePanel.add(aboutLabel);
        titlePanel.add(ecospark);

        JTextArea aboutText1 = new JTextArea(
                "Welcome to EcoSpark! We believe that every " +
                        "person has the power to be an everyday hero for " +
                        "our planet."
        );
        configureTextArea(aboutText1);

        JTextArea aboutText2 = new JTextArea(
                "Our mission is to provide you with the tools, " +
                        "knowledge, and support you need to make a real " +
                        "difference in your daily life."
        );
        configureTextArea(aboutText2);

        JTextArea aboutText3 = new JTextArea(
                "We strive to provide our users with the resources " +
                        "they need to contribute to lessening the effects of " +
                        "global warming one action at a time."
        );
        configureTextArea(aboutText3);

        textPanel.add(learnMore);
        textPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        textPanel.add(titlePanel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        textPanel.add(aboutText1);
        textPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        textPanel.add(aboutText2);
        textPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        textPanel.add(aboutText3);

        // Image section
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(UIConstants.PRIMARY_COLOR);
        imagePanel.setPreferredSize(new Dimension(250, 250));
        imagePanel.setMaximumSize(new Dimension(250, 250));
        imagePanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel imageLabel = new JLabel();
        try {
            ImageIcon aboutGraphic = new ImageIcon(new ImageIcon(getClass().getResource("/com/hillcrest/visuals/about_ecospark_graphic.png"))
                    .getImage().getScaledInstance(200, 200, Image.SCALE_AREA_AVERAGING));
            imageLabel.setIcon(aboutGraphic);
        } catch (Exception e) {
            imageLabel.setText("About Graphic");
            imageLabel.setFont(UIConstants.BODY_FONT);
            imageLabel.setForeground(Color.WHITE);
        }

        imagePanel.add(imageLabel);

        aboutPanel.add(textPanel);
        aboutPanel.add(Box.createHorizontalGlue());
        aboutPanel.add(imagePanel);

        mainPanel.add(aboutPanel);
    }

    private void configureTextArea(JTextArea textArea) {
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setFont(UIConstants.BODY_FONT);
        textArea.setBackground(Color.WHITE);
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        textArea.setMaximumSize(new Dimension(400, 70));
    }

    private void createFooterSection() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        footerPanel.setMaximumSize(new Dimension(800, 200));

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(230, 230, 230));
        separator.setMaximumSize(new Dimension(720, 1));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Contact information
        JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));
        contactPanel.setBackground(Color.WHITE);

        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setFont(new Font("Arial", Font.BOLD, 14));
        contactLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel emailLabel = new JLabel("ecospark@gmail.com");
        emailLabel.setFont(UIConstants.BODY_FONT);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailLabel.setBorder(new EmptyBorder(5, 0, 15, 0));

        JLabel inquiriesLabel = new JLabel("General Inquiries:");
        inquiriesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inquiriesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel phoneLabel = new JLabel("385-371-9236");
        phoneLabel.setFont(UIConstants.BODY_FONT);
        phoneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contactPanel.add(contactLabel);
        contactPanel.add(emailLabel);
        contactPanel.add(inquiriesLabel);
        contactPanel.add(phoneLabel);

        // Quick links
        JPanel linksPanel = new JPanel();
        linksPanel.setLayout(new BoxLayout(linksPanel, BoxLayout.Y_AXIS));
        linksPanel.setBackground(Color.WHITE);
        linksPanel.setBorder(new EmptyBorder(0, 100, 0, 0));

        JLabel linksLabel = new JLabel("Quick Links:");
        linksLabel.setFont(new Font("Arial", Font.BOLD, 14));
        linksLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] linkLabels = {"Home", "Contact", "About Us", "Courses", "Resources"};
        for (String label : linkLabels) {
            JLabel link = new JLabel(label);
            link.setFont(UIConstants.BODY_FONT);
            link.setForeground(UIConstants.TEXT_COLOR);
            link.setAlignmentX(Component.LEFT_ALIGNMENT);
            link.setBorder(new EmptyBorder(5, 0, 5, 0));
            link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            linksPanel.add(link);
        }

        // Social Media Icons
        JPanel socialPanel = new JPanel();
        socialPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        socialPanel.setBackground(Color.WHITE);

        String[] socialIcons = {"facebook", "instagram", "linkedin"};
        for (String social : socialIcons) {
            ImageIcon icon = new ImageIcon( "/src/facebook.png");
            JButton socialButton = new JButton(icon);

            socialButton.setFocusPainted(false);
            socialButton.setContentAreaFilled(false);
            socialButton.setBorderPainted(false);
            socialButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            socialPanel.add(socialButton);
        }

        contentPanel.add(contactPanel);
        contentPanel.add(linksPanel);
        contentPanel.add(Box.createHorizontalGlue());
        contentPanel.add(socialPanel);

        // Copyright
        JPanel copyrightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        copyrightPanel.setBackground(Color.WHITE);

        JLabel copyrightLabel = new JLabel("© 2025 EcoSpark. All rights reserved.");
        copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        copyrightPanel.add(copyrightLabel);

        footerPanel.add(separator);
        footerPanel.add(contentPanel);
        footerPanel.add(copyrightPanel);

        mainPanel.add(footerPanel);
    }
}