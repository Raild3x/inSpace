package api;

/**
 *
 * @author sytiva
 */
public class SunMoonRiseAdapter {

    private final SunMoonRiseApiInterface sunMoonRiseAdapter = new SunMoonRiseApiTranslator();

    public String getSunMoonInfo(String _event) {
        System.out.println("SunMoonRiseAdapter Checkpoint");
        return sunMoonRiseAdapter.getSunMoonInfo(_event);
    }

}
