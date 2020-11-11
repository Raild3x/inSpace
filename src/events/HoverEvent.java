package events;

import controllers.CelestialBodyController;
import java.util.EventObject;
import listeners.HoverListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author Logan
 */
public class HoverEvent {
    
    /*public HoverEvent(Object source, String state) {
        super(source);
    }*/
    
    private static Collection listeners = new HashSet();
    
    public static void addListener(HoverListener listener) {
        listeners.add(listener);
    }
    
    public static void removeListener(HoverListener listener) {
        listeners.remove(listener);
    }
    
    public static void fireHoverBegan(CelestialBodyController cbc) {
        //HoverEvent event = new HoverEvent(this, "open");
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            HoverListener listener = (HoverListener) iter.next();
            listener.HoverBegan(cbc);
        }
    }
    
    public static void fireHoverEnded(CelestialBodyController cbc) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            HoverListener listener = (HoverListener) iter.next();
            listener.HoverEnded(cbc);
        }
    }
    
}
