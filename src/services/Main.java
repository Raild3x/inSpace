package services;

import api.*;
import javafx.stage.Stage;
import javafx.application.Application;
import views.GuiView;
import views.MouseView;


/**
 * @author Logan, Taylor, Sytiva
 * @lastModified 12/1/2020
 * 
 * @descritpion The Main Class of The inSpace program. Calls to begin the program in the proper sequence.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Started in a new JavaFX thread and calls to the various services, controllers, and views to properly setup.
     * @param _stage A stage passed internally to the method
     * @throws Exception 
     */
    @Override
    public void start(Stage _stage) throws Exception {
        //Init models and services
        System.out.println(new AstroApiAdapter().getBodyInfo("Sun"));
        RenderService.getInstance(_stage);
        PlanetService.init();

        //Init view events
        MouseView.init();
        GuiView.getInstance();

        System.out.println("inSpace started!");
    }

}
