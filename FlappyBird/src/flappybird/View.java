package flappybird;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Game screen.
 *
 * @author Samil Korkmaz
 * @date October 2015
 * @copyright Public Domain
 */
public class View extends JPanel {

    private static final int PREF_WIDTH = 800;
    private static final int PREF_HEIGHT = 500;
    private static View instance;
    private static final JFrame frame = new JFrame("Flappy");
    private static final Font LEVEL_SUCCESS_FONT = new Font("Tahoma", 1, 24);
    private static final Font LEVEL_FAIL_FONT = new Font("Tahoma", 1, 24);
    private static final int SUCCESS_FAIL_X = 10;
    private static final int SUCCESS_FAIL_Y = 70;
    private static final int SUCCESS_FAIL_WIDTH = 250;
    private static final int SUCCESS_FAIL_HEIGHT = 50;
    private static final Color SUCCESS_FG_COLOR = Color.BLUE;
    private static final Color SUCCESS_BG_COLOR = Color.LIGHT_GRAY;
    private static final Color FAIL_FG_COLOR = Color.RED;
    private static final Color FAIL_BG_COLOR = Color.YELLOW;
    private static final String FAIL_TEXT = "GAME OVER!";
    private static final String SUCCESS_TEXT = "WIN!";
    private static final String SCORE_TEXT = "Score: ";
    private static final JLabel jlSuccessFail = new JLabel();
    private static final JLabel jlScore = new JLabel(SCORE_TEXT + "0");
    private static final int RECT_WIDTH = 60;
    private static final List<Rectangle> rectList = new ArrayList<>();
    private static final Random random = new Random();
    private static final Ellipse2D.Double bird = new Ellipse2D.Double(PREF_WIDTH / 2, 0, 50, 30);
    private static double birdSpeed = 0;
    private static final double GRAVITY = 0.05;
    private static boolean isCollided = false;
    private static long maxScore = 0;
    private static final String MAX_SCORE_FILE_NAME = "maxScore.txt";

    public static boolean isIsCollided() {
        return isCollided;
    }

    @Override
    public final Dimension getPreferredSize() {
        return new Dimension(PREF_WIDTH, PREF_HEIGHT);
    }

    public static void createAndShowGUI() {
        if (instance == null) {
            instance = new View();
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(instance);
            frame.pack();
            frame.setLocationRelativeTo(null);
        }
        frame.setVisible(true);
    }

