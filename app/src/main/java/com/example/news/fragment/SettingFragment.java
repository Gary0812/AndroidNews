package com.example.news.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.news.MainActivity;
import com.example.news.R;
import com.example.news.utils.DataCleanManager;
import com.example.news.utils.PrefUtilS;

public class SettingFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private TextView cache;
    private Context context;
    private String caches;
    private TextView clear_cache;
    private TextView exit_login;
    private TextView app_shouye;
    private ImageView image_drawer_home;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_setting, container, false);
        image_drawer_home =view.findViewById(R.id.image_drawer_home);
        image_drawer_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        initView();

        cache.setText(caches);
        return view;
    }

    @Override
    public View initView() {
        context = mActivity.getApplicationContext();
        cache = view.findViewById(R.id.cache);
        clear_cache = view.findViewById(R.id.clear_cache);
        clear_cache.setOnClickListener(this);
        exit_login = view.findViewById(R.id.exit_login);
        exit_login.setOnClickListener(this);
        app_shouye = view.findViewById(R.id.app_shouye);
        app_shouye.setOnClickListener(this);
        try {
            caches = DataCleanManager.getTotalCacheSize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.clear_cache:
                AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
                builder.setTitle("提示");
                builder.setMessage("确认清空缓存吗");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataCleanManager.clearAllCache(context);
                        try {
                            caches = DataCleanManager.getTotalCacheSize(context);
                            cache.setText(caches);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
            case R.id.exit_login:
                LoginFragment loginFragment=new LoginFragment();
                PrefUtilS.clear(context);
                showFragment(SettingFragment.this,loginFragment);

                break;
            case R.id.app_shouye:
                Intent intent=new Intent();
                intent.setClass(mActivity,MainActivity.class);
                mActivity.startActivity(intent);
                break;
        }
    }

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