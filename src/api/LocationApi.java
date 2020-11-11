package api;

/**
 *
 * @author sytiv
 */
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.IPGeolocationAPI;

public class LocationApi {

    protected static final String API_KEY = "18da31d005e94d3c84fe2cf81d79f114";
    private static final IPGeolocationAPI API = new IPGeolocationAPI(API_KEY);
    protected static final GeolocationParams geoParams = new GeolocationParams();
    protected static final Geolocation geolocation = API.getGeolocation(geoParams);

    //Returns specified info on the users location when given a string
    protected static String getLocationInfo(String _placeInfo) {
        String toLowerCase = _placeInfo.toLowerCase();
        switch (toLowerCase) {
            case "ip":
                return geolocation.getIPAddress();
            case "latitude":
                return geolocation.getLatitude();
            case "longitude":
                return geolocation.getLongitude();
            case "countryname":
                return geolocation.getCountryName();
            case "stateorprov":
                return geolocation.getStateProvince();
            case "city":
                return geolocation.getCity();
            case "zipcode":
                return geolocation.getZipCode();
            case "timezone":
                return geolocation.getTimezone().toString();
        }
        return "Invalid Request";
    }
}
