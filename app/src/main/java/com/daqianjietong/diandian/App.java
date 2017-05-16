package com.daqianjietong.diandian;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.daqianjietong.diandian.utils.SpUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by MuWenlei on 16/10/15.
 */
public class App extends Application {
    private static App mApp;
    private List<Activity> list = new ArrayList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        x.Ext.init(this);
        SpUtil.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public void exit() {
        for (Activity activity : list) {
            if (activity != null) {
                activity.finish();
            }
        }
        // android.os.Process.killProcess(android.os.Process.myPid());
//		System.exit(0);
    }

    public static App getmApp() {
        return mApp;
    }

    public static void setmApp(App mApp) {
        App.mApp = mApp;
    }


    public static Context getAppContext() {
        return mApp;
    }

    public static Resources getAppResources() {
        return mApp.getResources();
    }

}
