package views;

import listeners.HoverListener;
import listeners.SelectedListener;
import controllers.CelestialBodyController;
import controllers.GuiController;
import events.HoverEvent;
import events.SelectedEvent;
import javafx.geometry.Pos;
import services.RenderService;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

/**
 *
 * @author szoor
 */
public class GuiView implements HoverListener, SelectedListener {

    private final GuiController guiController;
    private static GuiView instance;

    // Gui Objects
    private final Label planetNameLabel;
    private final Label zoomLabel;
    private final VBox infoPane;
    private final Label title;
    private final Label info;
    private final Button close;

    //info strings
    String mass;
    String inclination;
    String radius;
    String density;
    String gravity;
    String axialTilt;
    String eccentricity;

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
        this.title.setAlignment(Pos.CENTER);
        this.title.setStyle("-fx-text-fill : white;");
        this.title.isWrapText();
        this.title.setOpacity(1);
        this.title.setFont(Font.font(35));

        this.info.setAlignment(Pos.CENTER);
        this.info.setStyle("-fx-text-fill : black;");
        this.info.setFont(Font.font(14));
        Color col = Color.SILVER;
        CornerRadii corner = new CornerRadii(10);
        Background background = new Background(new BackgroundFill(col, corner, Insets.EMPTY));
        info.setBackground(background);

        this.close.setText("Close");
        this.close.setAlignment(Pos.BOTTOM_CENTER);
        this.close.setStyle("-fx-text-fill : black;");
        this.close.setStyle("-fx-background-color : grey;");

        //this.infoPane.setStyle("-fx-background-color : silver;");
        this.infoPane.setTranslateX(450);
        this.infoPane.setTranslateY(0);
        this.infoPane.setMaxSize(this.guiController.getCanvas().getWidth() / 4, this.guiController.getCanvas().getHeight() - 290);
        this.infoPane.setOpacity(0.5);
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

        Thread thread = new Thread() {
            public void run() {
                mass = cbc.getInfo("mass");
                inclination = cbc.getInfo("inclination");
                radius = cbc.getInfo("meanRadius");
                density = cbc.getInfo("density");
                gravity = cbc.getInfo("gravity");
                axialTilt = cbc.getInfo("axialTilt");
                eccentricity = cbc.getInfo("eccentricity");
            }
        };
        thread.start();

        this.info.setText("\n  Mass: " + mass + "  \n\n  Inclination: "
                + inclination + "\n\n  Radius: " + radius
                + "\n\n  Density: " + density + "\n\n  Gravity: " + gravity
                + "\n\n  Axial Tilt: " + axialTilt + "\n\n  Eccentricity: " + eccentricity + "\n ");

//        this.info.setText("\n  Mass: " + cbc.getInfo("mass") + "  \n\n  Inclination: "
//                + cbc.getInfo("inclination") + "\n\n  Radius: " + cbc.getInfo("meanRadius")
//                + "\n\n  Density: " + cbc.getInfo("density") + "\n\n  Gravity: " + cbc.getInfo("gravity")
//                + "\n\n  Axial Tilt: " + cbc.getInfo("axialTilt") + "\n\n  Eccentricity: " + cbc.getInfo("eccentricity") + "\n ");
        this.guiController.addGuiObject(this.infoPane);

        close.setOnAction(e -> {
            this.guiController.removeGuiObject(this.infoPane);
            guiController.recenter();

        });
    }

    @Override
    public void UnSelected(CelestialBodyController cbc) {
        System.out.println("Unselected: " + cbc.getName());

        this.guiController.removeGuiObject(this.infoPane);
        //guiController.recenter();

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
