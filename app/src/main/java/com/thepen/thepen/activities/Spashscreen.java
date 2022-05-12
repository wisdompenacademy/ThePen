package com.thepen.thepen.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thepen.thepen.MainActivity;
import com.thepen.thepen.R;
import com.thepen.thepen.admin.Admin;
import com.thepen.thepen.admin.SuperAdmin;
import com.thepen.thepen.helper.UserInfo;

import org.w3c.dom.Text;

public class Spashscreen extends AppCompatActivity implements View.OnClickListener {
//    ImageView one,one2,one3,one4,one5,one6;
    ImageView one;
//    TextView appName;
    private Button btnLogin,btnReg;
    private LinearLayout llButtons;
    private TextView versionInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spashscreen);
//        appName = findViewById(R.id.appName);
        one = findViewById(R.id.one);
        btnLogin = findViewById(R.id.btn_login);
        btnReg = findViewById(R.id.btn_reg);
        llButtons = findViewById(R.id.llButtons);
        versionInfo = findViewById(R.id.versionInfo);

        btnReg.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        showVersionInfo();
        UserInfo.initialize(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(UserInfo.getSharedInstance().getToken()!=null){
                    if(UserInfo.getSharedInstance().getRole().equalsIgnoreCase("ROLE_ADMIN")){
                        Intent intent = new Intent(Spashscreen.this, FragmentContainer.class);
                        intent.putExtra("FRAGMENT","Admin");
                        startActivity(intent);
                        finish();
                    }else if(UserInfo.getSharedInstance().getRole().equalsIgnoreCase("ROLE_SUPER_ADMIN")){
                        startActivity(new Intent(Spashscreen.this, SuperAdmin.class));
                        finish();
                    }else{
                        startActivity(new Intent(Spashscreen.this, MainActivity.class));
                        finish();
                    }
                } else{
                    llButtons.setVisibility(View.VISIBLE);
                    taj(llButtons);
                }
            }
        }, 3000);
    }

    private void showVersionInfo() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            versionInfo.setText("Version: "+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void move(View view){
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rocket);
        view.startAnimation(animation1);
    }
    public void fadeOut(View view){
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        view.startAnimation(animation1);
    }
    public void rotate(View view){
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        view.startAnimation(animation1);
    }
    public void taj(View view){
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.taj);
        view.startAnimation(animation1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                Spashscreen.this.startActivity(new Intent(Spashscreen.this, Login.class));
                finish();
                break;
            case R.id.btn_reg:
                Spashscreen.this.startActivity(new Intent(Spashscreen.this, Registration.class));
                finish();
                break;
        }
    }
}