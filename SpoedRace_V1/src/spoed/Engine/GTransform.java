/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import java.util.LinkedList;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

/**
 *
 * @author TTRS_And_BTJB
 */
public class GTransform implements GITransformation{
    private TransformGroup transformGroup = new TransformGroup();
    private Point3d origine = new Point3d(0, 0, 0);
    private Point3d position = new Point3d(0, 0, 0);
    private Vector3d scale = new Vector3d(1f, 1f, 1f);
    private Vector3d orientation = new Vector3d(0, 0, 0);
    private Vector3d forwardVector = new Vector3d(0, 0, 1);
    private Vector3d upVector = new Vector3d(0, 1, 0);
    private Vector3d leftVector = new Vector3d(1, 0, 0);
    
    public GTransform() {
        this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        this.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    }
    public TransformGroup getTransform(){
        return transformGroup;
    }
    @Override
    public void setOrigine(Point3d o){
        origine = o;
    }
    @Override
    public Point3d getOrigine(){
        return origine;
    }
    @Override
    public void move(Vector3d u) {
        position.x += u.x;
        position.y += u.y;
        position.z += u.z;
        
        Transform3D t3d = new Transform3D();
        this.transformGroup.getTransform(t3d);
        t3d.setTranslation(new Vector3d(this.position.x, this.position.y, this.position.z));
        this.transformGroup.setTransform(t3d);
    }
    @Override
    public void setPosition(Point3d position) {
        this.position = position;
        Transform3D t3d = new Transform3D();
        this.transformGroup.getTransform(t3d);
        t3d.set(new Vector3d(this.position.x, this.position.y, this.position.z));
        this.transformGroup.setTransform(t3d);
    }

    @Override
    public Point3d getPosition() {
        return position;
    }

    @Override
    public void setScale(Vector3d s) {
        this.scale.x = 0;
        this.scale.y = 0;
        this.scale.z = 0;
        this.scale(s);
    }

    @Override
    public void scale(Vector3d s) {
        this.scale.x += s.x;
        this.scale.y += s.y;
        this.scale.z += s.z;
        
        Vector3d u = new Vector3d(position.x - origine.x, position.y - origine.y, position.z - origine.z);
        Transform3D tranlation1 = new Transform3D();
        tranlation1.setTranslation(u);
        // on gere apres la rotation sur tout les axes
        Transform3D scaling = new Transform3D();
        scaling.setScale(scale);
        scaling.mul(tranlation1);
        
        Vector3d translation2 = new Vector3d();
        scaling.get(translation2);
        
        translation2.x += origine.x;
        translation2.y += origine.y;
        translation2.z += origine.z;
        
        Transform3D t3d = new Transform3D();
        this.transformGroup.getTransform(t3d);
        t3d.setScale(this.scale);
        t3d.setTranslation(translation2);
        
        this.transformGroup.setTransform(t3d);
        position = getPositionTranformation();
    }
    private Point3d getPositionTranformation(){
        Transform3D t3d = new Transform3D();
        transformGroup.getTransform(t3d);
        Vector3d v1 = new Vector3d();
        t3d.get(v1);
        return new Point3d(v1.x, v1.y, v1.z);
    }
    @Override
    public Vector3d getScale() {
        return scale;
    }

    @Override
    public void setLocalRotation(Vector3d angle) {
        this.orientation = new Vector3d(0, 0, 0);
        localRotation(angle);
    }

    @Override
    public void localRotation(Vector3d angle) {
        orientation.x += angle.x;
        orientation.y += angle.y;
        orientation.z += angle.z;
        
        orientation.x = (orientation.x%360.) /*- 180.*/;
        orientation.y = (orientation.y%360.) /*- 180.*/;
        orientation.z = (orientation.z%360.) /*- 180.*/;
        
        Transform3D rotation = new Transform3D();
        rotation.setEuler(new Vector3d((orientation.x*Math.PI)/180.f, (orientation.y*Math.PI)/180.f, (orientation.z*Math.PI)/180.f));
        Quat4d rot = new Quat4d();
        rotation.get(rot);
        
        Transform3D t3d = new Transform3D();
        this.transformGroup.getTransform(t3d);
        t3d.setRotation(rot);
        
        this.transformGroup.setTransform(t3d);
        position = getPositionTranformation();
        
        localOrientationVector();
    }
    private void localOrientationVector(){
        Vector3d f = new Vector3d(0, 0, 1);
        Vector3d u = new Vector3d(0, 1, 0);
        Vector3d l = new Vector3d(1, 0, 0);
        
        Transform3D rotation1 = new Transform3D();
        rotation1.setEuler(new Vector3d((orientation.x*Math.PI)/180.f, (orientation.y*Math.PI)/180.f, (orientation.z*Math.PI)/180.f));
        Transform3D rotation2 = new Transform3D(rotation1);
        Transform3D rotation3 = new Transform3D(rotation1);
        
        Transform3D tranlation1 = new Transform3D();
        tranlation1.setTranslation(f);
        Transform3D tranlation2 = new Transform3D();
        tranlation1.setTranslation(f);
        Transform3D tranlation3 = new Transform3D();
        tranlation1.setTranslation(f);
        
        rotation1.mul(tranlation1);
        rotation1.get(f);
        
        rotation2.mul(tranlation2);
        rotation2.get(u);
        
        rotation3.mul(tranlation3);
        rotation3.get(l);
        
        forwardVector = f;
        upVector = u;
        leftVector = l;
    }
    @Override
    public void globalRotation(Point3d origine, Vector3d angle) {
        // recuperer la position de l'objet du nouveau repere et le positionner dans le repere locale
        Vector3d u = new Vector3d(position.x - origine.x, position.y - origine.y, position.z - origine.z);
        Transform3D tranlation1 = new Transform3D();
        tranlation1.setTranslation(u);
        // on gere apres la rotation sur tout les axes
        
        Transform3D rotation = new Transform3D();
        rotation.setEuler(new Vector3d((angle.x*Math.PI)/180.f, (angle.y*Math.PI)/180.f, (angle.z*Math.PI)/180.f));
        rotation.mul(tranlation1);
        
        Quat4d rot = new Quat4d();
        rotation.get(rot);
        Vector3d translation2 = new Vector3d();
        rotation.get(translation2);
        
        translation2.x += origine.x;
        translation2.y += origine.y;
        translation2.z += origine.z;
        
        Transform3D t3d = new Transform3D();
        this.transformGroup.getTransform(t3d);
        t3d.setTranslation(translation2);
        
        this.transformGroup.setTransform(t3d);
        position = getPositionTranformation();
    }

