package com.example.news.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.news.R;

import com.example.news.model.UserVo;
import com.example.news.utils.AESUtil;
import com.example.news.utils.XUtilsDate;
import com.example.news.utils.XmlParserUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MineFragment extends BaseFragment implements View.OnClickListener,XUtilsDate.LisData{
    public static Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            // 获取我们携带的数据
            switch ( msg.what)
            {
                case 0:
                    a=0;

                    break;
                case 1:
                    a=1;
                    break;
            }



        }
    };
    static int a;
    private View view;
    private Button myButton;
    private EditText register_phone;
    private TextView save_user;
    private EditText register_password;
    private EditText register_username;
    private EditText register_repassword;
    private EditText register_yzm;
    private Context context;
    private int countSeconds = 120;//倒计时秒数
    private Handler mCountHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (countSeconds > 0) {
                --countSeconds;
                myButton.setText("(" + countSeconds + ")后获取验证码");
                mCountHandler.sendEmptyMessageDelayed(0, 1000);
            } else {
                countSeconds = 120;
                myButton.setText("请重新获取验证码");
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (a == 0) {
            view = inflater.inflate(R.layout.activity_register, container, false);
            context = mActivity.getApplicationContext();
            initView();
        }else if (a==1)
        {
            view = inflater.inflate(R.layout.activity_login_register, container, false);
            context = mActivity.getApplicationContext();

        }


        return view;
    }

    @Override
    public View initView() {
        myButton = view.findViewById(R.id.btn_code);
        save_user = view.findViewById(R.id.save_user);
        register_password = view.findViewById(R.id.register_password);
        register_username = view.findViewById(R.id.register_username);
        register_repassword = view.findViewById(R.id.register_repassword);
        register_yzm = view.findViewById(R.id.register_yzm);
        myButton.setOnClickListener(this);
        save_user.setOnClickListener(this);
        register_phone = view.findViewById(R.id.register_phone);
        return view;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btn_code:
                String mobile = register_phone.getText().toString().trim();
                if (countSeconds == 120) {
                    getMobiile(mobile);
                } else {
                    new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("不能重复发送").setPositiveButton("确定", null).setCancelable(true).show();

                }
                break;

            case R.id.save_user:
                String moblie = register_phone.getText().toString().trim();
                String password = register_password.getText().toString().trim();
                String username = register_username.getText().toString().trim();
                String code = register_yzm.getText().toString().trim();
                //String IMEI=getIMEI(context);
                String IMEI= XmlParserUtils.getMacFromHardware();
                XUtilsDate xUtils = new XUtilsDate();
                xUtils.setmList(MineFragment.this);
                UserVo userVo=new UserVo();
                userVo.setCode(code);
                userVo.setIMEI(IMEI);
                userVo.setUsername(username);
                userVo.setMoblie(moblie);
                userVo.setPassword(AESUtil.encrypt(password));
                xUtils.onUserPost(view,userVo);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }


    }
    //获取验证码信息，判断是否有手机号码
 public void getMobiile(String mobile) {
        if ("".equals(mobile)) {
            Log.e("tag", "mobile=" + mobile);
            new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("手机号码不能为空").setPositiveButton("确定", null).setCancelable(true).show();
        } else if (isMobileNO(mobile) == false) {
            new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("请输入正确的手机号码").setPositiveButton("确定", null).setCancelable(true).show();
        } else {
            Toast.makeText(mActivity, "发送成功", Toast.LENGTH_SHORT).show();
            //获取验证码信息
            String data = "name=";
                 //  initListData1(data);
                  XUtilsDate xUtils = new XUtilsDate();
                  xUtils.setmList(MineFragment.this);
                  xUtils.onSmsPost(view, mobile);
        }
    }

    //使用正则表达式判断电话号码(港澳台）
    public static boolean isMobileNO(String tel) {
        Pattern p = Pattern.compile("^[1][3-8]\\d{9}$|^([6|9])\\d{7}$|^[0][9]\\d{8}$|^6\\d{5}$");
        Matcher m = p.matcher(tel);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    //获取验证码信息,进行计时操作
   public void startCountBack() {
        (getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myButton.setText(countSeconds + "");
                mCountHandler.sendEmptyMessage(0);
            }
        });
    }

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return null;
        }
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    @Override
    public void start() {
        startCountBack();
    }
}