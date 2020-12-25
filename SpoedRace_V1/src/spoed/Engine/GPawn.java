/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import java.util.LinkedList;
import javax.media.j3d.BranchGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

/**
 *
 * @author TTRS_And_BTJB
 * class de base pour la gestion d'objet grouper
 */
public abstract class GPawn implements GAbstract.GIAbstract, GITransformation {
    private GAbstract gameLoop;
    protected BranchGroup parent = null;
    //protected BranchGroup meshs = null;
    protected GTransform transform = null;
    protected LinkedList<GMesh> allMesh = new LinkedList<GMesh>();
    
    public GPawn() {
        gameLoop = GAbstract.getInstance();
        gameLoop.addGAbstract(this);
        
        //meshs = new BranchGroup();
        
        this.parent = new BranchGroup();
        this.parent.setCapability(BranchGroup.ALLOW_DETACH);
        
        this.transform = new GTransform();
        /*this.transform.getTransform().setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        this.transform.getTransform().setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        this.transform.getTransform().addChild(meshs);*/
        
        transform = new GTransform();
        this.parent.addChild(transform.getTransform());
    }
    public void addMesh(GMesh mesh){
        if(mesh == null){
            System.out.println("spoed.Engine.GPawn.addMesh()");
            return;
        }
        allMesh.add(mesh);
        transform.getTransform().addChild(allMesh.getLast().getParent());
    }
    public GMesh getMesh(int index){
        if(!allMesh.isEmpty() && index >= 0 && index < allMesh.size())
            return allMesh.get(index);
        return null;
    }
    public GMesh getMesh(String nom){ 
        if(!allMesh.isEmpty())
            for(int i = 0; i < allMesh.size(); i++){
                if(allMesh.get(i).isMeshName(nom))
                    return allMesh.get(i);
            }
        return null;
    }
    public BranchGroup getParent(){
        return parent;
    }
    public GTransform getTransform(){
        return transform;
    }
    @Override
    public Vector3d forwardVector() {
        return transform.forwardVector();
    }

    @Override
    public Vector3d backwardVector() {
        return transform.backwardVector();
    }

    @Override
    public Vector3d upVector() {
        return transform.upVector();
    }

    @Override
    public Vector3d downVector() {
        return transform.downVector();
    }

    @Override
    public Vector3d leftVector() {
        return transform.leftVector();
    }

    @Override
    public Vector3d rightVector() {
        return transform.rightVector();
    }

    @Override
    public void move(Vector3d u) {
        transform.move(u);
    }

    @Override
    public void setPosition(Point3d position) {
        transform.setPosition(position);
    }

    @Override
    public Point3d getPosition() {
        return transform.getPosition();
    }

    @Override
    public void setScale(Vector3d s) {
        transform.setScale(s);
    }

    @Override
    public void scale(Vector3d s) {
        transform.scale(s);
    }

    @Override
    public Vector3d getScale() {
        return transform.getScale();
    }

    @Override
    public void setLocalRotation(Vector3d angle) {
        transform.setLocalRotation(angle);
    }

    @Override
    public void localRotation(Vector3d angle) {
        transform.localRotation(angle);
    }

    @Override
    public void globalRotation(Point3d origine, Vector3d angle) {
        transform.globalRotation(origine, angle);
    }

    @Override
    public void globalRotation(Point3d origine, Vector2d angle) {
        transform.globalRotation(origine, angle);
    }

    @Override
    public void localRotationX(double Ox) {
        transform.localRotationX(Ox);
    }

    @Override
    public void localRotationY(double Oy) {
        transform.localRotationY(Oy);
    }

    @Override
    public void localRotationZ(double Oz) {
        transform.localRotationZ(Oz);
    }

    @Override
    public void globalRotationX(Point3d origine, double Ox) {
        transform.globalRotationX(origine, Ox);
    }

    @Override
    public void globalRotationY(Point3d origine, double Oy) {
        transform.globalRotationY(origine, Oy);
    }

    @Override
    public void globalRotationZ(Point3d origine, double Oz) {
        transform.globalRotationZ(origine, Oz);
    }

    @Override
    public Vector3d getLocalOrientation() {
        return transform.getLocalOrientation();
    }

    @Override
    public Vector3d getGlobalEulerOrientation(Point3d origine) {
        return transform.getGlobalEulerOrientation(origine);
    }

    @Override
    public Vector2d getGlobalOrientation(Point3d origine) {
        return transform.getGlobalOrientation(origine);
    }

    @Override
    public Point3d getGlobalOrientationOrigine(Vector3d orientation) {
        return transform.getGlobalOrientationOrigine(orientation);
    }

    @Override
    public Point3d getGlobalOrientationOrigine(Vector2d orientation) {
        return transform.getGlobalOrientationOrigine(orientation);
    }

    @Override
    public void setOrigine(Point3d origine) {
        transform.setOrigine(origine);
    }

    @Override
    public Point3d getOrigine() {
        return transform.getOrigine();
    }
}
