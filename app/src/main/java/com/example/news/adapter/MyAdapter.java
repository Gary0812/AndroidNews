package com.example.news.adapter;

import android.content.Context;
import android.text.TextUtils;
import com.example.news.model.NewsVo;
import com.example.news.R;

import java.util.List;

public class MyAdapter extends CommonRecyclerAdapter<NewsVo> {

    public MyAdapter(Context context, int itemLayoutId, List<NewsVo> list ,int type) {
        super(context, itemLayoutId, list,type);
    }

    public int getItemViewType(int type) {
        // TODO Auto-generated method stub
        if (type % 3 == 0) {
            return 0;
        }
        if (type % 10 == 0) {
            return 1;
        }
        return 2;

    }




    @Override
    public void convert(RecyclerHolder holder, NewsVo item, int position) {



        getItemViewType(position);
        //标题
        String title = item.getDoctitle();
        holder.setText(R.id.item_number, TextUtils.isEmpty(title) ? "-" : title);//三木判断下返回是否为空
        //图片地址
        String url = item.getDOCPUBURL();
      String img=  item.getImglink();
        String docid =  item.getDocid();
      String author=item.getAuthor();
      String CRTIME=item.getCRTIME();
        if (!TextUtils.isEmpty(img)) {
            holder.setImageByUrl(R.id.item_image, TextUtils.isEmpty(img) ? "-" : img);
        }


           holder.setText(R.id.content, TextUtils.isEmpty(url) ? "-" : url);
        holder.setText(R.id.tv_author, TextUtils.isEmpty(author) ? "-" : author);
        holder.setText(R.id.tv_pubDate, TextUtils.isEmpty(CRTIME) ? "-" : CRTIME);
        holder.setText(R.id.docid, TextUtils.isEmpty(docid) ? "-" : docid);
        holder.setText(R.id.share_title, TextUtils.isEmpty(title) ? "-" : title);

    }
}
