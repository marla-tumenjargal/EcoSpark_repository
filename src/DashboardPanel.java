import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * The DashboardPanel class is the main 'window' for the tasks that a user can complete.
 * It displays tasks, badges, and progress information to allow users to interact with their tasks
 * and track their achievements.
 *
 * The dashboard is divided into 1) the title section with the user's name, 2) the tasks panel showing
 * all the tasks, 3) the badges panel, and 4) the "explore" tasks panel for discovering additional tasks.
 */
public class DashboardPanel extends JPanel {
    private ApplicationModel model;
    private TaskManager taskManager;
    private Profile currentUser;
    private JPanel contentPanel;
    private JPanel tasksPanel;
    private JPanel badgesPanel;
    private UserManager userManager;;
    private CardLayout contentCardLayout;
    private Color primaryColor = new Color(33, 150, 83);
    private Map<String, Badge> badgeTypes = new HashMap<>();

    /**
     * Creates a DashboardPanel for the given application model and user profile.
     *
     * @param model, application data model.
     * @param currentUser, logged-in user's profile.
     */
    public DashboardPanel(ApplicationModel model, Profile currentUser) {
        this.model = model;
        this.taskManager = model.getTaskManager();
        this.currentUser = currentUser;
        userManager = new UserManager();

        initializeBadges();
        setLayout(new BorderLayout());

        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);
        JPanel dashboardContent = createDashboardContent();
        contentPanel.add(dashboardContent, "dashboard");
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Builds the main dashboard content, including tasks, badges, and explore sections.
     * @return The main content panel.
     */
    private JPanel createDashboardContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel titleLabel = new JLabel(currentUser.getName() + "'s Dashboard");

        titleLabel.setFont(UIConstants.SUBHEADER_FONT);
        titlePanel.add(titleLabel, BorderLayout.WEST);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 20, 10, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 1.0;

