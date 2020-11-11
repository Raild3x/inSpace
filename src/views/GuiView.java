
package views;

import listeners.HoverListener;
import listeners.SelectedListener;
import controllers.CelestialBodyController;
import controllers.GuiController;
import events.HoverEvent;
import events.SelectedEvent;
import javafx.geometry.Pos;
import services.PlanetService;
import services.RenderService;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author szoor
 */
public class GuiView implements HoverListener, SelectedListener {

    protected final GuiController guiController;
    protected static GuiView instance;
    
    // Gui Objects
    private final Label planetNameLabel;
    private final Label zoomLabel;
    private final Pane planetPane;
    private final Label title;
    private final Label info;
    private final Button close;
    
    /*
     * Private constructor for GuiView object.
    */
    private GuiView() {
        this.guiController = GuiController.getInstance();
        
        this.planetNameLabel = new Label();
        this.zoomLabel = new Label();
        this.planetPane = new Pane();
        this.title = new Label();
        this.info = new Label();
        this.close = new Button("Close Info");
        
        this.init();
    }
    
    /*
     * GuiView getInstance() returns the singleton instance of the GuiView and creates one if it does not yet exist
    */
    public static GuiView getInstance() {
        if (instance == null)
            instance = new GuiView();
        return instance;
    }
    
    public void init() {
        HoverEvent.addListener(this);
        SelectedEvent.addListener(this);
        initGui();
    }

    /*
     * Organizational method for setting up all the gui properties
    */
    public void initGui() {
        final Label appName = new Label("inSpace");
        appName.setStyle("-fx-text-fill : white; -fx-opacity : 0.3;");
        appName.setAlignment(Pos.TOP_CENTER);
        appName.setTranslateY(-395);
        appName.setFont(Font.font(30));
        this.guiController.addGuiObject(appName);

        this.zoomLabel.setStyle("-fx-text-fill : white; -fx-opacity : 0.3;");
        this.zoomLabel.setTranslateX(625);
        this.zoomLabel.setTranslateY(-400);
        this.zoomLabel.setFont(Font.font(15));

        RenderService.PostRenderstep.Connect(dt -> {
            zoomLabel.setText("ZOOM: " + Double.toString(Math.ceil(RenderService.getInstance().getZoom() * 10) / 10));
        });
        this.guiController.addGuiObject(this.zoomLabel);
        
        this.planetNameLabel.setTranslateX(-400);
        this.planetNameLabel.setAlignment(Pos.CENTER);
        this.planetNameLabel.setFont(Font.font(40));
        this.planetNameLabel.setStyle("-fx-text-fill : white; -fx-opacity : 0.5;");


        //===============================Code for Planet Info Windows================================================
        
        this.planetPane.setStyle("-fx-background-color : #2f4f4f;");
        this.planetPane.setTranslateX(500);
        this.planetPane.setTranslateY(-200);
        this.title.setAlignment(Pos.CENTER);
        this.info.setAlignment(Pos.CENTER_LEFT);
        this.planetPane.getChildren().addAll(this.title, this.info, this.close);

    }
    
    //======================================== EVENT RECIEVERS ===========================================//

    @Override
    public void Selected(CelestialBodyController cbc) {
        if (cbc.getName() == "Sun")
            return;
        System.out.println("Selected: " + cbc.getName());
        this.title.setText(cbc.getName());
        this.info.setText("Information on " + cbc.getName());
        this.planetPane.setOpacity(1);
        this.guiController.addGuiObject(this.planetPane);
        close.setOnAction(e -> {
            this.guiController.removeGuiObject(this.planetPane);
            //guiController.getRenderService().setZoom(10);
            cbc.boldOrbit(false);
       });
    }

    @Override
    public void UnSelected(CelestialBodyController cbc) {
        System.out.println("Unselected: " + cbc.getName());
        this.guiController.removeGuiObject(this.planetPane);
        cbc.boldOrbit(false);
    }

    @Override
    public void HoverBegan(CelestialBodyController cbc) {
        //System.out.println("Began hovering over: " + cbc.getName());
        this.planetNameLabel.setText(cbc.getName());
        this.guiController.addGuiObject(planetNameLabel);
    }

    @Override
    public void HoverEnded(CelestialBodyController cbc) {
        //System.out.println("Stopped hovering over: " + cbc.getName());
        this.guiController.removeGuiObject(planetNameLabel);
    }
}