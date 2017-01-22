package mslogic;

/**
 * @author othscs120
 *         Created on: 11/5/2014 , Time is :  1:07 PM
 *         Part of Project: MineSweeper
 */

public class MS_Map {

    @SuppressWarnings("CanBeFinal")
    private int numRowsM, numColsM, numMinesM;
    private MS_Square[][] grid; //the grid of squares that everything runs off of.

    public MS_Map(int numCols, int numRows, int numMines, int clickedCol, int clickedRow) {
        this.numColsM = numCols;
        this.numMinesM = numMines;
        this.numRowsM = numRows;

        createMap(clickedRow, clickedCol);
    }


    /**
     * Method to get the value of the provided location.
     *
     * @param c the column to check
     * @param r the row to check
     * @return data at the provided coordinate.
     */
    public MS_Square getSquare(int c, int r) {
        if ((c < numColsM && c >= 0) && (r < numRowsM && r >= 0))
            return grid[r][c];
        else
            return null;
    }

    /**
     * Method to determine if the provided coordinates are within the user-set grid size.
     *
     * @param x column
     * @param y row
     * @return returns if the provided coordinates are within the grid.
     */
    public boolean isInGrid(int x, int y) {
        return (x < numColsM && x >= 0) && (y < numRowsM && y >= 0);
    }


    /**
     * Method to generate the game map.
     *
     * @param clickedRow the row that was clicked on.
     * @param clickedCol the col that was clicked on.
     */
    public void createMap(int clickedRow, int clickedCol) {
        Logger.logCodeMessage("Creating map. Number of cols is " + numColsM + " Number of rows is " + numRowsM);

        //make the array
        grid = new MS_Square[numRowsM][numColsM];

        for (int y = 0; y < numRowsM; y++) {
            for (int x = 0; x < numColsM; x++) {
                grid[y][x] = new MS_Square();
            }
        }
        //set mines ---------------------
        Logger.logCodeMessage("Setting mines...");
        for (int m = 0; m < numMinesM; m++) {
            int randomY = (int) (Math.random() * numRowsM), randomX = (int) (Math.random() * numColsM);

            if (!getSquare(randomX, randomY).isMine() && (randomX != clickedCol && randomY != clickedRow))  //does not allow clicked to be a mine
                grid[randomY][randomX].setMine(true);
            else
                m--;
        }
        Logger.logCodeMessage("Mines set Success. Number of mines is " + numMinesM);

        //set numbers -----------------------
        Logger.logCodeMessage("Setting numbers...");
        int numOfMines = 0;


        for (int y1 = 0; y1 < numRowsM; y1++) {
            for (int x1 = 0; x1 < numColsM; x1++) {
                if (isInGrid(x1 - 1, y1 - 1) && grid[y1 - 1][x1 - 1].isMine()) { //top left
                    numOfMines++;
                }
                if (isInGrid(x1, y1 - 1) && grid[y1 - 1][x1].isMine()) {
                    numOfMines++;
                }
                if (isInGrid(x1 + 1, y1 - 1) && grid[y1 - 1][x1 + 1].isMine()) {
                    numOfMines++;
                }
                if (isInGrid(x1 - 1, y1) && grid[y1][x1 - 1].isMine()) {
                    numOfMines++;
                }
                if (isInGrid(x1 + 1, y1) && grid[y1][x1 + 1].isMine()) {
                    numOfMines++;
                }
                if (isInGrid(x1 - 1, y1 + 1) && grid[y1 + 1][x1 - 1].isMine()) {
                    numOfMines++;
                }
                if (isInGrid(x1, y1 + 1) && grid[y1 + 1][x1].isMine()) {
                    numOfMines++;
                }
                if (isInGrid(x1 + 1, y1 + 1) && grid[y1 + 1][x1 + 1].isMine()) {
                    numOfMines++;
                }

                if (numOfMines > 0) {
                    grid[y1][x1].setNumber(numOfMines);
                    numOfMines = 0;
                }
            }
        }

        Logger.logCodeMessage("Numbers set Success.");
        Logger.logCodeMessage("Map creation success.");
    }

    public MS_Square[][] getGrid() {
        return grid;
    }
}
