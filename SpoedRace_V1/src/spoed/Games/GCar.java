/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Games;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import spoed.Engine.GActor;
import spoed.Engine.GLoad3DAssetsManager;
import spoed.Engine.GTransform;

/**
 *
 * @author TTRS_And_BTJB
 */
public class GCar extends GActor{
    private String Model = null;
    private double vitesseTranslation = 10.0;
    private Vector3d vitesseRotation = new Vector3d(0, 0, 0);
    private Deplacement direction = Deplacement.AUCUN;
    private Orientation orientation = Orientation.AUCUN;
    
    public GCar() {
        super();
        /*this.addMesh(GLoad3DAssetsManager.getMesh("voiture"));
        allMesh.getLast().setPosition(new Point3d(0.004292, 0.849326, 0.114546));
        this.addMesh(GLoad3DAssetsManager.getMesh("roueavantg"));
        allMesh.getLast().setPosition(new Point3d(0.498462, 0.331681, 1.33142));
        this.addMesh(GLoad3DAssetsManager.getMesh("roueavantd"));
        allMesh.getLast().setPosition(new Point3d(-0.498462, 0.331681, 1.33142));
        this.addMesh(GLoad3DAssetsManager.getMesh("rouearrierg"));
        allMesh.getLast().setPosition(new Point3d(0.498462, 0.331681, -0.93888));
        this.addMesh(GLoad3DAssetsManager.getMesh("rouearrierd"));
        allMesh.getLast().setPosition(new Point3d(-0.498462, 0.331681, -0.93888));*/
        this.addMesh(GLoad3DAssetsManager.getMesh("bugatti_corps"));
        //allMesh.getLast().setPosition(new Point3d(0.004292, 0.849326, 0.114546));
        this.addMesh(GLoad3DAssetsManager.getMesh("bugatti_rfl"));
        //allMesh.getLast().setPosition(new Point3d(0.498462, 0.331681, 1.33142));
        this.addMesh(GLoad3DAssetsManager.getMesh("bugatti_rfr"));
        //allMesh.getLast().setPosition(new Point3d(-0.498462, 0.331681, 1.33142));
        this.addMesh(GLoad3DAssetsManager.getMesh("bugatti_rbl"));
        //allMesh.getLast().setPosition(new Point3d(0.498462, 0.331681, -0.93888));
        this.addMesh(GLoad3DAssetsManager.getMesh("bugatti_rbr"));
        //allMesh.getLast().setPosition(new Point3d(-0.498462, 0.331681, -0.93888));
    }
    
    @Override
    public void mousePressed(int mask, int boutton, int x, int y, int xScreen, int yScreen, int clickCount) {
    }
    @Override
    public void mouseReleased(int mask, int boutton, int x, int y, int xScreen, int yScreen, int clickCount) {
    }
    @Override
    public void mouseWheel(MouseWheelEvent mwe) {
    }
    @Override
    public void mouseDragged(MouseEvent e) {
    }
    @Override
    public void keyPressed(int code, boolean alt, boolean ctrl, boolean shift) {
        if(code == KeyEvent.VK_UP){
            vitesseTranslation = (vitesseTranslation < 0)?-vitesseTranslation:vitesseTranslation;
            direction = Deplacement.AVANCER;
        }
        if(code == KeyEvent.VK_DOWN){
            vitesseTranslation = (vitesseTranslation < 0)?vitesseTranslation:-vitesseTranslation;;
            direction = Deplacement.RECULER;
        }
        if(code == KeyEvent.VK_LEFT){
            vitesseRotation = new Vector3d(0, 1, 0);
            orientation = Orientation.ROTATION_GAUCHE;
        }
        if(code == KeyEvent.VK_RIGHT){
            vitesseRotation = new Vector3d(0, -1, 0);
            orientation = Orientation.ROTATION_DROITE;
        }
    }
    @Override
    public void keyReleased(int code, boolean alt, boolean ctrl, boolean shift) {
        if(code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN){
            //vitesseTranslation = 0;
            direction = Deplacement.AUCUN;
        }
        if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT){
            vitesseRotation = new Vector3d(0, 0, 0);
            orientation = Orientation.AUCUN;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_Z){
            vitesseRotation = new Vector3d(0, 0, 0);
        }
    }
    @Override
    public void keyType(char c) {
    }
    @Override
    public void update(long times, long deltaTime) {
        if(direction != Deplacement.AUCUN){
            if(orientation != Orientation.AUCUN)
                this.localRotation(GTransform.multily(vitesseRotation, 0.25));
            if(direction == Deplacement.AVANCER){
                allMesh.get(1).localRotationX(5);
                allMesh.get(2).localRotationX(5);
                allMesh.get(3).localRotationX(5);
                allMesh.get(4).localRotationX(5);
            }
            else{
                allMesh.get(1).localRotationX(-5);
                allMesh.get(2).localRotationX(-5);
                allMesh.get(3).localRotationX(-5);
                allMesh.get(4).localRotationX(-5);
            }
            System.out.println(vitesseTranslation*(deltaTime/1000.));
            this.move(GTransform.multily(this.transform.forwardVector(),vitesseTranslation*(deltaTime/1000.)));
        }
    }
    public enum Deplacement{
        AVANCER, RECULER, AUCUN
    }
    public enum Orientation{
        ROTATION_GAUCHE, ROTATION_DROITE, AUCUN
    }
}
