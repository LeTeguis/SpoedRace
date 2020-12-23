/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Enumeration;
import java.util.LinkedList;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Point3d;

/**
 *
 * @author TTRS_And_BTJB
 */
public class GEvent  extends Behavior {
    
    private final WakeupOnAWTEvent[] mouseEvents = new WakeupOnAWTEvent[7];
    private WakeupCondition wakeupCriterion;
    private static GEvent INSTANCE = new GEvent();
    private LinkedList<GIEvent> giEvent = new LinkedList<GIEvent>();
    
    private GEvent() {
        this.setSchedulingBounds(new BoundingSphere());
    }
    public void seBounds(BoundingSphere bound){
        System.out.println("spoed.Engine.GEvent.seBounds()");
        this.setSchedulingBounds(bound);
    }
    public void addGIEvent(GIEvent gie){
        giEvent.add(gie);
    }
    public static GEvent getInstance(){
        if(INSTANCE == null)
            INSTANCE = new GEvent();
        return INSTANCE;
    }
    @Override
    public void initialize() {
        mouseEvents[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
        mouseEvents[1] = new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
        mouseEvents[2] = new WakeupOnAWTEvent(MouseEvent.MOUSE_RELEASED);
        mouseEvents[3] = new WakeupOnAWTEvent(MouseEvent.MOUSE_CLICKED);
        mouseEvents[4] = new WakeupOnAWTEvent(MouseEvent.MOUSE_WHEEL);
        mouseEvents[5] = new WakeupOnAWTEvent(AWTEvent.KEY_EVENT_MASK);
        mouseEvents[6] = new WakeupOnAWTEvent(AWTEvent.MOUSE_EVENT_MASK);
        wakeupCriterion = new WakeupOr(mouseEvents);
        this.wakeupOn(wakeupCriterion);
    }
    @Override
    public void processStimulus(Enumeration criteria) {
        while (criteria.hasMoreElements()) {
            final WakeupOnAWTEvent wakeup = (WakeupOnAWTEvent) criteria.nextElement();
            final AWTEvent[] events = wakeup.getAWTEvent();
            for (final AWTEvent evt : events) {
                if (evt instanceof MouseEvent) doProcess((MouseEvent) evt);
                if (evt instanceof KeyEvent) doProcess((KeyEvent) evt);
            }
        }
        wakeupOn(wakeupCriterion);
    }
    private void doProcess(final KeyEvent e){
        final int id = e.getID();
        final int code = e.getKeyCode();
        if(!giEvent.isEmpty())
            for(int i = 0; i < giEvent.size(); i++){
                if(id == KeyEvent.KEY_PRESSED){
                    giEvent.get(i).keyPressed(code, e.isAltDown(), e.isControlDown(), e.isShiftDown());
                }
                if(id == KeyEvent.KEY_RELEASED){
                    giEvent.get(i).keyReleased(code, e.isAltDown(), e.isControlDown(), e.isShiftDown());
                }
                if(id == KeyEvent.KEY_TYPED){
                    giEvent.get(i).keyType(e.getKeyChar());
                }
            }
    }
    
    private void doProcess(final MouseEvent e){
        final int id = e.getID();
	final int mask = e.getModifiersEx();
        
        if(!giEvent.isEmpty())
            for(int i = 0; i < giEvent.size(); i++){
                if (id == MouseEvent.MOUSE_PRESSED) {
                    giEvent.get(i).mousePressed(mask, e.getButton(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(), e.getClickCount());
                }
                if (id == MouseEvent.MOUSE_RELEASED) {
                    giEvent.get(i).mouseReleased(mask, e.getButton(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(), e.getClickCount());
                }
                if (id == MouseEvent.MOUSE_WHEEL) {
                    giEvent.get(i).mouseWheel((MouseWheelEvent) e);
                }
                if (id == MouseEvent.MOUSE_DRAGGED) {
                    giEvent.get(i).mouseDragged(e);
                }
            }
    }
    
    public interface GIEvent{
        public void mousePressed(int mask, int boutton, int x, int y, int xScreen, int yScreen, int clickCount);
        public void mouseReleased(int mask, int boutton, int x, int y, int xScreen, int yScreen, int clickCount);
        public void mouseWheel(final MouseWheelEvent mwe);
        public void mouseDragged(final MouseEvent e);
        public void keyPressed(int code, boolean alt, boolean ctrl, boolean shift);
        public void keyReleased(int code, boolean alt, boolean ctrl, boolean shift);
        public void keyType(char c);
    }
}