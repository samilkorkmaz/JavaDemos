/**
 * Demo for filled slider.
 */
package filledslider;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UserInterface {

    private JLabel jlSliderValue = new JLabel();

    class SliderListener implements ChangeListener {

        /**
         * Update JLabel text when slider value changes.
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            jlSliderValue.setText("slider value = " 
                    + Integer.toString(source.getValue()));
        }
    }

    public UserInterface() {
        JFrame frame = new JFrame("UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setPreferredSize(new Dimension(300, 200));
        frame.setTitle("Filled slider demo");

        FilledSlider slider = new FilledSlider(new Color(34, 177, 76),
                Color.RED);
        slider.addChangeListener(new SliderListener());
        slider.setMinimum(0);
        slider.setMaximum(800);
        slider.setMajorTickSpacing(200);
        slider.setMinorTickSpacing(40);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setValue(75);
        int x = 20;
        int y = 50;
        int width = 200;
        int height = 60;
        slider.setBounds(x, y, width, height);

        JLabel jlSliderWidth = new JLabel();
        jlSliderValue.setBounds(x, y + 50, width, height);
        jlSliderWidth.setBounds(x, y + 75, width, height);

        jlSliderWidth.setText("slider width = " + Integer.toString(slider.getWidth()));


        frame.add(slider);
        frame.add(jlSliderValue);
        frame.add(jlSliderWidth);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        UserInterface s = new UserInterface();
    }
}
