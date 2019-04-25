package cache.base.jedis;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;

public class MyLogger {
    /**
     * @param stdout Hold default out
     */
    private static final PrintStream stdout = System.out;

    /**
     * This is printing to both console and console.log
     * Note that when print to file this can't handle newline
     */
    public static synchronized void ptln(Object str) {
        try {
            System.setOut(stdout);
            System.out.println(str + "");    //Error occurs

            System.setOut(new PrintStream(new FileOutputStream(
                    "C:\\Users\\Jingxuan\\Desktop\\console.log", true), true));
            System.out.println((new Timestamp(System.currentTimeMillis())
                    + "\t >>> " + str));

        } catch (Exception e) {
            System.out.println("Error. Wrong Print Type.");
            System.out.println(e.getMessage());
            return;
        }
    }
}