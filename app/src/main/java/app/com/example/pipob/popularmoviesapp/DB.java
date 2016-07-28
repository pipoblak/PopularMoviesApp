package app.com.example.pipob.popularmoviesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        values.put("imageData",movie.getImageData());
        values.put("filter",movie.getFilter());

        db.insert("Movie",null,values);
    }

    public void insertMovies(List<Movie> movies){
        ContentValues values = new ContentValues();

       for (int i = 0;i<movies.size();i++){

            values.put("name",movies.get(i).getName());
            values.put("date",movies.get(i).getDate());
            values.put("imageUrl",movies.get(i).getImageUrl());
            values.put("rating",movies.get(i).getRating());
            values.put("overview",movies.get(i).getOverview());
            values.put("imageData",movies.get(i).getImageData());
            values.put("filter",movies.get(i).getFilter());

            try{
                db.insert("Movie",null,values);
                }
            catch(Exception e){

            }

        }

    }

    public void update(Movie movie){
        ContentValues values = new ContentValues();

        values.put("name",movie.getName());
        values.put("date",movie.getDate());
        values.put("imageUrl",movie.getImageUrl());
        values.put("rating",movie.getRating());
        values.put("overview",movie.getOverview());
        values.put("imageData",movie.getImageData());
        values.put("filter",movie.getFilter());
        db.update("Device",values,"_id= ?",new String[]{"" + movie.getId()});
    }

    public void delete(Movie movie){
        db.delete("Movie","_id= ?",new String[]{"" + movie.getId()});
    }

    public void deleteAllMovies(String filter){
        db.delete("Movie","filter='" + filter + "'",null);
       }

    public List<Movie> searchAllMovies(String filter){
        List <Movie> listMovies = new ArrayList<Movie>();
        String[] columns = {"_id","name","date","imageUrl","rating","overview","imageData","filter"};

        Cursor cursor = db.query("Movie",columns,"filter='"+filter+"'",null,null,null,null);


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
                movie.setImageData(cursor.getBlob(6));
                movie.setFilter(cursor.getString(7));
                listMovies.add(movie);
            }while(cursor.moveToNext());

        }
        cursor.close();
        return listMovies;
    }

}
