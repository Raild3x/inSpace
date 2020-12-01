package api;

/**
 *
 * @author sytiva
 */
public class LocationApiAdapter {

    private final LocationApiInterface locationAdapter = new IPInfoApiTranslator();

    public String getLocationInfo(String _placeInfo) {
        return locationAdapter.getLocationInfo(_placeInfo);
    }

}
