package formatting;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author skorkmaz
 */
public class DecimalFormatDemo {
    
    private static final String DEG_CHAR = Character.toString((char) 176);    
    private static String formatLon(double lon) {
        String pattern = "000.##";
        DecimalFormat df = new DecimalFormat(pattern, new DecimalFormatSymbols(Locale.ENGLISH));
        return df.format(lon) + DEG_CHAR + "D";
    }

    public static void main(String[] args) {
        System.out.println("formatted lon = " + formatLon(33.126));                
    }

}
