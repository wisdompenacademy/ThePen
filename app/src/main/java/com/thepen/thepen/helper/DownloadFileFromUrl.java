package com.thepen.thepen.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.thepen.thepen.activities.VideoActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileFromUrl extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog pDialog;
    private String file_name;

    public DownloadFileFromUrl(VideoActivity videoActivity,String file_name) {
        this.context=videoActivity;
        this.file_name = file_name.replace("/","");
    }

    @Override
    protected String doInBackground(String... f_url) {
        Log.d("Download","doInBackground");
        int count;
        try {
            String root = Environment.getExternalStorageDirectory().toString();

            System.out.println("Downloading");
            URL url = new URL(f_url[0]);

            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file
            File folder = new File("/sdcard", "ThePen");
            folder.mkdirs();
            OutputStream output = new FileOutputStream(root+"/ThePen/"+file_name);
            byte data[] = new byte[1024];

            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;

                // writing data to file
                output.write(data, 0, count);

            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    /**
     * Before starting background thread
     * */
    @Override
    protected void onPreExecute() {
        Log.d("Download","onPreExecute");
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading... Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * After completing background task
     * **/
    @Override
    protected void onPostExecute(String file_url) {
        Log.d("Download","onPostExecute");
        pDialog.dismiss();
    }
}
