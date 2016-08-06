package app.com.example.pipob.popularmoviesapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by pipob on 05/08/2016.
 */
public class TrailerAdapter extends BaseAdapter {
    Context mContext;
    List<Trailer> trailers;
    LayoutInflater inflater;

    public TrailerAdapter(Context c,List<Trailer> trailers) {
        mContext = c;
        this.trailers=trailers;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.grid_trailerholder_layout, null);
        TextView trailerName = (TextView) view.findViewById(R.id.trailerName);
        trailerName.setText("Trailer " + position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_alpha);
                v.startAnimation(animation);
                watchYoutubeVideo(trailers.get(position).getId(),mContext);
            }
        });

        return view;


    }





//Code by http://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent made by Evin1_
    public  void watchYoutubeVideo(String id,Context c){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            c.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));


            c.startActivity(intent);
        }
    }
}
