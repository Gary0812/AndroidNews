package com.example.news;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.news.fragment.MainFragment;
import com.example.news.fragment.MineFragment;
import com.example.news.fragment.SettingFragment;



import java.util.ArrayList;
import java.util.List;

/*底部切换控制*/
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_main, ll_setting, ll_mine;

    private MainFragment mainFragment;
    private SettingFragment settingFragment;
    private MineFragment mineFragment;

    private List<Fragment> fragmentList = new ArrayList<>();

    private ImageView img_main, img_seting, img_mine;
    private TextView text_main, text_setting, text_mine;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   /*     setBehindContentView(R.layout.left_menu);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setAboveOffset(260);*/
        initView();
        initFragment();

        ll_main.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_mine.setOnClickListener(this);

    }

    private void initView() {



        ll_main = (LinearLayout) findViewById(R.id.layout_main);
        ll_setting = (LinearLayout)findViewById(R.id.layout_setting);
        ll_mine = (LinearLayout)findViewById(R.id.layout_mine);

        text_main = (TextView) findViewById(R.id.text_main);
        text_setting = (TextView) findViewById(R.id.text_setting);
        text_mine = (TextView) findViewById(R.id.text_mine);

        img_main = (ImageView) findViewById(R.id.img_main);
        img_seting =(ImageView) findViewById(R.id.img_setting);
        img_mine =(ImageView) findViewById(R.id.img_mine);

        img_main.setImageResource(R.drawable.main_selected);
        text_main.setTextColor(Color.RED);

    }

    private void initFragment() {
        mainFragment = new MainFragment();
        addFragment(mainFragment);
        showFragment(mainFragment);

    }

    /*添加fragment*/
    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(R.id.main_content, fragment).commit();
            fragmentList.add(fragment);
        }
    }

    /*显示fragment*/
    private void showFragment(Fragment fragment) {
        for (Fragment frag : fragmentList) {
            if (frag != fragment) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(frag).commit();
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.show(fragment).commit();
    }

public  void onClick1(View view)
{
    Intent intent = new Intent(this,vitamio.class);
    startActivity(intent);

}

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_main: {
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                }
                addFragment(mainFragment);
                showFragment(mainFragment);
                text_main.setTextColor(Color.RED);
                text_setting.setTextColor(Color.BLACK);
                text_mine.setTextColor(Color.BLACK);

                img_main.setImageResource(R.drawable.main_selected);
                img_seting.setImageResource(R.drawable.setting);
                img_mine.setImageResource(R.drawable.mine);

            }
            break;
            case R.id.layout_setting: {
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                }
                addFragment(settingFragment);
                showFragment(settingFragment);
                text_setting.setTextColor(Color.RED);
                text_main.setTextColor(Color.BLACK);
                text_mine.setTextColor(Color.BLACK);

                img_main.setImageResource(R.drawable.main);
                img_seting.setImageResource(R.drawable.setting_selected);
                img_mine.setImageResource(R.drawable.mine);
            }
            break;
            case R.id.layout_mine: {
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
                addFragment(mineFragment);
                showFragment(mineFragment);
                text_mine.setTextColor(Color.RED);
                text_main.setTextColor(Color.BLACK);
                text_setting.setTextColor(Color.BLACK);

                img_main.setImageResource(R.drawable.main);
                img_seting.setImageResource(R.drawable.setting);
                img_mine.setImageResource(R.drawable.mine_selected);
            }
            break;
            default:
                break;
        }
    }
}