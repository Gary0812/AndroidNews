package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class ShowNewsActivity extends AppCompatActivity {
    private WebView show_news;
    private Intent data;
    private String share_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        initView();

}

    private void initView() {
        show_news =(WebView) findViewById(R.id.show_news);
        data = getIntent();
        share_url = data.getStringExtra("share_url");
        show_news.loadUrl(share_url);
        show_news.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
    }
