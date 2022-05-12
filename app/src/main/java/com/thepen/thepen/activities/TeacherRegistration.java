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

public class TeacherRegistration extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText edtDob,edtFirstName,edtLastName,edtEmail,edtPass,edtConfPass,teachphone,city,state,university,degree,experience;
    private Button btnRegistration;
//    private TextView edtDob;
    ProgressBar registration_loader;
    private CheckBox teachDeclaration,teach_terms;
    private TextView read1,read2;
    private Spinner exptSubject;
    private ImageView backArrow;
    String[] subject = { "Select Subject","General","English","Science-1","Science-2",
            "Mathematics-1","Mathematics-2","Physics", "Chemistry", "Biology", "Science", "Mathematics"};
    private ArrayAdapter subjectAdp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);
        init();
    }

    private void init() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtConfPass = findViewById(R.id.edtConfPass);
        teachphone = findViewById(R.id.teachphone);
        edtDob = findViewById(R.id.edtDob);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        exptSubject = findViewById(R.id.subjectExpt);
        university = findViewById(R.id.university);
        degree = findViewById(R.id.degree);
        experience = findViewById(R.id.experience);
        registration_loader = findViewById(R.id.registration_loader);
        btnRegistration = findViewById(R.id.btnRegistration);
        teachDeclaration = findViewById(R.id.techDeclaration);
        teach_terms = findViewById(R.id.teach_terms);
        read1 = findViewById(R.id.read1);
        read2 = findViewById(R.id.read2);
        backArrow = findViewById(R.id.backArrow);
        setSpinner();
//        setText();
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validField();
            }
        });
        read1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherRegistration.this, FragmentContainer.class);
                intent.putExtra("FRAGMENT","WebFragment");
                intent.putExtra("URL","teacher_declaration.html");
                startActivity(intent);
            }
        });
        read2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherRegistration.this, FragmentContainer.class);
                intent.putExtra("FRAGMENT","WebFragment");
                intent.putExtra("URL","teach_terms_condition.html");
                startActivity(intent);
            }
        });
        edtDob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(TeacherRegistration.this, AlertDialog.THEME_HOLO_LIGHT,TeacherRegistration.this, year, month, day);
                dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                dialog.show();
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        int month=i1+1;
        edtDob.setText(""+i2+"/"+month+"/"+i);
        edtDob.setError(null,null);
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
        } else if (teachphone.getText().toString().trim().length() != 10) {
            teachphone.setError("Please enter Phone Number");
            teachphone.requestFocus();
        }
        else if(city.getText().toString().length()==0){
            city.requestFocus();
            city.setError("Please Enter City");
        } else if (state.getText().toString().trim().length() == 0) {
            state.setError("Please enter State");
            state.requestFocus();
        }else if(edtDob.getText().toString().length()==0){
            edtDob.requestFocus();
            edtDob.setError("Please Enter Birth date");
        } else if(exptSubject.getSelectedItem().toString().equals("Select Subject")){
            Utils.showConfirmDialog(TeacherRegistration.this, "Please Select Subject");
        }
//        else if (exptSubject.getText().toString().trim().length() == 0) {
//            exptSubject.setError("Please enter subjects");
//            exptSubject.requestFocus();
//        }

         else if (university.getText().toString().trim().length() == 0) {
            university.setError("Please enter University");
            university.requestFocus();
        } else if (degree.getText().toString().trim().length() == 0) {
            degree.setError("Please enter Degree");
            degree.requestFocus();
        }
//         else if (experience.getText().toString().trim().length() == 0) {
//            experience.setError("Please enter Experience");
//            experience.requestFocus();
//        }

//        else if(!Utils.validatePassword(edt_password.getText().toString().trim()) ){
//            edt_password.setError(getString(R.string.password_validation));
//            edt_password.requestFocus();
//
//        }

//        else if (edt_confirm_password.getText().toString().trim().length() == 0) {
//            edt_confirm_password.setError("Please enter confirm Password");
//            edt_confirm_password.requestFocus();
//        }

//        else if (edt_password.getText().toString().trim().length() != edt_confirm_password.getText().toString().trim().length()) {
//            edt_confirm_password.setError(getString(R.string.password_bot_match));
//            edt_confirm_password.requestFocus();
//
//        }
        else if (!teachDeclaration.isChecked()) {
            Utils.showAlertDialog(TeacherRegistration.this,"Please Check Declaration!");
        }else if (!teach_terms.isChecked()) {
            Utils.showAlertDialog(TeacherRegistration.this,"Please Check Terms And Condition!");
        }
        else {
            if (isConnectedInternet(TeacherRegistration.this) == true) {
                registration_loader.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Call API", Toast.LENGTH_SHORT).show();
                // toggleTouch(false);
                ApiUtil.TeacherRegistration(edtEmail.getText().toString(),edtPass.getText().toString(),
                        "add",teachphone.getText().toString(),"City","state",
                        "India","2","443001",edtFirstName.getText().toString(),
                        edtLastName.getText().toString(),edtDob.getText().toString(),
                        exptSubject.getSelectedItem().toString(),university.getText().toString(),degree.getText().toString(),
                        experience.getText().toString(),true,true,
                        "commitment",true,new RegistrationHandler());
            }else{
                if (TeacherRegistration.this!= null)
                    Utils.showSettingsAlert(TeacherRegistration.this);
            }
        }
    }
    class RegistrationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            registration_loader.setVisibility(View.GONE);
            Log.e("rr", "msg=" + msg);
            if(msg.obj.equals(201)){
                Toast.makeText(TeacherRegistration.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                finish();
                //Utils.showAlertDialog(TeacherRegistration.this, "Registration Successful!.");
            }else if(msg.obj.equals(409)){
                Utils.showConfirmDialog(TeacherRegistration.this, "Username is already taken, Please try different username.");
            }else{
                Utils.showConfirmDialog(TeacherRegistration.this, msg.toString());
            }
        }
    }

    private void setText(){
        edtFirstName.setText("akash");
        edtLastName.setText("Ingle");
        edtEmail.setText("akash@gmail.com");
        edtPass.setText("123456");
        university.setText("SPPU");
        city.setText("asdasd");
        degree.setText("MA");
        teachphone.setText("9822455896");
        edtDob.setText("25/05/1395");
        //exptSubject.setText("Math");
        state.setText("MH");
        experience.setText("Fresher");
    }
    private void setSpinner() {
        subjectAdp = new ArrayAdapter(TeacherRegistration.this,android.R.layout.simple_spinner_item,subject);
        subjectAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exptSubject.setAdapter(subjectAdp);
    }
}
