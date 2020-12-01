package views;

import controllers.MouseInputController;
import controllers.GuiController;
import interfaces.InputView;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

/**
 * @author Logan
 * @lastModified 12/1/2020
 *
 * @description The MouseView class acts to retrieve all mouse related input and
 * pass it to the MouseInputController for handling.
 */
public class MouseView implements InputView {

    public static void init() {
        Canvas screen = GuiController.getInstance().getCanvas();
        MouseInputController mic = MouseInputController.getInstance();
        //set response to mouse events
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
