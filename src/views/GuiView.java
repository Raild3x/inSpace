package views;

import listeners.HoverListener;
import listeners.SelectedListener;
import controllers.CelestialBodyController;
import controllers.GuiController;
import events.HoverEvent;
import events.SelectedEvent;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.HBox;

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
    private final Button zoomButton;
    private Label loading;
    private Label sunrise;
    private Label sunset;
    private Label date;
    HBox popUpButtons;

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
        this.zoomButton = new Button();
        this.infoPane = new VBox();
        this.loading = new Label(" ");
        this.popUpButtons = new HBox();
        this.sunrise = new Label();;
        this.sunset = new Label();;
        this.date = new Label();;

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

        final Label appName = new Label();
        appName.setText("inSpace");
        appName.setStyle("-fx-text-fill : white; -fx-opacity : 0.3;");

        appName.setAlignment(Pos.TOP_CENTER);
        appName.setTranslateY(-395);
        appName.setFont(Font.font(30));
        this.guiController.addGuiObject(appName);

        this.loading.setText("Loading...");
        this.loading.setAlignment(Pos.CENTER);
        this.loading.setFont(Font.font(30));
        this.loading.setStyle("-fx-text-fill : white; -fx-opacity : 0.8;");
        this.guiController.addGuiObject(this.loading);

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
        this.title.setStyle("-fx-text-fill : silver;");
        this.title.isWrapText();
        this.title.setOpacity(.8);
        this.title.setFont(Font.font(35));

        this.info.setAlignment(Pos.CENTER);
        this.info.setStyle("-fx-text-fill : ghostwhite;  -fx-opacity : 1;");
        this.info.setFont(Font.font(15));
        Color col = Color.SILVER;
        // corner = new CornerRadii(10);
        //Background background = new Background(new BackgroundFill(col, corner, Insets.EMPTY));
        //info.setBackground(background);
        info.setStyle(" -fx-background-color: lightgrey;");

        this.close.setText("Close");
        this.close.setAlignment(Pos.BOTTOM_CENTER);
        this.close.setStyle("-fx-text-fill : black;");
        this.close.setStyle("-fx-background-color : lightgray;");

        this.zoomButton.setText("Zoom to Planet");
        this.zoomButton.setAlignment(Pos.BOTTOM_CENTER);
        this.zoomButton.setStyle("-fx-text-fill : black;");
        this.zoomButton.setStyle("-fx-background-color : lightgray;");

        this.popUpButtons.getChildren().addAll(this.zoomButton, this.close);
        this.popUpButtons.setAlignment(Pos.CENTER);
        this.popUpButtons.setSpacing(6);

        //this.infoPane.setStyle("-fx-background-color : silver;");
        this.infoPane.setTranslateX(450);
        this.infoPane.setTranslateY(0);
        this.infoPane.setMaxSize(this.guiController.getCanvas().getWidth() / 3, this.guiController.getCanvas().getHeight() - 250);
        this.infoPane.setOpacity(1);
        this.infoPane.setAlignment(Pos.CENTER);
        this.infoPane.getChildren().addAll(this.title, this.info, popUpButtons);
        this.infoPane.setSpacing(30.0);

        //get sun rise and set info based on current IP and display it to user
        this.date.setText("Today's Date is " + guiController.getSunMoonRiseAdapter("date") + ".");
        this.sunrise.setText("Sunrise will occur at " + guiController.getSunMoonRiseAdapter("sunrise") + " hours today,");
        this.sunset.setText("and Sunset will occur at " + guiController.getSunMoonRiseAdapter("sunset") + " hours today.");
        date.setFont(Font.font(16));
        sunrise.setFont(Font.font(16));
        sunset.setFont(Font.font(16));

        this.guiController.addGuiObject(this.date);
        this.guiController.addGuiObject(this.sunrise);
        this.guiController.addGuiObject(this.sunset);

        this.date.setTranslateX(-400);
        this.date.setTranslateY(-390);
        this.date.setAlignment(Pos.TOP_LEFT);
        this.date.setStyle("-fx-text-fill : white;   -fx-opacity : 0.7;");
        // this.date.setStyle("-fx-background-color : lightgray;");

        this.sunrise.setTranslateX(-400);
        this.sunrise.setTranslateY(-370);
        this.sunrise.setAlignment(Pos.TOP_LEFT);
        this.sunrise.setStyle("-fx-text-fill : white;   -fx-opacity : 0.7;");
        //this.sunrise.setStyle("-fx-background-color : lightgray;");

        this.sunset.setTranslateX(-400);
        this.sunset.setTranslateY(-350);
        this.sunset.setAlignment(Pos.TOP_LEFT);
        this.sunset.setStyle("-fx-text-fill : white;   -fx-opacity : 0.7;");
        //this.sunset.setStyle("-fx-background-color : lightgray;");

    }

    //======================================== EVENT RECIEVERS ===========================================//
    @Override
    public void Selected(CelestialBodyController cbc) {

        //testing print statement
        System.out.println("Selected: " + cbc.getName());
        guiController.zoomPlanet(cbc.getName());

        this.title.setText(cbc.getName());
        Thread loadData = new Thread() {
            public void run() {

                mass = cbc.getInfo("mass");
                inclination = cbc.getInfo("inclination");
                radius = cbc.getInfo("meanRadius");
                density = cbc.getInfo("density");
                gravity = cbc.getInfo("gravity");
                axialTilt = cbc.getInfo("axialTilt");
                eccentricity = cbc.getInfo("eccentricity");

                //notifyAll();
            }
        };
        loadData.start();
        this.info.setText("   \n  Eccentricity: " + eccentricity + "   \n\n  Inclination: "
                + inclination + "   \n\n  Radius: " + radius
                + "   \n\n  Density: " + density + "   \n\n  Gravity: " + gravity
                + "   \n\n  Axial Tilt: " + axialTilt + "\n\n  Mass: " + mass + "   \n ");

        try {
            this.guiController.getStackPane().getChildren().remove(loading);
        } catch (URISyntaxException ex) {
            Logger.getLogger(GuiView.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        this.guiController.removeGuiObject(this.infoPane);
        this.guiController.addGuiObject(this.infoPane);

        try {

            if (cbc.isPlanet() && !guiController.getStackPane().getChildren().contains(this.zoomButton)) {
                //guiController.addGuiObject(this.zoomButton);
                this.zoomButton.setVisible(true);
            } else {
                //guiController.removeGuiObject(this.zoomButton);
                this.zoomButton.setVisible(false);

            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(GuiView.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        this.zoomButton.setOnAction(e -> {
            cbc.zoomIn();
        });

        this.close.setOnAction(e -> {
            this.guiController.removeGuiObject(this.infoPane);
            guiController.recenter();

        });
    }

    @Override
    public void UnSelected(CelestialBodyController cbc) {
        System.out.println("Unselected: " + cbc.getName());

        this.guiController.removeGuiObject(this.infoPane);

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
