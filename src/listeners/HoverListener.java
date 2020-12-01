package listeners;

import controllers.CelestialBodyController;
import java.util.EventListener;

/**
 * @author Logan
 * @lastModified 10/25/2020
 *
 * @description The HoverListener interface describes the methods implemented
 * when a hover event is triggered
 */
public interface HoverListener extends EventListener {

    public void HoverBegan(CelestialBodyController cbc);

    public void HoverEnded(CelestialBodyController cbc);
}
