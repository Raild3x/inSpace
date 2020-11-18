/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astroapi;

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
}
