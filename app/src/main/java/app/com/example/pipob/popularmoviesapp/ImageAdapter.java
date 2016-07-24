package app.com.example.pipob.popularmoviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        TextView movieTitle,ratingTitle;
        ImageView movieThumb,ratingStar;


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
            holder.movieThumb = (ImageView) view.findViewById(R.id.img_thumb_movie);
            holder.movieTitle = (TextView) view.findViewById(R.id.txt_movie_title);
            holder.ratingTitle = (TextView) view.findViewById(R.id.txt_rating_Title);
            holder.movieTitle.setText(movies[position]);
            holder.ratingTitle.setText(rating[position]);
            holder.movieThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.ratingStar = (ImageView) view.findViewById(R.id.ratingStar) ;

            int rat = Math.round (Float.parseFloat(rating[position]));
            if (rat<1){
                holder.ratingStar.setImageResource(R.mipmap.ic_popcorn_empty);
            }
            else if (rat>=1 && rat <2){
                holder.ratingStar.setImageResource(R.mipmap.ic_popcorn_01);
            }
            else if (rat>=2 && rat <3){
                holder.ratingStar.setImageResource(R.mipmap.ic_popcorn_02);
            }
            else if (rat>=3 && rat <4){
                holder.ratingStar.setImageResource(R.mipmap.ic_popcorn_03);
            }
            else if (rat>=4 && rat <5){
                holder.ratingStar.setImageResource(R.mipmap.ic_popcorn_04);
            }
            else if (rat>=5 && rat <6){
                holder.ratingStar.setImageResource(R.mipmap.ic_popcorn_05);
            }
            else if (rat>=6 && rat <7){
                holder.ratingStar.setImageResource(R.mipmap.ic_popcorn_06);
            }
            else if (rat>=7 && rat <8){
                holder.ratingStar.setImageResource(R.mipmap.ic_popcorn_07);
            }
            else if (rat>=8 && rat <9){
                holder.ratingStar.setImageResource(R.mipmap.ic_popcorn_08);
            }
            else if (rat>=9 && rat <10){
                holder.ratingStar.setImageResource(R.mipmap.ic_popcorn_09);
            }
            else if (rat>=10){
                holder.ratingStar.setImageResource(R.mipmap.ic_popcorn_10);
            }



        Picasso.with(mContext).load((urls[position])).resize(400, 550).centerCrop().into(holder.movieThumb);
        return view;
    }



}