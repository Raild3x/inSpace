/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sytiv
 */
public class AstroApiAdapter {
    AstroApiInterface AstroAdapter = new AstroApiTranslator();

    public String getBodyInfo(String _body, String _dataWanted){
        return AstroAdapter.getBodyInfo(_body, _dataWanted);
    }

    public String getBodyInfo(String _body){
        return AstroAdapter.getBodyInfo(_body);
    }
    
    public ArrayList<String> getBodyMoons(String _body) {
        System.out.println("Attempting to get moons of "+_body);
        ArrayList<String> moons = new ArrayList<>();
        
        JSONArray jsonMoons;
        try {
            jsonMoons = new JSONArray(this.getBodyInfo(_body, "moons"));
        } catch (JSONException ex) {
            System.out.println("No moons available!");
            return moons;
        }
        
        for (int i = 0; i < jsonMoons.length(); i++) {
            try {
                JSONObject jsonObj = jsonMoons.getJSONObject(i);
                String moon = jsonObj.getString("rel");
                moon = moon.substring(moon.lastIndexOf("/") + 1);
                moons.add(moon);
            } catch (JSONException ex) {
                System.out.println("Unable to get JSONObject from JSONArray");
                Logger.getLogger(AstroApiAdapter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return moons;
    }
}
