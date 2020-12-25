/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Games;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import spoed.Engine.GActor;
import spoed.Engine.GCamera;
import spoed.Engine.GLoad3DAssetsManager;
import spoed.Engine.GTransform;

/**
 *
 * @author TTRS_And_BTJB
 */
public class GCar extends GActor{

    public double getVitesseTranslation() {
        return vitesseTranslation;
    }

    public void setVitesseTranslation(double vitesseTranslation) {
        this.vitesseTranslation = vitesseTranslation;
    }
    private String Model = null;
    private double vitesseTranslation = 50.0;
    private double vitesseRotationY = 50.0;
    private Deplacement direction = Deplacement.AUCUN;
    private Orientation orientation = Orientation.AUCUN;
    
    private GCamera camera = null;
    private double deviation = 0;
    private double distanceCamera = 10.0;
    private double max_angle_deviation = 30.0;
    
    public GCar() {
        super();
    }
    public void setCamera(GCamera c){
        camera = c;
    }
    public void chargerVoiture(String marque, String postPath){
        if(!allMesh.isEmpty()){
            GLoad3DAssetsManager.removeMesh(allMesh.get(0));
            GLoad3DAssetsManager.removeMesh(allMesh.get(1));
            GLoad3DAssetsManager.removeMesh(allMesh.get(2));
            GLoad3DAssetsManager.removeMesh(allMesh.get(3));
            GLoad3DAssetsManager.removeMesh(allMesh.get(4));
            allMesh.clear();
        }
        this.addMesh(GLoad3DAssetsManager.getMesh(marque));
        this.addMesh(GLoad3DAssetsManager.getMesh(marque+"_rfl"));
        this.addMesh(GLoad3DAssetsManager.getMesh(marque+"_rfr"));
        this.addMesh(GLoad3DAssetsManager.getMesh(marque+"_rbl"));
        this.addMesh(GLoad3DAssetsManager.getMesh(marque+"_rbr"));
        
        loadPosition(postPath+marque+".pos");
    }
    private void loadPosition(String file){
        try {
            String ligne ;
            BufferedReader fichier = new BufferedReader(new FileReader(file));
            int k = 0;
            while((ligne = fichier.readLine()) != null){
                String[] value = ligne.split(" ");
                if(value.length == 3){
                    if(Double.isFinite(Double.parseDouble(value[0]))&&Double.isFinite(Double.parseDouble(value[1]))
                            &&Double.isFinite(Double.parseDouble(value[2]))){
                        if(k<=4){
                            this.allMesh.get(k).setPosition(new Point3d(Double.parseDouble(value[0]),Double.parseDouble(value[1]),Double.parseDouble(value[2])));
                            k++;
                        }
                    }
                }
            }
            fichier.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GCar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GCar.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            //setVitesseTranslation((getVitesseTranslation() < 0) ? -getVitesseTranslation() : getVitesseTranslation());
            setDirection(Deplacement.AVANCER);
        }
        if(code == KeyEvent.VK_DOWN){
            //setVitesseTranslation((getVitesseTranslation() < 0) ? getVitesseTranslation() : -getVitesseTranslation());
            setDirection(Deplacement.RECULER);
        }
        if(code == KeyEvent.VK_LEFT){
            //setVitesseRotationY((getVitesseRotationY()< 0) ? -getVitesseTranslation() : getVitesseTranslation());
            setOrientation(Orientation.ROTATION_GAUCHE);
        }
        if(code == KeyEvent.VK_RIGHT){
            //setVitesseRotationY((getVitesseRotationY()< 0) ? getVitesseTranslation() : -getVitesseTranslation());
            setOrientation(Orientation.ROTATION_DROITE);
        }
    }
    @Override
    public void keyReleased(int code, boolean alt, boolean ctrl, boolean shift) {
        if(code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN){
            setDirection(Deplacement.AUCUN);
        }
        if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT){
            setOrientation(Orientation.AUCUN);
        }
    }
    @Override
    public void keyType(char c) {
    }
    @Override
    public void update(long times, long deltaTime) {
        if(getDirection() != Deplacement.AUCUN && !allMesh.isEmpty()){
            if(getOrientation() != Orientation.AUCUN){
                if(getOrientation() == Orientation.ROTATION_DROITE){
                    this.localRotation(new Vector3d(0,-vitesseRotationY*((double)deltaTime/1000.),0));
                    deviation++;
                    if(deviation >= max_angle_deviation)
                        deviation = max_angle_deviation;
                }
                if(getOrientation() == Orientation.ROTATION_GAUCHE){
                    this.localRotation(new Vector3d(0,vitesseRotationY*((double)deltaTime/1000.),0));
                    deviation--;
                    if(deviation <= -max_angle_deviation)
                        deviation = -max_angle_deviation;
                }
            }
            if(getDirection() == Deplacement.AVANCER){
                setRotationRoueX(vitesseTranslation*60*((double)deltaTime/1000.));
            }
            else{
                setRotationRoueX(-vitesseTranslation*60*((double)deltaTime/1000.));
            }
            Vector3d u = new Vector3d();
            if(getDirection() == Deplacement.AVANCER){
                u = GTransform.multily(this.transform.forwardVector(),vitesseTranslation*((double)deltaTime/1000.));
                this.move(u);
                
            }else if(getDirection() == Deplacement.RECULER){
                u = GTransform.multily(this.transform.backwardVector(),vitesseTranslation*((double)deltaTime/1000.));
                this.move(u);
            }
            
            
        }
        if(getOrientation() == Orientation.AUCUN){
            if(deviation < 0)
                deviation+=0.5;
            else if(deviation > 0)
                deviation-=0.5;
        }
        
        Vector3d v = this.backwardVector();
            
        double x = v.x*distanceCamera + this.getPosition().x;
        double z = v.z*distanceCamera + this.getPosition().z;

        double x1 = this.getPosition().x - x;
        double z1 = this.getPosition().z - z;

        //double converte = Math.PI/180.;

        x = this.getPosition().x - (x1*Math.cos(GTransform.toRadian(deviation)) + z1*Math.sin(GTransform.toRadian(deviation)));
        z = this.getPosition().z - (-x1*Math.sin(GTransform.toRadian(deviation)) + z1*Math.cos(GTransform.toRadian(deviation)));

        //System.out.println((x1*Math.cos(deviation) + z1*Math.sin(deviation))+" : "+(-x1*Math.sin(deviation) + z1*Math.cos(deviation)));
        camera.setPosition(new Point3d(x, this.getPosition().y + 3, z));
        camera.setLocalRotation(new Vector3d(-15,this.getLocalOrientation().y + 180 + deviation,0));
        //camera.lookAt(this.getPosition());
    }
    private void setRotationRoueX(double vitesse){
        allMesh.get(1).localRotationX(vitesse);
        allMesh.get(2).localRotationX(vitesse);
        allMesh.get(3).localRotationX(vitesse);
        allMesh.get(4).localRotationX(vitesse);
    }
    public enum Deplacement{
        AVANCER, RECULER, AUCUN
    }
    public enum Orientation{
        ROTATION_GAUCHE, ROTATION_DROITE, AUCUN
    }

    public double getVitesseRotationY() {
        return vitesseRotationY;
    }

    public void setVitesseRotationY(double vitesseRotationY) {
        this.vitesseRotationY = vitesseRotationY;
    }

    public Deplacement getDirection() {
        return direction;
    }

    public void setDirection(Deplacement direction) {
        this.direction = direction;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
