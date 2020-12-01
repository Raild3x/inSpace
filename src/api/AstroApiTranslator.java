package api;

import java.lang.Exception;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author sytiva
 */
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
            if (noReturn == false) {
                
                return OBJ.getString(fixParam(_dataWanted));
            }
        } catch (NullPointerException | JSONException ex) {
            System.out.print("Invalid params at getBodyInfo(String,String)");
        }
        noReturn = false;
        return "";
    }

    /* Returns entire JSON of information on a celestial body to allow retrieval
     * of any info without making multiple calls to the API.
     */
    public JSONObject getBodyInfo(String _body) {
        String url = ASTRONOMY_URL + "/{" + fixParam(_body) + "}";
        JSONObject err = null;
        getConnection(url);
        if (noReturn == false) {
            return OBJ;
        }
        noReturn = false;
        return err;
    }

//Fixes any case sensitive parameters used by the getBodyInfo methods by forcing
//them to be the correct format. Also removes unneccessary spaces,slashes or accented letters.
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

        String replaceSlashes = _param.replace("/", "");
        String replaceSpace = replaceSlashes.replaceAll(" ", "");
        String replaceWeirdLetters = replaceSpace.replaceAll("œ", "oe");
        return StringUtils.stripAccents(replaceWeirdLetters);
    }

    // Returns a String array of the moons of a celestial body. If an array is needed, call
    // this instead of getBodyInfo(_body, "moons").Excludes the api rel link.
    public String[] getMoonsAsArray(String _body) {
        System.out.println("Getting moons as array");
        JSONArray moonArray;
        String[] moonArrStrVer = {};

        try {
            moonArray = getBodyInfo(_body).getJSONArray("moons");
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
                //System.out.println(moonArrStrVer[i]);
            }

            return moonArrStrVer;

        } catch (JSONException ex) {
            System.out.println("A JSONException at getMoonsAsArray. Body may have no moons");
            return moonArrStrVer;
        }
    }
}
