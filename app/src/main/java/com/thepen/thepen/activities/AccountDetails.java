package com.thepen.thepen.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.thepen.thepen.R;
import com.thepen.thepen.ThePen;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.entity.UserProfile;
import com.thepen.thepen.helper.UserInfo;
import com.thepen.thepen.utils.Utils;

import static com.thepen.thepen.utils.Utils.isConnectedInternet;

public class AccountDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener  {
    private EditText edtStdDob,edtFirstName,edtLastName,edtEmail,edtSchoolName,edtPname,
            edtpLastName,edtStdPhone,edtParentPhone;
    private Spinner edtstandYear;
    private Button btnUpdate;
    ProgressBar loader;
    private ImageView backArrow;
    private ArrayAdapter standardAdp;
    String[] standard = {"10","12"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        init();
        Log.d("INDEX"," "+ ThePen.userProfile.usercontent.lastName);
    }
    private void init() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtstandYear = findViewById(R.id.edtstandYear);
        edtPname = findViewById(R.id.edtPname);
        edtpLastName = findViewById(R.id.edtpLastName);
        edtStdPhone = findViewById(R.id.edtStdPhone);
        edtParentPhone = findViewById(R.id.edtParentPhone);
        edtStdDob = findViewById(R.id.edtStdDob);
        edtSchoolName = findViewById(R.id.edtSchoolName);
        
        loader = findViewById(R.id.loader);
        backArrow = findViewById(R.id.backArrow);

        btnUpdate = findViewById(R.id.btnUpdate);
        setSpinner();
         setText();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validField();
            }
        });
        edtStdDob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AccountDetails.this, AlertDialog.THEME_HOLO_LIGHT, AccountDetails.this, year, month, day);
                dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                dialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        int month=i1+1;
        edtStdDob.setText(""+i2+"/"+month+"/"+i);
        edtStdDob.setError(null,null);
    }

    @Override
    public void onClick(View view) {

    }

    private void setSpinner() {
        standardAdp = new ArrayAdapter(AccountDetails.this,android.R.layout.simple_spinner_item,standard);
        standardAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtstandYear.setAdapter(standardAdp);
    }
    public  void validField(){
        if (edtFirstName.getText().toString().trim().length() == 0) {
            edtFirstName.setError("Please enter first name");
            edtFirstName.requestFocus();
        } else if (edtLastName.getText().toString().trim().length() == 0) {
            edtLastName.setError("Please enter last name");
            edtLastName.requestFocus();
        } else if(edtSchoolName.getText().toString().length()==0){
            edtSchoolName.requestFocus();
            edtSchoolName.setError("Please Enter School Name");
        } else if (edtPname.getText().toString().trim().length() == 0) {
            edtPname.setError("Please enter Parent Name");
            edtPname.requestFocus();
        } else if (edtpLastName.getText().toString().trim().length() == 0) {
            edtpLastName.setError("Please enter Parent LastName");
            edtpLastName.requestFocus();
        } else if (edtStdPhone.getText().toString().trim().length() != 10) {
            edtStdPhone.setError("Please enter Phone Number");
            edtStdPhone.requestFocus();
        } else if (edtParentPhone.getText().toString().trim().length() != 10) {
            edtParentPhone.setError("Please enter Parent Phone");
            edtParentPhone.requestFocus();
        }
        else if (edtStdDob.getText().toString().trim().length() == 0) {
            edtStdDob.setError("Please enter Birth date");
            edtStdDob.requestFocus();
        }
        else {
            if (isConnectedInternet(AccountDetails.this) == true) {
                loader.setVisibility(View.VISIBLE);
                ApiUtil.updateUserProfile(UserInfo.getSharedInstance().getUserId(),
                        "add",edtStdPhone.getText().toString(),"City","state",
                        "India","443001",edtFirstName.getText().toString(),
                        edtLastName.getText().toString(),edtStdDob.getText().toString(),
                        "parent name",edtstandYear.getSelectedItem().toString(),
                        "school",new AccountUpdateHandler());
            }else{
                if (AccountDetails.this!= null)
                    Utils.showSettingsAlert(AccountDetails.this);
            }
        }
    }

    private void setText(){
        edtFirstName.setText(ThePen.userProfile.usercontent.firstName);
        edtLastName.setText(ThePen.userProfile.usercontent.lastName);
//        edtPname.setText("add");
//        edtpLastName.setText("add");
        edtStdPhone.setText(ThePen.userProfile.usercontent.phoneNo);
//        edtParentPhone.setText("9822455896");
        edtStdDob.setText(ThePen.userProfile.usercontent.dob);
//        edtSchoolName.setText("add");
    }

    public class AccountUpdateHandler extends Handler{

    }
}