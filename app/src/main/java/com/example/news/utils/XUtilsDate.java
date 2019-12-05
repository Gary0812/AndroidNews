package com.example.news.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.example.news.GuideActivity;
import com.example.news.MainActivity;
import com.example.news.fragment.MineFragment;
import com.example.news.model.UserVo;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

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

}