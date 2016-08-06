package tabbedpaneuidemo;

import java.awt.*;
import javax.swing.plaf.basic.*;

public class CustomTabbedPaneUI2 extends BasicTabbedPaneUI {

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        /*
        int inclTab = 4;
        Polygon shape;
        Graphics2D g2D = (Graphics2D) g;
        int[] xp = new int[]{x, x, x + 3, x + w - inclTab - 6, x + w - inclTab - 2, x + w - inclTab, x + w - inclTab, x};
        int[] yp = new int[]{y + h, y + 3, y, y, y + 1, y + 3, y + h, y + h};        
        Color selectColor = Color.RED;
        //Color deSelectColor = new Color(0, 120, 0, 50);
        Color deSelectColor = new Color(0, 200, 0);
        shape = new Polygon(xp, yp, xp.length);
        if (isSelected) {
            g2D.setColor(selectColor);
        } else {
            if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
                g2D.setColor(deSelectColor);
            }
        }        
        g2D.fill(shape);*/
        for (int i = 0; i < tabPane.getTabCount(); i++) {
            Color bgColor = Color.RED;
            if (i != tabIndex) {
                bgColor = new Color(0, 200, 0, 50);
            }
            Rectangle rect = rects[i];
            int pad = 2;
            g.setColor(bgColor);
            g.fillRect(rect.x + pad, rect.y + pad, rect.width - 2 * pad, rect.height - 2 * pad);
        }
    }
    
    
}
