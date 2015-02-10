package pack1;

/**
 * @author othscs120
 *         Created on: 11/4/2014 , Time is :  1:36 PM
 *         Part of Project: MineSweeper
 */

public class MS_Game {

    private static final int PLAYING = 0, WIN = 1, LOSE = 2, NOT_STARTED = 3;
    MS_Map map;
    private int numRowsG, numColsG, numMinesG, numMarked, state;
    private long startTime = System.nanoTime(); //todo stores starting time
    private int deadSeconds; //todo stores time of stop

    public MS_Game(int numCols, int numRows, int numMines) {
        this.numColsG = numCols;
        this.numRowsG = numRows;
        this.numMinesG = numMines;
        numMarked = 0;
        state = NOT_STARTED;
<<<<<<< HEAD
        makeGame(numCols, numRows);
=======
>>>>>>> b2d33d5fc9757c4cf108a8d01fdf70114f58d8de
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

    public long getStartTime() {
        return startTime;
    }

    public int getDeadSeconds() {
        return deadSeconds;
    }

    public MS_Map getMap() {
        return map;
    }

    int getMineCounter() {
        return 0; //todo get mine counter
    }

    void reveal(int c, int r) {
        //todo recursive AI method to reveal squares
    }

    void makeGame(int c, int r) {
<<<<<<< HEAD

=======
        //todo makes the game, does not set mine at provided >check
>>>>>>> b2d33d5fc9757c4cf108a8d01fdf70114f58d8de
        map = new MS_Map(numColsG, numRowsG, numMinesG, c, r);

    }

    void check() {
        //todo updates game


<<<<<<< HEAD
    }
}
=======
        }
    }
>>>>>>> b2d33d5fc9757c4cf108a8d01fdf70114f58d8de



