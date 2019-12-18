package com.example.news.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.news.R;

public abstract class BaseFragment extends Fragment {

    public FragmentActivity mActivity;//当context去使用
    private View mRootView;//fragment的根布局
    private Context mContext;
    private boolean isFirstLoad = true; // 是否第一次加载

    private ProgressDialog mProgressDialog; // 加载进度对话框

    protected BaseFragment() {
    }

    //fragment 创建
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();//获取fragment所依赖的activity的对象
    }
//初始化布局
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = initView();
        return mRootView;
    }
//fragment所在的activity创建完成
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            initData();
            isFirstLoad = false;
        }
    }




    //必须有子类实现
   public abstract View initView();
    //初始化数据,子类可以不实现
    public  void  initData(){

    }
}
