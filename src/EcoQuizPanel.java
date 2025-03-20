import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class EcoQuizPanel extends JPanel {
    private QuizBackend backend;
    private JPanel contentPanel;
    private JLabel questionLabel;
    private JLabel questionNumberLabel;
    private JLabel categoryLabel;
    private AnimatedButton[] optionButtons;
    private JPanel progressPanel;
    private JPanel buttonPanel;
    private JPanel feedbackPanel;
    private JLabel feedbackLabel;
    private Timer feedbackTimer;
    private JButton submitButton;
    private String selectedAnswer;
    private Color currentGradientStart = new Color(255, 90, 130); // Pink
    private Color currentGradientEnd = new Color(90, 110, 255);   // Purple-blue

    // Cute eco-themed emoji icons
    private String[] ecoIcons = {"🌱", "🌲", "🌍", "♻️", "🌊", "🌞", "🐝", "🦋", "🐢", "🌻"};

    public EcoQuizPanel() {
        backend = new QuizBackend();
        initializeUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Create an animated gradient background
        GradientPaint gradient = new GradientPaint(
                0, 0, currentGradientStart,
                getWidth(), getHeight(), currentGradientEnd
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Add some subtle texture/pattern
        g2d.setColor(new Color(255, 255, 255, 15));
        for (int i = 0; i < getWidth(); i += 20) {
            for (int j = 0; j < getHeight(); j += 20) {
                if ((i + j) % 40 == 0) {
                    g2d.fillOval(i, j, 5, 5);
                }
            }
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(40, 40, 40, 40));

        // Create container panel with shadow effect
        contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create a soft rounded rectangle for the panel
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                        0, 0, getWidth(), getHeight(), 30, 30);

                // Fill with semi-transparent white
                g2d.setColor(new Color(255, 255, 255, 230));
                g2d.fill(roundedRectangle);

                // Add a subtle shadow effect
                g2d.setColor(new Color(0, 0, 0, 10));
                for (int i = 0; i < 5; i++) {
                    g2d.draw(new RoundRectangle2D.Float(
                            i, i, getWidth() - 2 * i, getHeight() - 2 * i, 30, 30));
                }
            }
        };
        contentPanel.setLayout(new BorderLayout(0, 20));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Create header with category and progress
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        // Category label with cute icon
        categoryLabel = new JLabel();
        categoryLabel.setFont(createFancyFont(16, Font.BOLD));
        categoryLabel.setForeground(new Color(90, 90, 120));
        headerPanel.add(categoryLabel, BorderLayout.WEST);

        // Question counter
        questionNumberLabel = new JLabel();
        questionNumberLabel.setFont(createFancyFont(16, Font.BOLD));
        questionNumberLabel.setForeground(new Color(90, 90, 120));
        questionNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        headerPanel.add(questionNumberLabel, BorderLayout.EAST);

        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // Create question panel with fancy styling
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setOpaque(false);
        questionPanel.setBorder(new EmptyBorder(10, 0, 30, 0));

        questionLabel = new JLabel();
        questionLabel.setFont(createFancyFont(28, Font.BOLD));
        questionLabel.setForeground(new Color(50, 50, 80));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setBorder(new EmptyBorder(20, 10, 20, 10));

        // Add a cute bubble effect around the question
        JPanel questionBubble = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create a soft rounded rectangle
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                        0, 0, getWidth(), getHeight(), 25, 25);

                // Fill with light color
                g2d.setColor(new Color(245, 245, 255));
                g2d.fill(roundedRectangle);

                // Add a subtle border
                g2d.setColor(new Color(200, 200, 230));
                g2d.draw(roundedRectangle);
            }
        };
        questionBubble.setLayout(new BorderLayout());
        questionBubble.setOpaque(false);
        questionBubble.add(questionLabel);

        questionPanel.add(Box.createVerticalGlue());
        questionPanel.add(questionBubble);
        questionPanel.add(Box.createVerticalGlue());

        contentPanel.add(questionPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 15));
        buttonPanel.setOpaque(false);

