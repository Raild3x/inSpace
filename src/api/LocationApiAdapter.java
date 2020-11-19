/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

/**
 *
 * @author sytiv
 */
public class LocationApiAdapter {

    LocationApiInterface LocationAdapter = new IPInfoApiTranslator();

    public String getLocationInfo(String _placeInfo) {
        return LocationAdapter.getLocationInfo(_placeInfo);
    }

}
