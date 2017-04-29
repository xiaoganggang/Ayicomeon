package com.com.gang.aiyicomeon.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.com.gang.aiyicomeon.rongyun.FriendFragment;
import com.example.administrator.ayicomeon.R;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2017/3/28.
 */

public class LiaotianFragment extends Fragment implements View.OnClickListener {
    //给代码加一个单例模式
    public static LiaotianFragment instance = null;

    public static LiaotianFragment getInstance() {
        if (instance == null) {
            instance = new LiaotianFragment();
        }
        return instance;
    }

    private ViewPager mViewPagerjingdian;
    private List<Fragment> mFragmentsjidngdian;
    private FragmentPagerAdapter mAdapterJingdian;

    private Button gkbutton;
    private Button ylbutton;
    private Fragment mConversationList;
    private Fragment mConversationFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View kefuview = inflater.inflate(R.layout.liaotian_fragment, container, false);
        gkbutton = (Button) kefuview.findViewById(R.id.gongkaiclass);
        gkbutton.setOnClickListener(this);
        ylbutton = (Button) kefuview.findViewById(R.id.yinliangclass);
        ylbutton.setOnClickListener(this);
        mViewPagerjingdian = (ViewPager) kefuview.findViewById(R.id.jingdianviewpager);
        mFragmentsjidngdian = new ArrayList<Fragment>();
        //通过方法，获取融云会话列表的对象
        mConversationList = initConversationList();
        mFragmentsjidngdian.add(mConversationList);
        //这就是单例模式的好处，不用多次声明对象
        mFragmentsjidngdian.add(FriendFragment.getInstance());
        mAdapterJingdian = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            public int getCount() {
                return mFragmentsjidngdian.size();
            }

            public Fragment getItem(int position) {
                return mFragmentsjidngdian.get(position);
            }
        };
        mViewPagerjingdian.setAdapter(mAdapterJingdian);
        mViewPagerjingdian.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageSelected(int arg0) {
                int currentItem = mViewPagerjingdian.getCurrentItem();

            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            public void onPageScrollStateChanged(int arg0) {

            }
        });
        setSelect(0);
        return kefuview;
    }


    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.gongkaiclass:
                setSelect(0);
                break;
            case R.id.yinliangclass:
                setSelect(1);
                break;

            default:
                break;
        }
    }

    private void setSelect(int i) {
        setTab(i);
        mViewPagerjingdian.setCurrentItem(i);
    }

    @SuppressLint("NewApi")
    private void setTab(int i) {
        resetImgs();
        // 设置图片为亮色
        // 切换内容区域
        switch (i) {
            case 0:
                gkbutton.setAlpha(0.5f);
                break;
            case 1:
                ylbutton.setAlpha(0.5f);
                break;
        }
    }

    /**
     * 切换图片至暗色
     */
    @SuppressLint("NewApi")
    private void resetImgs() {
        gkbutton.setAlpha(1f);
        ylbutton.setAlpha(1f);
    }

    private Fragment initConversationList() {
        if (mConversationFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//讨论组
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                    .build();
            listFragment.setUri(uri);
            return listFragment;
        } else {
            return null;
        }
    }
}
