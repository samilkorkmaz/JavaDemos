package pathmapupdate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author sam
 */
public class View {

    private static final MyPanel MY_PANEL = new MyPanel();

    private static class MyPanel extends JPanel {

        private static final int PREF_WIDTH = 400;
        private static final int PREF_HEIGHT = 300;
        private static final int CELL_LENGTH = 20;
        private static final int NCOL = PREF_WIDTH / CELL_LENGTH;
        private static final int NROW = PREF_HEIGHT / CELL_LENGTH;
        private static final int X0 = 0;
        private static final int Y0 = 20;
        private static final Color GRID_BG_COLOR = new Color(51, 204, 0);
        private static final Color GRID_LINE_COLOR = new Color(0.6f, 0.6f, 0.6f);
        private static final Color GRID_CELL_FILL_COLOR = new Color(0f, 0f, 1f);
        private static final Color POLYGON_FILL_COLOR = new Color(0, 1, 0, 0.7f);
        private static final Color POLYGON_LINE_COLOR = Color.BLACK;
        private static final int[] XPOINTS = {10, 110, 110};
        private static final int[] YPOINTS = {30, 30, 130};
        private static final Polygon MY_POLYGON = new Polygon(XPOINTS, YPOINTS, XPOINTS.length);

        public MyPanel() {
            setBackground(GRID_BG_COLOR);
            JLabel jl = new JLabel("Drag shape to see change of grid cells");
            add(jl);
            MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
            addMouseListener(myMouseAdapter);
            addMouseMotionListener(myMouseAdapter);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            drawGridLines(g2);
            fillCellsUnderPolygon(g2);
            drawPolygon(g2);
        }

        private void fillCellsUnderPolygon(Graphics2D g2) {
            g2.setColor(GRID_CELL_FILL_COLOR);
            for (int iCol = 0; iCol < NCOL; iCol++) {
                for (int iRow = 0; iRow < NROW; iRow++) {
                    int xCenter = iCol * CELL_LENGTH + CELL_LENGTH / 2;
                    int yCenter = iRow * CELL_LENGTH + CELL_LENGTH / 2;
                    if (MY_POLYGON.contains(xCenter, yCenter)) {
                        g2.fillRect(xCenter - CELL_LENGTH / 2, yCenter - CELL_LENGTH / 2, CELL_LENGTH, CELL_LENGTH);
                    }
                }
            }
        }

        private void drawGridLines(Graphics2D g2) {
            g2.setColor(GRID_LINE_COLOR);
            for (int iCol = 0; iCol < NCOL; iCol++) {
                int x = X0 + iCol * CELL_LENGTH;
                g2.drawLine(x, Y0, x, PREF_HEIGHT);
            }
            for (int iRow = 0; iRow < NROW; iRow++) {
                int y = Y0 + iRow * CELL_LENGTH;
                g2.drawLine(X0, y, PREF_WIDTH, y);
            }
        }

        private void drawPolygon(Graphics2D g2) {
            g2.setColor(POLYGON_FILL_COLOR);
            g2.fillPolygon(MY_POLYGON);
            g2.setColor(POLYGON_LINE_COLOR);
            g2.draw(MY_POLYGON);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(PREF_WIDTH, PREF_HEIGHT);
        }
    }

    private static class MyMouseAdapter extends MouseAdapter {

        private Polygon selectedPolygon = null;
        private int prevMouseX;
        private int prevMouseY;

        @Override
        public void mousePressed(MouseEvent evt) {
            if (evt.getButton() == MouseEvent.BUTTON1) {
                if (MyPanel.MY_POLYGON.contains(evt.getPoint())) { //if there is a polygon at clicked point
                    selectedPolygon = MyPanel.MY_POLYGON;
                    prevMouseX = evt.getX();
                    prevMouseY = evt.getY();
                    MY_PANEL.repaint();
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent evt) {
            if (selectedPolygon != null) {
                selectedPolygon.translate(evt.getX() - prevMouseX, evt.getY() - prevMouseY);
                prevMouseX = evt.getX();
                prevMouseY = evt.getY();
                MY_PANEL.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (selectedPolygon != null) {
                MY_PANEL.repaint();
                selectedPolygon = null;
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            JFrame view = new JFrame("Path Map Update Demo");
            view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            view.setResizable(false);
            view.getContentPane().add(MY_PANEL);
            view.pack();
            view.setLocationRelativeTo(null);
            view.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
