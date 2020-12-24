/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Games;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Geometry;
import javax.media.j3d.Light;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import spoed.Engine.*;

/**
 *
 * @author TTRS_And_BTJB
 */
public class GLevel_Test extends GLevels{
    
    private GCamera cameraTPS = null;
    private float limiteBounds = 10000.0f;
    private BoundingSphere bounds = new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 100.0) ;
    
    public GLevel_Test(int width, int height) {
        super(width, height);
        GLoad3DAssetsManager.getInstance().loadModel("datas/Models/peugeot-308/peugeot-308.obj");
        GLoad3DAssetsManager.getInstance().loadModel("datas/Models/Decor1/decor1.obj");
        GLoad3DAssetsManager.getInstance().loadModel("datas/Models/Obs/obs.obj");
        GLoad3DAssetsManager.getInstance().loadModel("datas/Models/voiture/voiture.obj");
        GLoad3DAssetsManager.getInstance().loadModel("datas/Models/voiture/bugatti_1.obj");
        GLoad3DAssetsManager.getInstance().loadModel("datas/Models/Obs/direction.obj");
        cameraTPS = new GCamera();
        cameraTPS.init();
        //cameraTPS.setPosition(new Point3d(6f, 0f, 0f));
        cameraTPS.setPosition(new Point3d(0f, 2.5f, -6f));
        cameraTPS.localRotationX(-15);
        cameraTPS.localRotationY(180);
        //cameraTPS.setLocalRotation(new Vector3d(-15, 180, 0));
//        cameraTPS.lookAt();
        addCamera(cameraTPS);
        super.setScene();
        this.add(cameraTPS.getCanvas3D(), BorderLayout.CENTER);
    }

    @Override
    public BranchGroup createLevels() {
        return sceneLevelTest();
        /*BranchGroup parent = new BranchGroup();
        return parent;*/
    }
    private BranchGroup sceneLevelTest(){
        // Creation de l'objet parent qui contiendra tous les autres objets 3D
        BranchGroup parent = new BranchGroup();

        // Creation du groupe de transformation sur lequel vont s'appliquer les
        // comportements
        GTransform mouseTransform = new GTransform();

        // Le groupe de transformation sera modifie par le comportement de la
        // souris
        mouseTransform.getTransform().setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        mouseTransform.getTransform().setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        // Creation comportement rotation a la souris
        MouseRotate rotate = new MouseRotate(mouseTransform.getTransform());
        rotate.setSchedulingBounds(new BoundingSphere());
        parent.addChild(rotate);

        // Creation comportement deplacement a la souris
        MouseTranslate translate = new MouseTranslate(mouseTransform.getTransform());
        translate.setSchedulingBounds(new BoundingSphere());
        parent.addChild(translate);

        // Creation comportement zoom a la souris
        MouseZoom zoom = new MouseZoom(mouseTransform.getTransform());
        zoom.setSchedulingBounds(new BoundingSphere());
        parent.addChild(zoom);
        
        GMesh decor = GLoad3DAssetsManager.getMesh("cube");
        
        decor.move(new Vector3d(0, -1, 0));
        decor.scale(new Vector3d(100,100,100));
        
        GCar car = new GCar();
        parent.addChild(car.getParent());
        
        /*
        steering_wheel_torus.001
        car_plane.002
        sphere
        sweethome3d_window_mirror_plane.001
        car.001_plane.003
        */
        
        //DirectionalLight soleil = new DirectionalLight(new Color3f(255, 234, 159), new Vector3f(0,0,0));
        
        // Ajout de la lumiere a l'objet racine de la scene 3D
        //parent.addChild(/**/createLight()/** /createSpotLight(new Vector3d(0, 0, 0))/**/);
        GLight lumier = new GLight();
        parent.addChild(lumier.getParent());
        parent.addChild(GEvent.getInstance());
        return parent;
    }
    
    public TransformGroup createSpotLight (Vector3d v3d) {
        Transform3D translation = new Transform3D () ;
        translation.setTranslation (v3d) ;
        translation.setScale (0.5d) ;
        TransformGroup objTrans = new TransformGroup (translation) ;
        objTrans.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE) ;
        objTrans.setCapability (TransformGroup.ALLOW_TRANSFORM_READ) ;
        objTrans.setCapability (TransformGroup.ENABLE_PICK_REPORTING) ;
        Color3f spotLightColor = new Color3f (1.0f, 0.0f, 0.0f) ;
        Cone cone = new Cone (0.1f, 0.15f) ;
        cone.getShape (0).getGeometry ().setCapability(Geometry.ALLOW_INTERSECT) ;
        cone.getShape (1).getGeometry ().setCapability(Geometry.ALLOW_INTERSECT) ;
        objTrans.addChild (cone) ;
        Point3f position = new Point3f (0.0f, 0.0f, 0.0f) ;
        Point3f attenuation = new Point3f (1.0f, 1.0f, 1.0f) ;
        Vector3f direction = new Vector3f (0.0f, -1.0f, 0.0f) ;
        float spreadAngle = (float)Math.PI/2 ;
        float concentration = 0.0f ;
        SpotLight spot = new SpotLight (true, spotLightColor, position, attenuation, direction, spreadAngle, concentration) ;
        spot.setInfluencingBounds (bounds) ;
        objTrans.addChild (spot) ;
        return (objTrans) ;
    }
    private Light createLight() {
        DirectionalLight light = new DirectionalLight(true, new Color3f(new Color(255, 234, 159)), new Vector3f(-0.3f, 0.2f, -1.0f));
        light.setInfluencingBounds(new BoundingSphere(new Point3d(), 10000.0));
        return light;
    }
    
    @Override
    public BranchGroup createBackground() {
        // create a parent BranchGroup for the Background
        BranchGroup backgroundGroup = new BranchGroup();
        // create a new Background node
        Background back = new Background();
        // set the range of influence of the background
        back.setApplicationBounds( getBoundingSphere() );
        // create a BranchGroup that will hold
        // our Sphere geometry
        BranchGroup bgGeometry = new BranchGroup();
        // create an appearance for the Sphere
        Appearance app = new Appearance();
        // load a texture image using the Java 3D texture loader
        Texture tex = new TextureLoader("datas/hdri/back.jpg", this).getTexture();
        // apply the texture to the Appearance
        app.setTexture( tex );
        // create the Sphere geometry with radius 1.0.
        // we tell the Sphere to generate texture coordinates
        // to enable the texture image to be rendered
        // and because we are *inside* the Sphere we have to generate
        // Normal coordinates inwards or the Sphere will not be visible.
        Sphere sphere = new Sphere( 1.0f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS_INWARD, app );
        // start wiring everything together,
        // add the Sphere to its parent BranchGroup.
        bgGeometry.addChild( sphere );
        // assign the BranchGroup to the Background as geometry.
        back.setGeometry( bgGeometry );
        // add the Background node to its parent BranchGroup.
        backgroundGroup.addChild( back );
        return backgroundGroup;
    }
    
    private Bounds getBoundingSphere() {
        return new BoundingSphere( new Point3d(0.0,0.0,0.0), limiteBounds );
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
    }

    @Override
    public void keyReleased(int code, boolean alt, boolean ctrl, boolean shift) {
    }

    @Override
    public void keyType(char c) {
    }

    @Override
    public void loadData() {
    }

    @Override
    public void update(long times, long deltaTime) {
    }
}

