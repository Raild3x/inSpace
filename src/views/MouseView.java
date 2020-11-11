/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.MouseInputController;
import controllers.GuiController;
import controllers.Signal;
import interfaces.InputView;
import services.PlanetService;
import services.RenderService;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Logan
 */
public class MouseView implements InputView {
    
    public static void init() {
        Canvas screen = GuiController.getInstance().getCanvas();
        MouseInputController mic = MouseInputController.getInstance();
        //set response to mouse events
        screen.setOnMouseClicked((MouseEvent event) -> {
            mic.mouseClicked(event);
        });
        screen.setOnMouseDragged((MouseEvent event) -> {
            mic.mouseDragged(event);
        });
        screen.setOnMousePressed((MouseEvent event) -> {
            mic.mousePressed(event);
        });
        screen.setOnMouseReleased((MouseEvent event) -> {
            mic.mouseReleased(event);
        });
        screen.setOnMouseMoved((MouseEvent event) -> {
            mic.mouseMoved(event);
        });
    }
}
