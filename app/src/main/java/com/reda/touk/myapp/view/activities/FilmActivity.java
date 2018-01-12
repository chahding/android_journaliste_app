package com.reda.touk.myapp.view.activities;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.reda.touk.myapp.helpers.FirebaseVideoHelper;
import com.reda.touk.myapp.models.Video;
import com.reda.touk.myapp.view.adapters.VideoAdapter;


import java.util.ArrayList;
import java.util.List;

import com.reda.touk.myapp.helpers.OnNewVideoListener;

public class FilmActivity extends AppCompatActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;
    FloatingActionButton buttonNewVideo;
    FirebaseVideoHelper mFirebaseHelper;
    ListView mListView;
    VideoAdapter mAdapter;
    List<Video> mVideos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.reda.touk.myapp.R.layout.activity_film);
        buttonNewVideo = (FloatingActionButton) findViewById(com.reda.touk.myapp.R.id.floatingActionButton);
        mListView = (ListView) findViewById(com.reda.touk.myapp.R.id.listView);

        buttonNewVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakeVideoIntent();
            }
        });
        mFirebaseHelper = new FirebaseVideoHelper(this, FilmActivity.this);
        mFirebaseHelper.getAllVideos(new OnNewVideoListener() {
            @Override
            public void callback(List<Video> videos) {
                mVideos = videos;
                mAdapter = new VideoAdapter(FilmActivity.this, new ArrayList<>(mVideos));
                mListView.setAdapter(mAdapter);
            }
        });
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            mFirebaseHelper.sendFile(intent.getData());
        }
    }
}
