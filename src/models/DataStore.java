package astroapi;

import java.io.File;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import services.RenderService;
/**
 *
 * @author sytiva
 * This class Stores data from the location and sunMoonRise API. It also stores the time
 * taken to execute the program.
 */

public class DataStore {

    private static final File saveData = new File("datastore\\savedata.txt");
    private static final File saveTime = new File("datastore\\savetime.txt");
    private static final LocationApiAdapter location = new LocationApiAdapter();
    private static final SunMoonRiseAdapter sunMoon = new SunMoonRiseAdapter();
    private static final int timeLineInFile = 1;
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
        makeFile(saveData);

        try {
            FileWriter writer = new FileWriter(saveData);
            writer.write(location.getLocationInfo("Ip") + "\n");
            writer.write(location.getLocationInfo("city") + "\n");
            writer.write(location.getLocationInfo("stateorprov") + "\n");
            writer.write(location.getLocationInfo("timezone") + "\n");
            writer.write(location.getLocationInfo("latitude") + "\n");
            writer.write(location.getLocationInfo("longitude") + "\n");
            writer.write(sunMoon.getSunMoonInfo("sunrise") + "\n");
            writer.write(sunMoon.getSunMoonInfo("sunset") + "\n");
            writer.write(sunMoon.getSunMoonInfo("moonrise") + "\n");
            writer.write(sunMoon.getSunMoonInfo("moonset") + "\n");
            writer.close();
            System.out.println("Save complete!");
        } catch (IOException ex) {
            System.out.println("There was a problem writing to the file.");
        }
    }

    /*
    Saves time app was last used in the saveTime text file.
    */
    public void saveExecutionTime() {
        execTime = RenderService.getInstance().getElapsedTime();
        makeFile(saveTime);
        try {
            FileWriter writer = new FileWriter(saveTime);
            writer.write("Execution time: " + (execTime / 1000) + " Seconds.");
            writer.close();
            System.out.println("Save complete!");
        } catch (IOException ex) {
            System.out.println("There was a problem writing to the file.");
        }
    }

    /*
    Returns info in the saveData text file based on the string supplied.
    Strings allowed here are the same strings allowed for the locationApi and sun/moon rise api.
    */
    public String loadInfo(String _info) {
        String toLowerCase = _info.toLowerCase();
        if (saveData.exists()) {
            try {
                switch (toLowerCase) {
                    case "ip":
                        return Files.readAllLines(saveData.toPath()).get(Info.IP.line);
                    case "city":
                        return Files.readAllLines(saveData.toPath()).get(Info.CITY.line);
                    case "state/province":
                        return Files.readAllLines(saveData.toPath()).get(Info.STATE_PROVINCE.line);
                    case "timezone":
                        return Files.readAllLines(saveData.toPath()).get(Info.TIMEZONE.line);
                    case "latitude":
                        return Files.readAllLines(saveData.toPath()).get(Info.LATITUDE.line);
                    case "longitude":
                        return Files.readAllLines(saveData.toPath()).get(Info.LONGITUDE.line);
                    case "sunrise":
                        return Files.readAllLines(saveData.toPath()).get(Info.SUNRISE.line);
                    case "sunset":
                        return Files.readAllLines(saveData.toPath()).get(Info.SUNSET.line);
                    case "moonrise":
                        return Files.readAllLines(saveData.toPath()).get(Info.MOONRISE.line);
                    case "moonset":
                        return Files.readAllLines(saveData.toPath()).get(Info.MOONSET.line);
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
            return Files.readAllLines(saveTime.toPath()).get(timeLineInFile);
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
