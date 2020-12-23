/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import java.awt.BorderLayout;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.HiResCoord;
import javax.media.j3d.Locale;
import javax.media.j3d.VirtualUniverse;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

/**
 *
 * @author TTRS_And_BTJB
 */
public abstract class GLevels extends JPanel implements GEvent.GIEvent, GAbstract.GIAbstract{
    // Creation d'un univers et d'un objet Locale qui va contenir la scene
    VirtualUniverse universe = new VirtualUniverse();
    Locale locale = createLocale(universe);
    //
    private GEvent gameEvent = null;
    private GAbstract gameLoop = null;
    protected float activationRadius = 10000f;
    
    public GLevels(int width, int height){
        super();
        this.setLayout(new BorderLayout());
        this.setSize(width, height);
        
        gameEvent = GEvent.getInstance();
        gameEvent.addGIEvent(this);
        System.out.println("spoed.Engine.GLevels.<init>()");
        gameEvent.setBounds(new BoundingSphere(new Point3d(0,0,0), activationRadius));
        System.out.println("spoed.Engine.GLevels.<init>()11111");
        gameLoop = GAbstract.getInstance();
        gameLoop.addGAbstract(this);
        loadData();
    }
    
    protected void addCamera(GCamera gc){
        locale.addBranchGraph(gc.getParent());
    }
    protected Locale createLocale( VirtualUniverse u ){
        int[] xPos = { 0, 0, 0, 0, 0, 0, 0, 0 };
        int[] yPos = { 0, 0, 0, 0, 0, 0, 0, 0 };
        int[] zPos = { 0, 0, 0, 0, 0, 0, 0, 0 };
        HiResCoord hiResCoord = new HiResCoord( xPos, yPos, zPos );
        return new Locale( u, hiResCoord );
    }
    public void setScene(){
        BranchGroup scene = createLevels();
        scene.compile();
        locale.addBranchGraph(createBackground());
        locale.addBranchGraph(scene);
    }
    
    public void loop(){
    }
    public void input(){
        
    }
    public void update(){
    
    }
    public abstract BranchGroup createLevels();
    public abstract BranchGroup createBackground();
    public abstract  void loadData();
}
