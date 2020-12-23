/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Games;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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
    private double vitesseTranslation = 0.2;
    private Vector3d vitesseRotation = new Vector3d(0, 0, 0);
    private Deplacement direction = Deplacement.AUCUN;
    private Orientation orientation = Orientation.AUCUN;
    
    public GCar() {
        super();
        this.addMesh(GLoad3DAssetsManager.getMesh("voiture"));
        this.addMesh(GLoad3DAssetsManager.getMesh("roueavantg"));
        this.addMesh(GLoad3DAssetsManager.getMesh("roueavantd"));
        this.addMesh(GLoad3DAssetsManager.getMesh("rouearrierg"));
        this.addMesh(GLoad3DAssetsManager.getMesh("rouearrierd"));
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
            vitesseTranslation = 0.2;
            direction = Deplacement.AVANCER;
        }
        if(code == KeyEvent.VK_DOWN){
            vitesseTranslation = -0.2;
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
        if(orientation != Orientation.AUCUN)
            this.localRotation(GTransform.multily(vitesseRotation, 0.25));
        if(direction != Deplacement.AUCUN){
            /*if(direction == Deplacement.AVANCER)
                allMesh.get(1).localRotationX(1);
            else
                allMesh.get(1).localRotationX(-1);*/
            this.move(GTransform.multily(GTransform.multily(this.transform.forwardVector(),vitesseTranslation), 0.25));
        }
    }
    public enum Deplacement{
        AVANCER, RECULER, AUCUN
    }
    public enum Orientation{
        ROTATION_GAUCHE, ROTATION_DROITE, AUCUN
    }
}
