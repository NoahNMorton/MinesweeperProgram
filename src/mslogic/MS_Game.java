package mslogic;

/**
 * @author othscs120
 *         Created on: 11/4/2014 , Time is :  1:36 PM
 *         Part of Project: MineSweeper
 */

public class MS_Game {

    public static final int PLAYING = 0, WIN = 1, LOSE = 2, NOT_STARTED = 3, EASY = 0, MEDIUM = 1, HARD = 2;
    private MS_Map map;
    private int numRowsG, numColsG, numMinesG, numMarked, state, winTime = 0;
    private long startTime;
    private int difficulty;

    public MS_Game(int numCols, int numRows, int numMines) {
        this.numColsG = numCols;
        this.numRowsG = numRows;
        this.numMinesG = numMines;
        difficulty = MEDIUM;
        numMarked = 0;
        state = NOT_STARTED;
        startTime = 0;
        makeGame(numCols, numRows); //makes the game.

    }

    public int getWinTime() {
        return winTime;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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

    public void setState(int newState) {
        if (newState == MS_Game.PLAYING)
            startTime = System.nanoTime(); //used for timer
        else if (newState == MS_Game.WIN)
            winTime = (int) getSeconds(startTime); //used for saving scores
        this.state = newState;
    }

    /**
     * Method to return current time in seconds
     *
     * @param startTime the time the game was started
     * @return seconds in long format.
     */
    public long getSeconds(long startTime) {
        if (getState() == PLAYING)
            return (System.nanoTime() - startTime) / 1000000000; //divide by 1000000000
        else
            return 0;
    }

    public long getStartTime() {
        return startTime;
    }

    public MS_Map getMap() {
        return map;
    }

    /**
     * Method to determine how many flags are remaining.
     *
     * @return amt of flags remaining.
     */
    public int getMineCounter() {
        int amtFlagged = 0;
        for (int r = 0; r < numRowsG; r++) {
            for (int c = 0; c < numColsG; c++) {
                if (map.getSquare(c, r).getState() == MS_Square.FLAG) {
                    amtFlagged++;
                }
            }
        }
        return numMinesG - amtFlagged;
    }

    /**
     * Recursive AI method to reveal squares when clicked.
     *
     * @param c the column to start at, ie clicked
     * @param r the row to start at, ie clicked
     */
    public void reveal(int c, int r) {

        if (map.isInGrid(c, r) && map.getSquare(c, r).getState() == MS_Square.UP) { //if valid, and not revealed
            map.getSquare(c, r).setState(MS_Square.SHOWN); //set to be shown
            if (map.getSquare(c, r).getNumber() == 0) { //if not number
                reveal(c - 1, r - 1); //call reveal on the 8 squares around the origin.
                reveal(c, r - 1);
                reveal(c + 1, r - 1);
                reveal(c - 1, r);
                reveal(c + 1, r);
                reveal(c - 1, r + 1);
                reveal(c, r + 1);
                reveal(c + 1, r + 1);
            }
        }
    }

    /**
     * Method to create the game, and initialise the map.
     *
     * @param c number of columns selected
     * @param r number of rows selected
     */
    private void makeGame(int c, int r) {
        map = new MS_Map(numColsG, numRowsG, numMinesG, c, r); //makes the game, does not set mine at provided
    }
}







