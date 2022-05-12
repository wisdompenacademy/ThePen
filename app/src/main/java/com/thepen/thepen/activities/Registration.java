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
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thepen.thepen.R;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.utils.Utils;

import static com.thepen.thepen.utils.Utils.isConnectedInternet;

public class Registration extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private EditText edtStdDob,edtFirstName,edtLastName,edtEmail,edtPass,edtConfPass,edtSchoolName,edtPname,
            edtpLastName,edtStdPhone,edtParentPhone,reff;
    private Button btnRegistration;
    private ImageView img_show_password,img_show_confirm_password,backArrow;
    ProgressBar registration_loader;
    private CheckBox stdDeclaration,parDeclaraton,termsAndCon;
    String[] standard = {"10","12"};
    private Spinner edtstandYear;
    private ArrayAdapter standardAdp;
    private TextView read1,read2,read3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    private void init() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtstandYear = findViewById(R.id.edtstandYear);
        edtPname = findViewById(R.id.edtPname);
        edtpLastName = findViewById(R.id.edtpLastName);
        edtStdPhone = findViewById(R.id.edtStdPhone);
        edtParentPhone = findViewById(R.id.edtParentPhone);
        edtStdDob = findViewById(R.id.edtStdDob);
        edtSchoolName = findViewById(R.id.edtSchoolName);
        edtConfPass = findViewById(R.id.edtConfPass);
        stdDeclaration = findViewById(R.id.stdDeclaration);
        stdDeclaration = findViewById(R.id.stdDeclaration);
        termsAndCon = findViewById(R.id.termsAndCon);
        read1 = findViewById(R.id.read1);
        read2 = findViewById(R.id.read2);
        read3 = findViewById(R.id.read3);
        reff = findViewById(R.id.reff);
        registration_loader = findViewById(R.id.registration_loader);
        backArrow = findViewById(R.id.backArrow);

        btnRegistration = findViewById(R.id.btnRegistration);
        setSpinner();
       // setText();
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validField();
            }
        });
        read1.setOnClickListener(this);
        read2.setOnClickListener(this);
        read3.setOnClickListener(this);
        edtStdDob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Registration.this, AlertDialog.THEME_HOLO_LIGHT, Registration.this, year, month, day);
                dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                dialog.show();
            }
        });
    }
    public  void validField(){
        if (edtFirstName.getText().toString().trim().length() == 0) {
            edtFirstName.setError("Please enter first name");
            edtFirstName.requestFocus();
        } else if (edtLastName.getText().toString().trim().length() == 0) {
            edtLastName.setError("Please enter last name");
            edtLastName.requestFocus();
        }  else if (edtEmail.getText().toString().trim().length() == 0) {
            edtEmail.setError("Please enter Login Id");
            edtEmail.requestFocus();
        } else if (edtPass.getText().toString().trim().length() == 0) {
            edtPass.setError("Please enter Password");
            edtPass.requestFocus();
        } else if (!edtPass.getText().toString().equals(edtConfPass.getText().toString())) {
            edtConfPass.setError(getResources().getString(R.string.diff_password_err));
            edtConfPass.requestFocus();
        }

//        else if(edtSchoolName.getText().toString().length()==0){
//            edtSchoolName.requestFocus();
//            edtSchoolName.setError("Please Enter School Name");
//        }

//        else if(edtstandYear.getText().toString().length()==0){
//            edtstandYear.requestFocus();
//            edtstandYear.setError("Please Enter Standard");
//        }

//        else if (edtPname.getText().toString().trim().length() == 0) {
//            edtPname.setError("Please enter Parent Name");
//            edtPname.requestFocus();
//        }

//        else if (edtpLastName.getText().toString().trim().length() == 0) {
//            edtpLastName.setError("Please enter Parent LastName");
//            edtpLastName.requestFocus();
//        }

        else if (edtStdPhone.getText().toString().trim().length() != 10) {
            edtStdPhone.setError("Please enter Phone Number");
            edtStdPhone.requestFocus();
        }
//        else if (edtParentPhone.getText().toString().trim().length() != 10) {
//            edtParentPhone.setError("Please enter Parent Phone");
//            edtParentPhone.requestFocus();
//        }
        else if (edtStdDob.getText().toString().trim().length() == 0) {
            edtStdDob.setError("Please enter Birth date");
            edtStdDob.requestFocus();
        }
        else if (!stdDeclaration.isChecked()) {
            Utils.showAlertDialog(Registration.this,"Please Check Student Declaration!");
        }else if (!termsAndCon.isChecked()) {
            Utils.showAlertDialog(Registration.this,"Please Check Terms And Conditions!");
        }
        else {
            if (isConnectedInternet(Registration.this) == true) {
                registration_loader.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Call API", Toast.LENGTH_SHORT).show();
               // toggleTouch(false);
                ApiUtil.registration(edtEmail.getText().toString(),
                        edtPass.getText().toString(),
                        "add",edtStdPhone.getText().toString(),"City","state",
                        "India","3","443001",edtFirstName.getText().toString(),
                        edtLastName.getText().toString(),edtStdDob.getText().toString(),
                        "parent name",edtstandYear.getSelectedItem().toString(),"Objective",
                        "school",true,true,
                        "commitment","Reff","Pnote",true,new RegistrationHandler());
            }else{
                if (Registration.this!= null)
                    Utils.showSettingsAlert(Registration.this);
            }
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        int month=i1+1;
        edtStdDob.setText(""+i2+"/"+month+"/"+i);
        edtStdDob.setError(null,null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.read1:
                Intent intent = new Intent(Registration.this, FragmentContainer.class);
                intent.putExtra("FRAGMENT","WebFragment");
                intent.putExtra("URL","student_declaration.html");
                startActivity(intent);
                break;
            case R.id.read2:
                Intent intent2 = new Intent(Registration.this, FragmentContainer.class);
                intent2.putExtra("FRAGMENT","WebFragment");
                intent2.putExtra("URL","parent_declaration.html");
                startActivity(intent2);
                break;
            case R.id.read3:
                Intent intent3 = new Intent(Registration.this, FragmentContainer.class);
                intent3.putExtra("FRAGMENT","WebFragment");
                intent3.putExtra("URL","terms_conditions.html");
                startActivity(intent3);
                break;

            case R.id.backArrow:
                finish();
                break;
        }
    }

    class RegistrationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            registration_loader.setVisibility(View.GONE);
            //toggleTouch(true);
            Log.e("rr", "msg=" + msg);
            if(msg.obj.equals(201)){
                //Utils.showAlertDialog(Registration.this, "Registration Successful!");
                Toast.makeText(Registration.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Registration.this,Login.class));
            }else{
                Utils.showConfirmDialog(Registration.this, "Username is already taken, Please try different username.");
            }
        }
    }
    private void setText(){
        edtFirstName.setText("akash");
        edtLastName.setText("Ingle");
        edtEmail.setText("akash@gmail.com");
       // edtPass.setText("123456");
//        edtstandYear.setText("10");
        edtPname.setText("asdasd");
        edtpLastName.setText("asdasd");
        edtStdPhone.setText("9822455896");
        edtParentPhone.setText("9822455896");
        edtStdDob.setText("25/05/1395");
        edtSchoolName.setText(".edtSchoolName");
        reff.setText(".reff");
    }
    private void setSpinner() {
        standardAdp = new ArrayAdapter(Registration.this,android.R.layout.simple_spinner_item,standard);
        standardAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtstandYear.setAdapter(standardAdp);
    }
}