/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Games;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import spoed.Engine.*;

/**
 *
 * @author TTRS_And_BTJB
 */
public class SpoedRace {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    
    private static void initAndShowGUI() {
        
        JFrame frame = new JFrame("SPOED 1.0");
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        GLevel_Test level = new GLevel_Test(WIDTH, HEIGHT);
        level.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        level.setBounds(0, 0, WIDTH, HEIGHT);

        JLayeredPane paneLayer = frame.getLayeredPane();
        paneLayer.add(level, 0);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    }
    
}
