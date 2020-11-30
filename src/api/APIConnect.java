package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sytiva
 *
 * Utilized by the API Translators to retrieve information that comes in the form of a JSON.
 */
public class APIConnect {

    protected static JSONObject OBJ;
    protected static boolean noReturn = false;   //Ensures results of previous calls are not returned twice if current API call fails due to bad params

    protected static void getConnection(String _urlString) {
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
        } catch (IOException | JSONException ex) {
            System.out.println("Exception at getConnection");
            noReturn = true;
        }
    }
}
