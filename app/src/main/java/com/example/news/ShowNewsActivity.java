package com.example.news;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.news.model.NewsInfo;
import com.example.news.utils.MyDatabaseHelper;



public class ShowNewsActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView show_news;
    private Intent data;
    private String share_url;
    private String share_title;
    private String share_time;
    private String share_docid;
    private ImageView collect_news;
//    private Toolbar toolbar;
    private ImageView image_drawer_home;

    private String pageDescription="";
    private String video;
    private Context context;

    private ProgressDialog progressDialog;
    private MyDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);




        initView();
}

    private void initView() {
        show_news =(WebView) findViewById(R.id.show_news);

        helper = new MyDatabaseHelper(this, "TaiDB.db", null, 1);
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
        data = getIntent();
        share_url = data.getStringExtra("share_url");
        share_docid = data.getStringExtra("share_docid");
        share_title = data.getStringExtra("share_title");
        share_time = data.getStringExtra("share_time");
        show_news.loadUrl(share_url);
        image_drawer_home =(ImageView) findViewById(R.id.image_drawer_home);
        image_drawer_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowNewsActivity.this.finish();
            }
        });

        collect_news =(ImageView) findViewById(R.id.collect_news);
        collect_news.setOnClickListener(this);
        SharedPreferences sp = this.getSharedPreferences("show_news", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(sp.getString(share_url, "0").equals(share_url)) {
            collect_news.setImageResource(R.mipmap.favorite_selected);
        }else {
            collect_news.setImageResource(R.mipmap.favorite);
        }
        show_news.loadUrl(share_url);
        show_news.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // 在开始加载网页时会回调


                super.onPageStarted(view, url, favicon);
                showProgress("页面加载中");//开始加载动画
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // 在结束加载网页时会回调
                super.onPageFinished(view, url);

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

                removeProgress();//当加载结束时移除动画

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



    @Override
    public void onClick(View v) {
        SharedPreferences sp = this.getSharedPreferences("show_news", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

      switch(v.getId()) {
          case R.id.collect_news:
               if (sp.getString(share_url,"0").equals(share_url)){
                   //根据id移除收藏夹对应文章
                   SQLiteDatabase db = helper.getReadableDatabase();
                   db.execSQL("delete from Collection_News where news_docid=?",
                           new String[]{share_docid});
                   db.close();
//                   mNewsInfoDao.deleteDataById(share_docid);
                  editor.putString(share_url,"0");
                  editor.commit();
                   collect_news.setImageResource(R.mipmap.favorite);
                  Toast.makeText(this,"取消收藏",Toast.LENGTH_SHORT).show();
                  return;
             }

        editor.putString(share_url, share_url);
//              List<NewsInfo> list = new ArrayList<>();
//              NewsInfo newsInfo;
//              newsInfo = new NewsInfo();
//              newsInfo.setId(share_docid);
//              newsInfo.setTitle(share_title);
//              newsInfo.setTime(share_time);
//              newsInfo.setType(share_url);
//              list.add(newsInfo);
//        mNewsInfoDao.addData(list);
              SQLiteDatabase db = helper.getWritableDatabase();

              ContentValues values = new ContentValues();
              //组装数据
              values.put("news_url", share_url);
              values.put("news_title", share_title);
              values.put("news_date", share_time);
              values.put("news_docid", share_docid);
              db.insert("Collection_News", null, values);
              db.close();
        editor.commit();
        collect_news.setImageResource(R.mipmap.favorite_selected);
           Toast.makeText(ShowNewsActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
               break;
        }

   }

    //-----显示ProgressDialog
    public void showProgress(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(ShowNewsActivity.this, ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);//设置点击不消失
        }
        if (progressDialog.isShowing()) {
            progressDialog.setMessage(message);
        } else {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }
    //------取消ProgressDialog
    public void removeProgress(){
        if (progressDialog==null){
            return;
        }
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }

    }

}
