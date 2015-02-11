package pack1;

/**
 * @author othscs120
 *         Created on: 11/5/2014 , Time is :  1:07 PM
 *         Part of Project: MineSweeper
 */

public class MS_Map {

    private int numRowsM, numColsM, numMinesM;
    private MS_Square[][] grid;

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
     * @return data at the provided coord.
     */
    MS_Square getSquare(int c, int r) {
        if ((c < numColsM && c >= 0) && (r < numRowsM && r >= 0))
            return grid[r][c];
        else
            return null;
    }


    /**
     * Method to generate the game map.
     *
     * @param clickedRow the row that was clicked on.
     * @param clickedCol the col that was clicked on.
     */
    private void createMap(int clickedRow, int clickedCol) {
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

            if (randomX != clickedCol && randomY != clickedRow)  //does not allow clicked to be a mine
                grid[randomY][randomX].setMine(true);
            else
                m--;
        }
        Logger.logCodeMessage("Mines set Success. Number of mines is " + numMinesM);
        //todo set numbers use getMineCounter
        //set numbers -----------------------
    }


}
