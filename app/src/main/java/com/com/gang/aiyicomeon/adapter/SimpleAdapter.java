package com.com.gang.aiyicomeon.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.administrator.ayicomeon.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/25.
 * http://m.blog.csdn.net/article/details?id=50802898学习RecyleView加载不同的view
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ItemViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    private Context context;
    private List<String> hehe = new ArrayList<>();
    private List<Map<String, Object>> date = new ArrayList<Map<String, Object>>();
    //增加脚布局需要用的这个与listview不同没有添加addView的api
    private static final int TYPE_ITEM = 0;       //普通Item View
    private static final int TYPE_FOOTER = 1;    //底部FootView


    //添加点击事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String hehe);
    }

    //定义完接口，添加接口和设置Adapter接口的方法：
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //一个构造函数
    public SimpleAdapter(Context context, List<Map<String, Object>> date) {
        this.context = context;
        this.date = date;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getItemCount() {
        return date.size();    //因为在底部添加了脚布局，所以Count的数量要+1
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //创建一个viewholder
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //判断返回的ViewType是那种类型
        View view = mInflater.inflate(R.layout.classjiluitem, parent, false);
        ItemViewHolder vh = new ItemViewHolder(view);
        //ItemViewHolder ItemViewHoldero = new ItemViewHolder(mInflater.inflate(R.layout.classjiluitem, parent, false));
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        //根据返回的holder判断是普通条目还是脚布局，然后进行设置
            //将数据保存在itemView的Tag中，以便点击时进行获取
            ((ItemViewHolder) holder).itemView.setTag(date.get(position).get("每分钟学生状态"));
            if (date.get(position).get("每分钟学生状态").equals("111111")) {
                ((ItemViewHolder) holder).a.setBackgroundColor(Color.parseColor("#78C06E"));
            } else if (date.get(position).get("每分钟学生状态").equals("111100")) {
                ((ItemViewHolder) holder).a.setBackgroundColor(Color.parseColor("#78C06E"));
            } else if (date.get(position).get("每分钟学生状态").equals("001111")) {
                ((ItemViewHolder) holder).a.setBackgroundColor(Color.parseColor("#78C06E"));
            } else if (date.get(position).get("每分钟学生状态").equals("110011")) {
                ((ItemViewHolder) holder).a.setBackgroundColor(Color.parseColor("#78C06E"));
            } else if (date.get(position).get("每分钟学生状态").equals("110000")) {
                ((ItemViewHolder) holder).a.setBackgroundColor(Color.parseColor("#FF6666"));
            } else if (date.get(position).get("每分钟学生状态").equals("001100")) {
                ((ItemViewHolder) holder).a.setBackgroundColor(Color.parseColor("#FF6666"));
            } else if (date.get(position).get("每分钟学生状态").equals("000011")) {
                ((ItemViewHolder) holder).a.setBackgroundColor(Color.parseColor("#FF6666"));
            } else if (date.get(position).get("每分钟学生状态").equals("000000")) {
                ((ItemViewHolder) holder).a.setBackgroundColor(Color.parseColor("#FF6666"));
            }
    }

    public static class ItemViewHolder extends ViewHolder {
        TextView a;

        public ItemViewHolder(View itemView) {
            super(itemView);
            a = (TextView) itemView.findViewById(R.id.minjilu3);
        }

    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }

}




