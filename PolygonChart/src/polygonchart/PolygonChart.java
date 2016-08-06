package polygonchart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 * Paints a filled polygon for input XY data with grid lines at data points. Notifies observers on mouse click. Checks if point at mouse click or point sent by
 * observer is inside polygon.
 *
 * @author skorkmaz
 */
public class PolygonChart extends JPanel {

    private boolean valuesSentByMousePress;

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = Color.BLACK;
    private static final Color TITLE_COLOR = Color.BLACK;
    private static final Color LABEL_COLOR = Color.BLACK;
    private static final Color POINT_COLOR = Color.RED;
    private static final Color ERROR_COLOR = Color.RED;
    private static final Color ENVELOPE_COLOR = new Color(0, 255, 0, getAlpha(20));
    private static final Color GRID_COLOR = new Color(0, 0, 0, getAlpha(3));

    private static final int BACKGROUND_RIGHT_GAP_PIXELS = 5;
    private static final int BACKGROUND_LEFT_GAP_PIXELS = 30;
    private static final int BACKGROUND_TOP_GAP_PIXELS = 15;
    private static final int BACKGROUND_BOTTOM_GAP_PIXELS = 25;

    private static final int ENVELOPE_RIGHT_GAP_PIXELS = BACKGROUND_RIGHT_GAP_PIXELS + 20;
    private static final int ENVELOPE_LEFT_GAP_PIXELS = BACKGROUND_LEFT_GAP_PIXELS + 30;
    private static final int ENVELOPE_TOP_GAP_PIXELS = BACKGROUND_TOP_GAP_PIXELS + 5;
    private static final int ENVELOPE_BOTTOM_GAP_PIXELS = BACKGROUND_BOTTOM_GAP_PIXELS + 20;

    private static final int XNUMBER_YOFFSET = -20;
    private static final int YNUMBER_XOFFSET = 20;
    private static final int YLABEL_XOFFSET = 15;

    private final DecimalFormat formatXData;
    private final DecimalFormat formatYData;
    private final MouseClickObservable mouseClickObservable = new MouseClickObservable();

    private final double[] xDataArray;
    private final double[] yDataArray;
    private int[] xScreenArray;
    private int[] yScreenArray;
    private int xScreenAtMouseClick;
    private int yScreenAtMouseClick;
    private final String title;
    private final String xLabel;
    private final String yLabel;
    private ImprovedPolygon polygon;

    private double maxXData;
    private double minXData;
    private double maxYData;
    private double minYData;

    private double xDataAtMouseClick = Double.NaN;
    private double yDataAtMouseClick = Double.NaN;

    private final String errorMessage;

    /**
     *
     * @param x panel x (upper left) in pixels
     * @param y panel y (upper left) in pixels
     * @param width panel width in pixels
     * @param height panel height in pixels
     * @param xDataArray x axis data values
     * @param yDataArray y axis data values
     * @param title chart title
     * @param xLabel chart x label
     * @param yLabel chart y label
     * @param formatXData format to use to display x data
     * @param formatYData format to use to display y data
     * @param font font to use to display labels and data
     */
    public PolygonChart(final int x, final int y, final int width, final int height, final double[] xDataArray, final double[] yDataArray,
            final String title, final String xLabel, final String yLabel, final DecimalFormat formatXData, final DecimalFormat formatYData, Font font,
            String errorMessage) {
        setFont(font);
        this.setBounds(x, y, width, height);
        this.addMouseListener(new EnvelopeMouseListener());
        this.xDataArray = xDataArray;
        this.yDataArray = yDataArray;
        this.title = title;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.formatXData = formatXData;
        this.formatYData = formatYData;
        createScreenCoords(xDataArray, yDataArray);
        this.errorMessage = errorMessage;
    }

    /**
     * Add observers that need values at mouse click.
     *
     * @param observer
     */
    public void addObserver(Observer observer) {
        mouseClickObservable.addObserver(observer);
    }

    public double getXDataAtMouseClick() {
        return xDataAtMouseClick;
    }

    public double getYDataAtMouseClick() {
        return yDataAtMouseClick;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackGround(g);
        drawChart(g);
        drawMouseClickPoint(g);
        drawMessage(g);
        drawLabels(g);
    }

    /**
     * Used to import xData, yData values from outside and display on the chart.
     *
     * @param xData
     * @param yData
     */
    public void setXYData(final double xData, final double yData) {
        valuesSentByMousePress = false;
        xScreenAtMouseClick = convertXDataToScreenCoord(xData);
        yScreenAtMouseClick = convertYDataToScreenCoord(yData);
        repaint();
    }

