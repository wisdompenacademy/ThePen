package com.thepen.thepen.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.thepen.thepen.MainActivity;
import com.thepen.thepen.admin.SuperAdmin;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.helper.UserInfo;
import com.thepen.thepen.utils.Utils;

import static com.thepen.thepen.utils.Utils.isConnectedInternet;

import com.thepen.thepen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText edt_user_name, edt_password;
    Button btn_login;
    TextView txt_register, txt_forgot_password, txt_teacherRegister;
    String user_name, password;
    ProgressBar login_loader;
    private CheckBox ckbRememberMe;
    private ImageView img_show_password;
    private boolean toggle_old_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        UserInfo.initialize(this);
    }

    private void init() {
        edt_user_name = findViewById(R.id.edt_user_name);
        edt_password = findViewById(R.id.edt_password);
        setText();
        btn_login = findViewById(R.id.btn_login);
        txt_register = findViewById(R.id.txt_register);
        txt_teacherRegister = findViewById(R.id.txt_teacherRegister);
        txt_forgot_password = findViewById(R.id.txt_foprgot_password);
        img_show_password = findViewById(R.id.img_show_password);
        login_loader = findViewById(R.id.login_loader);

        btn_login.setOnClickListener(this);
        txt_register.setOnClickListener(this);
        txt_forgot_password.setOnClickListener(this);
        img_show_password.setOnClickListener(this);
        txt_teacherRegister.setOnClickListener(this);
    }

    private void setText() {
//        edt_user_name.setText("akashyyy@gmail.com");
//        edt_password.setText("123456123");
    }

    private boolean validateUser() {
        user_name = edt_user_name.getText().toString().trim();
        password = edt_password.getText().toString().trim();

        if (edt_user_name.getText().toString().trim().length() == 0) {
            edt_user_name.setError("Please enter email ID");
            edt_user_name.requestFocus();
        }
//        else if (!Utils.isValidEmailId(edt_user_name.getText().toString().trim())) {
//            edt_user_name.setError("Please enter valid email ID");
//            edt_user_name.requestFocus();
//        }
        else if (edt_password.getText().toString().trim().length() == 0) {
            edt_password.setError("Please enter Password");
            edt_password.requestFocus();
        } else {
           // startActivity(new Intent(Login.this, MainActivity.class));
            if (isConnectedInternet(Login.this)) {
                login_loader.setVisibility(View.VISIBLE);
                toggleTouch(false);
                Log.d("Login", "login api hit ");
               // startActivity(new Intent(Login.this, MainActivity.class));
                Log.e("DATA",""+user_name);
                Log.e("DATA",""+password);
                ApiUtil.userLogin(user_name, password, new UserLoginHandler());
                //startActivity(new Intent(Login.this, MainActivity.class));
            } else {
                Utils.showSettingsAlert(Login.this);
            }
        }
        return false;
    }

    public void toggleTouch(boolean b) {
        edt_password.setEnabled(b);
        edt_user_name.setEnabled(b);
        btn_login.setEnabled(b);
        txt_forgot_password.setEnabled(b);
        img_show_password.setEnabled(b);
        txt_register.setEnabled(b);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_teacherRegister:
                startActivity(new Intent(Login.this, TeacherRegistration.class));
                break;
            case R.id.btn_login:
                if (validateUser()) {
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
                break;
            case R.id.txt_register:
                Log.d("Login", "login api hit ");
                if (isConnectedInternet(Login.this)) {
                    startActivity(new Intent(Login.this, Registration.class));
                } else {
                    Utils.showSettingsAlert(Login.this);
                }
                break;
            case R.id.txt_foprgot_password:
                Log.d("Login", "login api hit ");
                if (isConnectedInternet(Login.this)) {
                    Intent intent = new Intent(Login.this, FragmentContainer.class);
                    intent.putExtra("FRAGMENT","PASSWORD");
                    startActivity(intent);
                } else {
                    Utils.showSettingsAlert(Login.this);
                }
                break;
            case R.id.img_show_password:
                if (toggle_old_password) {
                    edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_show_password.setImageResource(R.drawable.hide_password);
                    toggle_old_password = false;
                } else {
                    edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_show_password.setImageResource(R.drawable.show_password);
                    toggle_old_password = true;
                }
                break;
        }
    }

    class UserLoginHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("Login",""+msg.obj);
            login_loader.setVisibility(View.GONE);
            toggleTouch(true);
            if(msg.obj.toString().length()>100){
                decoded(UserInfo.getSharedInstance().getToken());
                UserInfo.getSharedInstance().setUserId(edt_user_name.getText().toString());
                Log.e("ROLL"," "+UserInfo.getSharedInstance().getRole());
                if(decoded(UserInfo.getSharedInstance().getToken()).equals("ROLE_SUPER_ADMIN")){
                    startActivity(new Intent(Login.this, SuperAdmin.class));
                    finish();
                    Log.d("ROLL"," "+decoded(UserInfo.getSharedInstance().getToken()));
                }else if(decoded(UserInfo.getSharedInstance().getToken()).equals("ROLE_ADMIN")){
                    Intent intent = new Intent(Login.this, FragmentContainer.class);
                    intent.putExtra("FRAGMENT","Admin");
                    startActivity(intent);
                    finish();
                    Log.d("ROLL"," "+decoded(UserInfo.getSharedInstance().getToken()));
                } else {
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                    Log.d("ROLL"," "+decoded(UserInfo.getSharedInstance().getToken()));
                }
            }
            else if(msg.obj.equals(401)){
                Utils.showAlertDialog(Login.this, getString(R.string.invalid_user));
            }else if(msg.obj.equals(400)){
                Utils.showAlertDialog(Login.this, getString(R.string.invalid_user));
            }
            else if(msg.obj.equals(103)){
            Utils.showAlertDialog(Login.this, "Faild to connect to server");
            } else {

            }
        }

    }

    public void move(View view) {
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rocket);
        view.startAnimation(animation1);
    }

    public void fadeOut(View view) {
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        view.startAnimation(animation1);
    }

    public void rotate(View view) {
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        view.startAnimation(animation1);
    }

    public void taj(View view) {
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.taj);
        view.startAnimation(animation1);
    }

    public String decoded(String JWTEncoded) {
        UserInfo.getSharedInstance().setToken(JWTEncoded);
        try {
            String[] split = JWTEncoded.split("\\.");
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[2]));
            String str= getJson(split[1]);
            JSONObject jsonObj = new JSONObject(str);
            JSONArray ja_data = jsonObj.getJSONArray("authorities");
            int length = ja_data.length();
            Log.d("JWT_DECODED", "Header: "+ja_data.get(0));
            UserInfo.getSharedInstance().setRole(ja_data.get(0).toString());
            return ja_data.get(0).toString();
        } catch (IOException e) {
            Log.d("JWT_DECODED", "Header: "+e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JWT_DECODED", "Header: "+e.getMessage());
        }
        return null;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException
    {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}