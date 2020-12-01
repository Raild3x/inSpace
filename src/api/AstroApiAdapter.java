
package api;

import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author sytiva, Logan
 * @lastModified 12/1/2020
 * 
 * @description Adapter for the AstroTranslator class, acts as an intermediary.
 */
public class AstroApiAdapter {
    private final AstroApiInterface astroAdapter = new AstroApiTranslator();

    public String getBodyInfo(String _body, String _dataWanted){
        return astroAdapter.getBodyInfo(_body, _dataWanted);
    }

    public JSONObject getBodyInfo(String _body){
        return astroAdapter.getBodyInfo(_body);
    }

     public String[] getMoonsAsArray(String _body) {
        return astroAdapter.getMoonsAsArray(_body);
    }

    /**
     * Returns moon apiNames in an ArrayList format.
     * @param _body
     * @return 
     */
    public ArrayList<String> getBodyMoons(String _body) {
        return astroAdapter.getBodyMoons(_body);
    }

}
