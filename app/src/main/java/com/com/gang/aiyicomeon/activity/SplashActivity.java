package com.com.gang.aiyicomeon.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.com.gang.aiyicomeon.utils.StreamUtil;
import com.com.gang.aiyicomeon.utils.ToastUtil;
import com.example.administrator.ayicomeon.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    private TextView tv_version_name;
    private int mLocalVersionCode;
    protected static final String tag = "SplashActivity";
    //更新新版本的状态码
    protected static final int UPDATE_VERSION = 100;
    //进入主界面的状态码
    protected static final int ENTER_HOME = 101;
    //url地址异常错误
    protected static final int URL_ERROR = 102;
    //io异常错误
    protected static final int IO_ERROR = 103;
    //JSON解析错误
    protected static final int JSON_ERROR = 104;
    private String mVersionDes;
    private String mDownloadUrl;
    private Handler mHandler = new Handler() {
        //注意这个方法千万不要手敲，如果敲出来的话就是起不到作用的方法，用handler发送消息
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VERSION:
                    //弹出对话框,提示用户更新
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    //进入应用程序主界面,activity跳转过程
                    enterHome();
                    break;
                case URL_ERROR:
                    //不管是上述的哪一种异常，我们都不应该阻止用户使用我们的软件
                    ToastUtil.show(getApplicationContext(), "url异常");
                    enterHome();
                    break;
                case IO_ERROR:
                    ToastUtil.show(getApplicationContext(), "读取异常");
                    enterHome();
                    break;
                case JSON_ERROR:
                    ToastUtil.show(getApplicationContext(), "json解析异常");
                    enterHome();
                    break;
            }
        }


    };

    /**
     * 进入应用程序主界面
     */
    protected void enterHome() {
        Intent intent = new Intent(this, Ayi_MainActivity.class);
        startActivity(intent);
        //在开启一个新的界面后,将导航界面关闭(导航界面只可见一次)
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //初始化ui
        InitUI();
        //初始化数据
        InitDate();
    }

    private void InitDate() {
        //1、应用版本名称
        tv_version_name.setText("版本名称" + getVersionname());
        //检测服务端（检测本地版本号和服务器版本号对比）是否有新版本，如果有更新，提示用户下载
        //2、获取本地版本号
        mLocalVersionCode = getVersionCode();
        //3、获取服务器版本号(客户端给服务端发送请求，服务器给返回)
        //服务器请求地址，返回200请求成功，流的方式将数据读取出来
        //这里需要以json格式来完成
        /*新版apk下载地址
        *新版apk描述
        *更新版本的版本名称
        *服务器版本号
        **/
        CheckVersion();


    }

    //检测服务器的版本号
    private void CheckVersion() {
        //开启线程的两种方法
        new Thread() {
            @Override
            public void run() {
                //在run方法中可以发送消息,创建消息的两种方式
                Message message = Message.obtain();
                long startTime = System.currentTimeMillis();
                //Message message=new Message();
                //发送请求，获取数据,这种地址只是本地测试可以，但是可以用的时候像我们访问百度，都是把服务器地址转化成了域名，上线阶段需要修改
                //谷歌单独提供了仅限于模拟器访问的服务器地址，用于测试10.0.2.2
                try {
                    //1.封装url地址
                    URL url = new URL("http://localhost:8080/update74.json");
                    //2、创建连接
                    try {
                        HttpURLConnection urlonnection = (HttpURLConnection) url.openConnection();
                        //3、设置常见请求参数
                        //请求超时,压根没连上
                        urlonnection.setConnectTimeout(2000);
                        //读取超时，不能读数据的时间超过两秒钟
                        urlonnection.setReadTimeout(2000);
                        //默认请求方式就是get，注意里面内容大写
                        //urlonnection.setRequestMethod("POST");
                        //4、获取请求成功码
                        if (urlonnection.getResponseCode() == 200) {
                            //5、以流的方式将数据获取下来
                            InputStream is = urlonnection.getInputStream();
                            //用到工具类，工具类封装，将流装成字符串
                            String json = StreamUtil.steamToString(is);
                            Log.i(tag, json);
                            //7,json解析
                            JSONObject jsonObject = new JSONObject(json);

                            //debug调试,解决问题,要注意的是服务器接口中的字段一定要是复制的，不能使手写过来的，容易报错
                            String versionName = jsonObject.getString("versionName");
                            mVersionDes = jsonObject.getString("versionDes");
                            String versionCode = jsonObject.getString("versionCode");
                            mDownloadUrl = jsonObject.getString("downloadUrl");
                            //日志打印
                            Log.i(tag, versionName);
                            Log.i(tag, mVersionDes);
                            Log.i(tag, versionCode);
                            Log.i(tag, mDownloadUrl);
                            //8、比对版本号
                            if (mLocalVersionCode < Integer.parseInt(versionCode)) {
                                //提示用户更新，弹出对话框，属于ui可视化的操作，用到消息机制
                                message.what = UPDATE_VERSION;
                            } else {
                                //进入应用程序主界面
                                message.what = ENTER_HOME;
                            }

                        }
                        //出现不同的异常我们也可以返回不同的值，这样便于看错
                    } catch (IOException e) {
                        e.printStackTrace();
                        message.what = URL_ERROR;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        message.what = IO_ERROR;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    message.what = JSON_ERROR;
                } finally {
                    //指定睡眠时间,请求网络的时长超过4秒则不做处理
                    //请求网络的时长小于4秒,强制让其睡眠满4秒钟
                    long endTime = System.currentTimeMillis();
                    if (endTime - startTime < 4000) {
                        try {
                            Thread.sleep(4000 - (endTime - startTime));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(message);
                }
            }
        }.start();
        //runable是接口，通过构造函数的话传进来接口是不可以的,所以要创建这个接口的实现类的对象
        /*new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });*/
    }

    //弹出对话框的方法,提示用户更新
    protected void showUpdateDialog() {
        //在创建对话框的时候需要注意的一个错误，传递的参数是this，不是上下文对象
        //对话框是依赖于Activity存在的,不能用getApplication。,用这个的话会报错，提示application什么没有载体
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置左上角图标
        builder.setIcon(R.drawable.ic_back);
        builder.setTitle("版本更新");
        //设置描述内容
        builder.setMessage(mVersionDes);

        //积极按钮,立即更新
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载apk,apk链接地址,downloadUrl
                downloadApk();
                //在这里我们回顾一下多线程断点续传的经历

            }
        });

        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消对话框,进入主界面
                enterHome();
            }
        });

        //点击取消事件监听
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //即使用户点击取消,也需要让其进入应用程序主界面
                enterHome();
                dialog.dismiss();
            }
        });

        builder.show();

    }


    //Apk下载的方法，有抽象方法的类一定是抽象类，抽象类中的方法不一定是抽象方法，抽象类不能声明对象
    private void downloadApk() {
        //需要确定的是apk下载地址和sdk下载后放置的路径

        //首先判断sd卡是否可用，是否挂在上
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //2,获取sd路径
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "mobilesafe74.apk";
            //3,发送请求,获取apk,并且放置到指定路径
            HttpUtils httpUtils = new HttpUtils();
            //4,发送请求,传递参数(下载地址,下载应用放置位置)
            httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    //下载成功(下载过后的放置在sd卡中apk)
                    Log.i(tag, "下载成功");
                    File file = responseInfo.result;
                    //提示用户安装
                    installApk(file);
                }

                @Override
                public void onFailure(HttpException arg0, String arg1) {
                    Log.i(tag, "下载失败");
                    //下载失败
                }

                //刚刚开始下载方法
                @Override
                public void onStart() {
                    Log.i(tag, "刚刚开始下载");
                    super.onStart();
                }

                //下载过程中的方法(下载apk总大小,当前的下载位置,是否正在下载)
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    Log.i(tag, "下载中........");
                    Log.i(tag, "total = " + total);
                    Log.i(tag, "current = " + current);
                    super.onLoading(total, current, isUploading);
                }
            });

        }
    }
//怎么导包，右键项目，然后Android Tools，然后导出一个带签名的包
    private void installApk(File file) {

    }

    //获取版本号的方法，软件的版本号不可能为0，出现异常的时候return0
    private int getVersionCode() {
        //1、用到包管理者对象PackageManager
        PackageManager pm = getPackageManager();
        //2、从包管理者对象中获取指定包名的基本信息,
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            //3、获取版本名称,正常状态下取到的版本名称
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }


    private void InitUI() {
        //引用了xutils的话可以用注解的方式代替掉常规找id的方式
        //@ViewInject(R.id.textView)
        // TextView textView;
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
    }

    //获取版本名称：在清单文件中
    private String getVersionname() {
        //1、用到包管理者对象PackageManager
        PackageManager pm = getPackageManager();
        //2、从包管理者对象中获取指定包名的基本信息,
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            //3、获取版本名称,正常状态下取到的版本名称
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();

        }
        //异常情况下返回的版本名称是null
        return null;
    }
}