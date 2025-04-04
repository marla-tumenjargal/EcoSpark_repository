import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.awt.geom.RoundRectangle2D;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * EcoSparkApp is the main application for the whole platform.
 * Manages input and output, as well as the buttons for calculating carbon footprints,
 * taking quizzes, and tracking user progress.
 */
public class EcoSparkApp extends JFrame {
    private ApplicationModel model;
    private Profile currentUser;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JScrollPane scrollPane;
    private TaskManager taskManager;
    private Map<String, JComponent> formFields = new HashMap<>();
    private JPanel carbonFootprintPanel;
    private QuizBackend backend = new QuizBackend();
    private JFrame quizFrame;
    private JPanel quizPanel;
    private JLabel questionLabel;
    private JButton[] optionButtons;
    private UserManager userManager;

    CarbonFootprintCalculator calculator = new CarbonFootprintCalculator();

    /**
     * Main method
     * @param args Command-line stuff
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EcoSparkApp();
            }
        });
    }

    /**
     * Constructs the EcoSparkApp window and initializes its components.
     */
    public EcoSparkApp() {
        model = new ApplicationModel();
        userManager = new UserManager();

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
        createQuizPanel();

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane);
        setVisible(true);
    }

    /**
     * Creates the login panel for user authentication.
     */
    private void createLoginPanel() {
        getContentPane().remove(scrollPane);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel titleLabel = new JLabel("Login to EcoSpark");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new RoundedButton("Log in", UIConstants.BUTTON_BLUE, Color.WHITE);
        loginButton.setPreferredSize(new Dimension(180, 40));
        JButton backButton = new RoundedButton("Back to Home", Color.WHITE, UIConstants.TEXT_COLOR);
        backButton.setPreferredSize(new Dimension(180, 40));

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

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

            if (userManager.authenticate(email, password)) {
                currentUser = userManager.getProfile(email);
                model.setCurrentUser(currentUser);
                statusLabel.setText("Login successful!");
                statusLabel.setForeground(UIConstants.GREEN_CHECK);

                Timer timer = new Timer(1300, event -> {
                    getContentPane().remove(loginPanel);
                    getContentPane().add(scrollPane);
                    revalidate();
                    repaint();
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                statusLabel.setText("Invalid email or password");
                statusLabel.setForeground(Color.RED);
            }
        });

        backButton.addActionListener(e -> {
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

    /**
     * Creates the registration panel for new users.
     */
    private void createRegisterPanel() {
        getContentPane().remove(scrollPane);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));
        registerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel titleLabel = new JLabel("Create Your EcoSpark Account");
        titleLabel.setFont(UIConstants.SUBHEADER_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("Full Name:");
        JTextField nameField = new JTextField(20);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        JPanel confirmPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordPanel.add(confirmPasswordLabel);
        confirmPasswordPanel.add(confirmPasswordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton registerButton = new RoundedButton("Register", UIConstants.BUTTON_BLUE, Color.WHITE);
        JButton backButton = new RoundedButton("Back to Home", Color.WHITE, UIConstants.TEXT_COLOR);
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

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

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                statusLabel.setText("All fields must be filled out!");
                statusLabel.setForeground(Color.RED);
            } else if (!password.equals(confirmPassword)) {
                statusLabel.setText("Passwords do not match!");
                statusLabel.setForeground(Color.RED);
            } else if (userManager.userExists(email)) {
                statusLabel.setText("Email already registered!");
                statusLabel.setForeground(Color.RED);
            } else {
                Profile newProfile = new Profile(name, email, password);
                userManager.addUser(newProfile);

                statusLabel.setText("Account created successfully!");
                statusLabel.setFont(UIConstants.BUTTON_FONT);
                statusLabel.setForeground(UIConstants.GREEN_CHECK);

                Timer timer = new Timer(1300, event -> {
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

    @Override
    public void dispose() {
        userManager.saveData();
        super.dispose();
    }

    /**
     * Creates the quiz panel for users.
     * @return quiz panel.
     */
    public JPanel createQuizPanel() {
        quizPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(UIConstants.GREEN_CHECK);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));
        quizPanel.setBorder(new EmptyBorder(40, 30, 40, 30));

        JPanel questionContainer = new JPanel();
        questionContainer.setOpaque(false);
        questionContainer.setLayout(new BorderLayout());

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(UIConstants.BUTTON_FONT);
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setBorder(new EmptyBorder(0, 0, 50, 0));
        questionContainer.add(questionLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 15));
        buttonPanel.setOpaque(false);

        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(UIConstants.BUTTON_FONT);
            optionButtons[i].setForeground(new Color(60, 60, 60));
            optionButtons[i].setBackground(Color.WHITE);
            optionButtons[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(5, 10, 5, 10),
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(240, 240, 240), 1),
                            BorderFactory.createEmptyBorder(10, 20, 10, 20)
                    )
            ));

            optionButtons[i].setLayout(new BorderLayout());
            JPanel buttonContent = new JPanel(new BorderLayout());
            buttonContent.setOpaque(false);
            JLabel bullet = new JLabel("●");
            bullet.setForeground(new Color(180, 180, 200));
            bullet.setFont(UIConstants.BUTTON_FONT);
            bullet.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            buttonContent.add(bullet, BorderLayout.WEST);

            JLabel textLabel = new JLabel(optionButtons[i].getText());
            textLabel.setFont(optionButtons[i].getFont());
            textLabel.setForeground(optionButtons[i].getForeground());
            buttonContent.add(textLabel, BorderLayout.CENTER);

            optionButtons[i].add(buttonContent);

            optionButtons[i].setUI(new BasicButtonUI() {
                @Override
                public void paint(Graphics g, JComponent c) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    AbstractButton b = (AbstractButton) c;
                    ButtonModel model = b.getModel();
                    RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, c.getWidth() - 1, c.getHeight() - 1, 25, 25);
                    g2.setColor(b.getBackground());
                    g2.fill(roundedRectangle);

                    if (model.isRollover()) {
                        g2.setColor(new Color(240, 240, 255));
                        g2.draw(roundedRectangle);
                    }

                    g2.dispose();
                    super.paint(g, c);
                }
            });

            optionButtons[i].addActionListener(new AnswerListener());
            buttonPanel.add(optionButtons[i]);
        }

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitPanel.setOpaque(false);
        submitPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(UIConstants.BUTTON_FONT);
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(150, 130, 170));
        submitButton.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        submitButton.setFocusPainted(false);

        submitButton.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();

                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, c.getWidth() - 1, c.getHeight() - 1, 25, 25);
                g2.setColor(b.getBackground());
                g2.fill(roundedRectangle);

                if (model.isRollover()) {
                    g2.setColor(new Color(170, 150, 190));
                    g2.fill(roundedRectangle);
                }
                g2.dispose();
                super.paint(g, c);
            }
        });

        submitPanel.add(submitButton);
        quizPanel.add(questionContainer);
        quizPanel.add(buttonPanel);
        quizPanel.add(submitPanel);

        submitButton.addActionListener(e -> {
            JButton clickedButton = null;
            for (JButton button : optionButtons) {
                if (button.getModel().isPressed()) {
                    clickedButton = button;
                    break;
                }
            }

            if (clickedButton != null) {
                backend.checkAnswer(clickedButton.getName());
                loadQuestion();
            }
        });

        loadQuestion();
        return quizPanel;
    }

    /**
     * Loads the next question in the quiz.
     */
    private void loadQuestion() {
        if (backend.hasMoreQuestions()) {
            for (JButton button : optionButtons) {
                button.setBackground(Color.WHITE);
            }

            Map<String, Object> qData = backend.getCurrentQuestion();
            questionLabel.setText((String) qData.get("questionText"));
            List<String> options = (List<String>) qData.get("options");
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setName(options.get(i));
                for (Component comp : optionButtons[i].getComponents()) {
                    if (comp instanceof JPanel) {
                        JPanel panel = (JPanel) comp;
                        for (Component innerComp : panel.getComponents()) {
                            if (innerComp instanceof JLabel && !(((JLabel) innerComp).getText().equals("●"))) {
                                ((JLabel) innerComp).setText(options.get(i));
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            showResults();
        }
    }

    /**
     * Displays the quiz results after completion.
     */
    private void showResults() {
        StringBuilder results = new StringBuilder();
        results.append("Quiz Over! Your Score: ").append(backend.getScore()).append(" / ").append(backend.getTotalQuestions()).append("\n\n");
        JOptionPane.showMessageDialog(quizPanel, results.toString());
    }

    private class AnswerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            boolean isCorrect = backend.checkAnswer(clickedButton.getName());

            if (isCorrect) {
                clickedButton.setBackground(Color.GREEN);
            } else {
                clickedButton.setBackground(Color.RED);
            }

            Timer timer = new Timer(300, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    loadQuestion();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Checks if the user is logged in.
     * @return True if the user is logged in, false otherwise.
     */
    private boolean isUserLoggedIn() {
        return currentUser != null;
    }

    /**
     * Creates the carbon footprint calculation panel.
     */
    private void createCarbonFootprintPanel() {
        getContentPane().remove(scrollPane);

        carbonFootprintPanel = new JPanel();
        carbonFootprintPanel.setLayout(new BoxLayout(carbonFootprintPanel, BoxLayout.Y_AXIS));
        carbonFootprintPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        carbonFootprintPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Calculate Your Carbon Footprint");
        titleLabel.setFont(UIConstants.HEADER_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel infoPanel = createInfoPanel();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.WHITE);
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

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component tabComponent = tabbedPane.getTabComponentAt(i);
            if (tabComponent instanceof JComponent) {
                ((JComponent) tabComponent).setFont(UIConstants.BUTTON_FONT);
            }
        }

        carbonFootprintPanel.add(titleLabel);
        carbonFootprintPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        carbonFootprintPanel.add(infoPanel);
        carbonFootprintPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        carbonFootprintPanel.add(tabbedPane);
        tabbedPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        JButton submitButton = new JButton("SUBMIT");
        submitButton.setBackground(UIConstants.BUTTON_BLUE);
        submitButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(submitButton);

        carbonFootprintPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        carbonFootprintPanel.add(buttonPanel);

        JPanel resultsPanel = createResultsPanel();

        carbonFootprintPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        carbonFootprintPanel.add(resultsPanel);

        JScrollPane carbonScrollPane = new JScrollPane(carbonFootprintPanel);
        carbonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        carbonScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        carbonScrollPane.setBorder(null);
        carbonScrollPane.setBackground(Color.WHITE);

        submitButton.addActionListener(e -> {
            if (validateInputs()) {
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

    /**
     * Validates that all required fields are filled and no negative values are entered.
     * @return true if all validations pass, false otherwise
     */
    private boolean validateInputs() {
        // First check if all fields are filled
        for (Map.Entry<String, JComponent> entry : formFields.entrySet()) {
            if (entry.getValue() instanceof JTextField) {
                JTextField textField = (JTextField) entry.getValue();
                String text = textField.getText().trim();

                // Check if field is empty
                if (text.isEmpty()) {
                    return false;
                }

                // For numeric fields, check if the value is negative
                String fieldName = entry.getKey();
                if (isNumericField(fieldName)) {
                    try {
                        double value = Double.parseDouble(text);
                        if (value < 0) {
                            JOptionPane.showMessageDialog(this,
                                    "Negative values are not allowed: " + fieldName,
                                    "Invalid Input",
                                    JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this,
                                "Please enter a valid number for: " + fieldName,
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
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

    /**
     * Determines if a field should contain numeric values.
     * @param fieldName The name of the field
     * @return true if the field should contain a numeric value
     */
    private boolean isNumericField(String fieldName) {
        // List of fields that should contain numeric values
        return fieldName.equals("carMilesPerWeek") ||
                fieldName.equals("mpg") ||
                fieldName.equals("flightsPerYear") ||
                fieldName.equals("transitMiles") ||
                fieldName.equals("meatConsumption") ||
                fieldName.equals("dairyConsumption") ||
                fieldName.equals("electricityUsage") ||
                fieldName.equals("gasUsage") ||
                fieldName.equals("wasteProduced") ||
                fieldName.equals("disposableItems") ||
                fieldName.equals("squareFootage");
    }

    /**
     * Creates the general information panel for the carbon footprint calculator.
     * @return The general information panel.
     */
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

    /**
     * Creates the results panel for displaying carbon footprint calculations.
     * @return The results panel.
     */
    private JPanel createResultsPanel() {
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel footprintLabel = new JLabel("Your Footprint");
        footprintLabel.setFont(UIConstants.HEADER_FONT);
        footprintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel totalFootprintLabel = new JLabel("Total Carbon Footprint: 0.00 tons CO2e");
        totalFootprintLabel.setFont(UIConstants.BUTTON_FONT);
        totalFootprintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel breakdownLabel = new JLabel("Breakdown by Category:");
        breakdownLabel.setFont(UIConstants.BUTTON_FONT);
        breakdownLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel breakdownPanel = new JPanel();
        breakdownPanel.setBackground(Color.WHITE);
        breakdownPanel.setLayout(new GridLayout(0, 1, 10, 10));
        breakdownPanel.setName("breakdownPanel");

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

    /**
     * Calculates and displays the carbon footprint results.
     * @param resultsPanel The panel to display the results.
     */
    private void calculateAndDisplayResults(JPanel resultsPanel) {
        Map<String, Object> inputValues = collectInputValues();

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

        for (Component comp : resultsPanel.getComponents()) {
            if (comp instanceof JLabel && "totalFootprintLabel".equals(comp.getName())) {
                ((JLabel) comp).setText("Total Carbon Footprint: " + String.format("%.2f", totalFootprint) + " tons CO2e");
                break;
            }
        }

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
        resultsPanel.setVisible(true);
        revalidate();
        repaint();
    }

    /**
     * collects inputs
     * @return
     */
    private Map<String, Object> collectInputValues() {
        Map<String, Object> values = new HashMap<>();
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

    /**
     * Gets color of the category(when outputting the results
     * i tried picking primary colors! randomly thoo
     * @param category
     * @return
     */
    private Color getCategoryColor(String category) {
        switch (category) {
            case "General Usage":
                return new Color(65, 105, 225);
            case "Transportation":
                return new Color(220, 20, 60);
            case "Food Consumption":
                return new Color(34, 139, 34);
            case "Energy Usage":
                return new Color(218, 165, 32);
            case "Waste Production":
                return new Color(41, 128, 185);
            default:
                return Color.LIGHT_GRAY;
        }
    }

    /**
     * Creates the general usage panel for the carbon footprint calculator.
     * @return The general usage panel.
     */
    private JPanel createGeneralPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<String> countryCombo = new JComboBox<>(new String[]{"", "USA", "Canada", "UK", "China", "India", "Brazil", "Australia", "Other"});
        JComboBox<String> stateCombo = new JComboBox<>(new String[]{"", "Alabama", "Alaska", "Arizona", "California", "Colorado", "New York", "Texas", "Other"});
        JComboBox<String> householdCombo = new JComboBox<>(new String[]{"", "1", "2", "3", "4", "5+"});
        JComboBox<String> areaCombo = new JComboBox<>(new String[]{"", "Urban", "Suburban", "Rural"});
        JComboBox<String> homeCombo = new JComboBox<>(new String[]{"", "Apartment", "Townhouse", "Single Family Home", "Other"});
        JComboBox<String> renewableCombo = new JComboBox<>(new String[]{"", "Yes", "No", "Partial"});

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

    /**
     * Creates the transportation panel for the carbon footprint calculator.
     * @return transportation panel.
     */
    private JPanel createTransportationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<String> vehicleTypeCombo = new JComboBox<>(new String[]{"", "SUV", "Sedan", "Truck", "Compact Car", "Hybrid", "Electric Vehicle", "None"});
        JComboBox<String> fuelTypeCombo = new JComboBox<>(new String[]{"", "Gas", "Diesel", "Electric", "Hybrid", "Other"});
        JComboBox<String> carpoolCombo = new JComboBox<>(new String[]{"", "Yes", "No", "Sometimes"});

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

    /**
     * Creates the food consumption panel for the carbon footprint calculator.
     * @return food consumption panel.
     */
    private JPanel createFoodPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<String> dietTypeCombo = new JComboBox<>(new String[]{"", "Vegan", "Vegetarian", "Pescatarian", "Omnivore", "Keto", "Other"});
        JComboBox<String> localFoodCombo = new JComboBox<>(new String[]{"", "0-20%", "21-40%", "41-60%", "61-80%", "81-100%"});
        JComboBox<String> restaurantCombo = new JComboBox<>(new String[]{"", "Daily", "Several times a week", "Weekly", "Monthly", "Rarely"});
        JComboBox<String> foodWasteCombo = new JComboBox<>(new String[]{"", "Very little", "Some", "Average", "Significant", "A lot"});
        JComboBox<String> growFoodCombo = new JComboBox<>(new String[]{"", "Yes", "No", "Some"});

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

    /**
     * Creates the energy usage panel for the carbon footprint calculator.
     * @return energy usage panel.
     */
    private JPanel createEnergyPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<String> energySourceCombo = new JComboBox<>(new String[]{"", "Coal", "Natural Gas", "Nuclear", "Solar", "Wind", "Hydroelectric", "Other"});
        JComboBox<String> efficientAppliancesCombo = new JComboBox<>(new String[]{"", "All", "Most", "Some", "Few", "None"});
        JComboBox<String> ledBulbsCombo = new JComboBox<>(new String[]{"", "All", "Most", "Some", "None"});

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

    /**
     * Creates the waste and other panel for the carbon footprint calculator.
     * @return waste and other panel.
     */
    private JPanel createWastePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<String> recycleCombo = new JComboBox<>(new String[]{"", "Always", "Often", "Sometimes", "Rarely", "Never"});
        JComboBox<String> compostCombo = new JComboBox<>(new String[]{"", "Yes", "No", "Sometimes"});
        JComboBox<String> clothesCombo = new JComboBox<>(new String[]{"", "Weekly", "Monthly", "Quarterly", "Yearly", "Rarely"});
        JComboBox<String> electronicsCombo = new JComboBox<>(new String[]{"", "Monthly", "Quarterly", "Yearly", "Every few years", "Rarely"});
        JComboBox<String> plasticCombo = new JComboBox<>(new String[]{"", "Daily", "Often", "Sometimes", "Rarely", "Never"});

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

    /**
     * Creates a text field and registers it in the form fields map.
     * @param fieldName  name of the field.
     * @return created text field.
     */
    private JTextField createAndRegisterTextField(String fieldName) {
        JTextField textField = new JTextField(10);
        formFields.put(fieldName, textField);
        return textField;
    }

    /**
     * Adds a form field with a sample text to panel.
     * @param panel The panel to add the field to.
     * @param labelText The label text for the field.
     * @param inputComponent input component (e.g., text field or combo box).
     * @param sampleText sample text to display.
     */
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

    /**
     * Creates the hero section of the application.
     */
    private void createHeroSection() {
        JPanel heroPanel = new JPanel();
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.X_AXIS));
        heroPanel.setBackground(Color.WHITE);
        heroPanel.setBorder(new EmptyBorder(0, 40, 40, 40));
        heroPanel.setMaximumSize(new Dimension(850, 600));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

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
        }

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

    /**
     * Creates the navigation bar for the application.
     */
    private void createNavBar() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        navPanel.setBackground(Color.WHITE);
        navPanel.setMaximumSize(new Dimension(800, 70));
        navPanel.setPreferredSize(new Dimension(800, 70));

        JLabel logoLabel = new JLabel("EcoSpark");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        logoLabel.setBorder(new EmptyBorder(0, 20, 0, 0));

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

        profileButton.addActionListener(e -> {
            if (currentUser != null) {
                createProfileEditPanel();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please log in to view your profile",
                        "Login Required",
                        JOptionPane.INFORMATION_MESSAGE);
                createLoginPanel();
            }
        });

        profilePanel.add(profileButton);
        navPanel.add(logoLabel, BorderLayout.WEST);
        navPanel.add(profilePanel, BorderLayout.EAST);
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

    /**
     * Creates the profile edit panel for the logged-in user.
     */
    private void createProfileEditPanel() {
        getContentPane().remove(scrollPane);

        JPanel profileEditPanel = new JPanel();
        profileEditPanel.setLayout(new BoxLayout(profileEditPanel, BoxLayout.Y_AXIS));
        profileEditPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        profileEditPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Edit Your Profile");
        titleLabel.setFont(UIConstants.SUBHEADER_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // User Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.setMaximumSize(new Dimension(500, 150));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(UIConstants.BUTTON_FONT);
        JTextField nameField = new JTextField(currentUser.getName());

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(UIConstants.BUTTON_FONT);
        JTextField emailField = new JTextField(currentUser.getEmail());
        emailField.setEditable(false); // Email should not be editable

        JLabel passwordLabel = new JLabel("New Password:");
        JPasswordField passwordField = new JPasswordField();

        infoPanel.add(nameLabel);
        infoPanel.add(nameField);
        infoPanel.add(emailLabel);
        infoPanel.add(emailField);
        infoPanel.add(passwordLabel);
        infoPanel.add(passwordField);

        // Points Panel
        JPanel pointsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pointsPanel.setBackground(Color.WHITE);
        pointsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pointsLabel = new JLabel("Total Points: " + currentUser.getPoints());
        pointsLabel.setFont(UIConstants.SUBHEADER_FONT);
        pointsLabel.setForeground(UIConstants.GREEN_CHECK);
        pointsPanel.add(pointsLabel);

        // Completed Tasks Panel
        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        tasksPanel.setBackground(Color.WHITE);
        tasksPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Completed Tasks",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                UIConstants.BUTTON_FONT
        ));
        tasksPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tasksPanel.setMaximumSize(new Dimension(600, 250));

        JPanel taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        taskListPanel.setBackground(Color.WHITE);

        List<Task> completedTasks = currentUser.getCompletedTasks();
        if (completedTasks.isEmpty()) {
            JLabel noTasksLabel = new JLabel("No tasks completed yet");
            noTasksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noTasksLabel.setFont(UIConstants.BUTTON_FONT);
            taskListPanel.add(noTasksLabel);
        } else {
            for (Task task : completedTasks) {
                JPanel taskItem = createTaskListItem(task);
                taskListPanel.add(taskItem);
                taskListPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        JScrollPane taskScrollPane = new JScrollPane(taskListPanel);
        taskScrollPane.setBorder(BorderFactory.createEmptyBorder());
        taskScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        taskScrollPane.setPreferredSize(new Dimension(0, 200));
        taskScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        tasksPanel.add(taskScrollPane);

        // Badges Panel
        JPanel badgesPanel = new JPanel();
        badgesPanel.setLayout(new BoxLayout(badgesPanel, BoxLayout.Y_AXIS));
        badgesPanel.setBackground(Color.WHITE);
        badgesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Your Badges",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                UIConstants.BUTTON_FONT
        ));
        badgesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        badgesPanel.setMaximumSize(new Dimension(600, 250));

        JPanel badgeGridPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        badgeGridPanel.setBackground(Color.WHITE);

        List<String> userBadges = currentUser.getBadges();
        if (userBadges.isEmpty()) {
            JLabel noBadgesLabel = new JLabel("No badges earned yet");
            noBadgesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noBadgesLabel.setFont(UIConstants.BUTTON_FONT);
            badgeGridPanel.add(noBadgesLabel);
        } else {
            for (String badgeName : userBadges) {
                JPanel badgeItem = createBadgeItem(badgeName);
                badgeGridPanel.add(badgeItem);
            }
        }

        JScrollPane badgeScrollPane = new JScrollPane(badgeGridPanel);
        badgeScrollPane.setBorder(BorderFactory.createEmptyBorder());
        badgeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        badgeScrollPane.setPreferredSize(new Dimension(0, 200));
        badgeScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        badgesPanel.add(badgeScrollPane);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        JButton saveButton = new RoundedButton("Save Changes", UIConstants.BUTTON_BLUE, Color.WHITE);
        JButton cancelButton = new RoundedButton("Cancel", Color.WHITE, UIConstants.TEXT_COLOR);
        JButton deleteButton = new RoundedButton("Delete Profile", Color.RED, Color.WHITE); // Delete button
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        JLabel statusLabel = new JLabel(" ");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        profileEditPanel.add(titleLabel);
        profileEditPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        profileEditPanel.add(infoPanel);
        profileEditPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        profileEditPanel.add(pointsPanel);
        profileEditPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        profileEditPanel.add(tasksPanel);
        profileEditPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        profileEditPanel.add(badgesPanel);
        profileEditPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        profileEditPanel.add(buttonPanel);
        profileEditPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        profileEditPanel.add(statusLabel);

        saveButton.addActionListener(e -> {
            String newName = nameField.getText();
            String newPassword = new String(passwordField.getPassword());

            if (newName.isEmpty()) {
                statusLabel.setText("Name cannot be empty");
                statusLabel.setForeground(Color.RED);
                return;
            }

            currentUser.setName(newName);
            if (!newPassword.isEmpty()) {
                currentUser.setPassword(newPassword);
            }

            userManager.updateProfile(currentUser);

            statusLabel.setText("Profile updated successfully!");
            statusLabel.setForeground(Color.GREEN);

            Timer timer = new Timer(1000, event -> {
                getContentPane().remove(profileEditPanel);
                getContentPane().add(scrollPane);
                mainPanel.removeAll();
                createNavBar();
                createHeroSection();
                createOfferSection();
                createAboutSection();
                revalidate();
                repaint();
            });
            timer.setRepeats(false);
            timer.start();
        });

        cancelButton.addActionListener(e -> {
            getContentPane().remove(profileEditPanel);
            getContentPane().add(scrollPane);
            revalidate();
            repaint();
        });

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete your profile? This action cannot be undone.",
                    "Delete Profile",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                userManager.deleteProfile(currentUser.getEmail());
                currentUser = null;
                model.setCurrentUser(null);

                getContentPane().remove(profileEditPanel);
                createLoginPanel();
                revalidate();
                repaint();

                JOptionPane.showMessageDialog(
                        this,
                        "Profile deleted successfully!",
                        "Profile Deleted",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        getContentPane().add(profileEditPanel);
        revalidate();
        repaint();
    }

    private JPanel createTaskListItem(Task task) {
        JPanel taskItem = new JPanel(new BorderLayout(10, 0));
        taskItem.setBackground(Color.WHITE);
        taskItem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 5, 8, 5)
        ));
        taskItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JPanel iconPanel = createColorfulTaskIcon(task.getType());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel(task.getTitle());
        titleLabel.setFont(UIConstants.BUTTON_FONT);
        JLabel pointsLabel = new JLabel("+" + task.getPointsValue() + " pts");
        pointsLabel.setFont(UIConstants.BUTTON_FONT);
        pointsLabel.setForeground(new Color(33, 150, 83));
        detailsPanel.add(titleLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        detailsPanel.add(pointsLabel);
        taskItem.add(iconPanel, BorderLayout.WEST);
        taskItem.add(detailsPanel, BorderLayout.CENTER);

        return taskItem;
    }

    private JPanel createBadgeItem(String badgeName) {
        Color badgeColor = getBadgeColor(badgeName);
        String badgeIcon = getBadgeIcon(badgeName);
        JPanel badgePanel = new JPanel();
        badgePanel.setLayout(new BoxLayout(badgePanel, BoxLayout.Y_AXIS));
        badgePanel.setBackground(Color.WHITE);

        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(badgeColor);
                g2d.fillOval(0, 0, getWidth(), getHeight());
            }
        };

        iconPanel.setPreferredSize(new Dimension(50, 50));
        iconPanel.setMaximumSize(new Dimension(50, 50));
        iconPanel.setMinimumSize(new Dimension(50, 50));
        iconPanel.setBackground(Color.WHITE);

        JLabel iconLabel = new JLabel(badgeIcon);
        iconLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        iconLabel.setForeground(Color.WHITE);
        iconPanel.setLayout(new GridBagLayout());
        iconPanel.add(iconLabel);

        JLabel nameLabel = new JLabel(badgeName, JLabel.CENTER);
        nameLabel.setFont(UIConstants.BUTTON_FONT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        badgePanel.add(iconPanel);
        badgePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        badgePanel.add(nameLabel);

        return badgePanel;
    }

    private JPanel createColorfulTaskIcon(String taskType) {
        JPanel iconPanel = new JPanel();
        iconPanel.setPreferredSize(new Dimension(40, 40));
        iconPanel.setMaximumSize(new Dimension(40, 40));
        iconPanel.setMinimumSize(new Dimension(40, 40));
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

        final Color finalBgColor = bgColor;
        final String finalIconText = iconText;

        JPanel colorPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(finalBgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }
        };

        colorPanel.setLayout(new GridBagLayout());

        JLabel iconLabel = new JLabel(finalIconText);
        iconLabel.setFont(UIConstants.BUTTON_FONT);
        colorPanel.add(iconLabel);
        iconPanel.setLayout(new BorderLayout());
        iconPanel.add(colorPanel, BorderLayout.CENTER);
        return iconPanel;
    }

    private Color getBadgeColor(String badgeName) {
        switch (badgeName.toLowerCase()) {
            case "novice":
                return new Color(173, 216, 230);
            case "beginner":
                return new Color(135, 206, 235);
            case "intermediate":
                return new Color(0, 191, 255);
            case "advanced":
                return new Color(30, 144, 255);
            case "expert":
                return new Color(0, 0, 205);
            case "master":
                return new Color(75, 0, 130);
            case "community champion":
            case "community":
                return new Color(135, 206, 250);
            case "eco learner":
            case "learning":
                return new Color(173, 216, 230);
            case "green innovator":
            case "innovation":
                return new Color(152, 251, 152);
            case "water protector":
            case "water":
                return new Color(30, 144, 255);
            case "energy saver":
            case "energy":
                return new Color(255, 215, 0);
            case "zero waste hero":
            case "waste":
                return new Color(255, 182, 193);
            default:
                return new Color(33, 150, 83); // Primary color for unknown badges
        }
    }

    private String getBadgeIcon(String badgeName) {
        // This matches the badge icons from the DashboardPanel
        switch (badgeName.toLowerCase()) {
            case "novice":
                return "🌱";
            case "beginner":
                return "🌿";
            case "intermediate":
                return "🌍";
            case "advanced":
                return "🌊";
            case "expert":
                return "⭐";
            case "master":
                return "🏆";
            case "community champion":
            case "community":
                return "🤝";
            case "eco learner":
            case "learning":
                return "🧩";
            case "green innovator":
            case "innovation":
                return "💡";
            case "water protector":
            case "water":
                return "💧";
            case "energy saver":
            case "energy":
                return "⚡";
            case "zero waste hero":
            case "waste":
                return "♻️";
            case "biodiversity guardian":
            case "biodiversity":
                return "🌺";
            case "environmental advocate":
            case "advocacy":
                return "🏅";
            case "climate defender":
            case "climate":
                return "🌀";
            case "ocean defender":
            case "oceans":
                return "🐠";
            case "plastic fighter":
            case "plastic":
                return "🚫";
            case "forest guardian":
            case "forest":
                return "🌲";
            default:
                return "🌱";
        }
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

        JPanel offerCards = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        offerCards.setBackground(Color.WHITE);
        offerCards.setMaximumSize(new Dimension(900, 835)); // Ensures centering

        JPanel carbonFootprintButton = createImageButton(
                "/com/hillcrest/visuals/carbon_footprint_button_graphic.png",
                "Calculate carbon footprint",
                e -> {

                    if (isUserLoggedIn()) {
                        createCarbonFootprintPanel();
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Please log in to access your dashboard.",
                                "Login Required",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
        );

        JPanel quizButton = createImageButton(
                "/com/hillcrest/visuals/informative_resources_button_graphic.png",
                "Test your knowledge",
                e -> {
                    quizPanel = createQuizPanel();
                    quizFrame = new JFrame("EcoSpark Quiz");
                    quizFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    quizFrame.setSize(600, 400);
                    quizFrame.setLayout(new BorderLayout());
                    quizFrame.add(quizPanel, BorderLayout.CENTER);
                    quizFrame.setLocationRelativeTo(null);
                    quizFrame.setVisible(true);
                });

        JPanel actionsButton = createImageButton(
                "/com/hillcrest/visuals/take_action.png",
                "Take personalized actions",
                e -> {

                    if (isUserLoggedIn()) {
                        JFrame dashboardFrame = new JFrame("🌲" + currentUser.getName() + "'s Dashboard 🌲");
                        dashboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        dashboardFrame.setSize(1000, 1000);

                        DashboardPanel dashboardPanel = new DashboardPanel(model, currentUser);
                        dashboardFrame.add(dashboardPanel);
                        dashboardFrame.setLocationRelativeTo(null);
                        dashboardFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Please log in to calculate your carbon footprint.",
                                "Login Required",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
        );

        offerCards.add(carbonFootprintButton);
        offerCards.add(quizButton);
        offerCards.add(actionsButton);
        offerPanel.add(offerTitle);
        offerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        offerPanel.add(offerCards);
        mainPanel.add(offerPanel);
    }



    private JPanel createImageButton(String imagePath, String title, ActionListener action) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JButton button = new JButton();
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(250, 250)); // Keep buttons uniform

        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image originalImage = originalIcon.getImage();
            int originalWidth = originalImage.getWidth(null);
            int originalHeight = originalImage.getHeight(null);
            int newWidth = 200;
            int newHeight = (int) ((double) originalHeight / originalWidth * newWidth);
            ImageIcon icon = new ImageIcon(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Image not found.");
        }

        button.addActionListener(action);

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(UIConstants.BUTTON_FONT);
        titleLabel.setPreferredSize(new Dimension(250, 30));
        panel.add(button, BorderLayout.CENTER);
        panel.add(titleLabel, BorderLayout.SOUTH);

        return panel;
    }

    private void createAboutSection() {
        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.X_AXIS));
        aboutPanel.setBackground(Color.WHITE);
        aboutPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        aboutPanel.setMaximumSize(new Dimension(800, 400));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel learnMore = new JLabel("Learn more");
        learnMore.setFont(UIConstants.BUTTON_FONT);
        learnMore.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel aboutLabel = new JLabel("About");
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
}