
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

public class GlassPaneDemo {

    static private MyGlassPane myGlassPane;

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("GlassPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Start creating and adding components.
        JCheckBox changeButton = new JCheckBox("Glass pane \"visible\"");
        changeButton.setSelected(false);

        //Set up the content pane, where the "main GUI" lives.
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(changeButton);
        contentPane.add(new JButton("Button 1"));
        contentPane.add(new JButton("Button 2"));

        //Set up the menu bar, which appears above the content pane.
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.add(new JMenuItem("Do nothing"));
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        //Set up the glass pane, which appears over both menu bar and content pane and is an item listener on the change button.
        myGlassPane = new MyGlassPane(changeButton, menuBar, frame.getContentPane());
        changeButton.addItemListener(myGlassPane);
        frame.setGlassPane(myGlassPane);

        //Show the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }
}

/**
 * We have to provide our own glass pane so that it can paint.
 */
class MyGlassPane extends JComponent implements ItemListener {

    Point pointMouseClick;
    JLabel jl;

    //React to change button clicks.
    @Override
    public void itemStateChanged(ItemEvent e) {
        setVisible(e.getStateChange() == ItemEvent.SELECTED);
    }

    /**
     * Paint red filled oval at mouse click location.
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (pointMouseClick != null) {
            g.setColor(Color.red);
            g.fillOval(pointMouseClick.x - 10, pointMouseClick.y - 10, 20, 20);
            g.drawString("Samil", pointMouseClick.x - 10, pointMouseClick.y - 10);
            jl.setVisible(true);
        }
    }

    public void setPoint(Point p) {
        pointMouseClick = p;
    }

    public MyGlassPane(AbstractButton aButton, JMenuBar menuBar, Container contentPane) {
        jl = new JLabel("SNAP ON");        
        this.add(jl);
        jl.setBackground(Color.YELLOW);
        jl.setOpaque(true);
        jl.setBounds(new Rectangle(100, 5, 54, 12));
        CBListener listener = new CBListener(aButton, menuBar, this, contentPane);
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }
}

/**
 * Listen for all events that our check box is likely to be interested in. Re-dispatch them to the check box.
 */
class CBListener extends MouseInputAdapter {

    Toolkit toolkit;
    Component liveButton;
    JMenuBar menuBar;
    MyGlassPane glassPane;
    Container contentPane;

    public CBListener(Component liveButton, JMenuBar menuBar, MyGlassPane glassPane, Container contentPane) {
        toolkit = Toolkit.getDefaultToolkit();
        this.liveButton = liveButton;
        this.menuBar = menuBar;
        this.glassPane = glassPane;
        this.contentPane = contentPane;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        redispatchMouseEvent(e, true);
    }

    //A basic implementation of redispatching events.
    private void redispatchMouseEvent(MouseEvent e, boolean repaint) {
        Point glassPanePoint = e.getPoint();
        Container container = contentPane;
        Point containerPoint = SwingUtilities.convertPoint(glassPane, glassPanePoint, contentPane);
        if (containerPoint.y < 0) { //we're not in the content pane
            if (containerPoint.y + menuBar.getHeight() >= 0) {
                //The mouse event is over the menu bar. Could handle specially.
            } else {
                //The mouse event is over non-system window decorations, such as the ones provided by the Java look and feel. Could handle specially.
            }
        } else {
            //The mouse event is probably over the content pane. Find out exactly which component it's over.  
            Component component = SwingUtilities.getDeepestComponentAt(container, containerPoint.x, containerPoint.y);

            if ((component != null) && (component.equals(liveButton))) {
                //Forward events over the check box.
                Point componentPoint = SwingUtilities.convertPoint(glassPane, glassPanePoint, component);
                component.dispatchEvent(new MouseEvent(component, e.getID(), e.getWhen(), e.getModifiers(), componentPoint.x, componentPoint.y,
                        e.getClickCount(), e.isPopupTrigger()));
            }
        }

        //Update the glass pane if requested.
        if (repaint) {
            glassPane.setPoint(glassPanePoint);
            glassPane.repaint();
        }
    }
}
