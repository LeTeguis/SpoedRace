/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import javax.vecmath.Point3d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

/**
 *
 * @author TTRS_And_BTJB
 * GITransformer est une interface ququi va etre heriter par toutes les class qui doivent faire une transformation quelquonque 
 * telque rotation, deplacement ou zoom/ dezoom on peut liste entre autre
 * GTranformer qui est notre class de base pour les tranformation
 * GCamera qui peut etre deplacer agrandi ou roter
 * GActor qui est la representation d'un objet physique etc...
 */
public interface GITransformation{
    public void setOrigine(Point3d origine);
    public Point3d getOrigine();
    public Vector3d forwardVector();
    public Vector3d backwardVector();
    public Vector3d upVector();
    public Vector3d downVector();
    public Vector3d leftVector();
    public Vector3d rightVector();
    public void move(Vector3d u);
    public void setPosition(Point3d position);
    public Point3d getPosition();
    public void setScale(Vector3d s);
    public void scale(Vector3d s);
    public Vector3d getScale();
    public void setLocalRotation(Vector3d angle);
    public void localRotation(Vector3d angle);
    public void globalRotation(Point3d origine, Vector3d angle);
    public void globalRotation(Point3d origine, Vector2d angle);
    public void localRotationX(double Ox);
    public void localRotationY(double Oy);
    public void localRotationZ(double Oz);
    public void globalRotationX(Point3d origine, double Ox);
    public void globalRotationY(Point3d origine, double Oy);
    public void globalRotationZ(Point3d origine, double Oz);
    public Vector3d getLocalOrientation();
    public Vector3d getGlobalEulerOrientation(Point3d origine);
    public Vector2d getGlobalOrientation(Point3d origine);
    public Point3d getGlobalOrientationOrigine(Vector3d orientation);
    public Point3d getGlobalOrientationOrigine(Vector2d orientation);
}
