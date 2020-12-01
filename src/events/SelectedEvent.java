package events;

import controllers.CelestialBodyController;
import listeners.SelectedListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Logan
 * @lastModified 10/25/2020
 *
 * @description The SelectedEvent class manages the firing of the methods of any
 * connected SelectedListeners
 */
public class SelectedEvent {

    private static Collection listeners = new HashSet();

    // Adds a listener to the list for execution.
    // @param _listener The listener to be added.
    public static void addListener(SelectedListener _listener) {
        listeners.add(_listener);
    }

    // Removes a listener from the list.
    // @param _listener The listener to be removed.
    public static void removeListener(SelectedListener _listener) {
        listeners.remove(_listener);
    }

    // Called from PlanetService to fire any connected listeners Selected methods.
    // @param _cbc The CelestialBodyController which was selected.
    public static void fireSelected(CelestialBodyController _cbc) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            SelectedListener listener = (SelectedListener) iter.next();
            listener.Selected(_cbc);
        }
    }

    // Called from PlanetService to fire any connected listeners UnSelected methods.
    // @param _cbc The CelestialBodyController which was unselected.
    public static void fireUnSelected(CelestialBodyController _cbc) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            SelectedListener listener = (SelectedListener) iter.next();
            listener.UnSelected(_cbc);
        }
    }
}
