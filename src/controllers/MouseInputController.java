/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import services.PlanetService;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Logan
 */
public class MouseInputController extends InputController {
    private static MouseInputController instance;
    
    private static long lastClickTime;
    
    private MouseInputController() {
        super();
        this.lastClickTime = System.currentTimeMillis();
    }
    
    public static MouseInputController getInstance() {
        if (instance == null) {
            instance = new MouseInputController();
        }
        return instance;
    }
    
    public void mouseMoved(MouseEvent e) {
        setXY(e.getX(), e.getY());
    }
    
    public void mouseDragged(MouseEvent e) {
        setXY(e.getX(), e.getY());
        model.drag();
    }
    
    public void mouseClicked(MouseEvent e) {
        //setXY(e.getX(), e.getY());
        
    }
    
    public void mousePressed(MouseEvent e) {
        setXY(e.getX(), e.getY());
        this.lastClickTime = System.currentTimeMillis();
    }
    
    public void mouseReleased(MouseEvent e) {
        setXY(e.getX(), e.getY());
        if (System.currentTimeMillis() < this.lastClickTime + 200) {
            //System.out.println("Mouse released in time; time left: "+(200-(System.currentTimeMillis()-this.lastClickTime)));
            PlanetService.focusNearestPlanet();
        }
            
    }
}
