package com.example.news.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.news.R;
import com.example.news.model.UserVo;
import com.example.news.utils.AESUtil;
import com.example.news.utils.XUtilsDate;
import com.example.news.utils.XmlParserUtils;


public class LoginFragment extends BaseFragment implements View.OnClickListener,XUtilsDate.LisData {
    private View view;
    private Button myButton;

    private Button check_user;
    private EditText login_mobile;
    private EditText login_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_login, container, false);
        initView();
        return view;
    }

    @Override
    public View initView() {

        login_mobile = view.findViewById(R.id.login_mobile);
        login_password = view.findViewById(R.id.login_password);
        myButton = view.findViewById(R.id.btn_register);
        check_user = view.findViewById(R.id.check_user);
        myButton.setOnClickListener(this);
        check_user.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.check_user:

                String mobile = login_mobile.getText().toString().trim();
                String password = login_password.getText().toString().trim();
                XUtilsDate xUtils = new XUtilsDate();
                String IMEI= XmlParserUtils.getMacFromHardware();
                UserVo userVo = new UserVo();
                userVo.setIMEI(IMEI);
                userVo.setMoblie(mobile);
                userVo.setPassword(AESUtil.encrypt(password));
                xUtils.login(userVo);
                break;
            case R.id.btn_register:
                RegistFragment registFragment = new RegistFragment();
                showFragment(LoginFragment.this, registFragment);
                break;
          /*  case R.id.btn_register:

                break;*/
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
    public void showFragment(Fragment fragment1, Fragment fragment2) {
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

    @Override
    public void start() {

    }
}