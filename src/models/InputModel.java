package models;

import controllers.Signal;

/**
 *
 * @author Logan
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
    
    private InputModel(){
        // Initialization Code Here If Needed
    }
    
    /*
    Singleton getInstance method for obtaining the InputModel object.
    */
    public static InputModel getInstance(){
        if (instance == null){
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
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    //=================================== SETTERS ===================================//
    public void setX(double _x) {
        lastX = x;
        x = _x;
    }
    
    public void setY(double _y) {
        lastY = y;
        y = _y;
    }
}
