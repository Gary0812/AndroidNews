package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.news.utils.ThemeUtil;

public class ThemeActivity extends AppCompatActivity {
    private ThemeActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置主题
        ThemeUtil.setBaseTheme(this);
        setContentView(R.layout.app_theme);
        mContext = this;
        View blue = findViewById(R.id.checkblue);
        View green = findViewById(R.id.checkgreen);
        View yellow = findViewById(R.id.checkyellow);
        View grey = findViewById(R.id.checkgrey);
        View red = findViewById(R.id.checkred);
        View white = findViewById(R.id.checkwhite);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtil.setNewTheme(mContext, ThemeUtil.ThemeColors.ThEME_BLUE)){
                    recreate();
                    intent();
                }
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtil.setNewTheme(mContext, ThemeUtil.ThemeColors.THEME_GREEN))
                    recreate();
                intent();
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtil.setNewTheme(mContext, ThemeUtil.ThemeColors.THEME_YELLOW))
                    recreate();
                intent();
            }
        });
        grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtil.setNewTheme(mContext, ThemeUtil.ThemeColors.THEME_GREY))
                    recreate();
                intent();
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtil.setNewTheme(mContext, ThemeUtil.ThemeColors.THEME_RED)){
                    recreate();
                    intent();
                }
            }
        });
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeUtil.setNewTheme(mContext, ThemeUtil.ThemeColors.THEME_WHITE)){
                    recreate();
                    intent();
                }
            }
        });
//        Toolbar toolbar= (Toolbar) findViewById(R.id.main_toolbar);
//        setSupportActionBar(toolbar);
        //设置不现实自带的title文字
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public void onBackPressed() {
       intent();

    }
    public void intent(){
        ThemeActivity.this.finish();
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }

}
