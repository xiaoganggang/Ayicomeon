package com.com.gang.aiyicomeon.rongyun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.administrator.ayicomeon.R;

/**
 * Created by gang on 2017/3/15.
 */

public class ConversationAct extends FragmentActivity {
    //控件命名规范先是小写的m，String类型命名规范先是小写的s
    private TextView mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_activity);
        mName = (TextView) findViewById(R.id.duihuaname);
        String sId = getIntent().getData().getQueryParameter("targetId");//获取id，id是都可以获取到的
        String sName = getIntent().getData().getQueryParameter("title");//获取昵称，但是获取昵称的前提是使用了用户信息提供者不然获取不到
        //getIntent().getData().getLastPathSegment();//获取会话的类型，包括是群聊，单聊，还是聊天室等等
        if (!TextUtils.isEmpty(sName)) {
            mName.setText(sName);
        } else {
            //如果sName为空的话可能是信息提供者的问题,demo中还有看到push的判断，是推送的消息，点击后有一个重连的操作，如果不重连会报错


        }
    }
}
