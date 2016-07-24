package app.com.example.pipob.popularmoviesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FirstPlace on 12/07/2016.
 */
public class DB {
    private SQLiteDatabase db;

    public DB(Context context){
        DBCore auxDb= new DBCore(context);
        db = auxDb.getWritableDatabase();
    }

    public void insert(Movie movie){
        ContentValues values = new ContentValues();

        values.put("name",movie.getName());
        values.put("date",movie.getDate());
        values.put("imageUrl",movie.getImageUrl());
        values.put("rating",movie.getRating());
        values.put("overview",movie.getOverview());

        db.insert("Movie",null,values);
    }

    public void update(Movie movie){
        ContentValues values = new ContentValues();

        values.put("name",movie.getName());
        values.put("date",movie.getDate());
        values.put("imageUrl",movie.getImageUrl());
        values.put("rating",movie.getRating());
        values.put("overview",movie.getOverview());
        db.update("Device",values,"_id= ?",new String[]{"" + movie.getId()});
    }

    public void delete(Movie movie){
        db.delete("Movie","_id= ?",new String[]{"" + movie.getId()});
    }

    public List<Movie> searchAllMovies(){
        List <Movie> listMovies = new ArrayList<Movie>();
        String[] columns = {"_id","name","date","imageUrl","rating","overview"};

        Cursor cursor = db.query("Movie",columns,null,null,null,null,null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();

            do{

                Movie movie = new Movie();
                movie.setId(cursor.getLong(0));
                movie.setName(cursor.getString(1));
                movie.setDate(cursor.getString(2));
                movie.setImageUrl(cursor.getString(3));
                movie.setRating(cursor.getFloat(4));
                movie.setOverview(cursor.getString(5));
                listMovies.add(movie);
            }while(cursor.moveToNext());

        }
        cursor.close();
        return listMovies;
    }

}
