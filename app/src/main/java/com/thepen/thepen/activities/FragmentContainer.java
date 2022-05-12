package com.thepen.thepen.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.thepen.thepen.MainActivity;
import com.thepen.thepen.R;
import com.thepen.thepen.admin.Admin;
import com.thepen.thepen.fragments.AccountFragment;
import com.thepen.thepen.fragments.AssignVideo;
import com.thepen.thepen.fragments.ChangePassword;
import com.thepen.thepen.fragments.ForgetpasswordFragment;
import com.thepen.thepen.fragments.WebFragment;

public class FragmentContainer extends AppCompatActivity {
    private ImageView backArrow;
    private RelativeLayout account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        backArrow = findViewById(R.id.backArrow);
        account = findViewById(R.id.avatar);

        final Fragment accountFragment = new AccountFragment();
        final Fragment forgetpasswordFragment = new ForgetpasswordFragment();
        Intent intent = getIntent();
       if(intent.getStringExtra("FRAGMENT").equals("ACCOUNT")){
           backArrow.setVisibility(View.GONE);
           FragmentManager fm = getSupportFragmentManager();
           FragmentTransaction fragmentTransaction = fm.beginTransaction();
           fragmentTransaction.replace(R.id.layout, accountFragment);
           fragmentTransaction.commit();
       }else if(intent.getStringExtra("FRAGMENT").equals("WebFragment")){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.layout, new WebFragment(intent.getStringExtra("URL")));
            fragmentTransaction.commit();
       }else if(intent.getStringExtra("FRAGMENT").equals("CHANGEPASS")){
           FragmentManager fm = getSupportFragmentManager();
           FragmentTransaction fragmentTransaction = fm.beginTransaction();
           fragmentTransaction.replace(R.id.layout, new ChangePassword());
           fragmentTransaction.commit();
       } else if(intent.getStringExtra("FRAGMENT").equals("AssignVideo")){
           FragmentManager fm = getSupportFragmentManager();
           FragmentTransaction fragmentTransaction = fm.beginTransaction();
           fragmentTransaction.replace(R.id.layout, new AssignVideo());
           fragmentTransaction.commit();
       }
       else if(intent.getStringExtra("FRAGMENT").equals("Admin")){
           FragmentManager fm = getSupportFragmentManager();
           FragmentTransaction fragmentTransaction = fm.beginTransaction();
           fragmentTransaction.replace(R.id.layout, new Admin());
           fragmentTransaction.commit();
           account.setVisibility(View.VISIBLE);
       } else {
           FragmentManager fm = getSupportFragmentManager();
           FragmentTransaction fragmentTransaction = fm.beginTransaction();
           fragmentTransaction.replace(R.id.layout, forgetpasswordFragment);
           fragmentTransaction.commit();
       }
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

       account.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(FragmentContainer.this, FragmentContainer.class);
               intent.putExtra("FRAGMENT","ACCOUNT");
               startActivity(intent);
               finish();
           }
       });
    }
}