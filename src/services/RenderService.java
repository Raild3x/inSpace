package services;

import controllers.CelestialBodyController;
import controllers.GuiController;
import controllers.Signal;
import models.InputModel;

import java.util.ArrayList;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

/**
 * @author Logan
 * @lastModified 12/1/2020
 *
 * @description The RenderService class manages the overarching rendering
 * system. Controlling what is rendered and when. It also manages zoom and
 * focusing as they are subsections of rendering.
 */
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
    public static final int MAX_ZOOM = 100000;
    public static final int MIN_ZOOM = 5;

    // Signals
    public static final Signal<Long> Renderstep = new Signal<>();
    public static final Signal<Long> PostRenderstep = new Signal<>();

    // Variables (Volatile)
    private long startTime = System.currentTimeMillis();
    private double ZOOM = 5;
    private double goalZOOM = ZOOM;
    private double offsetX = 0;
    private double offsetY = 0;
    private long lastTick;
    private CelestialBodyController currentPlanetFocus;


    /**
     * RenderService constructor
     * @param _stage The stage given by JavaFX in main
     * @throws Exception
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

    /**
     * This version of getInstance should only be called by Main, it initializes the class singleton.
     * @param _stage The stage given by JavaFX in Main.
     * @throws Exception
     */
    public static RenderService getInstance(Stage _stage) throws Exception {
        instance = new RenderService(_stage);
        return instance;
    }

    /**
     * Returns a reference to the singleton and errors if it has not yet been instantiated.
     */
    public static RenderService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("RenderService instance does not yet exist; it must first be created from Main.");
        }
        return instance;
    }

    /**
     * Initializes the Renderer, sets up the screen and begins the render cycle by calling run every set milliseconds.
     * @param _stage The window on which to build the canvas and scene
     * @throws Exception
     */
    private void init() throws Exception {
        GuiController gc = GuiController.getInstance();
        //gc.addGuiObject(gc.getProgressBar());
        this.scene.setFill(Color.BLACK);
        this.stage.setScene(this.scene);
        this.stage.show();

        this.startRendering();
    }

    // Starts the timeline for the rendering
    public void startRendering() {
        System.out.println("Beginning Render.");
        try {
            GuiController gc = GuiController.getInstance();

            Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000 / FPS), e -> run(this.gc)));
            tl.setCycleCount(Timeline.INDEFINITE);

            gc.addGuiObject(canvas);
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

    /**
     * Runs every frame and executes planet movement updates and rendering.
     * @param _gc GraphicsContext being used to render on.
     */
    private synchronized void run(GraphicsContext _gc) {
        // Do update logic
        this.ZOOM += (this.goalZOOM - this.ZOOM) / 5;
        if (this.currentPlanetFocus != null) {
            if (this.currentPlanetFocus.isRealisticSize()) {
                this.offsetX = this.currentPlanetFocus.getX();
                this.offsetY = this.currentPlanetFocus.getY();
            } else {
                this.offsetX += (this.currentPlanetFocus.getX() - this.offsetX) / 5;
                this.offsetY += (this.currentPlanetFocus.getY() - this.offsetY) / 5;
            }
        }

        // set background color
        _gc.setFill(Color.BLACK);
        _gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

        // Clear the canvas
        _gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

        double dx = getOffsetX();
        double dy = getOffsetY();
        // Update Object Movements //
        long currentTick = System.currentTimeMillis();
        try {
            for (CelestialBodyController body : gameObjects) {
                try {
                    body.moveCelestialBody(currentTick - lastTick);
                } catch (NullPointerException e) {
                    //System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to access arraylist");
        }

        // Draw Objects //
        // offset camera
        _gc.translate(-dx, -dy);
        try {
            for (CelestialBodyController body : gameObjects) {
                try {
                    body.renderCelestialBody(_gc);
                } catch (NullPointerException e) {
                    //System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to access arraylist");
        }

        //reset camera
        _gc.translate(dx, dy);

        PostRenderstep.Fire(currentTick - lastTick);
        lastTick = System.currentTimeMillis();
    }

    /**
     * Adds the Controller of a given CelestialBody to the gameObjects arraylist to be rendered.
     * @param _obj The CelestialBodyController that is going to be added.
     */
    public void addInstance(CelestialBodyController _obj) {
        gameObjects.add(_obj);
    }

    /**
     * Removes the Controller of a given CelestialBody from the gameObjects arraylist.
     * @param _obj The CelestialBodyController that is going to be removed.
     */
    public void removeInstance(CelestialBodyController _obj) {
        gameObjects.remove(_obj);
    }

    //=================================== GETTERS ===================================//
    // Return the current focus of the renderer (object in center of screen)
    public CelestialBodyController getFocus() {
        return this.currentPlanetFocus;
    }

    // Return the current zoom * 2 for math purposes
    public double getZoom() {
        return this.ZOOM * 2;
    }

    // Get the current x offset of the renderer accounting for screen size.
    public double getOffsetX() {
        return this.offsetX - this.canvas.getWidth() / 2;
    }

    // Get the current y offset of the renderer accounting for screen size.
    public double getOffsetY() {
        return this.offsetY - this.canvas.getHeight() / 2;
    }

    // Get the time elapsed since the program was started up.
    public long getElapsedTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    //=================================== SETTERS ===================================//
    /**
     * Sets the focus of the renderer to a celestialBodyController
     * @param _currentPlanetFocus The name of the celestial body controller to focus on.
    */
    public void setFocus(String _currentPlanetFocus) {
        this.currentPlanetFocus = PlanetService.getPlanetController(_currentPlanetFocus);
    }

    /**
     * Sets the attempted goal zoom of the renderer, clamped by the max and min zoom values.
     * @param _newZoom  The new goal value for the zoom
    */
    public void setZoom(double _newZoom) {
        this.goalZOOM = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, _newZoom));
    }

}
