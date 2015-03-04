package mswindow;

import mslogic.Logger;
import mslogic.MS_Game;
import mslogic.MS_Map;
import mslogic.MS_Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;


/**
 * @author othscs120
 *         Created on: 11/4/2014 , Time is :  1:31 PM
 *         Part of Project: MineSweeper
 */


public class MS_Panel extends JPanel implements MouseListener, MouseMotionListener, Runnable {

    private static final int GUIEXTRAHEIGHT = 130;
    private static final boolean DEBUG = false;
    final int faceX = (getWidth() / 2) - 11, faceY = (GUIEXTRAHEIGHT / 2) - 10; //determines place of face
    public boolean mouseDown = false;
    int numColsP, numRowsP, columnP = -1, rowP = -1;
    Image digitEmpty, dead, oh, down, happy, happyDown, shades, digitNine, digitEight, digitSeven, digitSix, digitFive,
            digitFour, digitThree, digitHyphen, digitTwo, digitOne, digitZero, eight, seven, six, five, four, three,
            two, one, empty, unclicked, flag, question, mine, incorrectFlag, exploded;

    private MS_Game game;

    public MS_Panel(int numCols, int numRows, int numMines) {


        setSize(numCols * 16, numRows * 16 + GUIEXTRAHEIGHT);
        this.numColsP = numCols;
        this.numRowsP = numRows;
        game = new MS_Game(numRows, numCols, numMines);
        game.setState(MS_Game.NOT_STARTED);


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

    }

    /**
     * Method to get the column of the provided x.
     *
     * @param e used for getting x.
     * @return the column clicked.
     */
    public static int getColumnOffCoord(MouseEvent e) {
        return (e.getX()) / 16;
    }

