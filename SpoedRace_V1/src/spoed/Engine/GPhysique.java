/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import java.util.LinkedList;

/**
 *
 * @author TTRS_And_BTJB
 */
public class GPhysique implements GAbstract.GIAbstract{
    private static GPhysique INSTANCE = new GPhysique();
    private LinkedList<GMesh> abonner = new LinkedList<GMesh>();
    private LinkedList<TYPE_PHYSIQUE> type_physique = new LinkedList<TYPE_PHYSIQUE>();
    
    private GPhysique() {
    }

    @Override
    public void update(long times, long deltaTime) {
    }
    public void abonnement(GMesh abonner, TYPE_PHYSIQUE type){
        if(this.abonner.contains(abonner))
            return;
        this.abonner.add(abonner);
        this.type_physique.add(type);
    }
    public void desabonnement(GMesh abonner){
        int index = this.abonner.indexOf(abonner);
        this.abonner.remove(abonner);
        this.type_physique.remove(index);
    }
    public void changeType(GMesh abonner, TYPE_PHYSIQUE type){
        if(this.abonner.contains(abonner)){
            int index = this.abonner.indexOf(abonner);
            this.type_physique.set(index, type);
        }
    }
    public static GPhysique getInstance(){
        if(INSTANCE == null)
            INSTANCE = new GPhysique();
        return INSTANCE;
    }
    
    public enum TYPE_PHYSIQUE{STATIQUE, DYNAMIQUE}
}
