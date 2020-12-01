package api;

import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author sytiva
 */
public class AstroApiAdapter {
    AstroApiInterface AstroAdapter = new AstroApiTranslator();

    public String getBodyInfo(String _body, String _dataWanted){
        return AstroAdapter.getBodyInfo(_body, _dataWanted);
    }

    public JSONObject getBodyInfo(String _body){
        return AstroAdapter.getBodyInfo(_body);
    }

     public String[] getMoonsAsArray(String _body) {
        return AstroAdapter.getMoonsAsArray(_body);
    }

    public ArrayList<String> getBodyMoons(String _body) {
        return AstroAdapter.getBodyMoons(_body);
    }

}
