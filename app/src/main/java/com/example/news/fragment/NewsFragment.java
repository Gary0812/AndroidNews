package com.example.news.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news.R;
import com.example.news.ShowNewsActivity;
import com.example.news.adapter.CommonAdapter;
import com.example.news.adapter.CommonRecyclerAdapter;
import com.example.news.adapter.MyAdapter;
import com.example.news.model.NewsVo;
import com.example.news.utils.GsonUtil;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends BaseFragment implements OnBannerListener {
    private static final String TAG = "NewsFragment";
    private Banner banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private View view;
    private CommonAdapter recycleAdapter;
    private List<NewsVo> stringList = new ArrayList<>();
    private RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    private MyAdapter adapter;
    private int type;
    private String result;
    private  final  String CHANNELID="59990";
    private boolean isFirstLoad = true; // 是否第一次加载
    private String share_url;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);

        initView();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", " NewsFragment onResume()");
        if (isFirstLoad) {
            // 将数据加载逻辑放到onResume()方法中

            querynewsItem(CHANNELID);

            isFirstLoad = false;
        }
        setRefresh();
    }


    public View initView() {
        banner = (Banner) view.findViewById(R.id.banner);
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        list_path.add("http://www.taiwan.cn/xwzx/la/201912/W020191225454951233530.jpg");
        list_path.add("http://www.taiwan.cn/taiwan/jsxw/201912/W020191225358156846020.jpg");
        list_path.add("http://www.taiwan.cn/xwzx/la/201906/W020190603630421436630.jpg");
        list_path.add("http://www.taiwan.cn/local/dfkx/201904/W020190415602878645969.jpg");
        list_title.add("国台办12月25日举行例行新闻发布会");
        list_title.add("韩国瑜上脱口秀节目播出9小时逾88万人观看");
        list_title.add("【融融观粤】台媒报道中的大湾区");
        list_title.add("【31条在山东】");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader((ImageLoaderInterface) new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
        recyclerView = view.findViewById(R.id.list);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerView.setAdapter(adapter);
        return  view;
    }

    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        switch (position){
            case 0:

                share_url = "http://www.taiwan.cn/xwzx/la/201912/t20191225_12227837.htm";
             intent(share_url);
                break;
            case 1:

            share_url ="http://www.taiwan.cn/taiwan/jsxw/201912/t20191225_12227786.htm";
                intent(share_url);
                break;
            case 2:

                share_url ="http://www.taiwan.cn/xwzx/la/201906/t20190603_12170661.htm";
                intent(share_url);
                break;
            case 3:

                share_url ="http://www.taiwan.cn/local/dfkx/201904/t20190415_12156601.htm";
                intent(share_url);
                break;

        }

        Log.i("tag", "你点了第"+position+"张轮播图");
    }
    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

  private  void intent(String url)
  {
      Intent i = new Intent(getContext(),ShowNewsActivity.class);
      i.putExtra("share_url", url);
      startActivity(i);
  }

    public void querynewsItem(String channelId) {

        String path = "http://172.16.2.94:8080/wcmInf/querynewsItem";
        RequestParams params = new RequestParams(path);
        params.addParameter("channelId", channelId);
        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                List list=new ArrayList ();
                list= GsonUtil.jsonToList(result,NewsVo.class);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                //设置ReCycleView所需的adapter
                adapter = new MyAdapter(mActivity,R.layout.taiwan_item,list, type);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        TextView textView= (TextView) view.findViewById(R.id.content);
                        TextView docid= (TextView) view.findViewById(R.id.docid);
                        TextView share_title= (TextView) view.findViewById(R.id.share_title);
                        TextView share_time= (TextView) view.findViewById(R.id.tv_pubDate);
                        share_title.getText().toString();
                        share_time.getText().toString();
                        textView.getText().toString();
                        docid.getText().toString();
                        Intent i = new Intent(getContext(),ShowNewsActivity.class);
                        i.putExtra("share_docid", docid.getText().toString());
                        i.putExtra("share_url", textView.getText().toString());
                        i.putExtra("share_time", share_time.getText().toString());
                        i.putExtra("share_title", share_title.getText().toString());
                        startActivity(i);


                    }
                });
                adapter.setOnItemLongClickListener(new CommonRecyclerAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(RecyclerView parent, View view, int position) {
                        Toast.makeText(mActivity,"长按事件"+position, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });


    }

    /**
     * 设置适配器
     *
     * @param
     */
    private void setAdapter() {


    }

    private void setRefresh() {
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                querynewsItem(CHANNELID);
                adapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
/*

                adapter.notifyDataSetChanged();*/
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }
}
