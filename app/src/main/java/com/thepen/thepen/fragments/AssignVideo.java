package com.thepen.thepen.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.thepen.thepen.R;
import com.thepen.thepen.ThePen;
import com.thepen.thepen.activities.VideoActivity;
import com.thepen.thepen.adapters.AdminListAdp;
import com.thepen.thepen.admin.SuperAdmin;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.entity.AssignTo;
import com.thepen.thepen.entity.UpdateVideo;
import com.thepen.thepen.entity.User;
import com.thepen.thepen.entity.UserList;
import com.thepen.thepen.entity.Video;
import com.thepen.thepen.entity.VideoList;
import com.thepen.thepen.helper.Constants;
import com.thepen.thepen.helper.UserInfo;
import com.thepen.thepen.utils.Utils;

import java.io.Serializable;

public class AssignVideo extends Fragment implements View.OnClickListener {
    View view;
    private Spinner spnAdmin2;
    private ImageView imgThumb,btnPlay;
    private Button btnVerify;
    private TextView videoName,authorName,descrption,info,ttxt;
    private ArrayAdapter admin2Adp;
    private Video video;
    private Button btnAssign,idMakeFree,idMakePaid,idPublish,idUnPublish;
    public AssignVideo() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_assign_to, container, false);
        spnAdmin2 = view.findViewById(R.id.spnAdmin2);
        videoName = view.findViewById(R.id.videoName);
        authorName = view.findViewById(R.id.authorName);
        descrption = view.findViewById(R.id.txtDescription);
        imgThumb = view.findViewById(R.id.imgThumb);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnAssign = view.findViewById(R.id.btnAssign);
        spnAdmin2 = view.findViewById(R.id.spnAdmin2);
        idMakeFree = view.findViewById(R.id.idMakeFree);
        idMakePaid = view.findViewById(R.id.idMakePaid);
        idPublish = view.findViewById(R.id.idPublish);
        idUnPublish = view.findViewById(R.id.idUnPublish);
        info = view.findViewById(R.id.info);
        ttxt = view.findViewById(R.id.ttxt);
        btnVerify = view.findViewById(R.id.btnVerify);

        Intent intent = getActivity().getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        video = (Video) args.getSerializable("Video");
        videoName.setText(video.videoName);
        authorName.setText(video.userName);
        descrption.setText(video.description);
        info.setText(video.educationBoard+" "
                +video.medium+" "
                +video.std+" " + video.subject+" By "+video.userName);
        Glide.with(this)
                .load(Constants.FILE_URL +video.imagePath)
                .placeholder(R.drawable.logo_with_text)
                .into(imgThumb);

        btnAssign.setOnClickListener(this);
        idMakePaid.setOnClickListener(this);
        idMakeFree.setOnClickListener(this);
        btnPlay.setOnClickListener(this);

        idUnPublish.setOnClickListener(this);
        idPublish.setOnClickListener(this);

        btnVerify.setOnClickListener(this);
        hideAssignOptions();

        if(intent.getBooleanExtra("VERIYFY",false)){
            spnAdmin2.setVisibility(View.GONE);
            btnAssign.setVisibility(View.GONE);
            hideButtons();
            ttxt.setVisibility(View.GONE);
        }else{
            btnVerify.setVisibility(View.GONE);
            ApiUtil.getUserList("1",new GetAdminList());
        }
        if(!ThePen.IS_APPROVED)
            hideButtons();
        if(ThePen.IS_YET_TO_APPROVED){
            spnAdmin2.setVisibility(View.VISIBLE);
            btnAssign.setVisibility(View.VISIBLE);
            ttxt.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void hideAssignOptions() {
        spnAdmin2.setVisibility(View.GONE);
        btnAssign.setVisibility(View.GONE);
        ttxt.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAssign:
                AssignTo  assignTo= new AssignTo();
                User user = (User)(spnAdmin2.getSelectedItem());
                assignTo.sendTouserName = user.userName;
                assignTo.video_id = video.video_id;
                ApiUtil.sendVideoForApproval(assignTo,new SendToHandler());
                break;
            case R.id.idMakePaid:
                UpdateVideo updtvdo = new UpdateVideo();
                updtvdo.charge=true;
                updtvdo.status="charge";
                updtvdo.publish=true;
                updtvdo.video_id=video.video_id;
                Log.d("ADMIN"," "+updtvdo.charge);
                Log.d("ADMIN"," "+updtvdo.status);
                Log.d("ADMIN"," "+updtvdo.publish);
                Log.d("ADMIN"," "+updtvdo.video_id);
                ApiUtil.videoPublishOrUnpublish(updtvdo,new UpdateVideoHandler());
                break;
            case R.id.idMakeFree:
                UpdateVideo updtvdo1 = new UpdateVideo();
                updtvdo1.charge=false;
                updtvdo1.status="charge";
                updtvdo1.publish=true;
                updtvdo1.video_id=video.video_id;
                Log.d("ADMIN"," "+updtvdo1.charge);
                Log.d("ADMIN"," "+updtvdo1.status);
                Log.d("ADMIN"," "+updtvdo1.publish);
                Log.d("ADMIN"," "+updtvdo1.video_id);
                ApiUtil.videoPublishOrUnpublish(updtvdo1,new UpdateVideoHandler());
                break;
            case R.id.idPublish:
                UpdateVideo updtvdo2 = new UpdateVideo();
                updtvdo2.charge=false;
                updtvdo2.status="Publish";
                updtvdo2.publish=true;
                updtvdo2.video_id=video.video_id;
                Log.d("ADMIN"," "+updtvdo2.charge);
                Log.d("ADMIN"," "+updtvdo2.status);
                Log.d("ADMIN"," "+updtvdo2.publish);
                Log.d("ADMIN"," "+updtvdo2.video_id);
                ApiUtil.videoPublishOrUnpublish(updtvdo2,new UpdateVideoHandler());
                break;
            case R.id.idUnPublish:
                UpdateVideo updtvdo3 = new UpdateVideo();
                updtvdo3.charge=false;
                updtvdo3.status="Publish";
                updtvdo3.publish=false;
                updtvdo3.video_id=video.video_id;
                Log.d("ADMIN"," "+updtvdo3.charge);
                Log.d("ADMIN"," "+updtvdo3.status);
                Log.d("ADMIN"," "+updtvdo3.publish);
                Log.d("ADMIN"," "+updtvdo3.video_id);
                ApiUtil.videoPublishOrUnpublish(updtvdo3,new UpdateVideoHandler());
                break;
            case R.id.btnVerify:
                Video vd=new Video();
                vd.userName= UserInfo.getSharedInstance().getUserId();
                vd.video_id= video.video_id;
                vd.approve=true;
                ApiUtil.adminApprove(vd,new verifyHandler());
                break;
            case R.id.btnPlay:
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra("URL",video.videoPath);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",null);
                intent.putExtra("BUNDLE",args);
                getActivity().startActivity(intent);
                break;
        }
    }

    class GetAdminList extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{
                UserList ul = (UserList) msg.obj;
                Log.e("Size","GetUserList "+ul.usercontents.size());
                AdminListAdp mAdminListAdp = new AdminListAdp(getContext(), ul.usercontents);
                spnAdmin2.setAdapter(mAdminListAdp);
            }catch(Exception cce){
                Log.d("A",""+cce);
            }
        }
    }
    class SendToHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{
                Object ul = (Object) msg.obj;
                Log.e("Size","SendToHandler "+ul.toString());
                if(msg.obj.equals(401)){
                    Utils.showAlertDialog(getContext(), "Not Found");
                }
            }catch(Exception cce){
                Log.d("A",""+cce);
            }
        }
    }

    class UpdateVideoHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{
                if(msg.obj.equals(401)){
                    Utils.showAlertDialog(getContext(), "This video is not approved by Admin2, Ask admin to approved update.");
                }else{
                    Object updtvdo = (Object) msg.obj;
                    VideoList mVideoList =(VideoList) msg.obj;
                    SuperAdmin.videos.clear();
                    SuperAdmin.videos = mVideoList.getUploadVideoList();
                }
                Log.e("Size","UpdateVideoHandler ");
            }catch(Exception cce){
                Log.d("A",""+cce);
            }
        }
    }

    class verifyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{
                Object updtvdo = (Object) msg.obj;
                if(msg.obj.equals(401)){
                    Utils.showAlertDialog(getContext(), "This video is not approved by Admin2, Ask admin to approved update.");
                }
                Log.e("Size","UpdateVideoHandler "+updtvdo.toString());
            }catch(Exception cce){
                Log.d("A",""+cce);
            }
        }
    }

    private void hideButtons(){
        idMakeFree.setVisibility(View.GONE);
        idMakePaid.setVisibility(View.GONE);
        idPublish.setVisibility(View.GONE);
        idUnPublish.setVisibility(View.GONE);
    }
}
