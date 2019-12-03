package com.example.news.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news.NewsVo;
import com.example.news.R;
import com.example.news.adapter.CommonAdapter;
import com.example.news.adapter.MyAdapter;
import com.example.news.utils.DummyContent;
import com.example.news.utils.DummyContent.DummyItem;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;



import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TaiwanFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private View view;

    private CommonAdapter recycleAdapter;
    private List<NewsVo> stringList = new ArrayList<>();
    private RecyclerView recyclerView;
    RefreshLayout refreshLayout ;
    private MyAdapter adapter;
    private  int type;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaiwanFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TaiwanFragment newInstance(int columnCount) {
        TaiwanFragment fragment = new TaiwanFragment();
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
        view = inflater.inflate(R.layout.fragment_taiwan_list, container, false);

initView();

  /*      // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView  recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            setAdapter();
            recyclerView.setAdapter(new TaiwanRecyclerViewAdapter(DummyContent.ITEMS, mListener));
            setRefresh();
        }*/




        return view;
    }

  private void initView() {

      recyclerView=view.findViewById(R.id.list);
      refreshLayout  = view.findViewById(R.id.refreshLayout);

      //设置数据
      setData();
      //设置ReCycleView
      setReCycleView();

    }



    private void setData() {
        if (stringList.size() == 0) {
            for (int i = 1; i < 21; i++) {
                NewsVo newsVo=new NewsVo();
                newsVo.setTitle("第" + i + "条数据");
                newsVo.setLink("www.taiwan.cn");
                newsVo.setType(i);
                stringList.add(newsVo);
            }
        }


    }

    private void setReCycleView() {
        //设置ReCycleView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置ReCycleView所需的adapter
        adapter = new MyAdapter(getActivity(),R.layout.taiwan_item,stringList, type);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        setRefresh();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    /**
     * 设置适配器
     *
     * @param
     */
    private void setAdapter() {


    }

    private void setRefresh() {
        refreshLayout  = view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new TwoLevelHeader(getActivity()));
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
              NewsVo newsVo=new NewsVo();
                newsVo.setTitle("下拉刷新");
                newsVo.setLink("aaa");
                stringList.add(0,newsVo);
                adapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                NewsVo newsVo=new NewsVo();
                newsVo.setTitle("上拉加载");
                newsVo.setLink("bbb");
                stringList.add(newsVo);
                adapter.notifyDataSetChanged();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

}
