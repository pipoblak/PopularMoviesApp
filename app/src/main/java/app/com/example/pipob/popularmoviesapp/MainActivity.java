package app.com.example.pipob.popularmoviesapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BaseAdapter adapter=null;
    GridView gridView;
    Uri.Builder builder;
    List<Movie> movies;
    String movieJson;
    View v;
    Context context;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView= (GridView) findViewById(R.id.gridViewMovies);
        context=this;
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), SelectedMovieActivity.class);

                intent.putExtra("movie_Name", movies.get(position).getName());
                intent.putExtra("movie_Thumb", movies.get(position).getImageUrl());
                intent.putExtra("movie_Date", movies.get(position).getDate());
                intent.putExtra("movie_Rating", movies.get(position).getRating() + "");
                intent.putExtra("movie_Overview", movies.get(position).getOverview());
                intent.putExtra("movie_ImageData", movies.get(position).getImageData());

                startActivity(intent);
            }
        });


    }
    //Code based in beerLantern's code in Stackoverflow http://stackoverflow.com/questions/4086159/checking-internet-connection-on-android
    public boolean haveInternet(Activity activity) {
        ConnectivityManager internetManager = (ConnectivityManager)activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo internetInfo = internetManager.getActiveNetworkInfo();
        return internetInfo != null;
    }

    public void updateMovies(Context ctx){

        if (haveInternet(this) == true)
            fetchMovie();
        else
            setmovies();

    }
    public void addOnDb(List<Movie> movies){
        DB db = new DB(context);
        db.insertMovies(movies);

    }

    public void fetchMovie(){

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
        updateMovies(this);

    }
    public void setmovies(){
        DB db = new DB (this);
        movies= db.searchAllMovies();
        adapter= new MovieAdapter(this,movies);
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
                    movies = movieparser.getMovieDataFromJson(movieJson);
                    addOnDb(movies);

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
