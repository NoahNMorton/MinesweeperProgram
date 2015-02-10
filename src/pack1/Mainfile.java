package pack1;

import java.util.Scanner;

/**
 * @author othscs120
 *         Created on: 11/4/2014 , Time is :  1:25 PM
 *         Part of Project: MineSweeper
 */

public class Mainfile {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new Logger();
<<<<<<< HEAD
        byte rows = 0, cols = 0, mines = 0;
        boolean argValid = false;

        try {
            if (args[0] != null && args[1] != null && args[2] != null) { //tries to receive cols, rows, and mines from args
                Logger.logCodeMessage("Initialising, using args for data...");
                rows = Byte.parseByte(args[0]);
                cols = Byte.parseByte(args[1]);
                mines = Byte.parseByte(args[2]);
                argValid = true;
            }
        } catch (Exception ignored) { //if args are not valid, simply skip them
        }

        if (!argValid) { //if args are not valid, will get names from user
            Logger.logCodeMessage("Initialising, Asking user for data...");
            System.out.println("Please enter the number of columns.");
            cols = scanner.nextByte();
            Logger.logUserMessage("Cols: " + cols);
            System.out.println("Please enter the number of rows.");
            rows = scanner.nextByte();
            Logger.logUserMessage("Rows: " + rows);
            System.out.println("Please enter the number of mines.");
            mines = scanner.nextByte();
            Logger.logUserMessage("Mines: " + mines);
        }

=======
        Logger.logCodeMessage("Initialising, Asking user for data...");

        System.out.println("Please enter the number of columns.");
        byte cols = scanner.nextByte();
        Logger.logUserMessage("Cols: " + cols);

        System.out.println("Please enter the number of rows.");
        byte rows = scanner.nextByte();
        Logger.logUserMessage("Rows: " + rows);

        System.out.println("Please enter the number of mines.");
        byte mines = scanner.nextByte();
        Logger.logUserMessage("Mines: " + mines);
>>>>>>> b2d33d5fc9757c4cf108a8d01fdf70114f58d8de

        new MS_Frame(rows, cols, mines);


    }


}
