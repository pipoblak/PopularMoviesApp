package app.com.example.pipob.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    BaseAdapter adapter=null;
    GridView gridView;
    Uri.Builder builder;
    List<Movie> movies;
    String movieJson,filter;
    View v;
    Context context;
    DB db ;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if(getString(R.string.isDualPane).equals("true")) {
            getFragmentManager().beginTransaction()
                    .add(R.id.viewer, new SelectedMovieFragment())
                    .commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.list, new GridMoviesFragment())
                    .commit();
        }
        else{
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new GridMoviesFragment())
                    .addToBackStack(null)
                    .commit();
        }


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


}
