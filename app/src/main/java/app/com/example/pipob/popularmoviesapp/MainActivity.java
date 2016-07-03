package app.com.example.pipob.popularmoviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    BaseAdapter adapter=null;
    GridView gridView;
    Uri.Builder builder;
    String movieJson;
    String movieName[]={""};
    String urls[]={""};
    int rating[]={};
    View v;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView= (GridView) findViewById(R.id.gridViewMovies);
        FetchMoviesTask moviesT = new FetchMoviesTask();
        moviesT.execute();

        gridView.setAdapter(adapter);



    }
    public void setmovies(){
        adapter= new ImageAdapter(this,movieName,urls,rating);
        gridView.setAdapter(adapter);

    }
    public class FetchMoviesTask extends AsyncTask<String,Void,String[]> {
        String LOG_TAG=FetchMoviesTask.class.getSimpleName();


        @Override
        protected void onPostExecute(String[] result) {
            setmovies();
        }

        protected String[] doInBackground(String... Params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.


            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                builder = new Uri.Builder();
                builder.scheme("http");
                https://api.themoviedb.org/3/discover/movie?api_key=31155e5c4ccfbb1de9a891b48154c2ce&sort_by=popularity.desc
                builder.authority("api.themoviedb.org");
                builder.appendPath("3");
                builder.appendPath("discover");
                builder.appendPath("movie");
                builder.appendQueryParameter("api_key",getString(R.string.apiMovieKey));
                builder.appendQueryParameter("sort_by", "popularity.desc");
                URL url = new URL(builder.build().toString());
                //Log.v("a",builder.build().toString() );

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJson = buffer.toString();

                MovieParser movieparser = new MovieParser();
                try{
                    movieparser.getMovieDataFromJson(movieJson);
                    movieName =movieparser.getMovieNames();
                    urls=movieparser.getUrlImages();
                    rating=movieparser.getRating();
                }catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            }  finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }


            return null;
        }

    }


}
