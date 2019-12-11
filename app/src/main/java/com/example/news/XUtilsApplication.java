package com.example.news;

import android.app.Application;

import org.xutils.x;

public class XUtilsApplication extends Application {
    public void onCreate() {
        super.onCreate();
	x.Ext.init(this);
	//x.Ext.setDebug(BuildConfig.DEBUG);
}} 
