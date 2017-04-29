package com.com.gang.aiyicomeon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ayicomeon.R;

/**
 * Created by Administrator on 2017/1/22.
 */

public class KefuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View kefuview = inflater.inflate(R.layout.kefu_fragment, container, false);
        return kefuview;
    }
}

//加班怎么处理、请假、工资结算时间、