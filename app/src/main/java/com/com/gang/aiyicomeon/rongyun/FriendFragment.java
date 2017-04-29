package com.com.gang.aiyicomeon.rongyun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.com.gang.aiyicomeon.adapter.PersonListViewAdapter;
import com.com.gang.aiyicomeon.javabeano.Friend_getFriends;
import com.com.gang.aiyicomeon.javabeano.GetFriends_gson;
import com.com.gang.aiyicomeon.utils.Config;
import com.com.gang.aiyicomeon.utils.LogUtil;
import com.example.administrator.ayicomeon.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import me.zhouzhuo.zzletterssidebar.ZzLetterSideBar;
import me.zhouzhuo.zzletterssidebar.interf.OnLetterTouchListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/3/15.
 * 带字母与标题头的高仿通讯录的listview  http://www.jianshu.com/p/4e673f5fbd37
 */

public class FriendFragment extends Fragment {
    private TextView oo;
    private OkHttpClient okHttpClient;
    private String share_username = "";
    private View friendviewlist;

    private ListView listView;
    private ZzLetterSideBar sideBar;
    private TextView dialog;
    private PersonListViewAdapter adapter;
    private List<Friend_getFriends> mDatas;
    private TextView tvFoot;

    private final static String TAG = "FriendFragment";
    //给代码加一个单例模式
    public static FriendFragment instance = null;

    public static FriendFragment getInstance() {
        if (instance == null) {
            instance = new FriendFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.friendliebiao, null);
        friendviewlist = mView;
        //缓存中获取学号
        //share_username = SpUtils.getString(getActivity(), "studentnum", "获取错误");
        getAllfriendlist();
        initView();
        initEvent();
        return mView;
    }

    private void initView() {
        sideBar = (ZzLetterSideBar) friendviewlist.findViewById(R.id.sidebar);
        dialog = (TextView) friendviewlist.findViewById(R.id.tv_dialog);
        listView = (ListView) friendviewlist.findViewById(R.id.list_view);
        //optional
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_head, null);
        listView.addHeaderView(header);

        //optional
        View footer = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_foot, null);
        tvFoot = (TextView) footer.findViewById(R.id.tv_foot);
        listView.addFooterView(footer);

        mDatas = new ArrayList<>();
        adapter = new PersonListViewAdapter(getActivity(), mDatas);
        listView.setAdapter(adapter);
    }

    private void getAllfriendlist() {
        okHttpClient = new OkHttpClient();
        String urls = Config.API.BASE_URL + "getFriends?sno=" + "2014060306004";
        Request request = new Request.Builder()
                .get()
                .url(urls)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //状态码是200
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String json = response.body().string();
                    GetFriends_gson model = gson.fromJson(json, GetFriends_gson.class);
                    //请求成功
                    //遍历状态
                    for (int i = 0; i < model.getData().getList().size(); i++) {
                        Friend_getFriends friendbean = new Friend_getFriends();
                        friendbean.setId(model.getData().getList().get(i).getId());
                        friendbean.setAvatar(model.getData().getList().get(i).getAvatar());
                        friendbean.setName(model.getData().getList().get(i).getName());
                        friendbean.setSno(model.getData().getList().get(i).getSno());
                        mDatas.add(friendbean);
                    }
                }
            }
        });
    }


    public void initEvent() {

        //设置右侧触摸监听
        sideBar.setLetterTouchListener(listView, adapter, dialog, new OnLetterTouchListener() {
            @Override
            public void onLetterTouch(String letter, int position) {
            }

            @Override
            public void onActionUp() {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= listView.getHeaderViewsCount()) {
                    TextView tvName = (TextView) view.findViewById(R.id.friend_list_num);
                    String name = tvName.getText().toString();
                    Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                    if (RongIM.getInstance() != null) {
                        //参数值第一个参数上下文对象，第二个参数是对应的userid，第三个参数是聊天会话对应的title名
                        //开启单聊模式
                        RongIM.getInstance().startPrivateChat(getActivity(), "2014060306006", "好友聊天");
                    }
                }
            }
        });
    }
}