// Create animated buttons for options
        optionButtons = new AnimatedButton[4];
        ButtonGroup buttonGroup = new ButtonGroup();

        String[] optionLabels = {"A", "B", "C", "D"}; // Labels for the options

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new AnimatedButton(optionLabels[i]); // Pass the label
            optionButtons[i].setFont(createFancyFont(18, Font.PLAIN));
            optionButtons[i].setForeground(new Color(60, 60, 80));
            optionButtons[i].setBackground(Color.WHITE);
            optionButtons[i].setFocusPainted(false);
            optionButtons[i].setBorderPainted(false);
            optionButtons[i].setContentAreaFilled(false);

            // Make the buttons toggle buttons
            optionButtons[i].setModel(new JToggleButton.ToggleButtonModel());
            buttonGroup.add(optionButtons[i]);

            final int index = i;
            optionButtons[i].addActionListener(e -> {
                selectedAnswer = optionButtons[index].getText();
                // Enable submit button when an answer is selected
                submitButton.setEnabled(true);
            });

            buttonPanel.add(optionButtons[i]);
        }


        // Create feedback panel for showing correct/wrong feedback
        feedbackPanel = new JPanel();
        feedbackPanel.setOpaque(false);
        feedbackPanel.setVisible(false);

        feedbackLabel = new JLabel();
        feedbackLabel.setFont(createFancyFont(20, Font.BOLD));
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feedbackPanel.add(feedbackLabel);

        // Create bottom panel with submit button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        // Progress indicators (cute leaf icons)
        progressPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        progressPanel.setOpaque(false);
        updateProgressIndicator();
        bottomPanel.add(progressPanel, BorderLayout.NORTH);

        // Submit button with animation
        submitButton = new JButton("Submit Answer");
        submitButton.setFont(createFancyFont(18, Font.BOLD));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(130, 110, 200));
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setEnabled(false); // Disabled until an answer is selected

        // Custom UI for rounded corners and hover effects
        submitButton.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();

                // Create rounded rectangle shape
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                        0, 0, c.getWidth(), c.getHeight(), 25, 25);

                // Fill with color based on state
                if (!model.isEnabled()) {
                    g2d.setColor(new Color(180, 180, 200));
                } else if (model.isPressed()) {
                    g2d.setColor(new Color(110, 90, 180));
                } else if (model.isRollover()) {
                    g2d.setColor(new Color(150, 130, 220));
                } else {
                    g2d.setColor(b.getBackground());
                }

                g2d.fill(roundedRectangle);

                // Draw text
                FontMetrics fm = g2d.getFontMetrics();
                Rectangle textRect = new Rectangle(0, 0, c.getWidth(), c.getHeight());
                String text = SwingUtilities.layoutCompoundLabel(
                        fm, b.getText(), null, b.getVerticalAlignment(),
                        b.getHorizontalAlignment(), b.getVerticalTextPosition(),
                        b.getHorizontalTextPosition(), textRect, new Rectangle(), new Rectangle(),
                        0);

                g2d.setColor(b.getForeground());
                g2d.setFont(b.getFont());
                int x = (c.getWidth() - fm.stringWidth(text)) / 2;
                int y = (c.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(text, x, y);

                g2d.dispose();
            }
        });

        submitButton.addActionListener(e -> {
            checkAnswer();
        });

        // Add padding and center the submit button
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitPanel.setOpaque(false);
        submitPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        submitPanel.add(submitButton);

        bottomPanel.add(submitPanel, BorderLayout.CENTER);
        bottomPanel.add(feedbackPanel, BorderLayout.SOUTH);

        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);

        // Initialize feedback timer
        feedbackTimer = new Timer(1500, e -> {
            feedbackPanel.setVisible(false);
            if (backend.hasMoreQuestions()) {
                loadNextQuestion();
            } else {
                showResults();
            }
        });
        feedbackTimer.setRepeats(false);

        // Start background color animation
        startBackgroundAnimation();

        // Load the first question
        loadNextQuestion();
    }

    private Font createFancyFont(int size, int style) {
        // Create a cute, rounded font for better aesthetics
        Font baseFont = new Font("Arial Rounded MT Bold", style, size);

        // Add some text attributes for extra cuteness
        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.05); // Slightly increase letter spacing

        return baseFont.deriveFont(attributes);
    }

    private void loadNextQuestion() {
        // Update question and answer labels
        String[] questionData = backend.getCurrentQuestion();

        // Update header information
        String category = backend.getCurrentCategory();
        int questionNum = backend.getCurrentQuestionNumber();
        int totalQuestions = backend.getTotalQuestions();

        // Get a cute emoji for this category
        String emoji = ecoIcons[Math.abs(category.hashCode()) % ecoIcons.length];
        categoryLabel.setText(emoji + " " + category);
        questionNumberLabel.setText("Question " + questionNum + " of " + totalQuestions);

        // Set question
        questionLabel.setText("<html><div style='text-align: center;'>" + questionData[0] + "</div></html>");

        // Reset button state
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(questionData[i + 1]); // Set the option text
            optionButtons[i].setSelected(false);
        }

        // Clear selection and disable submit button
        selectedAnswer = null;
        submitButton.setEnabled(false);

        // Update progress indicator
        updateProgressIndicator();
    }

    private void checkAnswer() {
        backend.checkAnswer(selectedAnswer);

        // Show feedback
        boolean correct = selectedAnswer.equals(backend.getCurrentQuestion()[1]);
        feedbackLabel.setText(correct ? "✅ Correct!" : "❌ Incorrect!");
        feedbackLabel.setForeground(correct ? new Color(40, 180, 120) : new Color(220, 50, 80));
        feedbackPanel.setVisible(true);

        // Start timer to move to next question
        feedbackTimer.start();
    }

    private void updateProgressIndicator() {
        progressPanel.removeAll();

        int currentQuestion = backend.getCurrentQuestionNumber();
        int totalQuestions = backend.getTotalQuestions();
        int score = backend.getScore();

        for (int i = 1; i <= totalQuestions; i++) {
            JLabel iconLabel = new JLabel();
            if (i < currentQuestion) {
                // Completed question
                if (i <= score) {
                    // Correct answer
                    iconLabel.setText("🌿");
                } else {
                    // Wrong answer
                    iconLabel.setText("🍂");
                }
            } else if (i == currentQuestion) {
                // Current question
                iconLabel.setText("🌱");
            } else {
                // Future question
                iconLabel.setText("⚪");
            }

            iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
            progressPanel.add(iconLabel);
        }

        progressPanel.revalidate();
        progressPanel.repaint();
    }

    private void showResults() {
        // Remove all components
        removeAll();
        setLayout(new BorderLayout());

        // Create results panel
        JPanel resultsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(150, 220, 250),
                        getWidth(), getHeight(), new Color(130, 200, 160)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Add decorative elements
                g2d.setColor(new Color(255, 255, 255, 30));
                for (int i = 0; i < getWidth(); i += 40) {
                    for (int j = 0; j < getHeight(); j += 40) {
                        g2d.fillOval(i, j, 10, 10);
                    }
                }
            }
        };
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Results title
        JLabel titleLabel = new JLabel("Quiz Complete!");
        titleLabel.setFont(createFancyFont(48, Font.BOLD));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultsPanel.add(titleLabel);

        // Add some space
        resultsPanel.add(Box.createVerticalStrut(40));

        // Score information
        int score = backend.getScore();
        int totalQuestions = backend.getTotalQuestions();
        int percentage = (int) ((double) score / totalQuestions * 100);

        // Create animated score display
        JPanel scorePanel = new JPanel();
        scorePanel.setOpaque(false);
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scoreLabel = new JLabel(score + " / " + totalQuestions);
        scoreLabel.setFont(createFancyFont(72, Font.BOLD));
        scoreLabel.setForeground(new Color(255, 255, 255));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel percentLabel = new JLabel(percentage + "%");
        percentLabel.setFont(createFancyFont(36, Font.BOLD));
        percentLabel.setForeground(new Color(255, 255, 255, 200));
        percentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scorePanel.add(scoreLabel);
        scorePanel.add(percentLabel);

        // Add feedback message based on score
        JLabel feedbackLabel = new JLabel();
        feedbackLabel.setFont(createFancyFont(24, Font.PLAIN));
        feedbackLabel.setForeground(new Color(255, 255, 255));
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (percentage >= 90) {
            feedbackLabel.setText("Amazing! You're an eco-warrior! 🌍");
        } else if (percentage >= 70) {
            feedbackLabel.setText("Great job! You know your environmental stuff! 🌱");
        } else if (percentage >= 50) {
            feedbackLabel.setText("Good effort! Keep learning about our planet! 🌲");
        } else {
            feedbackLabel.setText("There's room to grow your eco-knowledge! 🌿");
        }

        // Add score and feedback to results panel
        resultsPanel.add(scorePanel);
        resultsPanel.add(Box.createVerticalStrut(30));
        resultsPanel.add(feedbackLabel);
        resultsPanel.add(Box.createVerticalStrut(50));

        // Category performance breakdown
        JLabel breakdownTitle = new JLabel("Category Performance:");
        breakdownTitle.setFont(createFancyFont(24, Font.BOLD));
        breakdownTitle.setForeground(new Color(255, 255, 255));
        breakdownTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultsPanel.add(breakdownTitle);
        resultsPanel.add(Box.createVerticalStrut(20));

        // Create panel for category bars
        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setOpaque(false);
        categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS));
        categoriesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoriesPanel.setMaximumSize(new Dimension(500, 300));

        // Get category performance data
        Map<String, Double> categoryPerformance = backend.getCategoryPerformance();


