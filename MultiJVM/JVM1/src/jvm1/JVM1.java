package jvm1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import jvm2.JVM2;

/**
 * Running a second Java Virtual Machine from the first one.<br>
 * You must first build JVM2.jar and add it as library to this project.<br>
 * After you run this, open Windows Task Manager. You will see that there are two java.exe while jvm2 is running and one java.exe while only jvm1 is running.<br>
 * TODO: Pass input to and get output from jvm2.
 *
 * @author skorkmaz
 */
public class JVM1 {

    public static Process startSecondJVM() throws Exception {
        String classpath = System.getProperty("java.class.path");
        String separator = System.getProperty("file.separator");
        String path = System.getProperty("java.home") + separator + "bin" + separator + "java";
        //String path = "c:\\Program Files\\Java\\jdk1.8.0_45\\bin\\java";

        String secondJVMClassName = JVM2.class.getName();
        ProcessBuilder processBuilder = new ProcessBuilder(path, "-cp", classpath, secondJVMClassName);
        processBuilder.redirectErrorStream(true);
        return processBuilder.start();
    }

    public static void main(String[] args) throws InterruptedException, Exception {
        System.out.println("This is jvm1:");
        System.out.println("bitSize : " + System.getProperty("sun.arch.data.model"));
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Java home: " + System.getProperty("java.home"));

        System.out.println("Calling jvm2, please wait...");
        try {
            //Run jvm2:
            Process p = startSecondJVM();
            InputStream istr = p.getInputStream();
            final BufferedReader br = new BufferedReader(new InputStreamReader(istr));
            //Display output from jvm2:
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String s;
                    try {
                        while ((s = br.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(JVM1.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }).start();
            p.waitFor();
            br.close();
        } catch (IOException | InterruptedException e1) {
            System.out.println("An exception occured: " + e1.getMessage());
        }

        System.out.println("This is jvm1:");
        for (int i = 0; i < 10; i++) {
            System.out.println("i = " + i + " / 9");
            Thread.sleep(1000);
        }
    }

}
