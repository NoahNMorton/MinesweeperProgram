package mslogic;

import mswindow.MS_Frame;

/**
 * @author othscs120
 *         Created on: 11/4/2014 , Time is :  1:25 PM
 *         Part of Project: MineSweeper
 */

public class Mainfile {

    public static void main(String[] args) {

        new Logger();
        Logger.logOtherMessage("", "\n");

        new MS_Frame(15, 15, 30);
    }
}
