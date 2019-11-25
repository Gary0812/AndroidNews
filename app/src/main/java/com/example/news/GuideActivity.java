package com.example.news;

import android.content.Intent;
import android.os.Bundle;

import com.example.news.utils.PrefUtilS;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager vp_guide;
    int[] mImageIds = new int[]{R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3};
    private List<ImageView> mImageViews;
    private LinearLayout llcontainer;
    private int mPointDis;
    private ImageView iv_redpoint;
    private Button btn_start;


    /**
     * 新手引导页
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏的方法必须在setContentView之前调用
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        initViews();
        initData();
    }

    private void initViews() {
        vp_guide = findViewById(R.id.vp_guide);
        llcontainer = findViewById(R.id.ll_container);
        iv_redpoint = findViewById(R.id.iv_redpoint);
        btn_start = findViewById(R.id.btn_start);

    }

    //初始化数据
    private void initData() {
        mImageViews = new ArrayList<ImageView>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            mImageViews.add(view);
            //初始化小圆点
            ImageView point = new ImageView(this);
            //初始化小圆点
            point.setImageResource(R.drawable.shape_point_normal);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i >0) {
                params.leftMargin=15;//从第二个
            }
            point.setLayoutParams(params);//设置布局参数

            llcontainer.addView(point);
        }
        vp_guide.setAdapter(new GuideAdapter());
        //设置监听Viewpager滑动事件，更新红点位置
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("当前位置"+position+"移动位置"+positionOffset);
                //通过的修改小红点的左边距来更新小红点的位置
              int leftMargin =(int) (mPointDis * positionOffset+position*mPointDis+0.5f);//要将当前的位置信息产生的距离加进来
                //获取小红点的布局参数
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_redpoint.getLayoutParams();
            layoutParams.leftMargin=leftMargin;
            iv_redpoint.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                //最后一个页面显示按钮
                if (position == mImageIds.length-1) {
                    btn_start.setVisibility(View.VISIBLE);
                }else
                {
                    btn_start.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
        //measure->layout->draw,必须onCreate执行结束之后，才会开始绘制布局，走三个方法
        //监听layout执行结束的时间，一旦结束之后，再去获取当前的left位置
        //视图树
        iv_redpoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
           // 一但视图树的layout方法调用完成，就会回调此方法
            @Override
            public void onGlobalLayout() {
             //布局位置已经确定，可以拿到位置信息了
                //计算远点移动距离=第二个远点的左边距减去第一个远点的左边距
                mPointDis = llcontainer.getChildAt(1).getLeft()-llcontainer.getChildAt(0).getLeft();
            //移除观察者
               iv_redpoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
btn_start.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//在sp中记录访问过引导页的状态
        PrefUtilS.putBoolean(getApplicationContext(),"is_guide_show",true);

        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();

    }
});
    }


    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {


            return view == object;
        }

        //初始化布局
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView view = mImageViews.get(position);
            container.addView(view);
            return view;
        }

        //销毁布局
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
