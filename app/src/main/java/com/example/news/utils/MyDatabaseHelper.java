package com.example.news.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/*新建数据表*/

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    public static final String CREATE_COLLECTION = "create table Collection ("
            + "id integer primary key autoincrement,"
            + "news_title text,"
            + "news_date text,"
            + "news_url text,"
            + "news_docid text)";
    public static final String CREATE_COLLECTION_NEWS = "create table Collection_News ("
            + "id integer primary key autoincrement,"
            + "news_title text,"
            + "news_date text,"
            + "news_url text,"
            + "news_docid text)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_COLLECTION);
        sqLiteDatabase.execSQL(CREATE_COLLECTION_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("ALTER TABLE Collection_News ADD COLUMN news_docid INTEGER DEFAULT 0");
    }
}
