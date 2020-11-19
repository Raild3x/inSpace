package api;

public class AstroTestClass {

    public static void main(String[] args) {
        LocationApiInterface Location = new IPInfoApiTranslator();
//        AstroApiInterface testing = new AstroApiTranslator();
//
//        System.out.println(testing.getBodyInfo("sun", "isplanet"));
//        System.out.println(testing.getBodyInfo("sun","isPLanet"));
//        System.out.println(testing.getBodyInfo("sun", "isPLAnet"));
//        System.out.println(testing.getBodyInfo("sun", "isPlanet"));
//        System.out.println(testing.getBodyInfo("sun", "isPlanet"));
//        System.out.println(testing.getBodyInfo("sun", "isPlanet"));
//        System.out.println(testing.getBodyInfo("sun", "isPlanet"));
//        System.out.println(testing.getBodyInfo("sun", "isPlanet"));
//        System.out.println(testing.getBodyInfo("sun", "isPlanet"));
//
//        System.out.println(testing.getBodyInfo("sun", "semimajoraxis"));
//        System.out.println(testing.getBodyInfo("moon","SEMImajoraxis"));
//        System.out.println(testing.getBodyInfo("venus", "semiMAJORaxis"));
//        System.out.println(testing.getBodyInfo("mercury", "semimajorAXIS"));
//        System.out.println(testing.getBodyInfo("earth", "seMImajorAXis"));
//        System.out.println(testing.getBodyInfo("neptune", "semiMAjorAXis"));
//        System.out.println(testing.getBodyInfo("mars", "semimajORaxis"));
//        System.out.println(testing.getBodyInfo("saturn", "semimajorAXis"));
//        System.out.println(testing.getBodyInfo("uranus", "semimajorAXis"));
//
//        System.out.println(testing.getBodyInfo("sun", "moOns"));
//        System.out.println(testing.getBodyInfo("moon","MooNS"));
//        System.out.println(testing.getBodyInfo("neptune", "moons"));
//        System.out.println(testing.getBodyInfo("earth", "MOONS"));
//        System.out.println(testing.getBodyInfo("venus", "mOoNs"));
//        System.out.println(testing.getBodyInfo("mercury", "moonS"));
//        System.out.println(testing.getBodyInfo("mars", "mooNS"));
//        System.out.println(testing.getBodyInfo("saturn", "moONs"));
//        System.out.println(testing.getBodyInfo("uranus", "mOONs"));
//
//        System.out.println(testing.getBodyInfo("sun", "mass"));
//        System.out.println(testing.getBodyInfo("moon","Mass"));
//        System.out.println(testing.getBodyInfo("neptune", "MAss"));
//        System.out.println(testing.getBodyInfo("earth", "MASs"));
//        System.out.println(testing.getBodyInfo("venus", "MASS"));
//        System.out.println(testing.getBodyInfo("mercury", "mASS"));
//        System.out.println(testing.getBodyInfo("mars", "maSS"));
//        System.out.println(testing.getBodyInfo("saturn", "masS"));
//        System.out.println(testing.getBodyInfo("uranus", "mass"));
//
//        System.out.println(testing.getBodyInfo("earth", "moons"));
//        System.out.println(testing.getBodyInfo("earth","Mons"));
//        System.out.println(testing.getBodyInfo("earth", "vol"));
//        System.out.println(testing.getBodyInfo("earth", "volume"));//Invalid param
//        System.out.println(testing.getBodyInfo("earth", "sideralRotation"));
//        System.out.println(testing.getBodyInfo("earth", "sideralRot")); //Invalid param
//        System.out.println(testing.getBodyInfo("earth", "aroundPlanet"));
//        System.out.println(testing.getBodyInfo("earth", "planets its around")); //Invalid param
//        System.out.println(testing.getBodyInfo("earth", "axialtillt")); //Invalid param
        System.out.println(Location.getLocationInfo("ip"));
        System.out.println(Location.getLocationInfo("TIMEZONE"));
        System.out.println(Location.getLocationInfo("citY"));
        System.out.println(Location.getLocationInfo("STATEorPROV"));
        System.out.println(Location.getLocationInfo("latitude"));
        System.out.println(Location.getLocationInfo("LoNgItUdE"));
        System.out.println(Location.getLocationInfo("COUNTRYname"));
    }
}
