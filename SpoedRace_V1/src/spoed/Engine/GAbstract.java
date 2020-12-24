/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoed.Engine;

import java.util.LinkedList;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author TTRS_And_BTJB
 * classe de base du moteur pour la gestion des boucles de jeux
 * tout objet de l'univer qui doit avoir des donner qui vont changer avec le temps doivent declarrer un objet de type
 * GAbstract et implementer l'interface GIAbstract
 * a noter ici qu'un objet GAbstract est un singleton dont il n'a qu'une seul instance dans tout
 * le projetr
 */
public class GAbstract /*extends Thread*/{
    private static GAbstract INSTANCE = new GAbstract(); // instance unique de la classe
    // ensemble de classe qui vont subir une evolution dynamique dans le temps
    private LinkedList<GAbstract.GIAbstract> giLoop = new LinkedList<GAbstract.GIAbstract>();
    
    // gestion du temps
    private long temps_zero = System.currentTimeMillis();
    private long temps_actuel = 0;
    private long temps_precedent = 0;
    
    // le timer va nous permetre de creer un thread ou on va gerer la boucle de jeu
    Timer timer;
    
    private GAbstract() {
        /**/timer = createTimer ();
        timer.start ();
    }
    public void restart(){
        temps_zero = System.currentTimeMillis();
        temps_actuel = 0;
        temps_precedent = 0;
    }
    public void addGAbstract(GAbstract.GIAbstract gie){
        giLoop.add(gie);
    }
    public static GAbstract getInstance(){
        if(INSTANCE == null)
            INSTANCE = new GAbstract();
        return INSTANCE;
    }
    /**/private Timer createTimer (){
        ActionListener action = new ActionListener (){
            public void actionPerformed (ActionEvent event){
                temps_precedent = temps_actuel;
                temps_actuel = System.currentTimeMillis() - temps_zero;
                
                long delta_temps = temps_actuel - temps_precedent;
                
                if(!giLoop.isEmpty()){
                    for(int i = 0; i < giLoop.size(); i++){
                        giLoop.get(i).update(temps_actuel, delta_temps);
                    }
                }
            }
        };
        return new Timer (1, action);
    } 
    public interface GIAbstract{
        public void update(long times, long deltaTime);
    }
}
