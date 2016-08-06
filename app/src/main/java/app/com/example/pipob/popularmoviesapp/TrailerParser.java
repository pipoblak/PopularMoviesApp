package app.com.example.pipob.popularmoviesapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pipob on 05/08/2016.
 */
public class TrailerParser {
    String key[];
    Context ctx;

    public TrailerParser(Context context){
        ctx = context;

    }

    public List<Trailer> getTrailerDataFromJson(String json)
            throws JSONException {

        List<Trailer> keys = new ArrayList<Trailer>();


        final String OWM_RESULTS = "results";

        JSONObject moviesJson= new JSONObject(json);
        JSONArray reults = moviesJson.getJSONArray(OWM_RESULTS);




        String[] resultStrs = new String[reults.length()];

        key = new String[reults.length()];

        for(int i = 0; i < reults.length(); i++) {

            try{

                JSONObject singleTrailer = reults.getJSONObject(i);


                key[i] = singleTrailer.getString("key");

                Trailer trailer = new Trailer();
                trailer.setId(key[i]);
                keys.add(trailer);



            }catch(Exception e){
                Log.v("TAGH",e.toString());
            }
        }



        return  keys;
    }

 }
