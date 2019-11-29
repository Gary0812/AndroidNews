package com.example.news.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.example.news.NewsVo;
import com.example.news.R;

import java.util.List;

public class MyAdapter extends CommonRecyclerAdapter<NewsVo> {

    public MyAdapter(Context context, int itemLayoutId, List<NewsVo> list) {
        super(context, itemLayoutId, list);
    }



    @Override
    public void convert(RecyclerHolder holder, NewsVo item, int position) {
        //标题
        String title = item.getTitle();
        holder.setText(R.id.item_number, TextUtils.isEmpty(title) ? "-" : title);//三木判断下返回是否为空
        //图片地址
        String url = item.getLink();
        holder.setText(R.id.content, TextUtils.isEmpty(url) ? "-" : url);
       // holder.setImageByUrl(R.id.content, url);

    }
}
