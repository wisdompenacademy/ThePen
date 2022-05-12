package com.thepen.thepen.fragments;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thepen.thepen.R;
import com.thepen.thepen.ThePen;
import com.thepen.thepen.activities.AccountDetails;
import com.thepen.thepen.activities.FragmentContainer;
import com.thepen.thepen.activities.Login;
import com.thepen.thepen.activities.TeacherRegistration;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.entity.Registration;
import com.thepen.thepen.entity.UserProfile;
import com.thepen.thepen.helper.UserInfo;
import com.thepen.thepen.utils.Utils;

import java.io.Serializable;

public class AccountFragment extends Fragment implements View.OnClickListener {
    TextView txtName,name,txtAddress,txtPhone,txtDob,txtStandard;
    LinearLayout txtChangePass,llLogout,llAccountInfo;
    private ImageView backArrow2;
    private UserProfile userProfile;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_account, container, false);
        init();
        return view;
    }

    private void init() {
        name = view.findViewById(R.id.name);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtDob = view.findViewById(R.id.txtDob);
        txtStandard = view.findViewById(R.id.txtStandard);
        txtChangePass = view.findViewById(R.id.txtChangePass);
        backArrow2 = view.findViewById(R.id.backArrow2);
        llLogout = view.findViewById(R.id.llLogout);
        llAccountInfo = view.findViewById(R.id.llAccountInfo);

        llAccountInfo.setOnClickListener(this);
        llLogout.setOnClickListener(this);
        backArrow2.setOnClickListener(this);
        txtChangePass.setOnClickListener(this);

        name.setText(UserInfo.getSharedInstance().getUserId());
        ApiUtil.getUserProfle(UserInfo.getSharedInstance().getUserId(),new UserProfileHandler());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llAccountInfo:
                Intent intent1 = new Intent(getActivity(), AccountDetails.class);
                ThePen.userProfile=this.userProfile;
                getActivity().startActivity(intent1);
                getActivity().finish();
                break;
            case  R.id.llLogout:
                Utils.logout(getActivity());
                break;
            case R.id.backArrow2:
                getActivity().onBackPressed();
                break;
            case R.id.txtChangePass:
                Intent intent = new Intent(getContext(), FragmentContainer.class);
                intent.putExtra("FRAGMENT","CHANGEPASS");
                startActivity(intent);
                break;
        }
    }

    class UserProfileHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{
                userProfile = (UserProfile) msg.obj;
                name.setText(userProfile.usercontent.firstName+" "+userProfile.usercontent.lastName);
                txtAddress.setText("Address: "+userProfile.usercontent.address);
                txtPhone.setText("Mobile #: "+userProfile.usercontent.phoneNo);
                txtDob.setText("Date of birth: "+userProfile.usercontent.dob.substring(0,10));
                txtStandard.setText("Standard: "+userProfile.usercontent.student_std);
            }catch(Exception cce){
                Log.d("A",""+cce);
            }
        }
    }
}
