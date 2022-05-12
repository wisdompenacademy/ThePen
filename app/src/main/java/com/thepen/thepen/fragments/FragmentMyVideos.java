package com.thepen.thepen.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thepen.thepen.MainActivity;
import com.thepen.thepen.R;
import com.thepen.thepen.ThePen;
import com.thepen.thepen.activities.VideoActivity;
import com.thepen.thepen.adapters.VideoListAdapter;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.entity.Video;
import com.thepen.thepen.entity.VideoList;
import com.thepen.thepen.helper.UserInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class FragmentMyVideos extends Fragment {
    private ListView videoList;
    View view;
    private ArrayList<Video> videos;
    private ProgressBar loader;
    private TextView txtNoVidFound;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_videos, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        videoList = view.findViewById(R.id.videosList);
        loader = view.findViewById(R.id.loader);
        txtNoVidFound = view.findViewById(R.id.txtNoVidFound);
        if(UserInfo.getSharedInstance().getRole().equals("ROLE_TEACHER")){
            ApiUtil.getVideoList(null,null,
                    null,null,
                    UserInfo.getSharedInstance().getRole(),
                    UserInfo.getSharedInstance().getUserId(),
                    new GetVideoListHandler());
        }
        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), VideoActivity.class);
                intent.putExtra("URL",videos.get(i).videoPath);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)videos);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });
    }
    class GetVideoListHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("VideoList",""+msg.obj);
            loader.setVisibility(View.GONE);
            VideoList mVideoList =(VideoList) msg.obj;
            videos=mVideoList.getUploadVideoList();
            VideoListAdapter videoListAdapter = new VideoListAdapter(getContext(), videos);
            videoList.setAdapter(videoListAdapter);
            if(videos.size()==0) txtNoVidFound.setVisibility(View.VISIBLE);else  txtNoVidFound.setVisibility(View.GONE);
        }
    }
}
