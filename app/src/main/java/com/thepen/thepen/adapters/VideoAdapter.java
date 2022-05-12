package com.thepen.thepen.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thepen.thepen.R;
import com.thepen.thepen.helper.Constants;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyView> {

    private Context context;
    private ArrayList<String> strings;
    public class MyView extends RecyclerView.ViewHolder {
        TextView videoName;
        ImageView imgThumb;
        public MyView(View view) {
            super(view);
            videoName = view.findViewById(R.id.video_name);
            imgThumb = view.findViewById(R.id.img_thumbnail);
        }
    }
    public VideoAdapter(Context context, ArrayList<String> strings) {
        this.context = context;
        this.strings = strings;
    }
    @NonNull
    @Override
    public VideoAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video_items, parent, false);
        return new VideoAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.MyView holder, int position) {
        holder.videoName.setText(strings.get(position));
        Bitmap thumb  = getThumb(strings.get(position));
//        holder.imgThumb.setImageBitmap(thumb);
        Glide.with(context)
                .load(thumb)
                .placeholder(R.drawable.logo)
                .into(holder.imgThumb);
        holder.imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.imgThumb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return strings.size();
    }

    private Bitmap getThumb(String path){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Bitmap myBitmap=null;
        retriever.setDataSource(path);
        return myBitmap = retriever.getFrameAtTime(30 * 1000000,
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
    }
}
