package app.com.example.pipob.popularmoviesapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class SelectedMovieFragment extends Fragment {
    View v;

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
            ((RatingBar) v.findViewById(R.id.rating_Detailed_Movie_Rating)).incrementProgressBy(rat);

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
}



