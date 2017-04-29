package com.com.gang.aiyicomeon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.com.gang.aiyicomeon.activity.LocationActivity;
import com.example.administrator.ayicomeon.R;
import com.com.gang.aiyicomeon.adapter.SimpleAdapter1;
import com.com.gang.aiyicomeon.zidingyiview.PullToRefreshScrollView;
import com.com.gang.aiyicomeon.zidingyiview.SlideShowView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gang on 2017/1/22.
 */

public class ZhuyeFragment extends Fragment {
    // 滚动的自定义view
    private SlideShowView mSlideShowViewyl;
    private RecyclerView mRecyclerView;
    private List<String> mDates;
    private SimpleAdapter1 mAdapter;
    private PullToRefreshScrollView pullscrollview;
    private LinearLayout jingxuan;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhuye_fragment, container, false);
        //设置图片主要是传入图片路径，可以是int类型的，也 可以像博客中的一样，传入String类型的
        List<Integer> imageUrisyl = new ArrayList<Integer>();
        imageUrisyl.add(R.mipmap.huademo);
        imageUrisyl.add(R.mipmap.huademo);
        imageUrisyl.add(R.mipmap.huademo);
        imageUrisyl.add(R.mipmap.huademo);
        // 获取控件
        mSlideShowViewyl = (SlideShowView) view.findViewById(R.id.slideshowViewyinliang);
        //为控件设置图片
        mSlideShowViewyl.setImageUris(imageUrisyl);
        //对阿姨特产进行代码编写
        initDate();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.ayitecanrecycle1);

        mAdapter = new SimpleAdapter1(getActivity(), mDates);
        mRecyclerView.setAdapter(mAdapter);
        //通过setLayoutManager设置显示什么view,设置RecyleView的布局管理
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置RecvcleView 的分割线
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        ////也可以通过设置magrin和padding来设置边距
        //gridview与listview之间的转换等
        //切换到gridview
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //切换到listview
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //切换到水平的gridview
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        //Scrollview刷新
        pullscrollview = (PullToRefreshScrollView) view.findViewById(R.id.pull_to_refresh_scroll);
        pullscrollview.setOnPullToRefreshListener(new PullToRefreshScrollView.OnPullToRefreshListener() {
            @Override
            public void onRefresh() {
                //在这里进行动画加载
            }
        });
        jingxuan = (LinearLayout) view.findViewById(R.id.meirijingxuan1);
        jingxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), LocationActivity.class);
                startActivity(a);
            }
        });
        return view;
    }

    private void initDate() {
        mDates = new ArrayList<String>();
        for (int i = 'A'; i <= 'H'; i++) {
            mDates.add("" + (char) i);
        }
    }

}
