/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Logan
 */
public class KeyboardInputController extends InputController {
     private static KeyboardInputController instance;
    
    private KeyboardInputController() {
        super();
    }
    
    public static KeyboardInputController getInstance() {
        if (instance == null) {
            instance = new KeyboardInputController();
        }
        return instance;
    }
    
    public void keyPressed(){
        
    }
}
