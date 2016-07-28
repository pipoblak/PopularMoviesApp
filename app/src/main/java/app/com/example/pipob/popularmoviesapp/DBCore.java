package app.com.example.pipob.popularmoviesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by FirstPlace on 12/07/2016.
 */
public class DBCore extends SQLiteOpenHelper {
    private static final String NAME_DB="PopularMoviesApp";
    private static final int VERSION_DB=4;
    public DBCore(Context context){
        super(context,NAME_DB,null,VERSION_DB);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Movie(_id integer primary key autoincrement, name text not null , date text not null, imageUrl text not null, rating real not null, overview text not null,imageData blob not null,filter text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Movie;");
        onCreate(db);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
