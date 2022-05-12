package com.thepen.thepen.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thepen.thepen.MainActivity;
import com.thepen.thepen.R;
import com.thepen.thepen.activities.FragmentContainer;
import com.thepen.thepen.activities.TeacherRegistration;
import com.thepen.thepen.activities.VideoActivity;
import com.thepen.thepen.entity.Video;
import com.thepen.thepen.helper.Constants;
import com.thepen.thepen.helper.UserInfo;
import com.thepen.thepen.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

public class AdminVideoListAdp  extends RecyclerView.Adapter<AdminVideoListAdp.MyView> {

    private Context context;
    private ArrayList<Video> cards;
    public class MyView extends RecyclerView.ViewHolder {
        TextView videoName ,authorName ,description,isPaid,txtVideoInfo;
        ImageView imgThumb;
        public MyView(View view) {
            super(view);
            videoName = view.findViewById(R.id.videoName);
            authorName = view.findViewById(R.id.authorName);
            description = view.findViewById(R.id.txtDescription);
            isPaid = view.findViewById(R.id.isPaid);
            imgThumb = view.findViewById(R.id.imgThumb);
            txtVideoInfo = view.findViewById(R.id.txtVideoInfo);
        }
    }

    public AdminVideoListAdp(Context context, ArrayList<Video> cards) {
        this.context = context;
        this.cards = cards;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.videolist_item, parent, false);
        return new MyView(itemView);
    }
    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        holder.videoName.setText(cards.get(position).videoName);
        holder.authorName.setText(cards.get(position).userName);
        holder.description.setText(cards.get(position).description);
        holder.isPaid.setText(cards.get(position).charge+"");
        holder.txtVideoInfo.setText(cards.get(position).educationBoard+" "
                +cards.get(position).medium+" "
        +cards.get(position).std+" " + cards.get(position).subject+" ");
        holder.txtVideoInfo.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(Constants.FILE_URL +cards.get(position).imagePath)
                .placeholder(R.drawable.logo)
                .into(holder.imgThumb);
        holder.imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ASSIGN_TO","On Click "+cards.get(position).video_id);
                Intent intent = new Intent(context, FragmentContainer.class);
                Bundle args = new Bundle();
                args.putSerializable("Video",(Serializable)cards.get(position));
                intent.putExtra("BUNDLE",args);
                intent.putExtra("FRAGMENT","AssignVideo");
                if(UserInfo.getSharedInstance().getRole().equalsIgnoreCase("Role_super_admin")){
                    intent.putExtra("VERIYFY",false);
                }else {
                    intent.putExtra("VERIYFY",true);
                }
                context.startActivity(intent);
            }
        });
        holder.imgThumb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ShowAdminList();
                return false;
            }
        });
    }

    private void ShowAdminList() {
        Utils.showAlertDialog(context,"Admin2 List");
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }
}
