package updateguifromanotherthread;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Demonstrates how to update GUI from another thread.
 * 
 * @author Samil Korkmaz
 * @date January 2015
 * @copyright Public Domain
 */
public class MyPanel extends JPanel {
    
    private static final int PREF_WIDTH = 420;
    private static final int PREF_HEIGHT = 75;
    private static int counter;
    private static JLabel jlCounter;
    private static MyPanel instance;
    
    public void setCounter(int inCounter) {
        counter = inCounter;
        jlCounter.setText("counter = " + counter);        
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(10, 10, 400, 20);
        g2.setColor(Color.YELLOW);
        g2.fillRect(10, 10, counter*8, 20);        
    }
    
    private MyPanel() {
        setLayout(null);
        jlCounter = new JLabel("counter = " + counter);
        jlCounter.setBounds(160, 9, 100, 20);
        this.add(jlCounter);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_WIDTH, PREF_HEIGHT);
    }
    
    public static MyPanel getInstance() {
        return instance;
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Update GUI from another thread demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instance = new MyPanel();
        frame.getContentPane().add(instance);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        MyPanel.createAndShowGUI();
        Thread myThread = new Thread(() -> {
            //This thread updates the counter variable in GUI (MyPanel) which in turn updates its label and progress bar.
            for (int i = 0; i < 51; i++) {
                MyPanel.getInstance().setCounter(i);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        myThread.start();
    }

}
