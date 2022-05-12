package com.thepen.thepen.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.thepen.thepen.R;
import com.thepen.thepen.adapters.VideoAdapter;

import java.util.ArrayList;
import java.util.HashSet;

public class SavedVideos extends AppCompatActivity {
    private RecyclerView videoList;
    private LinearLayoutManager mLinearLayoutManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_videos);
        String str_folder_name="ThePen";
        videoList = findViewById(R.id.videosList);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        videoList.setLayoutManager(mLinearLayoutManager);
        VideoAdapter videoAdapter=new VideoAdapter(SavedVideos.this,getAllMediaFromFolder(str_folder_name));
        videoList.setAdapter(videoAdapter);
    }
    public ArrayList<String> getAllMediaFromFolder(String folder_name) {
        String selection= MediaStore.Video.Media.DATA +" like?";
        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME};
        String[] selectionArgs=new String[]{"%"+folder_name+"%"};
        Cursor cursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,projection, selection, selectionArgs, MediaStore.Video.Media.DATE_TAKEN + " DESC");

        HashSet<String> videoItemHashSet = new HashSet<>();
        try {
            cursor.moveToFirst();
            do{
                videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))));
            }while(cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> downloadedList = new ArrayList<>(videoItemHashSet);
        for (int i=0;i<downloadedList.size();i++){
            Log.d("Item",downloadedList.get(i));
        }
        return downloadedList;
    }
}