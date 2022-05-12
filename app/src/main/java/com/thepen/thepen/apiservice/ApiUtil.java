package com.thepen.thepen.apiservice;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.thepen.thepen.ThePen;
import com.thepen.thepen.entity.AssignTo;
import com.thepen.thepen.entity.ChangePassword;
import com.thepen.thepen.entity.Registration;
import com.thepen.thepen.entity.UpdateVideo;
import com.thepen.thepen.entity.User;
import com.thepen.thepen.entity.UserList;
import com.thepen.thepen.entity.UserLogin;
import com.thepen.thepen.entity.UserProfile;
import com.thepen.thepen.entity.Video;
import com.thepen.thepen.entity.VideoList;
import com.thepen.thepen.helper.Constants;
import com.thepen.thepen.helper.UserInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiUtil {

    private static Retrofit mRetrofit;

    private ApiUtil() {
    }

    public static Retrofit getInstance() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .callTimeout(5, TimeUnit.MINUTES)
                .build();

        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().baseUrl(Constants.DOMAIN_URL).client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create()).build();

        }
        return mRetrofit;
    }
    public  static  void registration(String userName, String password, String address, String phone,
                                      String city, String state, String country, String roleId,
                                      String zipCode, String firstName, String lastName, String dob,
                                      String parentName, String student_std, String objective,
                                      String schoolName, boolean declarationBystudent, boolean declarationByparent,
                                      String commitement, String referenceName, String parentNote,
                                      boolean termsAndcondtion, final Handler handler){

        Registration registration = new Registration();
        registration.setUserName(userName);
        registration.setPassword(password);
        registration.setAddress(address);
        Log.d("PASS",""+password);
        registration.setPassword(password);
        registration.setPhone(phone);
        registration.setCity(city);
        registration.setState(state);
        registration.setCountry(country);
        registration.setRoleId(roleId);
        registration.setZipCode(zipCode);
        registration.setFirstName(firstName);
        registration.setLastName(lastName);
        registration.setDob(dob);
        registration.setParentName(parentName);
        registration.setStudent_std(student_std);
        registration.setObjective(objective);
        registration.setSchoolName(schoolName);
        registration.setDeclarationBystudent(declarationBystudent);
        registration.setDeclarationByparent(declarationByparent);
        registration.setCommitement(commitement);
        registration.setReferenceName(referenceName);
        registration.setParentNote(parentNote);
        registration.setTermsAndcondtion(termsAndcondtion);


        Call<Registration> call = ApiUtil.getInstance().create(ApiCall.class).Registration(registration);
//        Log.d("rr call", String.valueOf(call));
        call.enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
                Log.d("rr", String.valueOf(response.raw()));
                if(response.isSuccessful()  && response.body() != null){
                Log.d("rr", String.valueOf(response.body()));
                Message message =new Message();
                message.obj = response.code();
                handler.sendMessage(message);
                }
                else{
                    Message message =new Message();
                    message.obj  = response.code() ;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<Registration> call, Throwable t) {
                t.printStackTrace();
                Message message =new Message();
                message.obj  = t.getMessage() ;
                handler.sendMessage(message);
            }
        });

    }

    public  static  void userLogin(String emailID, String password,final Handler handler){
        UserLogin userLogin = new UserLogin();
        userLogin.setUserName(emailID);
        userLogin.setPassword(password);
        userLogin.setGrant_type("password");

        Call<UserLogin> call = ApiUtil.getInstance().create(ApiCall.class).UserLogin2("password",emailID,password);
        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
//                if(response.isSuccessful() && response.body() != null){
                Log.d("rr","raw"+ response.raw());
                if(response.code()==200 && response.body() != null){
                    Log.d("rr","token"+response.body());
                        Message message =new Message();
                        message.obj  =response.body().getAccess_token();
                        UserInfo.getSharedInstance().setToken(response.body().getAccess_token());
                        Log.d("rr", message.obj.toString());
                        handler.sendMessage(message);
                }else{
                    Log.d("rr","response unsuccessful");
                    Message message =new Message();
                    message.obj  = response.code();
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                t.printStackTrace();
                Log.d("rr","response onFailure"  +  t.getMessage() );
                Message message =new Message();
                message.obj  = 103;
                handler.sendMessage(message);
            }
        });
    }
    public  static  void TeacherRegistration(String userName, String password, String address, String phone,
                                             String city, String state, String country, String roleId,
                                             String zipCode, String firstName, String lastName, String dob,
                                             String exptSubject, String university, String degree, String experience,
                                             boolean b, boolean andcondtion, String commitement, boolean termsAndcondtion, final Handler handler){

        Registration registration = new Registration();
        registration.setUserName(userName);
        registration.setPassword(password);
        registration.setAddress(address);
        registration.setPassword(password);
        registration.setPhone(phone);
        registration.setCity(city);
        registration.setState(state);
        registration.setCountry(country);
        registration.setRoleId(roleId);
        registration.setZipCode(zipCode);
        registration.setFirstName(firstName);
        registration.setLastName(lastName);
        registration.setDob(dob);
        registration.setCommitement(commitement);
        registration.setTermsAndcondtion(termsAndcondtion);
        registration.setExptSubject(exptSubject);
        registration.setUniversity(university);
        registration.setDegree(degree);
        registration.setExperience(experience);
        registration.setDeclarationByTeacher(true);

        Call<Registration> call = ApiUtil.getInstance().create(ApiCall.class).Registration(registration);
        Log.d("rr call", String.valueOf(call));
        call.enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
                Log.d("rr", String.valueOf(response.raw()));
                if(response.isSuccessful()  && response.body() != null){
                    Log.d("rr", String.valueOf(response.body()));
                    Message message =new Message();
                    message.obj = response.code();
                    handler.sendMessage(message);
                }
                else{
                    Message message =new Message();
                    message.obj  = response.code() ;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<Registration> call, Throwable t) {
                t.printStackTrace();
                Message message =new Message();
                message.obj  = t.getMessage() ;
                handler.sendMessage(message);
            }
        });

    }

    public  static  void getVideoList(String subject, String std, String educationBoard,
            String medium,String rollID,String userName, final Handler handler){
        Log.e("VideoList","query "+subject+std+educationBoard+medium);
        VideoList videoList = new VideoList();
        videoList.setSubject(subject);
        videoList.setStd(std);
        videoList.setEducationBoard(educationBoard);
        videoList.setMedium(medium);
        videoList.setRoleId(rollID);
        videoList.setUserName(userName);

        Call<VideoList> call = ApiUtil.getInstance().create(ApiCall.class).getVideoList("Bearer "
                        +UserInfo.getSharedInstance().getToken(),videoList);
        call.enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                Log.d("VideoList", String.valueOf(response.raw()));
                if(response.isSuccessful()  && response.body() != null){
                    Message message =new Message();
                    message.obj = response.body();
                    handler.sendMessage(message);
                    //Log.d("AAA", String.valueOf(response.body().getUploadVideoList().get(0).description));
                } else{
                    Message message =new Message();
                    message.obj  = response.code() ;
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
                t.printStackTrace();
                Message message =new Message();
                message.obj  = t.getMessage() ;
                handler.sendMessage(message);
            }
        });
    }

    public  static  void getuploadvideoList(final Handler handler){
        Call<VideoList> call = ApiUtil.getInstance().create(ApiCall.class).getuploadvideoList("Bearer "
                +UserInfo.getSharedInstance().getToken());
        call.enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                Log.d("VideoList", String.valueOf(response.raw()));
                if(response.isSuccessful()  && response.body() != null){
                    Message message =new Message();
                    message.obj = response.body();
                    handler.sendMessage(message);
                } else{
                    Message message =new Message();
                    message.obj  = response.code() ;
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
                t.printStackTrace();
                Message message =new Message();
                message.obj  = t.getMessage() ;
                handler.sendMessage(message);
            }
        });
    }

    public  static  void getUserProfle(String userName,final Handler handler){
        Call<UserProfile> call = ApiUtil.getInstance().create(ApiCall.class).getUserProfle("Bearer "
                +UserInfo.getSharedInstance().getToken(),userName);
        Log.d("Akash call", String.valueOf(call));
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                Log.d("Akash", String.valueOf(response.raw()));
                if(response.isSuccessful()  && response.body() != null){
                    UserProfile upf = response.body();
                    Log.d("Akash", String.valueOf(upf.usercontent.firstName));
                    Message message =new Message();
                    message.obj = response.body();
                    handler.sendMessage(message);
                }
                else{
                    Message message =new Message();
                    message.obj  = response.code() ;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                t.printStackTrace();
                Message message =new Message();
                message.obj  = t.getMessage() ;
                handler.sendMessage(message);
            }
        });
    }

    public  static  void changePassword(String oldPass,String newPass,String userName, final Handler handler){
        ChangePassword changePassword = new ChangePassword();
        changePassword.setUserName(userName);
        changePassword.setOldPassword(oldPass);
        changePassword.setNewPassword(newPass);
        Call<Object> call = ApiUtil.getInstance().create(ApiCall.class).changePassword("Bearer "
                +UserInfo.getSharedInstance().getToken(),changePassword);
        Log.d("ThePen call", String.valueOf(call));
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("rr", String.valueOf(response.raw()));
                if(response.isSuccessful()  && response.body() != null){
                    Log.d("rr", String.valueOf(response.body()));
                    Message message =new Message();
                    message.obj = response.code();
                    handler.sendMessage(message);
                }
                else{
                    Message message =new Message();
                    message.obj  = response.code() ;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                Message message =new Message();
                message.obj  = t.getMessage() ;
                handler.sendMessage(message);
            }
        });
    }

    public  static  void getUserList(String roleID,final Handler handler){
        Call<UserList> call = ApiUtil.getInstance().create(ApiCall.class).getUserList("Bearer "
                +UserInfo.getSharedInstance().getToken(),roleID);
        Log.d("ThePen call", String.valueOf(call));
        call.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                Log.d("rr", String.valueOf(response.raw()));
                if(response.isSuccessful()  && response.body() != null){
                    Log.d("rr", String.valueOf(response.body()));
                    Message message =new Message();
                    message.obj = response.body();
                    handler.sendMessage(message);
                }
                else{
                    Message message =new Message();
                    message.obj  = response.code() ;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                t.printStackTrace();
                Message message =new Message();
                message.obj  = t.getMessage() ;
                handler.sendMessage(message);
            }
        });
    }

    public static void sendVideoForApproval(AssignTo assignTo, final Handler handler){
        Call<VideoList> call = ApiUtil.getInstance().create(ApiCall.class).sendVideoForApproval("Bearer "
                +UserInfo.getSharedInstance().getToken(), assignTo);
        Log.d("ThePen call", String.valueOf(call));
        call.enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                Log.d("rr", String.valueOf(response.raw()));
                if(response.isSuccessful()  && response.body() != null){
                    Log.d("rr", String.valueOf(response.body()));
                    Message message =new Message();
                    message.obj = response.body();
                    handler.sendMessage(message);
                }
                else{
                    Message message =new Message();
                    message.obj  = response.code() ;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
                t.printStackTrace();
                Message message =new Message();
                message.obj  = t.getMessage() ;
                handler.sendMessage(message);
            }
        });
    }

    public static void videoPublishOrUnpublish(UpdateVideo mUpdateVideo, final Handler handler){
        Call<VideoList> call = ApiUtil.getInstance().create(ApiCall.class).videoPublishOrUnpublish("Bearer "
                +UserInfo.getSharedInstance().getToken(), mUpdateVideo);
        Log.d("ThePen call", String.valueOf(call));
        call.enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                Log.d("rr", String.valueOf(response.raw()));
                if(response.isSuccessful()  && response.body() != null){
                    Log.d("rr", String.valueOf(response.body()));
                    Message message =new Message();
                    message.obj = response.body();
                    handler.sendMessage(message);
                }
                else{
                    Message message =new Message();
                    message.obj  = response.code() ;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
                t.printStackTrace();
                Message message =new Message();
                message.obj  = t.getMessage() ;
                handler.sendMessage(message);
            }
        });
    }

    public static void changeRoleToAdmin(User user, final Handler handler){
        Call<UserList> call = ApiUtil.getInstance().create(ApiCall.class).changeRoleToAdmin("Bearer "
                +UserInfo.getSharedInstance().getToken(), user);
        Log.d("ThePen call", String.valueOf(call));
        call.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                Log.d("rr", String.valueOf(response.raw()));
                if(response.isSuccessful()  && response.body() != null){
                    Log.d("rr", String.valueOf(response.body()));
                    Message message =new Message();
                    message.obj = response.body();
                    handler.sendMessage(message);
                }
                else{
                    Message message =new Message();
                    message.obj  = response.code() ;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                t.printStackTrace();
                Message message =new Message();
                message.obj  = t.getMessage() ;
                handler.sendMessage(message);
            }
        });
    }
    public  static  void getVideosToVerify(String userName,final Handler handler){
        Call<VideoList> call = ApiUtil.getInstance().create(ApiCall.class).getVideosToVerify("Bearer "
                +UserInfo.getSharedInstance().getToken(),userName);
        call.enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                Log.d("VideoList", String.valueOf(response.raw()));
                if(response.isSuccessful()  && response.body() != null){
                    Message message =new Message();
                    message.obj = response.body();
                    handler.sendMessage(message);
                } else{
                    Message message =new Message();
                    message.obj  = response.code() ;
                    handler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
                t.printStackTrace();
                Message message =new Message();
                message.obj  = t.getMessage() ;
                handler.sendMessage(message);
            }
        });
    }
    public static void adminApprove(Video video, final Handler handler){
        Call<Object> call = ApiUtil.getInstance().create(ApiCall.class).adminApprove("Bearer "
                +UserInfo.getSharedInstance().getToken(), video);
        Log.d("ThePen call", String.valueOf(call));
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("rr", String.valueOf(response.raw()));
                if(response.isSuccessful()  && response.body() != null){
                    Log.d("rr", String.valueOf(response.body()));
                    Message message =new Message();
                    message.obj = response.body();
                    handler.sendMessage(message);
                }
                else{
                    Message message =new Message();
                    message.obj  = response.code() ;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
                Message message =new Message();
                message.obj  = t.getMessage() ;
                handler.sendMessage(message);
            }
        });
    }

