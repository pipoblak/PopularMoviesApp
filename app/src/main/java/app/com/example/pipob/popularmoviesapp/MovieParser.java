package app.com.example.pipob.popularmoviesapp;

import android.text.format.Time;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by pipob on 03/07/2016.
 */
public class MovieParser{
    String movieNames[];
    String urlImages[];
    int rating[];

        public MovieParser(){


        }



        public void getMovieDataFromJson(String json)
                throws JSONException {


            final String OWM_RESULTS = "results";

            JSONObject moviesJson= new JSONObject(json);
            JSONArray reults = moviesJson.getJSONArray(OWM_RESULTS);




            String[] resultStrs = new String[reults.length()];
            movieNames = new String[reults.length()];
            urlImages = new String[reults.length()];
            rating = new int[reults.length()];
            for(int i = 0; i < reults.length(); i++) {

                try{

                    JSONObject singleMovie = reults.getJSONObject(i);


                    movieNames[i] = singleMovie.getString("original_title");
                    urlImages[i]  = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + singleMovie.getString("poster_path");
                    rating[i] = singleMovie.getInt("vote_average");
                    //Log.v("TASAD",movieNames    [i]  + urlImages[i]);




                }catch(Exception e){}
            }




        }

    public String[] getMovieNames(){
        return movieNames  ;

    }

    public String[] getUrlImages() {
        return urlImages;
    }

    public int[] getRating() {
        return rating;
    }

    public static String titleize(final String input) {
            StringBuilder retorno = new StringBuilder(input.length());
            boolean embranco = true;
            for(int i = 0; i < input.length(); i++) {
                char indexchar = input.charAt(i);
                if(embranco && Character.isLowerCase(indexchar)) {
                    indexchar = Character.toTitleCase(indexchar);
                }
                retorno.append(indexchar);
                embranco = Character.isWhitespace(indexchar);
            }
            return retorno.toString();
        }

}
