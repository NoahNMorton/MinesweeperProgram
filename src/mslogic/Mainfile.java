package mslogic;

import mswindow.MS_Frame;

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

        new MS_Frame(15, 15, 30);


    }


}
