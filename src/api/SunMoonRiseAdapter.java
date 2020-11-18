package api;

/**
 *
 * @author sytiv
 */
public class SunMoonRiseAdapter {

    SunMoonRiseApiInterface SunMoonRiseAdapter = new SunMoonRiseApiTranslator();

    public String getSunMoonInfo(String _event) {
        return SunMoonRiseAdapter.getSunMoonInfo(_event);
    }

}
