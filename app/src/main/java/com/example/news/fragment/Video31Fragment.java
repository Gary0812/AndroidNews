package com.example.news.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import org.xutils.x;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.R;
import com.example.news.ShowNewsActivity;
import com.example.news.VideoActivity;
import com.example.news.adapter.CommonRecyclerAdapter;
import com.example.news.adapter.MyAdapter;
import com.example.news.model.NewsVo;
import com.example.news.utils.GsonUtil;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class Video31Fragment extends BaseFragment {
    private boolean isFirstLoad = true; // 是否第一次加载
    private int type;
    private String result;
    private RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    private  final  String CHANNELID="59995";
    private View view;
    private MyAdapter adapter;
    private List list;

    public static Video31Fragment newInstance() {
        return new Video31Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.video31_fragment, container, false);

        initView();
        return view;
    }

    @Override
    public View initView() {

        recyclerView = view.findViewById(R.id.list);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        return null;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            // 将数据加载逻辑放到onResume()方法中
            querynewsItem(CHANNELID);

            isFirstLoad = false;
        }
        setRefresh();
    }
    public void querynewsItem(String channelId) {

        String path = "http://172.16.2.94:8080/wcmInf/querynewsItem";
        RequestParams params = new RequestParams(path);
        params.addParameter("channelId", channelId);
        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                list = new ArrayList();
                list = GsonUtil.jsonToList(result, NewsVo.class);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                //设置ReCycleView所需的adapter
                adapter = new MyAdapter(mActivity,R.layout.item_video, list, type);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        TextView textView= (TextView) view.findViewById(R.id.content);
                        textView.getText().toString();
//
                        Intent i = new Intent(getContext(), VideoActivity.class);
                        System.out.println();
                        i.putExtra("share_url", textView.getText().toString());
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



    private void setRefresh() {
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                querynewsItem(CHANNELID);
                adapter = new MyAdapter(getActivity(),R.layout.taiwan_item, list, type);
                adapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {


                adapter.notifyDataSetChanged();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }





}
