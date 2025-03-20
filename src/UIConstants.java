import java.awt.*;
import java.io.InputStream;

/**
 * Defines the color and font constants used in the EcoSparkApp to
 * give the application a more aesthetic appeal.
 *
 * Uses the imported DM_Sans and Poppins fonts
 */
public class UIConstants {

    static final Color PRIMARY_COLOR = new Color(70, 130, 95);
    static final Color ACCENT_COLOR = new Color(95, 102, 177);
    static final Color BUTTON_BLUE = new Color(65, 90, 230);
    static final Color TEXT_COLOR = new Color(30, 30, 30);
    static final Color BRIGHT_BLUE = new Color(81, 146, 207);
    static final Color GREEN_CHECK = new Color(120, 180, 120);

    public static final Font HEADER_FONT;
    public static final Font SUBHEADER_FONT;
    public static final Font BODY_FONT;
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);

    static {
        Font headerFont = new Font("Arial", Font.BOLD, 36);
        Font subheaderFont = new Font("Arial", Font.BOLD, 19);
        Font bodyFont = new Font("Arial", Font.PLAIN, 14);

        try {
            InputStream poppinsStream = UIConstants.class.getResourceAsStream("/Poppins/Poppins-Medium.ttf");
            if (poppinsStream != null) {
                headerFont = Font.createFont(Font.TRUETYPE_FONT, poppinsStream).deriveFont(36f);
                subheaderFont = headerFont.deriveFont(26f);
            }

            InputStream dmSansStream = UIConstants.class.getResourceAsStream("/DM_Sans/DMSans-VariableFont_opsz,wght.ttf");
            if (dmSansStream != null) {
                bodyFont = Font.createFont(Font.TRUETYPE_FONT, dmSansStream).deriveFont(14f);
            }

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(headerFont);
            ge.registerFont(subheaderFont);
            ge.registerFont(bodyFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HEADER_FONT = headerFont;
        SUBHEADER_FONT = subheaderFont;
        BODY_FONT = bodyFont;
    }
}