// Show categories sorted by performance
        List<Map.Entry<String, Double>> sortedCategories = new ArrayList<>(categoryPerformance.entrySet());
        sortedCategories.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

// Create performance bars
        for (Map.Entry<String, Double> entry : sortedCategories) {
            JPanel categoryBar = createCategoryPerformanceBar(entry.getKey(), entry.getValue());
            categoriesPanel.add(categoryBar);
            categoriesPanel.add(Box.createVerticalStrut(10));
        }

        // Add scrollable panel for categories
        JScrollPane scrollPane = new JScrollPane(categoriesPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setMaximumSize(new Dimension(500, 200));

        resultsPanel.add(scrollPane);
        resultsPanel.add(Box.createVerticalStrut(40));

        // Add restart button
        JButton restartButton = new JButton("Play Again");
        restartButton.setFont(createFancyFont(20, Font.BOLD));
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(new Color(50, 180, 150));
        restartButton.setFocusPainted(false);
        restartButton.setBorderPainted(false);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setMaximumSize(new Dimension(200, 50));

        // Add custom UI for button
        restartButton.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();

                // Create rounded rectangle shape
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                        0, 0, c.getWidth(), c.getHeight(), 25, 25);

                // Fill with color based on state
                if (model.isPressed()) {
                    g2d.setColor(new Color(40, 150, 120));
                } else if (model.isRollover()) {
                    g2d.setColor(new Color(60, 200, 170));
                } else {
                    g2d.setColor(b.getBackground());
                }

                g2d.fill(roundedRectangle);

                // Draw text
                FontMetrics fm = g2d.getFontMetrics();
                Rectangle textRect = new Rectangle(0, 0, c.getWidth(), c.getHeight());
                String text = SwingUtilities.layoutCompoundLabel(
                        fm, b.getText(), null, b.getVerticalAlignment(),
                        b.getHorizontalAlignment(), b.getVerticalTextPosition(),
                        b.getHorizontalTextPosition(), textRect, new Rectangle(), new Rectangle(),
                        0);

                g2d.setColor(b.getForeground());
                g2d.setFont(b.getFont());
                int x = (c.getWidth() - fm.stringWidth(text)) / 2;
                int y = (c.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(text, x, y);

                g2d.dispose();
            }
        });

        restartButton.addActionListener(e -> {
            // Reset the quiz
            removeAll();
            initializeUI();
            revalidate();
            repaint();
        });

        resultsPanel.add(restartButton);

        add(resultsPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private JPanel createCategoryPerformanceBar(String category, double performance) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);

        // Get emoji for category
        String emoji = ecoIcons[Math.abs(category.hashCode()) % ecoIcons.length];

        JLabel nameLabel = new JLabel(emoji + " " + category);
        nameLabel.setFont(createFancyFont(14, Font.BOLD));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setPreferredSize(new Dimension(150, 25));

        JPanel barPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background (empty) bar
                g2d.setColor(new Color(255, 255, 255, 100));
                RoundRectangle2D bg = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10);
                g2d.fill(bg);

                // Filled portion of bar
                int fillWidth = (int) (getWidth() * performance);
                g2d.setColor(getColorForPerformance(performance));
                RoundRectangle2D fill = new RoundRectangle2D.Float(0, 0, fillWidth, getHeight(), 10, 10);
                g2d.fill(fill);

                // Add performance percentage text
                String text = Math.round(performance * 100) + "%";
                g2d.setFont(createFancyFont(12, Font.BOLD));
                g2d.setColor(Color.WHITE);

                FontMetrics fm = g2d.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(text)) / 2;
                int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

                g2d.drawString(text, textX, textY);
            }
        };
        barPanel.setPreferredSize(new Dimension(250, 25));

        panel.add(nameLabel, BorderLayout.WEST);
        panel.add(barPanel, BorderLayout.CENTER);

        return panel;
    }

    private Color getColorForPerformance(double performance) {
        // Return a color from red to green based on performance
        int red = (int) (255 * (1 - performance));
        int green = (int) (200 * performance);
        int blue = 100;

        return new Color(red, green, blue);
    }

    private void startBackgroundAnimation() {
        // Animate the background gradient colors
        Timer colorTimer = new Timer(50, new ActionListener() {
            private float hue1 = 0.8f; // Start with purple-blue
            private float hue2 = 0.3f; // Start with greenish
            private float step = 0.0005f;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Slowly change hues
                hue1 = (hue1 + step) % 1.0f;
                hue2 = (hue2 + step) % 1.0f;

                // Update gradient colors
                currentGradientStart = Color.getHSBColor(hue1, 0.7f, 0.9f);
                currentGradientEnd = Color.getHSBColor(hue2, 0.6f, 0.8f);

                // Request repaint
                repaint();
            }
        });
        colorTimer.start();
    }

    // Custom animated button class
    class AnimatedButton extends JButton {
        private float alpha = 0.7f;
        private Timer animationTimer;

        public AnimatedButton(String text) {
            super(text);
            setupButton();
        }

        private void setupButton() {
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setHorizontalAlignment(SwingConstants.LEFT);
            setBorder(new EmptyBorder(15, 20, 15, 20));

            // Add mouse listeners for hover animations
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    startAnimation(0.9f);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    startAnimation(0.7f);
                }
            });
        }

        private void startAnimation(float targetAlpha) {
            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.stop();
            }

            final float startAlpha = alpha;
            final float diff = targetAlpha - startAlpha;
            final int steps = 10;
            final int[] count = {0};

            animationTimer = new Timer(20, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    count[0]++;
                    if (count[0] <= steps) {
                        // Linear interpolation
                        alpha = startAlpha + diff * count[0] / steps;
                        repaint();
                    } else {
                        alpha = targetAlpha;
                        repaint();
                        animationTimer.stop();
                    }
                }
            });

            animationTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create rounded rectangle shape
            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                    0, 0, getWidth(), getHeight(), 20, 20);

            // Fill with semi-transparent white
            Color bgColor = isSelected() ?
                    new Color(130, 165, 255, (int) (255 * 0.9)) :
                    new Color(255, 255, 255, (int) (255 * alpha));

            g2d.setColor(bgColor);
            g2d.fill(roundedRectangle);

            // Add a subtle outline
            g2d.setColor(isSelected() ?
                    new Color(90, 140, 255) :
                    new Color(200, 200, 220));
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.draw(roundedRectangle);

            // Add selection indicator
            if (isSelected()) {
                g2d.setColor(new Color(90, 140, 255));
                g2d.fillOval(10, getHeight() / 2 - 5, 10, 10);
            }

            // Draw text with proper padding
            g2d.setColor(new Color(60, 60, 80));
            g2d.setFont(getFont());
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(getText(), 30, getHeight() / 2 + fm.getAscent() / 2 - 2);

            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("EcoQuiz - Learn About Our Planet");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 700);
            frame.setLocationRelativeTo(null);

            EcoQuizPanel quizPanel = new EcoQuizPanel();
            frame.add(quizPanel);

            frame.setVisible(true);
        });
    }
}