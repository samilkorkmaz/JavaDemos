package flappybird;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 * Simple Flappy Bird clone.
 *
 * @author skorkmaz
 */
public class FlappyBird {

    private static int counter = 0;
    private static Timer timer;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            View.createAndShowGUI();
            int delay = 10; //milliseconds
            ActionListener taskPerformer = (ActionEvent ae) -> {
                if (counter % 200 == 0) {
                    View.createNewColumn();
                }
                counter++;
                View.scrollColumnsFromRightToLeft();
                if (View.isIsCollided()) {
                    timer.stop();
                }
            };
            timer = new Timer(delay, taskPerformer);
            timer.start();
        });
    }

}
