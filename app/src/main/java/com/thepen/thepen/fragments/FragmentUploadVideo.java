package com.thepen.thepen.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.thepen.thepen.R;
import com.thepen.thepen.ThePen;
import com.thepen.thepen.activities.TeachersActivity;
import com.thepen.thepen.apiservice.ApiCall;
import com.thepen.thepen.apiservice.ApiUtil;
import com.thepen.thepen.helper.PermissionsRequestor;
import com.thepen.thepen.helper.UserInfo;
import com.thepen.thepen.utils.Utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class FragmentUploadVideo extends Fragment implements View.OnClickListener {
    private static int RESULT_LOAD_VIDEO = 1;
    private PermissionsRequestor permissionsRequestor;
    String decodableString,errorMsg;
    private ImageView video,image;
    String mediaPath, mediaPath1;
    private Button btnUpload;
    TextView str1, str2;
    private boolean charge;
    private EditText edtvideoName,edtDescription;
    String[] subject = { "Select Subject","English","Science-1","Science-2",
            "Mathematics-1","Mathematics-2","Physics", "Chemistry", "Biology", "Science", "Mathematics"};

    String[] subject_admin = { "Select Subject","General","Advertisement","English","Science-1","Science-2",
            "Mathematics-1","Mathematics-2","Physics", "Chemistry", "Biology", "Science", "Mathematics"};

    String[] standard = { "Select Standard","General","10","12"};
    String[] board = { "Select Board","General","Maharashtra", "CBSC"};
    String[] medium = { "Select Medium","General","English", "Marathi","Hindi"};
    private Spinner spnsubject,spnStandard,spnBoard,spnMedium;
    private ArrayAdapter subjectAdp,standardAdp,boardAdp,mediunAdapter;
    private ProgressBar progressBar;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_upload_video, container, false);
        init(view);
        handleAndroidPermissions();
        return view;
    }
    private void init(View view) {
        video =view.findViewById(R.id.video);
        image =view.findViewById(R.id.thubnailImag);
        btnUpload=view.findViewById(R.id.btnUpload);
        progressBar=view.findViewById(R.id.progressBar);

        edtvideoName=view.findViewById(R.id.videoName);
        spnsubject=view.findViewById(R.id.subject);
        spnStandard=view.findViewById(R.id.standered);
        edtDescription=view.findViewById(R.id.description);
        spnBoard=view.findViewById(R.id.board);
        spnMedium=view.findViewById(R.id.medium);

        setSpinner();

        str1=view.findViewById(R.id.filename1);
        str2=view.findViewById(R.id.filename2);

        video.setOnClickListener(this);
        image.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }
    /*
     * PICK THE VIDEO AND EXTRACT ITS ADDRESS
     */
    public void loadVideoFromGallery()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_VIDEO);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When a video is picked
            if (requestCode == RESULT_LOAD_VIDEO && resultCode == RESULT_OK && null != data) {
                // Get the video from data
                Uri selectedVideo = data.getData();
                String[] filePathColumn = { MediaStore.Video.Media.DATA };
                Cursor cursor = getActivity().getContentResolver().query(selectedVideo,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                decodableString = cursor.getString(columnIndex);
                Log.i("mok",decodableString);
                str2.setText(decodableString);
                cursor.close();
            } else if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                str1.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                image.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
            }
            else {
                Toast.makeText(getActivity(), "You haven't picked any video",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }
    public void paramUpload(String yourStringPath,String imagePath){
        progressBar.setVisibility(View.VISIBLE);
        btnUpload.setEnabled(false);
        ShowSnackBar("Uploading...");
//        final View customLayout = getLayoutInflater().inflate(R.layout.loader, null);
//        AlertDialog d = new AlertDialog.Builder(UploadVideo.this).setTitle("Uploading...").setCancelable(false).setView(customLayout).show();
        // Utils.showConfirmDialog(UploadVideo.this, Constants.HEADER_AuthorizationVideo+ThePen.accessToken);
        File file = new File(yourStringPath);
        File fileImage = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileImage);
        MultipartBody.Part multipartBodyVideo =MultipartBody.Part.createFormData("videoFile",file.getName(),requestFile);
        MultipartBody.Part multipartBodyImage =MultipartBody.Part.createFormData("image",file.getName(),requestImageFile);
        ApiCall getResponse = ApiUtil.getInstance().create(ApiCall.class);
        Call<Object> call = getResponse.uploadkycdoc("Bearer "+
                        UserInfo.getSharedInstance().getToken(),
                multipartBodyVideo,
                multipartBodyImage,
                spnsubject.getSelectedItem().toString().replaceAll("^\"+|\"+$", ""),
                spnStandard.getSelectedItem().toString().replaceAll("^\"+|\"+$", ""),
                true,
                UserInfo.getSharedInstance().getUserId(),
                edtvideoName.getText().toString().replaceAll("^\"+|\"+$", ""),
                edtDescription.getText().toString().replaceAll("^\"+|\"+$", ""),
                spnBoard.getSelectedItem().toString().replaceAll("^\"+|\"+$", ""),
                spnMedium.getSelectedItem().toString().replaceAll("^\"+|\"+$", ""));
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> responseCall, Response<Object> response) {
                Log.e("UPLOAD",""+response.raw());
//                d.dismiss();
                progressBar.setVisibility(View.GONE);
                btnUpload.setEnabled(true);
                ShowSnackBar("Uploaded");
                // UploadVideo.this.finish();
                if (response.body() != null) {
                    //Utils.showConfirmDialog(UploadVideo.this, ""+response.toString());
                } else {
                    Utils.showConfirmDialog(getContext(), "Aksh"+response.toString());
                }
            }
            @Override
            public void onFailure(Call<Object> responseCall, Throwable t) {
                t.printStackTrace();
                Log.e("UPLOAD",""+t.getMessage());
                Utils.showConfirmDialog(getContext(), t.getMessage()+" Retry");
                progressBar.setVisibility(View.GONE);
                btnUpload.setEnabled(true);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.video:
                loadVideoFromGallery();
                break;
            case R.id.thubnailImag:
                pickImage();
                break;
            case R.id.btnUpload:
                if(getData()){
                    paramUpload(decodableString,mediaPath);
                }else{
                    Utils.showConfirmDialog(getContext(), errorMsg);
                }
                break;

        }
    }

    private boolean getData() {
        if(edtvideoName.getText().toString().isEmpty()){
            errorMsg="Please Enter Video Name";
            return false;
        }
        if(spnsubject.getSelectedItem().toString().equals("Select Subject")){
            errorMsg="Please Select Subject";
            return false;
        }
        if(spnStandard.getSelectedItem().toString().equals("Select Standard")){
            errorMsg="Please Select Standard";
            return false;
        }
        if(spnBoard.getSelectedItem().toString().equals("Select Board")){
            errorMsg="Please Select Education Board";
            return false;
        }
        if(spnMedium.getSelectedItem().toString().equals("Select Medium")){
            errorMsg="Please Select Education Medium";
            return false;
        }
        if(edtDescription.getText().toString().isEmpty()){
            errorMsg="Please Enter Video Description";
            return false;
        }
        if(decodableString==null){
            errorMsg="Please Select Video File";
            return false;
        }if(mediaPath==null){
            errorMsg="Please Select Cover File";
            return false;
        }
        else{
            return true;
        }
    }

    private void pickImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }
    private void setSpinner() {
        if(UserInfo.getSharedInstance().getRole().equals("ROLE_TEACHER")){
            subjectAdp = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,subject);
        }else{
            subjectAdp = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,subject_admin);
        }
        subjectAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnsubject.setAdapter(subjectAdp);

        standardAdp = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,standard);
        standardAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStandard.setAdapter(standardAdp);

        boardAdp = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,board);
        boardAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBoard.setAdapter(boardAdp);

        mediunAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,medium);
        mediunAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMedium.setAdapter(mediunAdapter);
    }
    private void ShowSnackBar(String msg){
        Snackbar snackbar = Snackbar.make(progressBar, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void handleAndroidPermissions() {
        permissionsRequestor = new PermissionsRequestor(getActivity());
        permissionsRequestor.request(new PermissionsRequestor.ResultListener() {

            @Override
            public void permissionsGranted() {
                //loadMapScene();
            }

            @Override
            public void permissionsDenied() {
                Log.e("search", "Permissions denied by user.");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsRequestor.onRequestPermissionsResult(requestCode, grantResults);
    }
}
