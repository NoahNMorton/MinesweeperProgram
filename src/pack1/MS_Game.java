package pack1;

/**
 * @author othscs120
 *         Created on: 11/4/2014 , Time is :  1:36 PM
 *         Part of Project: MineSweeper
 */

public class MS_Game {

    public static final int PLAYING = 0, WIN = 1, LOSE = 2, NOT_STARTED = 3;
    MS_Map map;
    private int numRowsG, numColsG, numMinesG, numMarked, state;
    private long startTime = 0; //todo stores starting time
    private int deadSeconds; //todo stores time of stop

    public MS_Game(int numCols, int numRows, int numMines) {
        this.numColsG = numCols;
        this.numRowsG = numRows;
        this.numMinesG = numMines;
        numMarked = 0;
        state = NOT_STARTED; //todo might need to set to playing. >ask teach
        startTime = System.nanoTime();
        makeGame(numCols, numRows);

    }

    public int getNumRowsG() {
        return numRowsG;
    }

    public int getNumColsG() {
        return numColsG;
    }

    public int getNumMinesG() {
        return numMinesG;
    }

    public int getNumMarked() {
        return numMarked;
    }

    public void setNumMarked(int numMarked) {
        this.numMarked = numMarked;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * Method to return current time in seconds
     *
     * @param startTime the time the game was started
     * @return seconds in long format.
     */
    public long getSeconds(long startTime) {
        if (getState() == PLAYING) {
            return (System.nanoTime() - startTime) / 1000000000; //divide by 1000000000
        } else
            return 0;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getDeadSeconds() {
        return deadSeconds;
    }

    public MS_Map getMap() {
        return map;
    }

    /**
     * Method to determine how many mines are marked.
     *
     * @return amt of mines marked.
     */
    int getMineCounter() {
        int amtFlagged = 0;
        for (int r = 0; r < numRowsG; r++) {
            for (int c = 0; c < numColsG; c++) {
                if (map.getSquare(c, r).getState() == MS_Square.FLAG) {
                    amtFlagged++;
                }
            }
        }
        return amtFlagged;
    }

    /**
     * Recursive AI method to reveal squares when clicked.
     *
     * @param c the column to start at, ie clicked
     * @param r the row to start at, ie clicked
     */
    void reveal(int c, int r) {
        //todo recursive AI method to reveal squares
    }

    /**
     * Method to create the game, and initialise the map.
     *
     * @param c number of columns selected
     * @param r number of rows selected
     */
    void makeGame(int c, int r) {
        //todo makes the game, does not set mine at provided >check

        map = new MS_Map(numColsG, numRowsG, numMinesG, c, r);

    }

    void check() {
        //todo updates game


    }
}






