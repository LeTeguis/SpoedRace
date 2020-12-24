/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.Bounds;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.media.j3d.WakeupOnCollisionMovement;
import javax.media.j3d.WakeupOr;

/**
 *
 * @author TTRS_And_BTJB
 */
public class GCollision extends Behavior{
    /** The separate criteria used to wake up this beahvior. */
    protected WakeupCriterion[] theCriteria;

    /** The OR of the separate criteria. */
    protected WakeupOr oredCriteria;

    /** The shape that is watched for collision. */
    protected Shape3D collidingShape;

    /**
     * @param theShape
     *            Shape3D that is to be watched for collisions.
     * @param theBounds
     *            Bounds that define the active region for this behaviour
     */
    public GCollision(Shape3D theShape, Bounds theBounds) {
        collidingShape = theShape;
        setSchedulingBounds(theBounds);
    }

    /**
     * This creates an entry, exit and movement collision criteria. These are
     * then OR'ed together, and the wake up condition set to the result.
     */
    public void initialize() {
        theCriteria = new WakeupCriterion[3];
        theCriteria[0] = new WakeupOnCollisionEntry(collidingShape);
        theCriteria[1] = new WakeupOnCollisionExit(collidingShape);
        theCriteria[2] = new WakeupOnCollisionMovement(collidingShape);
        oredCriteria = new WakeupOr(theCriteria);
        wakeupOn(oredCriteria);
    }

    /**
     * Where the work is done in this class. A message is printed out using the
     * userData of the object collided with. The wake up condition is then set
     * to the OR'ed criterion again.
     */
    public void processStimulus(Enumeration criteria) {
        WakeupCriterion theCriterion = (WakeupCriterion) criteria.nextElement();
        if (theCriterion instanceof WakeupOnCollisionEntry) {
            Node theLeaf = ((WakeupOnCollisionEntry) theCriterion).getTriggeringPath().getObject();
            System.out.println("Collided with " + theLeaf.getUserData());
        } else if (theCriterion instanceof WakeupOnCollisionExit) {
            Node theLeaf = ((WakeupOnCollisionExit) theCriterion).getTriggeringPath().getObject();
            System.out.println("Stopped colliding with  " + theLeaf.getUserData());
        } else {
            Node theLeaf = ((WakeupOnCollisionMovement) theCriterion).getTriggeringPath().getObject();
            System.out.println("Moved whilst colliding with "+ theLeaf.getUserData());
        }
        wakeupOn(oredCriteria);
    }
}
