package api;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sytiva
 */
public class SunMoonRiseApiTranslator extends APIConnect implements SunMoonRiseApiInterface {

    private static final String astronomyUrl = "https://api.ipgeolocation.io/astronomy?apiKey=";
    private static final String apiKEY = "18da31d005e94d3c84fe2cf81d79f114";
    private final LocationApiAdapter locationData = new LocationApiAdapter();
    private final String lat = locationData.getLocationInfo("Latitude");
    private final String lon = locationData.getLocationInfo("Longitude");
    private JSONObject obj;
    private String data;

    /**
     * Returns time expected for sunrise/set and moon rise/set based on the
     * users location. Uses IPGEOLocation API. Strings allowed can be found in
     * the Info Accessible through API google doc.
     */
    public String getSunMoonInfo(String _event) {
        try {
            String url = astronomyUrl + apiKEY + "&lat=" + lat + "&long=" + lon;
            String param = _event.toLowerCase();
            System.out.println(param);
            obj = getConnection(url);
            this.data = obj.getString(param);
            System.out.println(this.data);

            if (this.data.equals("-:-")) {
                return "No set / rise for this date.";
            } else {
                return this.data;
            }

        } catch (JSONException ex) {
            Logger.getLogger(SunMoonRiseApiTranslator.class.getName()).log(Level.SEVERE, null, ex);
            return "Invalid Params. Check spelling";
        }
    }
}
