package listeners;

import controllers.CelestialBodyController;
import java.util.EventListener;

/**
 * @author Logan
 * @lastModified 10/25/2020
 *
 * @description The SelectedListener interface describes the methods implemented
 * when the SelectedEvent is triggered.
 */
public interface SelectedListener extends EventListener {
    public void Selected(CelestialBodyController cbc);
    public void UnSelected(CelestialBodyController cbc);
}
