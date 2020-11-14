/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author szoor
 */
public class AstroAPIBase {
    
    /**
 * This is the parent class for any class that is going to make calls to the movie database API.
 * By changing the one line of code, the API that is being used can be changed for the
 * entire system.
 * @author ike
 * 
 * // Here we designate the Translator that we want to use. In this case, the OpenMovieDatabaseAPITranslator.
    protected final static MovieApiInterface myMovieAPI = new OpenMovieDatabaseAPITranslator();

    // This commented out line of code would switch the API we're using for the entire application.
    // protected final static MovieApiInterface myMovieAPI = new InternetMovieDatabaseAPITranslator();

 */
}
