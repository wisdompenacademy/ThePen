package com.thepen.thepen.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thepen.thepen.R;
import com.thepen.thepen.activities.VideoActivity;
import com.thepen.thepen.entity.Video;
import com.thepen.thepen.helper.Constants;
import com.thepen.thepen.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoListAdapter1  extends RecyclerView.Adapter<VideoListAdapter1.MyView> {

    private Context context;
    private ArrayList<Video> cards;
    public class MyView extends RecyclerView.ViewHolder {
        TextView videoName ,authorName ,description,isPaid;
        ImageView imgThumb;
        RelativeLayout blur;
        public MyView(View view) {
            super(view);
            videoName = view.findViewById(R.id.videoName);
            authorName = view.findViewById(R.id.authorName);
            description = view.findViewById(R.id.txtDescription);
            isPaid = view.findViewById(R.id.isPaid);
            imgThumb = view.findViewById(R.id.imgThumb);
            blur = view.findViewById(R.id.blur);
        }
    }
    public VideoListAdapter1(Context context, ArrayList<Video> cards) {
        this.context = context;
        this.cards = cards;
    }
    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.videolist_item, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        holder.videoName.setText(cards.get(position).videoName);
        holder.authorName.setText(cards.get(position).userName);
        holder.description.setText(cards.get(position).description);
        //holder.isPaid.setText(cards.get(position).charge+"");
        if(cards.get(position).charge){
            //holder.isPaid.setVisibility(View.VISIBLE);
            holder.blur.setVisibility(View.VISIBLE);
        }
        Glide.with(context)
                .load(Constants.FILE_URL +cards.get(position).imagePath)
                .placeholder(R.drawable.logo)
                .into(holder.imgThumb);
        holder.imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ASSIGN_TO","On Click "+cards.get(position).video_id);
                if(!cards.get(position).charge){
                    Intent intent = new Intent(context, VideoActivity.class);
                    intent.putExtra("URL",cards.get(position).videoPath);
                    intent.putExtra("index",position+"");
                    Log.d("Index",""+position);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST",(Serializable)cards);
                    intent.putExtra("BUNDLE",args);
                    context.startActivity(intent);
                }else{
                    ShowAdminList();
                }
            }
        });
        holder.imgThumb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                ShowAdminList();
                return false;
            }
        });
    }
    private void ShowAdminList() {
        Utils.showAlertDialog(context,"Please purchase this course to play this video");
    }
    @Override
    public int getItemCount() {
        return cards.size();
    }
}
