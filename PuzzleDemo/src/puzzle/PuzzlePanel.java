package puzzle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Puzzle demo. Drag pieces into their positions.
 *
 * NOTES:<br>
 * - You cannot change polygon location with polygon.getBounds().x = 10. The only way is to use translate and for
 * that, you have to calculate dx, dy.
 *
 * @author Samil Korkmaz
 * @licence Public Domain
 */
public class PuzzlePanel extends JPanel {

    private static final int SNAP_DISTANCE_PIXELS = 10;
    private static final Color POLYGON_FILL_COLOR = new Color(0, 1, 0, 0.6f);
    private static final Color POLYGON_LINE_COLOR = Color.BLACK;
    private static final Color SNAP_POLYGON_COLOR = Color.LIGHT_GRAY;
    private static final Color STRING_COLOR = Color.BLUE;
    private static final int PREF_WIDTH = 350;
    private static final int PREF_HEIGHT = 170;
    private static final Stroke POLYGON_STROKE = new BasicStroke(1f);
    private int prevMouseX;
    private int prevMouseY;
    private final List<MyPolygon> polygonList;
    private final List<Polygon> snapPolygonList = new ArrayList<>();

    private void createRectPolygon(int width, int height, int xSnap, int ySnap) {
        MyPolygon polygon = new MyPolygon(width, height);
        polygonList.add(polygon);
        MyPolygon snapPolygon = new MyPolygon(width, height);
        snapPolygon.translate(xSnap, ySnap);
        snapPolygonList.add(snapPolygon);
    }

    public PuzzlePanel() {
        add(new JLabel("Drag green rectangles with mouse"));
        this.polygonList = new ArrayList<>();
        MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
        addMouseListener(myMouseAdapter);
        addMouseMotionListener(myMouseAdapter);
        createRectPolygon(50, 70, 100, 40);
        createRectPolygon(60, 30, 160, 40);
        createRectPolygon(20, 10, 160, 90);
    }

    private int calcAvg(final int[] valueArray) {
        int sum = 0;
        for (int i = 0; i < valueArray.length; i++) {
            sum = sum + valueArray[i];
        }
        int avg = sum / valueArray.length;
        return avg;
    }

    private Point calcCenterOfPolygon(Polygon polygon) {
        int xAvg = calcAvg(polygon.xpoints);
        int yAvg = calcAvg(polygon.ypoints);
        return new Point(xAvg, yAvg);
    }

    private void drawSnapPolygon(Graphics2D g2, final int iPolygon) {
        Polygon sp = snapPolygonList.get(iPolygon);
        g2.setColor(SNAP_POLYGON_COLOR);
        g2.draw(sp);
    }

    private int calcDistToSnapPolygon(final Polygon polygon, final int iPolygon) {
        Point centerPolygon = calcCenterOfPolygon(polygon);
        Point centerStationaryPolygon = calcCenterOfPolygon(snapPolygonList.get(iPolygon));
        return (int) Math.round(Math.hypot(centerStationaryPolygon.x - centerPolygon.x, centerStationaryPolygon.y - centerPolygon.y));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //draw smooth (antialiased) edges:
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(POLYGON_STROKE);
        for (int iPolygon = 0; iPolygon < polygonList.size(); iPolygon++) {
            MyPolygon myPolygon = polygonList.get(iPolygon);
            int distToStationaryPolygon = calcDistToSnapPolygon(myPolygon, iPolygon);
            if (myPolygon.isSnapped() || distToStationaryPolygon <= SNAP_DISTANCE_PIXELS) {
                int deltaX = snapPolygonList.get(iPolygon).getBounds().x - myPolygon.getBounds().x;
                int deltaY = snapPolygonList.get(iPolygon).getBounds().y - myPolygon.getBounds().y;
                myPolygon.translate(deltaX, deltaY);
                myPolygon.setIsSnapped(true);
            }
            drawSnapPolygon(g2, iPolygon);
            g2.setColor(POLYGON_FILL_COLOR);
            g2.fillPolygon(myPolygon);
            g2.setColor(POLYGON_LINE_COLOR);
            g2.draw(myPolygon);
        }
        boolean allPolygonsSnapped = true;
        for (MyPolygon myPolygon : polygonList) {
            allPolygonsSnapped = allPolygonsSnapped && myPolygon.isSnapped();
        }
        if (allPolygonsSnapped) {
            g2.setColor(STRING_COLOR);
            g2.setFont(new Font("Tahoma", Font.BOLD, 24));
            g2.drawString("Complete!", 100, 50);
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_WIDTH, PREF_HEIGHT);
    }

    private class MyMouseAdapter extends MouseAdapter {

        private MyPolygon selectedPolygon = null;

        @Override
        public void mousePressed(MouseEvent evt) {
            if (evt.getButton() == MouseEvent.BUTTON1) {
                if (polygonList.size() > 0) {
                    for (int i = polygonList.size() - 1; i >= 0; i--) {
                        if (polygonList.get(i).contains(evt.getPoint())) { //if there is a polygon at clicked point
                            selectedPolygon = polygonList.get(i);

                            //move the selected polygon to the end of list so that it will be drawn last (i.e. on top) in paintComponent and checked first for mouse click:
                            polygonList.remove(selectedPolygon);
                            polygonList.add(polygonList.size(), selectedPolygon);

                            Polygon snapPolygon = snapPolygonList.get(i);
                            snapPolygonList.remove(snapPolygon);
                            snapPolygonList.add(snapPolygonList.size(), snapPolygon);

                            prevMouseX = evt.getX();
                            prevMouseY = evt.getY();
                            repaint();
                            break;
                        }
                    }
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent evt) {
            if (selectedPolygon != null) {
                if (!selectedPolygon.isSnapped()) {
                    selectedPolygon.translate(evt.getX() - prevMouseX, evt.getY() - prevMouseY);
                    prevMouseX = evt.getX();
                    prevMouseY = evt.getY();
                }
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (selectedPolygon != null) {
                repaint();
                selectedPolygon = null;
            }
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Puzzle Demo - Şamil Korkmaz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(new PuzzlePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

}
