/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Logan
 */
import api.AstroApiAdapter;
import services.RenderService;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CelestialBody {
    
    final AstroApiAdapter AstroApi = new AstroApiAdapter();

    // Test Vars
    private double angle = 0;
    
    // Properties
    private final double A, B, C;
    public final String name;
    public final double size;
    public final Color color;
    public final CelestialBody orbitingBody;
    
    private double x = 0;
    private double y = 0;

    public boolean displayOrbit = true;
    public boolean boldOrbit = false;

    /*
    Constructor for CelestialBody object.
    */
    public CelestialBody (String _name, double _size, CelestialBody _orbitingBody, double _A, double _B, double _C, Color _color) {
        this.name = _name;
        this.size = _size;
        this.color = _color;
        this.orbitingBody = _orbitingBody;
        this.A = _A;
        this.B = _B;
        this.C = _C;
    }
    /*
    Constructor for CelestialBody object without any orbital data.
    */
    public CelestialBody (String _name, double _size, Color _color) {
        this.name = _name;
        this.size = _size;
        this.color = _color;
        this.orbitingBody = null;
        this.A = 0;
        this.B = 0;
        this.C = 0;
    }
    
    /*
    Moves the Celestial Body to the given angle along its orbit
    @param _angle The given angle in degrees along which it should be in its orbit.
    */
    public void move(double _angle){
        double zoom = RenderService.getInstance().getZoom();
        if (orbitingBody != null){
            this.x = this.orbitingBody.getX() + zoom * this.B * Math.sin(_angle) + this.C * zoom;
            this.y = this.orbitingBody.getY() - zoom * this.A * Math.cos(_angle);
        } else {
            this.x = zoom * this.B * Math.sin(_angle) + this.C * zoom;
            this.y = zoom * this.A * Math.cos(_angle);
        }
        
        
    }

    public void update(double _dt){
        this.angle += 0.001;
        move(this.angle);
    }
    
    /*
    Called by RenderService to execute the code needed to paint the planet and its orbit
    @param _gc The GraphicsContext to paint on.
    */
    public void render(GraphicsContext _gc) {
        double zoom = RenderService.getInstance().getZoom();
        if (this.orbitingBody != null) {
            if (this.displayOrbit) {
                // draw orbit
                _gc.setStroke(Color.GRAY);
                double x  = this.orbitingBody.getX() + this.C * zoom - (this.A * zoom);
                double y = this.orbitingBody.getY() - (this.B * zoom);
                if (this.boldOrbit)
                    _gc.setLineWidth(2.0);
                _gc.strokeOval(x, y, this.A * zoom * 2, this.B * zoom * 2);
                _gc.setLineWidth(1.0);
            }
        }
        // draw planet
        _gc.setFill(this.color);
        double size = this.size/3*(zoom/350);
        if (this.boldOrbit && this.name != "Sun")
            size *= 3;
        _gc.fillOval(this.x-size/2,this.y-size/2,size,size);
    }
    
    /*
    Returns a double representing the distance from a given point to the closest point along the given planet's orbit.
    @param _px Given point x coordinate.
    @param _py Given point y coordinate.
    */
    public double getDistToOrbit(double _px, double _py) {
        if (orbitingBody == null)
            return Integer.MAX_VALUE;
        double zoom = RenderService.getInstance().getZoom();
        double x  = this.orbitingBody.getX() + this.C * zoom;
        double y = this.orbitingBody.getY();
        double rx = this.A * zoom;
        double ry = this.B * zoom;
        
        double d1 = Math.sqrt(Math.pow(_px - x, 2) + Math.pow(_py - y, 2));
        double angle = Math.atan((_py - y) / (_px - x));
        double d2 = Math.sqrt(Math.pow(rx, 2) * Math.pow(Math.cos(angle), 2) + Math.pow(ry, 2) * Math.pow(Math.sin(angle), 2));
        return Math.abs(d1 - d2);
    }
    
    /*
    Returns a double representing the distance from a given point to the planet in question in terms of screen space
    @param _px Given point x coordinate.
    @param _py Given point y coordinate.
    */
    public double getDistToPlanet(double _px, double _py) {
        return Math.sqrt(Math.pow(getX() - _px, 2) + Math.pow(getY() - _py, 2));
    }
    
    //=================================== GETTERS ===================================//
    public String getInfo(String _infoType) {
        return this.AstroApi.getBodyInfo(this.name, _infoType);
    }
    
    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public CelestialBody getOrbitingBody(){ return this.orbitingBody; }
}
