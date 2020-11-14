package controllers;

import java.io.File;
import views.GuiView;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.scene.image.Image;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;
import services.RenderService;

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

    private File file;
    private Image image;
    private ImageView imageView;

    protected static GuiController instance;

    /* 
     * Constructor for Gui Controller, set to private so it cant be accessed outside of getInstance
     */
    private GuiController() {
        this.canvas = new Canvas();
        this.stackPane = new StackPane();
        try {
            this.stackPane.getChildren().add(getImageView("background")); // background layer, I'm doing this here because it needs to be added first, o it will overlay everything else - Taylor
        } catch (URISyntaxException ex) {
            Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    /*
     * Adds a gui object node (Label, Button, etc..) to the stackPane to be displayed.
     * @param _kids Varargs array of Nodes to be added.
     */
    public void addGuiObject(Node... _kids) {
        for (Node obj : _kids) {
            this.stackPane.getChildren().add(obj);
        }
    }

    /*
     * Removes a gui object node (Label, Button, etc..) from the stackPane.
     * @param _kids Varargs array of Nodes to be added.
     */
    public void removeGuiObject(Node... _kids) {
        for (Node obj : _kids) {
            this.stackPane.getChildren().remove(obj);
        }
    }

    public void recenter(String name) {

        RenderService.getInstance().setFocus("Sun");
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

        switch (request) {
            case "background": //set background
                this.file = new File("C:\\Users\\szoor\\OneDrive\\Documents\\School\\CSC 340\\inSpace\\images\\background1.png");
                this.image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                this.imageView = new ImageView(image);
                this.imageView.setFitHeight(this.HEIGHT);
                this.imageView.setFitWidth(this.WIDTH);
                break;
            /*grab planet image by "name" request string, 
          when each planet image is grabbed, we manipulate the image to the size and location of the planet - Taylor*/
            case "Sun":
                this.file = new File("C:\\Users\\szoor\\OneDrive\\Documents\\School\\CSC 340\\inSpace\\images\\sun.png");
                this.image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                this.imageView = new ImageView(image);
                break;

            case "Mercury":
                this.file = new File("C:\\Users\\szoor\\OneDrive\\Documents\\School\\CSC 340\\inSpace\\images\\mercury.png");
                this.image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                this.imageView = new ImageView(image);
                break;

            case "Venus":
                this.file = new File("C:\\Users\\szoor\\OneDrive\\Documents\\School\\CSC 340\\inSpace\\images\\venus.png");
                this.image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                this.imageView = new ImageView(image);
                break;

            case "Earth":
                this.file = new File("C:\\Users\\szoor\\OneDrive\\Documents\\School\\CSC 340\\inSpace\\images\\earth.png");
                this.image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                this.imageView = new ImageView(image);
                break;
            case "Moon":
                this.file = new File("C:\\Users\\szoor\\OneDrive\\Documents\\School\\CSC 340\\inSpace\\images\\moon.png");
                this.image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                this.imageView = new ImageView(image);
                break;

            case "Mars":
                this.file = new File("C:\\Users\\szoor\\OneDrive\\Documents\\School\\CSC 340\\inSpace\\images\\mars.png");
                this.image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                this.imageView = new ImageView(image);
                break;

            case "Jupiter":
                this.file = new File("C:\\Users\\szoor\\OneDrive\\Documents\\School\\CSC 340\\inSpace\\images\\jupiter.png");
                this.image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                this.imageView = new ImageView(image);
                break;

            case "Saturn":
                this.file = new File("C:\\Users\\szoor\\OneDrive\\Documents\\School\\CSC 340\\inSpace\\images\\saturn.png");
                this.image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                this.imageView = new ImageView(image);
                break;

            case "Uranus":
                this.file = new File("C:\\Users\\szoor\\OneDrive\\Documents\\School\\CSC 340\\inSpace\\images\\uranus.png");
                this.image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                this.imageView = new ImageView(image);
                break;

            case "Neptune":
                this.file = new File("C:\\Users\\szoor\\OneDrive\\Documents\\School\\CSC 340\\inSpace\\images\\neptune.png");
                this.image = new Image(file.toURI().toString(), this.WIDTH, this.HEIGHT, true, true, true);
                this.imageView = new ImageView(image);
                break;
        }

        return imageView;
    }
}
