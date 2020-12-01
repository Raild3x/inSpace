package api;

/**
 *
 * @author sytiva
 */
public class SunMoonRiseAdapter {

    private final SunMoonRiseApiInterface sunMoonRiseAdapter = new SunMoonRiseApiTranslator();

    public String getSunMoonInfo(String _event) {
        return sunMoonRiseAdapter.getSunMoonInfo(_event);
    }

}
