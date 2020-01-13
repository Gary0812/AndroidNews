package com.example.news.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.R;
import com.example.news.ShowNewsActivity;
import com.example.news.adapter.CommonAdapter;
import com.example.news.adapter.CommonRecyclerAdapter;
import com.example.news.adapter.MyAdapter;
import com.example.news.model.NewsVo;

import com.example.news.utils.DummyContent.DummyItem;
import com.example.news.utils.GsonUtil;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class LocalityFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private View view;

    private CommonAdapter recycleAdapter;
    private List<NewsVo> stringList = new ArrayList<>();
    private RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    private MyAdapter adapter;
    private int type;
    private String result;
    private  final  String CHANNELID="59992";
    private boolean isFirstLoad = true; // 是否第一次加载
    private List list;

    public LocalityFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LocalityFragment newInstance(int columnCount) {
        LocalityFragment fragment = new LocalityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_locality_list, container, false);
        initView();
        return  view;
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", "LocalityFragment onResume()");
        if (isFirstLoad) {
            // 将数据加载逻辑放到onResume()方法中

            querynewsItem(CHANNELID);

            isFirstLoad = false;
        }
        setRefresh();
    }
/*    public  void  initData(){
        setRefresh();
        querynewsItem(CHANNELID);
    }*/
    @Override
    public View initView() {
        recyclerView = view.findViewById(R.id.list);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerView.setAdapter(adapter);

        return null;
    }


/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    public void querynewsItem(String channelId) {
        recyclerView = view.findViewById(R.id.list);
        String path = "http://172.16.2.94:8080/wcmInf/querynewsItem";
        RequestParams params = new RequestParams(path);
        params.addParameter("channelId", channelId);


        // params.addParameter("password", "123");
        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                list = new ArrayList ();
                list = GsonUtil.jsonToList(result,NewsVo.class);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                //设置ReCycleView所需的adapter
                adapter = new MyAdapter(getActivity(),R.layout.taiwan_item, list, type);
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
//                        Toast.makeText(mActivity,"点击事件"+textView.getText().toString(),Toast.LENGTH_SHORT).show();
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


    private void setReCycleView() {

        //设置ReCycleView

    }


 /*   @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }*/

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
          /*      NewsVo newsVo = new NewsVo();
                newsVo.setTitle("下拉刷新");
                newsVo.setLink("aaa");
                stringList.add(0, newsVo);*/
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
