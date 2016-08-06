package exceptions;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author skorkmaz
 */
public class TryFinally {

    public static class MyE extends Exception {

        public MyE(String msg) {
            super(msg);
        }

    }

    private static final Logger LOGGER = Logger.getLogger(TryFinally.class.getName());

    private void test11() {
        LOGGER.log(Level.FINEST, "Inside test11");        
        throw new IllegalArgumentException();
    }

    public void test1() {
        LOGGER.log(Level.INFO, "entering");        
        try {
            test11();
        } finally {
            System.out.println("Inside test1.finally");
        }
    }
    
    private static void removeLoggerDefaultConsoleHandler() {
        LOGGER.setUseParentHandlers(false);
    }
    
    private static void initLogger(){
        removeLoggerDefaultConsoleHandler();
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        SimpleFormatter formatter = new SimpleFormatter();  
        consoleHandler.setFormatter(formatter);
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.ALL);
    }

    public static void main(String[] args) {
        System.out.println("Started");
        initLogger();
        TryFinally tf = new TryFinally();
        try {
            tf.test1();
            System.out.println("After test");
        } catch (Exception e) {
            System.out.println("Inside catch");
        }
        System.out.println("End of main");
    }

}
