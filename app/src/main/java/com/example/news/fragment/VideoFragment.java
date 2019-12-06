package com.example.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.news.R;
import com.example.news.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends BaseFragment {

    private View view;


    private List<String> titleList;
    private List<Fragment> fragmentList;
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    private VideoRRFragment videoRRFragment;
    private Video31Fragment video31Fragment;
    private TabLayout tabLayout;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_video, container, false);

        initView();
        fragmentChange();
        return view;
    }

    @Override
    public View initView() {
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        return view;
    }



    private void fragmentChange() {
        fragmentList = new ArrayList<>();
        video31Fragment=new Video31Fragment();
        videoRRFragment=new VideoRRFragment();

        fragmentList.add(videoRRFragment);
        fragmentList.add(video31Fragment);


        titleList = new ArrayList<>();
        titleList.add("融视频");
        titleList.add("31条");


        fragmentAdapter = new FragmentAdapter(mActivity.getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(fragmentAdapter);

        //将tabLayout与viewPager连起来
        tabLayout.setupWithViewPager(viewPager);
    }

}