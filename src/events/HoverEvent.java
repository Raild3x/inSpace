package events;

import controllers.CelestialBodyController;
import java.util.EventObject;
import listeners.HoverListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Logan
 * @lastModified 10/25/2020
 *
 * @description The HoverEvent class manages the firing of the methods of any
 * connected HoverListeners
 */
public class HoverEvent {

    private static Collection listeners = new HashSet();

    // Adds a listener to the list for execution.
    // @param _listener The listener to be added.
    public static void addListener(HoverListener _listener) {
        listeners.add(_listener);
    }

    // Removes a listener from the list.
    // @param _listener The listener to be removed.
    public static void removeListener(HoverListener _listener) {
        listeners.remove(_listener);
    }

    // Called from PlanetService to fire any connected listeners HoverBegan
    // @param _cbc The CelestialBodyController which was hovered over.
    public static void fireHoverBegan(CelestialBodyController _cbc) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            HoverListener listener = (HoverListener) iter.next();
            listener.HoverBegan(_cbc);
        }
    }

    // Called from PlanetService to fire any connected listeners HoverEnded
    // @param _cbc The CelestialBodyController which is no longer being hovered over.
    public static void fireHoverEnded(CelestialBodyController _cbc) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            HoverListener listener = (HoverListener) iter.next();
            listener.HoverEnded(_cbc);
        }
    }

}
