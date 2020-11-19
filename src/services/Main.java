package services;

/**
 *
 * @author Logan, Taylor, Sytiva
 */
import api.*;
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
        /*System.out.println("AstoApi Testing:");
        System.out.println(new AstroApiAdapter().getBodyInfo("AitnÃ©"));
        System.out.println(new AstroApiAdapter().getBodyInfo("S/2003 J 16"));
        System.out.println(new AstroApiAdapter().getBodyInfo("Io"));
        System.out.println(new AstroApiAdapter().getBodyInfo("io"));
        System.out.println(new AstroApiAdapter().getBodyInfo("ganymede"));*/
        System.out.println("inSpace started!");
    }

}