    /**
     * Method to get the row of a coord.
     *
     * @param e used for getting y.
     * @return the row clicked.
     */
    public static int getRowOffCoord(MouseEvent e) {
        return (e.getY() - GUIEXTRAHEIGHT) / 16;
    }

    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            paint(this.getGraphics());
            try {
                Thread.sleep(35);
            } catch (Exception e) {
                System.err.println("Error Sleeping.");
                Logger.logErrorMessage("Error Sleeping Thread.");
            }
        }
    }

    public void paint(Graphics g) {

        long time = game.getSeconds(game.getStartTime());

        //paint game
        g.setColor(Color.WHITE);


        //GUI ---------------------------------

        g.drawRect(0, 0, getWidth(), GUIEXTRAHEIGHT);
        //draw the face in the gui
        switch (game.getState()) {
            case MS_Game.LOSE:
                g.drawImage(dead, faceX, faceY, null);
                break;
            case MS_Game.WIN:
                g.drawImage(shades, faceX, faceY, null);
                break;
            case MS_Game.NOT_STARTED:
                g.drawImage(oh, faceX, faceY, null);
                break;
            default:
                g.drawImage(happy, faceX, faceY, null);
                break;
        }

        showNumbers(g, time);
        showFlagNumbers(g);


        //Items -------------------------
        //Numbers -------------------------
        for (int r = 0; r < game.getNumRowsG(); r++) {
            for (int c = 0; c < game.getNumColsG(); c++) {
                MS_Map m = game.getMap();

                if (r == rowP && c == columnP && m.getSquare(c, r).getState() == MS_Square.UP) { //draw the square being clicked as down
                    g.drawImage(down, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                } else {
                    //noinspection ConstantConditions,PointlessBooleanExpression
                    if (m.getSquare(c, r).getState() == MS_Square.UP && !DEBUG) {
                        g.drawImage(unclicked, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                    } else if (m.getSquare(c, r).getState() == MS_Square.FLAG) {
                        g.drawImage(flag, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                    } else if (m.getSquare(c, r).getState() == MS_Square.QUESTION) {
                        g.drawImage(question, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                    } else if (m.getSquare(c, r).isMine()) {
                        g.drawImage(mine, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                    } else {
                        switch (m.getSquare(c, r).getNumber()) {
                            case 1:
                                g.drawImage(one, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                                break;
                            case 2:
                                g.drawImage(two, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                                break;
                            case 3:
                                g.drawImage(three, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                                break;
                            case 4:
                                g.drawImage(four, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                                break;
                            case 5:
                                g.drawImage(five, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                                break;
                            case 6:
                                g.drawImage(six, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                                break;
                            case 7:
                                g.drawImage(seven, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                                break;
                            case 8:
                                g.drawImage(eight, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                                break;
                            default:
                                g.drawImage(empty, c * 16, r * 16 + GUIEXTRAHEIGHT, null);
                                break;
                        }
                    }
                }
            }
        }
    }

    public void addNotify() {
        super.addNotify();
        Thread a = new Thread(this);
        a.start();
        Logger.logCodeMessage("Thread Created Successfully.");
    }

    public void mousePressed(MouseEvent e) {
        if (game.getState() == (MS_Game.LOSE)) {
            //do nothing
            Logger.logUserMessage("Disallowing clicking as game is over.");
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            if (game.getState() == MS_Game.NOT_STARTED && (e.getX() >= faceX && e.getX() <= faceX + 16) && (e.getY() >= faceY && e.getY() <= faceY + 16)) {
                game.setState(MS_Game.PLAYING);
                Logger.logCodeMessage("Starting Game.");
            }

            mouseDown = true;
            columnP = getColumnOffCoord(e);
            rowP = getRowOffCoord(e);

            System.out.println("User Pressed the mouse at " + e.getX() + "," + e.getY() + " at col " + columnP + "," + rowP);
            Logger.logUserMessage("Pressed the mouse at " + e.getX() + "," + e.getY() + " at col " + columnP + "," + rowP);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (game.getState() == (MS_Game.LOSE)) {
            //do nothing
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            int columnR = getColumnOffCoord(e), rowR = getRowOffCoord(e);
            System.out.println("User Released the mouse at " + e.getX() + "," + e.getY() + " at col " + columnR + "," + rowR);
            Logger.logUserMessage("Released the mouse at " + e.getX() + "," + e.getY() + " at col " + columnR + "," + rowR);
            game.reveal(columnR, rowR);

            if (game.getMap().getSquare(columnR, rowR).isMine()) {
                game.setState(MS_Game.LOSE);
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {

            if (game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).getState() == MS_Square.UP)
                game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).setState(MS_Square.FLAG);
            else if (game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).getState() == MS_Square.FLAG) {
                game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).setState(MS_Square.QUESTION);
            } else if (game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).getState() == MS_Square.QUESTION) {
                game.getMap().getSquare(getColumnOffCoord(e), getRowOffCoord(e)).setState(MS_Square.UP);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        //not used
    }

    public void mouseEntered(MouseEvent e) {
        //unused
    }

    public void mouseExited(MouseEvent e) {
        //unused
    }

    public void mouseMoved(MouseEvent e) {
        //game features
    }

    public void mouseDragged(MouseEvent e) {
        if (game.getState() == (MS_Game.LOSE)) {
            //do nothing
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            mouseDown = true;
            columnP = getColumnOffCoord(e);
            rowP = getRowOffCoord(e);
        }
    }

    /**
     * Analyses the provided square for what image to display.
     *
     * @param s an MS_Square to analyse.
     * @return the image to be displayed for the square.
     */
    public Image getSquare(MS_Square s) {
        //get square image

        if (s.getState() == MS_Square.FLAG)
            return flag;
        else if (s.getState() == MS_Square.QUESTION)
            return question;
        else if (s.getState() == MS_Square.UP)
            return unclicked;
        else if (s.getState() == MS_Square.SHOWN) {
            if (s.isMine()) {
                return mine;
            }
            switch (s.getNumber()) { //returns images if the object is a number
                case 0:
                    return empty;
                case 1:
                    return one;
                case 2:
                    return two;
                case 3:
                    return three;
                case 4:
                    return four;
                case 5:
                    return five;
                case 6:
                    return six;
                case 7:
                    return seven;
                case 8:
                    return eight;
            }

        }
        return null;
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
        int ones, tens = 0;
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

}
