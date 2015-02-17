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
        Logger.logOtherMessage("", "\n");

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
            System.out.println("Please enter the number of columns. 10-20");
            cols = scanner.nextByte();
            if (cols < 10) cols = 10;
            if (cols > 20) cols = 20;
            Logger.logUserMessage("Cols: " + cols);
            System.out.println("Please enter the number of rows. 10-20");
            rows = scanner.nextByte();
            if (rows < 10) rows = 10;
            if (rows > 20) rows = 20;
            Logger.logUserMessage("Rows: " + rows);
            System.out.println("Please enter the number of mines. ");
            mines = scanner.nextByte();
            Logger.logUserMessage("Mines: " + mines);
            if (cols != rows) {
                System.out.println("Shape of board is rectangular. Setting cols equal to rows.");
                Logger.logErrorMessage("Shape of board was rectangular, fixing...");
                if (cols < rows)
                    cols = rows;
                else
                    rows = cols;
                Logger.logCodeMessage("Shape of board fixed successfully.");
            }
        }


        new MS_Frame(rows, cols, mines);


    }


}
