package astroapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.logging.Level;  //Left for debugging
import java.util.logging.Logger; //Left for debugging

public class AstroApiTranslator extends APIConnect implements AstroApiInterface {

    private static final String ASTRONOMY_URL = "https://api.le-systeme-solaire.net/rest/bodies";

    /*Returns info(_dataWanted) of a specific celestial body(_body). Info on what strings are allowed as _dataWanted
     *here: https://api.le-systeme-solaire.net/en/
     * _body is not case sensitive, _dataWanted is case sensitive.
     */
    public String getBodyInfo(String _body, String _dataWanted) {
        String url = ASTRONOMY_URL + "/{" + _body + "}";
        getConnection(url);
        try {
            if ((noReturn == false) && OBJ.getString("mass").compareTo("null") != 0 && OBJ.getString("vol").compareTo("null") != 0) {

                if (_dataWanted.toLowerCase().equals("moons")) {
                    getMoonsAsArray(OBJ);
                } else {
                    return OBJ.getString(fixParam(_dataWanted));
                }
            }
        } catch (NullPointerException | JSONException ex) {
            //Logger.getLogger(AstroApi.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print("Invalid params at getBodyInfo(String,String)");
        }
        noReturn = false;
        return "";
    }

    /*Same as above method except it gives all info on one celestial body instead
    * of one specific piece of info. Returns entire JSON to allow retrieval of any
    * info without making multiple calls.
    */
    public JSONObject getBodyInfo(String _body) {
        String url = ASTRONOMY_URL + "/{" + fixParam(_body) + "}?exclude=discoveredBy,discoveryDate,"
                + "alternativeName,id,name,dimension";
        getConnection(url);
        try {
            if (OBJ.getString("mass").equals("null") && OBJ.getString("vol").equals("null")) {
                throw new NullPointerException();
            } else {
                return OBJ;
            }
        } catch (JSONException ex) {
            //Logger.getLogger(AstroApiTranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception. Check your params");
            return OBJ;
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

    //Returns a String array of the moons of a celestial body. Excludes the api rel link.
    public String[] getMoonsAsArray(JSONObject _jsonObj) {
        JSONArray moonArray;
        String[] moonArrStrVer = null;

        try {
            moonArray = _jsonObj.getJSONArray("moons");
            moonArrStrVer = new String[moonArray.length()];
            String initString;
            int startIndex;
            int endIndex;
            String toBeReplaced = "";

            for (int i = 0; i < moonArrStrVer.length; i++) {
                initString = moonArray.optString(i);
                startIndex = initString.indexOf(",");
                endIndex = initString.indexOf("}");
                moonArrStrVer[i] = initString.replace(initString.substring(startIndex, endIndex), toBeReplaced);
                //System.out.println(moonArrStrVer[i]); //left for debugging
            }

            return moonArrStrVer;

        } catch (JSONException ex) {
            //Logger.getLogger(AstroApiTranslator.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("A JSONException occurred. Planet may have no moons");
            return moonArrStrVer;
        }
    }
}
