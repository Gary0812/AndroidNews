package com.example.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import io.vov.vitamio.MediaPlayer.OnPreparedListener;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class vitamio extends AppCompatActivity {

    private Intent data;
    private ImageView collect_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitamio);
        collect_news =(ImageView) findViewById(R.id.collect_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collect_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_news.setImageResource(R.mipmap.favorite_selected);
                Toast.makeText(vitamio.this, "收藏成功！", Toast.LENGTH_SHORT).show();
            }
        });
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        final VideoView  vov = findViewById(R.id.vov);
        data = getIntent();
        String svideo = data.getStringExtra("videourl");
//        String share_docid = data.getStringExtra("share_docid");
//        String share_time = data.getStringExtra("share_time");
//        String share_title = data.getStringExtra("share_title");
//        System.out.println("666666"+share_docid+share_time+share_title);



        Vitamio.isInitialized(getApplicationContext());
        vov.setVideoPath(svideo);
        vov.setMediaController(new MediaController(this));
        vov.requestFocus();
        vov.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
                vov.start();

            }
        });
    }
    @Override
    public void onBackPressed() {
        vitamio.this.finish();

    }
}
