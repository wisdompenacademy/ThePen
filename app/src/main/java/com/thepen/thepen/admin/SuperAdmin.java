package com.thepen.thepen.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.thepen.thepen.MainActivity;
import com.thepen.thepen.R;
import com.thepen.thepen.ThePen;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.entity.User;
import com.thepen.thepen.entity.UserList;
import com.thepen.thepen.entity.Video;
import com.thepen.thepen.entity.VideoList;
import com.thepen.thepen.utils.Utils;

import java.util.ArrayList;

import okhttp3.internal.Util;

public class SuperAdmin extends AppCompatActivity {
    private TabLayout tabLayout;
    private ListView adminsList;
    private View tblHeader;
    private RecyclerView adminVideoList;
    private RecyclerView.LayoutManager savedListayoutManager;
    private GridLayoutManager mGridLayoutManager ;
    public static ArrayList<Video> videos;
    private ArrayList<User> users;
    private TextView exptSubject;
    private LinearLayout llLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        tabLayout       = findViewById(R.id.tablayout);
        adminsList      = findViewById(R.id.adminsList);
        tblHeader       = findViewById(R.id.tblHeader);
        adminVideoList  = findViewById(R.id.adminVideoList);
        exptSubject  = findViewById(R.id.exptSubject);
        llLogout  = findViewById(R.id.Logout);

