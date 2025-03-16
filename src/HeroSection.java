import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HeroSection extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public HeroSection(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(0, 40, 40, 40));
        setMaximumSize(new Dimension(800, 400));

        // Text section
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome to EcoSpark");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

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
        descriptionText.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionText.setBackground(Color.WHITE);
        descriptionText.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionText.setMaximumSize(new Dimension(350, 150));

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        JButton joinButton = new JButton("JOIN NOW");
        joinButton.setBackground(new Color(0, 120, 215));
        joinButton.setForeground(Color.WHITE);
        joinButton.setFont(new Font("Arial", Font.BOLD, 14));
        joinButton.setFocusPainted(false);
        joinButton.setBorderPainted(false);
        joinButton.setPreferredSize(new Dimension(150, 40));
        joinButton.setMaximumSize(new Dimension(150, 40));

        JButton loginButton = new JButton("LOG IN");
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.setMaximumSize(new Dimension(150, 40));

        buttonPanel.add(joinButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        buttonPanel.add(loginButton);

        textPanel.add(welcomeLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        textPanel.add(descriptionText);
        textPanel.add(buttonPanel);

        // Image section
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setLayout(new FlowLayout());

        JLabel imageLabel = new JLabel();
        try {
            ImageIcon homeGraphic = new ImageIcon(new ImageIcon(getClass().getResource("/com/hillcrest/visuals/homepagegraphic.png"))
                    .getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
            imageLabel.setIcon(homeGraphic);
        } catch (Exception e) {
            imageLabel.setText("Image Not Found");
            imageLabel.setFont(new Font("Arial", Font.BOLD, 14));
            imageLabel.setPreferredSize(new Dimension(300, 400));
            imageLabel.setBackground(new Color(220, 240, 255));
            imageLabel.setOpaque(true);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        imagePanel.add(imageLabel);

        add(textPanel);
        add(Box.createRigidArea(new Dimension(20, 0)));
        add(imagePanel);

        // Action listeners for the buttons
        joinButton.addActionListener(e -> {
            // Switch to registration screen
            cardLayout.show(mainPanel, "register");
        });

        loginButton.addActionListener(e -> {
            // Switch to login screen
            cardLayout.show(mainPanel, "login");
        });
    }
}


//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//
//public class HeroSection extends JPanel {
//    public HeroSection() {
//        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        setBackground(Color.WHITE);
//        setBorder(new EmptyBorder(0, 40, 40, 40));
//        setMaximumSize(new Dimension(800, 400));
//
//        // Text section
//        JPanel textPanel = new JPanel();
//        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
//        textPanel.setBackground(Color.WHITE);
//        textPanel.setAlignmentY(Component.TOP_ALIGNMENT);
//
//        JLabel welcomeLabel = new JLabel("Welcome to EcoSpark");
//        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
//        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        JTextArea descriptionText = new JTextArea(
//                "Climate change is one of our world's most pressing issues. It " +
//                        "threatens our forests, our freshwater supply, and our planet's " +
//                        "livelihood. EcoSpark was made with our Earth in mind; we strive " +
//                        "to create a community that supports, educates, and inspires our " +
//                        "users to visualize their unique impact on climate change. Be the " +
//                        "change, one step at a time."
//        );
//        descriptionText.setWrapStyleWord(true);
//        descriptionText.setLineWrap(true);
//        descriptionText.setEditable(false);
//        descriptionText.setFocusable(false);
//        descriptionText.setFont(new Font("Arial", Font.PLAIN, 14));
//        descriptionText.setBackground(Color.WHITE);
//        descriptionText.setAlignmentX(Component.LEFT_ALIGNMENT);
//        descriptionText.setMaximumSize(new Dimension(350, 150));
//
//        // Buttons
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
//        buttonPanel.setBackground(Color.WHITE);
//        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        buttonPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
//
//        JButton joinButton = new JButton("JOIN NOW");
//        joinButton.setBackground(new Color(0, 120, 215));
//        joinButton.setForeground(Color.WHITE);
//        joinButton.setFont(new Font("Arial", Font.BOLD, 14));
//        joinButton.setFocusPainted(false);
//        joinButton.setBorderPainted(false);
//        joinButton.setPreferredSize(new Dimension(150, 40));
//        joinButton.setMaximumSize(new Dimension(150, 40));
//
//        JButton loginButton = new JButton("LOG IN");
//        loginButton.setBackground(Color.WHITE);
//        loginButton.setForeground(Color.BLACK);
//        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
//        loginButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//        loginButton.setFocusPainted(false);
//        loginButton.setPreferredSize(new Dimension(150, 40));
//        loginButton.setMaximumSize(new Dimension(150, 40));
//
//        buttonPanel.add(joinButton);
//        buttonPanel.add(Box.createRigidArea(new Dimension(15, 0)));
//        buttonPanel.add(loginButton);
//
//        textPanel.add(welcomeLabel);
//        textPanel.add(Box.createRigidArea(new Dimension(0, 20)));
//        textPanel.add(descriptionText);
//        textPanel.add(buttonPanel);
//
//        // Image section
//        JPanel imagePanel = new JPanel();
//        imagePanel.setBackground(Color.WHITE);
//        imagePanel.setLayout(new FlowLayout());
//
//        JLabel imageLabel = new JLabel();
//        try {
//            ImageIcon homeGraphic = new ImageIcon(new ImageIcon(getClass().getResource("/homepagegraphic.png"))
//                    .getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
//            imageLabel.setIcon(homeGraphic);
//        } catch (Exception e) {
//            imageLabel.setText("Image Not Found");
//            imageLabel.setFont(new Font("Arial", Font.BOLD, 14));
//            imageLabel.setPreferredSize(new Dimension(300, 400));
//            imageLabel.setBackground(new Color(220, 240, 255));
//            imageLabel.setOpaque(true);
//            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        }
//
//        imagePanel.add(imageLabel);
//
//        add(textPanel);
//        add(Box.createRigidArea(new Dimension(20, 0)));
//        add(imagePanel);
//    }
//}