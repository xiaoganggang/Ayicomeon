package com.com.gang.aiyicomeon.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.com.gang.aiyicomeon.fragment.DingdanFragment;
import com.com.gang.aiyicomeon.fragment.KefuFragment;
import com.com.gang.aiyicomeon.fragment.LiaotianFragment;
import com.com.gang.aiyicomeon.fragment.ZhuyeFragment;
import com.com.gang.aiyicomeon.rongyun.FriendFragment;
import com.com.gang.aiyicomeon.utils.ToastUtil;
import com.example.administrator.ayicomeon.R;
import com.com.gang.aiyicomeon.adapter.FragAdapter;
import com.com.gang.aiyicomeon.zidingyiview.RoundImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class Ayi_MainActivity extends AppCompatActivity {
    //对于有状态的button要`想使用Vectordrawable的时候除了要是用Selector进行设置，还需要在当前的Activity中进行一个配置,这是谷歌开发者挖的一个坑
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private ViewPager viewPager;
    private FragAdapter fragAdapter;
    private ZhuyeFragment zhuyefragment;
    private DingdanFragment dingdanfragment;
    private TextView findayitextview, dingdantextview, kefutextview;
    private LinearLayout findayilin, dingdanlin, kefulin;
    private ImageView findayiimg, dingdanimg, kefuimg;
    //侧滑时候需要的声明
    private DrawerLayout drawerLayout;
    //private ListView lv;
    private FrameLayout flContent;
    private List<String> datas;
    private ArrayAdapter<String> adapter;
    private String title;
    private String[] citys = {
            "上海", "北京", "深圳", "广州", "杭州"
    };
    private AnimationDrawable mAnimationDrawable;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private RoundImageView pcjinru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_ayi);
        //使用token连接服务器
        connectRongServer("akdsJ62YjfVsqp3+SktuagnQ9UyzLZGLAKrPTuDJmhINjbsYK7UaDscbc8reSr3R2lskCrWo\\/p6+a0+k+cEhrrOearke5MiALmi\\/eFomL7w=");
        InitView();
    }

    private void InitView() {
        //下方控件初始化
        findayitextview = (TextView) findViewById(R.id.findayitextview);
        dingdantextview = (TextView) findViewById(R.id.dingdantextview);
        kefutextview = (TextView) findViewById(R.id.kefutextview);
        findayilin = (LinearLayout) findViewById(R.id.findayilin);
        dingdanlin = (LinearLayout) findViewById(R.id.dingdanlin);
        kefulin = (LinearLayout) findViewById(R.id.kefulin);
        findayiimg = (ImageView) findViewById(R.id.findayiimg);
        dingdanimg = (ImageView) findViewById(R.id.dingdanimg);
        kefuimg = (ImageView) findViewById(R.id.kefuimg);
        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        zhuyefragment = new ZhuyeFragment();
        dingdanfragment = new DingdanFragment();
        fragments.add(zhuyefragment);
        fragments.add(dingdanfragment);
        fragments.add(LiaotianFragment.getInstance());
        fragAdapter = new FragAdapter(getSupportFragmentManager(), fragments);
        //设定适配器
        viewPager = (ViewPager) findViewById(R.id.zhuyeviewpager);
        viewPager.setAdapter(fragAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                ResetBackground();
                // int currentItem = viewPager.getCurrentItem();
                switch (position) {
                    case 0:
                        findayitextview.setTextColor(Color.parseColor("#01BEAE"));
                        findayiimg.setBackgroundResource(R.mipmap.findayilan);
                        break;
                    case 1:
                        dingdantextview.setTextColor(Color.parseColor("#01BEAE"));
                        dingdanimg.setBackgroundResource(R.mipmap.dingdanlan);
                        break;
                    case 2:
                        kefutextview.setTextColor(Color.parseColor("#01BEAE"));
                        kefuimg.setBackgroundResource(R.mipmap.kefulan);
                        break;
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            public void onPageScrollStateChanged(int arg0) {

            }
        });
        viewpagedianji();
        //侧滑内容
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        flContent = (FrameLayout) findViewById(R.id.flContent);
        //lv = (ListView) findViewById(R.id.cehualv);
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        datas = new ArrayList<String>();
        datas.addAll(Arrays.asList(citys));
       /* mAnimationDrawable = (AnimationDrawable) ivRunningMan.getBackground();
        mAnimationDrawable.start();*/
        toolbar.setTitle("");
        //toolbar.setTitle("阿姨来了");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#01BEAE")); //设置标题颜色
        //设置显示toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //mAnimationDrawable.stop();
                invalidateOptionsMenu();// 重新绘制actionBar上边的菜单项
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //mAnimationDrawable.start();
                invalidateOptionsMenu();// 重新绘制actionBar上边的菜单项
            }
        };
        mDrawerToggle.syncState();//使得箭头和三道杠图案和抽屉拉和保持一致
        drawerLayout.setDrawerListener(mDrawerToggle);
        //设置菜单列表
        //绑定适配器
       /* adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });*/
        pcjinru = (RoundImageView) findViewById(R.id.pcjinru);
        pcjinru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent = new Intent();
                loginintent.setClass(Ayi_MainActivity.this, LoginActivity.class);
                startActivity(loginintent);
            }
        });
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Toast.makeText(this, "aaaaaaaaaa", Toast.LENGTH_LONG).show();
    }

    private void ResetBackground() {
        findayitextview.setTextColor(Color.parseColor("#BFBFBF"));
        dingdantextview.setTextColor(Color.parseColor("#BFBFBF"));
        kefutextview.setTextColor(Color.parseColor("#BFBFBF"));
        findayiimg.setBackgroundResource(R.mipmap.findayihui);
        dingdanimg.setBackgroundResource(R.mipmap.dingdanhui);
        kefuimg.setBackgroundResource(R.mipmap.kefuhui);
    }

    private void viewpagedianji() {
        findayilin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ResetBackground();
                viewPager.setCurrentItem(0);
                findayitextview.setTextColor(Color.parseColor("#01BEAE"));
                findayiimg.setBackgroundResource(R.mipmap.findayilan);
            }
        });
        dingdanlin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ResetBackground();
                viewPager.setCurrentItem(1);
                dingdantextview.setTextColor(Color.parseColor("#01BEAE"));
                dingdanimg.setBackgroundResource(R.mipmap.dingdanlan);
            }
        });
        kefulin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ResetBackground();
                viewPager.setCurrentItem(2);
                kefutextview.setTextColor(Color.parseColor("#01BEAE"));
                kefuimg.setBackgroundResource(R.mipmap.kefulan);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    private void connectRongServer(final String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            //成功的回调，输出的s就是当前token输入的userid
            @Override
            public void onSuccess(String s) {
                //ToastUtil.show(Ayi_MainActivity.this, s + "********" + ssno);
                //这里相当于就是判断账号和密码是否正确
                if (s.equals("s:2014060306004")) {
                    //用户连接成功
                    ToastUtil.show(Ayi_MainActivity.this, "用户" + s + "融云服务器连接成功");
                    if (RongIM.getInstance() != null)
                        RongIM.getInstance().setCurrentUserInfo(new UserInfo("s:2014060306004", "姬志欣", Uri.parse("http:\\/\\/10.11.3.177:80\\/infoCollect\\/im\\/e\\/w\\/avatar_2014060306004.jpg")));
                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                } else {
                    //ToastUtil.show(MainAct.this, "USERID不匹配");
                }
            }

            //token错误，网路有误，网络不可用都可以在这边回调回来
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                //打印错误代码
                Log.e("onError", "onError" + errorCode.getValue());
            }

            //token有误的回调
            @Override
            public void onTokenIncorrect() {

            }
        });
    }
}
