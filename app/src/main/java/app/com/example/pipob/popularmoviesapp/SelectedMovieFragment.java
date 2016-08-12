package app.com.example.pipob.popularmoviesapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SelectedMovieFragment extends Fragment {
    View v;
    List<Trailer> trailers;
    List<Comments> comments;
    BaseAdapter adapter=null;
    GridView gridView;
    ListView listView;
    String movieApiId="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.activity_selected_movie, container, false);
        Bundle movieAttributes = this.getArguments();
        if (movieAttributes != null) {

            String movie_Name = movieAttributes.getString("movie_Name", "");
            String movie_Thumb = movieAttributes.getString("movie_Thumb", "");
            String movie_Date = movieAttributes.getString("movie_Date", "");
            String movie_Rating = movieAttributes.getString("movie_Rating", "");
            String movie_Overview = movieAttributes.getString("movie_Overview", "");
            movieApiId = movieAttributes.getString("movie_ApiId","");
            byte[] img = movieAttributes.getByteArray("movie_ImageData");

            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length, options);
            ((TextView) v.findViewById(R.id.txt_Detailed_Movie_Title)).setText(movie_Name);
            ;
            ((ImageView) v.findViewById(R.id.img_Detailed_Movie_Poster)).setImageBitmap(bitmap);
            // Picasso.with(this).load((movie_Thumb)).resize(300, 450).centerCrop().into((ImageView) findViewById(R.id.img_Detailed_Movie_Poster));
            ((TextView) v.findViewById(R.id.txt_Detailed_Movie_Date)).setText(movie_Date);
            ((TextView) v.findViewById(R.id.txt_Detailed_Movie_Rating)).setText(movie_Rating);
            ((TextView) v.findViewById(R.id.txt_Detailed_Movie_Overview)).setText(movie_Overview);
            int rat = Math.round(Float.parseFloat(movie_Rating));
            ImageView ratingStar = (ImageView) v.findViewById(R.id.rating_Detailed_Movie_Rating);
            gridView = (GridView) v.findViewById(R.id.trailersGrid);
            listView = (ListView) v.findViewById(R.id.commentsList);

            if (rat<1){
                ratingStar.setImageResource(R.mipmap.ic_popcorn_empty);
            }
            else if (rat>=1 && rat <2){
                ratingStar.setImageResource(R.mipmap.ic_popcorn_01);
            }
            else if (rat>=2 && rat <3){
                ratingStar.setImageResource(R.mipmap.ic_popcorn_02);
            }
            else if (rat>=3 && rat <4){
                ratingStar.setImageResource(R.mipmap.ic_popcorn_03);
            }
            else if (rat>=4 && rat <5){
                ratingStar.setImageResource(R.mipmap.ic_popcorn_04);
            }
            else if (rat>=5 && rat <6){
                ratingStar.setImageResource(R.mipmap.ic_popcorn_05);
            }
            else if (rat>=6 && rat <7){
                ratingStar.setImageResource(R.mipmap.ic_popcorn_06);
            }
            else if (rat>=7 && rat <8){
                ratingStar.setImageResource(R.mipmap.ic_popcorn_07);
            }
            else if (rat>=8 && rat <9){
                ratingStar.setImageResource(R.mipmap.ic_popcorn_08);
            }
            else if (rat>=9 && rat <10){
                ratingStar.setImageResource(R.mipmap.ic_popcorn_09);
            }
            else if (rat>=10){
                ratingStar.setImageResource(R.mipmap.ic_popcorn_10);
            }

            if (getString(R.string.isDualPane).equals("true")) {
                LinearLayout viewerLayout = (LinearLayout) getActivity().findViewById(R.id.viewer);
                viewerLayout.setVisibility(View.VISIBLE);
            }



        }else{
            if (getString(R.string.isDualPane).equals("true")) {
                LinearLayout viewerLayout = (LinearLayout) getActivity().findViewById(R.id.viewer);
                viewerLayout.setVisibility(View.INVISIBLE);
            }


        }
        return v;
    }

    public void loadTrailers(List<Trailer> trailers){
        adapter= new TrailerAdapter(getActivity(),trailers);
        try{
            gridView.setAdapter(adapter);
        }catch(Exception e){}


    }
    public void loadComments(){
        adapter= new CommentsAdapter(comments,getActivity());
        try{
            listView.setAdapter(adapter);
        }catch(Exception e){}


    }

    public void fetchTrailers(){

            FetchTrailersTask moviesT = new FetchTrailersTask();

            moviesT.execute(movieApiId);


    }
    public void fetchComments(){

        FetchCommentsTask commentsT = new FetchCommentsTask();

        commentsT.execute(movieApiId);


    }
    public boolean haveInternet(Activity activity) {
        ConnectivityManager internetManager = (ConnectivityManager)activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo internetInfo = internetManager.getActiveNetworkInfo();
        return internetInfo != null;
    }
    @Override
    public void onStart() {
        super.onStart();
        if(haveInternet(getActivity()) ) {
            fetchTrailers();
            fetchComments();
        }
    }



    public class FetchTrailersTask extends AsyncTask<String,Void,String[]> {
        String LOG_TAG=FetchTrailersTask.class.getSimpleName();


        @Override
        protected void onPostExecute(String[] result) {
            loadTrailers(trailers);

        }

        protected String[] doInBackground(String... Params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;



            try {

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http");
                builder.authority("api.themoviedb.org");
                builder.appendPath("3");
                builder.appendPath("movie");
                builder.appendPath(Params[0]);
                builder.appendPath("videos");
                builder.appendQueryParameter("api_key",getString(R.string.apiMovieKey));
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
                String trailerJson = buffer.toString();

                try{
                    TrailerParser trailerParser = new TrailerParser(getActivity());
                    trailers = trailerParser.getTrailerDataFromJson(trailerJson);

                    //DB db = new DB(ctx);
                    //addOnDb(movies);



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
    public class FetchCommentsTask extends AsyncTask<String,Void,String[]> {
        String LOG_TAG=FetchCommentsTask.class.getSimpleName();


        @Override
        protected void onPostExecute(String[] result) {
            loadComments();

        }

        protected String[] doInBackground(String... Params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;



            try {

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http");
                builder.authority("api.themoviedb.org");
                builder.appendPath("3");
                builder.appendPath("movie");
                builder.appendPath(Params[0]);
                builder.appendPath("reviews");
                builder.appendQueryParameter("api_key",getString(R.string.apiMovieKey));
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
                String commentJson = buffer.toString();

                try{
                    CommentsParser commentsParser = new CommentsParser(getActivity());
                    comments = commentsParser.getCommentsDataFromJson(commentJson);

                    //DB db = new DB(ctx);
                    //addOnDb(movies);



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



