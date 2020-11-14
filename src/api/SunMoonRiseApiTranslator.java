/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

/**
 *
 * @author szoor
 */
public class SunMoonRiseApiTranslator {

  /*  import java.io.InputStreamReader ;
    import java.net.HttpURLConnection ;
    import java.net.URL ;
    import org.json.JSONObject ;

   
     * The MovieDbApi class connects to the TheMovieDB.org web server for
     * retrieving information about movies including titles, ratings, actors...
     * etc. Note: Do not call this class directly. Route all Movie API requests
     * through the MovieAPITranslator class. Last Updated: 2/21/2019
     *
     * @author riquigle
     
    public class OpenMovieDatabaseAPITranslator implements MovieApiInterface {

        private static final String baseURL = "https://api.themoviedb.org";
        private static final String apiKey = "680201a1d38ff559b9c8b20ffde6db61";

        
         * Given an id number, this method queries the MovieDbApi for a movie
         * title and returns it.
         *
         * @param _id
         * @return String - the movie title.
         
        @Override
        public String loadMovieTitleById(int _id) {
            // Build the base url string.
            String searchString = "/3/movie/" + _id + "?api_key=" + OpenMovieDatabaseAPITranslator.apiKey + "&language=en-US";
            try {
                URL url = new URL(OpenMovieDatabaseAPITranslator.baseURL + searchString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                // Build the content from the buffered input.
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // Close the connections.
                in.close();
                con.disconnect();
                // Extract the JSON object.
                JSONObject obj = new JSONObject(content.toString());
                String movieName = obj.getString("original_title");
                return movieName;
            } catch (Exception ex) {
                //Logger.getLogger(MovieApp.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

        
         * STUB: This stub method returns a dummy value of 12.
         *
         * @param _title
         * @return int - the movie ID.
         
        @Override
        public int loadMovieIdByTitle(String _title) {
            return 12;
        }
    }
    ________________________________________________________________________________
    package prototype ;

    import java.io.BufferedReader ;
    import java.io.InputStreamReader ;
    import java.net.HttpURLConnection ;
    import java.net.URL ;
    import java.util.logging.Level ;
    import java.util.logging.Logger ;
    import org.json.*;

 * This prototype demonstrates a call to an API.
 * @author Ike
 
    
   
    
public class APIDemo {

        public static void main(String[] args) {
            // Create a HTTP Connection.
            String baseUrl = "https://api.themoviedb.org";
            String callAction = "/3/movie/";
            String movieId = "299536";
            String apiKey = "680201a1d38ff559b9c8b20ffde6db61";
            String urlString = baseUrl + callAction + movieId + "?api_key=" + apiKey + "&language=en-US";
            URL url;
            System.out.println("Marker 1");
            try {
                System.out.println("Marker 2");
                url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                System.out.println("Marker 3");

                int status = con.getResponseCode();
                System.out.println("Response Code: " + status);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();
                System.out.println("Output: " + content.toString());
                JSONObject obj = new JSONObject(content.toString());
                String movieName = obj.getString("original_title");
                System.out.println("Movie Name: " + movieName);
            } catch (Exception ex) {
                Logger.getLogger(APIDemo.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

        }

    }
 */
    // ===========================================================================
/*package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class SolarSystemAPITranslator {

    private static JSONObject OBJ;

    public static String getAstroInfo(String _event) {
        String baseUrl = "https://api.le-systeme-solaire.net/rest/bodies/";

        //API parameters = data, exclude, order, page, rowData, filter[], satisfy
        String data;
        String exclude = "exclude=name,perihelion,apheion,inclination,escape,meanRadius,"
                + "dimension,alternativeName,axialTilt";
        String order = "order="; //sort order of data you want to use
        String name = "englishName"; //string value
        String id = "id"; //string value
        String isPlanet = "isPlanet"; //boolean value
        String moons = "moons"; //table with all moons
        String semimajorAxis = "semimajorAxis"; //integer value
        String eccentricity = "eccentricity"; //decimal value
        String massKG = "mass{massValue,massExponent}"; //object{number, integer}
        String volKG = "vol{volValue,volExponent}"; //object{number, integer}
        String density = "density"; //decimal
        String gravity = "gravity"; //decimal
        String equaRadiuskm = "equaRadius"; //integer
        String polarRadiuskm = "polarRadius"; //integer
        String sideralOrbit = "sideralOrbit"; //decimal
        String sideralRotation = "sideralRotation"; //decimal
        String aroundPlanet = "aroundPlanet{planet,rel}"; //object{string,string}
        String discoveredBy = "discoveredBy"; //string
        String discoveryDate = "discoveryDate"; //string

        data = id + isPlanet + moons + semimajorAxis + eccentricity + massKG + volKG
                + density + gravity + equaRadiuskm + polarRadiuskm + sideralOrbit + sideralRotation + aroundPlanet + discoveredBy + discoveryDate;
        String filter = "filter[]="; //filter data, operator, value (separated by comma) Example: filter[]=id,eq,mars
       

         * Accepted operators are: cs (like) sw (start with) ew (end with) eq
         * (equal) lt (less than) le (less or equal than) ge (greater or equal
         * than) gt (greater than) bt (between) And all the opposite operators :
         * ncs - nsw - new - neq - nlt - nle - nge - ngt - nbt. *
         

        //url to get name of planet in english
        String url = baseUrl + exclude + order + name + "&language=en-US";
        getConnection(url);
        try {
            return OBJ.getString(_event);
        } catch (JSONException ex) {
            Logger.getLogger(LocationAdapter.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    private static void getConnection(String _urlString) {
        URL url;
        try {
            url = new URL(_urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            System.out.println("Response Code: " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            OBJ = new JSONObject(content.toString());
        } catch (Exception ex) {
            Logger.getLogger(LocationAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(getAstroInfo("sunrise"));
        System.out.println(getAstroInfo("date"));
        System.out.println(getAstroInfo("moon_parallactic_angle"));

    }

} */
}
