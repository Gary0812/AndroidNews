package com.example.news.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;


import com.example.news.MainActivity;
import com.example.news.fragment.MineFragment;
import com.example.news.fragment.TaiwanFragment;
import com.example.news.model.NewsVo;
import com.example.news.model.UserVo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XUtilsDate {

        final private int SUCESS=1;
        final private int NOT=0;
        private Context mContext;
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


        public   void onUserPost(View v, final UserVo userVo) {
            String path=url+"regtistUser";
            RequestParams params = new RequestParams(path);
            params.addParameter("mobile", userVo.getMoblie());
            params.addParameter("IMEI", userVo.getIMEI());
            params.addParameter("username", userVo.getUsername());
            params.addParameter("password", userVo.getPassword());
            params.addParameter("code", userVo.getCode());
            // params.addParameter("password", "123");
            x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(x.app(),result,Toast.LENGTH_LONG).show();
                    if (result.equals("注册成功\r\n")) {
                        PrefUtilS.putUser(x.app(),"username",userVo.getUsername());
                        PrefUtilS.putUser(x.app(),"password",userVo.getPassword());
                        PrefUtilS.putUser(x.app(),"IMEI",userVo.getIMEI());
                        intent();
                    }
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
        public  void intent()
        {
            Intent intent = new Intent();
            intent.setClass(x.app(), MainActivity.class);
            x.app().startActivity(intent);//载入主窗口
        }



        public void queryLogin( final UserVo userVo) {

            String path=url+"queryLogin";
            RequestParams params = new RequestParams(path);
            params.addParameter("IMEI", userVo.getIMEI());
            params.addParameter("username", userVo.getUsername());
            params.addParameter("password", userVo.getPassword());

            // params.addParameter("password", "123");
            x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Handler handler=new Handler();
                    Message msg=Message.obtain();
                    if (result.equals("已存在\r\n")) {
                        msg.what= SUCESS;
                    }else
                    {
                        msg.what=NOT;
                    }

                    //发送一条消息
                    MineFragment.handler.sendMessage(msg);

                    }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }
                @Override
                public void onCancelled(CancelledException cex) {
                }
                @Override
                public void onFinished() {
                }
            });

        }


        public void querynewsItem(String channelId ) {

            String path=url+"querynewsItem";
            RequestParams params = new RequestParams(path);
            params.addParameter("channelId", channelId);


            // params.addParameter("password", "123");
            x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {


/*                    JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();
//需要遍历的数组
                    JsonArray jsonArray = jsonObject.getAsJsonArray("DOCOUTUPID");



                    System.out.println("aaaaa"+jsonArray);*/



                    Handler handler=new Handler();
                    Message msg=Message.obtain();


                    Bundle bundle=new Bundle();
                    bundle.putString("result",result);
                    /*  bundle.putSerializable("list", (Serializable) list);*/
                    msg.setData(bundle);
                    //发送一条消息
                    TaiwanFragment taiwanFragment=new TaiwanFragment();
              //     taiwanFragment.handler.sendMessage(msg);






                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }
                @Override
                public void onCancelled(CancelledException cex) {
                }
                @Override
                public void onFinished() {
                }
            });

        }

    public static void jsonTree(JsonElement e)
    {
        if (e.isJsonNull())
        {
            System.out.println(e.toString());
            return;
        }

        if (e.isJsonPrimitive())
        {
            System.out.println(e.toString());
            return;
        }

        if (e.isJsonArray())
        {
            JsonArray ja = e.getAsJsonArray();
            if (null != ja)
            {
                for (JsonElement ae : ja)
                {
                    jsonTree(ae);
                }
            }
            return;
        }

        if (e.isJsonObject())
        {
            Set<Map.Entry<String, JsonElement>> es = e.getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> en : es)
            {
                jsonTree(en.getValue());
            }
        }
    }


    }
