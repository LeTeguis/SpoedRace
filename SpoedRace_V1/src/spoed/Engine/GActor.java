/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import java.util.LinkedList;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

/**
 *
 * @author TTRS_And_BTJB
 * class de base pour la gestion d'objet grouper qui on la possibiliter davoir un comportement utilisateur
 */
public abstract class GActor extends GPawn implements GEvent.GIEvent, GAbstract.GIAbstract, GITransformation{
    private GEvent gevent;
    public  static LinkedList<GActor> allActor = new LinkedList<GActor>();

    public GActor() {
        super();
        allActor.add(this);
        gevent = GEvent.getInstance();
        gevent.addGIEvent(this);
    }
}
