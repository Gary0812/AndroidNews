package com.example.news.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.news.R;
import com.example.news.model.NewsInfo;


import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsInfo> implements View.OnClickListener {
    private int resourceId;
    private CallBack mCallBack;

    public NewsAdapter(Context context, int textViewResourceId, List<NewsInfo> objects, CallBack callBack) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mCallBack = callBack;
    }


    public interface CallBack {
        public void click(View view);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsInfo news = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
//            viewHolder.newsImg = view.findViewById(R.id.news_item_img);
            viewHolder.newsTitle = view.findViewById(R.id.news_item_title);
            viewHolder.newsAuthor = view.findViewById(R.id.news_item_author);
            viewHolder.newsDate = view.findViewById(R.id.news_item_date);
//            viewHolder.newsDelete = view.findViewById(R.id.delete_item);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.newsTitle.setText(news.getTitle());
        viewHolder.newsDate.setText(news.getTime());
//       viewHolder.newsDate.setText(news.getDate());

//      viewHolder.newsDelete.setTag(position);
//     viewHolder.newsDelete.setOnClickListener(this);

        return view;
    }


    static class ViewHolder {
//        ImageView newsImg;
        TextView newsTitle;
        TextView newsAuthor;
        TextView newsDate;
//      ImageView newsDelete;
    }

    @Override
    public void onClick(View view) {
        mCallBack.click(view);
    }
}

