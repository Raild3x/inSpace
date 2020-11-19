/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author Logan
 */
import api.*;
import javafx.scene.paint.Color;
import models.CelestialBody;
import controllers.CelestialBodyController;
import controllers.Signal;
import events.HoverEvent;
import events.SelectedEvent;
import java.util.ArrayList;
import models.InputModel;
import views.MouseView;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Moon;
import org.json.JSONException;


public class PlanetService{
    
    private static RenderService renderService;
    
    // Properties
    private static Hashtable<String, CelestialBodyController> celestialBodyControllers = new Hashtable<String, CelestialBodyController>();
    private static CelestialBodyController closest;
    private static double dist;
    
    
    private PlanetService() {
        throw new IllegalStateException("Service class");
    }

    public static void init(){
        renderService = RenderService.getInstance();
        initPlanets();
        initPlanetEvents();
    }

    /*
    Manages the creation of all CelestialBody objects
    */
    private static void initPlanets(){
        // Create celestial bodies
        CelestialBody Sun = new CelestialBody("Sun", 526.90, Color.YELLOW);//4326.90
        
        CelestialBody Mercury = new CelestialBody("Mercury", 15.16, Sun, 0.3870, 0.3788, 0.0796, Color.GRAY);
        CelestialBody Venus = new CelestialBody("Venus", 37.60, Sun, 0.7219, 0.7219, 0.0049, Color.GREEN);
        CelestialBody Earth = new CelestialBody("Earth", 39.59, Sun, 1.0027, 1.0025, 0.0167, Color.BLUE);
        CelestialBody Mars = new CelestialBody("Mars", 21.06, Sun, 1.5241, 1.5173, 0.1424, Color.RED);
        CelestialBody Jupiter = new CelestialBody("Jupiter", 434.41, Sun, 5.2073, 5.2010, 0.2520, Color.BEIGE);
        CelestialBody Saturn = new CelestialBody("Saturn", 361.84, Sun, 9.5590, 9.5231, 0.5181, Color.CHOCOLATE);
        CelestialBody Uranus = new CelestialBody("Uranus", 157.59, Sun, 19.1848, 19.1645, 0.9055, Color.AQUAMARINE);
        CelestialBody Neptune = new CelestialBody("Neptune", 152.99, Sun, 30.0806, 30.0788, 0.2687, Color.AQUA);
        
        //CelestialBody Moon = new CelestialBody("Moon", 10.79, Earth, 0.002569, 0.002569, 0.0, Color.GRAY);
        
        // Init their controllers
        initNewCelestialBody(Sun);
        initNewCelestialBody(Mercury);
        initNewCelestialBody(Venus);
        initNewCelestialBody(Earth);
        initNewCelestialBody(Mars);
        initNewCelestialBody(Jupiter);
        initNewCelestialBody(Saturn);
        initNewCelestialBody(Uranus);
        initNewCelestialBody(Neptune);
        //initNewCelestialBody(Moon);
        
        
        // Use api to get other planet moons
        AstroApiAdapter AstroApi = new AstroApiAdapter();
        ArrayList<CelestialBody> Moons = new ArrayList<>();
        celestialBodyControllers.forEach((name,controller) -> {
            if (AstroApi.getBodyInfo(name, "isPlanet") == "true") {
                System.out.println(name + " isPlanet: " + AstroApi.getBodyInfo(name, "isPlanet"));
                ArrayList<String> moons = AstroApi.getBodyMoons(name);
                for (String moonName : moons) {
                    System.out.println(moonName+": "+AstroApi.getBodyInfo(moonName));
                    CelestialBody moon = new Moon(moonName, controller.getModel());
                    Moons.add(moon);
                }
            }
        });
        //Create moon controllers
        for (CelestialBody moon : Moons)
            initNewCelestialBody(moon);
        
        // Set initial focus
        renderService.setFocus(getPlanetController("Sun"));
    }
    
    /*
    An organizational method to split up the code. Manages the creation of any events needed to manage the planets.
    */
    private static void initPlanetEvents(){
        InputModel input = InputModel.getInstance();
        
        // Runs every frame, determines the closest planet/orbit if there is one.
        RenderService.PostRenderstep.Connect(dt -> {
            CelestialBodyController lastClosest = closest;
            closest = null;
            dist = Integer.MAX_VALUE;
            
            double ix = input.getX();
            double iy = input.getY();
            double ox = renderService.getOffsetX();
            double oy = renderService.getOffsetY();
            
            renderService.getFocus().boldOrbit(true);

            celestialBodyControllers.forEach((name,controller) -> {
                if (controller != renderService.getFocus())
                    controller.boldOrbit(false);
                double cd = Math.min(controller.getDistToOrbit(ix + ox, iy + oy), controller.getDistToPlanet(ix + ox, iy + oy));
                if(cd < dist) {
                    closest = controller;
                    dist = cd;
                } 
            });
            if (dist < 10) { // Change how close you need to be to the orbit here to trigger the Hover events
                if (closest != lastClosest) {
                    if (lastClosest != null)
                        HoverEvent.fireHoverEnded(lastClosest);
                    HoverEvent.fireHoverBegan(closest);
                }
                closest.boldOrbit(true);
            } else {
                if (lastClosest != null)
                    HoverEvent.fireHoverEnded(lastClosest);
                closest = null;
            }
        });
        
    }
    
    /*
    Method to simplify creating new celestial bodies and their respective controllers
    @param _cb The CelestialBody object to be used as the base for the controller
    */
    private static void initNewCelestialBody(CelestialBody _cb){
        CelestialBodyController cbc = new CelestialBodyController(_cb);
        celestialBodyControllers.put(_cb.name, cbc);
        renderService.addInstance(cbc);
    }
    
    public static void focusNearestPlanet(){
        CelestialBodyController lastSelected = renderService.getFocus();
        if (closest == lastSelected)
            return;
        SelectedEvent.fireUnSelected(lastSelected);
        if (closest == null) {
            SelectedEvent.fireSelected(getPlanetController("Sun"));
            renderService.setFocus(getPlanetController("Sun"));
            return;
        }
        //closest.boldOrbit(true);
        SelectedEvent.fireSelected(closest);
        renderService.setFocus(closest);
    }
    
    //=================================== GETTERS ===================================//
    public static CelestialBodyController getPlanetController(String _planetName){
        return celestialBodyControllers.get(_planetName);
    }
}
