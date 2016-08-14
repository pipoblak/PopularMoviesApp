package app.com.example.pipob.popularmoviesapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pipob on 03/07/2016.
 */
public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    LayoutInflater inflater;
    FragmentManager fm;
    Toast toast ;

    List<Movie> movies;
    public class Holder
    {
        TextView movieTitle,ratingTitle;
        ImageView movieThumb,ratingStar;


    }
    public MovieAdapter(Context c,List<Movie> movies) {
        mContext = c;
        this.movies=movies;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toast=new Toast(mContext);
    }

    public int getCount() {
        return movies.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View view ;


            view = inflater.inflate(R.layout.grid_holder_layout, null);
            holder.movieThumb = (ImageView) view.findViewById(R.id.img_thumb_movie);
            holder.movieTitle = (TextView) view.findViewById(R.id.txt_movie_title);
            holder.ratingTitle = (TextView) view.findViewById(R.id.txt_rating_Title);
            holder.ratingStar = (ImageView) view.findViewById(R.id.ratingStar) ;
            fm = ((Activity) mContext).getFragmentManager();
            holder.movieTitle.setText(movies.get(position).getName());
            holder.ratingTitle.setText(movies.get(position).getRating() + "");

            holder.movieThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.ratingStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_rotate);
                    v.startAnimation(animation);
                    DB db = new DB(mContext);
                    if( db.insertFavored(movies.get(position).getApiID()))
                        showToast(mContext,"Filme Adicionado aos Favoritos!");
                    else
                        showToast(mContext,"Filme Removido dos Favoritos!");



                }
            });

            int rat = Math.round (movies.get(position).getRating());
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

        byte[] img = movies.get(position).getImageData();
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length, options);
        holder.movieThumb.setImageBitmap(bitmap);
        //Picasso.with(mContext).load((bitmap)).resize(400, 550).centerCrop().into(holder.movieThumb);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_alpha);
                v.startAnimation(animation);
                actionFragment(position);
            }
        });

        return view;
    }
    public void actionFragment(int position){

        Bundle movieAttributes = new Bundle();

        movieAttributes.putString("movie_Name", movies.get(position).getName());
        movieAttributes.putString("movie_Name", movies.get(position).getName());
        movieAttributes.putString("movie_Thumb", movies.get(position).getImageUrl());
        movieAttributes.putString("movie_Date", movies.get(position).getDate());
        movieAttributes.putString("movie_Rating", movies.get(position).getRating() + "");
        movieAttributes.putString("movie_Overview", movies.get(position).getOverview());
        movieAttributes.putByteArray("movie_ImageData", movies.get(position).getImageData());
        movieAttributes.putString("movie_ApiId", movies.get(position).getApiID()+ "");
        Fragment selectedMovie = new SelectedMovieFragment();
        selectedMovie.setArguments(movieAttributes);

        if(mContext.getString(R.string.isDualPane).equals("true")) {
            fm.beginTransaction()
                    .replace(R.id.viewer, selectedMovie)
                    .commit();
        }
        else{
            fm.beginTransaction()
                    .replace(R.id.container, selectedMovie)
                    .addToBackStack(null)
                    .commit();

        }
    }
    public void showToast(Context c, String message){
        try {
            toast.cancel();
        }catch(Exception e){}


        toast = Toast.makeText(c,message ,Toast.LENGTH_SHORT);
        toast.show();

    }

}