package mswindow;

import mslogic.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * @author othscs120
 *         Created on: 11/4/2014 , Time is :  1:31 PM
 *         Part of Project: MineSweeper
 */

public class MS_Panel extends JPanel implements MouseListener, MouseMotionListener {

    public static final int GUIEXTRAHEIGHT = 110;
    private static int flaggedMines; //number of currently accurately flagged mines.
    private static boolean showAll = false;
    ArrayList<ScoreEntry> easyArrayList, mediumArrayList, hardArrayList;
    private boolean leftMouseDown = false, rightMouseDown = false; //used for dragging.
    private int numColsP, numRowsP, columnP = -1, rowP = -1;
    private BufferedImage buffer;
    private Image digitEmpty, dead, oh, down, happy, happyDown, shades, digitNine, digitEight, digitSeven, digitSix, digitFive,
            digitFour, digitThree, digitHyphen, digitTwo, digitOne, digitZero, eight, seven, six, five, four, three,
            two, one, empty, unclicked, flag, question, mine, incorrectFlag, exploded;
    private int faceX, faceY; //the location to display the face.
    private MS_Game game;
    private boolean faceClicked = false; //if the face is being clicked.
    private Point clickedSquare;

    public MS_Panel(int numCols, int numRows, int numMines) {
        //init
        setSize(numCols * 16, numRows * 17 + GUIEXTRAHEIGHT + 15);
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        this.numColsP = numCols;
        this.numRowsP = numRows;
        game = new MS_Game(numRows, numCols, numMines);
        game.setState(MS_Game.NOT_STARTED);
        easyArrayList = new ArrayList<>();
        mediumArrayList = new ArrayList<>(); //arrays used for storing scores
        hardArrayList = new ArrayList<>();

        //determines place of face
        faceX = (getWidth() / 2) - 11;
        faceY = (GUIEXTRAHEIGHT / 2) - 10;

        try {

            dead = ImageIO.read((new File("resource/Dead.png")));
            oh = ImageIO.read((new File("resource/Oh.png")));
            down = ImageIO.read((new File("resource/Down.png")));
            happy = ImageIO.read((new File("resource/Happy.png")));
            happyDown = ImageIO.read((new File("resource/Happy_Down.png")));
            shades = ImageIO.read((new File("resource/Shades.png")));
            digitNine = ImageIO.read((new File("resource/Digit_Nine.png")));
            digitEmpty = ImageIO.read((new File("resource/Digit_Empty.png")));
            digitEight = ImageIO.read((new File("resource/Digit_Eight.png")));
            digitSeven = ImageIO.read((new File("resource/Digit_Seven.png")));
            digitSix = ImageIO.read((new File("resource/Digit_Six.png")));
            digitFive = ImageIO.read((new File("resource/Digit_Five.png")));
            digitFour = ImageIO.read((new File("resource/Digit_Four.png")));
            digitThree = ImageIO.read((new File("resource/Digit_Three.png")));
            digitHyphen = ImageIO.read((new File("resource/Digit_Hyphen.png")));
            digitTwo = ImageIO.read((new File("resource/Digit_Two.png")));
            digitOne = ImageIO.read((new File("resource/Digit_One.png")));
            digitZero = ImageIO.read((new File("resource/Digit_Zero.png")));
            eight = ImageIO.read((new File("resource/Eight.png")));
            seven = ImageIO.read((new File("resource/Seven.png")));
            six = ImageIO.read((new File("resource/Six.png")));
            five = ImageIO.read((new File("resource/Five.png")));
            four = ImageIO.read((new File("resource/Four.png")));
            three = ImageIO.read((new File("resource/Three.png")));
            two = ImageIO.read((new File("resource/Two.png")));
            one = ImageIO.read((new File("resource/One.png")));
            empty = ImageIO.read((new File("resource/Empty.png")));
            unclicked = ImageIO.read((new File("resource/Unclicked.png")));
            flag = ImageIO.read((new File("resource/Flag.png")));
            question = ImageIO.read((new File("resource/Question.png")));
            mine = ImageIO.read((new File("resource/Mine.png")));
            incorrectFlag = ImageIO.read((new File("resource/IncorrectFlag.png")));
            exploded = ImageIO.read((new File("resource/Exploded.png")));
            Logger.logOtherMessage("ImageLoader", "Succeeded.");
        } catch (Exception e) {
            System.err.println("Error Loading Images: " + e.getMessage());
            Logger.logErrorMessage("Error with loading images. Exiting...");
            System.exit(-1); //if loading fails, end the program.
        }
        addMouseListener(this);
        addMouseMotionListener(this);
        readAllScores(); //read all scores from the scores file into the 3 arraylists.
    }

