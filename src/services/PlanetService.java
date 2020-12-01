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
import controllers.GuiController;
import events.HoverEvent;
import events.SelectedEvent;
import java.util.ArrayList;
import models.InputModel;
import java.util.Hashtable;
import javafx.concurrent.Task;

/**
 * @author Logan
 * @lastModified 12/1/2020
 *
 * @description The PlanetService class acts as a subset of main, it loads in
 * all the planets/moons and their data and manages their related events.
 */
public class PlanetService {

    private static final RenderService renderService = RenderService.getInstance();

    // Properties
    private static final Hashtable<String, CelestialBodyController> celestialBodyControllers = new Hashtable<String, CelestialBodyController>();
    private static CelestialBodyController closest;
    private static CelestialBodyController lastSelected;
    private static double dist;

    private PlanetService() {
        throw new IllegalStateException("Service class");
    }
    
    // Called by main to init the planets and their events.
    public static void init() {
        initPlanets();
        initPlanetEvents();
    }

    /*
     * Manages the creation of all CelestialBody objects
     */
    private static void initPlanets() {
        AstroApiAdapter AstroApi = new AstroApiAdapter();

        // Create celestial bodies
        CelestialBody Sun = new CelestialBody("Sun", Color.YELLOW);//4326.90
        initNewCelestialBody(Sun);

        // Create a new thread to load the planets in so they dont lag the renderer.
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                updateMessage("Loading: " + "Mercury");
                updateProgress(1, 9);
                CelestialBody Mercury = new CelestialBody("Mercury", Sun, Color.GRAY);
                initNewCelestialBody(Mercury);

                updateMessage("Loading: " + "Venus");
                updateProgress(2, 9);
                CelestialBody Venus = new CelestialBody("Venus", Sun, Color.GREEN);
                initNewCelestialBody(Venus);

                updateMessage("Loading: " + "Earth");
                updateProgress(3, 9);
                CelestialBody Earth = new CelestialBody("Earth", Sun, Color.BLUE);
                initNewCelestialBody(Earth);

                updateMessage("Loading: " + "Mars");
                updateProgress(4, 9);
                CelestialBody Mars = new CelestialBody("Mars", Sun, Color.RED);
                initNewCelestialBody(Mars);

                updateMessage("Loading: " + "Jupiter");
                updateProgress(5, 9);
                CelestialBody Jupiter = new CelestialBody("Jupiter", Sun, Color.BEIGE);
                initNewCelestialBody(Jupiter);

                updateMessage("Loading: " + "Saturn");
                updateProgress(6, 9);
                CelestialBody Saturn = new CelestialBody("Saturn", Sun, Color.CHOCOLATE);
                initNewCelestialBody(Saturn);

                updateMessage("Loading: " + "Uranus");
                updateProgress(7, 9);
                CelestialBody Uranus = new CelestialBody("Uranus", Sun, Color.AQUAMARINE);
                initNewCelestialBody(Uranus);

                updateMessage("Loading: " + "Neptune");
                updateProgress(8, 9);
                CelestialBody Neptune = new CelestialBody("Neptune", Sun, Color.AQUA);
                initNewCelestialBody(Neptune);

                updateMessage("Done!");
                updateProgress(9, 9);

                // Use api to get other planet moons
                ArrayList<CelestialBody> Moons = new ArrayList<>();
                celestialBodyControllers.forEach((name, controller) -> {
                    if (AstroApi.getBodyInfo(name, "isPlanet") == "true") {
                        System.out.println("Loading moons for: " + name);
                        try {
                            for (String moonName : controller.getMoons()) {
                                CelestialBody moon = new CelestialBody(moonName, controller.getModel());
                                Moons.add(moon);
                            }
                        } catch (Exception e) {
                            System.out.println("Issue loading moons: " + e);
                        }
                    }
                });

                System.out.println("Initializing Moon Controllers.");
                //Create moon controllers afterwards so it doesnt cause issues in the foreach
                for (CelestialBody moon : Moons) {
                    CelestialBodyController cbc = new CelestialBodyController(moon);
                    celestialBodyControllers.put(moon.apiName, cbc);
                }

                System.out.println("LOADING COMPLETE!");
                return null;
            }
        };
        new Thread(task).start();

        // Set initial focus
        renderService.setFocus("Sun");
    }

    /*
     * An organizational method to split up the code. Manages the creation of any events needed to manage the planets.
     */
    private static void initPlanetEvents() {
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

            if (lastSelected != null) {
                lastSelected.boldOrbit(true);
            }

            celestialBodyControllers.forEach((name, controller) -> {
                if (controller != lastSelected) {
                    controller.boldOrbit(false);
                }
                double cd = Math.min(controller.getDistToOrbit(ix + ox, iy + oy), controller.getDistToPlanet(ix + ox, iy + oy));
                if (cd < dist) {
                    closest = controller;
                    dist = cd;
                }
            });

            // Change how close you need to be to the orbit here to trigger the Hover events
            if (dist < 10) {
                if (closest != lastClosest) {
                    if (lastClosest != null) {
                        HoverEvent.fireHoverEnded(lastClosest);
                    }
                    HoverEvent.fireHoverBegan(closest);
                }
                closest.boldOrbit(true);
            } else {
                if (lastClosest != null) {
                    HoverEvent.fireHoverEnded(lastClosest);
                }
                closest = null;
            }
        });
    }

    /**
     * Method to simplify creating new celestial bodies and their respective controllers
     * @param _cb The CelestialBody object to be used as the base for the controller
     */
    private static void initNewCelestialBody(CelestialBody _cb) {
        CelestialBodyController cbc = new CelestialBodyController(_cb);
        celestialBodyControllers.put(_cb.name, cbc);
        renderService.addInstance(cbc);
    }

    // Adjusts the camera render to center upon the nearest planet to where the user clicked.
    public static void focusNearestPlanet() {
        System.out.println("focus nearest planet");
        if (closest == lastSelected) {
            return;
        }
        unFocus();
        if (closest == null) {
            return;
        }
        SelectedEvent.fireSelected(closest);
        renderService.setFocus(closest.getName());
        lastSelected = closest;
    }

    // Action method to unfocus the current planet and set the focus back to the Sun.
    public static void unFocus() {
        if (lastSelected != null) {
            System.out.println("Unfocusing!");
            try {
                if (closest != null) {
                    if (!lastSelected.getModel().moons.contains(closest.getModel().apiName)) {
                        SelectedEvent.fireUnSelected(lastSelected);
                    }
                } else {
                    SelectedEvent.fireUnSelected(lastSelected);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            lastSelected.boldOrbit(false);
            lastSelected = null;
            renderService.setFocus("Sun");
        }
    }

    /**
     * Converts a double in terms of km to AU.
     * @param _km Value in km to convert
     * @return 
     */
    public static double kmToAU(double _km) {
        return _km / 149598073;
    }

    //=================================== GETTERS ===================================//
    /**
     * Return the controller for the given planetName
     * @param _planetName
     * @return 
     */
    public static CelestialBodyController getPlanetController(String _planetName) {
        return celestialBodyControllers.get(_planetName);
    }
}
