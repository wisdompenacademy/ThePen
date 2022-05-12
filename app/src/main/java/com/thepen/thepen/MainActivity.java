package com.thepen.thepen;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.jarvanmo.exoplayerview.media.SimpleMediaSource;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;
import com.thepen.thepen.activities.FragmentContainer;
import com.thepen.thepen.activities.Login;
import com.thepen.thepen.activities.SavedVideos;
import com.thepen.thepen.activities.Spashscreen;
import com.thepen.thepen.activities.TeachersActivity;
import com.thepen.thepen.adapters.VideoListAdapter1;
import com.thepen.thepen.admin.SuperAdmin;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.entity.Video;
import com.thepen.thepen.entity.VideoList;
import com.thepen.thepen.helper.Constants;
import com.thepen.thepen.helper.UserInfo;
import com.thepen.thepen.utils.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView videoList;
    private TabLayout tabLayout;
    private ImageView uploadVideo,logo;
    private TextView initial,name,appV,txtNoVidFound,thePenTAndC,thePenObj;
    private LinearLayout tenthMahEng,tenthMahMar,TwelfthMah,TwelfthCbsc,tenthCbsc;
    private RelativeLayout mainSwipe,avatar;
    private ImageView imgOption;
    private DrawerLayout drawer;
    private ArrayList<Video> videos;
    private CheckBox ck12MhCbsc,ck10MhEng,ck10MhMar,ck10MhCbsc,ck12MhEng;
    ProgressBar loader;
    private LinearLayoutManager mLinearLayoutManager ;
    private TextView txtCourse,med,med_desc,skip_btn;

    private ExoVideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        name.setText(UserInfo.getSharedInstance().getUserId());
        String a = UserInfo.getSharedInstance().getUserId().charAt(0)+"".toUpperCase();
        initial.setText(a.toUpperCase());
        if(UserInfo.getSharedInstance().getRole().equals("ROLE_TEACHER")){
            uploadVideo.setVisibility(View.VISIBLE);
            ApiUtil.getVideoList("General",null,null,null,null,null,new GetVideoListHandler());
        }else {
            uploadVideo.setVisibility(View.INVISIBLE);
            ApiUtil.getVideoList("General",null,null,null,null,null,new GetVideoListHandler());
        }
        setTabLayout();
    }

    private void init() {
        videoList = findViewById(R.id.videosList);
        tabLayout = findViewById(R.id.tablayout);
        uploadVideo = findViewById(R.id.uploadVideo);
        initial = findViewById(R.id.initial);
        avatar = findViewById(R.id.avatar);
        name = findViewById(R.id.name);
        mainSwipe = findViewById(R.id.mainSwipe);
        imgOption =findViewById(R.id.option);
        drawer =findViewById(R.id.drawer);
        logo =findViewById(R.id.logo);
        loader = findViewById(R.id.loader);
        txtNoVidFound = findViewById(R.id.txtNoVidFound);
        appV = findViewById(R.id.appV);showVersionInfo();

        videoView = findViewById(R.id.videoView);
        skip_btn = findViewById(R.id.skip_btn);

        tenthMahEng =findViewById(R.id.tenthMahEng);
        tenthMahMar =findViewById(R.id.tenthMahMar);
        TwelfthMah =findViewById(R.id.TwelfthMah);
        TwelfthCbsc =findViewById(R.id.TwelfthCbsc);
        tenthCbsc =findViewById(R.id.tenthCbsc);


        ck10MhEng =findViewById(R.id.ck10MhEng);
        ck10MhMar =findViewById(R.id.ck10MhMar);
        ck10MhCbsc =findViewById(R.id.ck10MhCbsc);
        ck12MhEng =findViewById(R.id.ck12MhEng);
        ck12MhCbsc =findViewById(R.id.ck12MhCbsc);
        thePenTAndC =findViewById(R.id.thePenTAndC);
        thePenObj =findViewById(R.id.thePenObj);
        txtCourse =findViewById(R.id.course);
        med =findViewById(R.id.med);
        med_desc =findViewById(R.id.med_desc);

        mainSwipe.setOnClickListener(this);
        imgOption.setOnClickListener(this);
        logo.setOnClickListener(this);
        uploadVideo.setOnClickListener(this);

        tenthMahEng.setOnClickListener(this);
        tenthMahMar.setOnClickListener(this);
        TwelfthMah.setOnClickListener(this);
        TwelfthCbsc.setOnClickListener(this);
        tenthCbsc.setOnClickListener(this);

        thePenTAndC.setOnClickListener(this);
        thePenObj.setOnClickListener(this);

        avatar.setOnClickListener(this);
        skip_btn.setOnClickListener(this);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

       videoList.setLayoutManager(mLinearLayoutManager);
       showAdv();
    }

    private void showAdv() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                videoView.setVisibility(View.VISIBLE);
                //        SimpleMediaSource mediaSource = new SimpleMediaSource(Constants.FILE_URL+videos.get(0).videoPath);
                SimpleMediaSource mediaSource = new SimpleMediaSource("http://93.188.166.106/2020-09-28/video1_1601318185398.mp4");
                mediaSource.setDisplayName("VideoPlaying");
                videoView.play(mediaSource, false);
            }
        }, 10000);
    }

    private void setTabLayout() {
        txtCourse.setText("10 th MH English");
        setSubjectsToTab(1);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    ApiUtil.getVideoList("General",null,null,null,null,null,new GetVideoListHandler());
                }else {
                    ThePen.subject=tab.getText().toString();
                    ApiUtil.getVideoList(tab.getText().toString(),ThePen.Standard,ThePen.board,ThePen.medium,null,null,new GetVideoListHandler());
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

    private void setSubjectsToTab(int i) {
        tabLayout.removeAllTabs();
        switch (i){
            case 1:
                tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_home_24));
                tabLayout.addTab(tabLayout.newTab().setText("English"));
                tabLayout.addTab(tabLayout.newTab().setText("Science-1"));
                tabLayout.addTab(tabLayout.newTab().setText("Science-2"));
                tabLayout.addTab(tabLayout.newTab().setText("Mathematics-1"));
                tabLayout.addTab(tabLayout.newTab().setText("Mathematics-2"));
                break;
            case 2:
                tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_home_24));
                tabLayout.addTab(tabLayout.newTab().setText("English"));
                tabLayout.addTab(tabLayout.newTab().setText("Physics"));
                tabLayout.addTab(tabLayout.newTab().setText("Chemistry"));
                tabLayout.addTab(tabLayout.newTab().setText("Biology"));
                tabLayout.addTab(tabLayout.newTab().setText("Mathematics"));
                break;
            case 3:
                tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_home_24));
                tabLayout.addTab(tabLayout.newTab().setText("English"));
                tabLayout.addTab(tabLayout.newTab().setText("Science"));
                tabLayout.addTab(tabLayout.newTab().setText("Mathematics"));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mainSwipe:break;
            case R.id.logo:
               startActivity(new Intent(MainActivity.this, SavedVideos.class));
                break;
            case R.id.option:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.tenthMahEng:
                setCourseTitle("10 th MH English","Eng","Std. 10th Maharashtra State English Medium");
                ThePen.Standard="10";
                ThePen.medium="English";
                ThePen.board="maharashtra";
                ApiUtil.getVideoList(ThePen.subject,ThePen.Standard,
                        "maharashtra",ThePen.medium,null,null,new GetVideoListHandler());
                selectCourse(1);
                setSubjectsToTab(1);
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.tenthMahMar:
                setCourseTitle("10 th MH Marathi","Mar","Std. 10th Maharashtra State Marathi Medium");
                ThePen.Standard="10";
                ThePen.medium="Marathi";
                ThePen.board="maharashtra";
                ApiUtil.getVideoList(ThePen.subject,ThePen.Standard,
                        "maharashtra",ThePen.medium,null,null,new GetVideoListHandler());
                selectCourse(2);
                setSubjectsToTab(1);
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.TwelfthMah:
                setCourseTitle("12 th MH Marathi","English","Std. 12th Maharashtra State English Medium");
                ThePen.Standard="12";
                ThePen.medium="English";
                ThePen.board="maharashtra";
                ApiUtil.getVideoList(ThePen.subject,ThePen.Standard,
                        "maharashtra",ThePen.medium,null,null,new GetVideoListHandler());
                selectCourse(3);
                setSubjectsToTab(2);
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.TwelfthCbsc:
                setCourseTitle("12 th CBSC","English","Std. 12th CBSC");
                ThePen.Standard="12";
                ThePen.medium="English";
                ThePen.board="CBSC";
                setSubjectsToTab(2);
                ApiUtil.getVideoList(ThePen.subject,ThePen.Standard,
                        "CBSC",ThePen.medium,null,null,new GetVideoListHandler());
                selectCourse(4);
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.tenthCbsc:
                txtCourse.setText("10 th CBSC");
                setCourseTitle("10 th CBSC","English","Std. 12th CBSC");
                ThePen.Standard="10";
                ThePen.medium="English";
                ThePen.board="CBSC";
                ApiUtil.getVideoList(ThePen.subject,ThePen.Standard,
                        "CBSC",ThePen.medium,null,null,new GetVideoListHandler());
                selectCourse(5);
                setSubjectsToTab(3);
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.avatar:
                Intent intent = new Intent(MainActivity.this, FragmentContainer.class);
                intent.putExtra("FRAGMENT","ACCOUNT");
                startActivity(intent);
                break;
            case  R.id.uploadVideo:
                startActivity(new Intent(MainActivity.this, TeachersActivity.class));
                break;
            case R.id.thePenTAndC:
                Intent intent3 = new Intent(MainActivity.this, FragmentContainer.class);
                intent3.putExtra("FRAGMENT","WebFragment");
                intent3.putExtra("URL","terms_conditions.html");
                startActivity(intent3);
                break;
            case R.id.thePenObj:
                Intent intent4 = new Intent(MainActivity.this, FragmentContainer.class);
                intent4.putExtra("FRAGMENT","WebFragment");
                intent4.putExtra("URL","pen_objectives.html");
                startActivity(intent4);
                break;
            case R.id.skip_btn:
                videoView.pause();
                videoView.releasePlayer();
                videoView.setVisibility(View.GONE);
                break;

        }
    }

    private void setCourseTitle(String course,String medium,String med_description) {
        txtCourse.setText(course);
        med.setText(medium);
        med_desc.setText(med_description);
    }

    private void selectCourse(int selected) {
        switch (selected){
            case 1:
                ck10MhEng.setChecked(true);
                ck12MhCbsc.setChecked(false);
                ck10MhMar.setChecked(false);
                ck10MhCbsc.setChecked(false);
                ck12MhEng.setChecked(false);
             break;
            case 2:
                ck10MhEng.setChecked(false);
                ck12MhCbsc.setChecked(false);
                ck10MhMar.setChecked(true);
                ck10MhCbsc.setChecked(false);
                ck12MhEng.setChecked(false);
                break;
            case 3:
                ck10MhEng.setChecked(false);
                ck12MhCbsc.setChecked(false);
                ck10MhMar.setChecked(false);
                ck10MhCbsc.setChecked(false);
                ck12MhEng.setChecked(true);
                break;
            case 4:
                ck10MhEng.setChecked(false);
                ck12MhCbsc.setChecked(true);
                ck10MhMar.setChecked(false);
                ck10MhCbsc.setChecked(false);
                ck12MhEng.setChecked(false);
                break;
            case 5:
                ck10MhEng.setChecked(false);
                ck12MhCbsc.setChecked(false);
                ck10MhMar.setChecked(false);
                ck10MhCbsc.setChecked(true);
                ck12MhEng.setChecked(false);
                break;
        }
    }

    class GetVideoListHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                Log.e("VideoList",""+msg.obj);
                loader.setVisibility(View.GONE);
                VideoList  mVideoList =(VideoList) msg.obj;
                videos=mVideoList.getUploadVideoList();
                VideoListAdapter1 videoListAdapter = new VideoListAdapter1(MainActivity.this, videos);
                videoList.setAdapter(videoListAdapter);
                if(videos.size()==0) txtNoVidFound.setVisibility(View.VISIBLE);else  txtNoVidFound.setVisibility(View.GONE);
            }catch (Exception e){
                Log.e("VideoList","AAA");
               Utils.logout(MainActivity.this);
            }
        }
    }
    private void showVersionInfo() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            appV.setText("Version: "+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}