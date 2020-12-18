/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author TTRS_And_BTJB
 */
public abstract class GLevels extends JPanel{
    public GLevels(int width, int height){
        this.setLayout(new BorderLayout());
        this.setSize(width, height);
    }
}