        tasksPanel = createTasksPanel();
        contentPanel.add(tasksPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;

        badgesPanel = createBadgesPanel();
        contentPanel.add(badgesPanel, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel explorePanel = createExploreTasksPanel();
        mainPanel.add(explorePanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    /**
     * Creates a panel to display tasks in tabs
     * @return The tasks panel.
     */
    private JPanel createTasksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JTabbedPane tasksTabbedPane = new JTabbedPane();
        tasksTabbedPane.setFont(UIConstants.BODY_FONT);

        JPanel allTasksPanel = new JPanel();
        allTasksPanel.setLayout(new BoxLayout(allTasksPanel, BoxLayout.Y_AXIS));
        allTasksPanel.setBackground(Color.WHITE);

        List<Task> allTasks = taskManager.getTaskLibrary();
        int halfSize = allTasks.size() / 2;

        for (int i = 0; i < halfSize; i++) {
            allTasksPanel.add(createTaskItem(allTasks.get(i)));
        }


        JScrollPane allTasksScrollPane = new JScrollPane(allTasksPanel);
        allTasksScrollPane.setBorder(BorderFactory.createEmptyBorder());
        allTasksScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        allTasksScrollPane.setPreferredSize(new Dimension(550, 450)); // Increased size
        allTasksScrollPane.getVerticalScrollBar().setUnitIncrement(30); // More sensitive scrolling

        tasksTabbedPane.addTab("All Tasks", allTasksScrollPane);
        JPanel favoriteTasksPanel = new JPanel();
        favoriteTasksPanel.setLayout(new BoxLayout(favoriteTasksPanel, BoxLayout.Y_AXIS));
        favoriteTasksPanel.setBackground(Color.WHITE);

        JScrollPane favoriteTasksScrollPane = new JScrollPane(favoriteTasksPanel);
        favoriteTasksScrollPane.setBorder(BorderFactory.createEmptyBorder());
        favoriteTasksScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        favoriteTasksScrollPane.setPreferredSize(new Dimension(550, 450)); // Increased size
        favoriteTasksScrollPane.getVerticalScrollBar().setUnitIncrement(30); // More sensitive scrolling

        tasksTabbedPane.addTab("Favorite", favoriteTasksScrollPane);

        panel.add(tasksTabbedPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates a single task item for display in the tasks panel.
     * @param task The task to display.
     * @return The task item panel.
     */
    private JPanel createTaskItem(Task task) {
        boolean isCompleted = taskManager.isTaskCompleted(currentUser, task);

        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BorderLayout(10, 0));
        taskPanel.setBackground(Color.WHITE);
        taskPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        taskPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Checkbox for task status
        JCheckBox statusCheckBox = new JCheckBox();
        statusCheckBox.setSelected(isCompleted);
        statusCheckBox.setBackground(Color.WHITE);

        // Make the checkbox green when selected
        statusCheckBox.setIcon(new ColorCheckboxIcon(false, new Color(200, 200, 200)));
        statusCheckBox.setSelectedIcon(new ColorCheckboxIcon(true, primaryColor));

        // Task title and description
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(task.getTitle());
        titleLabel.setFont(UIConstants.BODY_FONT);

        JPanel titlePointsPanel = new JPanel(new BorderLayout());
        titlePointsPanel.setBackground(Color.WHITE);
        titlePointsPanel.add(titleLabel, BorderLayout.WEST);

        JLabel pointsValueLabel = new JLabel("+" + task.getPointsValue() + " pts");
        pointsValueLabel.setFont(UIConstants.BODY_FONT);
        pointsValueLabel.setForeground(primaryColor);
        titlePointsPanel.add(pointsValueLabel, BorderLayout.EAST);

        textPanel.add(titlePointsPanel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JTextArea descriptionArea = new JTextArea(task.getDescription());
        descriptionArea.setFont(UIConstants.BODY_FONT);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.WHITE);

        textPanel.add(descriptionArea);
        JPanel iconPanel = createColorfulTaskIcon(task.getType());

        taskPanel.add(statusCheckBox, BorderLayout.WEST);
        taskPanel.add(textPanel, BorderLayout.CENTER);
        taskPanel.add(iconPanel, BorderLayout.EAST);

        statusCheckBox.addActionListener(e -> {
            if (statusCheckBox.isSelected() && !isCompleted) {
                taskManager.completeTask(currentUser, task);
                currentUser.addPoints(task.getPointsValue());
                List<Badge> newBadges = checkForNewBadges();
                String message = "Great job! You earned " + task.getPointsValue() + " points!";
                if (!newBadges.isEmpty()) {
                    message += "\n\nYou've also earned new badges:";
                    for (Badge badge : newBadges) {
                        message += "\n- " + badge.getName() + " " + badge.getIcon();
                    }
                }
                JOptionPane.showMessageDialog(this,
                        message,
                        "Task Completed",
                        JOptionPane.INFORMATION_MESSAGE);

                refreshBadgesPanel();
                refreshHeaderPoints();
                userManager.saveData();
            }
        });
        return taskPanel;
    }

    /**
     * Creates a colorful icon for a task based on its type.
     * @param taskType The type of the task.
     * @return The task icon panel.
     */
    private JPanel createColorfulTaskIcon(String taskType) {
        JPanel iconPanel = new JPanel(new BorderLayout());
        iconPanel.setPreferredSize(new Dimension(70, 70));

        Color bgColor;
        String iconText;

        switch (taskType.toLowerCase()) {
            case "community":
                bgColor = new Color(135, 206, 250); // Light blue
                iconText = "🌎";
                break;
            case "energy":
                bgColor = new Color(255, 215, 0); // Gold
                iconText = "💡";
                break;
            case "water":
                bgColor = new Color(30, 144, 255); // Dodger blue
                iconText = "💧";
                break;
            case "waste":
                bgColor = new Color(152, 251, 152); // Pale green
                iconText = "♻️";
                break;
            case "consumption":
                bgColor = new Color(255, 182, 193); // Light pink
                iconText = "🛍️";
                break;
            case "food":
                bgColor = new Color(255, 165, 0); // Orange
                iconText = "🍎";
                break;
            case "transport":
                bgColor = new Color(147, 112, 219); // Medium purple
                iconText = "🚲";
                break;
            case "conservation":
                bgColor = new Color(60, 179, 113); // Medium sea green
                iconText = "🌳";
                break;
            case "sustainable":
            default:
                bgColor = new Color(173, 216, 230); // Light blue
                iconText = "🌱";
                break;
        }

        JPanel colorPanel = new RoundedPanel(10, bgColor);
        colorPanel.setLayout(new GridBagLayout());
        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setFont(UIConstants.SUBHEADER_FONT);
        colorPanel.add(iconLabel);
        iconPanel.add(colorPanel, BorderLayout.CENTER);
        return iconPanel;
    }

    /**
     * Updates the points displayed in the header.
     */
    private void refreshHeaderPoints() {
        Component[] headerComponents = getComponents();
        for (Component c : headerComponents) {
            if (c instanceof JPanel) {
                JPanel headerPanel = (JPanel) c;
                Component[] components = headerPanel.getComponents();
                for (Component comp : components) {
                    if (comp instanceof JPanel) {
                        JPanel profilePanel = (JPanel) comp;
                        Component[] profileComponents = profilePanel.getComponents();
                        for (Component profComp : profileComponents) {
                            if (profComp instanceof JLabel) {
                                JLabel label = (JLabel) profComp;
                                if (label.getText().contains("pts")) {
                                    label.setText(currentUser.getPoints() + " pts");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if the user has earned any new badges.
     * @return A list of new badges earned.
     */
    private List<Badge> checkForNewBadges() {
        List<Badge> newBadges = new ArrayList<>();
        int currentPoints = currentUser.getPoints();

        for (Badge badge : badgeTypes.values()) {
            if (badge.getPointsRequired() > 0) {
                if (currentPoints >= badge.getPointsRequired() &&
                        !currentUser.hasBadge(badge.getName())) {
                    currentUser.addBadge(badge.getName());
                    newBadges.add(badge);
                }
            }
        }
        return newBadges;
    }

    /**
     * Creates a button to display a badge.
     * @param badge The badge to display.
     * @param isEarned Whether the badge is earned.
     * @return The badge button panel.
     */
    private JPanel createBadgeButton(Badge badge, boolean isEarned) {
        JPanel badgeContainer = new JPanel(new BorderLayout(0, 5));
        badgeContainer.setBackground(Color.WHITE);

        JButton badgeButton = new JButton();
        badgeButton.setLayout(new GridBagLayout());
        badgeButton.setBorderPainted(false);
        badgeButton.setFocusPainted(false);

        if (isEarned) {
            badgeButton.setBackground(badge.getColor());
            JLabel badgeIcon = new JLabel(badge.getIcon());
            badgeIcon.setFont(new Font("Dialog", Font.PLAIN, 24));
            badgeIcon.setForeground(Color.WHITE);
            badgeButton.add(badgeIcon);
        } else {
            badgeButton.setBackground(new Color(220, 220, 220));
            JLabel questionMark = new JLabel("?");
            questionMark.setFont(UIConstants.SUBHEADER_FONT);
            questionMark.setForeground(Color.WHITE);
            badgeButton.add(questionMark);
        }

        badgeButton.setPreferredSize(new Dimension(65, 65));

        badgeButton.addActionListener(e -> {
            String title = isEarned ? badge.getName() : "Locked Badge";
            String message = isEarned ?
                    badge.getDescription() :
                    (badge.getPointsRequired() > 0 ?
                            "Requires " + badge.getPointsRequired() + " points to unlock" :
                            "Complete special tasks to unlock this badge");

            JPanel detailPanel = new JPanel(new BorderLayout(0, 10));
            detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel iconPanel = new JPanel();
            iconPanel.setBackground(isEarned ? badge.getColor() : new Color(220, 220, 220));
            JLabel iconLabel = new JLabel(isEarned ? badge.getIcon() : "?");
            iconLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
            iconLabel.setForeground(Color.WHITE);
            iconPanel.add(iconLabel);
            iconPanel.setPreferredSize(new Dimension(70, 70));

            detailPanel.add(iconPanel, BorderLayout.NORTH);

            JTextArea descArea = new JTextArea(message);
            descArea.setWrapStyleWord(true);
            descArea.setLineWrap(true);
            descArea.setEditable(false);
            descArea.setBackground(detailPanel.getBackground());
            descArea.setFont(UIConstants.BODY_FONT);

            detailPanel.add(descArea, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(
                    this,
                    detailPanel,
                    title,
                    JOptionPane.PLAIN_MESSAGE
            );
        });

        badgeContainer.add(badgeButton, BorderLayout.CENTER);
        JLabel badgeLabel = new JLabel(isEarned ? badge.getName() : "???", JLabel.CENTER);
        badgeLabel.setFont(UIConstants.BODY_FONT);
        badgeContainer.add(badgeLabel, BorderLayout.SOUTH);
        return badgeContainer;
    }

    /**
     * Refreshes the badges panel to show updated badges and progress.
     */
    private void refreshBadgesPanel() {
        for (Component c : getComponents()) {
            if (c instanceof JPanel) {
                JPanel panel = (JPanel) c;
                if (panel.getName() != null && panel.getName().equals("contentPanel")) {
                    for (Component innerComp : panel.getComponents()) {
                        if (innerComp instanceof JPanel) {
                            JPanel contentPanel = (JPanel) innerComp;
                            for (Component gridComp : contentPanel.getComponents()) {
                                if (gridComp == badgesPanel) {
                                    contentPanel.remove(gridComp);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        badgesPanel = createBadgesPanel();

        for (Component c : getComponents()) {
            if (c instanceof JPanel) {
                JPanel mainPanel = (JPanel) c;
                if (mainPanel.getLayout() instanceof BorderLayout) {
                    Component centerComponent = ((BorderLayout) mainPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
                    if (centerComponent instanceof JPanel) {
                        JPanel contentPanel = (JPanel) centerComponent;
                        if (contentPanel.getLayout() instanceof GridBagLayout) {
                            GridBagConstraints gbc = new GridBagConstraints();
                            gbc.fill = GridBagConstraints.BOTH;
                            gbc.insets = new Insets(10, 20, 10, 20);
                            gbc.gridx = 1;
                            gbc.gridy = 0;
                            gbc.weightx = 0.4;
                            gbc.weighty = 1.0;

                            contentPanel.add(badgesPanel, gbc);
                            contentPanel.revalidate();
                            contentPanel.repaint();
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates a panel to explore additional tasks.
     * @return The explore tasks panel.
     */
    private JPanel createExploreTasksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Title section
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);

        JLabel exploreTitleLabel = new JLabel("Explore Tasks:");
        exploreTitleLabel.setFont(UIConstants.SUBHEADER_FONT);
        titlePanel.add(exploreTitleLabel, BorderLayout.WEST);

        panel.add(titlePanel, BorderLayout.NORTH);

        // Tasks container
        JPanel tasksContainer = new JPanel();
        tasksContainer.setLayout(new BoxLayout(tasksContainer, BoxLayout.Y_AXIS));
        tasksContainer.setBackground(Color.WHITE);

        // Get the other half of tasks we didn't show in the main dashboard
        List<Task> allTasks = taskManager.getTaskLibrary();
        int halfSize = allTasks.size() / 2;

        for (int i = halfSize; i < allTasks.size(); i++) {
            tasksContainer.add(createTaskItem(allTasks.get(i)));
        }

        JScrollPane scrollPane = new JScrollPane(tasksContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(0, 300));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * A custom JPanel class for creating rounded corners.
     */
    private class RoundedPanel extends JPanel {
        private int cornerRadius;
        private Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            super();
            this.cornerRadius = radius;
            this.backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(backgroundColor);
            graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        }
    }

    /**
     * A custom icon for colorful checkboxes.
     */
    private class ColorCheckboxIcon implements Icon {
        private final boolean selected;
        private final Color color;
        private final int size = 20;

        public ColorCheckboxIcon(boolean selected, Color color) {
            this.selected = selected;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.fillRoundRect(x, y, size, size, 4, 4);

            if (selected) {
                g2d.setColor(Color.WHITE);
                int padding = 4;
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(x + padding, y + size/2, x + size/3, y + size - padding);
                g2d.drawLine(x + size/3, y + size - padding, x + size - padding, y + padding);
            }
            g2d.dispose();
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }
    }

    /**
     * inner class for badges
     */
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

    /**
     * Creates the badges panel, which displays the user's
     * earned badges, progress, and upcoming badges.
     * @return A JPanel containing the badges interface.
     */
    private JPanel createBadgesPanel() {
        badgesPanel = new JPanel();
        badgesPanel.setLayout(new BoxLayout(badgesPanel, BoxLayout.Y_AXIS));
        badgesPanel.setBackground(Color.WHITE);
        badgesPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel badgesTitle = new JLabel("Badges");
        badgesTitle.setFont(UIConstants.SUBHEADER_FONT);
        badgesTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        badgesTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        badgesPanel.add(badgesTitle);

        // Progress info
        JLabel progressTitle = new JLabel("Points Progress: " + currentUser.getPoints() + " points");
        progressTitle.setFont(UIConstants.BODY_FONT);
        progressTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        progressTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        badgesPanel.add(progressTitle);

        // Add progress bar
        JProgressBar progressBar = new JProgressBar(0, 320); // Updated to match highest badge requirement
        progressBar.setValue(Math.min(currentUser.getPoints(), 320));
        progressBar.setStringPainted(true);
        progressBar.setString(currentUser.getPoints() + "/320");
        progressBar.setForeground(primaryColor);
        progressBar.setPreferredSize(new Dimension(0, 20));
        progressBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        badgesPanel.add(progressBar);
        badgesPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add badge collection in a grid layout (4 columns for more badges
        JLabel allBadgesTitle = new JLabel("Badge Collection:");
        allBadgesTitle.setFont(UIConstants.SUBHEADER_FONT);
        allBadgesTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        allBadgesTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        badgesPanel.add(allBadgesTitle);

        JPanel badgeGrid = new JPanel(new GridLayout(0, 4, 10, 10));
        badgeGrid.setBackground(Color.WHITE);
        badgeGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

        List<String> userBadges = currentUser.getBadges();

        for (Badge badge : badgeTypes.values()) {
            if (badge.getPointsRequired() > 0) {
                boolean isEarned = userBadges.contains(badge.getName());
                JPanel badgePanel = createBadgeButton(badge, isEarned);
                badgeGrid.add(badgePanel);
            }
        }
        for (Badge badge : badgeTypes.values()) {
            if (badge.getPointsRequired() == 0) {
                boolean isEarned = userBadges.contains(badge.getName());
                JPanel badgePanel = createBadgeButton(badge, isEarned);
                badgeGrid.add(badgePanel);
            }
        }

        JScrollPane badgeScrollPane = new JScrollPane(badgeGrid);
        badgeScrollPane.setBorder(BorderFactory.createEmptyBorder());
        badgeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        badgeScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        badgeScrollPane.setPreferredSize(new Dimension(0, 400)); // Much bigger size for badges
        badgeScrollPane.getVerticalScrollBar().setUnitIncrement(30); // More sensitive scrolling

        badgesPanel.add(badgeScrollPane);
        badgesPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel upcomingBadgesTitle = new JLabel("Next Badges:");
        upcomingBadgesTitle.setFont(UIConstants.SUBHEADER_FONT);
        upcomingBadgesTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        upcomingBadgesTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        badgesPanel.add(upcomingBadgesTitle);

        // Show upcoming point-based badges
        JPanel upcomingBadgesPanel = new JPanel();
        upcomingBadgesPanel.setLayout(new BoxLayout(upcomingBadgesPanel, BoxLayout.Y_AXIS));
        upcomingBadgesPanel.setBackground(Color.WHITE);
        upcomingBadgesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        boolean hasUpcomingBadges = false;
        for (Badge badge : badgeTypes.values()) {
            if (badge.getPointsRequired() > 0 &&
                    badge.getPointsRequired() > currentUser.getPoints() &&
                    !currentUser.hasBadge(badge.getName())) {

                JPanel upcomingBadgeRow = new JPanel(new BorderLayout(10, 5));
                upcomingBadgeRow.setBackground(Color.WHITE);
                upcomingBadgeRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

                JPanel iconContainer = new JPanel(new BorderLayout());
                iconContainer.setBackground(Color.WHITE);

                JPanel badgeIcon = new RoundedPanel(25, new Color(220, 220, 220));
                badgeIcon.setPreferredSize(new Dimension(40, 40));
                badgeIcon.setLayout(new GridBagLayout());

                JLabel iconLabel = new JLabel("?");
                iconLabel.setFont(UIConstants.SUBHEADER_FONT);
                iconLabel.setForeground(Color.WHITE);
                badgeIcon.add(iconLabel);

                iconContainer.add(badgeIcon, BorderLayout.CENTER);
                upcomingBadgeRow.add(iconContainer, BorderLayout.WEST);

                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                infoPanel.setBackground(Color.WHITE);

                JLabel badgeLabel = new JLabel(badge.getName() + " (" + badge.getPointsRequired() + " pts) " + badge.getIcon());
                badgeLabel.setFont(UIConstants.BODY_FONT);
                badgeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                infoPanel.add(badgeLabel);
                infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));

                JProgressBar badgeProgress = new JProgressBar(0, badge.getPointsRequired());
                badgeProgress.setValue(currentUser.getPoints());
                badgeProgress.setStringPainted(true);
                badgeProgress.setString(currentUser.getPoints() + "/" + badge.getPointsRequired());
                badgeProgress.setForeground(badge.getColor());
                badgeProgress.setAlignmentX(Component.LEFT_ALIGNMENT);
                infoPanel.add(badgeProgress);

                upcomingBadgeRow.add(infoPanel, BorderLayout.CENTER);
                upcomingBadgesPanel.add(upcomingBadgeRow);
                upcomingBadgesPanel.add(Box.createRigidArea(new Dimension(0, 15)));

                hasUpcomingBadges = true;

                if (upcomingBadgesPanel.getComponentCount() >= 6) break; // 3 badges + 3 spacers (just show the next few here)
            }
        }

        if (!hasUpcomingBadges) {
            JLabel allBadgesLabel = new JLabel("You've earned all point-based badges! 🎉");
            allBadgesLabel.setFont(UIConstants.BODY_FONT);
            upcomingBadgesPanel.add(allBadgesLabel);
        }
        badgesPanel.add(upcomingBadgesPanel);
        return badgesPanel;
    }

    /**
     * Initializes the available badge types and their requirements.
     */
    private void initializeBadges() {
        badgeTypes.put("novice", new Badge("Novice", "Starting your sustainability journey", 10, new Color(173, 216, 230), "🌱"));
        badgeTypes.put("beginner", new Badge("Beginner", "Making regular eco-friendly choices", 20, new Color(135, 206, 235), "🌿"));
        badgeTypes.put("intermediate", new Badge("Intermediate", "Taking significant steps to reduce impact", 40, new Color(0, 191, 255), "🌍"));
        badgeTypes.put("advanced", new Badge("Advanced", "Adopting a sustainable lifestyle", 80, new Color(30, 144, 255), "🌊"));
        badgeTypes.put("expert", new Badge("Expert", "Leading by example in sustainability", 160, new Color(0, 0, 205), "⭐"));
        badgeTypes.put("master", new Badge("Master", "Mastering sustainable living", 320, new Color(75, 0, 130), "🏆"));
        badgeTypes.put("community", new Badge("Community Champion", "Building environmental community connections", 0, new Color(135, 206, 250), "🤝"));
        badgeTypes.put("learning", new Badge("Eco Learner", "Expanding environmental knowledge", 0, new Color(173, 216, 230), "🧩"));
        badgeTypes.put("innovation", new Badge("Green Innovator", "Finding creative eco solutions", 0, new Color(152, 251, 152), "💡"));
        badgeTypes.put("water", new Badge("Water Protector", "Conserving and protecting water resources", 0, new Color(30, 144, 255), "💧"));
        badgeTypes.put("energy", new Badge("Energy Saver", "Reducing energy consumption", 0, new Color(255, 215, 0), "⚡"));
        badgeTypes.put("waste", new Badge("Zero Waste Hero", "Minimizing waste production", 0, new Color(255, 182, 193), "♻️"));
        badgeTypes.put("biodiversity", new Badge("Biodiversity Guardian", "Protecting plants and wildlife", 0, new Color(152, 251, 152), "🌺"));
        badgeTypes.put("advocacy", new Badge("Environmental Advocate", "Spreading awareness and advocacy", 0, new Color(255, 99, 71), "🏅"));
        badgeTypes.put("climate", new Badge("Climate Defender", "Taking action against climate change", 0, new Color(147, 112, 219), "🌀"));
        badgeTypes.put("oceans", new Badge("Ocean Defender", "Taking action to protect marine ecosystems", 0, new Color(0, 105, 148), "🐠"));
        badgeTypes.put("plastic", new Badge("Plastic Fighter", "Reducing plastic pollution in the environment", 0, new Color(255, 64, 129), "🚫"));
        badgeTypes.put("forest", new Badge("Forest Guardian", "Protecting and preserving forest ecosystems", 0, new Color(76, 175, 80), "🌲"));
        badgeTypes.put("air", new Badge("Clean Air Champion", "Taking steps to reduce air pollution", 0, new Color(178, 235, 242), "💨"));
        badgeTypes.put("soil", new Badge("Soil Protector", "Preserving soil health and preventing erosion", 0, new Color(121, 85, 72), "🌰"));
        badgeTypes.put("wildlife", new Badge("Wildlife Ally", "Supporting and protecting endangered species", 0, new Color(255, 193, 7), "🦁"));
        badgeTypes.put("sustainable_food", new Badge("Food Sustainability", "Supporting sustainable agriculture practices", 0, new Color(139, 195, 74), "🥗"));
        badgeTypes.put("transport", new Badge("Green Transport", "Using eco-friendly transportation methods", 0, new Color(103, 58, 183), "🚲"));
    }
}