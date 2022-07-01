/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kursinis;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
/**
 *
 * @author Justinas
 */
public class Launcher extends Agent{
    @Override
    protected void setup(){
        addBehaviour(new Start());
    }
    private class Start extends OneShotBehaviour{
       @Override
       public void action(){
           
           try{
                AgentContainer mc = myAgent.getContainerController();
                String[] args = {};
                String[] args1 = { "duomenys1.txt" }; 
                String[] args2 = { "duomenys2.txt" }; 
                 String[] args3 = { "duomenys3.txt" }; 
                 AgentController actrl = mc.createNewAgent("B1", "kursinis.Buyer", args);
                 actrl.start();
//                 actrl = mc.createNewAgent("B2", "kursinis.Buyer", args);
//                 actrl.start();
                 actrl = mc.createNewAgent("S1", "kursinis.Seller", args1);
                 actrl.start();
                 actrl = mc.createNewAgent("S2", "kursinis.Seller", args2);
                 actrl.start();
                 actrl = mc.createNewAgent("S3", "kursinis.Seller", args3);
                 actrl.start();
           }
           catch(Exception e){}
           doDelete();
       }
   }
}
