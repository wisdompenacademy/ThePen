package com.thepen.thepen.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thepen.thepen.R;
import com.thepen.thepen.entity.Video;
import com.thepen.thepen.helper.Constants;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter  extends BaseAdapter {
    Context context;
    List<Video> videoList;

    public VideoListAdapter(Context context, ArrayList<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int i) {
        return videoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        ReadvideoList_Response readvideoListResponse = videoList.get(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.videolist_item, viewGroup, false);
        }
        TextView videoName = view.findViewById(R.id.videoName);
        TextView authorName = view.findViewById(R.id.authorName);
        TextView description = view.findViewById(R.id.txtDescription);
        TextView isPaid = view.findViewById(R.id.isPaid);
        ImageView imgThumb = view.findViewById(R.id.imgThumb);
        RelativeLayout blur = view.findViewById(R.id.blur);
        videoName.setText(videoList.get(i).videoName);
        authorName.setText("By "+videoList.get(i).userName);
        description.setText("Description: "+videoList.get(i).description);
        if(videoList.get(i).charge){
            isPaid.setVisibility(View.VISIBLE);
            blur.setVisibility(View.VISIBLE);
        }
        Glide.with(context)
                .load(Constants.FILE_URL +videoList.get(i).imagePath)
                .placeholder(R.drawable.logo_with_text)
                .into(imgThumb);
        return view;
    }
}