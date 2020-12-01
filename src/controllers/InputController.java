package controllers;

import models.InputModel;

/**
 * @author Logan
 * @lastModified 11/4/2020
 *
 * @description A abstract class to be inherited by the various inputController
 * types.
 */
public abstract class InputController {

    protected static final InputModel model = InputModel.getInstance();

    public InputController() {
    }

    //=================================== GETTERS ===================================//
    // Return the current X position of the input model
    public double getX() {
        return this.model.getX();
    }

    // Return the current Y position of the input model
    public double getY() {
        return this.model.getY();
    }

    //=================================== SETTERS ===================================//
    // Calls to the inputModel to set a new x position.
    // @param _x    The new x position
    public void setX(double _x) {
        this.model.setX(_x);
    }

    // Calls to the inputModel to set a new y position.
    // @param _y    The new y position
    public void setY(double _y) {
        this.model.setY(_y);
    }

    // Calls to the inputModel to set a new x and y position. This is a helper method to set both at once.
    // @param _x    The new x position
    // @param _y    The new y position
    public void setXY(double _x, double _y) {
        this.setX(_x);
        this.setY(_y);
    }
}
