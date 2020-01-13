package com.example.news.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.R;
import com.example.news.model.UserVo;
import com.example.news.utils.AESUtil;
import com.example.news.utils.XUtilsDate;
import com.example.news.utils.XmlParserUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistFragment extends BaseFragment implements View.OnClickListener, XUtilsDate.LisData{
    private View view;
    private TextView save_user;
    private EditText register_password;
    private EditText register_username;
    private EditText register_repassword;
    private EditText register_yzm;
    private EditText register_phone;
    private Button myButton;
    private ImageView  image_drawer_home;
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
    private CheckBox checkbox_tiaokuan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_register, container, false);

initView();
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
        checkbox_tiaokuan = view.findViewById(R.id.checkbox_tiaokuan);
        image_drawer_home = (ImageView) view.findViewById(R.id.image_drawer_home);
        image_drawer_home.setOnClickListener(this);

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
                String repassword  = register_repassword.getText().toString().trim();
                String code = register_yzm.getText().toString().trim();

                if (!checkbox_tiaokuan.isChecked()) {
                    Toast.makeText(mActivity, "请同意融视频条款", Toast.LENGTH_SHORT).show();
                }
             else   if (!repassword.equals(password)) {
                    Toast.makeText(mActivity, "密码输入不一致", Toast.LENGTH_SHORT).show();
                }
               else if (!moblie.equals("")&&!password.equals("")&&!username.equals("")&&!code.equals("")) {
                    //String IMEI=getIMEI(context);
                    String IMEI = XmlParserUtils.getMacFromHardware();
                    XUtilsDate xUtils = new XUtilsDate();
                    xUtils.setmList(RegistFragment.this);
                    UserVo userVo = new UserVo();
                    userVo.setCode(code);
                    userVo.setIMEI(IMEI);
                    userVo.setUsername(username);
                    userVo.setMoblie(moblie);
                    userVo.setPassword(AESUtil.encrypt(password));
                    xUtils.onUserPost(view, userVo);
                }else
                {
                    Toast.makeText(mActivity, "请填写注册信息", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.image_drawer_home:
                getFragmentManager().popBackStack();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }


    }


    /**
     * 公共方法： 从碎片fragment1跳转到碎片fragment2
     *
     * @param fragment1 当前fragment
     * @param fragment2 跳转后的fragment
     */
    private void showFragment(Fragment fragment1, Fragment fragment2) {
        // 获取 FragmentTransaction  对象
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        //如果fragment2没有被添加过，就添加它替换当前的fragment1
        if (!fragment2.isAdded()) {
            transaction.replace(R.id.lr, fragment2)
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
            xUtils.setmList(RegistFragment.this);
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