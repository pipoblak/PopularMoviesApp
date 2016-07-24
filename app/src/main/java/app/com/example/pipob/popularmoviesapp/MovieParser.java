package app.com.example.pipob.popularmoviesapp;

import android.text.format.Time;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pipob on 03/07/2016.
 */
public class MovieParser{
    String movieNames[];
    String urlImages[];
    String rating[];
    String movieDate[];
    String movieOverview[];
    byte[] imageData[] ;


        public MovieParser(){


        }



        public List<Movie> getMovieDataFromJson(String json)
                throws JSONException {

            List<Movie> movies = new ArrayList<Movie>();


            final String OWM_RESULTS = "results";

            JSONObject moviesJson= new JSONObject(json);
            JSONArray reults = moviesJson.getJSONArray(OWM_RESULTS);




            String[] resultStrs = new String[reults.length()];
            movieNames = new String[reults.length()];
            urlImages = new String[reults.length()];
            rating = new String[reults.length()];
            movieDate = new String[reults.length()];
            movieOverview = new String[reults.length()];
            imageData = new byte [reults.length()] [];
            for(int i = 0; i < reults.length(); i++) {

                try{

                    JSONObject singleMovie = reults.getJSONObject(i);


                    movieNames[i] = singleMovie.getString("original_title");
                    urlImages[i]  = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + singleMovie.getString("poster_path");
                    rating[i] = singleMovie.getString("vote_average");
                    movieDate[i] = singleMovie.getString("release_date");
                    movieOverview[i] = singleMovie.getString("overview");
                    URL url = new URL(urlImages[i]);
                    imageData[i]= downloadUrl(url);

                    Movie movie = new Movie();
                    movie.setName(movieNames[i]);
                    movie.setImageUrl(urlImages[i]);
                    movie.setRating(Float.parseFloat(rating[i]));
                    movie.setDate(movieDate[i]);
                    movie.setOverview(movieOverview[i]);
                    movie.setImageData(imageData[i]);
                    movies.add(movie);



                }catch(Exception e){
                    Log.v("TAGH",e.toString());
                }
            }



        return  movies;
        }

    public String[] getMovieNames(){
        return movieNames  ;

    }

    public String[] getUrlImages() {
        return urlImages;
    }

    public String[] getRating() {
        return rating;
    }

    public String[] getMovieDate() {
        return movieDate;
    }

    public String[] getMovieOverview() {
            return movieOverview;
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

    //CODE FROM STACKOVERFLOW user:RonReiter
    //LINK:http://stackoverflow.com/questions/2295221/java-net-url-read-stream-to-byte
    private byte[] downloadUrl(URL toDownload) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] chunk = new byte[4096];
            int bytesRead;
            InputStream stream = toDownload.openStream();

            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return outputStream.toByteArray();
    }

}
