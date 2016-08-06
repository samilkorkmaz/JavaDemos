package formatting;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * NumberFormat demo.</br>
 * Reference: <The Java Tutorials>\i18n\format\numberFormat.html
 *
 * @author skorkmaz
 */
public class NumberFormatDemo {

    private static void displayNumber(Locale currentLocale) {
        Integer quantity = 123456;
        Double amount = 345987.246;
        NumberFormat numberFormatter;

        numberFormatter = NumberFormat.getNumberInstance(currentLocale);
        String quantityStr = numberFormatter.format(quantity);
        String amountStr = numberFormatter.format(amount);
        System.out.println(quantityStr + "   " + currentLocale.toString());
        System.out.println(amountStr + "   " + currentLocale.toString());
    }

    public static void main(String[] args) {
        displayNumber(Locale.FRENCH);
        displayNumber(Locale.GERMANY);
        displayNumber(Locale.ENGLISH);
        displayNumber(new Locale("tr", "TR"));
    }
    
}