public  static  void updateUserProfile(String userName, String address, String phone,
                                  String city, String state, String country,
                                  String zipCode, String firstName, String lastName, String dob,
                                  String parentName, String student_std,
                                  String schoolName, final Handler handler){

    Registration registration = new Registration();
    registration.setUserName(userName);
    registration.setAddress(address);
    registration.setPhone(phone);
    registration.setCity(city);
    registration.setState(state);
    registration.setCountry(country);
    registration.setZipCode(zipCode);
    registration.setFirstName(firstName);
    registration.setLastName(lastName);
    registration.setDob(dob);
    registration.setParentName(parentName);
    registration.setStudent_std(student_std);
    registration.setSchoolName(schoolName);


    Call<Registration> call = ApiUtil.getInstance().create(ApiCall.class).updateUserProfile("Bearer "
            +UserInfo.getSharedInstance().getToken(),registration);
//        Log.d("rr call", String.valueOf(call));
    call.enqueue(new Callback<Registration>() {
        @Override
        public void onResponse(Call<Registration> call, Response<Registration> response) {
            Log.d("rr", String.valueOf(response.raw()));
            if(response.isSuccessful()  && response.body() != null){
                Log.d("rr", String.valueOf(response.body()));
                Message message =new Message();
                message.obj = response.code();
                handler.sendMessage(message);
            }
            else{
                Message message =new Message();
                message.obj  = response.code() ;
                handler.sendMessage(message);
            }
        }

        @Override
        public void onFailure(Call<Registration> call, Throwable t) {
            t.printStackTrace();
            Message message =new Message();
            message.obj  = t.getMessage() ;
            handler.sendMessage(message);
        }
    });

}

}