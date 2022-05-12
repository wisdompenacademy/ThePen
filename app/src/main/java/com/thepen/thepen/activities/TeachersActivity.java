package com.thepen.thepen.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.thepen.thepen.MainActivity;
import com.thepen.thepen.R;
import com.thepen.thepen.ThePen;
import com.thepen.thepen.adapters.TeacherFragmentAdapter;
import com.thepen.thepen.apiservice.ApiCall;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.fragments.AccountFragment;
import com.thepen.thepen.fragments.ForgetpasswordFragment;
import com.thepen.thepen.fragments.FragmentMyVideos;
import com.thepen.thepen.fragments.FragmentUploadVideo;
import com.thepen.thepen.utils.Utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeachersActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TeacherFragmentAdapter teacherFragmentAdapter;
    private ImageView backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        init();
        setViewPager();
    }

    private void init() {
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
        backArrow = findViewById(R.id.backArrow);

        tabLayout.addTab(tabLayout.newTab().setText("My Videos"));
        tabLayout.addTab(tabLayout.newTab().setText("Upload"));

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setViewPager(){
        teacherFragmentAdapter = new TeacherFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(teacherFragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}