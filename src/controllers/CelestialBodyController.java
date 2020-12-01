package controllers;

import events.SelectedEvent;
import java.util.ArrayList;
import models.CelestialBody;
import javafx.scene.canvas.GraphicsContext;
import listeners.SelectedListener;

/**
 * @author Logan
 * @lastModified 12/1/2020
 *
 * @description The CelestialBodyController acts as an intermediary controller
 * between the CelestialBody models and the rest of the program.
 */
public class CelestialBodyController implements SelectedListener {

    private CelestialBody model;

    /**
     * Constructor for CelestialBodyController, sets up events.
     * @param _model 
     */
    public CelestialBodyController(CelestialBody _model) {
        this.model = _model;
        SelectedEvent.addListener(this);
    }

    // Generic toString method returns the name of the CelestialBody
    @Override
    public String toString() {
        return this.getName();
    }

    /**
     * Called by RenderService, updates the orbital angle of the CelestialBody
     * @param _dt The time that has passed since update was last called
    */
    public void moveCelestialBody(double _dt) {
        this.model.update(_dt);
    }

    /**
     * Called by RenderService, draws the CelestialBody on the canvas
     * @param _gc The graphics context on which to draw
    */
    public void renderCelestialBody(GraphicsContext _gc) {
        this.model.render(_gc);
    }

    // Called when we select a planet, bolds the orbit
    public void clickPlanet() {
        this.boldOrbit(true);
        System.out.println("Clicked: " + this.getName());
    }

    // Called when we want to zoom in close enough to the planets to see their moons
    public void zoomIn() {
        this.model.loadMoons();
        this.model.zoomIn();
    }

    //=================================== GETTERS ===================================//
    // Returns if the CelestialBody is a planet
    public boolean isPlanet() {
        return this.model.isPlanet;
    }

    // Returns if the CelestialBody is currently rendering at a realistic size
    public boolean isRealisticSize() {
        return this.model.realisticSize;
    }

    // Returns the celestialBody for this controller
    public CelestialBody getModel() {
        return this.model;
    }

    // Returns the X position of the celestialBody
    public double getX() {
        return this.model.getX();
    }

    // Returns the Y position of the celestialBody
    public double getY() {
        return this.model.getY();
    }

    // Returns the size of the celestialBody in km as the average radius
    public double getSize() {
        return this.model.getSize();
    }

    // Returns the english name of the celestialBody
    public String getName() {
        return this.model.name;
    }

    // Returns the moons of the planet (if any) as an arraylist of strings.
    public ArrayList<String> getMoons() {
        return this.model.moons;
    }

    // Makes an api call to retrieve some specified info about the CelestialBody
    // @param _infoType The string containing what property you want to ask for
    public String getInfo(String _infoType) {
        return this.model.getInfo(_infoType);
    }

    /** Returns the distance from a given x and y coordinate to the nearest point on the CelestialBody's orbit.
     * @param _px The x position value of the point
     * @param _py The y position value of the point
     */
    public double getDistToOrbit(double _px, double _py) {
        return this.model.getDistToOrbit(_px, _py);
    }

    /** Returns the distance from a given x and y coordinate CelestialBody.
     * @param _px The x position value of the point
     * @param _py The y position value of the point
     */
    public double getDistToPlanet(double _px, double _py) {
        return this.model.getDistToPlanet(_px, _py);
    }

    //=================================== SETTERS ===================================//
    /** Sets the boldOrbit value of the model to true or false
     * @param _val The Boolean to set the boldOrbit property to.
    */
    public void boldOrbit(boolean _val) {
        this.model.boldOrbit = _val;
    }

    //=================================== EVENTS ===================================//
    /** Fires when CelestialBody is selected and loads its moons
     * @param _cbc The controller that was selected (compares to see if they are the same)
     */
    @Override
    public void Selected(CelestialBodyController _cbc) {
        if (_cbc != this) {
            return;
        }
    }

    /** Fires when CelestialBody is unSelected and unloads its moons
     * @param _cbc The controller that was unSelected (compares to see if they are the same)
     */
    @Override
    public void UnSelected(CelestialBodyController _cbc) {
        if (_cbc != this) {
            return;
        }
        if (!this.model.isPlanet && this.model.orbitingBody != null) {
            System.out.println("Unloading "+this.model.orbitingBody.name);
            this.model.orbitingBody.unloadMoons();
            this.model.orbitingBody.realisticSize = false;
            this.model.orbitingBody.displayOrbit = true;
        } else {
            System.out.println("Unloading "+this.getName());
            this.model.unloadMoons();
            this.model.realisticSize = false;
            this.model.displayOrbit = true;
        }
        
    }
}
