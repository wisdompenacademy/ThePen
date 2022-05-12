package com.thepen.thepen.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.jarvanmo.exoplayerview.media.ExoMediaSource;
import com.jarvanmo.exoplayerview.media.SimpleMediaSource;
import com.jarvanmo.exoplayerview.media.SimpleQuality;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;
import com.thepen.thepen.MainActivity;
import com.thepen.thepen.R;
import com.thepen.thepen.ThePen;
import com.thepen.thepen.adapters.VideoListAdapter2;
import com.thepen.thepen.admin.SuperAdmin;
import com.thepen.thepen.entity.Video;
import com.thepen.thepen.helper.Constants;
import com.thepen.thepen.helper.Downback;
import com.thepen.thepen.helper.DownloadFileFromUrl;
import com.thepen.thepen.helper.UserInfo;
import com.thepen.thepen.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_LANDSCAPE;
import static com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_PORTRAIT;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    private ExoVideoView videoView;
    private Button modeFit;
    private Button modeNone;
    private Button modeHeight;
    private Button modeWidth;
    private Button modeZoom;
    private Button downloadVideo;
    private View wrapper;
    private Button play;
    private View contentView;
    private ListView videoList;
    private LinearLayout ll_videoControls;
    private ArrayList<Video> videos;
    private ImageView backArrow,back,pause,next,flag,save_offine;
    int index=0;
    private boolean isPlaying=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        contentView = findViewById(R.id.activity_main);

        videoView = findViewById(R.id.videoView);
        modeFit = findViewById(R.id.mode_fit);
        modeNone = findViewById(R.id.mode_none);
        modeHeight = findViewById(R.id.mode_height);
        modeWidth = findViewById(R.id.mode_width);
        modeZoom = findViewById(R.id.mode_zoom);
        downloadVideo = findViewById(R.id.downloadVideo);
        ll_videoControls = findViewById(R.id.ll_videoControls);

        back = findViewById(R.id.back);
        pause = findViewById(R.id.pause);
        next = findViewById(R.id.next);
        save_offine = findViewById(R.id.save_offine);

        back.setOnClickListener(this);
        pause.setOnClickListener(this);
        next.setOnClickListener(this);
        save_offine.setOnClickListener(this);

        wrapper = findViewById(R.id.wrapper);
        play = findViewById(R.id.changeMode);
        videoList = findViewById(R.id.videosList);
        backArrow = findViewById(R.id.backArrow);
        flag = findViewById(R.id.flag);
        flag.setOnClickListener(this);
        videoView.setBackListener((view, isPortrait) -> {
//            if (isPortrait) {
//                finish();
//            }
            finish();
            return false;
        });

//        videoView.setOrientationListener(orientation -> {
//            if (orientation == SENSOR_PORTRAIT) {
//               // changeToPortrait();
//            } else if (orientation == SENSOR_LANDSCAPE) {
//                //changeToLandscape();
//            }
//        });

        Intent intent = getIntent();
        try {
            Log.d("INDEX"," "+ intent.getStringExtra("index"));
            index = Integer.parseInt(intent.getStringExtra("index"));
        }catch (Exception e){
            e.printStackTrace();
        }
        Bundle args = intent.getBundleExtra("BUNDLE");
        videos = (ArrayList<Video>) args.getSerializable("ARRAYLIST");
        Log.e("URL",intent.getStringExtra("URL"));

//        VideoListAdapter2 videoListAdapter = new VideoListAdapter2(VideoActivity.this, videos);
//        videoList.setAdapter(videoListAdapter);

        SimpleMediaSource mediaSource = new SimpleMediaSource(Constants.FILE_URL+intent.getStringExtra("URL"));
