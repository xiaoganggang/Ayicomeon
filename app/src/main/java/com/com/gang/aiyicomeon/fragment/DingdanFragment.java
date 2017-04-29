package com.com.gang.aiyicomeon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.ayicomeon.R;
import com.com.gang.aiyicomeon.adapter.DingdanAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/22.
 */
public class DingdanFragment extends Fragment {
    private View dingdanview;
    private List<Map<String, Object>> dingdanlist;
    private Map<String, Object> dingdanmaplist;
    private ListView ddlistview;
    private DingdanAdapter dingdanAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dingdan_fragment, container, false);
        dingdanview = view;
        initDate();
        initView();
        return view;
    }
    //数据源
    private void initDate() {
        dingdanlist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 1; i++) {
            dingdanmaplist = new HashMap<String, Object>();
            dingdanmaplist.put("距离", "2.3公里");
            dingdanmaplist.put("年龄", "22");
            dingdanmaplist.put("姓名", "齐小刚");
            dingdanlist.add(dingdanmaplist);
        }
    }
    //初始化
    private void initView() {
        ddlistview = (ListView) dingdanview.findViewById(R.id.dingdanlistview);
        dingdanAdapter = new DingdanAdapter(getActivity(), dingdanlist);
        ddlistview.setAdapter(dingdanAdapter);
    }

}
