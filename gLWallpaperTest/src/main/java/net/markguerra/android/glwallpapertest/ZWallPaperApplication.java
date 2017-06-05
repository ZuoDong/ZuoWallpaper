package net.markguerra.android.glwallpapertest;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;


/**
 * 作者：zuo
 * 时间：2017/6/1:17:42
 */

public class ZWallPaperApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
