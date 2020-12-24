/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author TTRS_And_BTJB
 */
public class GLight implements GITransformation{
    protected BranchGroup parent = null;
    protected  GTransform transform;
    protected LightType type = LightType.DIRECTIONNEL;
    private Vector3f direction = new Vector3f(-1.0f, -1.0f, -1.0f);
    private Color3f couleur = new Color3f(1.0f, 1.0f, 1.0f);
    private Bounds zoneInfluance = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
    private Light lumier = null;
    
    public enum LightType{DIRECTIONNEL};

    public GLight() {
        this.parent = new BranchGroup();
        this.transform = new GTransform();
        this.parent.addChild(transform.getTransform());
        
        lumier = new DirectionalLight(couleur, direction);
        lumier.setInfluencingBounds(zoneInfluance);
        
        AmbientLight ambientLight = new AmbientLight(couleur);
        ambientLight.setInfluencingBounds(zoneInfluance);
        
        transform.getTransform().addChild(ambientLight);
        transform.getTransform().addChild(lumier);
    }
    
    public void setZoneInfluance(Bounds bound){
        zoneInfluance = bound;
        lumier.setInfluencingBounds(zoneInfluance);
    }
    public void setColor(Color3f col){
        couleur = col;
        lumier.setColor(col);
    }
    public void setDirection(Vector3f vec){
        direction = vec;
    }
    
    public BranchGroup getParent(){
        return this.parent;
    }
    
    public void setType(LightType t){
        type = t;
    }
    public LightType getType(){
        return type;
    }
    @Override
    public void setOrigine(Point3d origine) {
        transform.setOrigine(origine);
    }

    @Override
    public Point3d getOrigine() {
        return transform.getOrigine();
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
}
