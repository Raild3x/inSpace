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
import controllers.CelestialBodyController;
import controllers.GuiController;
import models.CelestialBody;
import controllers.Signal;
import models.InputModel;
import views.MouseView;
import java.io.FileInputStream;

import java.util.ArrayList;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RenderService {

    // Instance
    private static RenderService instance;

    // References
    private final Stage stage;
    private final Canvas canvas;
    private final Scene scene;
    private final StackPane stackPane;
    private final GraphicsContext gc;
    private final ArrayList<CelestialBodyController> gameObjects;

    // Settings (Finals)
    //public static final int WIDTH = 1200;
    //public static final int HEIGHT = 1000;
    public static final int FPS = 60;
    public static final int MAX_ZOOM = 4000;
    public static final int MIN_ZOOM = 5;

    // Signals
    public static final Signal<Long> Renderstep = new Signal<>();
    public static final Signal<Long> PostRenderstep = new Signal<>();

    // Variables (Volatile)
    private double ZOOM = 5;
    private double goalZOOM = ZOOM;
    private double offsetX = 0;
    private double offsetY = 0;
    private long lastTick;
    private CelestialBodyController currentPlanetFocus;


    /*
     * RenderService constructor
     * @param _stage The stage given by JavaFX in main
     */
    private RenderService(Stage _stage) throws Exception {
        this.stage = _stage;
        this.canvas = GuiController.getInstance().getCanvas();
        this.stackPane = GuiController.getInstance().getStackPane();
        this.scene = new Scene(this.stackPane);
        this.gc = this.canvas.getGraphicsContext2D();
        this.gameObjects = new ArrayList<>();
        this.lastTick = System.currentTimeMillis();
        this.init();
        this.initEvents();
    }

    /*
     * This version of getInstance should only be called by Main, it initializes the class singleton.
     * @param _stage The stage given by JavaFX in Main.
     */
    public static RenderService getInstance(Stage _stage) throws Exception {
        instance = new RenderService(_stage);
        return instance;
    }

    /*
     * Returns a reference to the singleton and errors if it has not yet been instantiated.
     */
    public static RenderService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("RenderService instance does not yet exist; it must first be created from Main.");
        }
        return instance;
    }

    /*
    Initializes the Renderer, sets up the screen and begins the render cycle by calling run every set milliseconds.
    @param _stage The window on which to build the canvas and scene
     */
    private void init() throws Exception {
        try {
            this.stage.setTitle("Orbit Test");

            Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000 / FPS), e -> run(gc)));
            tl.setCycleCount(Timeline.INDEFINITE);

            GuiController.getInstance().addGuiObject(canvas);
            this.stage.setScene(scene);
            this.stage.show();
            //begin rendering
            tl.play();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*
     * Organizational method for setting up events on singleton initialization
     */
    private void initEvents() {
        // Connect to events
        InputModel.getInstance().InputDragging.Connect(Delta -> {
            goalZOOM += Delta;
            goalZOOM = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, goalZOOM));
        });
    }

    /*
    Runs every frame and executes planet movement updates and rendering.
    @param _gc GraphicsContext being used to render on.
     */
    private void run(GraphicsContext _gc) {
        // Do update logic
        ZOOM += (goalZOOM - ZOOM) / 5;
        if (currentPlanetFocus != null) {
            offsetX += (currentPlanetFocus.getX() - offsetX) / 5;
            offsetY += (currentPlanetFocus.getY() - offsetY) / 5;
        }

        // set background color
        _gc.setFill(Color.BLACK);
        _gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

        // Clear the canvas
        gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

        double dx = getOffsetX();
        double dy = getOffsetY();
        // Update Object Movements //
        long currentTick = System.currentTimeMillis();
        for (CelestialBodyController body : gameObjects) {
            body.moveCelestialBody(currentTick - lastTick);
        }
        // Draw Objects //
        // offset camera
        _gc.translate(-dx, -dy);
        for (CelestialBodyController body : gameObjects) {
            body.renderCelestialBody(_gc);
        }
        //reset camera
        _gc.translate(dx, dy);

        PostRenderstep.Fire(currentTick - lastTick);
        lastTick = System.currentTimeMillis();
    }

    /*
    Adds the Controller of a given CelestialBody to the gameObjects arraylist to be rendered.
    @param _obj The CelestialBodyController that is going to be added.
     */
    public void addInstance(CelestialBodyController _obj) {
        gameObjects.add(_obj);
    }

    // Graphic Utility Methods
    /*
    --UNDER CONSTRUCTION--
     */
    public static void fadeIn(double t) {

    }

    //=================================== GETTERS ===================================//
    public CelestialBodyController getFocus() {
        return this.currentPlanetFocus;
    }

    public double getZoom() {
        return this.ZOOM * 2;
    }

    public double getOffsetX() {
        return this.offsetX - this.canvas.getWidth() / 2;
    }

    public double getOffsetY() {
        return this.offsetY - this.canvas.getHeight() / 2;
    }

    //=================================== SETTERS ===================================//
    public void setFocus(CelestialBodyController _currentPlanetFocus) {
        this.currentPlanetFocus = _currentPlanetFocus;
    }

    public void setFocus(String _currentPlanetFocus) {
        this.currentPlanetFocus = PlanetService.getPlanetController(_currentPlanetFocus);
    }

}
