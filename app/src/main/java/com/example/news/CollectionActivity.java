package com.example.news;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.news.adapter.MyAdapter;

public class CollectionActivity  extends AppCompatActivity {
    private ListView collection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
}

    private void initView() {
            collection = (ListView) findViewById(R.id.listview_collection);
        Toast.makeText(getApplicationContext(), "收藏夹为空！", Toast.LENGTH_SHORT).show();

    }



}
