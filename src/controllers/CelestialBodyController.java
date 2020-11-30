/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import events.SelectedEvent;
import java.util.ArrayList;
import models.CelestialBody;
import services.RenderService;
import javafx.scene.canvas.GraphicsContext;
import listeners.SelectedListener;

/**
 *
 * @author Logan
 */
public class CelestialBodyController implements SelectedListener {
    
    private CelestialBody model;
    
    public CelestialBodyController(CelestialBody _model){
        this.model = _model;
        SelectedEvent.addListener(this);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    public void moveCelestialBody(double dt){
        this.model.update(dt);
    }
    
    public void renderCelestialBody(GraphicsContext gc){
        this.model.render(gc);
    }

    public void clickPlanet(){
        this.boldOrbit(true);
        System.out.println("Clicked: "+this.getName());
    }
    
    //=================================== GETTERS ===================================//
    public CelestialBody getModel() {
        return this.model;
    }
    
    public double getX(){
        return this.model.getX();
    }
    
    public double getY(){
        return this.model.getY();
    }
    
    public String getName(){
        return this.model.name;
    }
    
    public String getInfo(String _infoType){
        return this.model.getInfo(_infoType);
    }
    
    public double getDistToOrbit(double px, double py){
       return this.model.getDistToOrbit(px, py);
    }
    
    public double getDistToPlanet(double px, double py){
        return this.model.getDistToPlanet(px, py);
    }
    
    public ArrayList<String> getMoons() {
        return this.model.moons;
    }
    
    //=================================== SETTERS ===================================//
    public void boldOrbit(boolean val){
        this.model.boldOrbit = val;
    }

    //=================================== EVENTS ===================================//
    @Override
    public void Selected(CelestialBodyController cbc) {
        if (cbc != this)
            return;
        this.model.loadMoons();
    }

    @Override
    public void UnSelected(CelestialBodyController cbc) {
        if (cbc != this)
            return;
        this.model.unloadMoons();
    }
}