        mGridLayoutManager = new GridLayoutManager(SuperAdmin.this, 3, GridLayoutManager.VERTICAL, false);
        adminVideoList.setLayoutManager(mGridLayoutManager);
        ApiUtil.getuploadvideoList(new GetAdmnVideoList());
        users=new ArrayList<>();
        setTabLayout();
        listviewListener();

        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.logout(SuperAdmin.this);
            }
        });
    }

    private void listviewListener() {
        adminsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(tabLayout.getSelectedTabPosition()==4 || tabLayout.getSelectedTabPosition()==6){
                    ChangeRoleDialog mChangeRoleDialog=new ChangeRoleDialog(SuperAdmin.this,users.get(i));
                    mChangeRoleDialog.show();
                }
            }
        });
    }

    private void setTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Unpublished"));
        tabLayout.addTab(tabLayout.newTab().setText("published"));
        tabLayout.addTab(tabLayout.newTab().setText("Paid"));
        tabLayout.addTab(tabLayout.newTab().setText("Free"));
        tabLayout.addTab(tabLayout.newTab().setText("Teachers"));
        tabLayout.addTab(tabLayout.newTab().setText("Students"));
        tabLayout.addTab(tabLayout.newTab().setText("Admin 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Approved"));
        tabLayout.addTab(tabLayout.newTab().setText("Yet to Approve"));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("TAB",""+tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        ThePen.IS_APPROVED=false;
                        showUnPublishedList();
                        break;
                    case 1:
                        ThePen.IS_APPROVED=false;
                        visibleVideoList();
                        ArrayList<Video> unPubVideos = new ArrayList<>();
                        if(videos!=null){
                            for(Video video : videos){
                                if(!video.publish){
                                    unPubVideos.add(video);
                                }
                            }
                            AdminVideoListAdp adminVideoListAdp3 = new AdminVideoListAdp(SuperAdmin.this,unPubVideos);
                            adminVideoList.setAdapter(adminVideoListAdp3);
                        }
                        break;
                    case 2:visibleVideoList();
                        ThePen.IS_APPROVED=false;
                        ArrayList<Video> paidVideos = new ArrayList<>();
                        if(videos!=null){
                            for(Video video : videos){
                                if(video.charge){
                                    paidVideos.add(video);
                                }
                            }
                            AdminVideoListAdp adminVideoListAdp = new AdminVideoListAdp(SuperAdmin.this,paidVideos);
                            adminVideoList.setAdapter(adminVideoListAdp);
                        }

                        break;
                    case 3:
                        ThePen.IS_APPROVED=false;
                        visibleVideoList();
                        ArrayList<Video> publishedv = new ArrayList<>();
                        if(videos!=null){
                            for(Video video : videos){
                                if(!video.charge){
                                    publishedv.add(video);
                                }
                            }
                            AdminVideoListAdp publishvadp = new AdminVideoListAdp(SuperAdmin.this,publishedv);
                            adminVideoList.setAdapter(publishvadp);
                        }
                        break;
                    case 4:
                        ThePen.IS_APPROVED=false;
                        tblHeader.setVisibility(View.VISIBLE);
                        adminsList.setVisibility(View.VISIBLE);
                        adminVideoList.setVisibility(View.GONE);
                        exptSubject.setText("Sub Expert");
                        ApiUtil.getUserList("2",new GetUserList());break;
                    case 5:
                        ThePen.IS_APPROVED=false;
                        tblHeader.setVisibility(View.VISIBLE);
                        adminsList.setVisibility(View.VISIBLE);
                        adminVideoList.setVisibility(View.GONE);
                        exptSubject.setText("Standard");
                        ApiUtil.getUserList("3",new GetUserList());break;
                    case 6:
                        ThePen.IS_APPROVED=false;
                        ThePen.IS_YET_TO_APPROVED=true;
                        tblHeader.setVisibility(View.VISIBLE);
                        adminsList.setVisibility(View.VISIBLE);
                        adminVideoList.setVisibility(View.GONE);
                        exptSubject.setText("Sub Expert");
                        ApiUtil.getUserList("1",new GetUserList());break;
                    case 7:
                        //Approved
                        ThePen.IS_APPROVED=true;
                        ThePen.IS_YET_TO_APPROVED=false;
                        visibleVideoList();
                        ArrayList<Video> Approved = new ArrayList<>();
                        if(videos!=null){
                            for(Video video : videos){
                                if(video.approved){
                                    Approved.add(video);
                                }
                            }
                            AdminVideoListAdp adminVideoListAdp2 = new AdminVideoListAdp(SuperAdmin.this,Approved);
                            adminVideoList.setAdapter(adminVideoListAdp2);
                        }
                        break;
                    case 8:
                        //yet to approved
                        ThePen.IS_APPROVED=false;
                        ThePen.IS_YET_TO_APPROVED=true;
                        visibleVideoList();
                        ArrayList<Video> yet_to_approved = new ArrayList<>();
                        if(videos!=null){
                            for(Video video : videos){
                                if(!video.approved){
                                    yet_to_approved.add(video);
                                }
                            }
                            AdminVideoListAdp adminVideoListAdp2 = new AdminVideoListAdp(SuperAdmin.this,yet_to_approved);
                            adminVideoList.setAdapter(adminVideoListAdp2);
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void visibleVideoList() {
        tblHeader.setVisibility(View.GONE);
        adminVideoList.setVisibility(View.VISIBLE);
    }

    class GetUserList extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{
                UserList ul = (UserList) msg.obj;
                Log.e("Size","GetUserList "+ul.usercontents.size());
                users.clear();
                users=ul.usercontents;
                UserListAdapter userListAdapter = new UserListAdapter(SuperAdmin.this, ul.usercontents);
                adminsList.setAdapter(userListAdapter);
            }catch(Exception cce){
                Log.d("A",""+cce);
            }
        }
    }
    class GetAdmnVideoList extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if(msg.obj.equals(401)){
                    Utils.logout(SuperAdmin.this);
                }else{
                    VideoList  mVideoList =(VideoList) msg.obj;
                    videos = mVideoList.getUploadVideoList();
                    showUnPublishedList();
                }
            }catch (Exception e){
                Log.e("VideoList","AAA " +e);
            }
        }
    }

    private void showUnPublishedList(){
        visibleVideoList();
        ArrayList<Video> pubVideos = new ArrayList<>();
        if(videos!=null){
            for(Video video : videos){
                if(video.publish){
                    pubVideos.add(video);
                }
            }
            AdminVideoListAdp adminVideoListAdp2 = new AdminVideoListAdp(SuperAdmin.this,pubVideos);
            adminVideoList.setAdapter(adminVideoListAdp2);
        }
    }
}