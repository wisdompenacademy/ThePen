package com.thepen.thepen.apiservice;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thepen.thepen.entity.AssignTo;
import com.thepen.thepen.entity.ChangePassword;
import com.thepen.thepen.entity.Registration;
import com.thepen.thepen.entity.ServerResponse;
import com.thepen.thepen.entity.TeacherRegistration;
import com.thepen.thepen.entity.UpdateVideo;
import com.thepen.thepen.entity.User;
import com.thepen.thepen.entity.UserList;
import com.thepen.thepen.entity.UserLogin;
import com.thepen.thepen.entity.UserProfile;
import com.thepen.thepen.entity.Video;
import com.thepen.thepen.entity.VideoList;
import com.thepen.thepen.helper.Constants;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface ApiCall {

    @Headers({Constants.HEADER_CONTENT_TYPE})
    @POST("user/registration")
    Call<Registration> Registration(@Body Registration registration);

    @Headers({Constants.HEADER_CONTENT_TYPE})
    @POST("user/registration")
    Call<Registration> teacherRegistration(@Body TeacherRegistration registration);

    @Headers({Constants.HEADER_CONTENT_TYPE})
    @POST("student/uploadvideoList")
    Call<VideoList> getVideoList(@Header("Authorization")String token,
                                 @Body VideoList videoList);


    @Headers({Constants.HEADER_Authorization,Constants.HEADER_CONTENT_TYPELogin})
    @POST("oauth/token?")
    Call<UserLogin> UserLogin2(@Query("grant_type") String strPass,@Query("username") String username,@Query("password") String password);

    @Multipart
    @POST("teacher/uploadvideo")
    Call<ServerResponse> uploadFile(@Header("Authorization") String authHeader,@Part MultipartBody.Part file,
                                    @Part("file") RequestBody name);

    @Multipart
    @POST("teacher/uploadvideo")
    Call<ServerResponse> uploadMulFile(@Header("Authorization") String authHeader,@Part("image") MultipartBody.Part file1,
                                       @Part("videoFile") MultipartBody.Part file2);
    @Multipart
    @POST("teacher/uploadvideo")
    Call<Object> uploadkycdoc(@Header("Authorization")String token,
                              @Part MultipartBody.Part ViedoFile,
                              @Part MultipartBody.Part imageFile,
                              @Part("subject") String subject,@Part("std") String std,
                              @Part("Charge") boolean Charge,
                              @Part("publisher") String publisher,@Part("videoName") String videoName,
                              @Part("description") String description,
                              @Part("educationBoard") String educationBoard,
                              @Part("medium") String medium);

    @Multipart
    @POST("teacher/uploadvideo")
    Call<Object> uploadkycdoc12(@Header("Authorization")String token,
                                @Part MultipartBody.Part file,
                                @PartMap() Map<String, RequestBody> partMap);

    @GET("users/getUserProfle")
    Call<UserProfile> getUserProfle(@Header("Authorization")String token, @Query("userName") String userName);

    @POST("users/changePassword")
    Call<Object> changePassword(@Header("Authorization")String token, @Body ChangePassword chngPassword);

    @GET("superadmin/getUserList")
    Call<UserList> getUserList(@Header("Authorization")String token, @Query("roleId") String roleId);

    @GET("superadmin/getuploadvideoList")
    Call<VideoList> getuploadvideoList(@Header("Authorization")String token);

    @Headers({Constants.HEADER_CONTENT_TYPE})
    @PUT("superadmin/sendvideoforapproval")
    Call<VideoList> sendVideoForApproval(@Header("Authorization")String token,@Body AssignTo assignTo);

    @Headers({Constants.HEADER_CONTENT_TYPE})
    @PUT("superadmin/videopublishorunpublish")
    Call<VideoList> videoPublishOrUnpublish(@Header("Authorization")String token,@Body UpdateVideo obj);

    @Headers({Constants.HEADER_CONTENT_TYPE})
    @PUT("superadmin/changeroletoadmin")
    Call<UserList> changeRoleToAdmin(@Header("Authorization")String token,@Body User obj);

    @GET("admin/getsendtovideolist")
    Call<VideoList> getVideosToVerify(@Header("Authorization")String token,@Query("userName") String userName);

    @Headers({Constants.HEADER_CONTENT_TYPE})
    @PUT("admin/videoapproveorunapprove")
    Call<Object> adminApprove(@Header("Authorization")String token,@Body Video obj);

//    http://93.188.166.106:8080/users/updateUserProfile

    @Headers({Constants.HEADER_CONTENT_TYPE})
    @POST("users/updateUserProfile")
    Call<Registration> updateUserProfile(@Header("Authorization")String token,@Body Registration mRegistration);

}

