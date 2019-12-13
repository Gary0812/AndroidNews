package com.example.news;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class ShowNewsActivity extends AppCompatActivity {
    private WebView show_news;
    private Intent data;
    private String share_url;
//    private Toolbar toolbar;
    private ImageView image_drawer_home;

    private String pageDescription="";
    private String video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        show_news =(WebView) findViewById(R.id.show_news);
        data = getIntent();
        share_url = data.getStringExtra("share_url");
        show_news.loadUrl(share_url);
        image_drawer_home =(ImageView) findViewById(R.id.image_drawer_home);
//        toolbar = (Toolbar) findViewById(R.id.contentToolbar);
//        toolbar.setTitle("融视频 - 文章内容");
        image_drawer_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowNewsActivity.this.finish();
            }
        });

        initView();
        // 启用支持JavaScript
        WebSettings webSettings = show_news.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        show_news.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript

        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用

        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小

        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。

        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放

        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存

        webSettings.setAllowFileAccess(true); //设置可以访问文件

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片

        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setSupportMultipleWindows(true);
        show_news.setWebChromeClient(new WebChromeClient() {

//            @Override
//
//            public void onReceivedTitle(WebView view, String title) {
//                tv_title.setText(title);
//            }
//            //加载进度显示
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress < 100) {
//                    contentt.setText(newProgress + "%");
//                } else {
//                    contentt.setText("100%");
//                }
//            }
        });

}

    private void initView() {
        show_news =(WebView) findViewById(R.id.show_news);
        data = getIntent();
        share_url = data.getStringExtra("share_url");
        show_news.loadUrl(share_url);
        show_news.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // 在开始加载网页时会回调
                super.onPageStarted(view, url, favicon);
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementById('footerG').style.display='none');");
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementById('relatedInfo').style.display='none');");
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementById('mainNav').style.display='none');");
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementById('breadCrumbs').style.display='none');");
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementById('footer').style.display='none');");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // 在结束加载网页时会回调

                // 获取页面内容
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementsByTagName('html')[0].innerHTML);");
                view.loadUrl("javascript:window.java_obj.getIVideoSrc("
                        + "document.getElementsByTagName('video').innerHTML");
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementById('footerG').style.display='none');");
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementById('relatedInfo').style.display='none');");
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementById('mainNav').style.display='none');");
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementById('breadCrumbs').style.display='none');");
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementById('footer').style.display='none');");

                // 获取解析<meta name="share-description" content="获取到的值">
                view.loadUrl("javascript:window.java_obj.showDescription("
                        + "document.querySelector('meta[name=\"share-description\"]').getAttribute('content')"
                        + ");");
                view.loadUrl("javascript:window.java_obj.showDescription("
                        + "document.querySelector('meta[name=\"share-description\"]').getAttribute('content')"
                        + ");");


//                String javascript =  "javascript:function hideOther() {"+
//                        "document.getElementsById('header')[0].remove();" +
//                        "document.getElementById('contentArea').style.display='none'"+
//                        "document.getElementsByClassName('contentArea')[0].remove();}";
//
//                //创建方法
//                view.loadUrl(javascript);
//
//                //加载方法
//                view.loadUrl("javascript:hideOther();");

                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // 加载错误的时候会回调，在其中可做错误处理，比如再请求加载一次，或者提示404的错误页面
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,WebResourceRequest request) {
                // 在每一次请求资源时，都会通过这个函数来回调
                return super.shouldInterceptRequest(view, request);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public final class InJavaScriptLocalObj
    {
        @JavascriptInterface
        public void showSource(String html) {
//            Document document = Jsoup.parse(html);
//            String elements = document.getElementsByTag("video").attr("src");
//            Log.d("LOGCAT","description:"+elements);
//            Intent intent = new Intent(ShowNewsActivity.this,vitamio.class);
//            intent.putExtra("url",elements);
//            startActivity(intent);

        }

        @JavascriptInterface
        public void showDescription(String str) {
            System.out.println("====>html=" + str);
        }

    }

}
