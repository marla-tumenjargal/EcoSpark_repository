import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class AnimatedButton extends JButton {
    private float alpha = 0.7f; // Transparency for hover effect
    private Timer animationTimer; // Timer for smooth animations
    private String optionLabel; // A, B, C, D label

    // Constructor
    public AnimatedButton(String text, String optionLabel) {
        super(text);
        this.optionLabel = optionLabel;
        setupButton();
    }

    // Initialize button properties
    private void setupButton() {
        setContentAreaFilled(false); // No default background
        setBorderPainted(false); // No border
        setFocusPainted(false); // No focus border
        setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
        setBorder(new EmptyBorder(15, 50, 15, 20)); // Add padding for the option label

        // Add mouse listeners for hover animations
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startAnimation(0.9f); // Increase transparency on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startAnimation(0.7f); // Reset transparency on exit
            }
        });
    }

    // Start smooth animation for transparency
    private void startAnimation(float targetAlpha) {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        final float startAlpha = alpha;
        final float diff = targetAlpha - startAlpha;
        final int steps = 10; // Number of animation steps
        final int[] count = {0};

        animationTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count[0]++;
                if (count[0] <= steps) {
                    // Linear interpolation for smooth transition
                    alpha = startAlpha + diff * count[0] / steps;
                    repaint(); // Redraw the button
                } else {
                    alpha = targetAlpha;
                    repaint();
                    animationTimer.stop();
                }
            }
        });

        animationTimer.start();
    }

    // Custom painting for the button
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create rounded rectangle shape
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                0, 0, getWidth(), getHeight(), 20, 20);

        // Fill with semi-transparent white
        Color bgColor = isSelected() ?
                new Color(130, 165, 255, (int) (255 * 0.9)) : // Selected color
                new Color(255, 255, 255, (int) (255 * alpha)); // Default color

        g2d.setColor(bgColor);
        g2d.fill(roundedRectangle);

        // Add a subtle outline
        g2d.setColor(isSelected() ?
                new Color(90, 140, 255) : // Selected outline color
                new Color(200, 200, 220)); // Default outline color
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.draw(roundedRectangle);

        // Draw the option label (A, B, C, D)
        g2d.setColor(new Color(90, 140, 255)); // Label color
        g2d.setFont(createFancyFont(18, Font.BOLD)); // Custom font for the label
        g2d.drawString(optionLabel, 20, getHeight() / 2 + g2d.getFontMetrics().getAscent() / 2 - 2);

        // Draw the answer text
        g2d.setColor(new Color(60, 60, 80)); // Text color
        g2d.setFont(getFont());
        g2d.drawString(getText(), 50, getHeight() / 2 + g2d.getFontMetrics().getAscent() / 2 - 2);

        g2d.dispose();
    }

    // Helper method to create a custom font
    private Font createFancyFont(int size, int style) {
        return new Font("Arial Rounded MT Bold", style, size);
    }
}