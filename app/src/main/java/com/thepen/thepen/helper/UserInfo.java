package com.thepen.thepen.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class UserInfo {
    private static UserInfo mInstance;
    private static SharedPreferences mPref;
    private SharedPreferences.Editor editor;

    private static final String KEY_PREF_NAME = "ER_USER_INFO";
    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String ROLE = "ROLE";
    private static final String USER_ID = "USER_ID";
    private static final String KEY_TIMER_FOR_BANNER_AD_CLOSE = "TIMER_FOR_BANNER_AD_CLOSE";
    private static final String UPDATE_WEATHER = "UPDATE_FIRST_TIME_WEATHER";
    private static final String TIME_FOR_REQUEST_AD = "TIME_FOR_REQUEST_AD";

    private UserInfo(Context context) {
        mPref = context.getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
        editor = mPref.edit();
    }

    public static synchronized void initialize(Context context) {
        if (mInstance == null)
            mInstance = new UserInfo(context);
    }

    public static synchronized UserInfo getSharedInstance() {
        if (mInstance == null) {
            throw new IllegalStateException(UserInfo.class.getSimpleName() + " is not initialized, call initialize(..) method first.");
        }
        return mInstance;
    }

    //get and set token
    public void setToken(String token){
        mPref.edit().putString(KEY_TOKEN,token).commit();
    }

    public String getToken(){
        return mPref.getString(KEY_TOKEN,null);
    }

    //get and set role
    public void setRole(String token){
        mPref.edit().putString(ROLE,token).commit();
    }

    public String getRole(){
        return mPref.getString(ROLE,"null");
    }

    //get and set role
    public void setUserId(String token){
        mPref.edit().putString(USER_ID,token).commit();
    }

    public String getUserId(){
        return mPref.getString(USER_ID,null);
    }
}
