package api;

import org.json.JSONObject;

/**
 *
 * @author sytiv
 */
public interface AstroApiInterface {

    public String getBodyInfo(String _body, String _dataWanted);

    public JSONObject getBodyInfo(String _body);

    public String[] getMoonsAsArray(String _body);
}
