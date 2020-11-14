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
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
    private final VBox infoPane;
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
        this.title = new Label();
        this.info = new Label();
        this.close = new Button();
        this.infoPane = new VBox();

        this.init();
    }

    /*
     * GuiView getInstance() returns the singleton instance of the GuiView and creates one if it does not yet exist
     */
    public static GuiView getInstance() {
        if (instance == null) {
            instance = new GuiView();
        }
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

//        DropShadow ds = new DropShadow();
//        ds.setOffsetY(3.0f);
//        ds.setColor(Color.BLACK);
//        final Text inSpace = new Text();
//        inSpace.setEffect(ds);
//        inSpace.setCache(true);        
//        
//        inSpace.setTextAlignment(TextAlignment.CENTER);
//        inSpace.setY(-395.0f);
//        inSpace.setFill(Color.GHOSTWHITE);
//        inSpace.setText("inSpace");
//       inSpace.setOpacity(0.3);
        final Label appName = new Label();
        appName.setText("inSpace");
        appName.setStyle("-fx-text-fill : white; -fx-opacity : 0.3;");

        appName.setAlignment(Pos.TOP_CENTER);
        appName.setTranslateY(-395);
        appName.setFont(Font.font(30));
        this.guiController.addGuiObject(appName);

        this.zoomLabel.setStyle("-fx-text-fill : white; -fx-opacity : 0.3;");
        this.zoomLabel.setTranslateX(600);
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

        //===============================info window components===============================
        this.title.setAlignment(Pos.TOP_CENTER);
        this.title.setStyle("-fx-text-fill : black;");
        this.title.isWrapText();
        this.title.setOpacity(1);

        this.title.setFont(Font.font(35));

        this.info.setAlignment(Pos.CENTER);
        this.info.setStyle("-fx-text-fill : black;");
        this.info.setFont(Font.font(20));

        this.close.setText("Close");
        this.close.setAlignment(Pos.BOTTOM_CENTER);
        this.close.setStyle("-fx-text-fill : black;");
        this.close.setStyle("-fx-background-color : grey;");

        this.infoPane.setStyle("-fx-background-color : silver;");
        this.infoPane.setTranslateX(450);
        this.infoPane.setTranslateY(0);
        this.infoPane.setMaxSize(this.guiController.getCanvas().getWidth() / 4, this.guiController.getCanvas().getHeight() - 100);
        this.infoPane.setOpacity(0.4);
        this.infoPane.setAlignment(Pos.CENTER);
        this.infoPane.getChildren().addAll(this.title, this.info, this.close);
        this.infoPane.setSpacing(70.0);
        
    }

    //======================================== EVENT RECIEVERS ===========================================//
    @Override
    public void Selected(CelestialBodyController cbc) {

        //===============================Code for Planet Info Windows================================================
        System.out.println("Selected: " + cbc.getName());
        guiController.zoomPlanet(cbc.getName());

        this.title.setText(cbc.getName());
        this.info.setText("Information on " + cbc.getName());
        this.guiController.addGuiObject(this.infoPane);
        ;
        close.setOnAction(e -> {
            this.guiController.removeGuiObject(this.infoPane);
            guiController.recenter("Sun");

        });
    }

    @Override
    public void UnSelected(CelestialBodyController cbc) {
        System.out.println("Unselected: " + cbc.getName());

        this.guiController.removeGuiObject(this.infoPane);
        guiController.recenter("Sun");

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
