package com.thepen.thepen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thepen.thepen.R;
import com.thepen.thepen.entity.Video;
import com.thepen.thepen.helper.Constants;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter2   extends BaseAdapter {
    Context context;
    List<Video> cards;

    public VideoListAdapter2(Context context, ArrayList<Video> videoList) {
        this.context = context;
        this.cards = videoList;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int i) {
        return cards.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.video_list_item2, viewGroup, false);
        }
        TextView videoName = view.findViewById(R.id.videoName);
        TextView authorName = view.findViewById(R.id.authorName);
        ImageView imgThumb = view.findViewById(R.id.imgThumb);
        videoName.setText(cards.get(i).videoName);
        authorName.setText("By "+cards.get(i).userName);
        Glide.with(context)
                .load(Constants.FILE_URL +cards.get(i).imagePath)
                .into(imgThumb);
        return view;
    }
}