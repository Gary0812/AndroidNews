package com.example.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.news.utils.PrefUtilS;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {
    /**
     * 闪屏页面
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       RelativeLayout rll=findViewById(R.id.rl_root);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
       rotateAnimation.setDuration(1200);
       rotateAnimation.setFillAfter(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
      scaleAnimation.setDuration(1200);
      scaleAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation =new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setDuration(2200);
        alphaAnimation.setFillAfter(true);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        rll.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //判断有没有展示过引导页
                Intent intent = new Intent();
                boolean is_guide_show = PrefUtilS.getBoolean(getApplicationContext(), "is_guide_show", false);
                if (!is_guide_show ) {
                    intent.setClass(getApplicationContext(),GuideActivity.class);
                }else {
                    intent.setClass(getApplicationContext(),MainActivity.class);
                }
                //把那个MainActivity设置为栈底    意思就是防止你按返回键的时候返回到哪个启动欢迎界面
                startActivity(intent);//载入主窗口
                finish();



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
