import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class EcoSparkApp extends JFrame {

    private Profile currentUser;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JScrollPane scrollPane;
    private JPanel tasksListPanel;
    private JPanel frame;
    private TaskManager taskManager;
    private JFrame dashboardFrame;

    CarbonFootprintCalculator calculator = new CarbonFootprintCalculator();

    // Login Panels
    private JPanel loginPanel;
    private JPanel registerPanel;
    private JPanel dashboardPanel;
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
        createTaskPanel();

        // Create scroll pane and add main panel to it
        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Prevent horizontal scrolling

        add(scrollPane);
        setVisible(true);
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
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create tabbed pane for different categories
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.WHITE);

        // Add tabs that match the UI
        tabbedPane.addTab("General Usages", createGeneralPanel());
        tabbedPane.addTab("Transportation", createTransportationPanel());
        tabbedPane.addTab("Food Consumption", createFoodPanel());
        tabbedPane.addTab("Energy Usage", createEnergyPanel());
        tabbedPane.addTab("Waste & Other", createWastePanel());

        // Add top components to main panel
        carbonFootprintPanel.add(titleLabel);
        carbonFootprintPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        carbonFootprintPanel.add(tabbedPane);
        tabbedPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        // Submit button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        JButton submitButton = new JButton("SUBMIT");
        submitButton.setBackground(new Color(65, 105, 225));
        submitButton.setForeground(Color.WHITE);
        submitButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(submitButton);

        // Add submit button with some space
        carbonFootprintPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        carbonFootprintPanel.add(buttonPanel);

        // Results section
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.WHITE);

        // Results title
        JLabel footprintLabel = new JLabel("Your Footprint");
        footprintLabel.setFont(new Font("Arial", Font.BOLD, 24));
        footprintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Breakdown section
        JPanel breakdownPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        breakdownPanel.setBackground(Color.WHITE);

        // Add results components
        resultsPanel.add(footprintLabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        resultsPanel.add(breakdownPanel);

        // Add results to main panel
        carbonFootprintPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        carbonFootprintPanel.add(resultsPanel);

        // Create a scroll pane for the carbon footprint panel
        JScrollPane carbonScrollPane = new JScrollPane(carbonFootprintPanel);
        carbonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        carbonScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        carbonScrollPane.setBorder(null);
        carbonScrollPane.setBackground(Color.WHITE);

        // UI elements for inputs (Example: Dropdowns, Text fields for actual input)
        JComboBox<String> countryComboBox = new JComboBox<>(new String[]{"USA", "India", "China"});
        JTextField carMilesField = new JTextField(5);
        JComboBox<String> vehicleTypeComboBox = new JComboBox<>(new String[]{"SUV", "Sedan", "Truck"});
        JComboBox<String> fuelTypeComboBox = new JComboBox<>(new String[]{"Diesel", "Electric", "Gas"});
        JTextField flightsPerYearField = new JTextField(5);
        JComboBox<String> dietTypeComboBox = new JComboBox<>(new String[]{"Vegan", "Vegetarian", "Omnivore"});
        JTextField meatConsumptionField = new JTextField(5);
        JTextField electricityUsageField = new JTextField(5);
        JComboBox<String> energySourceComboBox = new JComboBox<>(new String[]{"Coal", "Solar", "Wind"});
        JTextField wasteProducedField = new JTextField(5);

        // Arrange inputs (replace with appropriate layout panels)
        JPanel inputsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputsPanel.add(new JLabel("Country:"));
        inputsPanel.add(countryComboBox);
        inputsPanel.add(new JLabel("Car Miles per Week:"));
        inputsPanel.add(carMilesField);
        inputsPanel.add(new JLabel("Vehicle Type:"));
        inputsPanel.add(vehicleTypeComboBox);
        inputsPanel.add(new JLabel("Fuel Type:"));
        inputsPanel.add(fuelTypeComboBox);
        inputsPanel.add(new JLabel("Flights per Year:"));
        inputsPanel.add(flightsPerYearField);
        inputsPanel.add(new JLabel("Diet Type:"));
        inputsPanel.add(dietTypeComboBox);
        inputsPanel.add(new JLabel("Meat Consumption (meals per week):"));
        inputsPanel.add(meatConsumptionField);
        inputsPanel.add(new JLabel("Electricity Usage (kWh):"));
        inputsPanel.add(electricityUsageField);
        inputsPanel.add(new JLabel("Energy Source:"));
        inputsPanel.add(energySourceComboBox);
        inputsPanel.add(new JLabel("Waste Produced (kg):"));
        inputsPanel.add(wasteProducedField);

        carbonFootprintPanel.add(inputsPanel);

        // Submit button action listener
        submitButton.addActionListener(e -> {
            // Retrieve actual input values from the UI components
            String country = (String) countryComboBox.getSelectedItem();
            int carMilesPerWeek = Integer.parseInt(carMilesField.getText());
            String vehicleType = (String) vehicleTypeComboBox.getSelectedItem();
            String fuelType = (String) fuelTypeComboBox.getSelectedItem();
            int flightsPerYear = Integer.parseInt(flightsPerYearField.getText());
            String dietType = (String) dietTypeComboBox.getSelectedItem();
            int meatConsumption = Integer.parseInt(meatConsumptionField.getText());
            double electricityUsage = Double.parseDouble(electricityUsageField.getText());
            String energySource = (String) energySourceComboBox.getSelectedItem();
            double wasteProduced = Double.parseDouble(wasteProducedField.getText());

            // Calculate total footprint
            double totalFootprint = CarbonFootprintCalculator.calculateTotalFootprint(
                    country, carMilesPerWeek, vehicleType, fuelType, flightsPerYear,
                    dietType, meatConsumption, electricityUsage, energySource, wasteProduced);

            // Update the results breakdown
            Map<String, Double> footprintData = new HashMap<>();
            footprintData.put("General Usage", CarbonFootprintCalculator.calculateGeneralFootprint(country));
            footprintData.put("Transportation", CarbonFootprintCalculator.calculateTransportationFootprint(carMilesPerWeek, vehicleType, fuelType, flightsPerYear));
            footprintData.put("Food Consumption", CarbonFootprintCalculator.calculateFoodFootprint(dietType, meatConsumption));
            footprintData.put("Energy Usage", CarbonFootprintCalculator.calculateEnergyFootprint(electricityUsage, energySource));
            footprintData.put("Waste Production", CarbonFootprintCalculator.calculateWasteFootprint(wasteProduced));

            // Show the results in the breakdown panel
            breakdownPanel.removeAll();
            for (Map.Entry<String, Double> entry : footprintData.entrySet()) {
                JLabel categoryLabel = new JLabel(entry.getKey() + ": " + String.format("%.2f", entry.getValue()) + " tons");
                categoryLabel.setOpaque(true);
                categoryLabel.setBackground(getCategoryColor(entry.getKey()));  // Implement this method to get category colors
                categoryLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                breakdownPanel.add(categoryLabel);
            }

            // Show results panel
            resultsPanel.setVisible(true);
            JScrollBar vertical = carbonScrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
            revalidate();
            repaint();
        });

        // Initially hide results until submission
        resultsPanel.setVisible(false);

        // Back button action listener
        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(carbonFootprintPanel);
                getContentPane().add(scrollPane);
                revalidate();
                repaint();
            }
        });

        getContentPane().add(carbonScrollPane);
        setVisible(true);
    }

    private JPanel createWastePanel() {
        // Create a panel for waste input
        JPanel wastePanel = new JPanel();
        wastePanel.setLayout(new BoxLayout(wastePanel, BoxLayout.Y_AXIS));
        wastePanel.setBackground(Color.WHITE);
        wastePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title for waste section
        JLabel wasteTitle = new JLabel("Waste Produced");
        wasteTitle.setFont(new Font("Arial", Font.BOLD, 18));
        wasteTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        wastePanel.add(wasteTitle);

        // Description of waste section
        JLabel wasteDescription = new JLabel("Please enter the amount of waste you produce per week (in kilograms):");
        wasteDescription.setFont(new Font("Arial", Font.PLAIN, 14));
        wasteDescription.setAlignmentX(Component.LEFT_ALIGNMENT);
        wastePanel.add(wasteDescription);

        // Text field for user input of waste produced
        JTextField wasteProducedField = new JTextField(10);
        wasteProducedField.setMaximumSize(new Dimension(200, 30));  // Set field width
        wasteProducedField.setFont(new Font("Arial", Font.PLAIN, 14));
        wastePanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Spacer
        wastePanel.add(wasteProducedField);

        // Validation for waste input: Ensure that only numerical values are accepted
        wasteProducedField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = wasteProducedField.getText();
                try {
                    Double.parseDouble(text);  // Validate input
                } catch (NumberFormatException ex) {
                    // Optional: You can show a message or highlight the input field when the input is invalid
                }
            }
        });

        return wastePanel;
    }


    private Color getCategoryColor(String category) {
        switch (category) {
            case "General":
                return new Color(65, 105, 225); // Royal Blue
            case "Transportation":
                return new Color(220, 20, 60); // Crimson
            case "Food":
                return new Color(34, 139, 34); // Forest Green
            case "Energy":
                return new Color(218, 165, 32); // Goldenrod
            case "Other":
                return new Color(41, 128, 185); // Steel Blue
            default:
                return Color.LIGHT_GRAY;
        }
    }

    // Helper methods for creating tab panels
    private JPanel createGeneralPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240)); // Light gray background from image
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Country selection
        JLabel countryLabel = new JLabel("What country do you live in?");
        JComboBox<String> countrySelector = new JComboBox<>(new String[]{"Select"});
        countrySelector.setMaximumSize(new Dimension(300, 30));

        // Add components with spacing
        panel.add(countryLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(countrySelector);

        return panel;
    }

    private JPanel createTransportationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Car miles
        JLabel carLabel = new JLabel("Car Miles Driven (per week):");
        JTextField carField = new JTextField(10);
        carField.setMaximumSize(new Dimension(300, 30));

        // Add components with spacing
        panel.add(carLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(carField);

        return panel;
    }

    private JPanel createFoodPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Diet type
        JLabel dietLabel = new JLabel("What type of diet do you follow?");
        String[] dietOptions = {"Select", "Omnivore", "Pescatarian", "Vegetarian", "Vegan"};
        JComboBox<String> dietSelector = new JComboBox<>(dietOptions);
        dietSelector.setMaximumSize(new Dimension(300, 30));

        // Add components with spacing
        panel.add(dietLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(dietSelector);

        return panel;
    }

    private JPanel createEnergyPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Electricity usage
        JLabel electricityLabel = new JLabel("Electricity Usage (kWh/month):");
        JTextField electricityField = new JTextField(10);
        electricityField.setMaximumSize(new Dimension(300, 30));

        // Add components with spacing
        panel.add(electricityLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(electricityField);

        return panel;
    }

    private JPanel createOtherPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Waste produced
        JLabel wasteLabel = new JLabel("Waste Produced (kg/week):");
        JTextField wasteField = new JTextField(10);
        wasteField.setMaximumSize(new Dimension(300, 30));

        // Add components with spacing
        panel.add(wasteLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(wasteField);

        return panel;
    }

        private void openDashboardWindow() {
        // Create a new frame for the dashboard
        JFrame dashboardFrame = new JFrame("Dashboard");
        dashboardFrame.setSize(800, 600);
        dashboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dashboardFrame.setLocationRelativeTo(null); // Center the window

        // Call your existing createDashboardPanel() method
        JPanel dashboardPanel = new JPanel();
        createDashboardPanel(dashboardPanel); // Populate dashboardPanel

        // Add the panel to the new frame and show it
        dashboardFrame.add(dashboardPanel);
        dashboardFrame.setVisible(true);
    }



    private void createDashboardPanel(JPanel dashboardPanel) {

        dashboardPanel.setLayout(new BorderLayout(0, 20));
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dashboardPanel.setBackground(Color.WHITE);
        dashboardPanel.setPreferredSize(new Dimension(700, dashboardPanel.getPreferredSize().height));


        // Check if currentUser is initialized
        if (currentUser == null) {
            // Show error or login panel instead
            JLabel errorLabel = new JLabel("Please log in to view your dashboard", JLabel.CENTER);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 18));
            dashboardPanel.add(errorLabel, BorderLayout.CENTER);
            mainPanel.add(dashboardPanel, "dashboard");
            return;
        }

        // Check if taskManager is initialized
        if (taskManager == null) {
            taskManager = new TaskManager();
        }

        // Header
        JPanel headerPanel = createHeaderPanel();

        // Dashboard Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 30));
        contentPanel.setBackground(Color.WHITE);

        // Dashboard Title
        JLabel dashboardTitle = new JLabel("My Dashboard");
        dashboardTitle.setFont(new Font("Arial", Font.BOLD, 28));
        contentPanel.add(dashboardTitle, BorderLayout.NORTH);

        // Task Sections
        JPanel taskSectionsPanel = new JPanel();
        taskSectionsPanel.setLayout(new BorderLayout(20, 20));
        taskSectionsPanel.setBackground(Color.WHITE);

        // Tabs and badges panel
        JPanel tabsPanel = new JPanel();
        tabsPanel.setLayout(new BorderLayout());
        tabsPanel.setBackground(Color.WHITE);

        // Tabs
        JPanel taskTabs = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        taskTabs.setBackground(Color.WHITE);

        JLabel allTasksTab = new JLabel("All Tasks");
        allTasksTab.setFont(new Font("Arial", Font.BOLD, 14));
        allTasksTab.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

        JLabel favoriteTab = new JLabel("Favorite");
        favoriteTab.setFont(new Font("Arial", Font.PLAIN, 14));

        // Make tabs clickable
        allTasksTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                allTasksTab.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
                allTasksTab.setFont(new Font("Arial", Font.BOLD, 14));
                favoriteTab.setBorder(null);
                favoriteTab.setFont(new Font("Arial", Font.PLAIN, 14));
                refreshDashboard(false);
            }
        });

        favoriteTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                favoriteTab.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
                favoriteTab.setFont(new Font("Arial", Font.BOLD, 14));
                allTasksTab.setBorder(null);
                allTasksTab.setFont(new Font("Arial", Font.PLAIN, 14));
                refreshDashboard(true);
            }
        });

        taskTabs.add(allTasksTab);
        taskTabs.add(favoriteTab);

        // Badges Label
        JLabel badgesLabel = new JLabel("Badges");
        badgesLabel.setFont(new Font("Arial", Font.BOLD, 14));

        tabsPanel.add(taskTabs, BorderLayout.WEST);
        tabsPanel.add(badgesLabel, BorderLayout.EAST);
        tabsPanel.add(new JSeparator(), BorderLayout.SOUTH);

        // Main tasks panel
        JPanel mainTasksPanel = new JPanel();
        mainTasksPanel.setLayout(new BorderLayout(20, 0));
        mainTasksPanel.setBackground(Color.WHITE);

        // Tasks List Panel with scroll capability
        JPanel tasksListPanel = new JPanel();
        tasksListPanel.setLayout(new BoxLayout(tasksListPanel, BoxLayout.Y_AXIS));
        tasksListPanel.setBackground(Color.WHITE);

        // Wrap in scroll pane for many tasks
        JScrollPane scrollPane = new JScrollPane(tasksListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Color.WHITE);

        try {
            // Safely fetch tasks from TaskManager
            List<Task> taskLibrary = taskManager.getTaskLibrary();

            if (taskLibrary != null && !taskLibrary.isEmpty()) {
                // Current Tasks
                JPanel currentTasksPanel = createTasksSection("Daily Tasks", taskLibrary.stream()
                        .filter(task -> "daily".equals(task.getType()))
                        .limit(3)
                        .collect(Collectors.toList()));

                // Explore Tasks Section
                JPanel exploreSection = createTasksSection("Explore Tasks", taskLibrary.stream()
                        .filter(task -> !"daily".equals(task.getType()))
                        .limit(3)
                        .collect(Collectors.toList()));

                tasksListPanel.add(currentTasksPanel);
                tasksListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                tasksListPanel.add(exploreSection);
            } else {
                // Handle empty task library
                JLabel noTasksLabel = new JLabel("No tasks available", JLabel.CENTER);
                noTasksLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                tasksListPanel.add(noTasksLabel);
            }
        } catch (Exception e) {
            // Log the error
            System.err.println("Error loading tasks: " + e.getMessage());
            e.printStackTrace();

            // Show error message in the UI
            JLabel errorLabel = new JLabel("Error loading tasks. Please try again.", JLabel.CENTER);
            errorLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            tasksListPanel.add(errorLabel);
        }

        // Add a "Personalized Actions" button that takes you to the tasks page
        JPanel actionsButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionsButtonPanel.setBackground(Color.WHITE);

        JButton personalizedActionsButton = new JButton("PERSONALIZED ACTIONS");
        personalizedActionsButton.setBackground(UIConstants.BUTTON_BLUE);
        personalizedActionsButton.setForeground(Color.WHITE);
        personalizedActionsButton.setFont(UIConstants.BUTTON_FONT);
        personalizedActionsButton.setFocusPainted(false);
        personalizedActionsButton.setBorderPainted(false);
        personalizedActionsButton.addActionListener(e -> {
            try {
                refreshTasksPanel(); // Refresh tasks before showing the panel
                cardLayout.show(mainPanel, "tasks");
            } catch (Exception ex) {
                // Show error dialog if something goes wrong
                JOptionPane.showMessageDialog(dashboardPanel,
                        "Error loading tasks page: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        actionsButtonPanel.add(personalizedActionsButton);
        tasksListPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        tasksListPanel.add(actionsButtonPanel);

        // Badges Panel
        JPanel badgesPanel = createBadgesPanel();

        mainTasksPanel.add(scrollPane, BorderLayout.CENTER);
        mainTasksPanel.add(badgesPanel, BorderLayout.EAST);

        taskSectionsPanel.add(tabsPanel, BorderLayout.NORTH);
        taskSectionsPanel.add(mainTasksPanel, BorderLayout.CENTER);

        contentPanel.add(taskSectionsPanel, BorderLayout.CENTER);

        // Add footer separator
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        footerPanel.add(new JSeparator());

        // Main layout assembly
        dashboardPanel.add(headerPanel, BorderLayout.NORTH);
        dashboardPanel.add(contentPanel, BorderLayout.CENTER);
        dashboardPanel.add(footerPanel, BorderLayout.SOUTH);

    }

    // Helper method to create a task section with title and tasks
    private JPanel createTasksSection(String title, List<Task> tasks) {
        JPanel section = new JPanel();
        section.setLayout(new BorderLayout(0, 15));
        section.setBackground(Color.WHITE);

        JLabel sectionTitle = new JLabel(title);
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 22));
        section.add(sectionTitle, BorderLayout.NORTH);

        if (tasks.isEmpty()) {
            JLabel noTasksLabel = new JLabel("No " + title.toLowerCase() + " available", JLabel.CENTER);
            noTasksLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            section.add(noTasksLabel, BorderLayout.CENTER);
            return section;
        }

        JPanel taskCards = new JPanel();
        taskCards.setLayout(new GridLayout(1, Math.min(tasks.size(), 3), 10, 0));
        taskCards.setBackground(Color.WHITE);

        // Create icon background colors based on task type
        Map<String, Color> typeColors = new HashMap<>();
        typeColors.put("daily", new Color(200, 230, 200));
        typeColors.put("weekly", new Color(255, 220, 150));
        typeColors.put("monthly", new Color(200, 220, 240));
        typeColors.put("one-time", new Color(230, 200, 230));

        // Create emoji icons based on task title/keywords
        Map<String, String> keywordEmojis = new HashMap<>();
        keywordEmojis.put("recycle", "♻️");
        keywordEmojis.put("water", "💧");
        keywordEmojis.put("light", "💡");
        keywordEmojis.put("plant", "🌱");
        keywordEmojis.put("tree", "🌳");
        keywordEmojis.put("bike", "🚲");
        keywordEmojis.put("walk", "🚶");
        keywordEmojis.put("bag", "🛍️");
        // Default emoji
        String defaultEmoji = "🌐";

        for (Task task : tasks) {
            // Find appropriate emoji
            String emoji = defaultEmoji;
            for (Map.Entry<String, String> entry : keywordEmojis.entrySet()) {
                if (task.getTitle().toLowerCase().contains(entry.getKey())) {
                    emoji = entry.getValue();
                    break;
                }
            }

            // Get color based on type, default to light gray
            Color iconColor = typeColors.getOrDefault(task.getType(), new Color(220, 220, 220));

            // Create task card with proper completion status
            String taskKey = currentUser.getEmail() + "_" + task.getId();
            boolean isCompleted = taskManager.isTaskCompleted(taskKey);

            JPanel taskCard = createTaskCard(
                    task.getTitle(),
                    task.getDescription(),
                    isCompleted,
                    createIconPanel(iconColor, emoji)
            );

            // Make task card clickable to mark as complete
            taskCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!isCompleted) {
                        int result = JOptionPane.showConfirmDialog(
                                taskCard,
                                "Mark this task as complete?",
                                "Complete Task",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (result == JOptionPane.YES_OPTION) {
                            taskManager.completeTask(taskKey, currentUser);
                            refreshDashboard(false);
                        }
                    }
                }
            });

            taskCards.add(taskCard);
        }

        section.add(taskCards, BorderLayout.CENTER);
        return section;
    }

    // Helper method to refresh the dashboard
    private void refreshDashboard(boolean favoritesOnly) {
        // Remove the current dashboard panel
        Component[] components = mainPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i].getName() != null && components[i].getName().equals("dashboard")) {
                mainPanel.remove(i);
                break;
            }
        }

        // Show the dashboard
        cardLayout.show(mainPanel, "dashboard");
        mainPanel.revalidate();
        mainPanel.repaint();
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
        getContentPane().remove(scrollPane);  // Remove the current screen

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
            // Remove login panel and restore main panel
            getContentPane().remove(registerPanel);
            getContentPane().add(scrollPane);
            revalidate();
            repaint();
        });

        // Add the panel to the content pane
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

        JLabel logoLabel = new JLabel("EcoSpark");
        logoLabel.setFont(UIConstants.BODY_FONT);

        logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        logoLabel.setBorder(new EmptyBorder(0, 20, 0, 0));

        // Navigation items
        JPanel navItems = new JPanel();
        navItems.setLayout(new FlowLayout(FlowLayout.CENTER));
        navItems.setBackground(Color.WHITE);

        String[] navLabels = {"Dashboard", "Contact", "About", "My Carbon Footprint", "Tasks"};
        for (String label : navLabels) {
            JButton navButton = new JButton(label);
            navButton.setBorderPainted(false);
            navButton.setContentAreaFilled(false);
            navButton.setFocusPainted(false);
            navButton.setFont(UIConstants.BODY_FONT);
            navButton.setForeground(UIConstants.TEXT_COLOR);
            navItems.add(navButton);
        }

        // Profile icon
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        profilePanel.setBackground(Color.WHITE);

        JButton profileButton = new JButton();
        try {
            ImageIcon profileIcon = new ImageIcon(new ImageIcon(getClass().getResource("/com/hillcrest/visuals/profile_icon.png"))
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

        navPanel.add(logoLabel, BorderLayout.WEST);
        navPanel.add(navItems, BorderLayout.CENTER);
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

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel logoLabel = new JLabel("EcoSpark");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        navPanel.setBackground(Color.WHITE);

        String[] navItems = {"Dashboard", "Contact", "About", "My Carbon Footprint", "Tasks"};
        for (String item : navItems) {
            JLabel navItem = new JLabel(item);
            navItem.setFont(new Font("Arial", Font.PLAIN, 14));

            // Make the Dashboard and Tasks labels clickable
            if (item.equals("Dashboard") || item.equals("Tasks")) {
                navItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
                navItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (item.equals("Dashboard")) {
                            cardLayout.show(mainPanel, "dashboard");
                        } else if (item.equals("Tasks")) {
                            refreshTasksPanel();
                            cardLayout.show(mainPanel, "tasks");
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        navItem.setForeground(UIConstants.BUTTON_BLUE);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        navItem.setForeground(Color.BLACK);
                    }
                });
            }

            navPanel.add(navItem);
        }

        // Profile icon
        JLabel profileIcon = new JLabel("👤");
        profileIcon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        profileIcon.setPreferredSize(new Dimension(30, 30));
        profileIcon.setHorizontalAlignment(SwingConstants.CENTER);
        navPanel.add(profileIcon);

        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(navPanel, BorderLayout.EAST);
        headerPanel.add(new JSeparator(), BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createBadgesPanel() {
        JPanel badgesPanel = new JPanel();
        badgesPanel.setPreferredSize(new Dimension(250, 0));
        badgesPanel.setBackground(Color.LIGHT_GRAY);
        badgesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create a grid layout for badges
        badgesPanel.setLayout(new GridLayout(4, 3, 10, 10));

        // Colors for badges
        Color[] badgeColors = {
                new Color(220, 220, 255),  // Light blue
                new Color(200, 240, 200),  // Light green
                new Color(255, 240, 200),  // Light yellow
                new Color(255, 200, 200),  // Light red
                new Color(240, 200, 240),  // Light purple
                new Color(200, 240, 240),  // Light cyan
                new Color(255, 220, 180),  // Light orange
                new Color(220, 255, 220),  // Pale green
                new Color(255, 200, 220),  // Light pink
                new Color(220, 220, 220),  // Light gray
                new Color(255, 240, 180),  // Pale yellow
                new Color(200, 220, 255)   // Pale blue
        };

        // Icons for badges
        String[] icons = {
                "🤝", "🧩", "🧠", "⚙️", "📚", "❓", "🌱", "🏆", "🔄"
        };

        // Add 9 badges
        for (int i = 0; i < 9; i++) {
            JPanel badge = new JPanel();
            badge.setBackground(badgeColors[i]);
            badge.setBorder(BorderFactory.createLineBorder(badgeColors[i].darker(), 1));

            JLabel iconLabel = new JLabel(icons[i]);
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            iconLabel.setFont(new Font("Arial", Font.PLAIN, 18));

            badge.add(iconLabel);
            badgesPanel.add(badge);
        }

        return badgesPanel;
    }

    // Implementation of the Tasks panel that users navigate to from the Dashboard
    private void createTasksPanel() {
        JPanel tasksPanel = new JPanel(new BorderLayout());
        tasksPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tasksPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Eco-Friendly Tasks");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBackground(UIConstants.BUTTON_BLUE);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        headerPanel.add(backButton, BorderLayout.EAST);

        // User stats panel (points, completed tasks, etc.)
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel pointsLabel = new JLabel("Your Points: " + currentUser.getPoints());
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pointsLabel.setForeground(UIConstants.ACCENT_COLOR);

        JLabel tasksCompletedLabel = new JLabel(" | Tasks Completed: " +
                countCompletedTasks());
        tasksCompletedLabel.setFont(new Font("Arial", Font.BOLD, 14));

        statsPanel.add(pointsLabel);
        statsPanel.add(tasksCompletedLabel);

        // Combine header elements
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(statsPanel, BorderLayout.CENTER);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);

        tasksListPanel = new JPanel();
        tasksListPanel.setLayout(new BoxLayout(tasksListPanel, BoxLayout.Y_AXIS));
        tasksListPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tasksListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        tasksPanel.add(topPanel, BorderLayout.NORTH);
        tasksPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tasksPanel, "tasks");
    }

    private void refreshTasksPanel() {
        if (tasksListPanel == null) {
            System.err.println("Error: tasksListPanel is not initialized.");
            return;
        }

        tasksListPanel.removeAll();

        // Get tasks from Task class library
        List<Task> taskLibrary = Task.createTaskLibrary();
        if (taskLibrary == null || taskLibrary.isEmpty()) {
            System.err.println("Error: Task library is null or empty.");
            return;
        }

        // Create category panels
        JPanel dailyTasksPanel = createCategoryPanel("Daily Actions");
        JPanel weeklyTasksPanel = createCategoryPanel("Weekly Challenges");

        // Use an iterator to safely process tasks without direct indexing
        Iterator<Task> taskIterator = taskLibrary.iterator();
        int count = 0;

        while (taskIterator.hasNext()) {
            Task task = taskIterator.next();
            JPanel taskItem = createTaskItem(task);

            if (taskItem != null) {
                if (count < 5) {
                    dailyTasksPanel.add(taskItem);
                } else {
                    weeklyTasksPanel.add(taskItem);
                }
            } else {
                System.err.println("Warning: Failed to create task item for " + task.getTitle());
            }
            count++;
        }

        // Add category panels to the main tasks panel
        tasksListPanel.add(dailyTasksPanel);
        tasksListPanel.add(Box.createVerticalStrut(20)); // Adds spacing between sections
        tasksListPanel.add(weeklyTasksPanel);

        // Refresh the UI
        tasksListPanel.revalidate();
        tasksListPanel.repaint();
    }

    /**
     * Utility method to create a category panel with a title.
     */
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

    private JPanel createTaskCard(String title, String description, boolean completed, JPanel iconPanel) {
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BorderLayout(10, 0));
        taskPanel.setBackground(Color.LIGHT_GRAY);
        taskPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(5, 5));
        contentPanel.setBackground(Color.LIGHT_GRAY);

        // Checkbox with text
        JPanel checkboxPanel = new JPanel(new BorderLayout(10, 0));
        checkboxPanel.setBackground(Color.LIGHT_GRAY);

        JCheckBox checkbox = new JCheckBox();
        checkbox.setSelected(completed);
        checkbox.setBackground(Color.LIGHT_GRAY);

        if (completed) {
            JLabel checkmark = new JLabel("✓");
            checkmark.setForeground(UIConstants.GREEN_CHECK);
            checkmark.setFont(new Font("Arial", Font.BOLD, 14));
            checkboxPanel.add(checkmark, BorderLayout.WEST);
        } else {
            checkboxPanel.add(checkbox, BorderLayout.WEST);
        }

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        checkboxPanel.add(titleLabel, BorderLayout.CENTER);

        // Description
        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.LIGHT_GRAY);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionArea.setBorder(null);

        contentPanel.add(checkboxPanel, BorderLayout.NORTH);
        contentPanel.add(descriptionArea, BorderLayout.CENTER);

        taskPanel.add(contentPanel, BorderLayout.CENTER);
        taskPanel.add(iconPanel, BorderLayout.EAST);

        return taskPanel;
    }

    private JPanel createTaskPanel(JPanel... tasks) {
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        taskPanel.setBackground(Color.WHITE);

        for (int i = 0; i < tasks.length; i++) {
            taskPanel.add(tasks[i]);
            if (i < tasks.length - 1) {
                taskPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        return taskPanel;
    }

    private int countCompletedTasks() {
        int count = 0;
        for (Boolean completed : taskCompletionStatus.values()) {
            if (completed) {
                count++;
            }
        }
        return count;
    }

    private JPanel createTaskItem(Task task) {
        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 5, 10, 5)
        ));

        JPanel taskInfoPanel = new JPanel();
        taskInfoPanel.setLayout(new BoxLayout(taskInfoPanel, BoxLayout.Y_AXIS));

        JLabel taskTitleLabel = new JLabel(task.getTitle());
        taskTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel taskDescLabel = new JLabel(task.getDescription());
        taskDescLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        taskInfoPanel.add(taskTitleLabel);
        taskInfoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        taskInfoPanel.add(taskDescLabel);

        String taskKey = (currentUser != null) ? currentUser.getEmail() + "_" + task.getId() : String.valueOf(task.getId());
        boolean isCompleted = taskCompletionStatus.getOrDefault(taskKey, false);

        JButton actionButton = new JButton(isCompleted ? "Completed" : "Mark as Completed");
        if (isCompleted) {
            actionButton.setBackground(new Color(144, 238, 144)); // Light green
            actionButton.setEnabled(false);
        }

        actionButton.addActionListener(e -> {
            if (currentUser == null) {
                JOptionPane.showMessageDialog(frame, "Please log in to track task completion");
                return;
            }

            taskCompletionStatus.put(taskKey, true);
            actionButton.setText("Completed");
            actionButton.setBackground(new Color(144, 238, 144)); // Light green
            actionButton.setEnabled(false);

            // Add point system or rewards here if needed
            JOptionPane.showMessageDialog(frame, "Great job! You've completed this eco-task!");
        });

        taskPanel.add(taskInfoPanel, BorderLayout.CENTER);
        taskPanel.add(actionButton, BorderLayout.EAST);

        return taskPanel;
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
        offerPanel.setMaximumSize(new Dimension(900, 450)); // Keep it within the frame

        JLabel offerTitle = new JLabel("What We Offer");
        offerTitle.setFont(UIConstants.HEADER_FONT);
        offerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Container for buttons (ensures proper spacing & centering)
        JPanel offerCards = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        offerCards.setBackground(Color.WHITE);
        offerCards.setMaximumSize(new Dimension(900, 835)); // Ensures centering

        // Create Offer Buttons (Only images, no text on them)
        JButton carbonFootprintButton = createImageButton(
                "/com/hillcrest/visuals/carbon_footprint_button_graphic.png",
                e -> System.out.println("Carbon Footprint Calculator clicked!") // Replace with actual action
        );

        JButton resourcesButton = createImageButton(
                "/com/hillcrest/visuals/informative_resources_buttonn_graphic.png",
                e -> System.out.println("Informative Resources clicked!") // Replace with actual action
        );

        JButton actionsButton = createImageButton(
                "/com/hillcrest/visuals/take_action.png",
                e -> createCarbonFootprintPanel()
        );


        // Add buttons to the panel
        offerCards.add(carbonFootprintButton);
        offerCards.add(resourcesButton);
        offerCards.add(actionsButton);

        offerPanel.add(offerTitle);
        offerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        offerPanel.add(offerCards);

        mainPanel.add(offerPanel);
    }

    // Helper method to create an image button
    private JButton createImageButton(String imagePath, ActionListener action) {
        JButton button = new JButton();
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(250, 250)); // Keep buttons uniform

        try {
            ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource(imagePath))
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)); // Maintain aspect ratio
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Image not found: " + imagePath);
        }

        button.addActionListener(action);
        return button;
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
                    // Navigate to the tasks panel
                    refreshTasksPanel(); // Refresh tasks before showing the panel
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
        aboutPanel.setMaximumSize(new Dimension(800, 350));

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
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
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