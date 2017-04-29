package com.com.gang.aiyicomeon.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.com.gang.aiyicomeon.activity.Jiedan_xiangqing;
import com.example.administrator.ayicomeon.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/25.
 */

public class DingdanAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<Map<String, Object>> dingdanlist;

    //初始化的构造函数
    public DingdanAdapter(Context context, List<Map<String, Object>> dingdanlist) {
        this.context = context;
        this.dingdanlist = dingdanlist;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dingdanlist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler = null;
        //判断是否缓存，每次找listview的item浪费时间
        if (convertView == null) {
            viewHoler = new ViewHoler();
            convertView = mLayoutInflater.inflate(R.layout.dingdanitem, null);

            viewHoler.jiedanname = (TextView) convertView.findViewById(R.id.jiedanname);
            viewHoler.jiedanage = (TextView) convertView.findViewById(R.id.jiedanage);
            viewHoler.jiedanjuili = (TextView) convertView.findViewById(R.id.jiedanjuli);
            viewHoler.jiedanbaozeng = (TextView) convertView.findViewById(R.id.jiedanbaozhengjin);
            //两个点击按钮
            viewHoler.jiednxiangqing = (TextView) convertView.findViewById(R.id.jiedanxiangqing);
            viewHoler.jiedanpingjia = (TextView) convertView.findViewById(R.id.jiedanpingjia);
            viewHoler.jiedantouxiang = (ImageView) convertView.findViewById(R.id.jiedantouxiang);
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        viewHoler.jiedanname.setText((String) dingdanlist.get(position).get("姓名"));
        viewHoler.jiedanage.setText((String) dingdanlist.get(position).get("年龄"));
        viewHoler.jiedanjuili.setText("距您"+(String) dingdanlist.get(position).get("距离"));
        //查看详情点击事件
        viewHoler.jiednxiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ddxiangqing = new Intent();
                ddxiangqing.setClass(context, Jiedan_xiangqing.class);
                context.startActivity(ddxiangqing);
            }
        });
        return convertView;
    }

    //利用listview的缓存机制，避免每次都要寻找控件
    public final class ViewHoler {
        public TextView jiedanname;
        public TextView jiedanage;
        public TextView jiedanjuili;
        public TextView jiedanbaozeng;
        //两个点击按钮
        public TextView jiednxiangqing, jiedanpingjia;
        private ImageView jiedantouxiang;
    }
}
