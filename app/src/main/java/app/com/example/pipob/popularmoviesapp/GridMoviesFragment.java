package app.com.example.pipob.popularmoviesapp;

import android.app.Activity;
import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by pipob on 31/07/2016.
 */
public class GridMoviesFragment extends Fragment {
    BaseAdapter adapter=null;
    GridView gridView;
    Uri.Builder builder;
    List<Movie> movies;
    String movieJson,filter;
    SharedPreferences settings;
    View v;

    DB db ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.grid_movies, container, false);
        gridView = (GridView) v.findViewById(R.id.gridViewMovies);
        gridView.setAdapter(adapter);
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (getString(R.string.isDualPane).equals("true")) {
            gridView.setNumColumns(1);
        }

        return v;

    }


    public void actionFragment(int position){

        Bundle movieAttributes = new Bundle();

        movieAttributes.putString("movie_Name", movies.get(position).getName());
        movieAttributes.putString("movie_Name", movies.get(position).getName());
        movieAttributes.putString("movie_Thumb", movies.get(position).getImageUrl());
        movieAttributes.putString("movie_Date", movies.get(position).getDate());
        movieAttributes.putString("movie_Rating", movies.get(position).getRating() + "");
        movieAttributes.putString("movie_Overview", movies.get(position).getOverview());
        movieAttributes.putByteArray("movie_ImageData", movies.get(position).getImageData());
        Fragment selectedMovie = new SelectedMovieFragment();
        selectedMovie.setArguments(movieAttributes);

        if(getString(R.string.isDualPane).equals("true")) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.viewer, selectedMovie)
                    .commit();
        }
        else{
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, selectedMovie)
                    .addToBackStack(null)
                    .commit();

        }
    }
    //Code based in beerLantern's code in Stackoverflow http://stackoverflow.com/questions/4086159/checking-internet-connection-on-android
    public boolean haveInternet(Activity activity) {
        ConnectivityManager internetManager = (ConnectivityManager)activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo internetInfo = internetManager.getActiveNetworkInfo();
        return internetInfo != null;
    }

    public  void updateMovies(Context ctx){

        if (haveInternet(getActivity())){

            fetchMovie();
        }else{
            setmovies();
        }

    }

    public void addOnDb(List<Movie> movies){
        db = new DB(getActivity());
        db.insertMovies(movies);
        db.close();

    }

    public void getFilter(){
        settings= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String keyFilter = getString(R.string.pref_filter_key);
        String defaultFilter=getString(R.string.pref_filter_default);
        filter = settings.getString(keyFilter,defaultFilter);
    }
    public void fetchMovie(){

        FetchMoviesTask moviesT = new FetchMoviesTask();
        getFilter();
        moviesT.execute(filter);


    }
    @Override
    public void onStart(){
        super.onStart();

        getFilter();

        updateMovies(getActivity());



    }


    public void setmovies(){
        db = new DB (getActivity());
        movies= db.searchAllMovies(filter);
        adapter= new MovieAdapter(getActivity(),movies);
        gridView.setAdapter(adapter);
        try{
            if(getString(R.string.isDualPane).equals("true"))
                actionFragment(0);
        }catch(Exception e){

        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            case R.id.action_refresh:
                updateMovies(getActivity());
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
                    movies = movieparser.getMovieDataFromJson(movieJson,filter);
                    db = new DB(getActivity());
                    db.deleteAllMovies(filter);
                    db.close();
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