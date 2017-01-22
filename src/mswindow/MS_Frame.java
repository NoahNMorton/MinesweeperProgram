package mswindow;

import mslogic.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * @author othscs120 Created on: 11/4/2014 , Time is : 1:28 PM Part of Project:
 * MineSweeper
 */
@SuppressWarnings("AccessStaticViaInstance")
public class MS_Frame extends JFrame implements Runnable {

    private MS_Panel p;

    @SuppressWarnings("SameParameterValue")
    public MS_Frame(int rows, int cols, int mines) {
        super("MineSweeper");

        //Menu bar ------------------------------------------------
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //File menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenu newGameMenu = new JMenu("New Game");
        fileMenu.add(newGameMenu);
        JMenuItem easyMap = new JMenuItem("Easy");
        newGameMenu.add(easyMap);
        JMenuItem mediumMap = new JMenuItem("Medium");
        newGameMenu.add(mediumMap);
        JMenuItem hardMap = new JMenuItem("Hard");
        newGameMenu.add(hardMap);

        JMenuItem highScores = new JMenuItem("View Scores");
        fileMenu.add(highScores);

        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        //Help menu
        JMenu help = new JMenu("Help");
        menuBar.add(help);
        JMenuItem rules = new JMenuItem("Rules");
        help.add(rules);
        JMenuItem about = new JMenuItem("About");
        help.add(about);

        //Menu Actions ----------------------------------------------
        //Help menu
        rules.addActionListener(e -> JOptionPane.showMessageDialog(null, "The game is played by revealing squares of the grid by clicking or \n"
                + "otherwise indicating each square. If a square containing a mine is revealed, the player loses the \n"
                + "game. If no mine is revealed, a digit is instead displayed in the square, indicating how many \n"
                + "adjacent squares contain mines; if no mines are adjacent, the square becomes blank, and all \n"
                + "adjacent squares will be recursively revealed. The player uses this information to deduce the \n"
                + "contents of other squares, and may either safely reveal each square or mark the square as \n"
                + "containing a mine."));
        about.addActionListener(e -> JOptionPane.showMessageDialog(null, "Version 2.0 of Minesweeper.\nCreated by Noah Morton, 2015-17.\nThis "
                + "software is licenced using the MIT licence. All rights reserved."));

        //File menu - NewGame
        easyMap.addActionListener(e -> {
            System.out.println("Creating a new easy map...");
            Logger.logUserMessage("User chose to create a new easy map.");

            int numCols = 10, numRows = 10, numMines = 2;
            setSize((numCols * 16) + 10, (numRows * 17 + p.GUIEXTRAHEIGHT) + 45);
            p.recreate(numCols, numRows, numMines, p.getGame().EASY);
        });
        mediumMap.addActionListener(e -> {
            System.out.println("Creating a new medium map...");
            Logger.logUserMessage("User chose to create a new medium map.");
            p.getGame().setDifficulty(p.getGame().MEDIUM);

            int numCols = 15, numRows = 15, numMines = 30;
            setSize((numCols * 16) + 10, (numRows * 17 + p.GUIEXTRAHEIGHT) + 45);
            p.recreate(numCols, numRows, numMines, p.getGame().MEDIUM);
        });
        hardMap.addActionListener(e -> {
            System.out.println("Creating a new hard map...");
            Logger.logUserMessage("User chose to create a new hard map.");
            p.getGame().setDifficulty(p.getGame().HARD);

            int numCols = 20, numRows = 20, numMines = 60;
            setSize((numCols * 16) + 10, (numRows * 17 + p.GUIEXTRAHEIGHT) + 45);
            p.recreate(numCols, numRows, numMines, p.getGame().HARD);
        });
        // view scores
        highScores.addActionListener(e -> {
            String easyScores = "";
            for (int i = 0; i < p.easyArrayList.size(); i++) {
                try {
                    easyScores += p.easyArrayList.get(i).toString() + "\n";
                } catch (Exception ignored) {
                } //no scores in file

            }
            String mediumScores = "";
            for (int i = 0; i < p.easyArrayList.size(); i++) {
                try {
                    mediumScores += p.mediumArrayList.get(i).toString() + "\n";
                } catch (Exception ignored) {
                } //no scores in the file
            }
            String hardScores = "";
            for (int i = 0; i < p.easyArrayList.size(); i++) {
                try {
                    hardScores += p.hardArrayList.get(i).toString() + "\n";
                } catch (Exception ignored) {
                } //no scores in file
            }
            Logger.messageWindow("Easy:\n" + easyScores + "\nMedium:\n" + mediumScores + "\nHard:\n" + hardScores);
        });

        //Frame handling -------------------------------
        // Sets the close button to exit the program
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // makes the window not able to be re-sized
        setResizable(false);
        // creates the window
        pack();
        // creates the panel
        p = new MS_Panel(rows, cols, mines);
        // gets the frames insets
        Insets frameInsets = getInsets();
        // calculates panel size
        int frameWidth = p.getWidth()
                + (frameInsets.left + frameInsets.right);
        int frameHeight = p.getHeight()
                + (frameInsets.top + frameInsets.bottom);
        // sets the frame's size
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        // turns off the layout options
        setLayout(null);
        // adds the panel to the frame
        add(p);
        // adjusts the window to meet its new preferred size
        pack();
        // shows the frame
        setVisible(true);

        Logger.logOtherMessage("Window", "Window Created.");

    }

    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            //paint(this.getGraphics());
            repaint();
            try {
                Thread.sleep(35);
            } catch (Exception e) {
                System.err.println("Error Sleeping.");
                Logger.logErrorMessage("Error Sleeping Thread.");
            }
        }
    }

    public void addNotify() {
        super.addNotify();
        Thread a = new Thread(this);
        requestFocus();
        a.start();
        Logger.logCodeMessage("Thread Created Successfully.");
    }

}
