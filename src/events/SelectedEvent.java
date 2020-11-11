/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import controllers.CelestialBodyController;
import listeners.SelectedListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author Logan
 */
public class SelectedEvent {
    private static Collection listeners = new HashSet();
    
    public static void addListener(SelectedListener listener) {
        listeners.add(listener);
    }
    
    public static void removeListener(SelectedListener listener) {
        listeners.remove(listener);
    }
    
    public static void fireSelected(CelestialBodyController cbc) {
        //HoverEvent event = new HoverEvent(this, "open");
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            SelectedListener listener = (SelectedListener) iter.next();
            listener.Selected(cbc);
        }
    }
    
    public static void fireUnSelected(CelestialBodyController cbc) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            SelectedListener listener = (SelectedListener) iter.next();
            listener.UnSelected(cbc);
        }
    }
}
