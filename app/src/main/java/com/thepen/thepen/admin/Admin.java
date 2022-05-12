package com.thepen.thepen.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.thepen.thepen.MainActivity;
import com.thepen.thepen.R;
import com.thepen.thepen.activities.Login;
import com.thepen.thepen.adapters.VideoListAdapter1;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.entity.Video;
import com.thepen.thepen.entity.VideoList;
import com.thepen.thepen.fragments.FragmentMyVideos;
import com.thepen.thepen.helper.UserInfo;

import java.util.ArrayList;

public class Admin  extends Fragment {
    private TabLayout tabLayout;
    private ImageView backArrow;
    private RecyclerView videoList;
    private LinearLayoutManager mLinearLayoutManager ;
    private ArrayList<Video> videos;
    private TextView txtNoVidFound;
    View view;
    public Admin( ) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        tabLayout = view.findViewById(R.id.tablayout);
        videoList = view.findViewById(R.id.videosList);
        txtNoVidFound = view.findViewById(R.id.txtNoVidFound);
        tabLayout.addTab(tabLayout.newTab().setText("Videos To Verify"));
        tabLayout.addTab(tabLayout.newTab().setText("My Videos"));
        ApiUtil.getVideosToVerify(UserInfo.getSharedInstance().getUserId(),new GetVideoListHandler());
//test
//        ApiUtil.getVideoList("General",null,null,null,null,null,new GetVideoListHandler());

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==1){
                    ApiUtil.getVideoList(null,null,
                            null,null,
                            UserInfo.getSharedInstance().getRole(),
                            UserInfo.getSharedInstance().getUserId(),
                            new GetVideoListHandler());
                }else if(tab.getPosition()==0){
                    ApiUtil.getVideosToVerify(UserInfo.getSharedInstance().getUserId(),new GetVideoListHandler());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        videoList.setLayoutManager(mLinearLayoutManager);
    }
    class GetVideoListHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                Log.e("VideoList",""+msg.obj);
                //loader.setVisibility(View.GONE);
                VideoList mVideoList =(VideoList) msg.obj;
                videos=mVideoList.getUploadVideoList();
                AdminVideoListAdp adminVideoListAdp = new AdminVideoListAdp(getActivity(),videos);
                videoList.setAdapter(adminVideoListAdp);
                if(videos.size()==0) txtNoVidFound.setVisibility(View.VISIBLE);else  txtNoVidFound.setVisibility(View.GONE);
            }catch (Exception e){
                Log.e("VideoList","AAA"+e);
                UserInfo.getSharedInstance().setToken(null);
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        }
    }
}