package com.example.news;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.news.utils.MyDatabaseHelper;
import com.example.news.utils.ThemeUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.vov.vitamio.MediaPlayer.OnPreparedListener;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class vitamio extends BaseActivity {

    private Intent data;
    private ImageView collect_news;
    private String svideo;
    private String authors;
    private String share_title;

    private TextView content_title;
    private TextView content_author;
    private ImageView image_drawer_home;
    private  String share_url;

    private String share_time;
    private String share_docid;
    private SharedPreferences sp;
    private MyDatabaseHelper helper;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.setBaseTheme(this);
        setContentView(R.layout.activity_vitamio);
        initView();
    }


    private void initView() {
        helper = new MyDatabaseHelper(this, "TaiDB.db", null, 1);
        collect_news = (ImageView) findViewById(R.id.collect_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        data = getIntent();
        svideo = data.getStringExtra("videourl");
        authors = data.getStringExtra("authors");
        share_title = data.getStringExtra("titles");
        share_docid=data.getStringExtra("share_docid");
        share_url=data.getStringExtra("url");
        share_time=data.getStringExtra("publishdates");
        content_title = (TextView) findViewById(R.id.content_title);
        content_author = (TextView) findViewById(R.id.content_author);
        content_author.setText(authors);
        content_title.setText(share_title);
        sp = this.getSharedPreferences("show_news", Context.MODE_PRIVATE);
        editor = sp.edit();
        if(sp.getString(svideo, "0").equals( svideo)) {
            collect_news.setImageResource(R.mipmap.favorite_selected);
        }else {
            collect_news.setImageResource(R.mipmap.favorite);
        }

        collect_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp.getString(svideo,"0").equals(svideo)){
                    //根据url移除收藏夹对应文章
                    SQLiteDatabase db = helper.getReadableDatabase();
                    db.execSQL("delete from Collection_News where news_url=?",
                            new String[]{svideo});
                    db.close();
                    editor.putString(svideo,"0");
                    editor.commit();
                    collect_news.setImageResource(R.mipmap.favorite);
                    Toast.makeText(vitamio.this,"取消收藏",Toast.LENGTH_SHORT).show();
                    return;
                }

                editor.putString(svideo, svideo);

                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues values = new ContentValues();
                //组装数据
                values.put("news_url", svideo);
                values.put("news_type", "vedio");
                values.put("news_title", share_title);
                values.put("news_date", share_time);
                values.put("news_docid", share_docid);
                values.put("news_authors", authors);
                db.insert("Collection_News", null, values);
                db.close();
                editor.commit();
                collect_news.setImageResource(R.mipmap.favorite_selected);
                Toast.makeText(vitamio.this, "收藏成功！", Toast.LENGTH_SHORT).show();
            }
        });
        image_drawer_home = (ImageView) findViewById(R.id.image_drawer_home);
        image_drawer_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vitamio.this.finish();
            }
        });
        final VideoView vov = (VideoView) findViewById(R.id.vov);


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
