package api;

public class AstroTestClass {

    /*
    Test class to test the fixParams method in AstroApiTranslator. Tests Moons,satellites and planets,
    ensures params that are case-sensitive still work
     */
    public static void test() {
        AstroApiAdapter testing = new AstroApiAdapter();

        System.out.println(testing.getBodyInfo("sun", "isplanet"));
        System.out.println(testing.getBodyInfo("sun", "isPLanet"));
        System.out.println(testing.getBodyInfo("sun", "isPLAnet"));
        System.out.println(testing.getBodyInfo("sun", "isPlanet"));
        System.out.println(testing.getBodyInfo("sun", "isPlanet"));
        System.out.println(testing.getBodyInfo("sun", "isPlanet"));
        System.out.println(testing.getBodyInfo("sun", "isPlanet"));
        System.out.println(testing.getBodyInfo("sun", "isPlanet"));
        System.out.println(testing.getBodyInfo("sun", "isPlanet"));

        System.out.println(testing.getBodyInfo("sun", "semimajoraxis"));
        System.out.println(testing.getBodyInfo("moon", "SEMImajoraxis"));
        System.out.println(testing.getBodyInfo("venus", "semiMAJORaxis"));
        System.out.println(testing.getBodyInfo("mercury", "semimajorAXIS"));
        System.out.println(testing.getBodyInfo("earth", "seMImajorAXis"));
        System.out.println(testing.getBodyInfo("neptune", "semiMAjorAXis"));
        System.out.println(testing.getBodyInfo("mars", "semimajORaxis"));
        System.out.println(testing.getBodyInfo("saturn", "semimajorAXis"));
        System.out.println(testing.getBodyInfo("uranus", "semimajorAXis"));

        System.out.println(testing.getBodyInfo("sun", "moOns")); //Has no moons
        System.out.println(testing.getBodyInfo("moon", "MooNS")); //Has no moons
        System.out.println(testing.getBodyInfo("neptune", "moons"));
        System.out.println(testing.getBodyInfo("earth", "MOONS"));
        System.out.println(testing.getBodyInfo("venus", "mOoNs"));  //Has no moons
        System.out.println(testing.getBodyInfo("mercury", "moonS"));  //Has no moons
        System.out.println(testing.getBodyInfo("mars", "mooNS"));
        System.out.println(testing.getBodyInfo("saturn", "moONs"));
        System.out.println(testing.getBodyInfo("uranus", "mOONs"));
        System.out.println(testing.getBodyInfo("jupiter", "mOONs"));

        System.out.println(testing.getBodyInfo("sun", "mass"));
        System.out.println(testing.getBodyInfo("moon", "Mass"));
        System.out.println(testing.getBodyInfo("neptune", "MAss"));
        System.out.println(testing.getBodyInfo("earth", "MASs"));
        System.out.println(testing.getBodyInfo("venus", "MASS"));
        System.out.println(testing.getBodyInfo("mercury", "mASS"));
        System.out.println(testing.getBodyInfo("mars", "maSS"));
        System.out.println(testing.getBodyInfo("saturn", "masS"));
        System.out.println(testing.getBodyInfo("uranus", "mass"));

        System.out.println(testing.getBodyInfo("earth", "moons"));
        System.out.println(testing.getBodyInfo("earth", "Mons"));// Invalid Param
        System.out.println(testing.getBodyInfo("earth", "vol"));
        System.out.println(testing.getBodyInfo("earth", "volume"));//Invalid param
        System.out.println(testing.getBodyInfo("earth", "sideralRotation"));
        System.out.println(testing.getBodyInfo("earth", "sideralRot")); //Invalid param
        System.out.println(testing.getBodyInfo("earth", "aroundPlanet"));
        System.out.println(testing.getBodyInfo("earth", "planets its around")); //Invalid param
        System.out.println(testing.getBodyInfo("earth", "axialtillt")); //Invalid param

        System.out.println(testing.getBodyInfo("Epiméthée"));
        System.out.println(testing.getBodyInfo("Phœbé"));
        System.out.println(testing.getBodyInfo("Hypérion"));
        System.out.println(testing.getBodyInfo("Hélène"));
        System.out.println(testing.getBodyInfo("S/2004 S 7"));
        System.out.println(testing.getBodyInfo("S/2004 S 12"));
        System.out.println(testing.getBodyInfo("Deïmos"));
        System.out.println(testing.getBodyInfo("Néreïde"));
        System.out.println(testing.getBodyInfo("S/2003 J 4"));
        System.out.println(testing.getBodyInfo("S/2017 J 5"));
        System.out.println(testing.getBodyInfo("S/2011 J 2"));
    }
}
