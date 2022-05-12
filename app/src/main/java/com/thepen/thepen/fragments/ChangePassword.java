package com.thepen.thepen.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thepen.thepen.MainActivity;
import com.thepen.thepen.R;
import com.thepen.thepen.activities.FragmentContainer;
import com.thepen.thepen.activities.Login;
import com.thepen.thepen.activities.Registration;
import com.thepen.thepen.activities.TeacherRegistration;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.helper.UserInfo;
import com.thepen.thepen.utils.Utils;

import static com.thepen.thepen.utils.Utils.isConnectedInternet;

public class ChangePassword extends Fragment implements View.OnClickListener {
    EditText oldPassword,newPassword,cnfNewPAssword;
    Button btnChangePass;
    private ImageView img_show_password,img_show_old_password,img_show_conf_password;
    private boolean toggle_old_password;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password, container, false);
        init(view);
        UserInfo.initialize(getContext());
        return view;
    }

    private void init(View view) {
        oldPassword=view.findViewById(R.id.edt_old_password);
        newPassword=view.findViewById(R.id.edt_password);
        cnfNewPAssword=view.findViewById(R.id.edt_conf_password);btnChangePass=view.findViewById(R.id.btnChangePassword);
        img_show_password = view.findViewById(R.id.img_show_password);
        img_show_old_password = view.findViewById(R.id.img_show_old_password);
        img_show_conf_password = view.findViewById(R.id.img_show_conf_password);
        img_show_password.setOnClickListener(this);
        img_show_old_password.setOnClickListener(this);
        img_show_conf_password.setOnClickListener(this);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oldPassword.getText().toString().trim().length() == 0) {
                    oldPassword.setError("Please enter Password");
                    oldPassword.requestFocus();
                } else if (newPassword.getText().toString().trim().length() == 0) {
                    newPassword.setError("Please enter Password");
                    newPassword.requestFocus();
                } else if (!newPassword.getText().toString().equals(cnfNewPAssword.getText().toString())) {
                    cnfNewPAssword.setError(getResources().getString(R.string.diff_password_err));
                    cnfNewPAssword.requestFocus();
                }else{
                    ApiUtil.changePassword(oldPassword.getText().toString(),
                            newPassword.getText().toString(),
                            UserInfo.getSharedInstance().getUserId(),
                            new ChangePasswordHandler());
                }
            }
        });
    }
    class ChangePasswordHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("Login",""+msg.obj);
            if(msg.obj.toString().length()>100){
                Toast.makeText(getContext(), ""+msg.obj, Toast.LENGTH_SHORT).show();
            } else if(msg.obj.equals(401)){
                Utils.showAlertDialog(getContext(), getString(R.string.invalid_user));
            } else if(msg.obj.equals(400)){
                Utils.showAlertDialog(getContext(), getString(R.string.invalid_user));
            } else if(msg.obj.equals(103)){
                Utils.showAlertDialog(getContext(), "Faild to connect to server");
            } else {
                //Utils.showConfirmDialog(Login.this, msg.obj.toString());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_show_password:
                if (toggle_old_password) {
                    oldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_show_password.setImageResource(R.drawable.hide_password);
                    toggle_old_password = false;
                } else {
                    oldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_show_password.setImageResource(R.drawable.show_password);
                    toggle_old_password = true;
                }
                break;
            case R.id.img_show_old_password:
                if (toggle_old_password) {
                    oldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_show_old_password.setImageResource(R.drawable.hide_password);
                    toggle_old_password = false;
                } else {
                    oldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_show_old_password.setImageResource(R.drawable.show_password);
                    toggle_old_password = true;
                }
                break;
            case R.id.img_show_conf_password:
                if (toggle_old_password) {
                    oldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_show_conf_password.setImageResource(R.drawable.hide_password);
                    toggle_old_password = false;
                } else {
                    oldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_show_conf_password.setImageResource(R.drawable.show_password);
                    toggle_old_password = true;
                }
                break;

        }
    }
}