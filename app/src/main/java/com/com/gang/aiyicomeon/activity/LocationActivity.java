package com.com.gang.aiyicomeon.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.com.gang.aiyicomeon.baidumap.MyOrientationListener;
import com.com.gang.aiyicomeon.bmobbean.Dingdanbmob;
import com.com.gang.aiyicomeon.publiclass.PcOperate;
import com.com.gang.aiyicomeon.utils.ShareUtils;
import com.example.administrator.ayicomeon.R;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class LocationActivity extends AppCompatActivity {
    private CustomProgressDialog dialog;
    private MapView mapView = null;
    private BaiduMap mBaiadumap;
    private LocationMode mLocationMode;// 控制模式
    private Context context;
    // 定位相关
    private LocationClient mLocationClient;
    // 下面继承的一个类,定位的监听器
    private MyLocationListener mLocationListener;
    // 定位功能还需要一个api，Location什么什么
    private boolean isFirstIn = true;
    private double mLatitute;
    private double mLongtitute;
    private String mLocaadress;
    // 自定义定位图标
    private BitmapDescriptor mIconLocation;
    // 传感器的声明
    private MyOrientationListener myOrientationListener;
    // 当前的初始化定位
    private float mCurrentX;
    private BitmapDescriptor mMarkerhongbao;
    private RelativeLayout mMarkerLy;
    private TextView findpeople, findhuo;
    public StringBuilder sb;
    private static int ReQ1 = 1;
    // 回忆dialog里面的控件
    private ImageView fuwupcaddquan;
    private ImageView fuwupcadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        this.context = this;
        initView();
        // 定位的方法
        initLocation();
        // 覆盖物相关
        initMarker();
    }

    private void initView() {
        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.bmapView);
        mBaiadumap = mapView.getMap();
        // 在这里需要一个api就是设置地图放大的比例15f,标识大概在500米左右，这个参数是设置最后缩放的比例，
        // 第一句里的19就是比例尺等级，3.0里比例尺等级范围是3-19，19对应的是200m，等级越小比例尺越大，具体的会显示在页面左下角，你可以自己试一下
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomBy(19.0f);
        mBaiadumap.setMapStatus(msu);
        // mBaiadumap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        // 隐藏缩放控件
        int childCount = mapView.getChildCount();
        View zoom = null;
        for (int i = 0; i < childCount; i++) {

            View child = mapView.getChildAt(i);
            if (child instanceof ZoomControls) {
                zoom = child;
                break;
            }
        }
       /* zoom.setVisibility(View.GONE);
        // 隐藏比例尺控件
        int count = mapView.getChildCount();
        View scale = null;
        for (int i = 0; i < count; i++) {
            View child = mapView.getChildAt(i);
            if (child instanceof ZoomControls) {
                scale = child;
                break;
            }
        }
        scale.setVisibility(View.GONE);
        // 隐藏指南针
        UiSettings mUiSettings = mBaiadumap.getUiSettings();
        mUiSettings.setCompassEnabled(true);
        // 删除百度地图logo*/
        mapView.removeViewAt(1);
        findpeople = (TextView) findViewById(R.id.findpeople);
        findpeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialoghuiyi = new Dialog(LocationActivity.this,
                        R.style.dialog);
                dialoghuiyi.setContentView(R.layout.huiyi);
                dialoghuiyi.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
                /*
                 * 将对话框的大小按屏幕大小的百分比设置
				 */
                Window dialogWindow = dialoghuiyi.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                dialogWindow.setGravity(Gravity.CENTER | Gravity.CENTER);
                WindowManager m = getWindowManager();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.6
                p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕的0.65
                dialogWindow.setAttributes(p);
                // 显示dialog的时候按返回键也不能
                dialoghuiyi.setOnKeyListener(new DialogInterface.OnKeyListener() {

                    public boolean onKey(DialogInterface dialog, int keyCode,
                                         KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                            return true;
                        } else {
                            return false;// 默认返回 false
                        }

                    }
                });

                final TextView huiyidate = (TextView) dialoghuiyi
                        .findViewById(R.id.fuwuidate);
                huiyidate.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                    }
                });
                ImageView fanhuihuiyi = (ImageView) dialoghuiyi
                        .findViewById(R.id.fanhuifuwu);
                fanhuihuiyi.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                        dialoghuiyi.cancel();
                    }
                });
                // 啥是一起标签
                ImageView biaoqiansha = (ImageView) dialoghuiyi
                        .findViewById(R.id.biaoqiansha);
                biaoqiansha.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                        final Dialog biaoqiansha = new Dialog(
                                LocationActivity.this, R.style.dialog);
                        biaoqiansha.setContentView(R.layout.biaoqiansadialog);
                        /*
                         * 将对话框的大小按屏幕大小的百分比设置
						 */
                        Window dialogWindow = biaoqiansha.getWindow();
                        WindowManager.LayoutParams lp = dialogWindow
                                .getAttributes();
                        dialogWindow
                                .setGravity(Gravity.CENTER | Gravity.CENTER);
                        WindowManager m = getWindowManager();
                        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                        WindowManager.LayoutParams p = dialogWindow
                                .getAttributes(); // 获取对话框当前的参数值
                        p.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.6
                        p.width = (int) (d.getWidth() * 0.85); // 宽度设置为屏幕的0.65
                        dialogWindow.setAttributes(p);
                        biaoqiansha.show();
                    }
                });
                final TextView fuwudate = (TextView) dialoghuiyi.findViewById(R.id.fuwuidate);
                final EditText fuwucontext = (EditText) dialoghuiyi
                        .findViewById(R.id.fuwumiaosucontext);

                final EditText fuwutimechang = (EditText) dialoghuiyi
                        .findViewById(R.id.fuwutimechang);
                final EditText fuwujiner = (EditText) dialoghuiyi
                        .findViewById(R.id.fuwujiner);
                fuwupcaddquan = (ImageView) dialoghuiyi
                        .findViewById(R.id.fuwuaddpcquan);
                fuwupcadd = (ImageView) dialoghuiyi
                        .findViewById(R.id.fuwushijingpc);
                fuwupcadd.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, ReQ1);

                    }
                });

                TextView huiyifabu = (TextView) dialoghuiyi
                        .findViewById(R.id.huiyifabu);
                huiyifabu.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                        String fuwudatestr = fuwudate.getText().toString();
                        String fuwucontextstr = fuwucontext.getText()
                                .toString();
                        String fuwutimestr = fuwutimechang.getText()
                                .toString();
                        String fuwujinestr = fuwujiner.getText()
                                .toString();
                        String fuwupc = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1491719649&di=5da6253e1972792f2b961dabce7fcb18&src=http://img01.tobosu.net/users/zsgs/case/2014/10-06/e95ca0ae-4850-bf33-7574-5d7faa5101d1.jpg";

                        inserthuiyibmob(fuwudatestr,
                                fuwucontextstr, fuwutimestr,
                                fuwujinestr, fuwupc, mLatitute,
                                mLongtitute);
                        addOverlays(mMarkerhongbao);
                        dialoghuiyi.cancel();
                    }
                });
                dialoghuiyi.show();
            }
        });

        //找活点击事件
        findhuo = (TextView) findViewById(R.id.findhuo);
        findhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new CustomProgressDialog(LocationActivity.this,
                        "服务寻找中...", R.drawable.animationxun, "15941630147");
                dialog.show();
            }
        });
    }

    private void inserthuiyibmob(String fuwudatestr, String fuwucontextstr,
                                 String fuwutimestr, String fuwujinestr, String
                                         fuwupc, double mLatitute, double mLongtitute) {
// TODO Auto-generated method stub
        String name = ShareUtils.getString(LocationActivity.this, "姓名", "啦啦");
        String age = ShareUtils.getString(LocationActivity.this, "年龄", "啦啦");
        String phone = ShareUtils.getString(LocationActivity.this, "手机号", "啦啦");

        Dingdanbmob bean = new Dingdanbmob();
        bean.setName(name);
        bean.setAge(age);
        bean.setPhone(phone);
        bean.setFuwudate(fuwudatestr);
        bean.setFuwucontext(fuwucontextstr);
        bean.setFuwutimechang(fuwutimestr);
        bean.setMoneydingjin(fuwujinestr);
        bean.setFuwushijing(fuwupc);
        bean.setmLatitute(mLatitute);
        bean.setmLongtitute(mLongtitute);
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String arg0, BmobException arg1) {
                // TODO Auto-generated method stub
                if (arg1 == null) {
                    Toast.makeText(LocationActivity.this, "00000",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LocationActivity.this, "111111",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    /*
     *
	 * 含参数方法，传的参数是mMaker，这样就点击不同的添加，添加不同的图标
	 */

    /**
     * 添加单一覆盖物含参数方法，在这里要判断一下，如果添加的是红包宝贝、秘密宝贝、还是交流宝贝
     */
    private void addOverlays(BitmapDescriptor mMaker) {
        // mBaiadumap.clear();

        LatLng latLng = null;
        Marker marker = null;

        OverlayOptions options;
        // 获得经纬度
        latLng = new LatLng(mLatitute, mLongtitute);
        // 覆盖物用到的图标
        options = new MarkerOptions().position(latLng).icon(mMaker).zIndex(5);
        marker = (Marker) mBaiadumap.addOverlay(options);

        // 添加完图标之后就到那个位置
        /*
         * MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
		 * mBaiadumap.setMapStatus(msu);
		 */
    }

    private void initMarker() {
        mMarkerhongbao = BitmapDescriptorFactory
                .fromResource(R.mipmap.jiazhengbz);
        mMarkerLy = (RelativeLayout) findViewById(R.id.id_maker_ly);

    }

    /**
     * 定位到我的位置
     */
    private void centerToMyLocation() {
        LatLng latLng = new LatLng(mLatitute, mLongtitute);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiadumap.animateMapStatus(msu);
        /*
         * Toast.makeText(MainActivity.this, mLocaadress, Toast.LENGTH_SHORT)
		 * .show();
		 */
    }

    public void initLocation() {
        // 毕竟定位是一个耗时的操作，所以需要一个异步的操作，就需要listener，一个外部类，定位成功以后产生一个回调的方法，对我们主界面的ui实现操作
        mLocationClient = new LocationClient(this);
        // MyLocationListener实例化
        mLocationListener = new MyLocationListener();
        // 通过LocationClient对这个接口mLocationListener进行注册
        mLocationClient.registerLocationListener(mLocationListener);
        // 对mLocationClient设置一些必要的配置
        LocationClientOption option = new LocationClientOption();
        // 第一个参数是坐标类型
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        // 返回一下当前的位置
        option.setOpenGps(true);
        // 每隔多少秒进行一次请求
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        // 初始化图标，这里的方法可以通过许多方式找到图片，包括图片路径等，还并没有更新
        mIconLocation = BitmapDescriptorFactory
                .fromResource(R.mipmap.fangxiaobiao);
        // 因为是一个传感器，所以需要启动和关闭，与定位的生命周期保持一致即可
        myOrientationListener = new MyOrientationListener(context);
        // 接口的回调
        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
                    @Override
                    public void onOrientationChanged(float x) {
                        mCurrentX = x;
                    }
                });
    }


    private class MyLocationListener implements BDLocationListener {

        // 这是定位成功以后的一个回调方法
        public void onReceiveLocation(BDLocation location) {
            // 当参数比较多的时候我们会建一个内部类Builder()，用来初始化我们的数据两个//号的目地只是更好的展示我们的代码返回的是BDLocation，所以我们需要对其进行一个转换
            MyLocationData data = new MyLocationData.Builder()// 定位成功了在这里就可以设置传感器方向了
                    .direction(mCurrentX)// 这个参数就是可以设置定位坐标圆圈的半径
                    .accuracy(1.0f)//
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude()).build();
            mBaiadumap.setMyLocationData(data);
            // 定位以后，正式更新我们的图标
            MyLocationConfiguration config = new MyLocationConfiguration(
                    mLocationMode.NORMAL, true, mIconLocation);
            mBaiadumap.setMyLocationConfigeration(config);
            // 每次定位时确保定位是真的
            mLatitute = location.getLatitude();
            mLongtitute = location.getLongitude();
            mLocaadress = location.getAddrStr();
            // 用户第一次进入地图我们应该吧重心点设置到当前位置，而不是北京
            // 判断是否是第一进地图
            if (isFirstIn) {
                // 定义精度和纬度，初次定位时
                LatLng latLng = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                // 地图效果采用动画传过去
                mBaiadumap.animateMapStatus(msu);
                isFirstIn = false;
                // 这里只是能实现定位，但是我们依然要找地方实现开启定位和关闭定位，在
                // 生命周期的方法里写
                /*
                 * Toast.makeText(MainActivity.this, location.getAddrStr(),
				 * Toast.LENGTH_SHORT).show();
				 */
            }
        }

    }

    // 在这个方法开启我们的定位
    @Override
    protected void onStart() {
        super.onStart();
        // 开启定位之前地图一定开启允许
        mBaiadumap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
        // 开启方向传感器
        myOrientationListener.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mBaiadumap.setMyLocationEnabled(false);
        mLocationClient.stop();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 选择图片返回的
        if (requestCode == ReQ1) {
            if (data == null) {
                return;
            } else {
                Uri uri;
                uri = data.getData();
                if (PcOperate.saveImage(uri)) {
                    Bitmap yasuobitmap = PcOperate.setImage();
                    fuwupcaddquan.setImageBitmap(yasuobitmap);
                }
            }
        }
    }

    /**
     * @author http://blog.csdn.net/finddreams
     * @Description:自定义对话框 搜索宝贝的动画
     */
    public class CustomProgressDialog extends ProgressDialog {

        private AnimationDrawable mAnimation;
        private Context mContext;
        private ImageView mImageView;
        private String mLoadingTip;
        private TextView mLoadingTv;
        private int count = 0;
        private String oldLoadingTip;
        private int mResid;
        private String hehecuantype;

        public CustomProgressDialog(Context context, String content, int id,
                                    String heheotype) {
            super(context);
            this.mContext = context;
            this.mLoadingTip = content;
            this.mResid = id;
            this.hehecuantype = heheotype;
            setCanceledOnTouchOutside(true);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            initView();
            initData();
        }

        private void initData() {
            mImageView.setBackgroundResource(mResid);
            // 通过ImageView对象拿到背景显示的AnimationDrawable
            mAnimation = (AnimationDrawable) mImageView.getBackground();
            // 为了防止在onCreate方法中只显示第一帧的解决方案之一
            mImageView.post(new Runnable() {
                @Override
                public void run() {
                    mAnimation.start();
                }
            });
            mLoadingTv.setText(mLoadingTip);

        }

        public void setContent(String str) {
            mLoadingTv.setText(str);
        }

        private void initView() {
            setContentView(R.layout.progress_dialog);
            mLoadingTv = (TextView) findViewById(R.id.loadingTv);
            mImageView = (ImageView) findViewById(R.id.loadingIv);
        }

        public void onWindowFocusChanged(boolean hasFocus) {
            // TODO Auto-generated method stub
            mAnimation.start();
            final Timer t = new Timer();
            t.schedule(new TimerTask() {
                public void run() {
                    dialog.dismiss();
                    t.cancel();
                    //寻找服务
                    findfuwu();
                }
            }, 3000);
            super.onWindowFocusChanged(hasFocus);
        }
    }

    private void findfuwu() {
// TODO Auto-generated method stub
        BmobQuery<Dingdanbmob> query = new BmobQuery<Dingdanbmob>();
        query.findObjects(new FindListener<Dingdanbmob>() {

            @Override
            public void done(List<Dingdanbmob> arg0, BmobException arg1) {
                // TODO Auto-generated method stub
                if (arg0.size() > 0) {
                    Intent qiangdan = new Intent();
                    qiangdan.setClass(LocationActivity.this, QiangdanAct.class);
                    startActivity(qiangdan);
                }
            }

          /*  private void indataquan(List<Dingdanbmob> xinxianshibeans) {
             // TODO Auto-generated method stub

                for (int i = 0; i < xinxianshibeans.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("菜名", xinxianshibeans.get(i).getCaicaiming());
                    map.put("菜配料", xinxianshibeans.get(i).getCaipeiliao());
                    map.put("菜难度", xinxianshibeans.get(i).getCainandu());
                    map.put("菜pc", xinxianshibeans.get(i).getCaitupian());
                    mListfankui.add(0, map);
                    zaocanadapter = new Myadapter(Jiachangcaijiemian.this,
                            mListfankui);
                    listview.setAdapter(zaocanadapter);
                }
            }*/
        });

    }
}