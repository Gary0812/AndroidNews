package com.example.news.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.news.CollectionActivity;
import com.example.news.R;

import com.example.news.ShowNewsActivity;
import com.example.news.model.UserVo;
import com.example.news.utils.AESUtil;
import com.example.news.utils.PrefUtilS;
import com.example.news.utils.ThemeUtil;
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
    public  Handler handler=new Handler(){
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
    private UserVo userVo;
     int a;
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
    private Button btn_register;
    private TextView mine_user_name;
    private ImageView navigation_setting;
    private TextView collection;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ThemeUtil.setBaseTheme(getContext());
        boolean show= PrefUtilS.getUserBoolean(mActivity,"is_user",false);
        view = inflater.inflate(R.layout.activity_mine, container, false);
        String username=PrefUtilS.getUser(mActivity,"username",null);
        mine_user_name = view.findViewById(R.id.mine_user_name);
        mine_user_name.setText(username);
  initView();


       /* if (show) {
            init();
            if (a == 0) {
                LoginFragment loginFragment=new LoginFragment();
                showFragment(MineFragment.this,loginFragment );
               *//* view = inflater.inflate(R.layout.activity_mine, container, false);
                context = mActivity.getApplicationContext();
                initView();*//*
            }else if (a==1) {
                RegistFragment registFragment=new RegistFragment();
                showFragment(MineFragment.this,registFragment);
              *//*  view = inflater.inflate(R.layout.activity_login, container, false);
                context = mActivity.getApplicationContext();*//*
            }

        }
     else {
            RegistFragment registFragment=new RegistFragment();
            showFragment(MineFragment.this,registFragment);
         *//*   view = inflater.inflate(R.layout.activity_login, container, false);
            btn_register = view.findViewById(R.id.btn_register);
            btn_register.setOnClickListener(this);
            context = mActivity.getApplicationContext();*//*
        }

    *//*    if (a == 0) {
            view = inflater.inflate(R.layout.activity_register, container, false);
            context = mActivity.getApplicationContext();
            initView();
        }else if (a==1)
        {
            view = inflater.inflate(R.layout.activity_login, container, false);
            context = mActivity.getApplicationContext();

        }*/


        return view;
    }
    /* public void init()
    {
       String username=PrefUtilS.getUser(mActivity,"username",null);
        String password=PrefUtilS.getUser(mActivity,"password",null);
        String IMEI=PrefUtilS.getUser(mActivity,"IMEI",null);
        if (IMEI != null) {
            userVo = new UserVo();
            userVo.setUsername(username);
            userVo.setPassword(password);
            userVo.setIMEI(IMEI);
        }else
        {
            userVo=null;
        }
        XUtilsDate xUtils = new XUtilsDate();
        xUtils.queryLogin(userVo);
    }*/



    @Override
    public View initView() {
        collection = view.findViewById(R.id.collection);
        navigation_setting = view.findViewById(R.id.navigation_setting);
       navigation_setting.setOnClickListener(this);
       collection.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.collection:
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.navigation_setting:
                SettingFragment settingFragment=new SettingFragment();
                showFragment(MineFragment.this, settingFragment);
                break;
        }




       /* int id = v.getId();
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
            case  R.id.btn_register:
                RegistFragment registFragment=new RegistFragment();
             *//*   Bundle bundle = new Bundle();
// 传递实体类，需要实体类实现Serializable接口
                bundle.putSerializable("key_entity", value_Entity);
// 传递字符串
                bundle.putString("key_str",value_str);
// 传递int类型数
                bundle.putInt("key_int",value_int);
//设置数据
                fragment2.setArgument(bundle);
//调用上面的方法由 fragment1 跳转到 fragment2*//*
                showFragment(MineFragment.this, registFragment);


                break;
          *//*  case R.id.btn_register:

                break;*//*
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
*/

    }


    /**
     * 公共方法： 从碎片fragment1跳转到碎片fragment2
     *
     * @param fragment1
     *            当前fragment
     * @param fragment2
     *            跳转后的fragment
     */
    private void showFragment(Fragment fragment1, Fragment fragment2) {
        // 获取 FragmentTransaction  对象
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        //如果fragment2没有被添加过，就添加它替换当前的fragment1
        if (!fragment2.isAdded()) {
            transaction.replace(R.id.lr,fragment2)
                    //加入返回栈，这样你点击返回键的时候就会回退到fragment1了
                    .addToBackStack(null)
                    // 提交事务
                    .commitAllowingStateLoss();

        } else { //如果已经添加过了的话就隐藏fragment1，显示fragment2
            transaction
                    // 隐藏fragment1，即当前碎片
                    .hide(fragment1)
                    // 显示已经添加过的碎片，即fragment2
                    .show(fragment2)
                    // 加入返回栈
                    .addToBackStack(null)
                    // 提交事务
                    .commitAllowingStateLoss();
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

/*    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return null;
        }
        String imei = telephonyManager.getDeviceId();
        return imei;
    }*/

    @Override
    public void start() {
        startCountBack();
    }
}