package api;

/**
 *
 * @author sytiva
 */
public class LocationApiAdapter {

    LocationApiInterface LocationAdapter = new IPInfoApiTranslator();

    public String getLocationInfo(String _placeInfo) {
        return LocationAdapter.getLocationInfo(_placeInfo);
    }

}