    private View() {
        setLayout(null);
        add(jlSuccessFail);
        add(jlScore);
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                birdSpeed = -2;
            }
        });
        List<String> fileContents;
        try {
            fileContents = readTextFile(MAX_SCORE_FILE_NAME);
            if (!fileContents.isEmpty()) {
                maxScore = Long.parseLong(fileContents.get(0));
            } else {
                System.out.println("No " + MAX_SCORE_FILE_NAME);
            }
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static List<String> readTextFile(String fileName) throws IOException {
        List<String> fileContents = new ArrayList<>();
        // FileReader reads text files in the default encoding.
        FileReader fileReader = new FileReader(fileName);
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContents.add(line);
            }
        }
        return fileContents;
    }

    static void writeToTextFile(String fileName, List<String> fileContents) throws IOException {
        // Assume default encoding.
        FileWriter fileWriter = new FileWriter(fileName);
        try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (String string : fileContents) {
                bufferedWriter.write(string);
                // Note that write() does not automatically append a newline character.
                bufferedWriter.newLine();
            }
        }
    }

    /**
     * Scroll columns from right to left.
     */
    public static void scrollColumnsFromRightToLeft() {
        for (Rectangle rect : rectList) {
            rect.x--; //scroll from right to left            
        }
        removeNegativesFromList();
        birdSpeed = birdSpeed + GRAVITY;
        bird.y = bird.y + birdSpeed;
        instance.repaint();
        detectCollision();
    }

    static void removeNegativesFromList() {
        if (rectList.get(0).x < 0) {
            rectList.remove(0);
        }
    }

    private static void detectCollision() {
        for (int i = 0; i < rectList.size(); i++) {
            Rectangle rect = rectList.get(i);
            //check upper right corner of bird
            if (rect.x <= bird.x + bird.width && rect.x + rect.width >= bird.x + bird.width) {
                if (i % 2 == 0) { //upper rect
                    if (rect.y + rect.height >= bird.y) {
                        isCollided = true;
                    }
                } else {//lower rect
                    if (rect.y <= bird.y + bird.height) {
                        isCollided = true;
                    }
                }
            }
            //check lower left corner of bird
            if (rect.x <= bird.x && rect.x + rect.width >= bird.x) {
                if (i % 2 == 0) { //upper rect
                    if (rect.y + rect.height >= bird.y) {
                        isCollided = true;
                    }
                } else {//lower rect
                    if (rect.y <= bird.y + bird.height) {
                        isCollided = true;
                    }
                }
            }
        }
        if (isCollided) {
            frame.setTitle(FAIL_TEXT);
            setLevelFail();
            try {
                writeToTextFile(MAX_SCORE_FILE_NAME, new ArrayList<>(Arrays.asList(String.valueOf(maxScore))));
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            frame.setTitle("PASS");
            printScore();
        }
    }

    private static void printScore() {
        double score = 0;
        for (Rectangle rect : rectList) {
            if (rect.x < bird.x) {
                score = score + 0.5; //there are two rects per colums, each one adds 0.5;
            }
        }
        maxScore = Math.round(Math.max(score, maxScore));
        jlScore.setBounds(SUCCESS_FAIL_X, SUCCESS_FAIL_Y - 50, SUCCESS_FAIL_WIDTH, SUCCESS_FAIL_HEIGHT);
        jlScore.setFont(LEVEL_SUCCESS_FONT);
        jlScore.setForeground(SUCCESS_FG_COLOR);
        //jlScore.setBackground(SUCCESS_BG_COLOR);
        jlScore.setOpaque(true);
        jlScore.setText(SCORE_TEXT + Math.round(score) + ", max: " + maxScore);
    }

    /**
     * Create a new column which consists of two rectangles with a gap in between.
     */
    public static void createNewColumn() {
        int gap = 150;
        int minH = 10;
        int h1 = minH + random.nextInt(PREF_HEIGHT - gap - minH);
        Rectangle rect1 = new Rectangle(PREF_WIDTH, 0, RECT_WIDTH, h1);
        rectList.add(rect1);
        int h2 = PREF_HEIGHT - h1 - gap;
        Rectangle rect2 = new Rectangle(PREF_WIDTH, PREF_HEIGHT - h2, RECT_WIDTH, h2);
        rectList.add(rect2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, PREF_WIDTH, PREF_HEIGHT);
        g2.setColor(Color.RED);
        for (Rectangle rect : rectList) {
            g2.fill(rect);
        }
        g2.setColor(Color.BLUE);
        g2.fill(bird);
    }

    public static void makeVisible(boolean isVisible) {
        frame.setVisible(isVisible);
    }

    public static void initLevel() {
        jlSuccessFail.setOpaque(false);
        jlSuccessFail.setText("");
    }

    public static void setLevelSuccess() {
        jlSuccessFail.setBounds(SUCCESS_FAIL_X, SUCCESS_FAIL_Y, SUCCESS_FAIL_WIDTH, SUCCESS_FAIL_HEIGHT);
        jlSuccessFail.setFont(LEVEL_SUCCESS_FONT);
        jlSuccessFail.setForeground(SUCCESS_FG_COLOR);
        jlSuccessFail.setBackground(SUCCESS_BG_COLOR);
        jlSuccessFail.setOpaque(true);
        jlSuccessFail.setText(SUCCESS_TEXT);
    }

    public static void setLevelFail() {
        jlSuccessFail.setBounds(SUCCESS_FAIL_X, SUCCESS_FAIL_Y, SUCCESS_FAIL_WIDTH, SUCCESS_FAIL_HEIGHT);
        jlSuccessFail.setFont(LEVEL_FAIL_FONT);
        jlSuccessFail.setForeground(FAIL_FG_COLOR);
        jlSuccessFail.setBackground(FAIL_BG_COLOR);
        jlSuccessFail.setOpaque(true);
        jlSuccessFail.setText(FAIL_TEXT);
    }
}
