package app.com.example.pipob.popularmoviesapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pipob on 11/08/2016.
 */
public class CommentsParser {
    String author [] ;
    String content [] ;
    String url[];
    Context ctx;

    public CommentsParser(Context context){
        ctx = context;

    }

    public List<Comments> getCommentsDataFromJson(String json)
            throws JSONException {

        List<Comments>  comments = new ArrayList<Comments>();


        final String OWM_RESULTS = "results";

        JSONObject moviesJson= new JSONObject(json);
        JSONArray reults = moviesJson.getJSONArray(OWM_RESULTS);




        String[] resultStrs = new String[reults.length()];
        author = new String[reults.length()];
        content = new String[reults.length()];
        url = new String[reults.length()];

        for(int i = 0; i < reults.length(); i++) {

            try{

                JSONObject singleTrailer = reults.getJSONObject(i);


                author[i] = singleTrailer.getString("author");
                content[i] = singleTrailer.getString("content");
                url[i] = singleTrailer.getString("url");

                Comments comment = new Comments();
                comment.setAuthor(author[i]);
                comment.setContent(content[i]);
                comment.setUrl(url[i]);
                comments.add(comment);



            }catch(Exception e){
                Log.v("TAGH",e.toString());
            }
        }



        return  comments;
    }

}
