package api;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class AstroApiTranslator implements AstroApiInterface{

    private static final String ASTRONOMY_URL = "https://api.le-systeme-solaire.net/rest/bodies";
    private static JSONObject OBJ;
    private static boolean noReturn = false; //Ensures results of previous calls are not returned twice if current API call fails due to bad params

    /*Returns info(_dataWanted) of a specific celestial body(_body). Info on what strings are allowed as _dataWanted
     *here: https://api.le-systeme-solaire.net/en/
     * _body is not case sensitive, _dataWanted is case sensitive.
     */
    public String getBodyInfo(String _body, String _dataWanted) {
        String url = ASTRONOMY_URL + "/{" + _body + "}";
        getConnection(url);
        try {
            if (noReturn == false) {
                return OBJ.getString(fixParam(_dataWanted));
            }
        } catch (NullPointerException | JSONException ex) {
            //Logger.getLogger(AstroApi.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print("Invalid params");
        }
        noReturn = false;
        return "";
    }

    //Same as above method except it gives all info on one celestial body instead
    //of one specific piece of info.
    public String getBodyInfo(String _body) {
        String url = ASTRONOMY_URL + "/{" + fixParam(_body) + "}?exclude=discoveredBy,discoveryDate,"
                + "alternativeName,id,name,dimension";
        getConnection(url);
        return OBJ.toString();
    }

    //Used to get connect to internet to retrieve a json with the requested info.
    private static void getConnection(String _urlString) {
        URL url;
        try {
            url = new URL(_urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //int status = con.getResponseCode();
            //System.out.println("Response Code: " + status);   //Used these line to check response code while debugging

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            OBJ = new JSONObject(content.toString());
        } catch (IOException | JSONException ex) {
            Logger.getLogger(AstroApiTranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print("Invalid params");
            noReturn = true;
        }
    }

//Fixes any case sensitive parameters used by the getBodyInfo methods by forcing
//them to be the correct format.
    private static String fixParam(String _param) {
        String toLowerCase = _param.toLowerCase();
        if ("id".equals(toLowerCase) || "name".equals(toLowerCase) || "moons".equals(toLowerCase)
                || "eccentricity".equals(toLowerCase) || "inclination".equals(toLowerCase)
                || "mass".equals(toLowerCase) || "vol".equals(toLowerCase) || "density".equals(toLowerCase)
                || "gravity".equals(toLowerCase) || "neptune".equals(toLowerCase) || "jupiter".equals(toLowerCase)
                || "mars".equals(toLowerCase) || "uranus".equals(toLowerCase) || "venus".equals(toLowerCase)) {

            return toLowerCase;

        } else {
            switch (toLowerCase) {
                case "isplanet":
                    return "isPlanet";
                case "englishname":
                    return "englishName";
                case "semimajoraxis":
                    return "semimajorAxis";
                case "equaradius":
                    return "equaRadius";
                case "sideralorbit":
                    return "sideralOrbit";
                case "sideralrotation":
                    return "sideralRotation";
                case "aroundplanet":
                    return "aroundPlanet";
                case "axialtilt":
                    return "axialTilt";
                case "moon":
                    return "lune";
                case "earth":
                    return "terre";
                case "saturn":
                    return "saturne";
                case "mercury":
                    return "mercure";
                case "sun":
                    return "soleil";
            }
        }
        return _param;
    }
}
