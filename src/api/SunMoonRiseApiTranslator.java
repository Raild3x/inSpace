package api;

import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
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
 * @author sytiv
 */
public class SunMoonRiseApiTranslator implements SunMoonRiseApiInterface{
    private static final String ASTRONOMY_URL = "https://api.ipgeolocation.io/astronomy?apiKey=";
    protected static final String API_KEY = "18da31d005e94d3c84fe2cf81d79f114";
    private static final IPGeolocationAPI API = new IPGeolocationAPI(API_KEY);
    protected static final GeolocationParams geoParams = new GeolocationParams();
    protected static final Geolocation geolocation = API.getGeolocation(geoParams);
    private static JSONObject OBJ;

    public String getSunMoonInfo(String _event){
        String url = ASTRONOMY_URL + API_KEY + "&ip=" + geolocation.getIPAddress();
        getConnection(url);
        try {
            return OBJ.getString(_event);
        } catch (JSONException ex) {
            Logger.getLogger(SunMoonRiseApiTranslator.class.getName()).log(Level.SEVERE, null, ex);
            return "Invalid Params";
        }
    }

    private static void getConnection(String _urlString) {
        URL url;
        try {
            url = new URL(_urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //int status = con.getResponseCode();
            //System.out.println("Response Code: " + status);

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
            Logger.getLogger(SunMoonRiseApiTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
