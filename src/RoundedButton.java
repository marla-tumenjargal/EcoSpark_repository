import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class RoundedButton extends JButton {
    private static final int ARC_WIDTH = 10; // Adjust for roundness
    private static final int ARC_HEIGHT = 10;

    static Font HEADER_FONT;
    static Font BODY_FONT;
    static Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);

    static {
        try {
            // Header Font
            InputStream poppinsStream = EcoSparkApp.class.getResourceAsStream("/Poppins/Poppins-Medium.ttf");
            if (poppinsStream != null) {
                HEADER_FONT = Font.createFont(Font.TRUETYPE_FONT, poppinsStream).deriveFont(36f);
            } else {
                System.err.println("Poppins font file not found! Using fallback.");
                HEADER_FONT = new Font("Arial", Font.BOLD, 36);
            }

            // Body Font
            InputStream dmSansStream = EcoSparkApp.class.getResourceAsStream("/DM_Sans/DMSans-VariableFont_opsz,wght.ttf");
            if (dmSansStream != null) {
                BODY_FONT = Font.createFont(Font.TRUETYPE_FONT, dmSansStream).deriveFont(14f);
            } else {
                System.err.println("DM Sans font file not found.");
                BODY_FONT = new Font("Arial", Font.PLAIN, 14);
            }

            // Register fonts with GraphicsEnvironment
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(HEADER_FONT);
            ge.registerFont(BODY_FONT);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading custom fonts! Using fallback fonts.");
            HEADER_FONT = new Font("Arial", Font.BOLD, 36);
            BODY_FONT = new Font("Arial", Font.PLAIN, 14);
        }
    }

    public RoundedButton(String text, Color bgColor, Color fgColor) {
        super(text);
        setBackground(bgColor);
        setForeground(fgColor);
        setFont(BUTTON_FONT);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw filled rounded button
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

        // Draw thin gray border
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(1)); // 1px thin border
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT);

        super.paintComponent(g2);
        g2.dispose();
    }
}
