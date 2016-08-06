package tabbedpaneuidemo;

import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * UI customization for tabbed pane. reference: http://stackoverflow.com/questions/2637221/override-default-look-and-feel-java <br/>
 *
 * @author skorkmaz
 */
public class MyTabbedPaneUI extends BasicTabbedPaneUI {

    /**
     * NOTE: Do not perform lengthy operations (e.g. setting font to bold) inside this paint method because it causes 100% CPU load and has side effects like not being
     * able to do other CPU intensive tasks like drawing 3D models.
     */
    protected void paintTabBackground2(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        for (int i = 0; i < tabPane.getTabCount(); i++) {
            Color bgColor = Color.YELLOW;
            if (i != tabIndex) {
                bgColor = Color.GREEN;
            }
            Rectangle rect = rects[i];
            int pad = 2;
            g.setColor(bgColor);
            g.fillRect(rect.x + pad, rect.y + pad, rect.width - 2 * pad, rect.height - 2 * pad);
        }
    }
    
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        for (int i = 0; i < tabPane.getTabCount(); i++) {
            Color bgColor = Color.YELLOW;
            JPanel jp = (JPanel) tabPane.getTabComponentAt(i);
            if (jp != null) {
                JLabel jl = (JLabel) jp.getComponent(0);
                if (i != tabIndex) {
                    bgColor = Color.GREEN;
                    jl.setFont(jl.getFont().deriveFont(Font.BOLD));
                } else {
                    jl.setFont(jl.getFont().deriveFont(Font.PLAIN));
                }
            }

            Rectangle rect = rects[i];
            int pad = 2;
            g.setColor(bgColor);
            g.fillRect(rect.x + pad, rect.y + pad, rect.width - 2 * pad, rect.height - 2 * pad);
        }
    }

    /**
     * Get a foreground color that is readable for input bgColor, i.e. white if bgColor is dark and black if bgColor is light.<br/>
     * http://stackoverflow.com/questions/1855884/determine-font-color-based-on-background-color
     */
    public static Color getForegroundColor(Color bgColor) {
        Color fgColor = Color.BLACK;
        // Counting the perceptive luminance - human eye favors green color... 
        double a = 1 - (0.299 * bgColor.getRed() + 0.587 * bgColor.getGreen() + 0.114 * bgColor.getBlue()) / 255;

        if (a >= 0.5) {
            fgColor = Color.WHITE; // dark colors - white font
        }
        return fgColor;
    }

}