    /**
     * Draw mouse click point (a thick "+" sign).
     *
     * @param g
     */
    private void drawMouseClickPoint(Graphics g) {
        if (polygon.contains(xScreenAtMouseClick, yScreenAtMouseClick)) {
            g.setColor(POINT_COLOR);
            int r = 5;
            int x0 = xScreenAtMouseClick - r;
            int y0 = yScreenAtMouseClick - r;
            int x1 = xScreenAtMouseClick + r;
            int y1 = yScreenAtMouseClick + r;
            Graphics2D g2 = (Graphics2D) g;
            Stroke originalStroke = g2.getStroke();
            g2.setStroke(new BasicStroke(3));
            g.drawLine(x0, yScreenAtMouseClick, x1, yScreenAtMouseClick);
            g.drawLine(xScreenAtMouseClick, y0, xScreenAtMouseClick, y1);
            g2.setStroke(originalStroke);
        }
    }

    private void drawMessage(Graphics g) {
        if (!valuesSentByMousePress) {//Show message only when values are sent by an oberserver (by calling setXYData).
            if (!polygon.contains(xScreenAtMouseClick, yScreenAtMouseClick)) {
                g.setColor(ERROR_COLOR);
                FontMetrics fm = g.getFontMetrics();
                g.drawString(errorMessage, getCenterX(fm, errorMessage), getHeight() / 2);
            }
        }
    }

    private void createScreenCoords(final double[] xData, final double[] yData) {
        maxXData = max(xDataArray);
        minXData = min(xDataArray);
        maxYData = max(yDataArray);
        minYData = min(yDataArray);
        xScreenArray = new int[xData.length];
        yScreenArray = new int[yData.length];
        for (int i = 0; i < yData.length; i++) {
            xScreenArray[i] = convertXDataToScreenCoord(xData[i]);
            yScreenArray[i] = convertYDataToScreenCoord(yData[i]);
        }
        polygon = new ImprovedPolygon(xScreenArray, yScreenArray, xScreenArray.length);
    }

    private class MouseClickObservable extends Observable {

        //Note that we have to derive a custom class from observable to use change-notify properly.
        @Override
        public synchronized void setChanged() {
            super.setChanged();
        }
    }

    /**
     * Get start position for input string to be centered horizontally.
     *
     * @param fm
     * @param str
     * @return
     */
    private int getCenterX(FontMetrics fm, String str) {
        return (getWidth() - fm.stringWidth(str)) / 2;
    }

    /**
     * Get start position for input string to be centered vertically.
     *
     * @param fm
     * @param str
     * @return
     */
    private int getCenterY(FontMetrics fm, String str) {
        return (getHeight() + fm.stringWidth(str)) / 2;
    }

    private void drawLabels(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        //title:
        g.setColor(TITLE_COLOR);
        int titleXPos = getCenterX(fm, title);
        int fontHeight = fm.getHeight();
        int titleYPos = BACKGROUND_TOP_GAP_PIXELS - fontHeight / 4;
        g.drawString(title, titleXPos, titleYPos);
        //xLabel
        g.setColor(LABEL_COLOR);
        int xLabelXPos = getCenterX(fm, xLabel);
        int xLabelYPos = getHeight() - fontHeight / 2;
        g.drawString(xLabel, xLabelXPos, xLabelYPos);
        //yLabel:         
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform originalTransform = g2d.getTransform();
        g2d.rotate(Math.toRadians(-90));
        int yLabelXPos = YLABEL_XOFFSET;
        int yLabelYPos = -getCenterY(fm, yLabel);
        g.drawString(yLabel, yLabelYPos, yLabelXPos);  //Note: The (x, y) position of the String must be relative to the new (rotated) coordinate system.
        g2d.setTransform(originalTransform);
        //data
        for (int i = 0; i < yDataArray.length; i++) {
            g.setColor(LABEL_COLOR);
            String xDataStr = formatXData.format(xDataArray[i]);
            g.drawString(xDataStr, xScreenArray[i] - fm.stringWidth(xDataStr) / 2, getHeight() + XNUMBER_YOFFSET);

            String yDataStr = formatYData.format(yDataArray[i]);
            g.drawString(yDataStr, YNUMBER_XOFFSET, yScreenArray[i] + fm.getHeight() / 3);
            drawGridLines(g, xScreenArray[i], yScreenArray[i]);
        }
    }

    private void drawGridLines(Graphics g, final int x, final int y) {
        g.setColor(GRID_COLOR);
        g.drawLine(x, BACKGROUND_TOP_GAP_PIXELS, x, getHeight() - BACKGROUND_BOTTOM_GAP_PIXELS);
        g.drawLine(BACKGROUND_LEFT_GAP_PIXELS, y, getWidth() - BACKGROUND_RIGHT_GAP_PIXELS, y);
    }

