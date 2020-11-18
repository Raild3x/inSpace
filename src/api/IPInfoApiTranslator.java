package astroapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sytiva
 */
public class IPInfoApiTranslator implements LocationApiInterface {

    private static final String API_URL = "https://ipinfo.io/108.41.242.60/json?token=";
    private static final String ACCESS_TOKEN = "ab742ee8aa6621";
    private static JSONObject OBJ;
    private String latLongString = "";
    private final int latEndSub = 7;
    private final int longBeginSub = 8;

//Returns specified info (_placeInfo) on the users location when given a string.
    public String getLocationInfo(String _placeInfo) {
        String urlString = API_URL + ACCESS_TOKEN;
        String lowerInfo = _placeInfo.toLowerCase();
        String fixedInfo = fixLocationInfo(_placeInfo);

        getConnection(urlString);
        try {
            if (lowerInfo.equals("latitude")) {

                return OBJ.getString(fixLocationInfo(_placeInfo)).substring(0, latEndSub);

            } else if (lowerInfo.equals("longitude")) {

                return OBJ.getString(fixedInfo).substring(longBeginSub, OBJ.getString(fixedInfo).length());
                
            } else {
                return OBJ.getString(fixedInfo);
            }
        } catch (JSONException ex) {
            //Logger.getLogger(IPInfoApiTranslator.class.getName()).log(Level.SEVERE, null, ex);
            return "Invalid Params";
        }
    }

    public static String fixLocationInfo(String _placeInfo) {
        String toLowerCase = _placeInfo.toLowerCase();
        if ("ip".equals(toLowerCase) || "city".equals(toLowerCase) || "timezone".equals(toLowerCase)) {
            return toLowerCase;
        } else {
            switch (toLowerCase) {
                case "latitude":
                    return "loc";
                case "longitude":
                    return "loc";
                case "countryname":
                    return "country";
                case "stateorprov":
                    return "region";
            }
            return "";
        }
    }

    private static void getConnection(String _urlString) {
        URL url;
        try {
            url = new URL(_urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            OBJ = new JSONObject(content.toString());
        } catch (Exception ex) {
            //Logger.getLogger(SunMoonRiseApiTranslator.class.getName()).log(Level.SEVERE, null, ex);  //Left for debugging
        }
    }

}
