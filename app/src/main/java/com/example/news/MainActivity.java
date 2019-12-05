package com.example.news;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.news.fragment.MainFragment;
import com.example.news.fragment.MineFragment;
import com.example.news.fragment.SettingFragment;
import com.example.news.fragment.VideoFragment;
import com.example.news.model.UserVo;
import com.example.news.utils.PrefUtilS;
import com.example.news.utils.XUtilsDate;
import com.google.android.material.bottomnavigation.BottomNavigationView;




import java.util.ArrayList;
import java.util.List;


/*底部切换控制*/
public class MainActivity extends AppCompatActivity  {


    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private LinearLayout ll_main, ll_setting, ll_mine;

    private MainFragment mainFragment;
    private SettingFragment settingFragment;
    private static MineFragment mineFragment;
    private static VideoFragment videoFragment;


    private List<Fragment> fragmentList = new ArrayList<>();

    private ImageView img_main, img_seting, img_mine;
    private TextView text_main, text_setting, text_mine;
    private TextView mTextMessage;
    private UserVo userVo;
    private static int a;


    private static boolean b;

    private static NavController navController;


/*    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    transaction.replace(R.id.container,new MainFragment());
                    transaction.commit();

                    return true;
                case R.id.navigation_video:

                    transaction.replace(R.id.container,new VideoFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_mine:

                    transaction.replace(R.id.container,new MineFragment());
                    transaction.commit();
                    return true;
            }
            return false;
        }

    };*/


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         init();
        if (userVo != null) {
            XUtilsDate xUtils = new XUtilsDate();
             xUtils.queryLogin(userVo);
        }





       // setDefaultFragment();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_video,R.id.navigation_mine)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


      /*  mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int checkPermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1); //后面的1为请求码
                Log.d("注意", "未授权,去授权");

                return;
            } else {
                Log.d("注意", "已授权...");
            }

        } else {
            Log.d("注意", "版本<=8.0");

        }*/
    }


     public void init()
     {
         String username=PrefUtilS.getUser(this,"username",null);
         String password=PrefUtilS.getUser(this,"password",null);
         String IMEI=PrefUtilS.getUser(this,"IMEI",null);
         if (IMEI != null) {
             userVo = new UserVo();
             userVo.setUsername(username);
             userVo.setPassword(password);
             userVo.setIMEI(IMEI);
         }else
         {
             userVo=null;
         }

     }


    // 设置默认进来是tab 显示的页面
    private void setDefaultFragment(){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,new MainFragment());
        transaction.commit();
    }


}