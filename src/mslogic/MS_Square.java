package mslogic;

/**
 * @author othscs120
 *         Created on: 11/5/2014 , Time is :  1:06 PM
 *         Part of Project: MineSweeper
 */

public class MS_Square {
    public static final int SHOWN = 0, UP = 1, FLAG = 2, QUESTION = 3;

    private boolean mine; //if the square is a mine.

    private int numRowsS, numColsS, numMinesS, state, number; //todo num rows and cols might not be needed.


    public MS_Square() {
        mine = false;
        number = 0;
        state = UP;
    }


    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int count) {
        this.number = count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}



