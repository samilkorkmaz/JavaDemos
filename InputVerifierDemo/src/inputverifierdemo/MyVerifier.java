package inputverifierdemo;

import java.awt.Color;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author skorkmaz
 */
public class MyVerifier extends InputVerifier {

    private final String stringToCheck;
    private final boolean alwaysYieldFocus;

    public MyVerifier(String stringToCheck, boolean alwaysYieldFocus) {
        this.stringToCheck = stringToCheck;
        this.alwaysYieldFocus = alwaysYieldFocus;
    }

    @Override
    public boolean verify(JComponent input) {
        return ((JTextField) input).getText().equalsIgnoreCase(stringToCheck);
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {
        boolean isValid = verify(input);
        JTextField jtf = (JTextField) input;
        if (!isValid) {
            jtf.setBackground(Color.YELLOW);
            jtf.setForeground(Color.RED);
            jtf.selectAll();
        } else {
            jtf.setBackground(Color.WHITE);
            jtf.setForeground(Color.BLACK);
        }
        return isValid || alwaysYieldFocus;
    }

}
