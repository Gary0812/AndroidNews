package com.example.news.fragment;

import android.content.Intent;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.news.CollectionActivity;
import com.example.news.MainActivity;
import com.example.news.adapter.FragmentAdapter;
import com.example.news.R;
import com.example.news.utils.PrefUtilS;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*头部标题切换控制*/
public class MainFragment extends BaseFragment {

    private View view;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<String> titleList;
    private List<Fragment> fragmentList;

    private FragmentAdapter fragmentAdapter;
    private TaiwanFragment taiwanFragment;
    private NewsFragment news_fragment;
    private VideoFragment video_fragment;
    private NavigationView navigationView;
    private ImageView app_nav_header_bg;
    private TextView app_personal;
    private TextView textView;
    boolean isChanged = false;
    DrawerLayout mDrawerLayout;
private  TextView app_collect_info;
    Toolbar mToolbar;
    private ImageView imageView;
    private  LocalityFragment localityFragment;
     private  ThirtyOneFragment thirtyOneFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, container, false);
            initView();
            fragmentChange();
            imageClick();
            initEvent();
        }
        return view;
    }




    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            //相当于Fragment的onPause
            System.out.println("界面不可见");
        } else {
            // 相当于Fragment的onResume
            fragmentChange();
            System.out.println("界面可见");

        }
    }
    private void fragmentChange() {
        fragmentList = new ArrayList<>();
        localityFragment=new LocalityFragment();
        news_fragment = new NewsFragment();
        taiwanFragment = new TaiwanFragment();
        thirtyOneFragment=new ThirtyOneFragment();
        fragmentList.add(news_fragment);
        fragmentList.add(taiwanFragment);
        fragmentList.add(localityFragment);
        fragmentList.add(thirtyOneFragment);
        //fragmentList.add(video_fragment);

        titleList = new ArrayList<>();
        titleList.add("推荐");
        titleList.add("台湾");
        titleList.add("地方");
        titleList.add("31条");

        fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragmentList, titleList);
       // fragmentAdapter = new FragmentAdapter(mActivity.getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(fragmentAdapter);

        //将tabLayout与viewPager连起来
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View initView() {


        navigationView = view.findViewById(R.id.main_nav);
        imageView = view.findViewById(R.id.image_drawer_home);
        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView.setItemIconTintList(null);//菜单项颜色图标
        View headerView = navigationView.getHeaderView(0);
        app_nav_header_bg = headerView.findViewById(R.id.app_nav_header_bg);//获取头文件文本id
        textView = headerView.findViewById(R.id.textView3);
        boolean show= PrefUtilS.getUserBoolean(mActivity,"is_user",false);
        app_personal = headerView.findViewById(R.id.app_personal);//获取头文件背景id
        if (show) {
            String username=PrefUtilS.getUser(mActivity,"username",null);
            app_personal.setText(username);
        }
        app_collect_info=headerView.findViewById(R.id.app_collect_info);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        app_collect_info.setText("今天是："+simpleDateFormat.format(date));
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        return headerView;

    }


    private void initEvent() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home :
                        getActivity().finish();
                        Intent mainIntent = new Intent(getContext(), MainActivity.class);
                        mainIntent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(mainIntent);
                        break;
                    case R.id.nav_sousiba :
                        Intent collectionIntent = new Intent(getContext(), CollectionActivity.class);
                        startActivity(collectionIntent);
                        break;
                    case R.id.nav_wait :
                        Toast.makeText(mActivity, "敬请期待...", Toast.LENGTH_SHORT).show();
                        break;
                }
           //获取menu菜单事件
                item.getItemId();
                item.getTitle().toString();
                return true;
            }
        });
    }
    private void imageClick() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isChanged) {
                    /*重点，LEFT是xml布局文件中侧边栏布局所设置的方向*/
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }

            }
        });
    }

}