//        mediaSource.setDisplayName("VideoPlaying");

        List<ExoMediaSource.Quality> qualities = new ArrayList<>();
        ExoMediaSource.Quality quality;

        setListener();

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.YELLOW);
        SpannableString spannableString = new SpannableString("360p");
        spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        quality = new SimpleQuality(spannableString, mediaSource.uri());
        qualities.add(quality);

        spannableString = new SpannableString("240p");
        colorSpan = new ForegroundColorSpan(Color.LTGRAY);
        spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        quality = new SimpleQuality(spannableString, mediaSource.uri());
        qualities.add(quality);

        spannableString = new SpannableString("60p");
        colorSpan = new ForegroundColorSpan(Color.LTGRAY);
        spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        quality = new SimpleQuality(spannableString, mediaSource.uri());
        qualities.add(quality);
        mediaSource.setQualities(qualities);

        videoView.play(mediaSource, false);
        play.setOnClickListener(view -> {
            videoView.play(mediaSource);
            play.setVisibility(View.INVISIBLE);
        });
        modeFit.setOnClickListener(v -> videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT));
        modeWidth.setOnClickListener(v -> videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH));
        modeHeight.setOnClickListener(v -> videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT));
        modeNone.setOnClickListener(v -> videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL));
        modeZoom.setOnClickListener(v -> videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM));
        downloadVideo.setOnClickListener(v -> download(intent));

        ll_videoControls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back.setVisibility(View.VISIBLE);
                pause.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                hideControls();
            }
        });
    }

    private void setListener() {
        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SimpleMediaSource mediaSource = new SimpleMediaSource(Constants.FILE_URL +videos.get(i).videoPath);
                videoView.play(mediaSource);
            }
        });
    }

    private void changeToPortrait() {

        WindowManager.LayoutParams attr = getWindow().getAttributes();
//        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();
        window.setAttributes(attr);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        wrapper.setVisibility(View.VISIBLE);
    }


    private void changeToLandscape() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = getWindow();
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        wrapper.setVisibility(View.GONE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 23) {
            videoView.resume();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((Build.VERSION.SDK_INT <= 23)) {
            videoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            videoView.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            videoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.releasePlayer();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return videoView.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void download(Intent intent) {
        Downback DB = new Downback();
        DB.execute(Constants.FILE_URL+intent.getStringExtra("URL"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                if(index>0){
                    index=index-1;
                    Log.d("INDEX"," "+ index);
                    SimpleMediaSource mediaSource = new SimpleMediaSource(Constants.FILE_URL +videos.get(index).videoPath);
                    videoView.play(mediaSource);
                }
                break;
            case R.id.pause:
                if(isPlaying){
                    pause.setImageResource(R.drawable.ic_play_arrow_white_36dp);
                    isPlaying=false;
                    videoView.pause();
                }else{
                    isPlaying=true;
                    videoView.resume();
                    pause.setImageResource(R.drawable.ic_baseline_pause_24);
                }
                break;
            case R.id.next:
                if(index<(videos.size())-1){
                    index=index+1;
                    Log.d("INDEX"," "+ index);
                    SimpleMediaSource mediaSource2 = new SimpleMediaSource(Constants.FILE_URL +videos.get(index).videoPath);
                    videoView.play(mediaSource2);
                }
                break;
            case R.id.backArrow:
                    finish();
                break;
            case R.id.videoView:

                break;
            case R.id.flag:
                    sendMail();
                break;
            case R.id.save_offine:
                Toast.makeText(this, "Saving Offline", Toast.LENGTH_SHORT).show();
                new DownloadFileFromUrl(VideoActivity.this,videos.get(index).videoPath).execute(Constants.FILE_URL +videos.get(index).videoPath);
                save_offine.setOnClickListener(null);
                break;
        }
    }

    public void sendMail(){
        /* Create the Intent */
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        /* Fill it with Data */
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"appacuiti@email.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "About this video");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hello ");
        /* Send it off to the Activity-Chooser */
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void hideControls(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                back.setVisibility(View.GONE);
                pause.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
            }
        }, 10000);
    }
}