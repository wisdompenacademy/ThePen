package com.thepen.thepen.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.jarvanmo.exoplayerview.media.SimpleMediaSource;
import com.thepen.thepen.MainActivity;
import com.thepen.thepen.R;
import com.thepen.thepen.activities.Login;
import com.thepen.thepen.helper.UserInfo;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Utils {
    public static boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
    public static boolean isConnectedInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
    public static void showSettingsAlert(Context context){

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.network_connection));


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        Dialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

    }

    public static void sendEmailToSupport(Context context) {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        /* Fill it with Data */
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"sales@programmaticconnect.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        /* Send it off to the Activity-Chooser */
        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public static ArrayList<String> getTempData() {
        ArrayList<String> names=new ArrayList<>();
        names.add("Video Math");
        names.add("Video History");
        names.add("Video English");
        names.add("Video Science");
        names.add("Video Chemistry");
        names.add("Video Geography");
        names.add("Video Science");
        names.add("Video Math");
        names.add("Video Science");
        names.add("Video English");
        names.add("Video Math");
        names.add("Video Math");
        names.add("Video English");
        return names;
    }
    public static ArrayList<String> VideosLinks() {
        ArrayList<String> names=new ArrayList<>();
        names.add("http://video19.ifeng.com/video07/2013/11/11/281708-102-007-1138.mp4");
        names.add("https://media.w3.org/2010/05/sintel/trailer.mp4");
        names.add("https://videolinks.com/pub/media/videolinks/video/dji.osmo.action.mp4");
        return names;
    }

    public static void showConfirmDialog(Context context, String message) {
        new AlertDialog.Builder(context)
//                .setIcon(R.drawable.a)
//                .setTitle("Error..")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                })
                .show();
    }
    public static void showAlertDialog(Context context, String message) {
        new AlertDialog.Builder(context)
//                .setIcon(R.drawable.a)
//                .setTitle("Error..")
                .setMessage(message)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    public static void logout(Activity activity){
        UserInfo.getSharedInstance().setToken(null);
        activity.startActivity(new Intent(activity, Login.class));
        activity.finish();
    }
}
