package com.example.news;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.news.adapter.MyAdapter;
import com.example.news.adapter.NewsAdapter;
import com.example.news.model.NewsInfo;
import com.example.news.model.NewsVo;
import com.example.news.utils.GsonUtil;
import com.example.news.utils.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import org.xutils.x;

public class CollectionActivity  extends BaseActivity implements NewsAdapter.CallBack{
    private ListView collection;
    private Context context;
    private int type;

    private TextView tm;
    private TextView docid;
    private List<NewsInfo> newsList = new ArrayList<>();
    private ImageView image_drawer_home;
    private NewsAdapter adapter;
    private MyDatabaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        helper = new MyDatabaseHelper(this, "TaiDB.db", null, 1);


//        tm =(TextView) findViewById(R.id.news_item_date);
        image_drawer_home =(ImageView) findViewById(R.id.image_drawer_home);
        image_drawer_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        x.view().inject(this);
//        mNewsInfoDao = new NewsInfoDao();
        initView();
        initViews();
        collection.setAdapter(adapter);

        collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String type=newsList.get(i).getType();
                if (type.equals("news")) {
                    String url = newsList.get(i).getUrl();
                    String docid = newsList.get(i).getId();
                    String title = newsList.get(i).getTitle();
                    String time = newsList.get(i).getTime();
                    Intent intent = new Intent(CollectionActivity.this, ShowNewsActivity.class);
                    intent.putExtra("share_url", url);
                    intent.putExtra("share_docid", docid);
                    intent.putExtra("share_title", title);
                    intent.putExtra("share_time", time);
                    startActivity(intent);
                }
                if (type.equals("vedio")) {
                    String url = newsList.get(i).getUrl();
                    String docid = newsList.get(i).getId();
                    String title = newsList.get(i).getTitle();
                    String time = newsList.get(i).getTime();
                    Intent intent = new Intent(CollectionActivity.this, vitamio.class);
                    intent.putExtra("videourl", url);
                    intent.putExtra("share_docid", docid);
                    intent.putExtra("share_title", title);
                    intent.putExtra("share_time", time);
                    startActivity(intent);
                }

            }
        });

}
    private void initViews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("select * from Collection_News", null);
                if (cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            //遍历Cursor对象，取出数据并打印
                            String news_url = cursor.getString(cursor.getColumnIndex("news_url"));
                            String news_title = cursor.getString(cursor.getColumnIndex("news_title"));
                            String news_date = cursor.getString(cursor.getColumnIndex("news_date"));
                            String news_docid = cursor.getString(cursor.getColumnIndex("news_docid"));
                            String news_type = cursor.getString(cursor.getColumnIndex("news_type"));

                            NewsInfo news = new NewsInfo(news_title, news_url, news_date,news_docid,news_type);
                            newsList.add(news);

                        } while (cursor.moveToNext());
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "收藏夹为空！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

                cursor.close();
                db.close();
            }
        }).start();




    }
    private void initView() {
        collection = (ListView) findViewById(R.id.listview_collection);
        adapter = new NewsAdapter(this, R.layout.news_item, newsList, this);



    }

    @Override
    public void click(View view) {
//        int position = Integer.parseInt(view.getTag().toString());
//        System.out.println("666666"+position);
//        SQLiteDatabase db = helper.getReadableDatabase();
//        db.execSQL("delete from Collection_News where news_url=?",
//                new String[]{newsList.get(position).getType()});
//        db.close();
//        newsList.remove(position);
//        adapter.notifyDataSetChanged();
//        Toast.makeText(this, "该新闻已被移除收藏夹！", Toast.LENGTH_SHORT).show();
    }
}
