package controllers;

import api.SunMoonRiseAdapter;
import java.io.File;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.scene.image.Image;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;
import services.RenderService;
import services.PlanetService;

/**
 *
 * @author szoor
 */
public class GuiController {

    // Background settings
    private static final int WIDTH = 1300;
    private static final int HEIGHT = 850;

    private final Canvas canvas;
    private final StackPane stackPane;

    protected static GuiController instance;

    /* 
     * Constructor for Gui Controller, set to private so it cant be accessed outside of getInstance
     */
    private GuiController() {
        this.canvas = new Canvas();
        this.stackPane = new StackPane();
        this.init();
    }

    /*
     * getInstance() returns the GuiController singleton and creates one to return if one does not yet exist.
     */
    public static GuiController getInstance() {
        if (instance == null) {
            instance = new GuiController();
        }
        return instance;
    }

    // Initializes aspects about the guiController
    private void init() {
        try {
            this.stackPane.getChildren().add(getImageView("background")); // background layer, I'm doing this here because it needs to be added first, o it will overlay everything else - Taylor
        } catch (URISyntaxException ex) {
            Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Adds a gui object node (Label, Button, etc..) to the stackPane to be
     * displayed.
     *
     * @param _kids Varargs array of Nodes to be added.
     */
    public void addGuiObject(Node... _kids) {
        for (Node obj : _kids) {
            this.stackPane.getChildren().add(obj);
        }
    }

    /**
     * Removes a gui object node (Label, Button, etc..) from the stackPane.
     *
     * @param _kids Varargs array of Nodes to be added.
     */
    public void removeGuiObject(Node... _kids) {
        for (Node obj : _kids) {
            this.stackPane.getChildren().remove(obj);
        }
    }

    public void recenter() {
        PlanetService.unFocus();
        RenderService.getInstance().setZoom(Math.min(RenderService.getInstance().getZoom() / 4, 250));
    }

    public void zoomPlanet(String name) {
        RenderService.getInstance().setFocus(name);
    }

    //=================================== GETTERS ===================================//
    public StackPane getStackPane() throws URISyntaxException {
        return this.stackPane;
    }

    public Canvas getCanvas() {
        this.canvas.setHeight(HEIGHT);
        this.canvas.setWidth(WIDTH);
        return this.canvas;
    }

    // method to request an image, pass a string with the "request" name - Taylor
    public ImageView getImageView(String request) throws URISyntaxException {
        ImageView imageView;
        File file;
        Image image;

        switch (request) {
            case "background": //set background
                file = new File("images\\background1.png");
                image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                imageView = new ImageView(image);
                imageView.setFitHeight(this.HEIGHT);
                imageView.setFitWidth(this.WIDTH);
                break;
            /*grab planet image by "name" request string, 
          when each planet image is grabbed, we manipulate the image to the size and location of the planet - Taylor*/
            case "Sun":
                file = new File("images\\sun.png");
                image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                imageView = new ImageView(image);
                break;

            case "Mercury":
                file = new File("images\\mercury.png");
                image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                imageView = new ImageView(image);
                break;

            case "Venus":
                file = new File("images\\venus.png");
                image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                imageView = new ImageView(image);
                break;

            case "Earth":
                file = new File("images\\earth.png");
                image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                imageView = new ImageView(image);
                break;
            case "Moon":
                file = new File("images\\moon.png");
                image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                imageView = new ImageView(image);
                break;

            case "Mars":
                file = new File("images\\mars.png");
                image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                imageView = new ImageView(image);
                break;

            case "Jupiter":
                file = new File("images\\jupiter.png");
                image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                imageView = new ImageView(image);
                break;

            case "Saturn":
                file = new File("images\\saturn.png");
                image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                imageView = new ImageView(image);
                break;

            case "Uranus":
                file = new File("images\\uranus.png");
                image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                imageView = new ImageView(image);
                break;

            case "Neptune":
                file = new File("images\\neptune.png");
                image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                imageView = new ImageView(image);
                break;

            default:
                file = new File("images\\moon.png"); // CHANGE THIS TO SOME RANDOM MOON THING
                image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                imageView = new ImageView(image);
        }

        return imageView;
    }

    //call for info from API
    public String getSunMoonRiseAdapter(String str) {

        SunMoonRiseAdapter sunMoon = new SunMoonRiseAdapter();
        String event = sunMoon.getSunMoonInfo(str);
        return event;
    }
}
