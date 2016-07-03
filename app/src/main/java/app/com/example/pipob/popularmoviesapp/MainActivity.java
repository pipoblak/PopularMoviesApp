package app.com.example.pipob.popularmoviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    String rating[]={""};
    String movieDate[]={""};
    String movieOverview[]={""};
    View v;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView= (GridView) findViewById(R.id.gridViewMovies);
        gridView.setAdapter(adapter);



    }

    public void updateMovies(){
        FetchMoviesTask moviesT = new FetchMoviesTask();
        SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(this);
        String keylocation = getString(R.string.pref_filter_key);
        String defaultLocation=getString(R.string.pref_filter_default);
        String filter = settings.getString(keylocation,defaultLocation);
        moviesT.execute(filter);

    }
    @Override
    public void onStart(){
        super.onStart();
        updateMovies();

    }
    public void setmovies(){
        adapter= new ImageAdapter(this,movieName,urls,rating,movieDate,movieOverview);
        gridView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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



            try {

                builder = new Uri.Builder();
                builder.scheme("http");

                builder.authority("api.themoviedb.org");
                builder.appendPath("3");
                builder.appendPath("movie");
                builder.appendPath(Params[0]);
                builder.appendQueryParameter("api_key",getString(R.string.apiMovieKey));
                //builder.appendQueryParameter("sort_by", "popularity.desc");
                URL url = new URL(builder.build().toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                movieJson = buffer.toString();
                MovieParser movieparser = new MovieParser();
                try{
                    movieparser.getMovieDataFromJson(movieJson);
                    movieName =movieparser.getMovieNames();
                    urls=movieparser.getUrlImages();
                    rating=movieparser.getRating();
                    movieDate = movieparser .getMovieDate();
                    movieOverview = movieparser.getMovieOverview();

                }catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);

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
