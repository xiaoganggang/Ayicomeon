package com.com.gang.aiyicomeon.inputapp;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

import cn.bmob.v3.Bmob;
import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2017/3/13.
 */

public class App extends Application {
    private static Context applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        // 得到一个应用程序级别的
        applicationContext = getApplicationContext();
        /*
        * 融云的 SDK 使用了跨进程机制来进行通信，运行后您的 App 后您会发现以下三个进程：
        * 1、您的应用进程；
        * 2、您的应用进程: ipc，这是融云的通信进程；
        * 3、io.rong.push，这是融云的推送进程。
         */
        RongIM.init(this);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        //初始化Bmob
        Bmob.initialize(this, "2f4deb57da815a4f9d95082dd00c464c");
    }
    public static Context getContext() {
        return applicationContext;
    }

}
