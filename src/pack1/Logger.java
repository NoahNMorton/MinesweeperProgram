package pack1;

import java.io.File;
import java.io.FileWriter;

/**
 * This is a logging class written to log basic events within my code, for debug purposes. Everything
 * should theoretically work, with a few tweaks here and there.
 *
 * @author Noah Morton
 * @version 0.0.1
 */

public class Logger {
    private static File f;

    public Logger() {

        f = new File("programLog.log");

    }


    //logs a code message, such as a creation success.
    public static void logCodeMessage(String message) {

        try {
            FileWriter fw = new FileWriter(f, true); //the true will append the new data
            fw.write("\n[Code] " + message);
            fw.close();
        } catch (Exception e) {
            System.err.println("Error with writing logging file. " + e.getMessage());
        }
    }

    //logs input of the user in the console, for recreation of bugs.
    public static void logUserMessage(String message) {
        try {
            FileWriter fw = new FileWriter(f, true); //the true will append the new data
            fw.write("\n[User] " + message);
            fw.close();
        } catch (Exception e) {
            System.err.println("Error with writing logging file. " + e.getMessage());
        }

    }

    //logs error and failure messages.
    public static void logErrorMessage(String message) {
        try {
            FileWriter fw = new FileWriter(f, true); //the true will append the new data
            fw.write("\n[Error] " + message);
            fw.close();
        } catch (Exception e) {
            System.err.println("Error with writing logging file. " + e.getMessage());

        }

    }

    //logs other messages, that don't fit into a category.
    public static void logOtherMessage(String type, String message) {

        try {
            FileWriter fw = new FileWriter(f, true); //the true will append the new data
            fw.write("\n[" + type + "] " + message);
            fw.close();
        } catch (Exception e) {
            System.err.println("Error with writing logging file. " + e.getMessage());

        }

    }

}








