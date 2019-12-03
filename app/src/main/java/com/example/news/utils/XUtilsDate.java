package com.example.news.utils;

import android.view.View;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

    public class XUtilsDate {
    private  String url="http://172.16.2.94:8080/wcmInf/";
    public   void onSmsPost(View v,String mobile) {
      String path=url+"sms";
        RequestParams params = new RequestParams(path);
        params.addParameter("mobile", mobile);
       // params.addParameter("password", "123");
        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(x.app(),result+"请两分钟内填写",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(),"发送失败",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

}
