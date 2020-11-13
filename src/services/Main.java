package services;

/**
 *
 * @author Logan, Taylor, Sytiva
 */
import api.AstroApi;
import javafx.stage.Stage;
import javafx.application.Application;
//import Api.*;
import views.GuiView;
import views.MouseView;


public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage _stage) throws Exception {
        //Init models and services
        RenderService.getInstance(_stage);
        PlanetService.init();
        
        //Init view events
        MouseView.init();
        GuiView.getInstance();
        
        //LocationApi.Test();
        //System.out.println(getLocationInfo("latitude"));
        System.out.println(AstroApi.getBodyInfo("lune"));
        System.out.println("inSpace started!");
    }

}