package app.com.example.pipob.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SelectedMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String movie_Name= this.getIntent().getStringExtra("movie_Name");
        String movie_Thumb= this.getIntent().getStringExtra("movie_Thumb");
        String movie_Date = this.getIntent().getStringExtra("movie_Date");
        String movie_Rating = this.getIntent().getStringExtra("movie_Rating");
        String movie_Overview = this.getIntent().getStringExtra("movie_Overview");

        setContentView(R.layout.activity_selected_movie);
        ((TextView)  findViewById(R.id.txt_Detailed_Movie_Title) ).setText(movie_Name); ;
        Picasso.with(this).load((movie_Thumb)).resize(300, 450).centerCrop().into((ImageView) findViewById(R.id.img_Detailed_Movie_Poster));
        ((TextView)  findViewById(R.id.txt_Detailed_Movie_Date) ).setText(movie_Date);
        ((TextView)  findViewById(R.id.txt_Detailed_Movie_Rating) ).setText(movie_Rating);
        ((TextView)  findViewById(R.id.txt_Detailed_Movie_Overview) ).setText(movie_Overview);
        int rat = Math.round (Float.parseFloat(movie_Rating));
        ((RatingBar) findViewById(R.id.rating_Detailed_Movie_Rating)).incrementProgressBy(rat);
    }


}
