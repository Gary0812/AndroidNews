package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class VideoActivity extends AppCompatActivity {
    private WebView show_news;
    private Intent data;
    private String share_url;
    //    private Toolbar toolbar;
    private ImageView image_drawer_home;

    private String pageDescription = "";

    private Handler handler=new Handler(){
        //从子线程拿到的消息去主线程发消息
        @Override
        public void handleMessage(@NonNull Message msg) {


            String video = (String) msg.obj;

            Intent intent = new Intent(VideoActivity.this, vitamio.class);
            intent.putExtra("videourl", video);
            startActivity(intent);
            finish();
        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        data = getIntent();
        share_url = data.getStringExtra("share_url");

        image_drawer_home = (ImageView) findViewById(R.id.image_drawer_home);
//        toolbar = (Toolbar) findViewById(R.id.contentToolbar);
//        toolbar.setTitle("融视频 - 文章内容");
        image_drawer_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initDate(share_url);


    }
    public void initDate(final String url) {
        //只有主线程才能更新UI
        new Thread()
        {
            public void run()
            {
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).timeout(10000).get();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String video = doc.select("video").attr("src");
                //创建message对象
                Message message = new Message();

                //把数据放到message里面
                message.obj=video;
                //通过handler发送消息 handlemessage方法就会执行
                handler.sendMessage(message);

            }
        }.start();

    }


/*
    Intent intent = new Intent(VideoActivity.this, vitamio.class);
                intent.putExtra("videourl", video);
    startActivity(intent);*/


}