/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Link;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.SharedGroup;

/**
 *
 * @author TTRS_And_BTJB
 */
public class GLoad3DAssetsManager {
    private static GLoad3DAssetsManager INSTANCE = new GLoad3DAssetsManager();
    private static LinkedList<GMesh> allMesh = new LinkedList<GMesh>();
    private static LinkedList<String> allMeshName = new LinkedList<String>();
    
    private static Hashtable<String, SharedGroup> allStaticMesh = new Hashtable<String, SharedGroup>();
    
    private GLoad3DAssetsManager() {
    }
    public static Link getStaticMesh(String name){
        if(!allStaticMesh.isEmpty() && allStaticMesh.containsKey(name)){
            return new Link(allStaticMesh.get(name));
        }
        return null;
    }
    public static GMesh getMesh(String name, String newName){
        GMesh mesh = getMesh(name);
        changeNameMesh(getMesh(name), newName);
        return mesh;
    }
    public static GMesh getMesh(String name){
        Link mesh= getStaticMesh(name);
        if(mesh == null)
            return null;
        BranchGroup bg = new BranchGroup();
        bg.addChild(mesh);
        GMesh newMesh = new GMesh(name, bg);
        changeNameMesh(newMesh, name);
        allMesh.add(newMesh);
        return newMesh;
    }
    public static void changeNameMesh(GMesh mesh, String name){
        if(allMesh.isEmpty() || mesh == null)
            return;
        if(allMesh.contains(mesh)){
            String realName = name;
            String[] names = realName.split("_");
            try{
                Integer.parseInt(names[names.length - 1]);
                for(int i = 0; i < names.length - 2; i++){
                    realName += names[i];
                    if( i != names.length - 3)
                        realName += "_";
                }
            }catch(NumberFormatException e){
            }
            int index = 1;
            while(true){
                if(!containNameMesh(realName + "_" + index)){
                    realName = realName + "_" + index;
                    break;
                }
                index++;
            }
            mesh.setName(realName);
        }
    }
    private static boolean containNameMesh(String name){
        if(allMesh.isEmpty())
            return false;
        for(int i = 0; i < allMesh.size(); i++){
            if(allMesh.get(i).isMeshName(name))
                return true;
        }
        return false;
    }
    public static GLoad3DAssetsManager getInstance(){
        if(INSTANCE == null)
            INSTANCE = new GLoad3DAssetsManager();
        return INSTANCE;
    }
    public void opendFile(String file, String chemin){
        loadModel(file);
    }
    private boolean openZipFile(String nomArchive, String chemin){
        try {
            ZipFile zipFile = new ZipFile(nomArchive);
            Enumeration entries = zipFile.entries();
            ZipEntry entry = null;
            File fichier = null;
            File sousRep = null;
            while (entries.hasMoreElements()) {
                entry = (ZipEntry) entries.nextElement();
                if (!entry.isDirectory()) {
                    fichier = new File(chemin + File.separatorChar + entry.getName());
                    sousRep = fichier.getParentFile();
                    if (sousRep != null) {
                        if (!sousRep.exists()) {
                            sousRep.mkdirs();
                        }
                    }
                    int i = 0;
                    byte[] bytes = new byte[1024];
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fichier));
                    BufferedInputStream in = new BufferedInputStream(zipFile.getInputStream(entry));
                    while((i = in.read(bytes)) != -1){
                        out.write(bytes,0,i);
                    }
                    in.close();
                    out.flush();
                    out.close();
                    loadModel(chemin + "/" + entry.getName());
                }
            }
            zipFile.close();
            return true;
        } catch (ZipException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void loadModel(String file){
        if(file.contains(".obj")){
            Scene scene = loadWavefrontObject(file);
            listscenenamedobjects(scene);
        }
    }
    private Scene loadWavefrontObject(String filename) {
        ObjectFile waveFrontObject = new ObjectFile(ObjectFile.RESIZE);
        Scene scene = null;
        int error = 0;

        try {
            scene = waveFrontObject.load(filename);
        }catch (Exception e) {
            System.err.println(e+"*****************");
            System.exit(1);
        }

        if (error != 0)
          System.exit(error);

        return scene;
    }
    private static void listscenenamedobjects(Scene scene) {
        Map<String, Shape3D> namemap = scene.getNamedObjects(); 
        for (String name : namemap.keySet()) {
            System.out.println(name);
            Shape3D shape = namemap.get(name);
            scene.getSceneGroup().removeChild(namemap.get(name));
            
            String realName = name;
            if(allStaticMesh.containsKey(name)){
                String[] names = name.split("_");
                try{
                    Integer.parseInt(names[names.length - 1]);
                    for(int i = 0; i < names.length - 2; i++){
                        realName += names[i];
                        if( i != names.length - 3)
                            realName += "_";
                    }
                }catch(NumberFormatException e){
                    
                }
                int index = 1;
                while(true){
                    if(!allStaticMesh.containsKey(realName + "_" + index)){
                        realName = realName + "_" + index;
                        break;
                    }
                    index++;
                }
            }
            //shape.
            SharedGroup sg = new SharedGroup();
            sg.addChild(shape);
            allStaticMesh.put(realName, sg);
        }
    }
    public static void main(String[] args) {
        GLoad3DAssetsManager.getInstance().opendFile("datas/Models/peugeot-308/peugeot-308.obj", "datas/Models");
    }
}
