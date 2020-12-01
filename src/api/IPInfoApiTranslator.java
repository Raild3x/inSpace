package api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sytiva
 */
public class IPInfoApiTranslator extends APIConnect implements LocationApiInterface {

    private static final String API_URL = "http://ip-api.com/json/";
    private static JSONObject OBJ;

//Returns specified info (_placeInfo) on the users location. Returns that info in a String.
    public String getLocationInfo(String _placeInfo) {
        String urlString = API_URL;
        OBJ = getConnection(urlString);
        try {
            return OBJ.getString(fixParams(_placeInfo));
        } catch (JSONException ex) {
            return "JSONException: Info not found";
        }
    }

//Changes any string to correct param needed by the api.
    private static String fixParams(String _placeInfo) {
        String toLowerCase = _placeInfo.toLowerCase();
        if ("city".equals(toLowerCase) || "timezone".equals(toLowerCase)) {
            return toLowerCase;
        } else {
            switch (toLowerCase) {
                case "latitude":
                    return "lat";
                case "longitude":
                    return "lon";
                case "countryname":
                    return "country";
                case "stateorprov":
                    return "region";
                case "ip":
                    return "query";
            }
            return _placeInfo;
        }
    }
}

