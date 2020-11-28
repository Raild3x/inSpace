package api;

/**
 *
 * @author sytiva
 */
public class SunMoonRiseAdapter {

    final SunMoonRiseApiInterface SunMoonRiseAdapter = new SunMoonRiseApiTranslator();

    public String getSunMoonInfo(String _event) {
        return SunMoonRiseAdapter.getSunMoonInfo(_event);
    }

}
