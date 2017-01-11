package mswindow;

import mslogic.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * @author othscs120
 *         Created on: 11/4/2014 , Time is :  1:28 PM
 *         Part of Project: MineSweeper
 */

public class MS_Frame extends JFrame {

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


        //Frame handling -------------------------------
        // Sets the close button to exit the program
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // makes the window not able to be resized
        setResizable(false);
        // creates the window
        pack();
        // creates the panel
        MS_Panel p = new MS_Panel(rows, cols, mines);
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


}
