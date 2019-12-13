package com.example.news.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.news.R;
import com.example.news.model.UserVo;
import com.example.news.utils.PrefUtilS;
import com.example.news.utils.XUtilsDate;


public class SignInManager extends BaseFragment implements View.OnClickListener, XUtilsDate.LisData{

    private UserVo userVo;
    int a;
    private View view;
    private Button myButton;
    private EditText register_phone;
    private TextView save_user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean show= PrefUtilS.getUserBoolean(mActivity,"is_user",false);
        view = inflater.inflate(R.layout.activity_login_register, container, false);

        if (show) {
            MineFragment mineFragment=new MineFragment();
            showFragment(SignInManager.this,mineFragment );


        }
        else {
            LoginFragment loginFragment=new LoginFragment();
            showFragment(SignInManager.this,loginFragment);

        }
        return  view;
    }

    public void init()
    {
        String mobile=PrefUtilS.getUser(mActivity,"mobile",null);
        String password=PrefUtilS.getUser(mActivity,"password",null);
        String IMEI=PrefUtilS.getUser(mActivity,"IMEI",null);
        if (IMEI != null) {
            userVo = new UserVo();
            userVo.setMoblie(mobile);
            userVo.setPassword(password);
            userVo.setIMEI(IMEI);
        }else
        {
            userVo=null;
        }
        XUtilsDate xUtils = new XUtilsDate();
        xUtils.queryLogin(userVo);
    }



    @Override
    public View initView() {

        return view;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void start() {

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
                  //  .addToBackStack(null)
                    // 提交事务
                    .commitAllowingStateLoss();

        } else { //如果已经添加过了的话就隐藏fragment1，显示fragment2
            transaction
                    // 隐藏fragment1，即当前碎片
                    .hide(fragment1)
                    // 显示已经添加过的碎片，即fragment2
                    .show(fragment2)
                    // 加入返回栈
                  //  .addToBackStack(null)
                    // 提交事务
                    .commitAllowingStateLoss();
        }
    }
}
