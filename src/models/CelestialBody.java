package models;

import api.AstroApiAdapter;
import controllers.GuiController;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.RenderService;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import services.PlanetService;

/**
 * @author Logan
 * @lastModified 12/1/2020
 *
 * @description The CelestialBody class acts as an object to represent various
 * bodies in space such as stars, planets and moons.
 */
public class CelestialBody {

    final AstroApiAdapter AstroApi = new AstroApiAdapter();

    // Initialize at a random angle
    private double angle = Math.random() * (Math.PI * 2);

    // Properties
    private ImageView imageView;
    private final double semiMajorAxis, semiMinorAxis, eccentricity;
    public final String apiName;
    public final String name;
    public final double size;
    public final double orbitalPeriod;
    public final Color color;
    public final boolean isPlanet;
    public final CelestialBody orbitingBody;
    public final ArrayList<String> moons;

    private double x = 0;
    private double y = 0;

    public boolean displayOrbit = true;
    public boolean boldOrbit = false;
    public boolean realisticSize = false;

    /**
     * Constructor for CelestialBody object.
     * @param _apiName      The string name to be used with the AstroApi to get info.
     * @param _orbitingBody The CelestialBody that this one orbits.
     * @param _color        The default color of the planet if the image wont load.
     */
    public CelestialBody(String _apiName, CelestialBody _orbitingBody, Color _color) {
        this.name = AstroApi.getBodyInfo(_apiName, "englishName");
        this.apiName = _apiName;
        this.size = Double.parseDouble(AstroApi.getBodyInfo(_apiName, "meanRadius"));
        this.semiMajorAxis = PlanetService.kmToAU(Double.parseDouble(AstroApi.getBodyInfo(_apiName, "semimajorAxis")));
        this.eccentricity = Double.parseDouble(AstroApi.getBodyInfo(_apiName, "eccentricity"));
        this.semiMinorAxis = Math.sqrt(Math.pow(this.semiMajorAxis, 2) * (1 - Math.pow(this.eccentricity, 2)));
        this.orbitalPeriod = Math.sqrt(Math.pow((this.semiMajorAxis + this.semiMinorAxis) / 2, 3));
        this.color = _color;
        this.isPlanet = AstroApi.getBodyInfo(this.name, "isPlanet").equals("true");
        this.orbitingBody = _orbitingBody;
        if (AstroApi.getBodyInfo(name, "isPlanet") == "true") {
            this.moons = AstroApi.getBodyMoons(name);
        } else {
            this.moons = null;
        }
        try {
            this.imageView = GuiController.getInstance().getImageView(this.name);
        } catch (URISyntaxException ex) {
            Logger.getLogger(CelestialBody.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Loaded: " + this.name);
    }

    /**
     * Constructor for moon based CelestialBody objects.
     * @param _apiName      The string name to be used with the AstroApi to get info.
     * @param _orbitingBody The CelestialBody that this one orbits.
     */
    public CelestialBody(String _apiName, CelestialBody _orbitingBody) {
        this.name = AstroApi.getBodyInfo(_apiName, "englishName");
        this.apiName = _apiName;
        this.size = Double.parseDouble(AstroApi.getBodyInfo(_apiName, "meanRadius"))*10;
        this.semiMajorAxis = PlanetService.kmToAU(Double.parseDouble(AstroApi.getBodyInfo(_apiName, "semimajorAxis")));
        this.eccentricity = 0; //Double.parseDouble(AstroApi.getBodyInfo(_apiName, "eccentricity"))*10;
        this.semiMinorAxis = (Math.sqrt(Math.pow(this.semiMajorAxis, 2) * (1 - Math.pow(this.eccentricity, 2))));
        this.orbitalPeriod = Math.sqrt(Math.pow((this.semiMajorAxis + this.semiMinorAxis) / 2, 3));
        this.color = Color.GRAY;
        this.orbitingBody = _orbitingBody;
        this.realisticSize = true;
        this.isPlanet = false;
        this.moons = null;
        try {
            this.imageView = GuiController.getInstance().getImageView(this.name);
        } catch (URISyntaxException ex) {
            Logger.getLogger(CelestialBody.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Loaded: " + this.name);
    }

    /**
     * Constructor for CelestialBody object without any orbital data (The Sun).
     * @param _apiName      The string name to be used with the AstroApi to get info.
     * @param _color        The default color of the planet if the image wont load.
     */
    public CelestialBody(String _apiName, Color _color) {
        this.name = AstroApi.getBodyInfo(_apiName, "englishName");
        this.apiName = _apiName;
        this.size = Double.parseDouble(AstroApi.getBodyInfo(_apiName, "meanRadius")) / 500;
        this.color = _color;
        this.orbitingBody = null;
        this.semiMajorAxis = 0;
        this.semiMinorAxis = 0;
        this.eccentricity = 0;
        this.orbitalPeriod = 1;
        this.displayOrbit = false;
        this.isPlanet = false;
        this.moons = null;
        try {
            this.imageView = GuiController.getInstance().getImageView(this.name);
        } catch (URISyntaxException ex) {
            Logger.getLogger(CelestialBody.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Moves the Celestial Body to the given angle along its orbit
     * @param _angle The given angle in degrees along which it should be in its orbit.
     */
    public void move(double _angle) {
        double zoom = RenderService.getInstance().getZoom();
        if (this.realisticSize)
            zoom *= 50;
        if (orbitingBody != null) {
            this.x = this.orbitingBody.getX() + zoom * this.semiMinorAxis * Math.sin(_angle) + this.eccentricity * zoom;
            this.y = this.orbitingBody.getY() - zoom * this.semiMajorAxis * Math.cos(_angle);
        } else {
            this.x = zoom * this.semiMinorAxis * Math.sin(_angle) + this.eccentricity * zoom;
            this.y = zoom * this.semiMajorAxis * Math.cos(_angle);
        }
    }

    /**
     * Called by RenderService to execute the code needed to adjust the CelestialBody to the new position in its orbit
     * @param _dt Time passed since last update
     */
    public void update(double _dt) {
        double delta = ((Math.PI * 2) / this.orbitalPeriod) / 1000;
        // if were focused on something other than the sun then slow down time
        if (!RenderService.getInstance().getFocus().getName().equals("Sun")) {
            delta /= 1500;
        }
        this.angle += delta;

        move(this.angle);
    }

    /**
     * Called by RenderService to execute the code needed to paint the planet and its orbit
     * @param _gc The GraphicsContext to paint on.
     */
    public void render(GraphicsContext _gc) {
        double zoom = RenderService.getInstance().getZoom();
        if (this.realisticSize)
            zoom *= 50;
        
        if (this.orbitingBody != null) {
            if (this.displayOrbit) {
                // draw orbit
                _gc.setStroke(Color.GRAY);
                double x = this.orbitingBody.getX() + (this.eccentricity * zoom) - (this.semiMajorAxis * zoom);
                double y = this.orbitingBody.getY() - (this.semiMinorAxis * zoom);
                if (this.boldOrbit) {
                    _gc.setLineWidth(2.0);
                }
                _gc.strokeOval(x, y, this.semiMajorAxis * zoom * 2, this.semiMinorAxis * zoom * 2);
                _gc.setLineWidth(1.0);
            }
        }
        // draw planet
        _gc.setFill(this.color);
        double size = this.getSize();
        if (!this.realisticSize && this.boldOrbit && !this.name.equals("Sun")) {
            size *= 3;
        }
        if (this.imageView == null || !this.name.equals("Sun")) {
            _gc.fillOval(this.x - size / 2, this.y - size / 2, size, size);
        }
        if (this.imageView != null) {
            double x = this.x - size / 2;
            double y = this.y - size / 2;
            if (this.name.equals("Saturn")) {
                size *= 1.967;
                x = this.x - size /1.9;
                y = this.y - size /1.9;
            }
            _gc.drawImage(this.imageView.getImage(), x, y, size, size);
        }
    }

    // Action method to zoom in to the planet.
    public void zoomIn() {
        RenderService.getInstance().setZoom(1700 - this.size/50);
        this.realisticSize = true;
        this.displayOrbit = false;
    }

    // Action method to add moons of CelestialBody to be rendered if it has any
    public void loadMoons() {
        if (this.moons == null) {
            return;
        }
        for (String moonName : this.moons) {
            System.out.println("Adding "+moonName);
            RenderService.getInstance().addInstance(PlanetService.getPlanetController(moonName));
        }
    }

    // Action method to remove moons of CelestialBody from rendering queue if it has any
    public void unloadMoons() {
        if (this.moons == null) {
            return;
        }
        for (String moonName : this.moons) {
            RenderService.getInstance().removeInstance(PlanetService.getPlanetController(moonName));
        }
    }

    //=================================== GETTERS ===================================//
    // Returns the adjusted size
    public double getSize() {
        double zoom = RenderService.getInstance().getZoom();
        double size = (this.realisticSize) ? PlanetService.kmToAU(this.size)*2 * (zoom * 50) : (this.size / 600) * (zoom / 350);
        return (!this.name.equals("Sun")) ? (size) : (this.size / 50) * (zoom / 350);
    }

    /**
     * Returns a double representing the distance from a given point to the closest point along the given planet's orbit.
     * @param _px Given point x coordinate.
     * @param _py Given point y coordinate.
     */
    public double getDistToOrbit(double _px, double _py) {
        if (orbitingBody == null) {
            return Integer.MAX_VALUE;
        }
        double zoom = RenderService.getInstance().getZoom();
        if (this.realisticSize)
            zoom *= 50;
        
        double x = this.orbitingBody.getX() + this.eccentricity * zoom;
        double y = this.orbitingBody.getY();
        double rx = this.semiMajorAxis * zoom;
        double ry = this.semiMinorAxis * zoom;

        double d1 = Math.sqrt(Math.pow(_px - x, 2) + Math.pow(_py - y, 2));
        double angle = Math.atan((_py - y) / (_px - x));
        double d2 = Math.sqrt(Math.pow(rx, 2) * Math.pow(Math.cos(angle), 2) + Math.pow(ry, 2) * Math.pow(Math.sin(angle), 2));
        return Math.abs(d1 - d2);
    }

    /**
     * Returns a double representing the distance from a given point to the planet in question in terms of screen space
     * @param _px Given point x coordinate.
     * @param _py Given point y coordinate.
     */
    public double getDistToPlanet(double _px, double _py) {
        return Math.sqrt(Math.pow(getX() - _px, 2) + Math.pow(getY() - _py, 2));
    }

    /**
     * Request info about the CelestialBody from AstroApi
     * @param _infoType A string of the property that you want to get from the api
     */
    public String getInfo(String _infoType) {
        return this.AstroApi.getBodyInfo(this.apiName, _infoType);
    }

    // Returns the x value of the CelestialBody's position
    public double getX() {
        return this.x;
    }

    // Returns the y value of the CelestialBody's position
    public double getY() {
        return this.y;
    }

    // Returns the body that this CelestialBody is orbiting if any (ex: null if the sun)
    public CelestialBody getOrbitingBody() {
        return this.orbitingBody;
    }

}