    /**
     * Maps alpha (i.e. transparency) from [0, 100] to [0, 255].
     *
     * @param alpha_percent
     * @return
     */
    private static int getAlpha(final int alpha_percent) {
        if (alpha_percent < 0) {
            throw new IllegalArgumentException(String.format("alpha_percent (%d) < 0!", alpha_percent));
        }
        if (alpha_percent > 100) {
            throw new IllegalArgumentException(String.format("alpha_percent (%d) > 100!", alpha_percent));
        }
        return (int) Math.round(alpha_percent / 100.0 * 255);
    }

    private void drawBackGround(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(BACKGROUND_LEFT_GAP_PIXELS, BACKGROUND_TOP_GAP_PIXELS, getWidth() - BACKGROUND_LEFT_GAP_PIXELS - BACKGROUND_RIGHT_GAP_PIXELS,
                getHeight() - BACKGROUND_BOTTOM_GAP_PIXELS - BACKGROUND_TOP_GAP_PIXELS);
        g.setColor(BORDER_COLOR);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    private static double max(double[] array) {
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < array.length; i++) {
            if (array[i] >= max) {
                max = array[i];
            }
        }
        return max;
    }

    private static double min(double[] array) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < array.length; i++) {
            if (array[i] <= min) {
                min = array[i];
            }
        }
        return min;
    }

    /**
     * Convert data y to screen y coordinate with linear interpolation.
     *
     * @param yData
     * @return
     */
    private int convertYDataToScreenCoord(final double yData) {
        return getHeight() - ENVELOPE_BOTTOM_GAP_PIXELS + (int) Math.round((yData - minYData) / (maxYData - minYData) * (ENVELOPE_TOP_GAP_PIXELS + ENVELOPE_BOTTOM_GAP_PIXELS - getHeight()));
    }

    /**
     * Convert data x to screen x coordinate with linear interpolation.
     *
     * @param xData
     * @return
     */
    private int convertXDataToScreenCoord(final double xData) {
        return ENVELOPE_LEFT_GAP_PIXELS + (int) Math.round((xData - minXData) / (maxXData - minXData) * (getWidth() - ENVELOPE_LEFT_GAP_PIXELS - ENVELOPE_RIGHT_GAP_PIXELS));
    }

    private void convertDataCoordsToScreen(int[] xArray, int[] yArray) {
        for (int i = 0; i < xArray.length; i++) {
            xArray[i] = convertXDataToScreenCoord(xDataArray[i]);
            yArray[i] = convertYDataToScreenCoord(yDataArray[i]);
        }
    }

    /**
     * Convert screen coordinates to data coordinates using linear interpolation.
     *
     * @param pScreen
     * @return
     */
    private Point2D convertScreenCoordsToData(Point pScreen) {
        double x = minXData + (double) (pScreen.x - ENVELOPE_LEFT_GAP_PIXELS) / (getWidth() - ENVELOPE_RIGHT_GAP_PIXELS - ENVELOPE_LEFT_GAP_PIXELS) * (maxXData - minXData);
        double y = minYData + (double) (pScreen.y - getHeight() + ENVELOPE_BOTTOM_GAP_PIXELS) / (ENVELOPE_TOP_GAP_PIXELS + ENVELOPE_BOTTOM_GAP_PIXELS - getHeight()) * (maxYData - minYData);
        return new Point2D.Double(x, y);
    }

    private void drawChart(Graphics g) {
        g.setColor(ENVELOPE_COLOR);
        int[] xArray = new int[xDataArray.length];
        int[] yArray = new int[xDataArray.length];
        convertDataCoordsToScreen(xArray, yArray);
        g.fillPolygon(xArray, yArray, xArray.length);
    }

    private class EnvelopeMouseListener implements MouseListener {
       
        public void mouseClicked(MouseEvent me) {
        }

        public void mousePressed(MouseEvent me) {
            //Note: mousePressed is more responsive than mouseClicked.
            valuesSentByMousePress = true;
            int tempXScreenAtMouseClick = me.getPoint().x;
            int tempYScreenAtMouseClick = me.getPoint().y;
            if (polygon.contains(tempXScreenAtMouseClick, tempYScreenAtMouseClick)) {
                Point2D wp = convertScreenCoordsToData(me.getPoint());
                xDataAtMouseClick = wp.getX();
                yDataAtMouseClick = wp.getY();

                xScreenAtMouseClick = tempXScreenAtMouseClick;
                yScreenAtMouseClick = tempYScreenAtMouseClick;

                repaint();
            } else {
                xDataAtMouseClick = Double.NaN;
                yDataAtMouseClick = Double.NaN;
            }
            mouseClickObservable.setChanged();
            mouseClickObservable.notifyObservers();
        }

        public void mouseReleased(MouseEvent me) {
        }

        public void mouseEntered(MouseEvent me) {
        }

        public void mouseExited(MouseEvent me) {
        }

    }

}
