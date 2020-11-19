package models;

import api.AstroApiAdapter;
import javafx.scene.paint.Color;
/**
 *
 * @author Logan
 */
public class Moon extends CelestialBody {
    private final String apiName;
    
    public Moon (String _apiName, CelestialBody _orbitingBody) {
        super(
                new AstroApiAdapter().getBodyInfo(_apiName, "englishName"),
                Double.parseDouble(new AstroApiAdapter().getBodyInfo(_apiName, "meanRadius")),
                Color.GRAY
        );
        this.apiName = _apiName;
    }
    
    //================================== GETTERS =======================================//
    @Override
    public String getInfo(String _infoType) {
        return this.AstroApi.getBodyInfo(this.apiName, _infoType);
    }
}
