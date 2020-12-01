package controllers;

import services.PlanetService;
import javafx.scene.input.MouseEvent;

/**
 * @author Logan
 * @lastModified 11/4/2020
 *
 * @description A controller to communicate between the MouseView and the
 * InputModel
 */
public class MouseInputController extends InputController {

    private static MouseInputController instance;

    private static long lastClickTime;

    // Private Class Constructor
    private MouseInputController() {
        super();
        this.lastClickTime = System.currentTimeMillis();
    }

    // getInstance returns the singleton instance of this class.
    public static MouseInputController getInstance() {
        if (instance == null) {
            instance = new MouseInputController();
        }
        return instance;
    }

    // Called by MouseView when the mouse is moved. 
    // Sets the current x and y position in the inputModel.
    // @param e The recieved MouseEvent.
    public void mouseMoved(MouseEvent e) {
        setXY(e.getX(), e.getY());
    }

    // Called by MouseView when the mouse is dragged across the canvas. 
    // Sets the current x and y position in the inputModel.
    // @param e The recieved MouseEvent.
    public void mouseDragged(MouseEvent e) {
        setXY(e.getX(), e.getY());
        model.drag();
    }

    // Called by MouseView when the mouse is pressed. 
    // Sets the lastClickTime and the current x and y position in the inputModel.
    // @param e The recieved MouseEvent.
    public void mousePressed(MouseEvent e) {
        setXY(e.getX(), e.getY());
        this.lastClickTime = System.currentTimeMillis();
    }

    // Called by MouseView when the mouse is released. 
    // Calls to PlanetService to focus the nearest planet if it has been less than 200ms since pressing it down.
    // @param e The recieved MouseEvent.
    public void mouseReleased(MouseEvent e) {
        setXY(e.getX(), e.getY());
        if (System.currentTimeMillis() < this.lastClickTime + 200) {
            //System.out.println("Mouse released in time; time left: "+(200-(System.currentTimeMillis()-this.lastClickTime)));
            PlanetService.focusNearestPlanet();
        }

    }
}
