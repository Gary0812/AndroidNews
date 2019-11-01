package com.example.news;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<NewsVo> newsVoList;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//找到控件
        lv = findViewById(R.id.lv);
//准备数据 去服务器取数据封装
        initListData();


    }

    private void initListData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    String path="http://172.16.2.94:8080/news.xml";
                    URL url=new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = conn.getInputStream();
                        newsVoList = XmlParserUtils.parserXml(inputStream);
                        System.out.println("aaaa"+newsVoList.size());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lv.setAdapter(new MyAdapter() {
                                    @Override
                                    public int getCount() {
                                        return newsVoList.size();
                                    }

                                    @Override
                                    public Object getItem(int position) {
                                        return null;
                                    }

                                    @Override
                                    public long getItemId(int position) {
                                        return 0;
                                    }

                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View view;
                                        if (convertView==null) {
                                            view = View.inflate(getApplicationContext(), R.layout.item, null);

                                        }else {
                                            view=convertView;
                                        }
                                        ImageView iv_icon = view.findViewById(R.id.iv_icon);
                                        TextView tv_title = view.findViewById(R.id.tv_title);
                                        TextView tv_author = view.findViewById(R.id.tv_author);
                                        TextView tv_pubDate = view.findViewById(R.id.tv_pubDate);
                                        TextView tv_desc = view.findViewById(R.id.tv_desc);

                                        tv_author.setText(newsVoList.get(position).getAuthor());
                                        tv_desc.setText(newsVoList.get(position).getDescription());
                                        tv_pubDate.setText(newsVoList.get(position).getPubDate());
                                        tv_title.setText(newsVoList.get(position).getTitle());
                                        String link = newsVoList.get(position).getLink();//链接

                                        return view;
                                    }

                                });
                            }
                        });

                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "服务器忙...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

 private abstract class MyAdapter extends BaseAdapter
 {

 }

}
