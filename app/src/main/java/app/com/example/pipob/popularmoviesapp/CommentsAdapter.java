package app.com.example.pipob.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pipob on 11/08/2016.
 */
public class CommentsAdapter extends BaseAdapter {
    List<Comments> comments ;
    Context mContext;
    LayoutInflater inflater;

    public CommentsAdapter( List<Comments> comments, Context mContext){
        this.comments=comments;
        this.mContext=mContext;

    }

    @Override
    public int getCount() {
        return comments.size();
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
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_comments_holder, null);
        TextView authorName = (TextView) view.findViewById(R.id.txtAuthor);
        TextView content = (TextView) view.findViewById(R.id.txtContent);

        authorName.setText(comments.get(position).getAuthor() + " - ");
        content.setText(comments.get(position).getContent());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_alpha);
                v.startAnimation(animation);

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse( comments.get(position).getUrl()));
                mContext.startActivity(i);

            }
        });

        return view;
    }
}
