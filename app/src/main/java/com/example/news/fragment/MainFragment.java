package com.example.news.fragment;

import android.os.Bundle;
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
import androidx.viewpager.widget.ViewPager;

import com.example.news.FragmentAdapter;
import com.example.news.MainActivity;
import com.example.news.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/*头部标题切换控制*/
public class MainFragment extends Fragment {

    private View view;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<String> titleList;
    private List<Fragment> fragmentList;

    private FragmentAdapter fragmentAdapter;

    private NewsFragment news_fragment;
    private VideoFragment video_fragment;
    private NavigationView navigationView;
    private ImageView app_nav_header_bg;
    private TextView app_personal;
    private TextView textView;

    DrawerLayout mDrawerLayout;

    Toolbar mToolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        fragmentChange();
        return view;
    }

    private void initView() {
        mToolbar=view.findViewById(R.id.toolbar);
        navigationView = view.findViewById(R.id.main_nav);
        /*navigationView.inflateHeaderView(R.layout.nav_header);
        navigationView.inflateMenu(R.menu.nav_menu);*/
        navigationView.setItemIconTintList(null);//菜单项颜色图标
        View headerView = navigationView.getHeaderView(0);
        app_nav_header_bg = headerView.findViewById(R.id.app_nav_header_bg);//获取头文件文本id
        textView = headerView.findViewById(R.id.textView3);
        app_personal = headerView.findViewById(R.id.app_personal);//获取头文件背景id
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

    }

    private void fragmentChange() {
        fragmentList = new ArrayList<>();

        news_fragment = new NewsFragment();
        video_fragment = new VideoFragment();


        fragmentList.add(news_fragment);
        fragmentList.add(video_fragment);

        titleList = new ArrayList<>();
        titleList.add("推荐");
        titleList.add("视频");


        fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(fragmentAdapter);

        //将tabLayout与viewPager连起来
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
    }

    private void initEvent() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                textView.setText("您选择的是: "+item.getTitle().toString());
                Toast.makeText(getActivity(), "选择的是："+item.getGroupId(), Toast.LENGTH_SHORT).show();
                //获取menu菜单事件
                item.getGroupId();
                item.getTitle().toString();
                return true;
            }
        });
    }

}