    /**
     * Method to get the column of the provided x.
     *
     * @param e used for getting x.
     * @return the column clicked.
     */
    private static int getColumnOffCoord(MouseEvent e) {
        return (e.getX()) / 16;
    }

    /**
     * Method to get the row of a coord.
     *
     * @param e used for getting y.
     * @return the row clicked.
     */
    private static int getRowOffCoord(MouseEvent e) {
        return (e.getY() - GUIEXTRAHEIGHT) / 16;
    }

    /**
     * Recreates the panel for a resize.
     *
     * @param numCols  number of columns to create with
     * @param numRows  number of rows to create with
     * @param numMines number of mines to create
     */
    public void recreate(int numCols, int numRows, int numMines, int newDifficulty) {
        setSize(numCols * 16, numRows * 16 + GUIEXTRAHEIGHT);
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        this.numColsP = numCols;
        this.numRowsP = numRows;
        game = new MS_Game(numRows, numCols, numMines);
        game.setState(MS_Game.NOT_STARTED);
        game.setDifficulty(newDifficulty);
        //determines place of face
        faceX = (getWidth() / 2) - 11;
        faceY = (GUIEXTRAHEIGHT / 2) - 10;
        repaint();
    }

    public MS_Game getGame() {
        return game;
    }

    public void paint(Graphics g) {
        Graphics bg = buffer.getGraphics();

        long time = game.getSeconds(game.getStartTime());

        bg.setColor(Color.white);
        bg.fillRect(0, 0, getWidth(), getHeight());

        //paint game
        bg.setColor(Color.WHITE);

        //GUI ---------------------------------

        bg.drawRect(0, 0, getWidth(), GUIEXTRAHEIGHT);
        //draw the face in the gui
        switch (game.getState()) {
            case MS_Game.LOSE:

                bg.drawImage(dead, faceX, faceY, null);
                break;
            case MS_Game.WIN:
                bg.drawImage(shades, faceX, faceY, null);
                break;
            case MS_Game.NOT_STARTED:
                bg.drawImage(oh, faceX, faceY, null);
                break;
            default:
                bg.drawImage(happy, faceX, faceY, null);
                break;
        }
        //show the timer and flags left
        showNumbers(bg, time);
        showFlagNumbers(bg);

        //Items --------------------------
        //Numbers -------------------------
        for (int r = 0; r < game.getNumRowsG(); r++) {
            for (int c = 0; c < game.getNumColsG(); c++) {
                MS_Map m = game.getMap();

                //noinspection ConstantConditions,PointlessBooleanExpression
                if (m.getSquare(c, r).getState() == MS_Square.UP && !showAll) {
                    bg.drawImage(unclicked, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                } else if (m.getSquare(c, r).getState() == MS_Square.FLAG) {
                    bg.drawImage(flag, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                } else if (m.getSquare(c, r).getState() == MS_Square.QUESTION) {
                    bg.drawImage(question, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                } else if (m.getSquare(c, r).isMine()) {
                    if (game.getState() == MS_Game.LOSE) {
                        bg.drawImage(mine, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                        bg.drawImage(exploded, (int) clickedSquare.getX() * 16, (int) clickedSquare.getY() * 16 + GUIEXTRAHEIGHT, null);
                    } else
                        bg.drawImage(mine, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                } else {
                    switch (m.getSquare(c, r).getNumber()) {
                        case 1:
                            bg.drawImage(one, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                            break;
                        case 2:
                            bg.drawImage(two, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                            break;
                        case 3:
                            bg.drawImage(three, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                            break;
                        case 4:
                            bg.drawImage(four, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                            break;
                        case 5:
                            bg.drawImage(five, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                            break;
                        case 6:
                            bg.drawImage(six, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                            break;
                        case 7:
                            bg.drawImage(seven, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                            break;
                        case 8:
                            bg.drawImage(eight, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                            break;
                        default:
                            bg.drawImage(empty, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                            break;
                    }
                }
            }
        }

        if ((leftMouseDown ^ rightMouseDown) && rowP >= 0) { //draw the square being clicked as down, and draw face surprised
            bg.drawImage(down, columnP * 16, (rowP * 16) + GUIEXTRAHEIGHT, null);
            bg.drawImage(oh, faceX, faceY, null);
        } else if (rightMouseDown && leftMouseDown && rowP >= 0) { //dual click drag, should draw all 8 down as well
            //draw the 8 squares around the mouse as down
            bg.drawImage(down, columnP * 16, (rowP * 16) + GUIEXTRAHEIGHT, null); //center

            //draw the remaining clockwise, starting at the top left corner
            bg.drawImage(down, (columnP - 1) * 16, ((rowP - 1) * 16) + GUIEXTRAHEIGHT, null);
            bg.drawImage(down, (columnP) * 16, ((rowP - 1) * 16) + GUIEXTRAHEIGHT, null);
            bg.drawImage(down, (columnP + 1) * 16, ((rowP - 1) * 16) + GUIEXTRAHEIGHT, null);
            bg.drawImage(down, (columnP + 1) * 16, ((rowP) * 16) + GUIEXTRAHEIGHT, null);
            bg.drawImage(down, (columnP + 1) * 16, ((rowP + 1) * 16) + GUIEXTRAHEIGHT, null);
            bg.drawImage(down, (columnP) * 16, ((rowP + 1) * 16) + GUIEXTRAHEIGHT, null);
            bg.drawImage(down, (columnP - 1) * 16, ((rowP + 1) * 16) + GUIEXTRAHEIGHT, null);
            bg.drawImage(down, (columnP - 1) * 16, ((rowP) * 16) + GUIEXTRAHEIGHT, null);

            bg.drawImage(oh, faceX, faceY, null); //draw the face surprised
        }

        g.drawImage(buffer, 0, 0, null);
    }

    public void mousePressed(MouseEvent e) {
        if (game.getState() == MS_Game.NOT_STARTED && (e.getX() >= faceX && e.getX() <= faceX + 24) && (e.getY() >= faceY
                && e.getY() <= faceY + 24) && e.getButton() == MouseEvent.BUTTON1) { //start the game if face clicked

            game.setState(MS_Game.PLAYING);
            faceClicked = true;

            Logger.logCodeMessage("Starting Game.");
        } else if (game.getState() == MS_Game.LOSE && (e.getX() >= faceX && e.getX() <= faceX + 24) && (e.getY() >= faceY
                && e.getY() <= faceY + 24) && e.getButton() == MouseEvent.BUTTON1) { //if user clicked on the face to restart

            game.setState(MS_Game.PLAYING);
            faceClicked = true;

            for (int y = 0; y < numRowsP; y++) { //set all squares back to hidden
                for (int x = 0; x < numColsP; x++) {
                    game.getMap().getGrid()[y][x].setState(MS_Square.UP);
                }
            }
            showAll = false; //hide the board
            faceClicked = true;
            game.getMap().createMap(getColumnOffCoord(e), getRowOffCoord(e)); //recreate the map
            flaggedMines = 0;
            repaint();

            Logger.logCodeMessage("Restarting Game, after a loss.");
        } else if (game.getState() == MS_Game.WIN && (e.getX() >= faceX && e.getX() <= faceX + 24) && (e.getY() >= faceY
                && e.getY() <= faceY + 24) && e.getButton() == MouseEvent.BUTTON1) {

            game.setState(MS_Game.PLAYING);
            faceClicked = true;

            for (int y = 0; y < numRowsP; y++) { //set all squares back to hidden
                for (int x = 0; x < numColsP; x++) {
                    game.getMap().getGrid()[y][x].setState(MS_Square.UP);
                }
            }
            showAll = false; //hide the board
            faceClicked = true;
            game.getMap().createMap(getColumnOffCoord(e), getRowOffCoord(e)); //recreate the map
            repaint();
            Logger.logCodeMessage("Restarting Game, after a win.");
        }
        if (game.getState() != MS_Game.PLAYING || !faceClicked) {
            //do nothing, don't allow interaction until game is playing.
            Logger.logUserMessage("Disallowing clicking as game is over/not started.");
        } else if (e.getButton() == MouseEvent.BUTTON1) { //left click, this is used for dragging

            leftMouseDown = true;
            columnP = getColumnOffCoord(e);
            rowP = getRowOffCoord(e);

        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightMouseDown = true;
            columnP = getColumnOffCoord(e);
            rowP = getRowOffCoord(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            leftMouseDown = false;
        else if (e.getButton() == MouseEvent.BUTTON3)
            rightMouseDown = false;

        //noinspection StatementWithEmptyBody
        if (game.getState() == MS_Game.LOSE || game.getState() == MS_Game.NOT_STARTED || game.getState() == MS_Game.WIN || !faceClicked) {
            //do nothing
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            int columnR = getColumnOffCoord(e), rowR = getRowOffCoord(e);

            game.reveal(columnR, rowR); //call reveal to start revealing squares

            try {
                if (game.getMap().getSquare(columnR, rowR).isMine() &&
                        game.getMap().getSquare(columnR, rowR).getState() != MS_Square.FLAG) { //if they clicked on an un-flagged mine
                    game.setState(MS_Game.LOSE);
                    clickedSquare = new Point(getColumnOffCoord(e), getRowOffCoord(e)); //get the clicked square, this is used for painting the exploded mine
                    showAll = true; //reveal the whole board
                    Logger.logUserMessage("User has lost.");
                    faceClicked = false;
                    flaggedMines = 0; //reset the flagged mines counter
                }
            } catch (Exception ignored) {
            }

        } else if (e.getButton() == MouseEvent.BUTTON3) {

            if (game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).getState() == MS_Square.UP) {
                if (game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).isMine()) //if the flagged square is a mine
                    flaggedMines++;
                game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).setState(MS_Square.FLAG);
            } else if (game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).getState() == MS_Square.FLAG) {
                game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).setState(MS_Square.QUESTION);
            } else if (game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).getState() == MS_Square.QUESTION) {
                game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).setState(MS_Square.UP);
                if (game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).isMine()) //if they unflag a mine, free it back up
                    flaggedMines--;
            }
            checkForWin(); //check to see if the last mine was flagged.
        }
    }

    @Deprecated
    public void mouseClicked(MouseEvent e) {
        //not used
    }

    @Deprecated
    public void mouseEntered(MouseEvent e) {
        //unused
    }

    @Deprecated
    public void mouseExited(MouseEvent e) {
        //unused
    }

    @Deprecated
    public void mouseMoved(MouseEvent e) {
        //unused
    }

    public void mouseDragged(MouseEvent e) {
        //noinspection StatementWithEmptyBody
        if (game.getState() == game.PLAYING) { //if playing
            columnP = getColumnOffCoord(e);
            rowP = getRowOffCoord(e);
            //System.out.println(columnP + "," + rowP);
        }
    }

    /**
     * Method to show the timer.
     *
     * @param g    Graphics from paint
     * @param time current game time.
     */
    private void showNumbers(Graphics g, /*love you*/ long time) {

        int ones = (int) ((time) % 10);
        int tens = (int) ((time) % 100) / 10;
        int hundreds = (int) time / 100;

        switch (ones) {
            case 1:
                g.drawImage(digitOne, (int) (getWidth() * 0.30), 50, null);
                break;
            case 2:
                g.drawImage(digitTwo, (int) (getWidth() * 0.30), 50, null);
                break;
            case 3:
                g.drawImage(digitThree, (int) (getWidth() * 0.30), 50, null);
                break;
            case 4:
                g.drawImage(digitFour, (int) (getWidth() * 0.30), 50, null);
                break;
            case 5:
                g.drawImage(digitFive, (int) (getWidth() * 0.30), 50, null);
                break;
            case 6:
                g.drawImage(digitSix, (int) (getWidth() * 0.30), 50, null);
                break;
            case 7:
                g.drawImage(digitSeven, (int) (getWidth() * 0.30), 50, null);
                break;
            case 8:
                g.drawImage(digitEight, (int) (getWidth() * 0.30), 50, null);
                break;
            case 9:
                g.drawImage(digitNine, (int) (getWidth() * 0.30), 50, null);
                break;
            default:
                g.drawImage(digitZero, (int) (getWidth() * 0.30), 50, null);
        }
        switch (tens) {
            case 1:
                g.drawImage(digitOne, (int) (getWidth() * 0.20), 50, null);
                break;
            case 2:
                g.drawImage(digitTwo, (int) (getWidth() * 0.20), 50, null);
                break;
            case 3:
                g.drawImage(digitThree, (int) (getWidth() * 0.20), 50, null);
                break;
            case 4:
                g.drawImage(digitFour, (int) (getWidth() * 0.20), 50, null);
                break;
            case 5:
                g.drawImage(digitFive, (int) (getWidth() * 0.20), 50, null);
                break;
            case 6:
                g.drawImage(digitSix, (int) (getWidth() * 0.20), 50, null);
                break;
            case 7:
                g.drawImage(digitSeven, (int) (getWidth() * 0.20), 50, null);
                break;
            case 8:
                g.drawImage(digitEight, (int) (getWidth() * 0.20), 50, null);
                break;
            case 9:
                g.drawImage(digitNine, (int) (getWidth() * 0.20), 50, null);
                break;
            default:
                g.drawImage(digitZero, (int) (getWidth() * 0.20), 50, null);
        }
        switch (hundreds) {
            case 1:
                g.drawImage(digitOne, (int) (getWidth() * 0.10), 50, null);
                break;
            case 2:
                g.drawImage(digitTwo, (int) (getWidth() * 0.10), 50, null);
                break;
            case 3:
                g.drawImage(digitThree, (int) (getWidth() * 0.10), 50, null);
                break;
            case 4:
                g.drawImage(digitFour, (int) (getWidth() * 0.10), 50, null);
                break;
            case 5:
                g.drawImage(digitFive, (int) (getWidth() * 0.10), 50, null);
                break;
            case 6:
                g.drawImage(digitSix, (int) (getWidth() * 0.10), 50, null);
                break;
            case 7:
                g.drawImage(digitSeven, (int) (getWidth() * 0.10), 50, null);
                break;
            case 8:
                g.drawImage(digitEight, (int) (getWidth() * 0.10), 50, null);
                break;
            case 9:
                g.drawImage(digitNine, (int) (getWidth() * 0.10), 50, null);
                break;
            default:
                g.drawImage(digitZero, (int) (getWidth() * 0.10), 50, null);
        }
    }

    /**
     * Method to show the amount of squares flagged in the gui
     *
     * @param g graphics from paint method
     */
    private void showFlagNumbers(Graphics g) {
        int ones, tens;
        game.setNumMarked(game.getMineCounter());
        int numMarked = game.getNumMarked();

        if (numMarked < 0) //if marked is negative
        {
            String number = "" + numMarked;
            char tOnes = number.charAt(1);
            String tOnes2 = "" + tOnes;
            ones = Integer.parseInt(tOnes2);
            switch (ones) {
                case 1:
                    g.drawImage(digitOne, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 2:
                    g.drawImage(digitTwo, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 3:
                    g.drawImage(digitThree, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 4:
                    g.drawImage(digitFour, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 5:
                    g.drawImage(digitFive, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 6:
                    g.drawImage(digitSix, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 7:
                    g.drawImage(digitSeven, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 8:
                    g.drawImage(digitEight, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 9:
                    g.drawImage(digitNine, (int) (getWidth() * 0.80), 50, null);
                    break;
                default:
                    g.drawImage(digitZero, (int) (getWidth() * 0.80), 50, null);
            }
            g.drawImage(digitHyphen, (int) (getWidth() * 0.70), 50, null); //draw the negative place

        } else if (numMarked < 10) {
            String number = "" + numMarked;
            char tOnes = number.charAt(0);
            String tOnes2 = "" + tOnes;
            ones = Integer.parseInt(tOnes2);
            switch (ones) {
                case 1:
                    g.drawImage(digitOne, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 2:
                    g.drawImage(digitTwo, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 3:
                    g.drawImage(digitThree, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 4:
                    g.drawImage(digitFour, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 5:
                    g.drawImage(digitFive, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 6:
                    g.drawImage(digitSix, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 7:
                    g.drawImage(digitSeven, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 8:
                    g.drawImage(digitEight, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 9:
                    g.drawImage(digitNine, (int) (getWidth() * 0.80), 50, null);
                    break;
                default:
                    g.drawImage(digitZero, (int) (getWidth() * 0.80), 50, null);
            }
            g.drawImage(digitZero, (int) (getWidth() * 0.70), 50, null); //draw the tens place

        } else { //if above 10 - you gotta do what you gotta do. Int to char, to string, and back to int. such hack
            String number = "" + numMarked;
            char tTens = number.charAt(0);
            char tOnes = number.charAt(1);
            String tOnes2 = "" + tOnes;
            String tTens2 = "" + tTens;

            ones = Integer.parseInt(tOnes2);
            tens = Integer.parseInt(tTens2);

            switch (ones) {
                case 1:
                    g.drawImage(digitOne, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 2:
                    g.drawImage(digitTwo, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 3:
                    g.drawImage(digitThree, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 4:
                    g.drawImage(digitFour, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 5:
                    g.drawImage(digitFive, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 6:
                    g.drawImage(digitSix, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 7:
                    g.drawImage(digitSeven, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 8:
                    g.drawImage(digitEight, (int) (getWidth() * 0.80), 50, null);
                    break;
                case 9:
                    g.drawImage(digitNine, (int) (getWidth() * 0.80), 50, null);
                    break;
                default:
                    g.drawImage(digitZero, (int) (getWidth() * 0.80), 50, null);
            }
            switch (tens) {
                case 1:
                    g.drawImage(digitOne, (int) (getWidth() * 0.70), 50, null);
                    break;
                case 2:
                    g.drawImage(digitTwo, (int) (getWidth() * 0.70), 50, null);
                    break;
                case 3:
                    g.drawImage(digitThree, (int) (getWidth() * 0.70), 50, null);
                    break;
                case 4:
                    g.drawImage(digitFour, (int) (getWidth() * 0.70), 50, null);
                    break;
                case 5:
                    g.drawImage(digitFive, (int) (getWidth() * 0.70), 50, null);
                    break;
                case 6:
                    g.drawImage(digitSix, (int) (getWidth() * 0.70), 50, null);
                    break;
                case 7:
                    g.drawImage(digitSeven, (int) (getWidth() * 0.70), 50, null);
                    break;
                case 8:
                    g.drawImage(digitEight, (int) (getWidth() * 0.70), 50, null);
                    break;
                case 9:
                    g.drawImage(digitNine, (int) (getWidth() * 0.70), 50, null);
                    break;
                default:
                    g.drawImage(digitZero, (int) (getWidth() * 0.70), 50, null);
            }
        }
    }

    /**
     * Checks the board for a win, gets the username of the player and writes it to the scores file.
     */
    @SuppressWarnings("AccessStaticViaInstance")
    private void checkForWin() {
        if (flaggedMines == game.getNumMinesG()) //if all mines have been flagged
        {
            System.out.println("User has won.");
            flaggedMines = 0; //reset after a win
            game.setState(MS_Game.WIN);
            //code to set high score
            if (game.getDifficulty() == game.EASY) {
                try {
                    FileWriter fileWriter = new FileWriter("easy_scores.txt");
                    String username = JOptionPane.showInputDialog(null, "New high score! Username to enter: ");
                    easyArrayList.add(new ScoreEntry(username, game.getWinTime()));

                    for (ScoreEntry anEasyArrayList : easyArrayList) {
                        fileWriter.write(anEasyArrayList.toString() + "\n"); // write the data back into the file from the arraylists
                    }
                    fileWriter.close();

                } catch (IOException e) {
                    System.out.println("Issue with writing scores file, " + e.getMessage());
                }
            } else if (game.getDifficulty() == game.MEDIUM) {
                try {
                    FileWriter fileWriter = new FileWriter("medium_scores.txt");
                    String username = JOptionPane.showInputDialog(null, "New high score! Username to enter: ");
                    mediumArrayList.add(new ScoreEntry(username, game.getWinTime()));

                    for (ScoreEntry aMediumArrayList : mediumArrayList) {
                        fileWriter.write(aMediumArrayList.toString() + "\n"); // write the data back into the file from the arraylists
                    }

                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("Issue with writing scores file, " + e.getMessage());
                }

            } else if (game.getDifficulty() == game.HARD) {
                try {
                    FileWriter fileWriter = new FileWriter("hard_scores.txt");
                    String username = JOptionPane.showInputDialog(null, "New high score! Username to enter: ");
                    hardArrayList.add(new ScoreEntry(username, game.getWinTime()));

                    for (ScoreEntry aHardArrayList : hardArrayList) {
                        fileWriter.write(aHardArrayList.toString() + "\n"); // write the data back into the file from the arraylists
                    }
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("Issue with writing scores file, " + e.getMessage());
                }
            }
        }
    }

    /**
     * Reads all the scores from the score files into the score entry arrayLists.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void readAllScores() {
        Scanner easyFileScanner;
        Scanner mediumFileScanner;
        Scanner hardFileScanner;

        //check existence of files
        File easyFile = new File("easy_scores.txt");
        File mediumFile = new File("medium_scores.txt");
        File hardFile = new File("hard_scores.txt");
        if (!easyFile.exists() || !mediumFile.exists() || !hardFile.exists()) {
            System.out.println("Files don't exist, creating...");
            Logger.logCodeMessage("Files don't exist, creating...");
            try {
                easyFile.createNewFile();
                mediumFile.createNewFile();
                hardFile.createNewFile();
            } catch (IOException ignored) {
            }
        }

        try { //set up the scanners for the 3 files
            easyFileScanner = new Scanner(new FileReader(easyFile));
            mediumFileScanner = new Scanner(new FileReader(mediumFile));
            hardFileScanner = new Scanner(new FileReader(hardFile));
        } catch (FileNotFoundException e) {
            System.err.println("Issues with reading files. " + e.getMessage());
            Logger.logErrorMessage("Cannot read score files.");
            return;
        }

        //Easy difficulty
        int linesRead = 0; //ensure no more than 5 scores are read.
        try {
            while (easyFileScanner.hasNextLine() && linesRead < 6) {
                linesRead++;
                String line = easyFileScanner.nextLine();
                String name = line.split("[-]")[0];
                int score = Integer.parseInt(line.split("[-]")[1]);
                easyArrayList.add(new ScoreEntry(name, score));
            }
        } catch (Exception e) {
            System.out.println("No values in easy file.");
        }

        linesRead = 0; //reset the counter
        //Medium difficulty
        try {
            while (mediumFileScanner.hasNext() && linesRead < 6) {
                linesRead++;
                String line = mediumFileScanner.nextLine();
                String name = line.split("[-]")[0];
                int score = Integer.parseInt(line.split("[-]")[1]);
                mediumArrayList.add(new ScoreEntry(name, score));
            }
        } catch (Exception e) {
            System.out.println("No values in medium file.");
        }
        linesRead = 0;
        //Hard difficulty
        try {
            while (hardFileScanner.hasNext() && linesRead < 6) {
                linesRead++;
                String line = hardFileScanner.nextLine();
                String name = line.split("[-]")[0];
                int score = Integer.parseInt(line.split("[-]")[1]);
                hardArrayList.add(new ScoreEntry(name, score));
            }
        } catch (Exception e) {
            System.out.println("No values in hard file.");
        }

        easyFileScanner.close();
        mediumFileScanner.close(); //close all the files
        hardFileScanner.close();

        easyArrayList.sort(null);
        mediumArrayList.sort(null); //sort all the arrayLists
        hardArrayList.sort(null);

    }


}
