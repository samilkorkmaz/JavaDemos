package polygonchart;

import java.awt.Font;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * PolygonChart demo.
 *
 * @author skorkmaz
 */
public class Chart {

    private static JFrame frame;
    private static ObservingTextField jtfY;
    private static ObservingTextField jtfX;
    private static PolygonChart polygonChart;
    private static final DecimalFormat formatX = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));
    private static final DecimalFormat formatY = new DecimalFormat("0", new DecimalFormatSymbols(Locale.ENGLISH));

    /**
     * Text field whose values will be used to plot point on chart. Point clicked on chart will be displayed on text field.
     */
    private static class ObservingTextField extends JTextField implements Observer {

        private boolean valueEdited;
        private final boolean isY;
        private double value; //to prevent significant digit loss when converting to fomatted text

        private static final int ZERO_ON_KEYBOARD_KEYCODE = 48;
        private static final int NINE_ON_KEYBOARD_KEYCODE = 57;
        private static final int ZERO_ON_NUMPAD_KEYCODE = 96;
        private static final int NINE_ON_NUMPAD_KEYCODE = 105;
        private static final int DOT_KEYCODE = 46;
        private static final int MINUS_KEYCODE = 109;
        private static final int BACKSPACE_KEYCODE = 8;
        private static final int DELETE_KEYCODE = 127;

        private final PolygonChart polygonChart;

        public ObservingTextField(PolygonChart polygonChart, final boolean isY) {
            super();
            this.polygonChart = polygonChart;
            this.isY = isY;
            addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    //Note that text is not updated in keyPressed. Therefore we have to use keyReleased.
                    //System.out.println("key code = " + evt.getKeyCode());
                    if (isValidKey(evt.getKeyCode())) {
                        valueEdited = true;
                        updateValue();
                        setXYData();
                    }
                }
            });
        }

        private boolean isValidKey(final int keyCode) {
            return (keyCode >= ZERO_ON_KEYBOARD_KEYCODE && keyCode <= NINE_ON_KEYBOARD_KEYCODE)
                    || (keyCode >= ZERO_ON_NUMPAD_KEYCODE && keyCode <= NINE_ON_NUMPAD_KEYCODE)
                    || keyCode == DOT_KEYCODE || keyCode == MINUS_KEYCODE || keyCode == BACKSPACE_KEYCODE
                    || keyCode == DELETE_KEYCODE;
        }

        public double getValue() {
            return value;
        }

        public void setValue(final double value) {
            this.value = value;
            valueEdited = false;
        }

        public void updateValue() {
            try {
                //Update value only if user has edited it by pressing keys (to prevent precision loss due to formatting when text was updated by chart mouseClick):
                if (valueEdited) {
                    setValue(Double.parseDouble(getText()));
                }
            } catch (NumberFormatException ex) {
                showDialog(ex.toString());
            }
        }
       
        @Override
        public void update(Observable o, Object o1) {
            final double speed = polygonChart.getXDataAtMouseClick();
            final double altitude = polygonChart.getYDataAtMouseClick();
            if (!Double.isNaN(speed) && !Double.isNaN(altitude)) {
                if (isY) {
                    value = altitude;
                    setText(formatY.format(altitude));
                } else {
                    value = speed;
                    setText(formatX.format(speed));
                }
            }
        }

    }

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        double[] xArray = {5, 5.5, 8, 9, 10, 10, 9, 7.5, 6};
        double[] yArray = {150, 200, 170, 170, 200, 120, 100, 110, 100};
        double x = 7.5;
        double y = 150;

        polygonChart = new PolygonChart(30, 50, 500, 300, xArray, yArray, "Polygon Chart", "X", 
                "Y", formatX, formatY, new Font("Tahoma", 0, 11), "Input values are out of chart!");
        frame.add(polygonChart);

        jtfY = new ObservingTextField(polygonChart, true);
        jtfY.setValue(y);
        jtfY.setBounds(30, 5, 150, 20);
        jtfY.setText(formatY.format(y));
        jtfY.addFocusListener(null);
        polygonChart.addObserver(jtfY);
        frame.add(jtfY);

        jtfX = new ObservingTextField(polygonChart, false);
        jtfX.setValue(x);
        jtfX.setBounds(jtfY.getX() + jtfY.getWidth() + 5, jtfY.getY(), 150, jtfY.getHeight());
        jtfX.setText(formatX.format(x));
        jtfX.addFocusListener(null);
        polygonChart.addObserver(jtfX);
        frame.add(jtfX);
        
        frame.setVisible(true);
        setXYData();
    }

    public static void setXYData() {
        try {
            polygonChart.setXYData(jtfX.getValue(), jtfY.getValue());
        } catch (IllegalArgumentException ex) {
            showDialog(ex.getMessage());
        }
    }

    private static void showDialog(final String message) {
        JDialog jDialog = new JDialog(frame, message);
        jDialog.setBounds(frame.getX(), frame.getY(), 500, 100);
        jDialog.setVisible(true);
    }
}
