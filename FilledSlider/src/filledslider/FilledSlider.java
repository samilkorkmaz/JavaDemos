package filledslider;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * Slider that fills the left and right sides with different colors.
 *
 * @author skorkmaz, 2013
 */
class FilledSlider extends JSlider {

    {
        putClientProperty("JSlider.isFilled", Boolean.TRUE);
    }

    public FilledSlider(Color leftFillColor, Color rightFillColor) {
        setUI(new FilledSliderUI(this, leftFillColor, rightFillColor));
    }
}

class FilledSliderUI extends BasicSliderUI {

    private Color borderColor = Color.BLACK;
    private Color leftFillColor;
    private Color rightFillColor;

    public FilledSliderUI(JSlider jSlider, Color leftFillColor,
            Color rightFillColor) {
        super(jSlider);
        this.leftFillColor = leftFillColor;
        this.rightFillColor = rightFillColor;
    }

    @Override
    public void paintFocus(Graphics g) {
        //Do nothing, i.e. don't draw dashed rectangle around slider.
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        int dx = (slider.getWidth() - trackRect.width);
        int xLeftStart = dx / 2;
        double valueRange = slider.getMaximum() - slider.getMinimum();
        double normalizedValue = (slider.getValue() - slider.getMinimum())
                / valueRange;
        int xLeftEnd = (int) (normalizedValue * trackRect.width);
        g2D.setColor(leftFillColor);
        g2D.fill(new Rectangle2D.Double(xLeftStart, trackRect.y,
                xLeftEnd, trackRect.height));

        int xRightStart = xLeftEnd + dx / 2;
        int xRightEnd = slider.getWidth() - xRightStart - dx / 2;
        g2D.setColor(rightFillColor);
        g2D.fill(new Rectangle2D.Double(xRightStart, trackRect.y,
                xRightEnd, trackRect.height));
        /* Draw slider border. Notice that jSlider.getWidth() is larger than 
         * trackRect.width*/
        g2D.setColor(borderColor);
        g2D.draw(new Rectangle2D.Double(0, trackRect.y,
                slider.getWidth() - 1, trackRect.height));
    }
}