/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import javax.media.j3d.Canvas3D;

/**
 *
 * @author TTRS_And_BTJB
 */
public class GCanvas extends Canvas3D{
    
    public GCanvas(GraphicsConfiguration config) {
        super(config);
    }
    public void paint(Graphics g) {
        super.paint(g);
        Toolkit.getDefaultToolkit().sync();
    }
}
