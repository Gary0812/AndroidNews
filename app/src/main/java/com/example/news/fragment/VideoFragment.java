package com.example.news.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.news.adapter.FragmentAdapter;
import com.example.news.R;
import com.example.news.utils.PrefUtilS;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_video, container, false);

        initView();
        fragmentChange();
        return view;
    }

    @Override
    public View initView() {

        return view;
    }



    private void fragmentChange() {
        tabLayout = view.findViewById(R.id.tab_layout1);
        viewPager = view.findViewById(R.id.view_pager1);
        fragmentList = new ArrayList<>();
        videoRRFragment=new VideoRRFragment();
        video31Fragment=new Video31Fragment();


        fragmentList.add(videoRRFragment);
        fragmentList.add(video31Fragment);


        titleList = new ArrayList<>();
        titleList.add("融视频");
        titleList.add("31条");


        fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragmentList, titleList);
        viewPager.setAdapter(fragmentAdapter);

        //将tabLayout与viewPager连起来
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

}