    @Override
    public void globalRotation(Point3d origine, Vector2d angle) {
        globalRotation(origine, new Vector3d(angle.x, angle.y, 0));
    }

    @Override
    public void localRotationX(double Ox) {
        localRotation(new Vector3d(Ox, 0, 0));
    }

    @Override
    public void localRotationY(double Oy) {
        localRotation(new Vector3d(0, Oy, 0));
    }

    @Override
    public void localRotationZ(double Oz) {
        localRotation(new Vector3d(0, 0, Oz));
    }

    @Override
    public void globalRotationX(Point3d origine, double Ox) {
        globalRotation(origine, new Vector3d(Ox, 0, 0));
    }

    @Override
    public void globalRotationY(Point3d origine, double Oy) {
        globalRotation(origine, new Vector3d(0, Oy, 0));
    }

    @Override
    public void globalRotationZ(Point3d origine, double Oz) {
        globalRotation(origine, new Vector3d(0, 0, Oz));
    }

    @Override
    public Vector3d getLocalOrientation() {
        return orientation;
    }

    @Override
    public Vector3d getGlobalEulerOrientation(Point3d origine) {
        return null;
    }

    @Override
    public Vector2d getGlobalOrientation(Point3d origine) {
        return null;
    }

    @Override
    public Point3d getGlobalOrientationOrigine(Vector3d orientation) {
        return null;
    }

    @Override
    public Point3d getGlobalOrientationOrigine(Vector2d orientation) {
        return null;
    }
    public static Vector3d multily(Vector3d v, double a){
        return new Vector3d(v.x * a, v.y * a, v.z * a);
    }
    public static Vector3d substract(Vector3d v, Vector3d u){
        return new Vector3d(v.x - v.x, v.y - v.y, v.z - v.z);
    }
    public static Vector3d vecteur(Point3d A, Point3d B){
        return new Vector3d(B.x - A.x, B.y - A.y, B.z - A.z);
    }
    @Override
    public Vector3d forwardVector() {
        return forwardVector;
    }

    @Override
    public Vector3d backwardVector() {
        return multily(forwardVector, -1);
    }

    @Override
    public Vector3d upVector() {
        return upVector;
    }

    @Override
    public Vector3d downVector() {
        return multily(upVector, -1);
    }

    @Override
    public Vector3d leftVector() {
        return leftVector;
    }

    @Override
    public Vector3d rightVector() {
        return multily(leftVector, -1);
    }
    public static Vector3d angleRotation(Vector3d u){
        if(u.x == 0 && u.y == 0 && u.z == 0)
            return u;
        
        if(u.x == 0 && u.y == 0)
            return new Vector3d(0,(u.z > 0)? Math.PI : 0,  0);
        if(u.x == 0 && u.z == 0)
            return new Vector3d((u.y < 0)? -Math.PI/2. : Math.PI/2., 0, 0);
        if(u.y == 0 && u.z == 0)
            return new Vector3d(0 , (u.x < 0)? Math.PI/2. : -Math.PI/2. , 0);
        
        if(u.x == 0){
            if((u.y > 0 && u.z > 0) || (u.y < 0 && u.z > 0))
                return new Vector3d(Math.atan(u.y / u.z), Math.PI , 0);
            return new Vector3d(-Math.atan(u.y / u.z), 0, 0);
        }
        if(u.y == 0){
            if((u.x < 0 && u.z > 0)||(u.x > 0 && u.z > 0))
                return new Vector3d(0, Math.atan(u.z / u.x) + Math.PI, 0);
            return new Vector3d(0, Math.atan(u.x / u.z), 0);
        }
        if(u.z == 0)
            return new Vector3d(0, (u.x < 0)? Math.PI/2. : -Math.PI/2. , Math.atan(u.y / u.x));
        
        Vector3d angle = new Vector3d(0, 0, 0);
        double d = Math.sqrt((u.x * u.x) + (u.z * u.z));
        double Ox = Math.atan(u.y / u.z);
        double Oy = Math.atan(u.x / u.z);
        
        if(u.z > 0){
            angle.x = Ox;
            angle.y = Oy + Math.PI;
        }else{
            angle.x = Ox;
            angle.y = Oy;
        }
        return angle;
    }
}
