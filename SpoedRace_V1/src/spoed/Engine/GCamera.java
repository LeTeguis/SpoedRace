/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.Point3d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

/**
 *
 * @author TTRS_And_BTJB
 */
public class GCamera implements GEvent.GIEvent, GAbstract.GIAbstract, GITransformation{
    // Les objets de type PhysicalBody et PhysicalEnvironment doivent etre
    // communs a toutes les instances de la classe Vue. Ce sont les memes objets
    // que l'on utilisera pour toutes les vues
    protected static final PhysicalBody physBody = new PhysicalBody();
    protected static final PhysicalEnvironment physEnv = new PhysicalEnvironment();
    
    // Objets specifiques a chaque vue
    protected BranchGroup parent = null;
    protected  GTransform transform;
    protected ViewPlatform viewPlatform = null;
    protected float activationRadius = 10000f;
    protected View view = null;
    protected GCanvas canvas = null;
    //
    private GEvent gameEvent;
    private GAbstract gameLoop;
    //
    private GMesh lookAtMe = null;
    // 
    private Vector3d vitesse = new Vector3d(0, 0, 0);
    private Vector3d vitesseRotation = new Vector3d(0, 0, 0);//*/
    
    double vitesseTranslation = 0;
    
    public GCamera(){
        // si je n'appel pas de maniere explicite la methode init depuis la
        // class ou je crée la camera il ya un problème de null
        // pointeur exception
        init();
        gameEvent = GEvent.getInstance();
        gameEvent.addGIEvent(this);
        gameLoop = GAbstract.getInstance();
        gameLoop.addGAbstract(this);
    }
    public void lookAt(GMesh mesh){
        lookAtMe = mesh;
        lookAt();
    }
    public void lookAt(){
        Point3d cameraPosition = this.getPosition();
        Point3d meshPosition = lookAtMe.getPosition();
        
        Vector3d Ucm = GTransform.vecteur(cameraPosition, meshPosition);
        double constatnteRadial = 180/Math.PI;
        double norme = Math.sqrt((Ucm.x*Ucm.x) + (Ucm.y*Ucm.y) + (Ucm.z*Ucm.z));
        if(norme != 0){
            Ucm.x /= norme;
            Ucm.y /= norme;
            Ucm.z /= norme;
        }
        Vector3d angle = GTransform.angleRotation(GTransform.multily(Ucm, 1));
        angle.x = (angle.x*constatnteRadial)*(1);
        angle.y = (angle.y*constatnteRadial)*(1);
        angle.z = (angle.z*constatnteRadial)*(1);
        
        this.setLocalRotation(angle);
    }
    public void init(){
        initViewer();
        // initialisation de la view
        iniView(canvas, viewPlatform);
        //Création du groupe de transformation qui permet de modifier la position de la caméra
        initTransformGroup(viewPlatform);
        //Création de l'objet parent de type BranchGroup qui est père de tous les nodes de la classe Vue
        initParent(this.transform.getTransform());
    }
    public GTransform getViewPlatformTransformGroup() {
        return this.transform;
    }
    public void initViewer(){
        //Création de la configuration graphique que l'on va utiliser pour construire le canvas 3D
        GraphicsConfigTemplate3D gconfigTemplate = new GraphicsConfigTemplate3D();
        GraphicsConfiguration gconfig = GraphicsEnvironment.getLocalGraphicsEnvironment().
                                        getDefaultScreenDevice().getBestConfiguration(gconfigTemplate);
        
        //Création du canvas 3D, de l'objet viewPlatform et de l'objet view associé au canvas et à viewPlatform
        this.canvas = new GCanvas(gconfig);
        this.viewPlatform = new ViewPlatform();
        this.viewPlatform.setActivationRadius(activationRadius);
        this.view = new View();
    }
    public void initTransformGroup(ViewPlatform vp){
        this.transform = new GTransform();
        //this.transform.getTransform().setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        //this.transform.getTransform().setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        this.transform.getTransform().addChild(vp);
    }
    public void iniView(Canvas3D canvas, ViewPlatform viewPlatform){
        this.view.setBackClipDistance(activationRadius);
        this.view.addCanvas3D(canvas);
        this.view.attachViewPlatform(viewPlatform);
        this.view.setPhysicalBody(physBody);
        this.view.setPhysicalEnvironment(physEnv);
    }
    public void initParent(TransformGroup transformGroup){
        this.parent = new BranchGroup();
        this.parent.setCapability(BranchGroup.ALLOW_DETACH);
        this.parent.addChild(transformGroup);
    }
    public BranchGroup getParent() {
        return this.parent;
    }
    public Canvas3D getCanvas3D() {
        return this.canvas;
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
            //vitesse = GTransform.multily(this.transform.leftVector(),-0.2);
            vitesseTranslation = -0.2;
        }
        if(code == KeyEvent.VK_DOWN){
            //vitesse = GTransform.multily(this.transform.leftVector(),0.2);
            vitesseTranslation = 0.2;
        }
        if(code == KeyEvent.VK_LEFT){
            vitesseRotation = new Vector3d(0, 1, 0);
        }
        if(code == KeyEvent.VK_RIGHT){
            vitesseRotation = new Vector3d(0, -1, 0);
        }
        if(code == KeyEvent.VK_Z){
            vitesseRotation = new Vector3d(1, 0, 0);
        }
        if(code == KeyEvent.VK_S){
            vitesseRotation = new Vector3d(-1, 0, 0);
        }
    }

    @Override
    public void keyReleased(int code, boolean alt, boolean ctrl, boolean shift) {
        if(code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN){
            vitesse = new Vector3d(0, 0, 0);
            vitesseTranslation = 0;
        }
        if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_S || code == KeyEvent.VK_Z){
            vitesseRotation = new Vector3d(0, 0, 0);
        }
    }

    @Override
    public void keyType(char c) {
    }

    @Override
    public void update(long times, long deltaTime) {
        //this.localRotation(vitesseRotation);
        //this.move(GTransform.multily(GTransform.multily(this.transform.forwardVector(),vitesseTranslation), 0.25));
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
