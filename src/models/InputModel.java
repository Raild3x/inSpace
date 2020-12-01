package models;

import controllers.Signal;

/**
 * @author Logan
 * @lastModified 11/4/2020
 *
 * @description The InputModel class manages all the data for receiving user
 * input.
 */
public class InputModel {

    private static InputModel instance;

    // Events
    public static final Signal<Double> InputDragging = new Signal<>();

    // Properties
    private double lastX = 0;
    private double lastY = 0;
    private double x = 0;
    private double y = 0;

    private InputModel() {
        // Initialization Code Here If Needed
    }

    /*
    Singleton getInstance method for obtaining the InputModel object.
     */
    public static InputModel getInstance() {
        if (instance == null) {
            instance = new InputModel();
        }
        return instance;
    }

    /*
    Method to trigger the event for changing zoom
     */
    public void drag() {
        //System.out.println("DRAGGING");
        InputDragging.Fire((this.x - this.lastX) / 2); // pass delta
    }

    //=================================== GETTERS ===================================//
    // Return the current X position of user input
    public double getX() {
        return this.x;
    }

    // Return the current Y position of user input
    public double getY() {
        return this.y;
    }

    //=================================== SETTERS ===================================//
    // Sets the current X position of user input and sets the lastX to whatever the X position was before
    // @param _x The new x value
    public void setX(double _x) {
        lastX = x;
        x = _x;
    }

    // Sets the current Y position of user input and sets the lastY to whatever the Y position was before
    // @param _y The new y value
    public void setY(double _y) {
        lastY = y;
        y = _y;
    }
}
