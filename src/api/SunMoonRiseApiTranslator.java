package api;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sytiva
 */
public class SunMoonRiseApiTranslator extends APIConnect implements SunMoonRiseApiInterface {

    private static final String ASTRONOMY_URL = "https://api.ipgeolocation.io/astronomy?apiKey=";
    private static final String API_KEY = "18da31d005e94d3c84fe2cf81d79f114";
    private final LocationApiAdapter LOCATIONDATA = new LocationApiAdapter();
    private final String LAT = LOCATIONDATA.getLocationInfo("Latitude");
    private final String LON = LOCATIONDATA.getLocationInfo("Longitude");
    private static JSONObject OBJ;

    /**
     * Returns time expected for sunrise/set and moon rise/set based on the
     * users location. Uses IPGEOLocation API. Strings allowed can be
     * found in the Info Accessible through API google doc.
     */
    public String getSunMoonInfo(String _event) {
        String url = ASTRONOMY_URL + API_KEY + "&lat=" + LAT + "&long=" + LON;
        String param = _event.toLowerCase();
        OBJ = getConnection(url);

        try {
            if (OBJ.getString(param).equals("-:-")) {
                return "No set / rise for this date.";
            } else {
                return OBJ.getString(param);
            }
        } catch (JSONException ex) {
            return "Invalid Params. Check spelling";
        }
    }
}
