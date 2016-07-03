package app.com.example.pipob.popularmoviesapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

/**
 * Created by pipob on 03/07/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    LayoutInflater inflater;
    String movies[];
    String urls[];
    int rating[];
    public class Holder
    {
        TextView movie_Title;
        ImageView movie_Thumb;
        RatingBar  ratingStar;
    }
    public ImageAdapter(Context c,String moviesNames[],String imageUrls[],int rat[]) {
        urls=imageUrls;
        movies=moviesNames;
        rating=rat;
        mContext = c;
         inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return urls.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View view = convertView;


            view = inflater.inflate(R.layout.grid_layout, null);
            holder.movie_Thumb = (ImageView) view.findViewById(R.id.img_thumb_movie);
            holder.movie_Title = (TextView) view.findViewById(R.id.txt_movie_title);
            holder.movie_Title.setText(movies[position]);
            holder.movie_Thumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.ratingStar = (RatingBar) view.findViewById(R.id.ratingStar) ;
            holder.ratingStar.setStepSize((float)0.10);
            int rat = rating[position];
            holder.ratingStar.incrementProgressBy(rat);



        Picasso.with(mContext).load((urls[position])).resize(300, 450).centerCrop().into(holder.movie_Thumb);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(mContext, "You Clicked "+urls[position], Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    // references to our images


}