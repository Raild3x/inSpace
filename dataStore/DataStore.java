import java.io.File;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import services.RenderService;
/**
 *
 * @author sytiva
 */
public class DataStore {

    private static final File SAVEDATA = new File("datastore\\savedata.txt");
    private static final File SAVETIME = new File("datastore\\saveTime.txt");
    private static final LocationApiAdapter LOCATION = new LocationApiAdapter();
    private static final SunMoonRiseAdapter SUNMOON = new SunMoonRiseAdapter();
    private static final int TIMELINEINFILE = 1;
    private static long execTime;

    public enum Info {
        IP(0),
        CITY(1),
        STATE_PROVINCE(2),
        TIMEZONE(3),
        LATITUDE(4),
        LONGITUDE(5),
        SUNRISE(6),
        SUNSET(7),
        MOONRISE(8),
        MOONSET(9);

        private final int line;  // line it can be found at in the file

        Info(int line) {
            this.line = line;
        }
    }

    /*
    Saves info on Location from the location
     */
    public void saveInfo() {
        makeFile(SAVEDATA);

        try {
            FileWriter writer = new FileWriter(SAVEDATA);
            writer.write(LOCATION.getLocationInfo("Ip") + "\n");
            writer.write(LOCATION.getLocationInfo("city") + "\n");
            writer.write(LOCATION.getLocationInfo("stateorprov") + "\n");
            writer.write(LOCATION.getLocationInfo("timezone") + "\n");
            writer.write(LOCATION.getLocationInfo("latitude") + "\n");
            writer.write(LOCATION.getLocationInfo("longitude") + "\n");
            writer.write(SUNMOON.getSunMoonInfo("sunrise") + "\n");
            writer.write(SUNMOON.getSunMoonInfo("sunset") + "\n");
            writer.write(SUNMOON.getSunMoonInfo("moonrise") + "\n");
            writer.write(SUNMOON.getSunMoonInfo("moonset") + "\n");
            writer.close();
            System.out.println("Save complete!");
        } catch (IOException ex) {
            System.out.println("There was a problem writing to the file.");
        }
    }

    /*
    Saves time app was last used in the SAVETIME text file.
    */

    public void saveExecutionTime() {
        execTime = RenderService.getInstance().getElapsedTime();
        makeFile(SAVETIME);
        try {
            FileWriter writer = new FileWriter(SAVETIME);
            writer.write("Execution time: " + (execTime / 1000) + " Seconds.");
            writer.close();
            System.out.println("Save complete!");
        } catch (IOException ex) {
            System.out.println("There was a problem writing to the file.");
        }
    }

    /*
    Returns info in the SAVEDATA text file based on the string supplied.
    Strings allowed here are the same strings allowed for the locationApi and sun/moon rise api.
    */
    public String loadInfo(String _info) {
        String toLowerCase = _info.toLowerCase();
        if (SAVEDATA.exists()) {
            try {
                switch (toLowerCase) {
                    case "ip":
                        return Files.readAllLines(SAVEDATA.toPath()).get(Info.IP.line);
                    case "city":
                        return Files.readAllLines(SAVEDATA.toPath()).get(Info.CITY.line);
                    case "state/province":
                        return Files.readAllLines(SAVEDATA.toPath()).get(Info.STATE_PROVINCE.line);
                    case "timezone":
                        return Files.readAllLines(SAVEDATA.toPath()).get(Info.TIMEZONE.line);
                    case "latitude":
                        return Files.readAllLines(SAVEDATA.toPath()).get(Info.LATITUDE.line);
                    case "longitude":
                        return Files.readAllLines(SAVEDATA.toPath()).get(Info.LONGITUDE.line);
                    case "sunrise":
                        return Files.readAllLines(SAVEDATA.toPath()).get(Info.SUNRISE.line);
                    case "sunset":
                        return Files.readAllLines(SAVEDATA.toPath()).get(Info.SUNSET.line);
                    case "moonrise":
                        return Files.readAllLines(SAVEDATA.toPath()).get(Info.MOONRISE.line);
                    case "moonset":
                        return Files.readAllLines(SAVEDATA.toPath()).get(Info.MOONSET.line);
                }
            } catch (IOException ex) {
                System.out.println("Save not found. Try saving first");
                return "";
            }
        }
        return null;
    }

    public String loadExecutionTime(){
        try {
            return Files.readAllLines(SAVETIME.toPath()).get(TIMELINEINFILE);
        } catch (IOException ex) {
            System.out.println("Save not Found. Try saving first");
            return "";
        }
    }

    /*
    Creates a file (txt file) to save location/sunrise data to.
     */
    private void makeFile(File _file) {
        try {
            if (_file.createNewFile()) {
                System.out.println("File created: " + _file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred. File path may b");
        }
    }
}
