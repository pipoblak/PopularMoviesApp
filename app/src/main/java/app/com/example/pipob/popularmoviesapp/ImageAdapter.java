package app.com.example.pipob.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by pipob on 03/07/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    LayoutInflater inflater;
    String movies[];
    String urls[];
    String rating[];
    String movieDate[];
    String movieOverview[];
    public class Holder
    {
        TextView movie_Title,rating_Title;
        ImageView movie_Thumb;
        RatingBar  ratingStar;

    }
    public ImageAdapter(Context c,String moviesNames[],String imageUrls[],String rat[],String movieDat[],String movieOverv[]) {
        urls=imageUrls;
        movies=moviesNames;
        rating=rat;
        movieDate=movieDat;
        movieOverview=movieOverv;
        mContext = c;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return movies.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View view = convertView;


            view = inflater.inflate(R.layout.grid_holder_layout, null);
            holder.movie_Thumb = (ImageView) view.findViewById(R.id.img_thumb_movie);
            holder.movie_Title = (TextView) view.findViewById(R.id.txt_movie_title);
            holder.rating_Title = (TextView) view.findViewById(R.id.txt_rating_Title);
            holder.movie_Title.setText(movies[position]);
            holder.rating_Title.setText(rating[position]);
            holder.movie_Thumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.ratingStar = (RatingBar) view.findViewById(R.id.ratingStar) ;
            holder.ratingStar.setMax(10);
            int rat = Math.round (Float.parseFloat(rating[position]));
            holder.ratingStar.incrementProgressBy(rat);



        Picasso.with(mContext).load((urls[position])).resize(300, 450).centerCrop().into(holder.movie_Thumb);

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(mContext, SelectedMovie.class);
                    intent.putExtra("movie_Name", movies[position]);
                    intent.putExtra("movie_Thumb", urls[position]);
                    intent.putExtra("movie_Date", movieDate[position]);
                    intent.putExtra("movie_Rating", rating[position]);
                    intent.putExtra("movie_Overview", movieOverview[position]);

                    mContext.startActivity(intent);



            }
        });
        return view;
    }



}