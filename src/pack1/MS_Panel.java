package pack1;

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

<<<<<<< HEAD
public class MS_Panel extends JPanel implements MouseListener, MouseMotionListener, Runnable {

=======
public class MS_Panel extends JFrame implements MouseListener, MouseMotionListener, Runnable {

    MS_Game game;
>>>>>>> b2d33d5fc9757c4cf108a8d01fdf70114f58d8de
    int numColsP;
    int numRowsP;
    Image digitEmpty, dead, oh, down, happy, happyDown, shades, digitNine, digitEight, digitSeven, digitSix, digitFive,
            digitFour, digitThree, digitHyphen, digitTwo, digitOne, digitZero, eight, seven, six, five, four, three,
            two, one, empty, unclicked, flag, question, mine, incorrectFlag, exploded;
<<<<<<< HEAD
    private MS_Game game;
=======
>>>>>>> b2d33d5fc9757c4cf108a8d01fdf70114f58d8de


    public MS_Panel(int numCols, int numRows, int numMines) {

<<<<<<< HEAD
        setSize(numCols * 16, numRows * 16 + 130); //todo extract 130 to additionalHeight variable.
        this.numColsP = numCols;
        this.numRowsP = numRows;
        game = new MS_Game(numRows, numCols, numMines);
=======
        setSize(numCols * 16, numRows * 16);
        this.numColsP = numCols;
        this.numRowsP = numRows;

>>>>>>> b2d33d5fc9757c4cf108a8d01fdf70114f58d8de
        try {

            dead = ImageIO.read((new File("Dead.png")));
            oh = ImageIO.read((new File("Oh.png")));
            down = ImageIO.read((new File("Down.png")));
            happy = ImageIO.read((new File("Happy.png")));
            happyDown = ImageIO.read((new File("Happy_Down.png")));
            shades = ImageIO.read((new File("Shades.png")));
            digitNine = ImageIO.read((new File("Digit_Nine.png")));
            digitEmpty = ImageIO.read((new File("Digit_Empty.png")));
            digitEight = ImageIO.read((new File("Digit_Eight.png")));
            digitSeven = ImageIO.read((new File("Digit_Seven.png")));
            digitSix = ImageIO.read((new File("Digit_Six.png")));
            digitFive = ImageIO.read((new File("Digit_Five.png")));
            digitFour = ImageIO.read((new File("Digit_Four.png")));
            digitThree = ImageIO.read((new File("Digit_Three.png")));
            digitHyphen = ImageIO.read((new File("Digit_Hyphen.png")));
            digitTwo = ImageIO.read((new File("Digit_Two.png")));
            digitOne = ImageIO.read((new File("Digit_One.png")));
            digitZero = ImageIO.read((new File("Digit_Zero.png")));
            eight = ImageIO.read((new File("Eight.png")));
            seven = ImageIO.read((new File("Seven.png")));
            six = ImageIO.read((new File("Six.png")));
            five = ImageIO.read((new File("Five.png")));
            four = ImageIO.read((new File("Four.png")));
            three = ImageIO.read((new File("Three.png")));
            two = ImageIO.read((new File("Two.png")));
            one = ImageIO.read((new File("One.png")));
            empty = ImageIO.read((new File("Empty.png")));
            unclicked = ImageIO.read((new File("Unclicked.png")));
            flag = ImageIO.read((new File("Flag.png")));
            question = ImageIO.read((new File("Question.png")));
            mine = ImageIO.read((new File("Mine.png")));
            incorrectFlag = ImageIO.read((new File("IncorrectFlag.png")));
            exploded = ImageIO.read((new File("Exploded.png")));
            Logger.logOtherMessage("ImageLoader", "Succeeded.");
        } catch (Exception e) {
            System.err.println("Error Loading Images: " + e.getMessage());
            Logger.logErrorMessage("Error with loading images. Exiting...");
            System.exit(-1); //if loading fails, end the program.
        }
        addMouseListener(this);
        addNotify();


    }

    public void run() {
        while (true) {
            paint(this.getGraphics());
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                System.err.println("Error Sleeping.");
                Logger.logErrorMessage("Error Sleeping Thread.");
            }
        }
    }

    public void paint(Graphics g) {
<<<<<<< HEAD
        //paint game
        g.setColor(Color.WHITE);
        //todo make gui at top of screen

        for (int r = 0; r < game.getNumRowsG(); r++) {
            for (int c = 0; c < game.getNumColsG(); c++) {
                MS_Map m = game.getMap();
                if (m.getSquare(c, r).isMine()) {
                    g.drawImage(mine, c * 16, r * 16 + 130, null);
                } else
                    g.drawImage(empty, c * 16, r * 16 + 130, null);
            }

        }

=======
        //paint game todo help not printing
        //g.setColor(Color.black);
        // g.fillRect(0,0,20,50);
>>>>>>> b2d33d5fc9757c4cf108a8d01fdf70114f58d8de

    }

    public void addNotify() {
        super.addNotify();
        Thread a = new Thread();
        a.start();
        Logger.logCodeMessage("Thread Created Successfully.");
    }

    public void mousePressed(MouseEvent e) {
        //used for game features

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        //not used
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        //game features
    }

    public void mouseDragged(MouseEvent e) {
        //game feat
    }

    /**
     * Method to determine if the provided coords are within the user-set grid size.
     *
     * @param x column
     * @param y row
     * @return returns if the provided coords are within the grid.
     */
    public boolean isInGrid(int x, int y) {
        if ((x <= numColsP && x > 0) && (y <= numRowsP && y > 0)) //todo check
            return true;
        else
            return false;
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


}
