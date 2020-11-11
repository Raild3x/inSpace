/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import controllers.CelestialBodyController;
import java.util.EventListener;

/**
 *
 * @author Logan
 */
public interface SelectedListener extends EventListener {
    public void Selected(CelestialBodyController cbc);
    public void UnSelected(CelestialBodyController cbc);
}
